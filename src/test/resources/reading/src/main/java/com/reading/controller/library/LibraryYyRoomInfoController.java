package com.reading.controller.library;

import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.model.Result;
import com.reading.utils.*;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/11/28.
 */
@Controller
@RequestMapping("library/yyroominfo")
public class LibraryYyRoomInfoController extends BaseLibraryController {

    @Resource
    private YySeatInfoService yySeatInfoService;
    @Resource
    private YyTimeSettingDetailService yyTimeSettingDetailService;
    @Resource
    private YyTimeSettingService yyTimeSettingService;
    @Resource
    private YyFloorInfoService yyFloorInfoService;
    @Resource
    private YyBuildingInfoService yyBuildingInfoService;
    @Resource
    private YyRoomInfoService service;
    @Resource
    private LogOperateService logOperateService;
    @Resource
    private MessageService messageService;
    @Resource
    private YySeatExamAppointmentService yySeatExamAppointmentService;
    @Resource
    private LibraryService libraryService;
    @Resource
    private LibraryCollegeService libraryCollegeService;

    String table = "yyroominfo";

    @RequestMapping("list")
    public String list(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        YyRoomInfoExample example = new YyRoomInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andTypeNotEqualTo(EnumUtil.ROOM_TYPE.exam_seat.getType_value());
        List <YyRoomInfo> yyRoomInfoList = service.selectByExample(p, 20, example);


        request.setAttribute("list", fz(yyRoomInfoList, p));
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display(table + "_list");
    }


    public List <YyRoomInfo> fz(List <YyRoomInfo> yyRoomInfoList, Integer p) {
        if (yyRoomInfoList.size() > 0) {
            int i = (p - 1) * 20 + 1;
            for (YyRoomInfo yyRoomInfo : yyRoomInfoList) {
                yyRoomInfo.setNumber(i);
                i++;
                yyRoomInfo.setDate(yyRoomInfo.getCreatedatetime().getTime());

                YyTimeSettingDetailExample ytsdEx = new YyTimeSettingDetailExample();
                ytsdEx.createCriteria().andUseStatusEqualTo("1").andTimeSettingIdEqualTo(yyRoomInfo.getTimesettingid());
                List <YyTimeSettingDetail> listTemp = yyTimeSettingDetailService.selectByExample(ytsdEx);

                //排序
                List <YyTimeSettingDetail> list = yyTimeSettingDetailService.getSortList(listTemp);

                YyTimeSetting setT = yyTimeSettingService.find(yyRoomInfo.getTimesettingid());

                String timeMark = "别名(代号):";
                if (setT.getTimeMark() != null && !setT.getTimeMark().isEmpty()) {
                    timeMark += setT.getTimeMark();
                }

                List <String> openTimes = new ArrayList();
                openTimes.add(timeMark);

                for (YyTimeSettingDetail t : list) {
                    String openTime = EnumUtil.WEEKLY.getValueByCode(t.getWeekType()).toString()
                            + "开放时间：" + t.getStartTime() + "-" + t.getEndTime();
                    openTimes.add(openTime);
                }

                yyRoomInfo.setOpenTimes(openTimes);
            }
        }
        return yyRoomInfoList;
    }

    //获取普通座位预约（普通）
    @ModelAttribute("getRoomTypeOfSeat")
    public List <EnumUtil.ROOM_TYPE> getRoomTypeOfSeat() {
        List <EnumUtil.ROOM_TYPE> list = new ArrayList();
        list.add(EnumUtil.ROOM_TYPE.ordinary_seat);
        list.add(EnumUtil.ROOM_TYPE.collection);
        return list;
    }

    @RequestMapping("add")
    public Object add(HttpServletRequest request) {
        YyBuildingInfoExample example = new YyBuildingInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId());
        YyTimeSettingExample example1 = new YyTimeSettingExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);

        LibraryCollegeExample lcEx = new LibraryCollegeExample();
        lcEx.createCriteria().andLibraryIdEqualTo(getLibrary(request).getId()).andUseStatusEqualTo("1");
        List<LibraryCollege> collegeList = libraryCollegeService.selectByExample(lcEx);

        request.setAttribute("yyTimeSetingList", yyTimeSettingService.selectByExample(example1));
        request.setAttribute("yybuildinginfolist", yyBuildingInfoService.selectAll(example));
        request.setAttribute("collegeList", collegeList);
        return display(table + "_add");
    }

    @RequestMapping(value = "surchbybuilding", method = RequestMethod.POST)
    @ResponseBody
    public List <YyFloorInfo> surchbybuilding(HttpServletRequest request) {

        String buildingId = request.getParameter("buildingId");
        YyFloorInfoExample example = new YyFloorInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andBulidingidEqualTo(Long.parseLong(buildingId));
        return yyFloorInfoService.selectByExample(example);
    }

    @RequestMapping("addDos")
    @ResponseBody
    public Object addDos(HttpServletRequest request) {

        String buildingId = request.getParameter("buildingId");
        String floorid = request.getParameter("floorid");
        String roomname = request.getParameter("roomname");
        String content = request.getParameter("content");
        String timesetId = request.getParameter("timesetId");
        String type = request.getParameter("type");
        String restrictedAppointment = request.getParameter("restrictedAppointment");
        String canAppointmentCollege = request.getParameter("canAppointmentCollege");
//        String ibeacon1 = request.getParameter("ibeacon");
//        String[] ibeacons = ibeacon1.split(",");
        YyRoomInfo yyRoomInfo = new YyRoomInfo();
        yyRoomInfo.setType(type);
        yyRoomInfo.setLibraryid(getLibrary(request).getId());
        yyRoomInfo.setLibraryname(getLibrary(request).getTitle());
        yyRoomInfo.setBulidingid(Long.parseLong(buildingId));
        yyRoomInfo.setBuildingname(yyBuildingInfoService.find(Long.parseLong(buildingId)).getBuildingname());
        yyRoomInfo.setContent(content);
        yyRoomInfo.setRoomname(roomname);
        yyRoomInfo.setTimesettingid(Long.parseLong(timesetId));
        yyRoomInfo.setCreatedatetime(new Date());
        yyRoomInfo.setFloorid(Long.parseLong(floorid));
        yyRoomInfo.setStatus(1);
        yyRoomInfo.setFloorname(yyFloorInfoService.find(Long.parseLong(floorid)).getFloorname());
        yyRoomInfo.setPicurl(UploadUtil.uploadw(request, "picurl", "picurl", null));

        yyRoomInfo.setRestrictedAppointment(restrictedAppointment);
        yyRoomInfo.setCanAppointmentCollege(canAppointmentCollege);
        if(Objects.equals(restrictedAppointment,"0")){
            yyRoomInfo.setCanAppointmentCollege("");
        }
        YyRoomInfo yyRoomInfo1 = service.add(yyRoomInfo);
        //插入ibeacon表
//        for (String str : ibeacons) {
//            YyIbeacon yyIbeacon = new YyIbeacon();
//            yyIbeacon.setLibraryid(getLibrary(request).getId());
//            yyIbeacon.setLibraryname(getLibrary(request).getTitle());
//            yyIbeacon.setRoomid(yyRoomInfo1.getKeyid());
//            yyIbeacon.setRoomname(yyRoomInfo1.getRoomname());
//            yyIbeacon.setStatus(1);
//            yyIbeacon.setSerialNumber(str);
//            yyIbeaconService.add(yyIbeacon);
//        }

        logOperateService.operatingData(getLibrary(request), "yyroominfo", getLibAdmin(request), 1l, 18l);
        return ResultUtil.success(null, "添加成功", "/library/" + table + "/list.html");
    }

    @RequestMapping("exportLists")
    public void exportLists(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        String[] ids = id.split(",");
        exportList(ids, response);

    }


    public Result <YyRoomInfo> exportList(String[] ids, HttpServletResponse response) {
        //写入Excel
        WritableWorkbook book = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //生成的Excel文件名
            String filename = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "_房间配置信息.xls";
            // 打开文件
            book = Workbook.createWorkbook(response.getOutputStream());
            // 生成格式名称为："当前时间"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("房间配置信息", 0);
            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#0.00"); // 设置数字格式
            jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf); // 设置表单格式
            wcf.setAlignment(Alignment.LEFT); // 设置为右对齐

            if (ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    sheet.setRowView(i, 400);  //设置行的高度
                    sheet.setColumnView(i, 25);  //设置列的宽度
                    if (i == 0) {
                        //表头
                        sheet.addCell(new Label(0, i, "楼宇"));
                        sheet.addCell(new Label(1, i, "楼层"));
                        sheet.addCell(new Label(2, i, "房间"));
                        sheet.addCell(new Label(3, i, "座位数"));

                    }
                    YyRoomInfo bean = service.find(Long.parseLong(ids[i]));

                    sheet.addCell(new Label(0, i + 1, bean.getBuildingname()));
                    sheet.addCell(new Label(1, i + 1, bean.getFloorname()));
                    sheet.addCell(new Label(2, i + 1, bean.getRoomname()));

                    //统计房间的座位个数
                    YySeatInfoExample example = new YySeatInfoExample();
                    example.createCriteria().andRoomidEqualTo(Long.parseLong(ids[i]));

                    sheet.addCell(new Label(3, i + 1, yySeatInfoService.countByLibrary(example) + ""));


                }
                sheet.setRowView(ids.length, 400);  //设置行的高度
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
            return ResultUtil.success(null, "导出成功");

        }

    }

    @RequestMapping("edits")
    public Object edits(HttpServletRequest request) {
        YyRoomInfo yyRoomInfo = service.find(Long.parseLong(request.getParameter("id")));

        YyFloorInfoExample example = new YyFloorInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andBulidingidEqualTo(yyRoomInfo.getBulidingid());

        YyTimeSettingExample example1 = new YyTimeSettingExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);

        Library library = libraryService.find(yyRoomInfo.getLibraryid());

        LibraryCollegeExample lcEx = new LibraryCollegeExample();
        lcEx.createCriteria().andLibraryIdEqualTo(yyRoomInfo.getLibraryid()).andUseStatusEqualTo("1");
        List<LibraryCollege> collegeList = libraryCollegeService.selectByExample(lcEx);

        request.setAttribute("yyTimeSetingList", yyTimeSettingService.selectByExample(example1));
        request.setAttribute("yyflootinfolist", yyFloorInfoService.selectByExample(example));
        request.setAttribute("data", yyRoomInfo);
        request.setAttribute("collegeList", collegeList);
        request.setAttribute("openCollege", library.getOpenCollege() != null ?library.getOpenCollege():"0");
        return display(table + "_edit");
    }

    @RequestMapping("editdos")
    @ResponseBody
    public Object editdos(HttpServletRequest request) {
        String floorid = request.getParameter("floorid");
        String roomname = request.getParameter("roomname");
        String content = request.getParameter("content");
        String timesetId = request.getParameter("timesetId");
        String type = request.getParameter("type");
        String restrictedAppointment = request.getParameter("restrictedAppointment");
        String canAppointmentCollege = request.getParameter("canAppointmentCollege");

        YyRoomInfo yyRoomInfo = service.find(Long.parseLong(request.getParameter("id")));

        yyRoomInfo.setType(type);
        yyRoomInfo.setTimesettingid(Long.parseLong(timesetId));
        yyRoomInfo.setContent(content);

        yyRoomInfo.setRoomname(roomname);
        yyRoomInfo.setUpdatedatetime(new Date());
        yyRoomInfo.setFloorid(Long.parseLong(floorid));

        yyRoomInfo.setFloorname(yyFloorInfoService.find(Long.parseLong(floorid)).getFloorname());
        yyRoomInfo.setRestrictedAppointment(restrictedAppointment);
        yyRoomInfo.setCanAppointmentCollege(canAppointmentCollege);
        if(Objects.equals(restrictedAppointment,"0")){
            yyRoomInfo.setCanAppointmentCollege("");
        }
        yyRoomInfo.setPicurl(UploadUtil.uploadw(request, "picurl", "picurl", null));

        logOperateService.operatingData(getLibrary(request), "yyroominfo", getLibAdmin(request), 2l, 18l);
        service.save(yyRoomInfo);
        return ResultUtil.success(null, "添加成功", "/library/" + table + "/list.html");
    }

    /**
     * 关闭
     *
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("closeItems")
    @ResponseBody
    public Result <YyRoomInfo> closeItems(@RequestParam(value = "id", required = true) String id, HttpServletRequest request, Model model) {
        String[] arr = id.split(",");
        int i = 0;
        for (String item : arr) {
            if (item == null || item.length() < 1) {
                continue;
            }
            int itemInt = Integer.parseInt(item);
            YyRoomInfo t = service.find(itemInt);
            if (t != null) {
                t.setStatus(0);
                service.save(t);
                i++;
            }
        }
        return ResultUtil.success(null, "" + i + "条数据被关闭", "/library/" + table + "/list.html");
    }


    /**
     * 开启
     *
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("openItems")
    @ResponseBody
    public Result <YyRoomInfo> openItems(@RequestParam(value = "id", required = true) String id, HttpServletRequest request, Model model) {
        String[] arr = id.split(",");
        int i = 0;
        for (String item : arr) {
            if (item == null || item.length() < 1) {
                continue;
            }
            int itemInt = Integer.parseInt(item);
            YyRoomInfo t = service.find(itemInt);
            if (t != null) {
                t.setStatus(1);
                service.save(t);
                i++;
            }
        }
        return ResultUtil.success(null, "" + i + "条数据被开启", "/library/" + table + "/list.html");
    }

    @RequestMapping("Verify_name")
    @ResponseBody
    public Object VerifyName(HttpServletRequest request, String roomname, Long floorId) {
        YyRoomInfoExample example = new YyRoomInfoExample();
        example.createCriteria().andFlooridEqualTo(floorId).andRoomnameEqualTo(roomname).andStatusEqualTo(1);

        return service.countByExample(example);
    }


//   ============考研座位预约房间配置============================================================================================================


    /**
     * 考研座位房间配置
     *
     * @param p
     * @param request
     * @return
     */
    @RequestMapping("listExamRoom")
    public String listExamRoom(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        YyRoomInfoExample example = new YyRoomInfoExample();
        example.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId()).andTypeNotEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        List <YyRoomInfo> yyRoomInfoList = service.selectByExample(p, 20, example);
        request.setAttribute("list", fz(yyRoomInfoList, p));
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display("examRoomConfigList");
    }

    //获取考研座位预约（房间）
    @ModelAttribute("getRoomTypeOfExam")
    public List <EnumUtil.ROOM_TYPE> getRoomTypeOfExam() {
        List <EnumUtil.ROOM_TYPE> list = new ArrayList();
        list.add(EnumUtil.ROOM_TYPE.exam_seat);
        list.add(EnumUtil.ROOM_TYPE.collection);
        return list;
    }

    /**
     * 添加房间
     *
     * @param request
     * @return
     */
    @RequestMapping("addExamRoom")
    public String addExamRoom(HttpServletRequest request) {
        YyBuildingInfoExample example = new YyBuildingInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId());
        YyTimeSettingExample example1 = new YyTimeSettingExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);
        request.setAttribute("yyTimeSetingList", yyTimeSettingService.selectByExample(example1));
        request.setAttribute("yybuildinginfolist", yyBuildingInfoService.selectAll(example));

        return display("examRoomAdd");
    }

    /**
     * 添加房间操作
     *
     * @param request
     * @param room
     * @return
     */
    @RequestMapping("addDoExamRoom")
    @ResponseBody
    public Object addDoExamRoom(HttpServletRequest request, YyRoomInfo room) {

        room.setLibraryid(getLibrary(request).getId());
        room.setLibraryname(getLibrary(request).getTitle());
        room.setBuildingname(yyBuildingInfoService.find(room.getBulidingid()).getBuildingname());
        room.setCreatedatetime(new Date());
        room.setStatus(1);
        room.setFloorname(yyFloorInfoService.find(room.getFloorid()).getFloorname());
        room.setPicurl(UploadUtil.uploadw(request, "picUrl", "picUrl", null));
        room = service.add(room);
        if (room != null) {
            logOperateService.operatingData(getLibrary(request), "yyroominfo", getLibAdmin(request), 1l, 18l);

            return ResultUtil.success(null, "操作成功！", "/library/" + table + "/listExamRoom.html");
        } else {
            return ResultUtil.error("操作失败，请重新操作！", "/library/" + table + "/listExamRoom.html");
        }

    }

    /**
     * 考研座位编辑
     *
     * @param request
     * @return
     */
    @RequestMapping("editExamRoom")
    public Object editExamRoom(HttpServletRequest request) {
        YyRoomInfo yyRoomInfo = service.find(Long.parseLong(request.getParameter("id")));
        YyFloorInfoExample example = new YyFloorInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andBulidingidEqualTo(yyRoomInfo.getBulidingid());
        YyTimeSettingExample example1 = new YyTimeSettingExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);

        request.setAttribute("yyTimeSetingList", yyTimeSettingService.selectByExample(example1));
        request.setAttribute("yyflootinfolist", yyFloorInfoService.selectByExample(example));
        request.setAttribute("data", yyRoomInfo);
        return display("examRoomEdit");
    }

    /**
     * 编辑操作
     *
     * @param request
     * @param room
     * @return
     */
    @RequestMapping("editDoExamRoom")
    @ResponseBody
    public Object editDoExamRoom(HttpServletRequest request, YyRoomInfo room) {
        room.setUpdatedatetime(new Date());
        room.setBuildingname(yyBuildingInfoService.find(room.getBulidingid()).getBuildingname());
        room.setFloorname(yyFloorInfoService.find(room.getFloorid()).getFloorname());
        room.setPicurl(UploadUtil.uploadw(request, "picUrl", "picUrl", null));

        logOperateService.operatingData(getLibrary(request), "yyroominfo", getLibAdmin(request), 2l, 18l);
        room = service.save(room);
        if (room != null) {
            return ResultUtil.success(null, "操作成功", "/library/" + table + "/listExamRoom.html");

        } else {
            return ResultUtil.error("操作失败,请重新操作", "/library/" + table + "/listExamRoom.html");

        }
    }

    /**
     * 验证房间是否正在使用
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("verifyRoom")
    @ResponseBody
    public Object verifyRoom(HttpServletRequest request, Long id) {
        List <Long> ls = new ArrayList <>();
        ls.add(1l);
        ls.add(7l);

        YySeatExamAppointmentExample example = new YySeatExamAppointmentExample();
        example.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId()).andAppointmentstatusIn(ls).andRoomidEqualTo(id);
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List <YySeatExamAppointment> list = yySeatExamAppointmentService.selectByExample(example);
        return ResultUtil.success(list.size());

    }

    /**
     * 删除考研配置房间
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("delExamRoom")
    @ResponseBody
    public Object delExamRoom(HttpServletRequest request, Long id) {
        YyRoomInfo roomInfo = service.find(id);
        //查看此房间是否有人正在使用
        roomInfo.setStatus(0);
        roomInfo.setUpdatedatetime(new Date());
        roomInfo = service.save(roomInfo);
        if (roomInfo != null) {
            List <Long> ls = new ArrayList <>();
            ls.add(1l);
            ls.add(7l);
            YySeatExamAppointmentExample example = new YySeatExamAppointmentExample();
            example.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId()).andAppointmentstatusIn(ls).andRoomidEqualTo(id);
            List <YySeatExamAppointment> list = yySeatExamAppointmentService.selectByExample(example);
//            删除判断如果房间有正在使用的座位，则提示用户
            if (!list.isEmpty()) {
                String msg = "温馨提示：" + roomInfo.getRoomname() + "房间不在继续使用，您的预约已被终止";
                for (YySeatExamAppointment bean : list) {
                    JPushUtil.pushMassageToLibraryOne(getLibrary(request).getId().toString(), bean.getUserid().toString(), "座位预约提醒", msg, null);
                    Message message = new Message();
                    message.setClassid(5L);
                    message.setContent(msg);
                    message.setUserid(bean.getUserid());
                    message.setLibraryid(bean.getLibraryid());
                    message.setCreatetime(new Date());
                    message.setStatus(1);
                    messageService.add(message);
                    //此处将所有的符合条件的座位->馆员释放
                    bean.setAppointmentstatus(8l);
                    bean.setReturntime(new Date());
                    yySeatExamAppointmentService.save(bean);

                }

            }
            return ResultUtil.success(null, "删除成功", "/library/" + table + "/listExamRoom.html");

        } else {
            return ResultUtil.error("操作失败,请重新操作", "/library/" + table + "/listExamRoom.html");

        }

    }


}