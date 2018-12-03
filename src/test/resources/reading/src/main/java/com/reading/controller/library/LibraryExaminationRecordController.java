package com.reading.controller.library;

import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.model.Result;
import com.reading.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by change on 2017/9/20.
 * 考试记录管理
 */
@Controller
@RequestMapping("library/examinationrecord")
public class LibraryExaminationRecordController extends BaseLibraryController {
    @Resource
    ExaminationRecordService service;

    String table = "examinationrecord";

    /**
     * 列表页面
     * @param p       页码
     * @param request 请求体
     * @param model   model
     * @return 页面
     */
    @RequestMapping("list")
    public String list(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request, Model model) {
        p = p != null && p > 0 ? p : 1;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String current = simpleDateFormat.format(date);
        calendar.add(calendar.MONTH, -1);
        String firsttime = simpleDateFormat.format(calendar.getTime());

        ExaminationRecordExample example = new ExaminationRecordExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId());

        List<ExaminationRecord> examinationRecords = service.pageByExample(p, 20, example);
        int i = (p - 1) * 20 + 1;
        for (ExaminationRecord examinationRecord : examinationRecords) {
            examinationRecord.setNumbers(i);
            i++;
        }

        request.setAttribute("statusId", -1);
        request.setAttribute("list", examinationRecords);
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        request.setAttribute("current", current);
        request.setAttribute("firsttime", firsttime);
        return display(table + "_list");
    }


//    /**
//     * 批量导出学生成绩列表页面
//     * @param p       页码
//     * @param request 请求体
//     * @param model   model
//     * @return 页面
//     */
//    @RequestMapping("exportListd")
//    public String exportListd(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request, Model model) {
//        p = p != null && p > 0 ? p : 1;
//
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(calendar.MONTH, -1);
//        String current = simpleDateFormat.format(date);
//        String firsttime = simpleDateFormat.format(calendar.getTime());
//
//        UserLibraryExample example = new UserLibraryExample();
//        example.setOrderByClause("last_time desc");
//        example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId()).andExamscoreIsNotNull().andPassEqualTo(1);//考试成绩不为空并且通过考试
//        List<UserLibrary> userLibraries = userLibraryService.pageByExample(p, 20, example);
//        int i = (p - 1) * 20 + 1;
//        for (UserLibrary userLibrary : userLibraries) {
//            //examination.setLibraryName(libraryService.find(getLibrary(request).getId()).getTitle());
//            // Long typeId = Long.parseLong(examination.getType());
//            userLibrary.setNumber(i);
//            i++;
//        }
//
////        ExaminationTypeExample example1 = new ExaminationTypeExample();
////        example1.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId());
////        List<ExaminationType> examinationTypeList = examinationTypeService.selectByExample(example1);
//
//        request.setAttribute("statusId", -1);
//        request.setAttribute("list", userLibraries);
//        request.setAttribute("namew","");
//        request.setAttribute("count", userLibraryService.countByLibrary(example));
//        request.setAttribute("size", 20);
//        request.setAttribute("p", p);
//        request.setAttribute("current", current);
//        request.setAttribute("firsttime", firsttime);
//        return display(table + "_exportlist");
//    }
//
//    /**
//     * 批量导出学生成绩列表搜所页面
//     * @param p       页码
//     * @param request 请求体
//     * @param model   model
//     * @return 页面
//     */
//    @RequestMapping("exportSearch")
//    public String exportSearch(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request, Model model) {
//        p = p != null && p > 0 ? p : 1;
//        String namew = request.getParameter("namew");
//        UserLibraryExample example = new UserLibraryExample();
//        example.setOrderByClause("last_time desc");
//        if(namew!=null&&"".equals(namew)){
//            example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId()).andExamscoreIsNotNull().andPassEqualTo(1);//考试成绩不为空并且通过考试
//        }else {
//            example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId()).andExamscoreIsNotNull().andPassEqualTo(1).andCardNumberEqualTo(namew.trim());//考试成绩不为空并且通过考试
//        }
//        int i = (p - 1) * 20 + 1;
//        List<UserLibrary> userLibraries = userLibraryService.selectByExample(example);
//        for (UserLibrary userLibrary : userLibraries) {
//            //examination.setLibraryName(libraryService.find(getLibrary(request).getId()).getTitle());
//            // Long typeId = Long.parseLong(examination.getType());
//            userLibrary.setNumber(i);
//            i++;
//        }
//
//        request.setAttribute("statusId", -1);
//        request.setAttribute("list", userLibraries);
//        request.setAttribute("namew",namew);
//        request.setAttribute("count", userLibraryService.countByLibrary(example));
//        request.setAttribute("size", null);
//        request.setAttribute("p", p);
//        return display(table + "_exportlist");
//    }
//

    /**
     * 搜索
     * @param p
     * @param request
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(@RequestParam(value = "p", required = false) Integer p,
                         HttpServletRequest request) {

        p = p != null && p > 0 ? p : 1;

        String current = request.getParameter("current");
        String firsttime = request.getParameter("firsttime");
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        long statusId = -1 ;
        try {
            Date firsttime1 = new SimpleDateFormat("yyyy-MM-dd").parse(firsttime);
            Date current1 =  new Date(new SimpleDateFormat("yyyy-MM-dd").parse(current).getTime()+24*60*60*1000);
            String time1 = simpleDateFormat.format(firsttime1) ;
            String time2 = simpleDateFormat.format(current1) ;
            String sql = "SELECT * FROM `examination_record` where `status` = 1 and library_id = " + getLibrary(request).getId() + " and create_time BETWEEN '"+ time1 +"' and '"+ time2 +"' " ;
            if(type !=null && !"-1".equals(type)){
                statusId = Long.parseLong(type);
                sql += " and is_pass_status = "+type+"" ;
            }
            if(name !=null && !"".equals(name)){
                sql += " and card_number like '%"+name+"%'" ;
            }
            List<ExaminationRecord> records = service.selectBySql(sql);
            int i = (p - 1) * 20 + 1;
            for (ExaminationRecord examinationRecord : records) {
                examinationRecord.setNumbers(i);
                i++;
            }
            request.setAttribute("list", records);
            request.setAttribute("statusId", statusId);
            request.setAttribute("count", records.size());
            request.setAttribute("size", null);
            request.setAttribute("p", p);
            request.setAttribute("current", current);
            request.setAttribute("firsttime", firsttime);
            request.setAttribute("name", name);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return display(table + "_list");
    }
//
//    @RequestMapping(value = "importSearch", method = RequestMethod.POST)
//    public String importSearch(@RequestParam(value = "p", required = false) Integer p,
//                         HttpServletRequest request) {
//
//        p = p != null && p > 0 ? p : 1;
//
//        String current = request.getParameter("current");
//        String firsttime = request.getParameter("firsttime");
//
//        try {
//            Date firsttime1 = new SimpleDateFormat("yyyy-MM-dd").parse(firsttime);
//            Date current1 =  new Date(new SimpleDateFormat("yyyy-MM-dd").parse(current).getTime()+24*60*60*1000);
//            ExaminationExample example = new ExaminationExample();
//            example.setOrderByClause("createTime desc");
//            example.createCriteria().andStatusEqualTo(0).andLibraryidEqualTo(getLibrary(request).getId()).andTypeIsNull().andCreatetimeBetween(firsttime1,current1);
//            List<Examination> examinationList = service.selectByExample(example);
//            int i = (p - 1) * 20 + 1;
//            for (Examination examination : examinationList) {
//                examination.setNumbers(i);
//                i++;
//            }
//
//            ExaminationTypeExample example1 = new ExaminationTypeExample();
//            example1.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId());
//            List<ExaminationType> examinationTypeList = examinationTypeService.selectByExample(example1);
//
//            request.setAttribute("list", examinationList);
//            request.setAttribute("ExaminationTypeLists",examinationTypeList);
//            request.setAttribute("count", service.countByExamination(example));
//            request.setAttribute("size", null);
//            request.setAttribute("p", p);
//            request.setAttribute("current", current);
//            request.setAttribute("firsttime", firsttime);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return display(table + "_importlist");
//    }
//


    /**
     * excel导出学生成绩信息
     * @param p
     * @param response
     * @return
     */
    @RequestMapping("exportList")
    @ResponseBody
    public Result<UserLibrary> exportList(HttpServletRequest request ,
                                          @RequestParam(value = "p", required = false) Integer p,
                                          HttpServletResponse response) {
        p = p != null && p > 0 ? p : 1;
        String current = request.getParameter("current");
        String firstTime = request.getParameter("firstTime");
        String type = request.getParameter("type");
        String cardNumber = request.getParameter("cardNumber");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        try {
            Date firsttime1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstTime);
            Date current1 =  new Date(new SimpleDateFormat("yyyy-MM-dd").parse(current).getTime()+24*60*60*1000);
            String time1 = simpleDateFormat.format(firsttime1) ;
            String time2 = simpleDateFormat.format(current1) ;
            String sql = "SELECT * FROM `examination_record` where 1=1 and create_time BETWEEN '"+ time1 +"' and '"+ time2 +"' and library_id = "+ getLibrary(request).getId() ;
            if(type !=null && !"-1".equals(type)){
                sql += " and is_pass_status = "+type+"" ;
            }
            if(cardNumber !=null && !"".equals(cardNumber)){
                sql += " and card_number like '%"+cardNumber+"%'" ;
            }
            List<ExaminationRecord> recordList = service.selectBySql(sql) ;
            //写入Excel
            WritableWorkbook book = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //生成的Excel文件名
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "入馆培训考试记录下载";
                // 打开文件
                book = Workbook.createWorkbook(response.getOutputStream());
                // 生成格式名称为："当前时间"的工作表，参数0表示这是第一页

                WritableSheet sheet = book.createSheet("考试记录", 0);
                jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#0.00"); // 设置数字格式
                jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf); // 设置表单格式
                wcf.setAlignment(Alignment.LEFT); // 设置为右对齐
                if (recordList != null && !recordList.isEmpty()) {
                    for (int i = 0; i < recordList.size(); i++) {
                        sheet.setRowView(i, 400);  //设置行的高度
                        sheet.setColumnView(i, 25);  //设置列的宽度
                        if (i == 0) {
                            //表头
                            sheet.addCell(new Label(0, i, "读者证号"));
                            sheet.addCell(new Label(1, i, "考试成绩"));
                            sheet.addCell(new Label(2, i, "当前关数"));
                            sheet.addCell(new Label(3, i, "通过状态"));
                            sheet.addCell(new Label(4, i, "考试时间"));
                        }
                        sheet.addCell(new Label(0, i + 1, recordList.get(i).getCardNumber()));
                        sheet.addCell(new Label(1, i + 1, String.valueOf(recordList.get(i).getUserScore())));
                        sheet.addCell(new Label(2, i + 1, String.valueOf(recordList.get(i).getCurrentPass())));
                        sheet.addCell(new Label(3, i + 1, recordList.get(i).getIsPassStatus() == 0 ? "未通过" : "已通过"));
                        sheet.addCell(new Label(4, i + 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordList.get(i).getCreateTime())));
                    }
                    sheet.setRowView(recordList.size(), 400);  //设置行的高度
                }
                //写入数据并关闭文件
                book.write();
                //防止乱码
                filename = new String(filename.getBytes(), "iso-8859-1");
                response.setCharacterEncoding("gb2312");
                response.reset();
                response.setContentType("application/OCTET-STREAM;charset=gb2312");
                response.setHeader("pragma", "no-cache");
                //点击导出excle按钮时候页面显示的默认名称
                response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + ".xls\"");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LogUtil.log("关闭输出流！");
                if (book != null) {
                    try {
                        book.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                LogUtil.log("导出结束！");
                return ResultUtil.success(null, "导出成功", "/library/" + table + "/list.html");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultUtil.error("导出失败");
    }
}