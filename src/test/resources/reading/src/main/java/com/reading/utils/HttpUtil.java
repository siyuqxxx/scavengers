package com.reading.utils;


import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by change on 2016/7/11.
 * http请求
 */
public class HttpUtil {

    public static String timeOutMessage = "图书馆接口访问超时！";

    static int timeOut = 15000;

    /**
     * get请求
     *
     * @param url 网址
     * @return 结果
     */
    public static String httpGet(String url) {
        HttpResponse response = HttpRequest
                .get(url).connectionTimeout(timeOut)
                .queryEncoding("UTF-8")
                .send();

        return response.bodyText();
    }

    /**
     * Post请求
     *
     * @param url
     * @return
     */
    public static String httpPost(String url) {
        HttpResponse response = HttpRequest
                .post(url).connectionTimeout(timeOut)
                .queryEncoding("UTF-8")
                .send();
        return response.bodyText();
    }

    /**
     * Post请求
     *
     * @param url
     * @return
     */
    public static String httpPostWithParameter(String url, Map <String, Object> parameter) {
        HttpResponse response = HttpRequest
                .post(url).connectionTimeout(timeOut)
                .queryEncoding("UTF-8")
                .form(parameter)
                .send();

        return response.bodyText();
    }


    /**
     * get请求
     *
     * @param url 网址
     * @return 结果
     */
    public static String httpGetMessage(String url) {
        HttpResponse response = HttpRequest
                .get(url).connectionTimeout(timeOut)
                .queryEncoding("GB2312")
                .send();
        return response.bodyText();
    }

    /**
     * @param url
     * @param Params
     * @return
     * @throws IOException
     * @作用 使用urlconnection
     */
    public static String sendPost(String url, String Params) throws IOException {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream(), "UTF-8");
            out.write(Params);
            out.flush();


            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "utf-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes());
                response += lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        LogUtil.log(response + "============");
        return response;
    }
}