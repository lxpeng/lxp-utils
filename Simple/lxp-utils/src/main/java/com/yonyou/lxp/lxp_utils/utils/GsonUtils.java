package com.yonyou.lxp.lxp_utils.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuxiaopeng
 * @version V1.0.0
 * @time 16/9/21
 * @describe Gson解析工具类
 */

public class GsonUtils {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 转成list
     *
     * @param gsonString 字符串
     * @param cls 泛型
     * @return 返回List
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     *
     * @param cls 泛型
     * @param <T> 泛型
     * @return Type
     */
    public static <T> Type getListType(Class<T> cls) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return type;
    }

    /**
     *
     * @param cls 泛型
     * @param <T> 泛型
     * @return Gson解析所需类型类型
     */
    public static <T> Type getType(Class<T> cls) {
        Type type = new TypeToken<T>() {
        }.getType();
        return type;
    }

    /**
     * 转成bean
     *
     * @param gsonString json字符串
     * @param cls cls
     * @return 实体类
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成json
     *
     * @param object 实体
     * @return json字符串
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString json字符串
     * @return List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString json字符串
     * @return Map<String, T>
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }


    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json json字符串
     * @param cls 泛型
     * @param <T> 泛型
     * @return List<T>
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }
}
