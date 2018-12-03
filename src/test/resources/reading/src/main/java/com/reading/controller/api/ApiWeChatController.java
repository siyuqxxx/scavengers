package com.reading.controller.api;

import com.alibaba.fastjson.JSON;
import com.reading.base.BaseApiController;
import com.reading.data.model.*;
import com.reading.data.service.LibraryService;
import com.reading.data.service.RobotConfigService;
import com.reading.data.service.RobotConsultRecordService;
import com.reading.data.service.WechatThreeAuthorizeService;
import com.reading.model.TuLing;
import com.reading.model.TuLingData;
import com.reading.model.WeChatPower;
import com.reading.utils.*;
import com.reading.utils.aes.WXBizMsgCrypt;
import com.reading.utils.aes.XMLParse;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("api/WeChat")
@Controller
public class ApiWeChatController extends BaseApiController {

    @Resource
    WechatThreeAuthorizeService authorizeService;
    @Resource
    LibraryService libraryService;
    @Resource
    RobotConfigService configService;
    @Resource
    RobotConsultRecordService robotConsultRecordService;


    //    用于接受申请全网发布时的授权码
    String query_auth_code;

    /**
     * 授权事件接收(目前只用来获取第三方的入场券)
     *
     * @param request
     * @param response
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @param signature
     * @param encrypt_type
     * @return
     * @throws Exception
     */
    @RequestMapping("message")
    public void getWeCharMessage(HttpServletRequest request, HttpServletResponse response, String timestamp, String nonce,
                                 @RequestParam("msg_signature") String msgSignature, String signature, String encrypt_type) throws Exception {
//        LogUtil.log("Have Something new");
        //获取数据
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        WXBizMsgCrypt pc = new WXBizMsgCrypt(WechatThreeConfigUtil.three_token, WechatThreeConfigUtil.three_key, WechatThreeConfigUtil.three_app_id);
//        解密
        String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
        Element root = XMLParse.getContentByKey(result2);

        String str = root.getElementsByTagName("InfoType").item(0).getTextContent();
//判断事件类型
        if ("component_verify_ticket".equals(str)) {
//            LogUtil.log("huoderuchangquan_component_verify_ticket");
//获得推送验证的入场券
            String componentVerifyTicket = root.getElementsByTagName("ComponentVerifyTicket").item(0).getTextContent();
            WechatThreeConfigUtil.three_component_verify_ticket = componentVerifyTicket;
//            LogUtil.log(componentVerifyTicket);
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.MINUTE, -90);
//            LogUtil.log("获得日期——" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
//            LogUtil.log("iiii" + WechatThreeConfigUtil.three_component_access_token);
//            LogUtil.log("jjjj" + WechatThreeConfigUtil.three_ticket_create_time);

            if (WechatThreeConfigUtil.three_component_access_token == null || WechatThreeConfigUtil.three_ticket_create_time == null || calendar.getTime().after(WechatThreeConfigUtil.three_ticket_create_time)) {

                WechatThreeConfigUtil.three_component_access_token = getComponentAccessToken(componentVerifyTicket);
                LogUtil.log("_token" + WechatThreeConfigUtil.three_component_access_token);
                WechatThreeConfigUtil.three_ticket_create_time = new Date();
                weChatRefreshToken();
//                LogUtil.log("huode新的口令access_token ");
            }
            printXMl(response, "success");
        } else {
            String authorizerAppid = root.getElementsByTagName("AuthorizerAppid").item(0).getTextContent();
            WechatThreeAuthorize authorize = getWechatThreeAuthorize(authorizerAppid);
            if ("authorized".equals(str) || "updateauthorized".equals(str) || "unauthorized".equals(str)) {
//            授权相关的事件

                if ("authorized".equals(str)) {
//            授权成功通知
                    authorize.setAuthorizeStatus(EnumUtil.WechatThreeAuthorize.authorized.getStatus());
                } else if ("updateauthorized".equals(str)) {
//            更新授权
                    authorize.setAuthorizeStatus(EnumUtil.WechatThreeAuthorize.updateauthorized.getStatus());

                } else if ("unauthorized".equals(str)) {
//取消授权
                    authorize.setAuthorizeStatus(EnumUtil.WechatThreeAuthorize.unauthorized.getStatus());

                }
                authorize.setAuthorizeStatusTime(new Date());
                authorizeService.save(authorize);
            } else {
                LogUtil.log(str + "-----有新的事件------" + result2);

            }
        }
        printXMl(response, "success");


    }

    public void weChatRefreshToken() throws IOException {

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=" + WechatThreeConfigUtil.three_component_access_token;
        Map <String, Object> parameter = new HashMap <>();
        parameter.put("component_appid", WechatThreeConfigUtil.three_app_id);
//获取所有的授权公众号
        WechatThreeAuthorizeExample authorizeExample = new WechatThreeAuthorizeExample();
        authorizeExample.createCriteria().andAuthorizerAppidIsNotNull().andStatusEqualTo(1);
        List <WechatThreeAuthorize> authorizes = authorizeService.selectByExample(authorizeExample);
        for (WechatThreeAuthorize authorize : authorizes) {
            parameter.put("authorizer_refresh_token", authorize.getAuthorizerRefreshToken());
            parameter.put("authorizer_appid", authorize.getAuthorizerAppid());
            String data = HttpUtil.sendPost(url, JSON.toJSON(parameter).toString());
            JSONObject jsonObject = new JSONObject(data);
            authorize.setAuthorizerAccessToken(jsonObject.getString("authorizer_access_token"));
            authorize.setAuthorizerRefreshToken(jsonObject.getString("authorizer_refresh_token"));
            authorize.setExpiresIn(jsonObject.getInt("expires_in") + "");
            authorizeService.save(authorize);
        }
    }

    /**
     * 获得第三方口令
     *
     * @param componentVerifyTicket
     * @return
     */
    public String getComponentAccessToken(String componentVerifyTicket) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        Map <String, Object> parameter = new HashMap <>();
        parameter.put("component_appid", WechatThreeConfigUtil.three_app_id);
        parameter.put("component_appsecret", WechatThreeConfigUtil.three_app_secret);
        parameter.put("component_verify_ticket", componentVerifyTicket);
        String data = HttpUtil.sendPost(url, JSON.toJSON(parameter).toString());
        JSONObject jsonObject = new JSONObject(data);
        String componentAccessToken = jsonObject.getString("component_access_token");
        int expiresIn = jsonObject.getInt("expires_in");
        return componentAccessToken;
    }


    /**
     * 获取授权码（以及相关的权限）
     *
     * @param response
     * @param request
     * @param auth_code
     * @return
     */
    @RequestMapping("code")
    public void getcode(HttpServletResponse response, HttpServletRequest request, String auth_code, Long libraryId, String query_auth_code) throws IOException {

//        this.query_auth_code = query_auth_code;
//        return;
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=" + WechatThreeConfigUtil.three_component_access_token;
        Map <String, Object> parameter = new HashMap <>();
        parameter.put("component_appid", WechatThreeConfigUtil.three_app_id);
        parameter.put("authorization_code", auth_code);
        String data = HttpUtil.sendPost(url, JSON.toJSON(parameter).toString());
//授权功能
        WeChatPower.AuthorizationInfoBean infoBean = JSON.parseObject(data, WeChatPower.class).getAuthorization_info();

        WechatThreeAuthorize authorize = new WechatThreeAuthorize();
        authorize.setAuthorizerAppid(infoBean.getAuthorizer_appid());
        authorize.setAuthorizerAccessToken(infoBean.getAuthorizer_access_token());
        authorize.setExpiresIn(infoBean.getExpires_in() + "");
        authorize.setAuthorizerRefreshToken(infoBean.getAuthorizer_refresh_token());
        String ids = "[";
        for (WeChatPower.AuthorizationInfoBean.FuncInfoBean funcInfoBean : infoBean.getFunc_info()) {
            ids += funcInfoBean.getFuncscope_category().getId() + ",";
        }
        if (ids.length() > 1) {
            ids = ids.substring(0, ids.length() - 1) + "]";
        } else {
            ids = "]";
        }
        authorize.setWechatThreeFuncationIds(ids);
        authorize.setCreateTime(new Date());
        authorize.setUpdateTime(new Date());
        authorize.setStatus(1);
        authorize.setAuthCode(auth_code);

        WechatThreeAuthorizeExample authorizeExample = new WechatThreeAuthorizeExample();
        authorizeExample.createCriteria().andLibraryIdEqualTo(libraryId);
        WechatThreeAuthorize Tauthorize = authorizeService.selectByExample(authorizeExample).stream().findFirst().orElse(null);

        if (Tauthorize != null) {
            authorize.setId(Tauthorize.getId());
            authorizeService.save(authorize);
        }
        response.sendRedirect("/library/robot/robotcorpuslist.html");
    }

    /**
     * 申请全网发布
     *
     * @param request
     * @param response
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encrypt_type
     * @param msg_signature
     */
    @RequestMapping(value = "/{APPID}/information")
    @ResponseBody
    public void getHH(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "APPID") String appId,
                      String signature, String timestamp, String nonce, String openid, String encrypt_type, String msg_signature) {
        try {
            InputStream inputStream = request.getInputStream();
            String postData = IOUtils.toString(inputStream, "UTF-8");
//        解密
            WXBizMsgCrypt pc = new WXBizMsgCrypt(WechatThreeConfigUtil.three_token, WechatThreeConfigUtil.three_key, WechatThreeConfigUtil.three_app_id);
//            // 第三方收到公众号平台发送的消息
            String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, postData);
            Element root = XMLParse.getContentByKey(result2);
            String question = root.getElementsByTagName("Content").item(0).getTextContent();
            String FromUserName = root.getElementsByTagName("FromUserName").item(0).getTextContent();
            String toUserName = root.getElementsByTagName("ToUserName").item(0).getTextContent();

//根据appId查找对应的图书馆
            WechatThreeAuthorize authorize = getWechatThreeAuthorize(appId);
//        判断是否开启微信公众号智能咨询
            if (authorize == null || authorize.getStatus() == 0) {
                printXMl(response, "success");
                return;
            }

            RobotConfigExample configExample = new RobotConfigExample();
            configExample.createCriteria().andLibraryIdEqualTo(authorize.getLibraryId()).andStatusEqualTo(1);
            RobotConfig config = configService.selectByExample(configExample).stream().findFirst().orElse(null);
            Random random = new Random();
            TuLingData tuLingData = TuLingUtil.getData(config.getApikey(), String.valueOf(authorize.getLibraryId()+random.nextInt(500)+1), question);
            String df = HttpUtil.sendPost(TuLingUtil.openapi, JSON.toJSONString(tuLingData));
            TuLing tuLings = JSON.parseObject(df, TuLing.class);

//            WechatThreeAuthorize authorizet = getWechatThreeAuthorize(appId);
            String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + authorize.getAuthorizerAccessToken();

            String[] answer = TuLingUtil.getAnswer(tuLings.getResults());
            String content = "";
            if ("text".equals(answer[1])) {
                content = answer[0];
            } else {
                content = "宝宝不想说话，宝宝累了，让宝宝睡会！";
//                LogUtil.log(content);
//                        上传素材库
//                        Map<String, String> textMap = new HashMap<>();
//                        textMap.put("access_token", authorizet.getAuthorizerAccessToken());
//                        textMap.put("type", "image");
//                        Map<String, String> fileMap = new HashMap<>();
//                        fileMap.put("media", answer[0]);
//            //            fileMap.put("media", "D:\\123.jpg");
//                        String MEDIA_JSON = formUpload("https://api.weixin.qq.com/cgi-bin/media/upload", textMap,
//                                fileMap);
//                        JSONObject jsonObject = new JSONObject(MEDIA_JSON);
//                        messageToUser = "{\"touser\":\""+openid+"\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"" + jsonObject.getString("media_id") + "\" }}";

            }

            if (!"".equals(answer[1])) {
                String messageToUser = "{\"touser\":\"" + openid + "\", \"msgtype\":\"text\", \"text\":{ \"content\":\"" + content + " \"} }";
                //        发送消息
                HttpUtil.sendPost(url, messageToUser);
                //       对用户的咨询进行记录
                RobotConsultRecord record = new RobotConsultRecord();
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                record.setStatus(1);
                record.setLibraryId(authorize.getLibraryId());
                record.setQuestion(question);
                record.setAnswer(answer[0]);
                record.setCardNumber(openid);
                record.setSource("WeChat");
                RobotConsultRecord bean = robotConsultRecordService.add(record);
            }

//            String msg = "<xml> <ToUserName>< ![CDATA[%1$s] ]></ToUserName> <FromUserName>< ![CDATA[%2$s] ]></FromUserName> <CreateTime>%3$s</CreateTime> <MsgType>< ![CDATA[text] ]></MsgType> <Content>< ![CDATA[%4$s] ]></Content> </xml>";
//
//            //       用户的消息回复模板
//            String xml = pc.encryptMsg(String.format(msg, FromUserName, toUserName, "1528090850", "你好啊！"), "1528090850", "482896547");
//            LogUtil.log(xml);

//            printXMl(response,xml);
        } catch (Exception e) {
            LogUtil.log("第三方抛异常了~快来看看吧！");
        } finally {
            printXMl(response, "success");
        }
    }


    //返回XML
    public void printXMl(HttpServletResponse response, String xml) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-type", "text/xml");
            writer = response.getWriter();
            writer.print(xml);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    //根据授权公众号获得公众号的相关信息
    public WechatThreeAuthorize getWechatThreeAuthorize(String authorizerAppid) {
        WechatThreeAuthorizeExample authorizeExample = new WechatThreeAuthorizeExample();
        authorizeExample.createCriteria().andAuthorizerAppidEqualTo(authorizerAppid);
        return authorizeService.selectByExample(authorizeExample).stream().findFirst().orElse(null);
    }

}
