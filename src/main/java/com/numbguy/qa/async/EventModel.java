package com.numbguy.qa.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType eventType;
    private int actorId;
    private int entityType;
    private int getEntityId;
    private int entityOwner;

    private Map<String, String> exts = new HashMap<>();

    public EventModel() {};
    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }
    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getGetEntityId() {
        return getEntityId;
    }

    public EventModel setEntityId(int getEntityId) {
        this.getEntityId = getEntityId;
        return this;
    }

    public int getEntityOwner() {
        return entityOwner;
    }

    public EventModel setEntityOwner(int entityOwner) {
        this.entityOwner = entityOwner;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

}
