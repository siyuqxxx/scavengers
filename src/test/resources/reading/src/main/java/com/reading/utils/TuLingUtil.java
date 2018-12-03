package com.reading.utils;

import com.reading.model.TuLing;
import com.reading.model.TuLingData;

import java.util.List;

public class TuLingUtil {

    private static String api_secret = "f3ce35d9457a47958f8dbd70f0763a73";

    public static String baseUrl = "http://www.tuling123.com";//图灵域名

    public static String openapi = baseUrl + "/openapi/api/v2";//用户图灵接口智能咨询问答


    public static String matchingScore = baseUrl + "/v1/kb/match";//语料库设置匹配度

    public static String secret = baseUrl + "/v1/kb/secret";//加密模式

    public static String addUrl =baseUrl+ "/v1/kb/import";//新增语料库

    public static String queryUrl = baseUrl + "/v1/kb/select";//语料库查询

    public static String updateUrl = baseUrl + "/v1/kb/update";//修改

    public static String deleteUrl = baseUrl + "/v1/kb/delete";//删除




    public static String[] getAnswer(List <TuLing.ResultsBean> beans) {
        String text = "";
        String type = "";
        for (TuLing.ResultsBean bean : beans) {
            switch (bean.getResultType()) {
                case "text":
                    text += bean.getValues().getText();
                    type = "text";
                    break;
                case "url":
                    text += bean.getValues().getUrl();
                    break;
                case "voice":
                    text += bean.getValues().getVoice();
                    break;
                case "video":
                    text += bean.getValues().getVideo();
                    break;
                case "image":
                    text += bean.getValues().getImage();
                    type = "image";
                    break;
                case "news":
                    text += bean.getValues().getNews();
                    break;
                default:
                    LogUtil.log("未知的回答！");
                    break;
            }
        }
        return new String[]{text, type};
    }

    public static TuLingData getData(String appKey, String userId, String question) {
        TuLingData tuLingData = new TuLingData();
        TuLingData.UserInfoBean userInfoBean = new TuLingData.UserInfoBean();
        userInfoBean.setApiKey(appKey);
        userInfoBean.setUserId(userId);
        tuLingData.setUserInfo(userInfoBean);
        TuLingData.PerceptionBean perceptionBean = new TuLingData.PerceptionBean();
        tuLingData.setPerception(perceptionBean);
        TuLingData.PerceptionBean.InputTextBean inputTextBean = new TuLingData.PerceptionBean.InputTextBean();
        inputTextBean.setText(question);
        perceptionBean.setInputText(inputTextBean);
        return tuLingData;
    }
}
