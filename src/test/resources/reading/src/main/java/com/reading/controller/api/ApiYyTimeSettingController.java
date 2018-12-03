package com.reading.controller.api;

import com.reading.base.BaseApiController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.utils.EnumUtil;
import com.reading.utils.LogUtil;
import com.reading.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 座位预约---获取开放时间
 * <p>
 * Created by Administrator on 2016/11/29.
 */

@RequestMapping("api/YyTimeSetting")
@Controller
public class ApiYyTimeSettingController extends BaseApiController {

    @Resource
    YyTimeSettingService service;
    @Resource
    YyTimeConfigService configService;
    @Resource
    YyRoomInfoService roomInfoService;
    @Resource
    private YyTimeSettingDetailService yyTimeSettingDetailService;

    /**
     * 获取图书馆座位的预约时间
     *
     * @param libraryId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getTimeSetting", method = RequestMethod.POST)
    @ResponseBody
    public Object getTimeSetting(
            @RequestParam(value = "libraryId") Long libraryId,
            HttpServletRequest request, Model model) {

        YyTimeSettingExample example = new YyTimeSettingExample();
        example.createCriteria().andLibraryidEqualTo(libraryId).andStatusEqualTo(1);
        List<YyTimeSetting> yyTimeSettings = new ArrayList<YyTimeSetting>();
        try {

            Date date = new Date();

            YyTimeSetting yyTimeSetting = service.selectByExample(example).get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String a = format.format(date);
            StringBuffer d = new StringBuffer(a);
            StringBuffer e = new StringBuffer(a);

            Map<String,String> map = yyTimeSettingDetailService.getTimeByWeekDay(yyTimeSetting.getKeyid(),date);

            if(!map.get("startTime").isEmpty() && !map.get("endTime").isEmpty()){
                d.replace(11, 16, map.get("startTime"));
                e.replace(11, 16, map.get("endTime"));

                Date d1 = format.parse(d.toString());
                Date d2 = format.parse(e.toString());

                yyTimeSetting.setEndtime(map.get("endTime"));
                yyTimeSetting.setLibraryid(yyTimeSetting.getLibraryid());

                if (date.after(d1) && date.before(d2)) {
                    yyTimeSetting.setStarttime(a.toString().substring(11, 16));
                } else {
                    if (date.before(d1)) {
                        yyTimeSetting.setStarttime(map.get("startTime"));
                    }
                    if (date.after(d2)) {
                        yyTimeSetting.setStarttime(map.get("endTime"));
                    }
                }

                yyTimeSettings.add(yyTimeSetting);
            }

        } catch (Exception e) {
            LogUtil.log(e);
        }
        return ResultUtil.success(yyTimeSettings);
    }

    @RequestMapping("getTimeSeatConfig")
    @ResponseBody
    public Object getTimeSeatConfig(HttpServletRequest request,
                                    @RequestParam(value = "libraryId", required = true) Long libraryId
    ) {

        YyTimeConfigExample configExample = new YyTimeConfigExample();
        configExample.createCriteria().andLibraryIdEqualTo(libraryId);
        YyTimeConfig config = configService.selectByExample(configExample).stream().findFirst().orElse(null);

        if (config != null) {
            Map map = new HashMap();
            map.put("layTime", config.getLayTime());
            map.put("sysTodStTime", config.getSysTodStTime());
            map.put("sysTodEnTime", config.getSysTodEnTime());
            map.put("sysTomStTime", config.getSysTomStTime());
            map.put("sysTomEnTime", config.getSysTomEnTime());
            map.put("time", config.getTime());
//        模拟假数据
            map.put("starttime", "05:40");
            map.put("endtime", "06:40");
            map.put("nowTime", new Date());

//        间隔性签到默认第一条(需求需要讨论可行性)
            YyTimeSettingExample example = new YyTimeSettingExample();
            example.createCriteria().andLibraryidEqualTo(libraryId).andStatusEqualTo(1);
            List<YyTimeSetting> yyTimeSettings = service.selectByExample(example);

            if(yyTimeSettings.size()>0 && !yyTimeSettings.isEmpty()){
                YyTimeSetting yyTimeSetting = yyTimeSettings.get(0);

                if (yyTimeSetting.getIsOpenSign() == null) {
                    yyTimeSetting.setIsOpenSign(0);
                }
                map.put("isOpenSign", yyTimeSetting.getIsOpenSign());
                map.put("minute", yyTimeSetting.getMinute());

                return ResultUtil.success(map);
            }else{
                return ResultUtil.error("对不起，系统还没有配置开馆时间，请联系管理员！");
            }
        } else {
            return ResultUtil.error("对不起，系统还没有配置开馆时间，请联系管理员！");
        }
    }

    //    获取房间对应的间隔时间
    @RequestMapping("getSeatIntervalTimeConfig")
    @ResponseBody
    public Object getSeatIntervalTimeConfig(@RequestParam(value = "roomId") Long roomId,
                                            @RequestParam(value = "date", required = false) String date) {

        YyTimeSetting timeSetting = service.find(roomInfoService.find(roomId).getTimesettingid());
        if (timeSetting.getIsOpenSign() == null) {
            timeSetting.setIsOpenSign(0);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();
        try {
            if(date != null && !date.isEmpty()){
                String nowString = dfTime.format(d);
                d = df.parse(date + " " + nowString);
            }
            Map<String,String> map = yyTimeSettingDetailService.getTimeByWeekDay(timeSetting.getKeyid(),d);

            //starttime,endtime,zmStartTime,zmEndTime 这四个字段数据库已删除，因前台有用yyTimeSetting里面这四个字段，所以此处有判断，如果是周末setZmStartTime，否则setStarttime
            timeSetting.setStarttime(map.get("startTime"));
            timeSetting.setEndtime(map.get("endTime"));

            timeSetting.setZmStartTime(map.get("startTime"));
            timeSetting.setZmEndTime(map.get("endTime"));

            return ResultUtil.success(timeSetting,"请求成功！");
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error("时间格式错误！");
        }

    }
}
