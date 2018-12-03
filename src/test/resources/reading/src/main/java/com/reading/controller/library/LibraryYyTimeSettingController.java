package com.reading.controller.library;

import com.alibaba.fastjson.JSON;
import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.utils.EnumUtil;
import com.reading.utils.LogUtil;
import com.reading.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 座位预约时间配置
 * Created by Administrator on 2016/11/29.
 */
@Controller
@RequestMapping("library/yytimesetting")
public class LibraryYyTimeSettingController extends BaseLibraryController {
    @Resource
    private YyTimeSettingService service;
    @Resource
    private YyTimeConfigService configService;
    @Resource
    private YyRoomInfoService roomInfoService;
    @Resource
    private LogOperateService logOperateService;
    @Resource
    private YyTimeSettingDetailService yyTimeSettingDetailService;
    @Resource
    private YyTimeSettingHistoryService yyTimeSettingHistoryService;
    @Resource
    private YySeatAppointmentService yySeatAppointmentService;
    @Resource
    private YyTimeConfigService yyTimeConfigService;
    String table = "yytimesetting";

    @RequestMapping("list")
    public String list(@RequestParam(value = "p", required = false) Integer p, HttpServletRequest request) {
        p = p != null && p > 1 ? p : 1;

        YyTimeSettingExample example = new YyTimeSettingExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);
        List<YyTimeSetting> yyTimeSettingList = service.selectByExample(example);
        if (yyTimeSettingList.size() > 0) {
            int i = (p - 1) * 20 + 1;
            for (YyTimeSetting bean : yyTimeSettingList) {
                bean.setNumber(i);
                i++;

                YyTimeSettingDetailExample ex = new YyTimeSettingDetailExample();
                ex.createCriteria().andTimeSettingIdEqualTo(bean.getKeyid()).andUseStatusEqualTo("1");
                List<YyTimeSettingDetail> settingsTemp = yyTimeSettingDetailService.selectByExample(ex);

                //排序
                List<YyTimeSettingDetail> settings = yyTimeSettingDetailService.getSortList(settingsTemp);

                StringBuffer detailBuffer = new StringBuffer();
                if(settings != null && settings.size()>0){
                    for (YyTimeSettingDetail t : settings) {
                        detailBuffer.append(EnumUtil.WEEKLY.getValueByCode(t.getWeekType())).append(":(")
                                .append(t.getStartTime()).append("-").append(t.getEndTime()).append(");");
                    }
                }

                bean.setTimeSettingDetail(String.valueOf(detailBuffer));
            }
        }
        YyTimeConfigExample configExample = new YyTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(getLibrary(request).getId());
//        时间配置
        YyTimeConfig config = configService.selectByExample(configExample).stream().findFirst().orElse(null);
        request.setAttribute("data", config);
        request.setAttribute("list", yyTimeSettingList);
        request.setAttribute("count", service.countByExample(example));
        request.setAttribute("size", 20);
        request.setAttribute("p", p);
        return display(table + "_list");
    }

    @RequestMapping("add")
    public Object add(HttpServletRequest request) {

        return display(table + "_add");
    }

    @RequestMapping("addDos")
    @ResponseBody
    public Object addDos(HttpServletRequest request, YyTimeSetting yyTimeSetting,String timeSettingDetailJSON) {

        yyTimeSetting.setLibraryid(getLibrary(request).getId());
        yyTimeSetting.setLibraryname(getLibrary(request).getTitle());
        yyTimeSetting.setStatus(1);

        YyTimeSetting ytsT = service.adds(yyTimeSetting);

        Long settingId = ytsT.getKeyid();
        List<YyTimeSettingDetail> timeSettingDetailList = JSON.parseArray(timeSettingDetailJSON, YyTimeSettingDetail.class);

        Date d = new Date();
        if(timeSettingDetailList == null || timeSettingDetailList.size()<1){
            return ResultUtil.error( "添加失败，请添加开闭馆时间！");
        }else{

            //获得9999-12-31 23:59:59的时间
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime maxLocalDate = LocalDateTime.of(9999,12,31,23,59,59);
            ZonedDateTime zdt = maxLocalDate.atZone(zoneId);
            Date maxDate = Date.from(zdt.toInstant());

            for (YyTimeSettingDetail t : timeSettingDetailList) {

                //保存到详情表
                YyTimeSettingDetail ytsdT = new YyTimeSettingDetail();

                ytsdT.setTimeSettingId(settingId);
                ytsdT.setWeekType(t.getWeekType());
                ytsdT.setStartTime(t.getStartTime());

                ytsdT.setEndTime(t.getEndTime());
                ytsdT.setUseStatus("1");
                ytsdT.setEffectiveTime(d);

                ytsdT.setInvalidTime(maxDate);
                ytsdT.setCreateTime(d);
                ytsdT.setLastUpdateTime(d);

                yyTimeSettingDetailService.add(ytsdT);

            }
        }
        logOperateService.operatingData(getLibrary(request), "yytimesetting", getLibAdmin(request), 1l, 19l);
        return ResultUtil.success(d, "添加成功", "/library/" + table + "/list.html");
    }

    //    验证时间别名是否重复
    @RequestMapping("verifyAliasBylibrary")
    @ResponseBody
    public Object verifyAlias(HttpServletRequest request, String timeMark) {

        YyTimeSettingExample example = new YyTimeSettingExample();
        example.createCriteria().andStatusEqualTo(1).andLibraryidEqualTo(getLibrary(request).getId()).andTimeMarkEqualTo(timeMark);
        YyTimeSetting yyTimeSetting = service.selectByExample(example).stream().findFirst().orElse(null);
        if (yyTimeSetting != null) {
            return ResultUtil.error("别名已存在！");
        } else {
            return ResultUtil.success(null);
        }

    }

    @RequestMapping("edits")
    public Object edits(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        YyTimeSetting yyTimeSetting = service.find(id);

        YyTimeSettingDetailExample ex = new YyTimeSettingDetailExample();
        ex.createCriteria().andTimeSettingIdEqualTo(id).andUseStatusEqualTo("1");
        List<YyTimeSettingDetail> timeSettingsTemp = yyTimeSettingDetailService.selectByExample(ex);

        //排序
        List<YyTimeSettingDetail> timeSettingDetailList = yyTimeSettingDetailService.getSortList(timeSettingsTemp);

        request.setAttribute("data", yyTimeSetting);
        request.setAttribute("timeSettingDetailList", timeSettingDetailList);
        return display(table + "_edit");
    }

    @RequestMapping("editdos")
    @ResponseBody
    public Object editdos(HttpServletRequest request, YyTimeSetting yyTimeSetting,String timeSettingDetailJSON) throws Exception {

        YyTimeSetting ytsT = service.save(yyTimeSetting);

        List<YyTimeSettingDetail> timeSettingDetailList = JSON.parseArray(timeSettingDetailJSON, YyTimeSettingDetail.class);

        Date d = new Date();
        Date effectiveTime = d;
        LocalDate today = LocalDate.now();

        //获得9999-12-31 23:59:59的时间
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime maxLocalDate = LocalDateTime.of(9999,12,31,23,59,59);
        ZonedDateTime zdt = maxLocalDate.atZone(zoneId);
        Date maxDate = Date.from(zdt.toInstant());

        //格式化
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(timeSettingDetailList == null || timeSettingDetailList.size()<1){
            return ResultUtil.error( "添加失败，请添加开闭馆时间！");
        }else{
            Long settingId = ytsT.getKeyid();
            Long libraryId= ytsT.getLibraryid();

            //获得数据库中已存在的
            List<String> types = new ArrayList<>();
            types.add("DEFAULT");

            YyTimeSettingDetailExample ytsdEx1 = new YyTimeSettingDetailExample();
            ytsdEx1.createCriteria().andTimeSettingIdEqualTo(settingId).andWeekTypeNotIn(types);

            List<YyTimeSettingDetail> detailList1 = yyTimeSettingDetailService.selectByExample(ytsdEx1);

            String defaultTime = "";
            String tomorrow = "";
            String dayAfterTomorrow = "";

            //获得明天和后天的开馆时间
            for (YyTimeSettingDetail t : timeSettingDetailList) {
                //获得默认的时间
                if(Objects.equals(t.getWeekType(), "DEFAULT")){
                    defaultTime = t.getStartTime();
                }
                //获得明天的开馆时间
                if(Objects.equals(t.getWeekType(),today.plusDays(1).getDayOfWeek().toString())){
                    tomorrow = t.getStartTime();
                }
                //获得后天的开馆时间
                if(Objects.equals(t.getWeekType(),today.plusDays(2).getDayOfWeek().toString())){
                    dayAfterTomorrow = t.getStartTime();
                }
            }

            //获取明天的预约时间
            YyTimeConfigExample ytcEx = new YyTimeConfigExample();
            ytcEx.createCriteria().andLibraryIdEqualTo(libraryId);
            YyTimeConfig config = yyTimeConfigService.selectByExample(ytcEx).stream().findFirst().orElse(null);
            //如果预约时间存在，这里主要是获得生效时间
            if(config != null ){
                LocalTime tomStTime = LocalTime.parse(config.getSysTomStTime(), f);

                //今天
                String plusXDay;

                //查询今天有没有预约
                Date todayDate = Date.from(today.atStartOfDay(zoneId).toInstant());
                int count0 = this.getSeatCountByDate(libraryId,todayDate);
                if(count0 >0){
                    //明天
                    plusXDay = today.plusDays(1).toString();
                    String effectiveTimeTemp = plusXDay + " " + (!Objects.equals(tomorrow,"")?tomorrow:defaultTime) + ":00";
                    effectiveTime = format.parse(effectiveTimeTemp);
                }

                if(LocalTime.now().isAfter(tomStTime)){
                    //当前时间 在 可预约明天座位时间 之后
                    //查询明天有没有预约
                    Date tomorrowDate = Date.from(today.plusDays(1).atStartOfDay(zoneId).toInstant());
                    int count1 = this.getSeatCountByDate(libraryId,tomorrowDate);
                    if(count1 >0){
                        //后天
                        plusXDay = today.plusDays(2).toString();
                        String effectiveTimeTemp = plusXDay + " " + (!Objects.equals(dayAfterTomorrow,"")?dayAfterTomorrow:defaultTime) + ":00";
                        effectiveTime = format.parse(effectiveTimeTemp);
                    }
                }
            }

            Map<Long,Long> updateIds = new HashMap<>();
            for (YyTimeSettingDetail t : timeSettingDetailList) {

                //根据weekType和time_setting_id获得
                YyTimeSettingDetailExample ex = new YyTimeSettingDetailExample();
                ex.createCriteria().andTimeSettingIdEqualTo(settingId).andWeekTypeEqualTo(t.getWeekType());

                YyTimeSettingDetail ytsdT = yyTimeSettingDetailService.selectByExample(ex).stream().findFirst().orElse(null);
                if(ytsdT != null){
                    //将查出来的数据存入历史表
                    YyTimeSettingHistory ytshT = new YyTimeSettingHistory();

                    ytshT.setTimeSettingId(ytsdT.getTimeSettingId());
                    ytshT.setWeekType(ytsdT.getWeekType());
                    ytshT.setStartTime(ytsdT.getStartTime());

                    ytshT.setEndTime(ytsdT.getEndTime());
                    ytshT.setUseStatus(ytsdT.getUseStatus());
                    ytshT.setEffectiveTime(ytsdT.getEffectiveTime());

                    ytshT.setInvalidTime(ytsdT.getInvalidTime());
                    ytshT.setCreateTime(ytsdT.getCreateTime());
                    ytshT.setLastUpdateTime(ytsdT.getLastUpdateTime());

                    ytshT.setMigrationTime(d);
                    ytshT.setYyTimeSettingHistoryId(ytsdT.getYyTimeSettingHistoryId());

                    YyTimeSettingHistory tempT = yyTimeSettingHistoryService.add(ytshT);

                    //修改详情表
                    ytsdT.setStartTime(t.getStartTime());
                    ytsdT.setEndTime(t.getEndTime());
                    ytsdT.setUseStatus("1");

                    ytsdT.setLastUpdateTime(d);

                    if(ytsdT.getEffectiveTime().before(d)){
                        ytsdT.setYyTimeSettingHistoryId(tempT.getId());
                    }

                    ytsdT.setEffectiveTime(effectiveTime);
                    ytsdT.setInvalidTime(maxDate);

                    yyTimeSettingDetailService.save(ytsdT);
                    //将修改的数据添加到updateIds，下一步从detailIds移除这个updateIds
                    updateIds.put(ytsdT.getId(),ytsdT.getId());
                }else{
                    //存入详细表
                    ytsdT = new YyTimeSettingDetail();

                    ytsdT.setTimeSettingId(settingId);
                    ytsdT.setWeekType(t.getWeekType());
                    ytsdT.setStartTime(t.getStartTime());

                    ytsdT.setEndTime(t.getEndTime());
                    ytsdT.setUseStatus("1");
                    ytsdT.setCreateTime(d);

                    ytsdT.setLastUpdateTime(d);
                    ytsdT.setEffectiveTime(effectiveTime);
                    ytsdT.setInvalidTime(maxDate);

                    yyTimeSettingDetailService.add(ytsdT);
                }
            }

            //获得需要删除的数据
            Iterator<YyTimeSettingDetail> iterator = detailList1.iterator();
            while(iterator.hasNext()) {
                YyTimeSettingDetail thisT = iterator.next();
                if(updateIds.containsKey(thisT.getId())){
                    iterator.remove();
                }else {
                    //将状态置为0，修改失效时间invalid_time
                    //获得距离今天最近的未来的周几的日期
                    String week = thisT.getWeekType();
                    LocalDate weekLocalDate= LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(week)));
                    ZonedDateTime zdt1 = weekLocalDate.atStartOfDay(zoneId);
                    Date weekDate = Date.from(zdt1.toInstant());

                    Date oldEffectiveTime = thisT.getEffectiveTime();
                    Date invalidDate = new Date();
                    thisT.setEffectiveTime(invalidDate);

                    //如果当前时间段正在被使用，且有预约记录，又在此次被删除，那么失效时间为这条记录的结束时间
                    if(Objects.equals(thisT.getUseStatus(),"1") && oldEffectiveTime.before(invalidDate)){
                        int count = this.getSeatCountByDate(libraryId,weekDate);
                        if(count>0){
                            String invalidTime = weekLocalDate + " " + thisT.getEndTime() + ":00";
                            invalidDate = format.parse(invalidTime);
                        }
                    }

                    thisT.setInvalidTime(invalidDate);
                    thisT.setUseStatus("0");
                    thisT.setLastUpdateTime(d);
                    yyTimeSettingDetailService.save(thisT);
                }
            }
        }
        logOperateService.operatingData(getLibrary(request), "yytimesetting", getLibAdmin(request), 2L, 19l);
        return ResultUtil.success(null, "修改成功,生效时间为" +format.format(effectiveTime), "/library/" + table + "/list.html");
    }

    //根据时间获得当天的预约座位的数量
    private int getSeatCountByDate(Long libraryId,Date date){
        YySeatAppointmentExample ex = new YySeatAppointmentExample();
        ex.createCriteria().andLibraryidEqualTo(libraryId)
                .andAppointmentdayEqualTo(date)
                .andLeavetimeIsNull().andReturntimeIsNull()
                .andAppointmentstatusEqualTo(1L).andStatusEqualTo(1);
        int count = yySeatAppointmentService.countByExample(ex);

        return count;
    }

    @RequestMapping("dels")
    @ResponseBody
    public Object dels(HttpServletResponse response, HttpServletRequest request, Long ids) {


        YyTimeSetting setting = service.find(ids);
        setting.setStatus(0);
//            判断时间是否有引用(普通以及考研类型)
        YyRoomInfoExample infoExample = new YyRoomInfoExample();
        infoExample.createCriteria().andTimesettingidEqualTo(ids).andTypeNotEqualTo(EnumUtil.ROOM_TYPE.collection.getType_value());
        List<YyRoomInfo> infos = roomInfoService.selectByExample(infoExample);
        if (infos.size() > 0) {
            return ResultUtil.success(null, "操作失败，此时间段已经被时间引用！", "/library/" + table + "/list.html");
        } else {
            service.save(setting);
            return ResultUtil.success(null, "操作成功！", "/library/" + table + "/list.html");
        }


    }

//    --------------------------------------时间配置（公共部分）-----------------------------------------------------------------------------------

    @RequestMapping("addConfig")
    public Object addConfig(HttpServletRequest request) {

        return display("timeconfig_add");
    }

    @RequestMapping("addDoConfig")
    @ResponseBody
    public Object addDoConfig(HttpServletRequest request, YyTimeConfig timeConfig) {

        timeConfig.setLibraryId(getLibrary(request).getId());
        timeConfig.setLibraryName(getLibrary(request).getTitle());

        configService.add(timeConfig);
        logOperateService.operatingData(getLibrary(request), "yytimeConfigsetting", getLibAdmin(request), 1l, 19l);
        return ResultUtil.success(null, "添加成功", "/library/" + table + "/list.html");
    }

    @RequestMapping("config_edits")
    public Object config_edits(HttpServletRequest request) {
        YyTimeConfig config = configService.find(Long.parseLong(request.getParameter("id")));

        request.setAttribute("data", config);
        return display("config_edit");
    }

    @RequestMapping("config_editdos")
    @ResponseBody
    public Object config_editdos(HttpServletRequest request, YyTimeConfig config) {
        configService.save(config);
        logOperateService.operatingData(getLibrary(request), "yytimeConfigsetting", getLibAdmin(request), 2l, 19l);
        return ResultUtil.success(null, "修改成功", "/library/" + table + "/list.html");
    }

}
