package com.yonyou.lxp.lxp_utils.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuxiaopeng on 16/1/31.
 */
public class Tools {
    private static JSONObject jsonObject;

    /**
     * 判断 请求数据是否成功
     * @param result json字符串
     * @param key 判定成功的key
     * @param value 成功的 value
     * @return 判断 请求数据是否成功
     */
    public static boolean isSuccess(String result,String key,String value) {
        boolean is = false;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                String code = jsonObject.getString(key);
                is = value.equals(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 获取jsonStr的某个字段  只限第一层
     * @param result json字符串
     * @param key key值
     * @return 获取jsonStr的某个字段  只限第一层
     */
    public static String getJsonStr(String result, String key) {
        String jsonStr = "";
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                jsonStr = jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
