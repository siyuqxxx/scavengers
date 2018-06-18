package com.reading.controller.admin;

import com.reading.base.BaseAdminController;
import com.reading.data.model.Library;
import com.reading.data.model.RobotConfig;
import com.reading.data.service.LibraryService;
import com.reading.data.service.RobotConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */
@Controller
@RequestMapping("admin/robotConfig")
public class AdminRobotController extends BaseAdminController<RobotConfig> {
    @Resource
    LibraryService libraryService;
    RobotConfigService service;
    String table = "robotConfig";

    @Resource
    public void setService(RobotConfigService service) {
        this.service = service;
        super.setService(service);
        super.setTable(table);
    }

    @ModelAttribute("libraryList")
    public List<Library> getLibraryList(HttpServletRequest request) {
        String sql = "SELECT * from library where `status`=1 and id not  in (SELECT library_id from robot_config where `status`=1)";
        List<Library> libraryList = libraryService.selectBySql(sql);
        return libraryList;
    }

}
