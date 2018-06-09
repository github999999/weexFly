package com.example.weexdemo.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

@SuppressLint("PrivateApi")
public class MapUtil {

    public static Map<String, Object> convertToMap(JSONObject json) {
        Map<String, Object> params = null;
        try{
            if (json != null) {
                params = new HashMap<>(json.length());
                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        params.put(key, json.get(key));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static Map<String, Object> convertToMap(String json) {
        Map<String, Object> stringObjectMap = null;
        if (!TextUtils.isEmpty(json)) {
            try{
                JSONObject object = new JSONObject(json);
                stringObjectMap = convertToMap(object);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return stringObjectMap;
    }

    public static JSONObject convertToJson(Map<String, Object> map) {
        if (map != null) {
            try{
                return new JSONObject(map);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反射获取application对象
     * @return application
     */
    public static Application initApplication() {
        try {
            //通过反射的方式来获取当前进程的application对象
            Application app = (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null);
            if (app != null) {
                return app;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            Application app = (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication").invoke(null);
            if (app != null) {
                return app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
