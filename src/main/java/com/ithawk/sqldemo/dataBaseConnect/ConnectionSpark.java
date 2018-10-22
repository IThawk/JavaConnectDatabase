package com.ithawk.sqldemo.dataBaseConnect;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

    /**
     * 描述: spark监控连接
     *
     * @author IThawk
     * @create 2018-10-16 15:54
     */
    public class ConnectionSpark {

        public static String getTrace(Throwable t) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            t.printStackTrace(writer);
            StringBuffer buffer = stringWriter.getBuffer();
            return buffer.toString();
        }

        public static void main(String[] args) throws Exception {

            getWeatherInform("七台河"); //1表示开始获取数据

        }

        /* *
         * 描述: 先获取application的数据,取 id 拼接在获取allexecutors的数据
         * @params
         * @return int
         * @author czwei
         * @date 2018/10/18 10:59
         */
        public static int getWeatherInform(String cityName) {
            int state = -1;
            String mytext = null;
            try {
                mytext = java.net.URLEncoder.encode(cityName, "utf-8");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String PM2_5Url = "http://172.19.1.131:4040/api/v1/applications"; //  公共key
            System.out.println("=== " + cityName + " start! ===");
            int checkNum;
            StringBuffer strBuf;
            strBuf = new StringBuffer();
            try {
                URL url = new URL(PM2_5Url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();//设置网络获取时间间隔，超出时间后跳出
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                state = conn.getResponseCode();
                if (state == 200) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(),
                                    "utf-8"));// 转码。
                    String line = null;
                    while ((line = reader.readLine()) != null)
                        strBuf.append(line + " ");
                    System.out.println("connect success");
                    reader.close();
                } else {
                    System.out.println("connect false" + cityName);
                    return 0;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ConnectException ce) {
                ce.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取到的数据通过json格式转换
            String json = strBuf.toString();//判断获取的数据
            System.out.println(json);
            Map<String,Object> result = new HashMap<>();

            // 获取id 的值
            String jsResult = json.substring(2, json.length() - 2);
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsResult);
            String r =jsonObject.getString("id");
            if (r != null && r.length() != 0) {
                // 拼接获取allexecutors
                String PM2_5Url2 = "http://172.19.1.131:4040/api/v1/applications/" + r + "/allexecutors"; //  公共key
                System.out.println("=== " + cityName + " start! ===");
                int checkNum2;
                StringBuffer strBuf2;
                strBuf2 = new StringBuffer();
                try {
                    URL url = new URL(PM2_5Url2);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();//设置网络获取时间间隔，超出时间后跳出
                    conn.setConnectTimeout(30000);
                    conn.setReadTimeout(30000);
                    state = conn.getResponseCode();
                    if (state == 200) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(conn.getInputStream(),
                                        "utf-8"));// 转码。
                        String line = null;
                        while ((line = reader.readLine()) != null)
                            strBuf2.append(line + " ");
                        System.out.println("connect success");
                        reader.close();
                    } else {
                        System.out.println("connect false" + cityName);
                        return 0;
                    }
                    String jsResult1 = strBuf2.toString().substring(2, strBuf2.toString().length() - 2);
                    com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(jsResult1);
                    jsonObject1.forEach((key,value)->{
                        System.out.println(key +":"+value);
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ConnectException ce) {
                    ce.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取到的数据通过json格式转换
                String json2 = strBuf2.toString();
                //判断获取的数据
                System.out.println(json2);

            }
            // System.out.println(json);
            System.out.println(cityName + ", The end!!!");
            return 6;
        }

        public static String changeString(String str) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 65 && str.charAt(i) <= 90 || str.charAt(i) >= 97 && str.charAt(i) <= 122 || str.charAt(i) >= 46 && str.charAt(i) <= 57) {
                    sb.append(str.charAt(i));
                }
            }
            return sb.toString();
        }


    }



