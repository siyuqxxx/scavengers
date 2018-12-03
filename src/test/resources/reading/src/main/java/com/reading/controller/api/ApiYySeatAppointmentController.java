package com.reading.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reading.base.BaseApiController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.model.Result;
import com.reading.utils.*;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller -  webview座位列表显示控制器
 *
 * @author by huishaobo
 * @version 1.0
 * @datetime by 2016年12月13日13:05:44
 */
@Controller
@RequestMapping("api/YySeatAppointment")
public class ApiYySeatAppointmentController extends BaseApiController {

    @Resource
    private YySeatDefaultRecordService recordService;
    @Resource
    private YySeatInfoService seatInfoService;
    @Resource
    private YySeatAppointmentService service;
    @Resource
    private YyTimeSettingService timeSettingService;
    @Resource
    private YyTimeConfigService configService;
    @Resource
    private UserLibraryService userLibraryService;
    @Resource
    private LibraryService libraryService;
    @Resource
    private YyRoomInfoService roomInfoService;
    @Resource
    private YyBuildingInfoService buildingInfoService;
    @Resource
    private YyFloorInfoService floorInfoService;
    @Resource
    private YyIbeaconService ibeaconService;
    @Resource
    private YyIbeaconInfoService ibeaconInfoService;
    @Resource
    private YySeatAppointmentSignRecordService yySeatAppointmentSignRecordService;
    @Resource
    private YyTimeSettingDetailService yyTimeSettingDetailService;
    @Resource
    private LibraryFunctionSwitchStatusService libraryFunctionSwitchStatusService;
    @Resource
    private YySeatTimeConfigService yySeatTimeConfigService;
    @Resource
    private YySeatExamDefaultRecordService yySeatExamDefaultRecordService;
    @Resource
    private YySeatExamAppointmentService yySeatExamAppointmentService;
    @Resource
    private YySeatExamAppointmentSignRecordService yySeatExamAppointmentSignRecordService;
    @Resource
    private YySeatAppointmentRegisterService registerService;//预约座位注册表
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private YyIbeaconBatteryInfoService yyIbeaconBatteryInfoService;
    @Resource
    private YySeatExamWhiteService yySeatExamWhiteService;
    @Resource
    YySeatUseTimeByDayService yySeatUseTimeByDayService;
    @Resource
    LibUserRoleService libUserRoleService;
    @Resource
    private YySeatJointStripRecordService yySeatJointStripRecordService;
    @Resource
    private YySeatJointStripPhotoService yySeatJointStripPhotoService;
    @Resource
    private YySeatStepOutRecordService yySeatStepOutRecordService;
    @Resource
    private YySeatStepOutConfigService yySeatStepOutConfigService;
    @Resource
    private LibraryCollegeService libraryCollegeService;
    @Resource
    private MessageService messageService;




    /**
     * 添加座位预约信息(每个用户只可有一条预约记录)2.0
     *
     * @param reques
     * @param bean
     * @param userid
     * @param libraryid
     * @param appointmentDay
     * @param seatid
     * @return
     */
    @RequestMapping(value = "addes", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Result <YySeatAppointment> addSeatAppointmentes(HttpServletRequest reques, YySeatAppointment bean, Long userid,
                                                                        Long libraryid, String appointmentDay, Long seatid
    ) throws ParseException {
        YyTimeConfigExample configExample = new YyTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryid);
        YyTimeConfig yyTimeConfig = configService.selectByExample(configExample).stream().findFirst().orElse(null);
        if (yyTimeConfig == null) {
            return ResultUtil.error("联系管理员，进行相关配置！");
        }

        String cardNumber = getCarNumber(userid,libraryid);

        //判断学校有没有开启 教室只支持某些学院预约
        UserLibraryExample ulEx = new UserLibraryExample();
        ulEx.createCriteria().andLibraryIdEqualTo(libraryid).andCardNumberEqualTo(cardNumber).andUserIdEqualTo(userid).andStatusEqualTo(1);
        UserLibrary userLibrary = userLibraryService.selectByExample(ulEx).stream().findFirst().orElse(null);

        Long collegeId = userLibrary.getCollegeId();
        Library library = libraryService.find(libraryid);
        Boolean openCollege = Objects.equals(library.getOpenCollege(),"1");

        if(library != null && openCollege){
            if(collegeId == null || Objects.equals(collegeId,"")){
                return ResultUtil.error("未获取到您的学院信息，请联系管理员！");
            }
        }

        YySeatInfo seat = seatInfoService.find(seatid);
        YyRoomInfo roomInfo = roomInfoService.find(seat.getRoomid());

        //1是限制学院预约
        Boolean restricted = Objects.equals("1",roomInfo.getRestrictedAppointment());

        if(openCollege && restricted){
            String[] colleges = roomInfo.getCanAppointmentCollege().split(",");
            if(!Arrays.asList(colleges).contains(collegeId.toString())){
                LibraryCollege lc = libraryCollegeService.find(collegeId);
                return ResultUtil.error("该教室不允许【"+lc.getCollegeName()+"】预约！");
            }
        }

        //根据座位的获得对应的房间从而获得房间的配置时间
        YyTimeSetting timeSetting = timeSettingService.find(roomInfoService.find(seatInfoService.find(seatid).getRoomid()).getTimesettingid());

        //查询用户违规次数，如果在30天内超过三次，则不能预约
        YySeatDefaultRecordExample recordExample = new YySeatDefaultRecordExample();
        recordExample.createCriteria().andLibraryIdEqualTo(libraryid).andCardNumberEqualTo(cardNumber)
                .andCreateTimeBetween(new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000l), new Date()).andStatusEqualTo(1);
        if (!Objects.equals(yyTimeConfig.getTime(), 0)
                && recordService.countByExample(recordExample) >= yyTimeConfig.getTime()) {
            return ResultUtil.error("对不起，您违规已达上线，不能预约！");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        String tomorrow = simpleDateFormat.format(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));

        String appointmentStartTime = "";
        String appointmentEndTime = "";
        //开馆时间
        String startTime = "";
        //闭馆时间
        String endTime = "";
        //预约开始时间
        String sysStartTime = "";
        //预约结束时间
        String sysEndTime = "";
        //带秒的应到时间
        Date shouldBeTime = new Date();

        Date date = shouldBeTime;
        Date createTime = shouldBeTime;

        String time1 = new SimpleDateFormat("HH:mm:ss").format(shouldBeTime);
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointmentDay + " " + time1);

        Map <String, String> map = yyTimeSettingDetailService.getTimeByWeekDay(timeSetting.getKeyid(), d);
        startTime = map.get("startTime");
        endTime = map.get("endTime");

        //判断预约的是今天还是明天的座位，得到系统的开放时间
        if (today.equals(appointmentDay)) {
            //TODO  今天
            sysStartTime = yyTimeConfig.getSysTodStTime();
            sysEndTime = yyTimeConfig.getSysTodEnTime();

        } else if (tomorrow.equals(appointmentDay)) {
            //TODO 明天
            sysStartTime = yyTimeConfig.getSysTomStTime();
            sysEndTime = yyTimeConfig.getSysTomEnTime();

            String tomorrowDay = LocalDate.now().plus(1, ChronoUnit.DAYS).toString();
            shouldBeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    parse(tomorrowDay + " " + startTime + ":00");

        } else {
            //时间非法，不可预约
            return ResultUtil.error("抱歉，网络跑丢，请重新预约！");
        }
//拼成预约的开始时间以及系统的开放时间
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + startTime);
        Date sysStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + sysStartTime);
        Date sysEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + sysEndTime);

        //获得今天闭馆时间
        Date dateend = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + endTime);
        if (date.before(sysEndDate) && date.after(sysStartDate)) {
            if (today.equals(appointmentDay)) {
                if (new Date().after(dateend)) {
                    return ResultUtil.error("对不起，今天已过闭馆时间，请预约明天座位！");
                }
                //TODO  今天
                if (date.before(startDate)) {
                    appointmentStartTime = startTime;
                } else {
                    appointmentStartTime = new SimpleDateFormat("HH:mm").format(date);
                }
                appointmentEndTime = endTime;

            } else {
                //TODO 明天
                appointmentStartTime = startTime;
                appointmentEndTime = endTime;

            }
        } else {
            //时间非法，不可预约
            return ResultUtil.error("请在:" + sysStartTime + "-" + sysEndTime + "时间内预约！！");

        }

        if (today.equals(appointmentDay) || tomorrow.equals(appointmentDay)) {
            //从注册表中查找当前用户是否有预约记录
            YySeatAppointmentRegisterExample registerExample = new YySeatAppointmentRegisterExample();
            registerExample.createCriteria().andLibraryIdEqualTo(libraryid)
                    .andAppointmentStatusLessThan(3L).andTimeIdIsNull()
                    .andAppointmentDayEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay))
                    .andCardnumberEqualTo(cardNumber);

            int count = registerService.countByExample(registerExample);
            try{
                //判断图书馆和当前读者的考研座位是否开启
                boolean flag = isOpenFunctionById(libraryid,userid,30L,cardNumber) ;
                if(flag){
                    //由于某些学校既开通普通座位预约，又开通考研座位预约，所以还应该在yy_seat_exam_appointment表中查询是否有预约记录（AppointmentStatus是1和7的）
                    List <Long> okIds = new ArrayList <>();
                    okIds.add(1L);
                    okIds.add(7L);
                    YySeatExamAppointmentExample seatAppointmentExample = new YySeatExamAppointmentExample();
                    //预约成功 和 使用中
                    seatAppointmentExample.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryid)
                            .andAppointmentstatusIn(okIds).andCardnumberEqualTo(cardNumber);
                    List <YySeatExamAppointment> seatappointmentList = yySeatExamAppointmentService.selectByExample(seatAppointmentExample);
                    if (seatappointmentList.size() > 0) {
                        YySeatExamAppointment yySeatExamAppointment = seatappointmentList.get(0) ;
                        //判断当前时间是否在应到时间减去预留时间之后，如果在之后，则提示不可以重复预约座位。
                        Long layTime = new Double(Double.parseDouble(yySeatExamAppointment.getLayTime()) * 60 * 60 * 1000).longValue();
                        //可以签到的时间
                        Date tempTime = new Date(yySeatExamAppointment.getShouldBeTime().getTime() - layTime) ;
                        if(new Date().after(tempTime)){
                            return ResultUtil.error("抱歉，您已经预约了考研座位，无法重复预约！");
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.log(e.getMessage());
            }
            if (count == 0) {
                YySeatAppointmentRegisterExample registerExamplew = new YySeatAppointmentRegisterExample();

                registerExamplew.createCriteria().andSeatIdEqualTo(seatid).andAppointmentStatusLessThan(3L).andAppointmentDayEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay));

                if (registerService.countByExample(registerExamplew) > 0) {
                    return ResultUtil.error("抱歉，该座位已有预约，请刷新选座!");
                }
                try {
                    bean.setUserid(userid);
                    UserLibraryExample userLibrartExample = new UserLibraryExample();
                    userLibrartExample.createCriteria().andUserIdEqualTo(userid).andLibraryIdEqualTo(libraryid).andStatusEqualTo(1);
                    List <UserLibrary> userLibraryList = userLibraryService.selectByExample(userLibrartExample);
                    if (userLibraryList.size() > 0) {
                        bean.setCardnumber(userLibraryList.get(0).getCardNumber());
                    }
                    if (seat != null) {
                        bean.setNum(seat.getNumber());
                        bean.setBuildingid(seat.getBulidingid());
                        bean.setBuildName(buildingInfoService.find(seat.getBulidingid()).getBuildingname());
                        bean.setFloorid(seat.getFloorid());
                        bean.setFloorName(floorInfoService.find(seat.getFloorid()).getFloorname());
                        bean.setRoomid(seat.getRoomid());
                        bean.setRoomName(roomInfo.getRoomname());
                        bean.setSeatid(seatid);
                        bean.setSeatpoint(seat.getSeatpoint());
                        bean.setLibraryid(libraryid);
                    }
                    bean.setStatus(1);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    bean.setAppointmentday(sdf.parse(appointmentDay));
                    bean.setAppointmentEndTime(appointmentEndTime);
                    bean.setAppointmentStartTime(appointmentStartTime);

                    bean.setLibraryname(library.getTitle());
                    bean.setAppointmentstatus(1L);
                    bean.setCreateTime(createTime);
                    bean.setShouldBeTime(shouldBeTime);
                    bean.setDataSource("A");

                    YySeatAppointment appointment = service.add(bean);

                    if (appointment != null) {
                        //注册
                        registerService.addYySeatAppointmentRegister(appointment);
                        return ResultUtil.success(appointment, "操作成功");
                    } else {
                        return ResultUtil.error("系统错误，请稍后重试!");
                    }
                } catch (Exception ex) {
                    return ResultUtil.error("系统错误，请稍后重试!");
                }
            } else {
                return ResultUtil.error("抱歉，一天之内同一用户无法重复预约！");
            }
        } else {
            //时间非法，不可预约
            return ResultUtil.error("抱歉，当前时间不在该预约期间内！");
        }
    }

    /**
     * 获取当前用户座位预约历史记录(列表，多条)
     *
     * @param userid
     * @return
     */
    @RequestMapping("getUserAppointmentList")
    @ResponseBody
    public Object getCurrentUserAppointmentList(
            @RequestParam(value = "userid") Long userid,
            @RequestParam(value = "libraryId") Long libraryId,
            @RequestParam(value = "page", required = false) Integer page,
            Model model) {
        page = page != null && page > 1 ? page : 1;

        String cardNumber = getCarNumber(userid, libraryId);

        YySeatAppointmentExample seatAppointmentExample = new YySeatAppointmentExample();
        seatAppointmentExample.setLimitStart((page - 1) * 15);
        seatAppointmentExample.setLimitEnd(15);
        seatAppointmentExample.setOrderByClause("KeyId desc");
        seatAppointmentExample.createCriteria().andLibraryidEqualTo(libraryId)
                .andAppointmentstatusNotEqualTo(3L).andStatusEqualTo(1).andCardnumberEqualTo(cardNumber);
        List <YySeatAppointment> tempList = service.selectByExample(seatAppointmentExample);
        if (tempList.size() > 0) {
            for (YySeatAppointment seatAppointment : tempList) {
                if (seatAppointment.getBuildName() == null || seatAppointment.getFloorName() == null || seatAppointment.getRoomName() == null) {
                    YySeatInfo seatInfo = seatInfoService.find(seatAppointment.getSeatid());
                    if (seatInfo != null) {
                        seatAppointment.setAddress(seatInfo.getBuildingname() + "  " + seatInfo.getFloorname() + " " + seatInfo.getRoomname());
                    } else {
                        seatAppointment.setAddress("暂无信息");
                    }
                } else {
                    seatAppointment.setAddress(seatAppointment.getBuildName() + "  " + seatAppointment.getFloorName() + " " + seatAppointment.getRoomName());
                }
                seatAppointment.setSeatpoint(seatAppointment.getNum());
                seatAppointment.setTime(seatAppointment.getAppointmentStartTime() + "-" + seatAppointment.getAppointmentEndTime());
            }
        }
        return ResultUtil.success(tempList);
    }


    /**
     * 根据座位编号查询
     *
     * @param seatid
     * @return
     */
    @RequestMapping("getSiteBySeatId")
    @ResponseBody
    public Object getSiteBySeatId(Long seatid) {
        YySeatInfo seatInfo = seatInfoService.find(seatid);
        return ResultUtil.success(seatInfo);
    }

    //封装正在预约的座位

    public Map <String, Object> sd(YySeatAppointment seatAppointment) {
        Map <String, Object> map = new HashMap <String, Object>();
        YyBuildingInfo building = buildingInfoService.find(seatAppointment.getBuildingid());
        YyFloorInfo floor = floorInfoService.find(seatAppointment.getFloorid());
        YyRoomInfo room = roomInfoService.find(seatAppointment.getRoomid());
        YyIbeaconExample ibeaconExample = new YyIbeaconExample();
        ibeaconExample.createCriteria().andLibraryidEqualTo(seatAppointment.getLibraryid()).andRoomidEqualTo(room.getKeyid()).andStatusEqualTo(1);
        List <YyIbeacon> ibeaconList = ibeaconService.selectByExample(ibeaconExample);
        List <YyIbeaconInfo> ibeaconInfoList = new ArrayList <YyIbeaconInfo>();
        for (YyIbeacon yyIneacon : ibeaconList) {
            YyIbeaconInfo yyIbeaconInfo = ibeaconInfoService.find(yyIneacon.getIbeaconid());
            ibeaconInfoList.add(yyIbeaconInfo);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (format.format(seatAppointment.getAppointmentday()).equals(format.format(new Date()))) {
            //TODO  今天
            map.put("mark", "1");

        } else {
            //TODO  明天
            map.put("mark", "-1");
        }


        map.put("keyid", seatAppointment.getKeyid());
        map.put("buildName", building.getBuildingname());
        map.put("floorName", floor.getFloorname());
        map.put("roomName", room.getRoomname());
        map.put("roomId", room.getKeyid());
        map.put("starTime", seatAppointment.getAppointmentStartTime());
        map.put("endTime", seatAppointment.getAppointmentEndTime());
        map.put("nowTime", System.currentTimeMillis());
        map.put("appointmentDay", format.format(seatAppointment.getAppointmentday()));
        if (null != seatAppointment.getSigntime()) {
            map.put("signTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(seatAppointment.getSigntime()));
        } else {
            map.put("signTime", null);
        }
        map.put("seatId", seatAppointment.getSeatid());
        map.put("seatPoint", seatAppointment.getNum());
        map.put("ibeaconList", ibeaconInfoList);
        map.put("appointmentStatus", seatAppointment.getAppointmentstatus());
        map.put("shouldBeTime", seatAppointment.getShouldBeTime());
        try{
            //获取图书馆的暂离参数设置
            YySeatStepOutConfigExample configExample = new YySeatStepOutConfigExample();
            configExample.createCriteria().andLibraryIdEqualTo(seatAppointment.getLibraryid()).andStatusEqualTo(1) ;
            List<YySeatStepOutConfig> configs = yySeatStepOutConfigService.selectByExample(configExample) ;
            YySeatStepOutConfig stepOutConfig = configs.size() > 0 ? configs.get(0) : null ;
            int jointStripLayTime = 30 ;//贴条后座位预留默认值
            int stepOutLayTime = 30 ;//暂离后座位预留默认值
            if(stepOutConfig != null){
                jointStripLayTime = stepOutConfig.getJointStripLayTime() !=  null ? stepOutConfig.getJointStripLayTime() : jointStripLayTime ;
                stepOutLayTime = stepOutConfig.getJointStripLayTime() !=  null ? stepOutConfig.getStepOutLayTime() : stepOutLayTime ;
            }

            YySeatStepOutRoomConfig roomConfig =  getStepOutConfigValue(seatAppointment.getLibraryid(),seatAppointment.getRoomid()) ;
            //判断是否是申请暂离状态
            if(seatAppointment.getAppointmentstatus() ==2L){//暂离状态
                //暂离倒计时：
                Long stempOutTime = seatAppointment.getStepOutTime().getTime() + stepOutLayTime*60*1000;
                //判断当前时间是否在暂离时间段内,如果在，那么暂离倒计时算到免暂离时间段的结束时间
                String time_quantum = null ;
                if(roomConfig != null){
                    time_quantum = inStepOutTimeQuantum(roomConfig.getStepOutTimeSettingId(),stempOutTime) ;
                }
                if(time_quantum != null){
                    //获取时间段的结束时间作为暂离倒计时时间
                    String temp_times[] = time_quantum.split("-") ;
                    if(temp_times.length == 2){
                        String end_time = temp_times[1] ;
                        String strTime = format.format(new Date()) + " " + end_time + ":00" ;
                        Date tempEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strTime) ;
                        stempOutTime = tempEndTime.getTime() ;
                        map.put("stempOutTime",stempOutTime) ; //暂离倒计时
                    }
                }else {
                    map.put("stempOutTime",stempOutTime) ; //暂离倒计时
                }
            }
            //判断是否是贴条状态
            if(seatAppointment.getAppointmentstatus() ==-1L){//贴条状态
                //贴条倒计时
                Long jointStripTime = seatAppointment.getJointStripTime().getTime() + jointStripLayTime*60*1000;
                //判断当前时间是否在暂离时间段内,如果在，那么暂离倒计时算到免暂离时间段的结束时间
                String time_quantum = null ;
                if(roomConfig != null){
                    time_quantum = inStepOutTimeQuantum(roomConfig.getStepOutTimeSettingId(),jointStripTime) ;
                }
                if(time_quantum != null){
                    //获取时间段的结束时间作为暂离倒计时时间
                    String temp_times[] = time_quantum.split("-") ;
                    if(temp_times.length == 2){
                        String end_time = temp_times[1] ;
                        String strTime = format.format(new Date()) + " " + end_time + ":00" ;
                        Date tempEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strTime) ;
                        jointStripTime = tempEndTime.getTime() ;
                        map.put("jointStripTime",jointStripTime) ; //贴条倒计时
                    }
                }else {
                    map.put("jointStripTime",jointStripTime) ; //贴条倒计时
                }
            }
            if(roomConfig != null){
                map.put("openStepOut",roomConfig.getOpenStepOut()) ;////开启暂离
            }else {
                map.put("openStepOut",0) ;//未开启暂离
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.log(e.getMessage());
            LogUtil.log("api/YySeatAppointment 中的sd 方法报错了。。。");
        }
        return map;

    }

    /**
     * 获取当前用户预约信息（2.0）
     *
     * @param userid
     * @param libraryid
     * @return
     */
    @RequestMapping("getUserAppointmentInfoes")
    @ResponseBody
    public Object getCurrentUserAppointmentInfoes(Long userid, Long libraryid) {

        List <Long> listid = new ArrayList();
        listid.add(1L);
        listid.add(-1L);//被贴条
        listid.add(2L);//暂离
        listid.add(4L);
        listid.add(6L);

        String cardNumber = getCarNumber(userid, libraryid);

        // 获取当前系统时间，根据时间进行判断当前用户是否有预约记录
        YySeatAppointmentExample seatAppointmentExample = new YySeatAppointmentExample();
        seatAppointmentExample.createCriteria().andLibraryidEqualTo(libraryid)
                .andStatusEqualTo(1).andAppointmentdayGreaterThanOrEqualTo(new Date())
                .andTimeidIsNull().andAppointmentstatusIn(listid).andCardnumberEqualTo(cardNumber);

        List <YySeatAppointment> list = service.selectByExample(seatAppointmentExample);
        List <Map <String, Object>> listm = new ArrayList <Map <String, Object>>();

        try {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {

                    listm.add(sd(list.get(i)));
                }

                return ResultUtil.success(listm);
            } else {
                return ResultUtil.success(listm, "您还没有座位预约记录，快去预约一个吧!");
            }
        } catch (Exception e) {
            LogUtil.log("getCurrentUserAppointmentInfo方法异常" + e.toString());
            return ResultUtil.error("系统异常，请稍后再试!");
        }
    }


    /**
     * 一键抢座(每个用户只可有一条预约记录，若已有记录则不可自动抢座)2.0
     *
     * @param roomid         房间编号
     * @param buildingid     楼宇编号
     * @param floorid        楼层编号
     * @param libraryid      分馆编号
     * @param userid         用户编号
     * @param appointmentDay 预约日期
     * @return 是否预约成功信息
     */
    @RequestMapping("autoAppointmentes")
    @ResponseBody
    public synchronized Result <YySeatAppointment> autoSeatAppointmentes(Long libraryid, Long buildingid, Long floorid, Long roomid,
                                                                         Long userid, String appointmentDay) throws ParseException {

        YyTimeConfigExample configExample = new YyTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryid);
        YyTimeConfig yyTimeConfig = configService.selectByExample(configExample).stream().findFirst().orElse(null);
        if (yyTimeConfig == null) {
            return ResultUtil.error("联系管理员，进行相关配置！");
        }

        String cardNumber = getCarNumber(userid,libraryid);

        //判断学校有没有开启 教室只支持某些学院预约
        UserLibraryExample ulEx = new UserLibraryExample();
        ulEx.createCriteria().andLibraryIdEqualTo(libraryid).andCardNumberEqualTo(cardNumber).andUserIdEqualTo(userid).andStatusEqualTo(1);
        UserLibrary userLibrary = userLibraryService.selectByExample(ulEx).stream().findFirst().orElse(null);

        Long collegeId = userLibrary.getCollegeId();
        Library library = libraryService.find(libraryid);
        Boolean openCollege = Objects.equals(library.getOpenCollege(),"1");

        if(library != null && openCollege){
            if(collegeId == null || Objects.equals(collegeId,"")){
                return ResultUtil.error("未获取到您的学院信息，请联系管理员！");
            }
        }

        YyRoomInfo roomInfo = roomInfoService.find(roomid);
        //1是限制学院预约
        Boolean restricted = Objects.equals("1",roomInfo.getRestrictedAppointment());

        if(openCollege && restricted){
            String[] colleges = roomInfo.getCanAppointmentCollege().split(",");
            if(!Arrays.asList(colleges).contains(collegeId.toString())){
                LibraryCollege lc = libraryCollegeService.find(collegeId);
                return ResultUtil.error("该教室不允许【"+lc.getCollegeName()+"】预约！");
            }
        }

        YyTimeSetting timeSetting = timeSettingService.find(roomInfoService.find(roomid).getTimesettingid());

        //查询此用户违规次数，如果30天内超过3次，则不允许预约
        YySeatDefaultRecordExample recordExample = new YySeatDefaultRecordExample();
        recordExample.createCriteria().andLibraryIdEqualTo(libraryid).andCardNumberEqualTo(cardNumber)
                .andCreateTimeBetween(new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000L), new Date()).andStatusEqualTo(1);
        if (!Objects.equals(yyTimeConfig.getTime(), 0)
                && recordService.countByExample(recordExample) >= yyTimeConfig.getTime()) {
            return ResultUtil.error("对不起，您违规已达上线，不能抢座！");
        }

        //预约前查询当前用户是否已经有预约记录
        YySeatAppointmentRegisterExample registerExample = new YySeatAppointmentRegisterExample();
        registerExample.createCriteria().andLibraryIdEqualTo(libraryid)
                .andAppointmentStatusBetween(-1L, 2L).andTimeIdIsNull()
                .andAppointmentDayEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay))
                .andCardnumberEqualTo(cardNumber);

        if (registerService.countByExample(registerExample) > 0) {
            return ResultUtil.error("抱歉，同一用户无法重复预约！");
        }
        try{
            //判断图书馆和当前读者的考研座位是否开启
            boolean flag = isOpenFunctionById(libraryid,userid,30L,cardNumber) ;
            if(flag){
                //由于某些学校既开通普通座位预约，又开通考研座位预约，所以还应该在yy_seat_exam_appointment表中查询是否有预约记录（AppointmentStatus是1和7的）
                List <Long> okIds = new ArrayList <>();
                okIds.add(1L);
                okIds.add(7L);
                YySeatExamAppointmentExample seatAppointmentExample = new YySeatExamAppointmentExample();
                //预约成功 和 使用中
                seatAppointmentExample.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryid)
                        .andAppointmentstatusIn(okIds).andCardnumberEqualTo(cardNumber);
                List <YySeatExamAppointment> seatappointmentList = yySeatExamAppointmentService.selectByExample(seatAppointmentExample);
                if (seatappointmentList.size() > 0) {
                    YySeatExamAppointment yySeatExamAppointment = seatappointmentList.get(0) ;
                    //判断当前时间是否在应到时间减去预留时间之后，如果在之后，则提示不可以重复预约座位。
                    Long layTime = new Double(Double.parseDouble(yySeatExamAppointment.getLayTime()) * 60 * 60 * 1000).longValue();
                    //可以签到的时间
                    Date tempTime = new Date(yySeatExamAppointment.getShouldBeTime().getTime() - layTime) ;
                    if(new Date().after(tempTime)){
                        return ResultUtil.error("抱歉，您已经预约了考研座位，无法重复预约！");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.log(e.getMessage());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        String tomorrow = simpleDateFormat.format(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));

        String appointmentStartTime = "";
        String appointmentEndTime = "";
        String startTime = "";      //开馆时间
        String endTime = "";        //闭馆时间
        String sysStartTime = "";   //预约开始时间
        String sysEndTime = "";     //预约结束时间

        Date shouldBeTime = new Date(); //带秒的应到时间

        Date date = shouldBeTime;
        Date createTime = shouldBeTime;

        String time1 = new SimpleDateFormat("HH:mm:ss").format(shouldBeTime);
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointmentDay + " " + time1);

        //获得当前使用的开闭馆时间
        Map <String, String> map = yyTimeSettingDetailService.getTimeByWeekDay(timeSetting.getKeyid(), d);

        startTime = map.get("startTime");
        endTime = map.get("endTime");

        //判断预约的是今天还是明天的座位，得到系统的开放时间
        if (today.equals(appointmentDay)) {
            //TODO  今天
            sysStartTime = yyTimeConfig.getSysTodStTime();
            sysEndTime = yyTimeConfig.getSysTodEnTime();

        } else if (tomorrow.equals(appointmentDay)) {
            //TODO 明天
            sysStartTime = yyTimeConfig.getSysTomStTime();
            sysEndTime = yyTimeConfig.getSysTomEnTime();

            String tomorrowDay = LocalDate.now().plus(1, ChronoUnit.DAYS).toString();
            shouldBeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    parse(tomorrowDay + " " + startTime + ":00");
        } else {
            //时间非法，不可预约
            return ResultUtil.error("抱歉，网络跑丢，请重新操作！");
        }
//拼成预约的开始时间以及系统的开放时间
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + startTime);
        Date sysStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + sysStartTime);
        Date sysEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + sysEndTime);

        //得到当天闭馆时间
        Date dateend = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(today + " " + endTime);

        if (date.before(sysEndDate) && date.after(sysStartDate)) {
            if (today.equals(appointmentDay)) {

                if (new Date().after(dateend)) {
                    return ResultUtil.error("对不起，今天已过闭馆时间，请预约明天座位！");
                }
                //TODO  今天
                if (date.before(startDate)) {
                    appointmentStartTime = startTime;
                } else {
                    appointmentStartTime = new SimpleDateFormat("HH:mm").format(date);
                }
                appointmentEndTime = endTime;

            } else {
                //TODO 明天
                appointmentStartTime = startTime;
                appointmentEndTime = endTime;

            }
        } else {
            //时间非法，不可预约
            return ResultUtil.error("请在:" + sysStartTime + "-" + sysEndTime + "时间内预约！！");
        }
        // 查询当前位置已被预约了的座位集(若已预约满提示已满座)
        YySeatAppointmentExample appointmentExample = new YySeatAppointmentExample();
        try {
            if (roomid != null) {
                appointmentExample.createCriteria().andRoomidEqualTo(roomid).andAppointmentdayEqualTo
                        (new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay)).andStatusEqualTo(1).
                        andAppointmentstatusBetween(-1L, 2L);
            } else {
                if (floorid != null) {
                    appointmentExample.createCriteria().andBuildingidEqualTo(buildingid).
                            andFlooridEqualTo(floorid).andAppointmentdayEqualTo(new SimpleDateFormat("yyyy-MM-dd").
                            parse(appointmentDay)).andStatusEqualTo(1).andAppointmentstatusBetween(-1l, 2l);
                } else {
                    if (buildingid != null) {
                        appointmentExample.createCriteria().andBuildingidEqualTo(buildingid).andAppointmentdayEqualTo
                                (new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDay)).andStatusEqualTo(1).
                                andAppointmentstatusBetween(-1L, 2L);
                    }
                }
            }
        } catch (ParseException e) {
            return ResultUtil.error("系统异常，请稍后重试!");
        }
        List <YySeatAppointment> appointmentList = service.selectByExample(appointmentExample);
        List <Long> seatList = new ArrayList <Long>();// 要被过滤的
        // 已被预约座位集
        for (YySeatAppointment s : appointmentList) {
            seatList.add(s.getSeatid());
        }
        if (seatList == null || seatList.size() < 1) {
            seatList.add(0L);
        }
        // 过滤已被预约座位
        YySeatInfoExample seatInfoExample = new YySeatInfoExample();
        if (roomid != null) {
            seatInfoExample.createCriteria().andSeatStatusEqualTo(1).andBulidingidEqualTo(buildingid).andFlooridEqualTo(floorid).andRoomidEqualTo(roomid).andKeyidNotIn(seatList);
        } else {
            if (floorid != null) {
                YyRoomInfoExample yyRoomInfoExample = new YyRoomInfoExample();
                yyRoomInfoExample.createCriteria().andFlooridEqualTo(floorid).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
                List <Long> roomIdList = new ArrayList <Long>();
                List <YyRoomInfo> yyRoomInfos = roomInfoService.selectByExample(yyRoomInfoExample);
                if (yyRoomInfos.size() == 0) {
                    return ResultUtil.error("该处暂无可预约座位,抢座失败！");
                }
                for (YyRoomInfo yyRoomInfo : yyRoomInfos) {
                    roomIdList.add(yyRoomInfo.getKeyid());
                }
                seatInfoExample.createCriteria().andSeatStatusEqualTo(1).andBulidingidEqualTo(buildingid).andFlooridEqualTo(floorid).andRoomidIn(roomIdList).andKeyidNotIn(seatList);
            } else {
                if (buildingid != null) {

                    YyFloorInfoExample yyFloorInfoExample = new YyFloorInfoExample();
                    yyFloorInfoExample.createCriteria().andBulidingidEqualTo(buildingid).andStatusEqualTo(1);
                    List <YyFloorInfo> yyFloorInfos = floorInfoService.selectByExample(yyFloorInfoExample);
                    if (yyFloorInfos.size() == 0) {
                        return ResultUtil.error("该处暂无可预约座位,抢座失败！");
                    }
                    List <Long> floorIdList = new ArrayList <Long>();
                    for (YyFloorInfo yyFloorInfo : yyFloorInfos) {
                        floorIdList.add(yyFloorInfo.getKeyid());
                    }

                    YyRoomInfoExample yyRoomInfoExample = new YyRoomInfoExample();
                    yyRoomInfoExample.createCriteria().andBulidingidEqualTo(buildingid).andFlooridIn(floorIdList).andStatusEqualTo(1).andTypeEqualTo(EnumUtil.ROOM_TYPE.ordinary_seat.getType_value());
                    List <YyRoomInfo> yyRoomInfos = roomInfoService.selectByExample(yyRoomInfoExample);
                    if (yyRoomInfos.size() == 0) {
                        return ResultUtil.error("该处暂无可预约座位,抢座失败！");
                    }
                    List <Long> roomIdList = new ArrayList <Long>();
                    for (YyRoomInfo yyRoomInfo : yyRoomInfos) {
                        roomIdList.add(yyRoomInfo.getKeyid());
                    }
                    seatInfoExample.createCriteria().andSeatStatusEqualTo(1).andBulidingidEqualTo(buildingid).andRoomidIn(roomIdList).andKeyidNotIn(seatList);
                }
            }
        }
        List <YySeatInfo> seatInfoList = seatInfoService.selectByExample(seatInfoExample);
        if (seatInfoList.size() != 0) {
            try {
                // 分配的座位
                YySeatInfo seat = seatInfoList.get(0);
                UserLibraryExample userLibrartExample = new UserLibraryExample();
                userLibrartExample.createCriteria().andUserIdEqualTo(userid).andLibraryIdEqualTo(libraryid).andStatusEqualTo(1);
                List <UserLibrary> userLibraryList = userLibraryService.selectByExample(userLibrartExample);

                YySeatAppointment appointment = new YySeatAppointment();
                appointment.setLibraryid(libraryid);
                appointment.setLibraryname(seat.getLibraryname());

                appointment.setBuildingid(seat.getBulidingid());
                appointment.setBuildName(buildingInfoService.find(seat.getBulidingid()).getBuildingname());

                appointment.setFloorid(seat.getFloorid());
                appointment.setFloorName(floorInfoService.find(seat.getFloorid()).getFloorname());

                appointment.setRoomid(seat.getRoomid());
                appointment.setRoomName(roomInfo.getRoomname());

                appointment.setNum(seat.getNumber());
                appointment.setUserid(userid);
                if (userLibraryList.size() > 0) {
                    appointment.setCardnumber(userLibraryList.get(0).getCardNumber());
                }

                appointment.setSeatid(seat.getKeyid());
                appointment.setSeatpoint(seat.getSeatpoint());
                appointment.setStatus(1);

                appointment.setAppointmentstatus(1L);
                appointment.setAppointmentStartTime(appointmentStartTime);
                appointment.setAppointmentEndTime(appointmentEndTime);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date appDate = sdf.parse(appointmentDay);

                appointment.setAppointmentday(appDate);
                appointment.setCreateTime(createTime);
                appointment.setShouldBeTime(shouldBeTime);
                appointment.setDataSource("A");

                YySeatAppointment sa = new YySeatAppointment();
                try {
                    sa = service.add(appointment);
                    //注册
                    registerService.addYySeatAppointmentRegister(sa);

                } catch (Exception e) {
                    LogUtil.log("autoSeatAppointment方法异常!");
                }
                if (sa != null) {
                    return ResultUtil.success(sa, "预约座位成功!");
                } else {
                    return ResultUtil.error("系统异常，请稍后重试!");
                }
            } catch (Exception ex) {
                return ResultUtil.error("系统异常，请稍后重试!");
            }
        } else {
            return ResultUtil.error("该处暂无可预约座位，抢座失败!");
        }
    }


    /**
     * 获取违约次数
     *
     * @param userId
     * @param libraryId
     * @return
     */
    @RequestMapping("getDefaultCount")
    @ResponseBody
    public Object getDefaultCount(Long userId, Long libraryId) {
        Map <String, Object> map = new HashMap <>();
        String cardNumber = getCarNumber(userId, libraryId);

        //获得图书馆违约上线次数
        YyTimeConfigExample configExample = new YyTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryId);
        YyTimeConfig yyTimeConfig = configService.selectByExample(configExample).stream().findFirst().orElse(null);
        if (yyTimeConfig == null) {
            return ResultUtil.error("联系管理员，进行相关配置！");
        }

        YySeatDefaultRecordExample recordExample = new YySeatDefaultRecordExample();
        recordExample.createCriteria().andLibraryIdEqualTo(libraryId)
                .andCreateTimeBetween(new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L), new Date())
                .andStatusEqualTo(1).andCardNumberEqualTo(cardNumber);

        map.put("currentCount", recordService.countByExample(recordExample));
        map.put("maxCount", yyTimeConfig.getTime());
        return ResultUtil.success(map);
    }

    /***
     * 确认入座(签到)
     *
     * @param userid
     * @param libraryid
     * @return
     */
    @RequestMapping("affirmSeat")
    @ResponseBody
    public Result <YySeatAppointment> affirmSeat(
            @RequestParam(value = "userid") Long userid,
            @RequestParam(value = "libraryid") Long libraryid,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "ibeaconBatteryJson", required = false) String ibeaconBatteryJson) {

        String cardNumber = getCarNumber(userid, libraryid);
        deviceId = null;

        YySeatAppointmentExample seatAppointmentExample = new YySeatAppointmentExample();
        seatAppointmentExample.createCriteria().andUseridEqualTo(userid).andLibraryidEqualTo(libraryid)
                .andAppointmentstatusBetween(-1L, 2L).andStatusEqualTo(1)
                .andAppointmentdayEqualTo(new Date()).andCardnumberEqualTo(cardNumber);
        List <YySeatAppointment> list = service.selectByExample(seatAppointmentExample);

        if (list.size() > 0) {
            try {
                YySeatAppointment seatAppointment = list.get(list.size() - 1);
                if (seatAppointment.getIgnoreStatus() == null
                        || Objects.equals(seatAppointment.getIgnoreStatus(), "1")) {

                    //如果device_id不存在，根据userId从login_log获得deviceId
                    if (deviceId == null || deviceId.isEmpty()) {
                        LoginLog log = this.getDeviceId(userid);
                        if(log != null){
                            deviceId = log.getDeviceid();
                        }
                    }

                    if (deviceId != null && !deviceId.isEmpty()) {
                        List <Long> keyIds = new ArrayList();
                        keyIds.add(new Long((long) seatAppointment.getKeyid()));

                        //连续签到
                        String signInDeviceId = seatAppointment.getSignInDeviceId();
                        if (signInDeviceId != null && !signInDeviceId.isEmpty()) {
                            if (!Objects.equals(deviceId, signInDeviceId)) {
                                return ResultUtil.error("请用首次签到手机签到!");
                            }
                        }

                        LocalDateTime localDateTime = LocalDateTime.now();
                        LocalDateTime minTime = localDateTime.with(LocalTime.MIN);
                        LocalDateTime maxTime = localDateTime.with(LocalTime.MAX);

                        ZoneId zone = ZoneId.systemDefault();
                        Instant minInstant = minTime.atZone(zone).toInstant();
                        Date minDate = Date.from(minInstant);

                        Instant maxInstant = maxTime.atZone(zone).toInstant();
                        Date maxDate = Date.from(maxInstant);

                        //根据设备Id查询此设备是否有未释放的座位
                        YySeatAppointmentExample ex1 = new YySeatAppointmentExample();
                        ex1.createCriteria().andSignInDeviceIdEqualTo(deviceId)
                                .andAppointmentstatusBetween(-1L, 2L).andStatusEqualTo(1)
                                .andSigntimeIsNotNull().andKeyidNotIn(keyIds).andCreateTimeGreaterThanOrEqualTo(minDate)
                                .andCreateTimeLessThanOrEqualTo(maxDate);
                        List <YySeatAppointment> list1 = service.selectByExample(ex1);
                        if (list1 != null && list1.size() > 0) {
                            return ResultUtil.error("当前设备有未释放的座位，请释放座位后再签到!");
                        }
                    }

                    seatAppointment.setSignInDeviceId(deviceId != null ? deviceId : "");
                    seatAppointment.setIgnoreStatus("1");
                }
                //统计时长
                if(seatAppointment.getSigntime()!=null){
                    computingTime( seatAppointment,new Date().getTime());

                }
                seatAppointment.setSigntime(new Date());
                if(seatAppointment.getAppointmentstatus() == -1L || seatAppointment.getAppointmentstatus() == 2){//如果是暂离或被贴条状态
                    seatAppointment.setAppointmentstatus(1L);
                }
                //由于在网络差的情况下出现签到时返回后释放座位，释放座位执行的快，签到执行的晚，导致座位没有释放掉
                YySeatAppointment newAppointment = service.find(seatAppointment.getKeyid()) ;
                if(newAppointment.getAppointmentstatus() == 5L){//已手动释放
                    return ResultUtil.error("座位已手动释放，请刷新！");
                }
                if(newAppointment.getAppointmentstatus() == 3L){//取消
                    return ResultUtil.error("您已取消预约，请刷新！");
                }
                YySeatAppointment sa = service.save(seatAppointment);

                YySeatAppointmentRegisterExample registerExample = new YySeatAppointmentRegisterExample();
                registerExample.createCriteria().andLibraryIdEqualTo(libraryid).andYySeatAppointmentIdEqualTo(sa.getKeyid());
                YySeatAppointmentRegister register = registerService.selectByExample(registerExample).stream().findFirst().orElse(null);

                if (register != null) {
                    //修改状态位
                    register.setSignTime(new Date());
                    if(register.getAppointmentStatus() == -1L || register.getAppointmentStatus() == 2){//如果是暂离或被贴条状态
                        register.setAppointmentStatus(1L);
                    }
                    registerService.save(register);
                }

                if (sa != null) {
                    YySeatAppointmentSignRecord record = new YySeatAppointmentSignRecord();
                    record.setSignTime(new Date());
                    record.setYySeatAppointmentId(sa.getKeyid());
                    yySeatAppointmentSignRecordService.add(record);

                    //存储ibeacon剩余电量
                    if (ibeaconBatteryJson != null && !ibeaconBatteryJson.isEmpty()) {
                        List <YyIbeaconBatteryInfo> ibeaconBatteryInfos = JSON.parseArray(ibeaconBatteryJson, YyIbeaconBatteryInfo.class);
                        yyIbeaconBatteryInfoService.addIbeaconBattery(ibeaconBatteryInfos, libraryid, new Date());
                    }

                    return ResultUtil.success(sa, "签到成功!");
                }
            } catch (Exception e) {
                LogUtil.log(e);
                LogUtil.log("affirmSeat方法异常!");
            }
        } else {
            return ResultUtil.error("抱歉,您当前没有预约信息!");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

//    /***
//     * 返回座位（暂已停用此功能）
//     *
//     * @param userid
//     * @param libraryid
//     * @return
//     */
//    @RequestMapping("backSeat")
//    @ResponseBody
//    public Result<YySeatAppointment> backSeat(Long userid, Long libraryid) {
//        YySeatAppointmentExample seatAppointmentExample = new YySeatAppointmentExample();
//        seatAppointmentExample.createCriteria().andUseridEqualTo(userid).andLibraryidEqualTo(libraryid).andAppointmentstatusBetween(1l, 2l).andStatusEqualTo(1);
//        List<YySeatAppointment> list = service.selectByExample(seatAppointmentExample);
//        if (list.size() > 0) {
//            YySeatAppointment seatAppointment = list.get(0);
//            seatAppointment.setReturntime(new Date());
//            seatAppointment.setAppointmentstatus(1L);   // 预约状态
//            try {
//                YySeatAppointment sa = service.save(seatAppointment);
//                if (sa != null) {
//                    return ResultUtil.success(sa, "您好，您已返回座位!");
//                }
//            } catch (Exception e) {
//                LogUtil.log("backSeat方法异常!");
//            }
//        } else {
//            return ResultUtil.error("抱歉,您当前没有预约信息!");
//        }
//        return ResultUtil.error("系统错误，请稍后重新再试!");
//    }


    //座位预约参数配置
    @RequestMapping("getlibraryapptime")
    @ResponseBody
    public Result <YyTimeSetting> getAppointmentTime(Long libraryId) {
        try {
            YyTimeSettingExample example = new YyTimeSettingExample();
            example.createCriteria().andLibraryidEqualTo(libraryId);
            return ResultUtil.success(timeSettingService.selectByExample(example).get(0));
        } catch (Exception e) {
            return ResultUtil.error("图书馆未配置座位相关信息，请联系管理员！");
        }

    }

    /**
     * 用户释放座位预约
     *
     * @return
     */
    @RequestMapping("releaseBySelfes")
    @ResponseBody
    public Result <YySeatAppointment> releaseSeatBySelfes(
            @RequestParam(value = "seatAppointmentId") Long seatAppointmentId,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "ibeaconBatteryJson", required = false) String ibeaconBatteryJson) {
        deviceId = null;
        YySeatAppointment t = service.find(seatAppointmentId);
        //由于app存在更新或卸载后出现设备号发生改变 ，导致手动释放座位异常，和 思宇商量，暂时去掉限制，避免学校再次投诉 2018-11-06
//        Boolean deviceSame = true;
//        //如果不忽略签到deviceId
//        if (Objects.equals(t.getIgnoreStatus(), "1")) {
//
//            //如果device_id不存在，根据userId从login_log获得deviceId
//            if (deviceId == null || deviceId.isEmpty()) {
//                LoginLog log = this.getDeviceId(t.getUserid());
//                if(log != null){
//                    deviceId = log.getDeviceid();
//                }
//            }
//        }
//        if (deviceId != null) {
//            if (!Objects.equals(t.getSignInDeviceId(), deviceId)) {
//                deviceSame = false;
//            }
//        }
//
//        if (deviceSame) {
//
//        } else {
//            return ResultUtil.error("签到和释放座位的手机异常，无法释放座位，请联系管理员!");
//        }
        try {
            t.setAppointmentstatus(5L);
            t.setStatus(1);
            //用户主动释放时间
            t.setReleasetime(new Date());
            YySeatAppointment sa = service.save(t);
            if (sa != null) {
                YySeatAppointmentRegisterExample registerExample = new YySeatAppointmentRegisterExample();
                registerExample.createCriteria().andYySeatAppointmentIdEqualTo(seatAppointmentId);
                //从这个注册表中将此对象移除
                registerService.delByExample(registerExample);
                //存储ibeacon剩余电量
                if (ibeaconBatteryJson != null && !ibeaconBatteryJson.isEmpty()) {
                    List <YyIbeaconBatteryInfo> ibeaconBatteryInfos = JSON.parseArray(ibeaconBatteryJson, YyIbeaconBatteryInfo.class);
                    yyIbeaconBatteryInfoService.addIbeaconBattery(ibeaconBatteryInfos, sa.getLibraryid(), new Date());
                }
                //统计时长
                computingTime( sa,sa.getReleasetime().getTime());
                return ResultUtil.success(sa, "您好，该座位已释放成功!");
            }
        } catch (Exception e) {
            LogUtil.log("releaseBySelf方法异常!");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    //统计当前用户的使用时长
    public void computingTime(YySeatAppointment appointment,Long nowTime) {
//        Date start_time = null;
//根据预约记录获取对应的房间规则，如果是间隔性签到，则在间隔性签到计算时长，否则将直接计算释放时间与签到时间的差值
//        YyRoomInfo roomInfo = roomInfoService.find(appointment.getRoomid());
//        int isOpenSign = timeSettingService.find(roomInfo.getTimesettingid()).getIsOpenSign();
//        if (isOpenSign == 1) {
//            YySeatAppointmentSignRecordExample signRecordExample = new YySeatAppointmentSignRecordExample();
//            signRecordExample.createCriteria().andYySeatAppointmentIdEqualTo(appointment.getKeyid());
//            List <YySeatAppointmentSignRecord> signRecords = yySeatAppointmentSignRecordService.selectByExample(signRecordExample);
//
//            start_time = signRecords.get(0).getSignTime();
//
//        } else {
//            start_time = appointment.getSigntime();
//        }
//获得座位预约的使用时间
        Integer use_time = Integer.parseInt( (nowTime- appointment.getSigntime().getTime()) + "");

//查看当前用户在当天是否已经有使用过，如果有则累计，否则直接添加新的数据
        YySeatUseTimeByDayExample example = new YySeatUseTimeByDayExample();
        example.createCriteria().andLibraryIdEqualTo(appointment.getLibraryid()).andCardNumberEqualTo(appointment.getCardnumber()).andAppointmentDayEqualTo(new Date());
        List <YySeatUseTimeByDay> useTimeByDays = yySeatUseTimeByDayService.selectByExample(example);
        if (!useTimeByDays.isEmpty()) {
            YySeatUseTimeByDay bean = useTimeByDays.get(0);
            bean.setUseSumTime(bean.getUseSumTime() + use_time);
            yySeatUseTimeByDayService.save(bean);
        } else {
            YySeatUseTimeByDay bean = new YySeatUseTimeByDay();
            bean.setUseSumTime(use_time);
            bean.setAppointmentDay(new Date());
            bean.setCardNumber(appointment.getCardnumber());
            bean.setCreateTime(new Date());
            bean.setLibraryId(appointment.getLibraryid());
            bean.setUserId(appointment.getUserid());
            yySeatUseTimeByDayService.add(bean);
        }

    }

    /**
     * 用户违约后，点击知道了，将在返回时间插入数据
     *
     * @return
     */
    @RequestMapping("remind_know")
    @ResponseBody
    public Result <YySeatAppointment> remind_know(Long seatAppointmentId) {

        YySeatAppointment seatAppointment = service.find(seatAppointmentId);
        seatAppointment.setTimeid(System.currentTimeMillis());
        try {
            YySeatAppointment sa = service.save(seatAppointment);
            if (sa != null) {
                return ResultUtil.success(null, "知道了");
            }
        } catch (Exception e) {
            LogUtil.log("知道了异常!");
        }

        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     * 取消座位预约
     *
     * @param seatAppointmentId
     * @return
     */
    @RequestMapping("cancelAppointmentes")
    @ResponseBody
    public Result <YySeatAppointment> cancelAppointment(Long seatAppointmentId) {
        YySeatAppointment seatAppointment = service.find(seatAppointmentId);

        if (Objects.equals(seatAppointment.getAppointmentstatus(), 4L)) {
            return ResultUtil.error("超时未签到被系统主动释放，请刷新！");
        }
        //leavetime：读者取消预约时间，appointmentstatus：预约状态
        seatAppointment.setLeavetime(new Date());
        seatAppointment.setAppointmentstatus(3L);
        try {
            YySeatAppointment sa = service.save(seatAppointment);

            if (sa != null) {
                YySeatAppointmentRegisterExample registerExample = new YySeatAppointmentRegisterExample();
                registerExample.createCriteria().andYySeatAppointmentIdEqualTo(seatAppointmentId);
                //从这个注册表中将此对象移除
                registerService.delByExample(registerExample);
                return ResultUtil.success(sa, "取消预约成功!");
            }
        } catch (Exception e) {
            LogUtil.log("cancelAppointment方法异常!");
        }

        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     * 贴条操作
     * @param request
     * @param photoCount 上传照片数量
     * @param userId 贴条人的userId
     * @param libraryId 贴条人的libraryId
     * @param cardNumber 贴条人的卡号
     * @param yySeatAppointmentId 被贴条的座位预约记录的id
     * @param bean
     * @return
     */
    @RequestMapping(value = "addJointStrip",method = RequestMethod.POST)
    @ResponseBody
    public synchronized Object addJointStrip(HttpServletRequest request, Integer photoCount,
                                     Long userId, Long libraryId, String cardNumber,
                                     Long yySeatAppointmentId,YySeatJointStripRecord bean){
        YySeatAppointment yySeatAppointment = service.find(yySeatAppointmentId) ;
        if(yySeatAppointment.getAppointmentstatus() == -1L){
            return ResultUtil.error("此座位已被贴条，请刷新！") ;
        }
        bean.setStatus(1);
        bean.setLibraryId(libraryId);
        bean.setCreateTime(new Date());
        bean.setInitiativeCardNumber(cardNumber);
        bean.setInitiativeUserId(userId);
        bean.setPassivityCardNumber(yySeatAppointment.getCardnumber());
        bean.setPassivityUserId(yySeatAppointment.getUserid());
        YySeatJointStripRecord temp = yySeatJointStripRecordService.add(bean) ;
        if(temp != null){
            Long repairRecordId = temp.getId();
            //判断是否上传照片
            for (int i = 1; i <= photoCount; i++) {
                YySeatJointStripPhoto jointStripPhoto = new YySeatJointStripPhoto();
                jointStripPhoto.setStatus(1);
                jointStripPhoto.setCreateTime(new Date());
                jointStripPhoto.setJointStripRecordId(repairRecordId);
                YySeatJointStripPhoto tempPhoto = yySeatJointStripPhotoService.add(jointStripPhoto);
                String[] imagePath = UploadUtil.uploadImg(request, "coverFile" + i, "jointStripPhoto", null);
                tempPhoto.setPhotoPath(imagePath[0]);
                tempPhoto.setSmallPhotoPath(imagePath[1]);
                yySeatJointStripPhotoService.save(tempPhoto);
            }
            //贴条成功后将被贴条的预约信息状态进行修改
            yySeatAppointment.setAppointmentstatus(-1L);//贴条状态
            //贴条时间
            yySeatAppointment.setJointStripTime(new Date());
            service.save(yySeatAppointment) ;
            //修改注册表中的对应的
            YySeatAppointmentRegister register =  getRegister(yySeatAppointment) ;
            if(register != null){
                register.setAppointmentStatus(-1L);//贴条状态
                register.setJointStripTime(new Date());// 贴条时间
                registerService.save(register) ;
            }
            try{
                //获取贴条预留时间
                YySeatStepOutConfigExample configExample = new YySeatStepOutConfigExample();
                configExample.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId) ;
                List<YySeatStepOutConfig> roomConfigs = yySeatStepOutConfigService.selectByExample(configExample) ;
                if(roomConfigs.size() > 0){
                    YySeatStepOutConfig yySeatStepOutConfig = roomConfigs.get(0) ;
                    String content = "您的座位已被贴条，请在 " + yySeatStepOutConfig.getJointStripLayTime() + " 分钟内再次签到，否则系统将自动释放您的座位。"  ;
                    //推送消息
                    JPushUtil.pushMassageToLibraryOne(String.valueOf(register.getLibraryId()), String.valueOf(register.getUserId()), "座位贴条", content, null);
                    Message message = new Message();
                    message.setClassid(2L);
                    message.setContent(content);
                    message.setUserid(register.getUserId());
                    message.setLibraryid(register.getLibraryId());
                    message.setCreatetime(new Date());
                    message.setStatus(1);
                    messageService.add(message);
                }
            }catch (Exception e){
                e.getStackTrace();
                LogUtil.log(e.getMessage());
                LogUtil.log("贴条推送消息失败");
            }
            return ResultUtil.success("贴条成功！") ;
        }else {
            return ResultUtil.error("贴条失败！") ;
        }
    }

    /**
     * 判断当前状态是否可以贴条
     * @param request
     * @param userId
     * @param libraryId
     * @param cardNumber
     * @param yySeatAppointmentId
     * @return
     */
    @RequestMapping(value = "whetherOrNotJointStrip",method = RequestMethod.POST)
    @ResponseBody
    public Object whetherOrNotJointStrip(HttpServletRequest request,Long userId, Long libraryId, String cardNumber,
                                Long yySeatAppointmentId){
        Map map = new HashMap() ;
        boolean flag  = false ;
        //判断贴条的座位预约是否存在
        YySeatAppointment yySeatAppointment = service.find(yySeatAppointmentId) ;
        if(yySeatAppointment == null){
            map.put("flag",flag) ;
            map.put("message","未找到要贴条的预约信息，请先刷新！") ;
            return ResultUtil.success(map) ;
        }
        if(yySeatAppointment.getSigntime() == null ){
            map.put("flag",flag) ;
            map.put("message","抱歉，未入座的座位不允许贴条！") ;
            return ResultUtil.success(map) ;
        }
        //判断房间是否开启贴条
        YySeatStepOutRoomConfig roomConfig = getStepOutConfigValue(libraryId,yySeatAppointment.getRoomid()) ;
        if(roomConfig == null || roomConfig.getOpenJointStrip() == null || roomConfig.getOpenJointStrip() != 1){
            map.put("flag",flag) ;
            map.put("message","抱歉，当前房间未开启贴条功能！") ;
            return ResultUtil.success(map) ;
        }
        //判断当前预约是否发起了暂离申请，如果发起了申请，则不允许贴条
        if(yySeatAppointment.getAppointmentstatus() == 2L){//暂离状态
            map.put("flag",flag) ;
            map.put("message","申请暂离的座位不允许贴条！") ;
            return ResultUtil.success(map) ;
        }else if(yySeatAppointment.getAppointmentstatus() == -1){
            map.put("flag",flag) ;
            map.put("message","请不要重复贴条！") ;
            return ResultUtil.success(map) ;
        }
        //判断当前读者一天最多可以贴条的数量，是否还可以继续贴条
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        YySeatJointStripRecordExample jointStripRecordExample = new YySeatJointStripRecordExample();
        jointStripRecordExample.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId)
                .andInitiativeUserIdEqualTo(userId).andInitiativeCardNumberEqualTo(cardNumber).andCreateTimeBetween(zero,new Date()) ;
        int jointStripCount = yySeatJointStripRecordService.countByExample(jointStripRecordExample) ;
        //获取暂离及贴条配置参数
        YySeatStepOutConfigExample seatStepOutConfigExample = new YySeatStepOutConfigExample();
        seatStepOutConfigExample.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId) ;
        List<YySeatStepOutConfig> seatStepOutConfigs = yySeatStepOutConfigService.selectByExample(seatStepOutConfigExample) ;
        YySeatStepOutConfig yySeatStepOutConfig = null ;
        if(seatStepOutConfigs.size() > 0){
            yySeatStepOutConfig = seatStepOutConfigs.get(0) ;
            if(yySeatStepOutConfig.getMaxJointStrip() != null && jointStripCount >= yySeatStepOutConfig.getMaxJointStrip() ){
                map.put("flag",flag) ;
                map.put("message","您的当天贴条数已达上限，感谢参与！") ;
                return ResultUtil.success(map) ;
            }
        }else {
            map.put("flag",flag) ;
            map.put("message","请联系管理员设置贴条参数！") ;
            return ResultUtil.success(map) ;
        }
        //判断是否在免暂离时间段，如果在则不允许贴条
        String result = inStepOutTimeQuantum(roomConfig.getStepOutTimeSettingId(),null) ;
        if(result != null){
            map.put("flag",flag) ;
            map.put("message","请不要在" + result + "时间段内贴条！") ;
            return ResultUtil.success(map) ;
        }
        //判断被贴条人最近的一次签到记录时间（在有效的时间内签到后，XX分钟后不可以再次贴条）
        int signLayTime = yySeatStepOutConfig.getJointStripIntervalTime() == null ? 0 : yySeatStepOutConfig.getJointStripIntervalTime() ;

        if(yySeatAppointment.getSigntime().getTime() + signLayTime*60*1000 > System.currentTimeMillis()){
            map.put("flag",flag) ;
            map.put("message","这个座位刚开始使用，过会再贴吧！") ;
            return ResultUtil.success(map) ;
        }
        //可以贴条
        flag = true ;
        YySeatInfo yySeatInfo = seatInfoService.find(yySeatAppointment.getSeatid()) ;
        map.put("flag",flag) ;
        String message = "是否对" + yySeatInfo.getNumber() + "号座进行贴条？" ;
        map.put("message",message) ;
        map.put("seatInfo",yySeatInfo) ;
        //获取当前房间的ibeacon
        YyIbeaconExample ibeaconExample = new YyIbeaconExample();
        ibeaconExample.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryId).andRoomidEqualTo(yySeatInfo.getRoomid()) ;
        List<YyIbeacon> ibeaconList = ibeaconService.selectByExample(ibeaconExample) ;
        List<Long> ibeaconIds = ibeaconList.stream().map(YyIbeacon :: getIbeaconid).collect(Collectors.toList());
        if(ibeaconIds.size() > 0 ){//房间有ibeacon
            //获取ibeacon的信息（序列号，电量等。。。）
            YyIbeaconInfoExample infoExample = new YyIbeaconInfoExample();
            infoExample.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryId).andKeyidIn(ibeaconIds) ;
            List<YyIbeaconInfo> ibeaconInfoList = ibeaconInfoService.selectByExample(infoExample) ;
            map.put("ibeaconInfoList",ibeaconInfoList) ;
        }else {
            map.put("ibeaconInfoList",new ArrayList<YyIbeaconInfo>()) ;
        }
        return ResultUtil.success(map)  ;
    }

    /**
     * 判断当前状态是否可以暂离
     * @param request
     * @param userId
     * @param libraryId
     * @param cardNumber
     * @param yySeatAppointmentId
     * @return
     */
    @RequestMapping(value = "whetherOrNotStepOut",method = RequestMethod.POST)
    @ResponseBody
    public Object whetherOrNotStepOut(HttpServletRequest request,Long userId, Long libraryId, String cardNumber,
                                         Long yySeatAppointmentId){
        Map map = new HashMap() ;
        boolean flag  = false ;
        //判断暂离的座位预约是否存在
        YySeatAppointment yySeatAppointment = service.find(yySeatAppointmentId) ;
        if(yySeatAppointment == null){
            map.put("flag",flag) ;
            map.put("message","未找到要暂离的预约信息，请先刷新！") ;
            return ResultUtil.success(map) ;
        }
        if(yySeatAppointment.getSigntime() == null ){
            map.put("flag",flag) ;
            map.put("message","抱歉，未入座的座位不允许暂离！") ;
            return ResultUtil.success(map) ;
        }
        //判断房间是否开启贴条
        YySeatStepOutRoomConfig roomConfig = getStepOutConfigValue(libraryId,yySeatAppointment.getRoomid()) ;
        if(roomConfig == null || roomConfig.getOpenStepOut() == null || roomConfig.getOpenStepOut() != 1){
            map.put("flag",flag) ;
            map.put("message","抱歉，当前房间未开启暂离功能！") ;
            return ResultUtil.success(map) ;
        }
        //判断当前预约是否发起了暂离申请，如果发起了申请，则不允许贴条
        if(yySeatAppointment.getAppointmentstatus() == 2L){//暂离状态
            map.put("flag",flag) ;
            map.put("message","请不要重复申请暂离！") ;
            return ResultUtil.success(map) ;
        }
        //判断是否在免暂离时间段，如果在则不需要暂离
        String result = inStepOutTimeQuantum(roomConfig.getStepOutTimeSettingId(),null) ;
        if(result != null){
            map.put("flag",flag) ;
            map.put("message","在" + result + "时间段内不会释放座位，无需申请暂离！") ;
            return ResultUtil.success(map) ;
        }
        //可以暂离
        flag = true ;
        map.put("flag",flag) ;
        String message = " 确认要申请暂离？" ;
        map.put("message",message) ;
        return ResultUtil.success(map)  ;
    }


    /***
     * 暂离座位（2.0）
     * @param userId
     * @param libraryId
     * @param cardNumber 卡号
     * @return
     */
    @RequestMapping("leaveSeat")
    @ResponseBody
    public Result<YySeatAppointment> leaveSeat(Long userId, Long libraryId,String cardNumber,YySeatStepOutRecord bean) {

        YySeatAppointmentExample seatAppointmentExample = new YySeatAppointmentExample();
        seatAppointmentExample.createCriteria().andUseridEqualTo(userId).andLibraryidEqualTo(libraryId).andStatusEqualTo(1).andAppointmentstatusBetween(-1l, 2l).andCardnumberEqualTo(cardNumber);
        List<YySeatAppointment> list = service.selectByExample(seatAppointmentExample);
        if (list.size() > 0) {
            YySeatAppointment seatAppointment = list.get(0);
            seatAppointment.setStepOutTime(new Date());//暂离时间
            seatAppointment.setAppointmentstatus(2L);   // 暂离状态
            try {
                YySeatAppointment sa = service.save(seatAppointment);
                //修改注册表中的数据
                YySeatAppointmentRegister register =  getRegister(sa) ;
                if(register != null){
                    register.setStepOutTime(new Date()) ;
                    register.setAppointmentStatus(2L);
                    registerService.save(register) ;
                }
                if (sa != null) {
                    //插入一条暂离记录
                    bean.setStatus(1);
                    bean.setCreateTime(new Date());
                    bean.setYySeatAppointmentId(sa.getKeyid());
                    yySeatStepOutRecordService.add(bean) ;
                    return ResultUtil.success(sa, "您好，您已暂时离开座位，请按时返回!");
                }
            } catch (Exception e) {
                LogUtil.log("leaveSeat方法异常!");
            }
        } else {
            return ResultUtil.error("抱歉,您当前没有预约信息!");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }



    /**--------------------------------以下代码是支持一次预约，在规定时间段内达到某个条件时，可以使用多天，即使用权顺延（案例：陕西理工）----------------------------------------*/

    /**
     * 判断当前学校是否开通座位预约一次，可以使用n（n>1）天的功能
     *
     * @param libraryId
     * @return
     */
    @RequestMapping("getSeatAppointmentType")
    @ResponseBody
    public Object getSeatAppointmentType(Long libraryId) {

        LibraryFunctionSwitchStatusExample libraryFunctionSwitchStatusExample = new LibraryFunctionSwitchStatusExample();
        libraryFunctionSwitchStatusExample.createCriteria().andLibraryIdEqualTo(libraryId).andStatusEqualTo(1).andFunctionInfoIdEqualTo(3L);//参数表libraryFunctionInfo中的id
        List <LibraryFunctionSwitchStatus> libraryFunctionSwitchStatuses = libraryFunctionSwitchStatusService.selectByExample(libraryFunctionSwitchStatusExample);
        boolean flag = false; //假设功能未开启
        if (libraryFunctionSwitchStatuses.size() > 0) {
            LibraryFunctionSwitchStatus libraryFunctionSwitchStatus = libraryFunctionSwitchStatuses.get(0);
            if (libraryFunctionSwitchStatus.getOpenStatus() == 1) {//功能已开启
                flag = true;
            }
        }
        return ResultUtil.success(flag, "获取成功！");
    }

    /**
     * 获取图书馆开始预约时间和开始使用时间
     *
     * @param libraryId
     * @return
     */
    @RequestMapping("getStartAppointmentTime")
    @ResponseBody
    public Object getStartAppointmentTime(Long libraryId, Long userId) {

        YySeatTimeConfigExample example = new YySeatTimeConfigExample();
        example.createCriteria().andLibraryIdEqualTo(libraryId);
        YySeatTimeConfig yySeatTimeConfig = yySeatTimeConfigService.selectByExample(example).stream().findFirst().orElse(null);
        if (yySeatTimeConfig == null) {
            return ResultUtil.error("座位预约时间配置获取失败，请联系管理员添加！");
        } else {
            Map map = new HashMap();
            yySeatTimeConfig.setStartAppintmentTime(yySeatTimeConfig.getStartAppintmentTime() + ":00");
            //滚动多少天
            Integer periodTime = 30;
            //获得图书馆违约上线次数
            // 修复（482 考研座位预约支持签到规则和违约规则分开控制）0917生产bug,将maxCount设置为10，为了兼容旧版本
            Integer maxCount = 10;
            if (yySeatTimeConfig.getIsHaveSignLimit() == 1 && yySeatTimeConfig.getIsHavePunishmentLimit() == 1) {
                //滚动多少天
                periodTime = yySeatTimeConfig.getBreakContractTimePeriod();

                maxCount = yySeatTimeConfig.getBreakContractCount();
            }
            String cardNumber = getCarNumber(userId, libraryId);
            //获取滚动时间周期
            Date startTime1 = new Date(new Date().getTime() - periodTime * 24 * 60 * 60 * 1000L);
            //从这一天的0点开始
            String tempTime = new SimpleDateFormat("yyyy-MM-dd").format(startTime1) + " 00:00:00";
            try {
                startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tempTime);
            } catch (ParseException e) {
                LogUtil.log("时间转换异常");
            }
            YySeatExamDefaultRecordExample recordExample = new YySeatExamDefaultRecordExample();
            recordExample.createCriteria().andLibraryIdEqualTo(libraryId)
                    .andCreateTimeBetween(startTime1, new Date())
                    .andStatusEqualTo(1).andCardNumberEqualTo(cardNumber);
            //获取当前用户的违约次数
            map.put("currentCount", yySeatExamDefaultRecordService.countByExample(recordExample));
            //获取违约上线的次数
            map.put("maxCount", maxCount);
            //判断是否已经预约座位座位
            boolean flag = false;//默认没有
            YySeatExamAppointmentExample yySeatExamAppointmentExample = new YySeatExamAppointmentExample();
            //预约成功
            yySeatExamAppointmentExample.or().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                    .andAppointmentstatusEqualTo(1L).andCardnumberEqualTo(cardNumber);
            //使用中
            yySeatExamAppointmentExample.or().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                    .andAppointmentstatusEqualTo(7L).andCardnumberEqualTo(cardNumber);
            List <YySeatExamAppointment> yySeatExamAppointments = yySeatExamAppointmentService.selectByExample(yySeatExamAppointmentExample);
            YySeatExamAppointmentSignRecord signRecord = null;
            //有预约
            if (yySeatExamAppointments.size() > 0) {
                //预约信息
                YySeatExamAppointment yySeatExamAppointment = yySeatExamAppointments.get(0);
                //判断预约是否错过首签时间，
                if (yySeatExamAppointment.getSigntime() == null && yySeatExamAppointment.getShouldBeTime().before(new Date())) {
                    //已过时，释放座位
                    yySeatExamAppointment.setAppointmentstatus(4L);
                    yySeatExamAppointment.setReturntime(new Date());
                    YySeatExamAppointment sa = yySeatExamAppointmentService.save(yySeatExamAppointment);
                    if (sa != null) {
                        //开启了违约限制
                        if (yySeatTimeConfig.getIsHaveSignLimit() == 1) {
                            //并记录一次违约
                            examDefaultRecord(userId, libraryId, cardNumber, yySeatExamAppointment.getId());
                        }
                    }
                } else {//未过时
                    flag = true;
                    map.put("yySeatExamAppointment", yySeatExamAppointment);
                    //获取房间ibacon
                    YyIbeaconExample ibeaconExample = new YyIbeaconExample();
                    ibeaconExample.createCriteria().andLibraryidEqualTo(libraryId).andRoomidEqualTo(yySeatExamAppointment.getRoomid()).andStatusEqualTo(1);
                    List <YyIbeacon> ibeaconList = ibeaconService.selectByExample(ibeaconExample);
                    List <YyIbeaconInfo> ibeaconInfoList = new ArrayList <YyIbeaconInfo>();
                    for (YyIbeacon yyIneacon : ibeaconList) {
                        YyIbeaconInfo yyIbeaconInfo = ibeaconInfoService.find(yyIneacon.getIbeaconid());
                        ibeaconInfoList.add(yyIbeaconInfo);
                    }
                    map.put("ibeaconInfoList", ibeaconInfoList);
                    //获取房间的开闭馆时间
                    YyRoomInfo yyRoomInfo = roomInfoService.find(yySeatExamAppointment.getRoomid());
                    //获取房间开闭馆时间
                    //Today
                    Map todayRoomOpenTime = yyTimeSettingDetailService.getTimeByWeekDay(yyRoomInfo.getTimesettingid(), new Date());
                    //Tomter
                    Map tommterRoomOpenTime = yyTimeSettingDetailService.getTimeByWeekDay(yyRoomInfo.getTimesettingid(), new Date(new Date().getTime() + 24 * 60 * 60 * 1000L));
                    map.put("todayRoomOpenTime", todayRoomOpenTime);
                    map.put("tommterRoomOpenTime", tommterRoomOpenTime);
                }
                //判断当前预约是否有未签离得签到记录
                if (yySeatTimeConfig.getIsHaveSignLeave() == 1) {//设置了签离
                    LocalDate today = LocalDate.now();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startTime = sdf.parse(String.valueOf(today));
                        Date endTime = new Date(startTime.getTime() + 24 * 60 * 60 * 1000L);
                        YySeatExamAppointmentSignRecordExample example1 = new YySeatExamAppointmentSignRecordExample();
                        example1.createCriteria().andLibraryIdEqualTo(libraryId).andCardNumberEqualTo(cardNumber).andYySeatAppointmentIdEqualTo(yySeatExamAppointment.getId()).andSignEndTimeIsNull().andSignStartTimeBetween(startTime, endTime);
                        List <YySeatExamAppointmentSignRecord> lists = yySeatExamAppointmentSignRecordService.selectByExample(example1);
                        if (lists.size() > 0) {
                            signRecord = lists.get(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        LogUtil.log("考研座位预约getStartAppointmentTime中日期转换异常");
                    }
                    map.put("isHaveSignLeave", true);
                } else {//为设置签离
                    map.put("isHaveSignLeave", false);
                }
            }
            //获取是否有释放的座位并且未点击我知道了
            List <Long> ids = new ArrayList <>();
            ids.add(4L);//未首次签到
            ids.add(8L);//馆员手动释放
            ids.add(9L);//签到不足
            YySeatExamAppointmentExample example1 = new YySeatExamAppointmentExample();
            example1.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                    .andAppointmentstatusIn(ids).andIsKnowNotEqualTo(1).andCardnumberEqualTo(cardNumber);//未点击我知道了
            List <YySeatExamAppointment> tempList = yySeatExamAppointmentService.selectByExample(example1);
            if (tempList.size() > 0) {
                map.put("notKnowSeatAppointment", tempList.get(0));
            } else {
                map.put("notKnowSeatAppointment", null);
            }
            //TODO如果设置了签到和签离，需要把当天未签离得对象返回给app
            map.put("YySeatExamAppointmentSignRecord", signRecord);
            map.put("isHaveSeatAppointment", flag);
            map.put("nowTime", new Date().getTime());
            map.put("yySeatTimeConfig", yySeatTimeConfig);
            return ResultUtil.success(map, "获取成功！");
        }
    }

    /**
     * 考研座位预约一键抢座(每个用户只可有一条预约记录，若已有记录则不可自动抢座)1.0
     *
     * @param roomId    房间编号
     * @param libraryId 图书馆编号
     * @param userId    用户编号
     * @return 是否预约成功信息
     */
    @RequestMapping("autoAppointmenteExamSeat")
    @ResponseBody
    public synchronized Result <YySeatExamAppointment> autoAppointmenteExamSeat(Long libraryId, Long userId, Long roomId, String cardNumber) {

        YySeatTimeConfigExample configExample = new YySeatTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryId);
        List <YySeatTimeConfig> yySeatTimeConfigs = yySeatTimeConfigService.selectByExample(configExample);
        if (yySeatTimeConfigs.size() == 0) {
            return ResultUtil.error("联系管理员，进行相关配置！");
        }
        YySeatTimeConfig yySeatTimeConfig = yySeatTimeConfigs.get(0);
        yySeatTimeConfig.setStartAppintmentTime(yySeatTimeConfig.getStartAppintmentTime() + ":00");
        //判断是否开始预约
        Date startAppointmentTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startAppointmentTime = sdf.parse(yySeatTimeConfig.getStartAppintmentTime());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("预约时间格式错误！");
        }
        if (startAppointmentTime.after(new Date())) {
            return ResultUtil.error("对不起，预约未开始，抢座失败！");
        }
        //设置了违约限制
        if (yySeatTimeConfig.getIsHaveSignLimit() == 1 && yySeatTimeConfig.getIsHavePunishmentLimit() == 1) {
            //查询此用户违约次数，如果滚动时间周期内超过最大违约次数，则不允许预约
            Integer maxCount = yySeatTimeConfig.getBreakContractCount();
            //滚动时间周期
            Integer periodCount = yySeatTimeConfig.getBreakContractTimePeriod();
            YySeatExamDefaultRecordExample yySeatExamDefaultRecordExample = new YySeatExamDefaultRecordExample();
            yySeatExamDefaultRecordExample.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId).andCardNumberEqualTo(cardNumber)
                    .andCreateTimeBetween(new Date(new Date().getTime() - periodCount * 24 * 60 * 60 * 1000L), new Date());
            List <YySeatExamDefaultRecord> yySeatExamDefaultRecords = yySeatExamDefaultRecordService.selectByExample(yySeatExamDefaultRecordExample);
            if (yySeatExamDefaultRecords.size() >= maxCount) {
                return ResultUtil.error("对不起，您违约已达上线，不能抢座！");
            }
        }
        //预约前查询当前用户是否已经有预约记录
        YySeatExamAppointmentExample seatAppointmentExample = new YySeatExamAppointmentExample();
        //预约成功
        seatAppointmentExample.or().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                .andAppointmentstatusEqualTo(1L).andCardnumberEqualTo(cardNumber);
        //使用中
        seatAppointmentExample.or().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                .andAppointmentstatusEqualTo(7L).andCardnumberEqualTo(cardNumber);
        List <YySeatExamAppointment> seatappointmentList = yySeatExamAppointmentService.selectByExample(seatAppointmentExample);
        if (seatappointmentList.size() > 0) {
            return ResultUtil.error("抱歉，同一用户无法重复预约！");
        }
        // 查询当前位置已被预约了的座位集(若已预约满提示已满座)
        List <Long> okIds = new ArrayList <>();
        okIds.add(1L);
        okIds.add(7L);
        YySeatExamAppointmentExample appointmentExample = new YySeatExamAppointmentExample();
        //预约成功和使用中
        appointmentExample.createCriteria().andRoomidEqualTo(roomId).andStatusEqualTo(1).andAppointmentstatusIn(okIds);
        List <YySeatExamAppointment> appointmentList = yySeatExamAppointmentService.selectByExample(appointmentExample);

        YySeatExamWhiteExample example = new YySeatExamWhiteExample();
        example.createCriteria().andLibraryIdEqualTo(libraryId).andRoomIdEqualTo(roomId).andUserStatusEqualTo("1");
        List <YySeatExamWhite> whiteList = yySeatExamWhiteService.selectByExample(example);
        List <Long> seatList = new ArrayList <Long>();// 要被过滤的
        // 已被预约座位集
        for (YySeatExamAppointment s : appointmentList) {
            seatList.add(s.getSeatid());
        }

        if (whiteList != null && whiteList.size() > 0) {
            for (YySeatExamWhite t : whiteList) {
                seatList.add(t.getSeatId());
            }
        }

        if (seatList == null || seatList.size() < 1) {
            seatList.add(0L);
        }
        // 过滤已被预约座位
        YySeatInfoExample seatInfoExample = new YySeatInfoExample();
        seatInfoExample.createCriteria().andRoomidEqualTo(roomId).andKeyidNotIn(seatList).andSeatStatusEqualTo(1);
        List <YySeatInfo> seatInfoList = seatInfoService.selectByExample(seatInfoExample);
        if (seatInfoList.size() != 0) {
            try {
                // 分配的座位
                YySeatInfo seat = seatInfoList.get(0);
                YySeatExamAppointment appointment = new YySeatExamAppointment();
                appointment.setLibraryid(libraryId);
                appointment.setLibraryname(seat.getLibraryname());
                appointment.setBuildingid(seat.getBulidingid());

                appointment.setFloorid(seat.getFloorid());
                appointment.setRoomid(seat.getRoomid());

                YyRoomInfo yyRoomInfo = roomInfoService.find(roomId);

                appointment.setBuildName(yyRoomInfo.getBuildingname());
                appointment.setFloorName(yyRoomInfo.getFloorname());
                appointment.setRoomName(yyRoomInfo.getRoomname());

                appointment.setNum(seat.getNumber());
                appointment.setUserid(userId);
                appointment.setSeatid(seat.getKeyid());

                appointment.setSeatpoint(seat.getSeatpoint());
                appointment.setStatus(1);
                appointment.setIsKnow(0);//未点击我知道了

                appointment.setAppointmentstatus(1L);
                appointment.setAppointmentday(new Date());
                //当前座位预留时间（因为这个在后台有可能修改，所以动态保存）
                appointment.setLayTime(yySeatTimeConfig.getLayTime());
                appointment.setCreateTime(new Date());
                appointment.setCardnumber(cardNumber);

                //开始使用时间
                Date startUseTime = sdf.parse(yySeatTimeConfig.getStartUseTime() + " 00:00:00");
                Long tempTime = new Date().getTime();
                //如果预约时间在开始使用时间之前
                if (new Date().before(startUseTime)) {
                    //shouldBeTime 为开始使用时间+预留时间
                    tempTime = startUseTime.getTime() + (long) (Double.parseDouble(appointment.getLayTime()) * 60 * 60 * 1000L);
                } else {
                    //否则为当前时间+预留时间
                    tempTime = new Date().getTime() + (long) (Double.parseDouble(appointment.getLayTime()) * 60 * 60 * 1000L);
                }
                appointment.setShouldBeTime(new Date(tempTime));
                YySeatExamAppointment sa = new YySeatExamAppointment();
                try {
                    sa = yySeatExamAppointmentService.add(appointment);
                } catch (Exception e) {
                    LogUtil.log("autoAppointmenteExamSeat方法异常!");
                }
                if (sa != null) {
                    return ResultUtil.success(sa, "预约座位成功!");
                } else {
                    return ResultUtil.error("系统异常，请稍后重试!");
                }
            } catch (Exception ex) {
                return ResultUtil.error("系统异常，请稍后重试!");
            }
        } else {
            return ResultUtil.error("该处暂无可预约座位，抢座失败!");
        }
    }

    /**
     * 考研座位选座预约(每个用户只可有一条预约记录)1.0
     *
     * @param request
     * @param bean
     * @param userId
     * @param libraryId
     * @param seatId     座位主键
     * @param cardNumber 卡号
     * @return
     */
    @RequestMapping(value = "appointmenteExamSeat", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Result <YySeatExamAppointment> appointmenteExamSeat(HttpServletRequest request, YySeatExamAppointment bean, Long userId,
                                                                            Long libraryId, Long seatId, String cardNumber) {
        YySeatTimeConfigExample configExample = new YySeatTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryId);
        List <YySeatTimeConfig> yySeatTimeConfigs = yySeatTimeConfigService.selectByExample(configExample);
        if (yySeatTimeConfigs.size() == 0) {
            return ResultUtil.error("联系管理员，进行相关配置！");
        }
        YySeatTimeConfig yySeatTimeConfig = yySeatTimeConfigs.get(0);
        yySeatTimeConfig.setStartAppintmentTime(yySeatTimeConfig.getStartAppintmentTime() + ":00");
        //判断是否开始预约
        Date startAppointmentTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startAppointmentTime = sdf.parse(yySeatTimeConfig.getStartAppintmentTime());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("预约时间格式错误！");
        }
        if (startAppointmentTime.after(new Date())) {
            return ResultUtil.error("对不起，预约未开始，预约失败！");
        }
        //设置了违约限制
        if (yySeatTimeConfig.getIsHaveSignLimit() == 1 && yySeatTimeConfig.getIsHavePunishmentLimit() == 1) {
            //查询此用户违约次数，如果滚动时间周期内超过最大违约次数，则不允许预约
            Integer maxCount = yySeatTimeConfig.getBreakContractCount();
            //滚动时间周期
            Integer periodCount = yySeatTimeConfig.getBreakContractTimePeriod();
            YySeatExamDefaultRecordExample yySeatExamDefaultRecordExample = new YySeatExamDefaultRecordExample();
            yySeatExamDefaultRecordExample.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId).andCardNumberEqualTo(cardNumber)
                    .andCreateTimeBetween(new Date(new Date().getTime() - periodCount * 24 * 60 * 60 * 1000L), new Date());
            List <YySeatExamDefaultRecord> yySeatExamDefaultRecords = yySeatExamDefaultRecordService.selectByExample(yySeatExamDefaultRecordExample);
            if (yySeatExamDefaultRecords.size() >= maxCount) {
                return ResultUtil.error("对不起，您违约已达上线，不能预约座位！");
            }
        }
        YySeatInfo seat = seatInfoService.find(seatId);
        if (seat == null) {
            return ResultUtil.error("对不起，未找到对应座位，预约失败！");
        }
        //预约前查询当前用户是否已经有预约记录
        List <Long> okIds = new ArrayList <>();
        //预约成功和使用中
        okIds.add(1L);
        okIds.add(7L);
        YySeatExamAppointmentExample seatAppointmentExample = new YySeatExamAppointmentExample();
        seatAppointmentExample.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(libraryId)
                .andAppointmentstatusIn(okIds).andCardnumberEqualTo(cardNumber);
        List <YySeatExamAppointment> seatappointmentList = yySeatExamAppointmentService.selectByExample(seatAppointmentExample);
        if (seatappointmentList.size() > 0) {
            return ResultUtil.error("抱歉，同一用户无法重复预约！");
        }
        YySeatExamAppointmentExample yySeatExamAppointmentExample = new YySeatExamAppointmentExample();
        yySeatExamAppointmentExample.createCriteria().andSeatidEqualTo(seatId).andStatusEqualTo(1).andAppointmentstatusIn(okIds);
        List <YySeatExamAppointment> yySeatExamAppointments = yySeatExamAppointmentService.selectByExample(yySeatExamAppointmentExample);

        YySeatExamWhiteExample example = new YySeatExamWhiteExample();
        example.createCriteria().andLibraryIdEqualTo(libraryId).andSeatIdEqualTo(seatId).andUserStatusEqualTo("1");
        List <YySeatExamWhite> whiteList = yySeatExamWhiteService.selectByExample(example);

        if (yySeatExamAppointments.size() > 0 || whiteList.size() > 0) {
            return ResultUtil.error("抱歉，该座位已有预约，请刷新选座!");
        }
        try {
            bean.setUserid(userId);
            bean.setCardnumber(cardNumber);
            bean.setNum(seat.getNumber());

            bean.setBuildingid(seat.getBulidingid());
            bean.setFloorid(seat.getFloorid());
            bean.setRoomid(seat.getRoomid());

            YyRoomInfo yyRoomInfo = roomInfoService.find(seat.getRoomid());

            bean.setBuildName(yyRoomInfo.getBuildingname());
            bean.setFloorName(yyRoomInfo.getFloorname());
            bean.setRoomName(yyRoomInfo.getRoomname());

            bean.setSeatid(seatId);
            bean.setSeatpoint(seat.getSeatpoint());
            bean.setLibraryid(libraryId);

            bean.setStatus(1);
            bean.setIsKnow(0);//未点击我知道了
            bean.setAppointmentday(new Date());
            //当前座位预留时间（因为这个在后台有可能修改，所以动态保存）
            bean.setLayTime(yySeatTimeConfig.getLayTime());
            bean.setCreateTime(new Date());
            //如果预约时间在开始使用时间之前，shouldBeTime 为开始使用时间+预留时间，否则为当前时间+预留时间
            Date startUseTime = sdf.parse(yySeatTimeConfig.getStartUseTime() + " 00:00:00");
            Long tempTime = new Date().getTime();
            if (new Date().before(startUseTime)) {
                tempTime = startUseTime.getTime() + (long) (Double.parseDouble(bean.getLayTime()) * 60 * 60 * 1000L);
            } else {
                tempTime = new Date().getTime() + (long) (Double.parseDouble(bean.getLayTime()) * 60 * 60 * 1000L);
            }
            bean.setShouldBeTime(new Date(tempTime));
            bean.setLibraryname(seat.getLibraryname());
            bean.setAppointmentstatus(1L);
            YySeatExamAppointment appointment = yySeatExamAppointmentService.add(bean);
            if (appointment != null) {
                return ResultUtil.success(appointment, "预约成功");
            } else {
                return ResultUtil.error("系统错误，请稍后重试!");
            }
        } catch (Exception ex) {
            return ResultUtil.error("系统错误，请稍后重试!");
        }
    }

    /**
     * 取消考研座位预约（1.0）
     *
     * @param seatAppointmentId
     * @return
     */
    @RequestMapping("delAppointment")
    @ResponseBody
    public Result <YySeatExamAppointment> delAppointment(Long seatAppointmentId) {

        YySeatExamAppointment seatAppointment = yySeatExamAppointmentService.find(seatAppointmentId);
        //判断是否被系统释放
        if (Objects.equals(seatAppointment.getAppointmentstatus(), 4L)) {//未首次签到
            return ResultUtil.error("未在规定时间内首次签到被系统主动释放，请刷新！");
        } else if (Objects.equals(seatAppointment.getAppointmentstatus(), 8L)) {//馆员手动释放
            return ResultUtil.error("被图书馆管理员手动释放，请刷新！");
        } else if (Objects.equals(seatAppointment.getAppointmentstatus(), 9L)) {//签到不足
            return ResultUtil.error("在规定时间内签到数量不足被系统主动释放，请刷新！");
        }
        //leavetime：读者取消预约时间，appointmentstatus：预约状态
        if (seatAppointment.getSigntime() == null) {//未首次签到
            seatAppointment.setAppointmentstatus(3L);//取消预约
            seatAppointment.setLeavetime(new Date());
        } else {//已首次签到
            seatAppointment.setAppointmentstatus(5L);//使用成功
            seatAppointment.setReleasetime(new Date());
        }
        try {
            YySeatExamAppointment sa = yySeatExamAppointmentService.save(seatAppointment);
            yySeatExamWhiteService.updateUserNull(sa);
            if (sa != null) {
                return ResultUtil.success(sa, "操作成功!");
            }
        } catch (Exception e) {
            LogUtil.log("delAppointmente方法异常!");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     * 考研座位入座签到或签离（1.0）
     *
     * @param seatAppointmentId
     * @param libraryId
     * @param userId
     * @param cardNumber
     * @param isComeOrGo        //1代表签到，2代表签离
     * @param recordId          //签离时的记录id
     * @return
     */
    @RequestMapping("joinSeat")
    @ResponseBody
    public Result <YySeatExamAppointmentSignRecord> joinSeat(
            @RequestParam(value = "seatAppointmentId") Long seatAppointmentId,
            @RequestParam(value = "libraryId") Long libraryId,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "cardNumber") String cardNumber,
            @RequestParam(value = "isComeOrGo") Integer isComeOrGo,
            @RequestParam(value = "recordId", required = false) Long recordId,
            @RequestParam(value = "ibeaconBatteryJson", required = false) String ibeaconBatteryJson) {

        YySeatExamAppointment seatAppointment = yySeatExamAppointmentService.find(seatAppointmentId);
        //判断是否被系统释放
        if (Objects.equals(seatAppointment.getAppointmentstatus(), 4L)) {
            //未首次签到
            return ResultUtil.error("未在规定时间内首次签到被系统主动释放，请刷新！");
        } else if (Objects.equals(seatAppointment.getAppointmentstatus(), 8L)) {
            //馆员手动释放
            return ResultUtil.error("被图书馆管理员手动释放，请刷新！");
        } else if (Objects.equals(seatAppointment.getAppointmentstatus(), 9L)) {
            //签到不足
            return ResultUtil.error("在规定时间内签到数量不足被系统主动释放，请刷新！");
        }
        if (seatAppointment == null) {
            return ResultUtil.error("未找到对应的座位预约信息!");
        }
        if (recordId != null) {
            //签离
            YySeatExamAppointmentSignRecord yySeatExamAppointmentSignRecord = yySeatExamAppointmentSignRecordService.find(recordId);
            if (yySeatExamAppointmentSignRecord != null) {
                yySeatExamAppointmentSignRecord.setSignEndTime(new Date());
                yySeatExamAppointmentSignRecord.setIsComeOrGo(isComeOrGo);
                YySeatExamAppointmentSignRecord temp = yySeatExamAppointmentSignRecordService.save(yySeatExamAppointmentSignRecord);

                //存储ibeacon剩余电量
                if (ibeaconBatteryJson != null && !ibeaconBatteryJson.isEmpty()) {
                    List <YyIbeaconBatteryInfo> ibeaconBatteryInfos = JSON.parseArray(ibeaconBatteryJson, YyIbeaconBatteryInfo.class);
                    yyIbeaconBatteryInfoService.addIbeaconBattery(ibeaconBatteryInfos, libraryId, new Date());
                }

                return ResultUtil.success(temp, "签离成功!");
            }
        } else {
            //签到
            String todayString = LocalDate.now().toString();
            LoginLog log = this.getDeviceId(userId);
            //根据图书馆Id获得配置
            YySeatTimeConfigExample configEx = new YySeatTimeConfigExample();
            configEx.createCriteria().andLibraryIdEqualTo(libraryId);
            YySeatTimeConfig config = yySeatTimeConfigService.selectByExample(configEx).stream().findFirst().orElse(null);

            if(config != null && config.getIsHaveSignDeviceLimit() == 1){
                if(log != null && log.getDeviceid() != null && !log.getDeviceid().isEmpty()){
                    String message = this.checkSignDevice(libraryId,cardNumber,todayString,log.getDeviceid(),config,seatAppointmentId);
                    if(message != null){
                        return ResultUtil.error(message);
                    }
                }
            }
            //修改签到时间
            if (seatAppointment.getSigntime() == null) {
                seatAppointment.setSigntime(new Date());
                //使用中7
                seatAppointment.setAppointmentstatus(7L);
                yySeatExamAppointmentService.save(seatAppointment);
            }

            //插入签到记录
            YySeatExamAppointmentSignRecord record = new YySeatExamAppointmentSignRecord();
            record.setStatus(1);
            record.setSignStartTime(new Date());
            record.setLibraryId(libraryId);

            record.setUserid(userId);
            //isComeOrGo：1代表签到，2代表签离
            record.setIsComeOrGo(isComeOrGo);
            record.setCardNumber(cardNumber);

            record.setYySeatAppointmentId(seatAppointmentId);
            record.setRemarkTime(LocalDate.now().toString());
            record.setIgnoreStatus("1");

            record.setSignDeviceId(log!=null?log.getDeviceid():"");
            record.setSignDeviceName(log!=null?log.getDevicename():"");

            YySeatExamAppointmentSignRecord temp = yySeatExamAppointmentSignRecordService.add(record);

            //存储ibeacon剩余电量
            if (ibeaconBatteryJson != null && !ibeaconBatteryJson.isEmpty()) {
                List <YyIbeaconBatteryInfo> ibeaconBatteryInfos = JSON.parseArray(ibeaconBatteryJson, YyIbeaconBatteryInfo.class);
                yyIbeaconBatteryInfoService.addIbeaconBattery(ibeaconBatteryInfos, libraryId, new Date());
            }
            return ResultUtil.success(temp, "签到成功!");

        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     *校验签到手机
     */
    private String checkSignDevice(Long libraryId,String cardNumber,String todayString,String deviceId,
                                   YySeatTimeConfig config,Long seatAppointmentId){
        //防代签到限制规则：一个设备一天只能给一个正在使用中的预约记录签到
        YySeatExamAppointmentSignRecordExample recordEx1 = new YySeatExamAppointmentSignRecordExample();
        recordEx1.createCriteria().andRemarkTimeEqualTo(todayString)
                .andLibraryIdEqualTo(libraryId).andSignDeviceIdEqualTo(deviceId).andYySeatAppointmentIdNotEqualTo(seatAppointmentId)
                .andIgnoreStatusEqualTo("1");
        List<YySeatExamAppointmentSignRecord> recodes = yySeatExamAppointmentSignRecordService.selectByExample(recordEx1);

        if(recodes!= null && recodes.size()>0){
            List<Long> appointments = new ArrayList<>();
            recodes.forEach( t ->appointments.add(t.getYySeatAppointmentId()));

            YySeatExamAppointmentExample examEx = new YySeatExamAppointmentExample();
            examEx.createCriteria().andIdIn(appointments).andAppointmentstatusEqualTo(7L);
            examEx.setOrderByClause(" id DESC");
            YySeatExamAppointment examT = yySeatExamAppointmentService.selectByExample(examEx).stream().findFirst().orElse(null);

            if(examT != null ){
                StringBuffer seat = new StringBuffer();
                seat.append("签到失败，当前设备已经给").append(examT.getFloorName()).append("的")
                        .append(examT.getRoomName()).append("的").append(examT.getNum())
                        .append("座位签到。");
                return seat.toString();
            }
        }

        //防代签到限制规则：一个预约记录，最近7(可以设置)个签到日内，最多允许2（可以设置；最小值2）个设备。
        int days = config.getDeviceDays();
        int count = config.getDeviceCount();
        //获得最近n个签到日内的有多少个设备信息
        List<YySeatExamAppointmentSignRecord> differentDevices = yySeatExamAppointmentSignRecordService.getLateSignDaysDevice(seatAppointmentId,days);

        if(differentDevices != null && !differentDevices.isEmpty()){
            HashSet<String> set = new HashSet<>();
            set.add(deviceId+cardNumber);

            for(int i = 0;i<differentDevices.size();i++){
                set.add(differentDevices.get(i).getSignDeviceId()+differentDevices.get(i).getCardNumber());
            }

            if(set.size() > count){
                return "签到失败，请勿频繁更换设备。如有特殊原因请联系馆员老师!";
            }
        }
        return null;
    }

    /**
     *获得用户最后一次的设备id
     */
    private LoginLog getDeviceId(Long userId){
        LoginLogExample logEx = new LoginLogExample();
        logEx.createCriteria().andUseridEqualTo(userId).andStatusEqualTo(1);
        logEx.setOrderByClause("id desc");
        logEx.setLimitStart(0);
        logEx.setLimitEnd(1);

        LoginLog log = loginLogService.selectByExample(logEx).stream().findFirst().orElse(null);
        return log;
    }

    /**
     * 考研座位违约后，点击知道了，修改状态（1.0）
     *
     * @return
     */
    @RequestMapping("examRemind_know")
    @ResponseBody
    public Result <YySeatExamAppointment> examRemind_know(Long seatAppointmentId) {

        YySeatExamAppointment seatAppointment = yySeatExamAppointmentService.find(seatAppointmentId);
        seatAppointment.setIsKnow(1);
        try {
            YySeatExamAppointment sa = yySeatExamAppointmentService.save(seatAppointment);
            if (sa != null) {
                return ResultUtil.success(null, "知道了");
            }
        } catch (Exception e) {
            LogUtil.log("知道了异常!");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     * app考研座位倒计时结束后（未按时进行首签），释放座位，并记录一次违约（1.0）
     *
     * @return
     */
    @RequestMapping("appReleaseSeat")
    @ResponseBody
    public Result <YySeatExamAppointment> appReleaseSeat(
            Long userId, Long libraryId, String cardNumber, Long seatAppointmentId) {

        YySeatExamAppointment seatAppointment = yySeatExamAppointmentService.find(seatAppointmentId);
        if (seatAppointment.getSigntime() == null && seatAppointment.getAppointmentstatus() == 1L) {
            if (seatAppointment.getShouldBeTime().after(new Date(new Date().getTime() + 1 * 1000L))) {//1秒的时间差
                return ResultUtil.error("未到释放时间，请稍后重试！");
            }
            seatAppointment.setAppointmentstatus(4L);//预约超时
            seatAppointment.setReturntime(new Date());
            try {
                YySeatExamAppointment sa = yySeatExamAppointmentService.save(seatAppointment);
                if (sa != null) {
                    //插入一条违约记录
                    examDefaultRecord(userId, libraryId, cardNumber, seatAppointmentId);
                    yySeatExamWhiteService.updateUserNull(sa);
                    return ResultUtil.success(null, "操作成功");
                }
            } catch (Exception e) {
                LogUtil.log("appReleaseSeat异常!");
            }
        } else {
            return ResultUtil.success(null, "操作成功");
        }
        return ResultUtil.error("系统错误，请稍后重新再试!");
    }

    /**
     * 获取考研座位预约的历史记录(列表，多条)
     *
     * @param userId
     * @return
     */
    @RequestMapping("getExamSeatAppointmentList")
    @ResponseBody
    public Object getExamSeatAppointmentList(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "libraryId") Long libraryId,
            @RequestParam(value = "page", required = false) Integer page,
            Model model) {
        page = page != null && page > 1 ? page : 1;
        String cardNumber = getCarNumber(userId, libraryId);
        YySeatExamAppointmentExample seatAppointmentExample = new YySeatExamAppointmentExample();
        seatAppointmentExample.setLimitStart((page - 1) * 15);
        seatAppointmentExample.setLimitEnd(15);
        seatAppointmentExample.setOrderByClause("id desc");
        seatAppointmentExample.createCriteria().andLibraryidEqualTo(libraryId).andStatusEqualTo(1).andCardnumberEqualTo(cardNumber);
        List <YySeatExamAppointment> tempList = yySeatExamAppointmentService.selectByExample(seatAppointmentExample);
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取开始使用时间
        YySeatTimeConfigExample example = new YySeatTimeConfigExample();
        example.createCriteria().andLibraryIdEqualTo(libraryId);
        YySeatTimeConfig yySeatTimeConfig = yySeatTimeConfigService.selectByExample(example).get(0);
        if (tempList.size() > 0) {
            for (YySeatExamAppointment seatAppointment : tempList) {
                if (seatAppointment.getBuildName() != null && seatAppointment.getFloorName() != null && seatAppointment.getRoomName() != null) {
                    seatAppointment.setAddress(seatAppointment.getBuildName() + "  " + seatAppointment.getFloorName() + " " + seatAppointment.getRoomName());
                } else {
                    seatAppointment.setAddress("暂无信息");
                }
                seatAppointment.setSeatpoint(seatAppointment.getNum());
                Long status = seatAppointment.getAppointmentstatus();
                if (status == 1L) {//预约成功
                    seatAppointment.setTitle("预约成功");
                    seatAppointment.setSecondTime(yySeatTimeConfig.getStartUseTime() + " 00:00:00");
                } else if (status == 3L) {
                    seatAppointment.setTitle("取消预约");
                    if (seatAppointment.getLeavetime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getLeavetime()));
                    }
                } else if (status == 4L) {
                    seatAppointment.setTitle("违规(未首签)");
                    if (seatAppointment.getReturntime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getReturntime()));
                    }
                } else if (status == 5L) {
                    seatAppointment.setTitle("使用结束");
                    if (seatAppointment.getReleasetime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getReleasetime()));
                    }
                } else if (status == 6L) {
                    seatAppointment.setTitle("违规(未释放座位)");
                    if (seatAppointment.getReturntime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getReturntime()));
                    }
                } else if (status == 7L) {
                    seatAppointment.setTitle("使用中");
                    seatAppointment.setSecondTime(yySeatTimeConfig.getStartUseTime() + " 00:00:00");
                } else if (status == 8L) {
                    seatAppointment.setTitle("馆员释放");
                    if (seatAppointment.getReturntime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getReturntime()));
                    }
                } else {
                    seatAppointment.setTitle("违规(签到不足)");
                    if (seatAppointment.getReturntime() != null) {
                        seatAppointment.setSecondTime(sdm.format(seatAppointment.getReturntime()));
                    }
                }
                seatAppointment.setFirstTime(sdm.format(seatAppointment.getCreateTime()));
            }
        }
        return ResultUtil.success(tempList);
    }

    public Boolean isOpenFunctionById(Long libraryId ,Long userId ,Long id,String cardNumber){
        boolean flag = false ;
        //判断图书馆是否开启某个功能
        Library library = libraryService.find(libraryId) ;
        List<Long> functionIds = new Gson().fromJson(library.getFunctions(), new TypeToken<List<Long>>() {
        }.getType());
        if(functionIds.contains(id)){//图书馆开启
            UserLibraryExample example =  new UserLibraryExample();
            example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(libraryId).andUserIdEqualTo(userId).andCardNumberEqualTo(cardNumber) ;
            List<UserLibrary> userLibraryList = userLibraryService.selectByExample(example) ;
            if(userLibraryList.size() > 0){
                UserLibrary userLibrary = userLibraryList.get(0) ;
                Long userGroupId = userLibrary.getUserGroupId() ;
                if(userGroupId != null){
                    LibUserRole libUserRole = libUserRoleService.find(userGroupId) ;
                    List<Long> roleFunctionIds = new Gson().fromJson(libUserRole.getFunctions(), new TypeToken<List<Long>>() {
                    }.getType());
                    if(roleFunctionIds.contains(id)){
                        flag = true ;
                    }
                }
            }
        }
        return flag ;
    }

}
