package com.passer.demo.netty.utils;

import com.google.gson.Gson;

/**
 * @author passer
 * @time 2022/11/13 15:38
 */
public class GsonUtils {
    private static final Gson gson = new Gson();

    public static String toJson(Object t) {
        return gson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
