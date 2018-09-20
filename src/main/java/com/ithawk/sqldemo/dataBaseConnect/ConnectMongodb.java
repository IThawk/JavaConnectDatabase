package com.ithawk.sqldemo.dataBaseConnect;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSONException;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class ConnectMongodb {


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
}


