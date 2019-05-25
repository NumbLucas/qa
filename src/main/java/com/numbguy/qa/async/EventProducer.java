package com.numbguy.qa.async;

import com.alibaba.fastjson.JSONObject;
import com.numbguy.qa.QAUtil.JedisAdapter;
import com.numbguy.qa.QAUtil.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventqueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e) {
            return false;
        }

    }
}
