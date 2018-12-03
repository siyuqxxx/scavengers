package com.reading.controller.library;

import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.utils.EnumUtil;
import com.reading.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by Administrator on 2016/11/29.
 */
@Controller
@RequestMapping("library/yysetappointment")
public class LibraryYySeatAppointmentController extends BaseLibraryController {
    @Resource
    private YyAppointmentStatusService yyAppointmentStatusService;

    @Resource
    private YySeatAppointmentService service;

    @Resource
    private YySeatInfoService yySeatInfoService;

    @Resource
    private YyRoomInfoService yyRoomInfoService;

    @Resource
    private YySeatAppointmentService yySeatAppointmentService;

    @Resource
    private LibraryService libraryService;

    @Resource
    private YyFloorInfoService yyFloorInfoService ;

    @Resource
    private ZtVisitorInfoService ztVisitorInfoService;


    String table = "yysetappointment";

    @RequestMapping("list")
    public String list(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current = simpleDateFormat.format(date);

        YySeatAppointmentExample example = new YySeatAppointmentExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId());
        example.setOrderByClause("appointmentday desc");

        List<YySeatAppointment> yySeatAppointmentList = service.selectByExample(p, 20, example);
        boolean flag = isOpenFunctionByInfoId(getLibrary(request).getId(),5L);//参数表中的id=5代表座位预约记录和违约记录列表是否显示读者姓名
        int i = (p - 1) * 20 + 1;
        if (yySeatAppointmentList.size() > 0) {
            for (YySeatAppointment yySeatAppointment : yySeatAppointmentList) {

                YySeatInfo info = yySeatInfoService.find(yySeatAppointment.getSeatid());
                if(flag){
                    UserLibrary userLibrary = getUserLibrary(yySeatAppointment.getLibraryid(),yySeatAppointment.getCardnumber(),yySeatAppointment.getUserid());
                    if(userLibrary != null){
                        yySeatAppointment.setUserName(userLibrary.getCardusername());
                    }else {
                        String temp = "" ;
                        if(String.valueOf(yySeatAppointment.getLibraryid()).length() < 10){
                            for(int t = 0 ; t < 10 - String.valueOf(yySeatAppointment.getLibraryid()).length();t++){
                                temp += "0" ;
                            }
                        }
                        String tempLibraryId = temp + yySeatAppointment.getLibraryid() ;
                        String keyId = tempLibraryId + yySeatAppointment.getCardnumber() ;
                        ZtVisitorInfo ztVisitorInfo = ztVisitorInfoService.find(keyId) ;
                        if(ztVisitorInfo != null){
                            yySeatAppointment.setUserName(ztVisitorInfo.getUserName());
                        }
                    }
                }
                yySeatAppointment.setYySeatInfo(info);
                yySeatAppointment.setNumber(i);
                yySeatAppointment.setYyAppointmentStatus(yyAppointmentStatusService.find(yySeatAppointment.getAppointmentstatus()));
                i++;
            }
        }

        request.setAttribute("flag", flag);
        request.setAttribute("current", current);
        request.setAttribute("firsttime", current);
        request.setAttribute("status", -1);
        request.setAttribute("list", yySeatAppointmentList);
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);

        return display(table + "_list");
    }

//    @ModelAttribute("YyAppointmentStatusList")
//    public List<YyAppointmentStatus> getYyAppointmentStatus() {
//
//        return yyAppointmentStatusService.select();
//    }


    /**
     * 根据libraryId获得所有房间
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("getRoomLists")
    @ResponseBody
    public Object getRoomList(HttpServletRequest request) {
        YyRoomInfoExample example = new YyRoomInfoExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        List<YyRoomInfo> yyRoomInfoList = yyRoomInfoService.selectByExample(example);

        return ResultUtil.success(yyRoomInfoList,"请求成功！");
    }

    /**
     * 座位预约记录-->根据读者账号/借书证号查询
     * @param p
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("search")
    public String search(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) throws ParseException {
        p = p != null && p > 1 ? p : 1;

        String firsttime = request.getParameter("firsttime");
        String current = request.getParameter("current");
        Long status = Long.parseLong(request.getParameter("status"));

        Long roomId = Long.parseLong(request.getParameter("roomId"));
        String namew = request.getParameter("namew").trim();

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date f = format.parse(firsttime);
        Date c = format.parse(current);

        YySeatAppointmentExample example = new YySeatAppointmentExample();

        YySeatAppointmentExample.Criteria cri = example.createCriteria()
                .andLibraryidEqualTo(getLibrary(request).getId()).
                andAppointmentdayBetween(f, c);

        if(status != -1){
            cri.andAppointmentstatusEqualTo(status);
        }

        if(roomId != -1){
            cri.andRoomidEqualTo(roomId);
        }

        if(!Objects.equals("",namew)){
            cri.andCardnumberEqualTo(namew);
        }

        List<YySeatAppointment> yySeatAppointmentList = service.selectByExample(example);

        int i = (p - 1) * 20 + 1;
        //参数表中的id=5代表座位预约记录和违约记录列表是否显示读者姓名
        boolean flag = isOpenFunctionByInfoId(getLibrary(request).getId(),5L);
        for (YySeatAppointment yySeatAppointment : yySeatAppointmentList) {

            YySeatInfo info = yySeatInfoService.find(yySeatAppointment.getSeatid());
            if(flag){
                UserLibrary userLibrary = getUserLibrary(yySeatAppointment.getLibraryid(),yySeatAppointment.getCardnumber(),yySeatAppointment.getUserid());
                if(userLibrary != null){
                    yySeatAppointment.setUserName(userLibrary.getCardusername());
                }
            }
            yySeatAppointment.setNumber(i);
            yySeatAppointment.setYySeatInfo(info);
            yySeatAppointment.setYyAppointmentStatus(yyAppointmentStatusService.find(yySeatAppointment.getAppointmentstatus()));
            i++;

        }
        request.setAttribute("flag", flag);
        request.setAttribute("current", current);
        request.setAttribute("firsttime", firsttime);
        request.setAttribute("status", status);
        request.setAttribute("namew", namew);
        request.setAttribute("list", yySeatAppointmentList);
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", null);
        request.setAttribute("p", p);

        return display(table + "_list");
    }


    /**
     * 获取座位预约统计数据(今天)
     * @param p
     * @param request
     * @return
     */
    @RequestMapping("infolist")
    public String infolist(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, -1);
        String current = simpleDateFormat.format(date);
        String firsttime = simpleDateFormat.format(calendar.getTime());
        //获得隐藏的楼层的id集合
        YyFloorInfoExample example1 = new YyFloorInfoExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(0);
        List<YyFloorInfo> yyFloorInfos = yyFloorInfoService.selectByExample(example1);
        List<Long> floorIds = new ArrayList<Long>();
        for(YyFloorInfo yyFloorInfo : yyFloorInfos){
            floorIds.add(yyFloorInfo.getKeyid());
        }
        YyRoomInfoExample example  = new YyRoomInfoExample();
        if(floorIds.size()>0){
            example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andFlooridNotIn(floorIds).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        }else{
            example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        }
        example.setOrderByClause("CreateDateTime desc");

        //图书馆未关闭楼层和房间的总座位数量
        int libraryallSeatCount =  0 ;

        //图书馆未关闭楼层和房间已预约的座位数量
        int libraryalreadySeatCount =  0 ;

        List<YyRoomInfo> yyRoomInfos = yyRoomInfoService.selectByExample(p, 20, example);
        int i = (p - 1) * 20 + 1;
        if (yyRoomInfos.size() > 0) {
            for (YyRoomInfo yyRoomInfo : yyRoomInfos) {
                //获得房间所有座位
                String sql = "SELECT COUNT(1) FROM `yy_seat_info` where LibraryId = "+yyRoomInfo.getLibraryid()
                        +" and BulidingId = "+yyRoomInfo.getBulidingid()+" and FloorId = "+yyRoomInfo.getFloorid()
                        +" and RoomId =  "+yyRoomInfo.getKeyid()+" and seat_status = 1";
                int  allSeatCount = yySeatInfoService.getSeatCountByBuildingId(sql);
                //当天已经预约的座位信息
                String sql1 =  "SELECT COUNT(1) FROM `yy_seat_appointment` where LibraryId = "+yyRoomInfo.getLibraryid()
                        +" and BuildingId = "+yyRoomInfo.getBulidingid()+" and FloorId = "+yyRoomInfo.getFloorid()
                        +" and RoomId =  "+yyRoomInfo.getKeyid()+" and  AppointmentDay = '"+current
                        +"' and AppointmentStatus BETWEEN -1 and 2";
                int alreadySeatCount = yySeatAppointmentService.getAppointmentSeatCount(sql1);
                libraryallSeatCount += allSeatCount ;
                libraryalreadySeatCount += alreadySeatCount ;
                yyRoomInfo.setAllSeatCount(allSeatCount);
                yyRoomInfo.setAlreadySeatCount(alreadySeatCount);
                yyRoomInfo.setNumber(i++);
            }
        }
        request.setAttribute("library", libraryService.find(getLibrary(request).getId()));
        request.setAttribute("libraryallSeatCount", libraryallSeatCount);
        request.setAttribute("libraryalreadySeatCount", libraryalreadySeatCount);
        request.setAttribute("surplusSeatCount", libraryallSeatCount-libraryalreadySeatCount);
        request.setAttribute("current", current);
        request.setAttribute("firsttime", firsttime);
        request.setAttribute("status", -1);
        request.setAttribute("list", yyRoomInfos);
        request.setAttribute("count", yyRoomInfoService.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display(table + "_infolist");
    }

    /**
     * 获取座位预约统计数据(明天)
     * @param p
     * @param request
     * @return
     */
    @RequestMapping("tominfolist")
    public String tominfolist(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_MONTH, 1);
        String current = simpleDateFormat.format(calendar.getTime());
        String firsttime = simpleDateFormat.format(calendar.getTime());
        //获得隐藏的楼层的id集合
        YyFloorInfoExample example1 = new YyFloorInfoExample();
        example1.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(0);
        List<YyFloorInfo> yyFloorInfos = yyFloorInfoService.selectByExample(example1);
        List<Long> floorIds = new ArrayList<Long>();
        for(YyFloorInfo yyFloorInfo : yyFloorInfos){
            floorIds.add(yyFloorInfo.getKeyid());
        }
        YyRoomInfoExample example  = new YyRoomInfoExample();
        if(floorIds.size()>0){
            example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andFlooridNotIn(floorIds).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        }else{
            example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
        }
        example.setOrderByClause("CreateDateTime desc");

        //图书馆未关闭楼层和房间的总座位数量
        int libraryallSeatCount =  0 ;

        //图书馆未关闭楼层和房间已预约的座位数量
        int libraryalreadySeatCount =  0 ;

        List<YyRoomInfo> yyRoomInfos = yyRoomInfoService.selectByExample(p, 20, example);
        int i = (p - 1) * 20 + 1;
        if (yyRoomInfos.size() > 0) {
            for (YyRoomInfo yyRoomInfo : yyRoomInfos) {
                //获得房间所有座位
                String sql = "SELECT COUNT(1) FROM `yy_seat_info` where LibraryId = "+yyRoomInfo.getLibraryid()
                        +" and BulidingId = "+yyRoomInfo.getBulidingid()+" and FloorId = "+yyRoomInfo.getFloorid()
                        +" and RoomId =  "+yyRoomInfo.getKeyid()+" and seat_status = 1";
                int  allSeatCount = yySeatInfoService.getSeatCountByBuildingId(sql);
                //当天已经预约的座位信息
                String sql1 =  "SELECT COUNT(1) FROM `yy_seat_appointment` where LibraryId = "+yyRoomInfo.getLibraryid()
                        +" and BuildingId = "+yyRoomInfo.getBulidingid()+" and FloorId = "+yyRoomInfo.getFloorid()
                        +" and RoomId =  "+yyRoomInfo.getKeyid()+" and  AppointmentDay = '"+current
                        +"' and AppointmentStatus =  1";
                int alreadySeatCount = yySeatAppointmentService.getAppointmentSeatCount(sql1);
                libraryallSeatCount += allSeatCount ;
                libraryalreadySeatCount += alreadySeatCount ;
                yyRoomInfo.setAllSeatCount(allSeatCount);
                yyRoomInfo.setAlreadySeatCount(alreadySeatCount);
                yyRoomInfo.setNumber(i++);
            }
        }
        request.setAttribute("library", libraryService.find(getLibrary(request).getId()));
        request.setAttribute("libraryallSeatCount", libraryallSeatCount);
        request.setAttribute("libraryalreadySeatCount", libraryalreadySeatCount);
        request.setAttribute("surplusSeatCount", libraryallSeatCount-libraryalreadySeatCount);
        request.setAttribute("current", current);
        request.setAttribute("firsttime", firsttime);
        request.setAttribute("status", -1);
        request.setAttribute("list", yyRoomInfos);
        request.setAttribute("count", yyRoomInfoService.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display(table + "_tominfolist");
    }


    /**
     * webview座位模板显示
     *
     * @param request
     * @param roomid         房间编号
     * @param appointmentDay 预约日期
     * @return
     */
    @RequestMapping("seatList")
    public String querySeatListByRoomId(HttpServletRequest request, Long libraryid, Long roomid, String appointmentDay) {
        Date day = new Date();
        try {
            day = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        YyTimeSetting timeSetting = timeSettingService.find(timeid);
        YySeatAppointmentExample appointmentExample = new YySeatAppointmentExample();
        appointmentExample.createCriteria().andLibraryidEqualTo(libraryid).andRoomidEqualTo(roomid).andAppointmentdayEqualTo(day).andAppointmentstatusLessThan(3l);
        List<YySeatAppointment> appointmentList = service.selectByExample(appointmentExample);
        YySeatInfoExample example = new YySeatInfoExample();
        example.createCriteria().andLibraryidEqualTo(libraryid).andRoomidEqualTo(roomid).andSeatStatusEqualTo(1);
        List<YySeatInfo> seatList = yySeatInfoService.selectByExample(example);
//        if (seatList.size() > 0){
//            request.setAttribute("seatList", seatList);
//            request.setAttribute("seatinfo", seatList.get(0));  // 获取其中一个座位信息，以便程序获取相同属性、传值
//        }
//        request.setAttribute("appointmentList", appointmentList);

        if (seatList.size() > 0) {
            YySeatInfo seatInfo = seatList.get(0);

            int seatcolumn = seatInfo.getSeatcolumn();
            int seatline = seatInfo.getSeatline();
            int seatListcount = seatList.size();  // 已配置座位数量(当前房间座位数量)
            int appointmentSeatpointcount = appointmentList.size();   // 已预约座位数量
            int inden = 0;          // 标识
            String direction = "N";    //座位默认方向
            String currentSeatid = "";         //当前座位ID
            String currentSeat = "";           //当前座位坐标
            String str = ",";
            if (seatline > 0 && seatcolumn > 0) {
                // 初始化座位
                String htm = "";
                for (int i = 0; i < seatcolumn; i++) {
                    htm += "<ul name=\"chair\" class=\"chair\">";
                    for (int j = 0; j < seatline; j++) {
                        String curr = j + "-" + i;
                        for (int k = 0; k < seatListcount; k++) {
                            currentSeatid = seatList.get(k).getKeyid().toString();   //当前座位ID
                            currentSeat = seatList.get(k).getSeatpoint(); //当前座位坐标
                            direction = seatList.get(k).getDirection(); //当前座位方向
                            // 代表当前位置有座位，有的话再判断座位状态
                            if (currentSeat.equals(curr)) {
                                inden = 1;
                                for (int h = 0; h < appointmentSeatpointcount; h++) {
                                    if (str.indexOf(","+ h +",") < 0) {
                                        String appointmentSeat = appointmentList.get(h).getSeatpoint();
                                        String appointmentStatus = appointmentList.get(h).getAppointmentstatus().toString();
                                        if (currentSeat.equals(appointmentSeat) && appointmentStatus.equals("1")) {
                                            // 已预约
                                            inden = 2;
                                            str = str +h + ',';
                                            break;
                                        }
//                                       else if (currentSeat.equals(appointmentSeat) && appointmentStatus.equals("2")) {
//                                            // 暂离
//                                            inden = 3;
//                                            str += (h + ',');
//                                            break;
//                                        }
                                        else {
                                            // 可预约
                                            inden = 1;

                                        }
                                    } else {
                                        continue;
                                    }
                                }

                            } else {
                                continue;
                            }
                            break;
                        }


                        // inden ： 0代表此处无座  1代表有座位但无预约  2代表有预约  3代表有预约但暂时离开
                        if (inden == 0) {
                            htm += "<li class=\"item\"><img style=\"width:30px;height:30px;visibility:hidden;\"></img></li>";
                        } else if (inden == 1) {
                            YySeatInfoExample example1 = new YySeatInfoExample();
                            example1.createCriteria().andLibraryidEqualTo(libraryid).andRoomidEqualTo(roomid).andSeatpointEqualTo(curr);
                            String number = yySeatInfoService.selectByExample(example1).get(0).getNumber();
                            if (direction.equals("N")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/unselect_N.png\" class=\"unselect\" onclick=\"getCurrentSeat('" + i + "','" + number + "','" + currentSeatid + "',this)\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("S")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/unselect_S.png\" class=\"unselect\" onclick=\"getCurrentSeat('" + i + "','" + number + "','" + currentSeatid + "',this)\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("W")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/unselect_W.png\" class=\"unselect\" onclick=\"getCurrentSeat('" + i + "','" + number + "','" + currentSeatid + "',this)\" width=\"30\" height=\"30\"/></li>";
                            } else {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/unselect_E.png\" class=\"unselect\" onclick=\"getCurrentSeat('" + i + "','" + number + "','" + currentSeatid + "',this)\" width=\"30\" height=\"30\"/></li>";
                            }
                            inden = 0;
                        } else if (inden == 2) {

                            if (direction.equals("N")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/onselect_N.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("S")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/onselect_S.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("W")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/onselect_W.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/onselect_E.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            }

                            inden = 0;
                        } else if (inden == 3) {

                            if (direction.equals("N")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/leaving_N.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("S")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/leaving_S.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else if (direction.equals("W")) {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/leaving_W.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            } else {
                                htm += "<li class=\"item\"><img src=\"/static/common/img/leaving_E.png\" class=\"onselect\" width=\"30\" height=\"30\"/></li>";
                            }

                            inden = 0;
                        }
                    }
                    htm += "</ul>";
                }

                request.setAttribute("htm", htm);
            }



        }else{
            request.setAttribute("htm", "<h1>抱歉，本房间未配置座位<h1>");
        }
        return "library/yyseatinfo_seatwebview";
    }

    /**
     * 根据预约记录主键，处理签到、释放座位设备异常
     * @param seatAppointmentId 记录Id
     * @return
     */
    @RequestMapping("handleSignDeviceException")
    @ResponseBody
    public Object handleSignDeviceException
                    (@RequestParam(value = "seatAppointmentId") Long seatAppointmentId) {
        YySeatAppointment t = service.find(seatAppointmentId);
        boolean flag = t.getAppointmentstatus() == -1L || t.getAppointmentstatus() == 1L || t.getAppointmentstatus() == 2L ;
        if(flag && t.getSigntime() != null){
            t.setIgnoreStatus("0");
            YySeatAppointment bean = service.save(t);
            return ResultUtil.success(bean,"处理成功！");
        }else{
            return ResultUtil.error("只有签到状态才允许处理异常！");
        }
    }

}
