package com.reading.controller.api;

import com.alibaba.fastjson.JSON;
import com.reading.base.BaseApiController;
import com.reading.data.model.RobotConfig;
import com.reading.data.model.RobotConfigExample;
import com.reading.data.model.RobotConsultRecord;
import com.reading.data.service.RobotConfigService;
import com.reading.data.service.RobotConsultRecordService;
import com.reading.model.TuLing;
import com.reading.model.TuLingData;
import com.reading.utils.HttpUtil;
import com.reading.utils.LogUtil;
import com.reading.utils.ResultUtil;
import com.reading.utils.TuLingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 智能机器人客服问答
 * Created by Administrator on 2018/3/22.
 */
@Controller
@RequestMapping("api/robot")
public class ApiRobotController extends BaseApiController {

    @Resource
    private RobotConsultRecordService robotConsultRecordService;
    @Resource
    private RobotConfigService configService;


    /**
     * 机器人智能问答
     *
     * @param request
     * @param question  问题详情
     * @param userId    用户的id
     * @param libraryId 图书馆的id
     * @return
     */
    @RequestMapping("aiAnswers")
    @ResponseBody
    public Object aiAnswers(HttpServletRequest request, String question, Long userId, Long libraryId) {
        try {
            if (question.equals(new String(question.getBytes("iso-8859-1"), "iso-8859-1"))) {
                question = new String(question.getBytes("iso-8859-1"), "utf-8");
            }
// TODO: 2018/3/22 此处调用图灵的方法
            RobotConfigExample example = new RobotConfigExample();
            example.createCriteria().andLibraryIdEqualTo(libraryId).andStatusEqualTo(1);
            List<RobotConfig> configs = configService.selectByExample(example);
            if (configs.size() == 0) {
                return ResultUtil.error("对不起，未开通此功能！");
            }

            TuLingData tuLingData = TuLingUtil.getData(configs.get(0).getApikey(), userId.toString(), question);

            String df = HttpUtil.sendPost(TuLingUtil.openapi, JSON.toJSONString(tuLingData));
            TuLing tuLings = JSON.parseObject(df, TuLing.class);

//将问题以及答案存储在问答记录表中
            RobotConsultRecord record = new RobotConsultRecord();
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setStatus(1);
            record.setLibraryId(libraryId);
            record.setUserId(userId);
            record.setCardNumber(getCarNumber( userId, libraryId));
            record.setQuestion(question);
//        TODO 此处将图灵返回的答案存在记录表中，方便查询以及日后的统计
            String text = TuLingUtil.getAnswer(tuLings.getResults())[0];
            record.setAnswer(text);
            record.setSource("书蜗APP");
            RobotConsultRecord bean = robotConsultRecordService.add(record);
            if (bean != null) {
                //TODO将图灵返回的内容 返回到用户端
                return ResultUtil.success(tuLings, "获取成功！");
            } else {
                return ResultUtil.error("网络异常，请稍后重试！");
            }
        } catch (IOException e) {
            return ResultUtil.error("宝宝累了，让宝宝睡会！");
        }

    }





}
