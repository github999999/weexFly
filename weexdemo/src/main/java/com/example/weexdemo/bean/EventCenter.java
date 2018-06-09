package com.example.weexdemo.bean;

/**
 * Created by Administrator on 2018/5/24.
 */

public class EventCenter<T> {
    private String type;
    private T obj;
//    private static EventCenter eventCenter = new EventCenter();

//    private EventCenter() {
//    }
//
//    public static EventCenter getInstence(){
//        return eventCenter;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public EventCenter(String type, T obj) {
        this.type = type;
        this.obj = obj;
    }
}
