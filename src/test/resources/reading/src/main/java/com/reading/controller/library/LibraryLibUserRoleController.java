package com.reading.controller.library;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.model.Result;
import com.reading.utils.LogUtil;
import com.reading.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
@Controller
@RequestMapping("library/libuserrole")
public class LibraryLibUserRoleController extends BaseLibraryController {

    @Resource
    FunctionsService functionsService;

    @Resource
    LibraryFunctionsService libraryFunctionsService;

    @Resource
    LibraryService libraryService;

    @Resource
    LibUserRoleService service;
    @Resource
    LogOperateService logOperateService;

    String table = "libuserrole";

    /**
     * 列表页面
     *
     * @param p       页码
     * @param request 请求体
     * @return 页面
     */
    @RequestMapping("list")
    public String list(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 0 ? p : 1;
        LibUserRoleExample example = new LibUserRoleExample();
        example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId());

        List <LibUserRole> libUserRoleList = service.pageByExample(p, 20, example);
        int i = (p - 1) * 20 + 1;
        for (LibUserRole libUserRole : libUserRoleList) {
            libUserRole.setNumber(i);
            i++;
        }
        request.setAttribute("list", libUserRoleList);
        request.setAttribute("count", service.countByLibrary(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display(table + "_list");
    }

    /**
     * 新增页面
     *
     * @param request 请求体
     * @return 页面
     */
    @RequestMapping("add")
    public String add(HttpServletRequest request) {
        Library library = libraryService.find(getLibrary(request).getId());
        List <Long> libAdminAuths = new Gson().fromJson(library.getFunctions(), new TypeToken <List <Long>>() {
        }.getType());
        libAdminAuths.add(-1l);
        FunctionsExample functionsExample = new FunctionsExample();
        FunctionsExample.Criteria criteria = functionsExample.createCriteria();
        criteria.andStatusEqualTo(1).andOpenEqualTo(1).andIdNotEqualTo(23l).andIdIn(libAdminAuths);


        List <Functions> functionsList = functionsService.selectByExample(functionsExample);
        LibraryFunctionsExample libraryFunctionsExample = new LibraryFunctionsExample();
        libraryFunctionsExample.createCriteria().andStatusEqualTo(1).andOpenEqualTo(1).andLibraryIdEqualTo(library.getId());
        List <LibraryFunctions> libraryFunctionsList = libraryFunctionsService.selectByExample(libraryFunctionsExample);
        request.setAttribute("functionsList", functionsList);
        request.setAttribute("libraryFunctionsList", libraryFunctionsList);
        request.setAttribute("item", library);
        return display(table + "_add");
    }

    /**
     * 新增操作
     *
     * @param request 请求体
     * @return 结果
     */
    @RequestMapping("addDo")
    @ResponseBody
    public Result <LibUserRole> addDo(HttpServletRequest request, String roleName) {

        LibUserRoleExample libUserRoleExample = new LibUserRoleExample();
        libUserRoleExample.createCriteria().andRoleNameEqualTo(roleName).andLibraryIdEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);
        if (service.selectByExample(libUserRoleExample).size() > 0) {
            return ResultUtil.error("角色已存在");
        } else {
            LibUserRole bean = new LibUserRole();
            String[] functionsArr = request.getParameterValues("funcation");
            String[] libraryArr = request.getParameterValues("libraryFunction");
            bean.setFunctions(fz(functionsArr));
            bean.setLibraryFunctions(fz(libraryArr));
            bean.setRoleName(roleName);
            bean.setStatus(1);
            bean.setLibraryId(getLibrary(request).getId());
            LibUserRole libUserRole = service.add(bean);

            if (libUserRole != null) {
                logOperateService.operatingData(getLibrary(request), "libuserrole", getLibAdmin(request), 1l, 10l);
                return ResultUtil.success(null, "操作成功", "/library/" + table + "/list.html");
            } else {
                return ResultUtil.error("操作失败");
            }
        }
    }

    public String fz(String[] arr) {
        if(arr==null){
            return "[]";
        }
        String st = "";
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                st = arr[0];
            } else {
                st += "," + arr[i];
            }
        }
        return "["+st+"]";
    }

    /**
     * 删除操作
     *
     * @param id      操作对象id字符串，多个以英文逗号隔开
     * @param request 请求体
     * @return 结果
     */
    @RequestMapping("del")
    @ResponseBody
    public Result <LibAdmin> del(@RequestParam(value = "id", required = true) String id, HttpServletRequest request) {
        String[] arr = id.split(",");
        int i = 0;
        for (String item : arr) {
            if (item == null || item.length() < 1) {
                continue;
            }
            int itemInt = Integer.parseInt(item);
            LibUserRole t = service.find(itemInt);
            if (t != null) {
                t.setStatus(0);
                service.save(t);
                i++;
                logOperateService.operatingData(getLibrary(request), "libuserrole", getLibAdmin(request), 4l, 10l);
            }
        }
        return ResultUtil.success(null, "" + i + "条数据被删除", "/library/" + table + "/list.html");
    }


    /**
     * 编辑页面
     *
     * @param id      编辑对象id
     * @param request 请求体
     * @return 页面
     */
    @RequestMapping("edit")
    public String edit(@RequestParam(value = "id", required = true) long id, HttpServletRequest request) {

        LibUserRole libUserRole = service.find(id);
        List <Long> funcationlist = new Gson().fromJson(libUserRole.getFunctions(), new TypeToken <List <Long>>() {
        }.getType());
//获得图书馆所有的app端功能
        List <Long> libraryfuncationlist = new Gson().fromJson(getLibrary(request).getFunctions(), new TypeToken <List <Long>>() {
        }.getType());

        List <Functions> functionsList = new ArrayList<>();

        if(libraryfuncationlist != null && libraryfuncationlist.size()>0){
            FunctionsExample functionsExample = new FunctionsExample();
            functionsExample.createCriteria().andStatusEqualTo(1).andIdIn(libraryfuncationlist).andOpenEqualTo(1);
            functionsList = functionsService.selectByExample(functionsExample);
        }

        LibraryFunctionsExample libraryFunctionsExample = new LibraryFunctionsExample();
        libraryFunctionsExample.createCriteria().andStatusEqualTo(1).andOpenEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId());
        List <LibraryFunctions> libraryFunctionsList = libraryFunctionsService.selectByExample(libraryFunctionsExample);
        request.setAttribute("libraryFunctionsList", libraryFunctionsList);
        request.setAttribute("data", libUserRole);
        request.setAttribute("functionsList", functionsList);
        request.setAttribute("userRole", libUserRole.getFunctions().replace("[", "").replace("]", ""));
        request.setAttribute("userLibraryRole", libUserRole.getLibraryFunctions()==null?"":libUserRole.getLibraryFunctions().replace("[", "").replace("]", ""));
        return display(table + "_edit");
    }

    /**
     * 编辑操作
     *
     * @param request 请求体
     * @return 结果
     */
    @RequestMapping("editDo")
    @ResponseBody
    public Object editDo(HttpServletRequest request) {
        LibUserRole bean = service.find(Long.parseLong(request.getParameter("id")));
        String roleName = request.getParameter("roleName");
        bean.setRoleName(roleName);
        String[] functionsArr = request.getParameterValues("functions");
        String[] libraryArr = request.getParameterValues("libraryFunction");

        bean.setFunctions(fz(functionsArr));
        bean.setLibraryFunctions(fz(libraryArr));
        LibUserRole libUserRole1 = service.save(bean);

        if (libUserRole1 != null) {
            logOperateService.operatingData(getLibrary(request), "libuserrole", getLibAdmin(request), 2l, 10l);
            return ResultUtil.success(null, "操作成功", "/library/" + table + "/list.html");
        } else {
            return ResultUtil.error("操作失败");

        }
    }


}



