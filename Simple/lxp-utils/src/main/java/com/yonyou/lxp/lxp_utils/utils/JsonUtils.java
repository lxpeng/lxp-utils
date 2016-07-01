package com.yonyou.lxp.lxp_utils.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者： liuxiaopeng on 16/7/1.
 * 描述：封装简单的JSON解析
 */

public class JsonUtils {
    /**
     * @param result 返回值
     * @param key    key值
     * @return 判断集合是否有数据
     */
    public static boolean isArrayThereData(String result, String key) {
        boolean is = false;
        try {
            JSONObject object = new JSONObject(result);
            if (object.has(key)) {
                String data = Tools.getJsonStr(result, key);
                if ("".equals(data)) {
                    return false;
                }
                JSONArray array = new JSONArray(data);
                if (array.length() > 0) {
                    is = true;
                }
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * @param result json
     * @param key    条数的字段  字段为int型
     * @return 判断 是否有数据 返回
     */
    public static boolean isThereData(String result, String key) {
        boolean is = false;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if ("".equals(jsonObject.get(key) + "")) {
                return false;
            }
            int number = Integer.parseInt(jsonObject.get(key) + "");
            if (number > 0) {
                is = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is;
    }


    /**
     * @param result       json
     * @param key          key
     * @param SuccessValue 成功value
     * @return 判断 请求数据是否成功
     */
    public static boolean isSuccess(String result, String key, String SuccessValue) {
        boolean is = false;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                String code = jsonObject.getString(key);
                is = SuccessValue.equals(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * @param result 返回值
     * @param key    key值
     * @return json是否存在key
     */
    public static boolean isHasKey(String result, String key) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param result 返回值json
     * @param key    key值
     * @return 获取jsonStr的某个字段  只限第一层
     */
    public static String getJsonStr(String result, String key) {
        String jsonStr = "";
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                jsonStr = jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
