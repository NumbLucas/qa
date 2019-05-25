package com.numbguy.qa.async;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.numbguy.qa.QAUtil.JedisAdapter;
import com.numbguy.qa.QAUtil.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;
    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null) {
            for(Map.Entry<String, EventHandler> entry:beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType eventType:eventTypes) {
                    if(!config.containsKey(eventType)) {
                        config.put(eventType, new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String key = RedisKeyUtil.getEventqueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for(String event :events) {
                        if(event.equals(key)) {
                            continue;
                        }

                        EventModel eventModel = JSON.parseObject(event, EventModel.class);
                        if(!config.containsKey(eventModel.getEventType())) {
                            System.out.println("不能识别的事件");
                            continue;
                        }
                        for(EventHandler handler:config.get(eventModel.getEventType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
