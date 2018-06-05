package com.uroad.rxhttp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    /**
     * 将Json数据转化为对象;
     *
     * @param json Json数据;
     * @param cls  转换后的类;
     */
    public static <T> T getObject(String json, Class<T> cls) {
        T t;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            return null;
        }
        return t;
    }

    /**
     * 将Json数据转化成List<Object>集合;
     *
     * @param json Json数据;
     * @param cls  将要转化成集合的类;
     */
    public static <T> List<T> getArray(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
            return list;
        }
        return list;
    }


    /**
     * 将Json数据转化成List<Map<String, Object>>对象;
     *
     * @param json Json数据;
     */
    public static List<Map<String, Object>> listKeyMaps(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    /**
     * 将Json数据转化成Map<String, Object>对象;
     *
     * @param json Json数据;
     */
    public static Map<String, Object> objKeyMaps(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            Gson gson = new Gson();
            map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            return map;
        }
        return map;
    }
}
