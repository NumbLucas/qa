package com.numbguy.qa.async;

public enum EventType {
    LIKE("like", 0),
    COMMENT("comment",1),
    LOGIN("login", 2),
    MAIL("mail", 3);
    private int value;
    private String name;
    EventType(String name, int value) { this.value = value; this.name = name; }

    public static String getName(int value) {
        for(EventType eventType:EventType.values()) {
            if(value == eventType.getValue()) {
                return eventType.name;
            }
        }
        return null;
    }

    public static int getValue(String name) {
        for(EventType eventType:EventType.values()) {
            if(name.equals(eventType.getName())) {
                return eventType.value;
            }
        }
        return  -1;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static  void main(String[] args) {

    }
}
