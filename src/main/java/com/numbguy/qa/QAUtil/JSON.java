package com.numbguy.qa.QAUtil;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JSON {
    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for(Map.Entry<String, Object> entry:map.entrySet())
        {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}
