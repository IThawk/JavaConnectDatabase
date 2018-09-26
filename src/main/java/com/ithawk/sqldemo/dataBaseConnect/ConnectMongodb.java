package com.ithawk.sqldemo.dataBaseConnect;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSONException;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

/***
 *
 * 这个连接MongoDB的类
 * @author IThawk
 */
public class ConnectMongodb {
    /***
     * 没有密码的连接情况
     */
    public void testMongodb() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
            Document result = mongoDatabase.runCommand(new BasicDBObject("serverStatus", Boolean.TRUE));
//            while (true) {
//             DB ds = mongoClient.getDB("admin");
            System.out.println("lianjie");
            // CommandResult result = ds.command("serverStatus");
            // result.toMap();
               /* HashMap<Object, String> map = (HashMap<Object, String>) result.toMap();
                map.forEach((key, value) -> {
                    System.out.println(value);
                });
                System.out.println(map.size());
                */
            Map<String, Object> map = result;
            String json = result.toJson();
            JSONObject json1 = new JSONObject();
            Stack<JSONObject> stObj = new Stack<JSONObject>();
            stObj.push(json1);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            JsonToMap(stObj, resultMap);
            resultMap.forEach((key, value) -> {
                System.out.println(key + ":" + value);
            });
            System.out.println();
            Thread.sleep(6000);
//            }

//                ds.command("serverStatus");
//                mongoDatabase.runCommand("serverStatus");
            // System.out.println("Connect to database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static void JsonToMap(Stack<JSONObject> stObj, Map<String, Object> resultMap) throws IOException, JSONException {

        if (stObj == null && stObj.pop() == null) {
            return;
        }
        JSONObject json = stObj.pop();
        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            //得到value的值
            Object value = json.get(key);
            //System.out.println(value);
            if (value instanceof JSONObject) {
                stObj.push((JSONObject) value);
                //递归遍历
                JsonToMap(stObj, resultMap);
            } else {
                resultMap.put(key, value);
            }
        }
    }


    /**
     * 创建mongoDB数据库连接
     *
     * @return  mogodb连接
     */
    public Mongo mongoDBConnect() {

        //设置超时连接
        MongoClientOptions.Builder build = null;
        try {
            build = new MongoClientOptions.Builder();
            build.serverSelectionTimeout(2000);
            //设置连接池与数据库的最大连接数 默认为100
            build.connectionsPerHost(20);
            //如果当前连接都在使用中，则每个连接上可以有1个线程在等待  默认值为5
            build.threadsAllowedToBlockForConnectionMultiplier(1);
            build.connectTimeout(2000);
            //空闲时间之后,会转化可用的连接
            build.maxWaitTime(1000);
            build.socketTimeout(1000);

            //设置连接池最大空闲时间, 入库模块每5秒调用一次,超过这个时间之后,线程池将会回收连接
            build.maxConnectionIdleTime(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MongoClientOptions options = null;
        if (build != null) {
            options = build.build();
        }

        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("127.0.0.1", 27017);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);
        String password = "123456";
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin",
                password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs, credentials, options);

        try {
            DB db = mongoClient.getDB("admin");
            // 连接到数据库
            // MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
            db.command("serverStatus");
            return mongoClient;
        } catch (Exception e) {

            mongoClient.close();
            //参考博客的说法，连接池关闭但是连接还在，赋值为null关闭连接
            mongoClient = null;

        }
        return null;
    }
}


