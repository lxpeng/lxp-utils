package com.yonyou.lxp.lxp_utils.net;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yonyou.lxp.lxp_utils.utils.GsonUtils;
import com.yonyou.lxp.lxp_utils.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者： liuxiaopeng on 16/6/28.
 * 描述：网络请求
 */

public class Http {

    private static Gson gson = new Gson();
    private static String HTTP_DOMAIN_NAME = "";
    private static String HTTP_URL = "";

    private static final String TAG = "Http";

    /**
     * @param paramsMap 参数 必传 HTTP_URL 及 HTTP_DOMAIN_NAME
     * @param clazzMap  需要转化的实体类或List
     * @param callBack  回调函数
     * @return 返回字符串及转化的数据 如果传入clazzMap为null 返回的mapData也为null
     */
    public static Call<String> post(ParamsMap paramsMap, final Map<String, Type> clazzMap, final HttpCallBack callBack) {
        Retrofit retrofit = getRetrofit(paramsMap);
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendPost(HTTP_URL, paramsMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Logger.d("网络请求成功:\n " + call.request().url().toString());
                Logger.json(response.body());
                HashMap<String, Object> mapData = new HashMap<>();
                if (clazzMap != null && clazzMap.keySet().size() > 0) {
                    for (String key : clazzMap.keySet()) {
                        try {
                            String jsonItem = JsonUtils.getJsonStr(response.body(), key);
                            if (key != null && jsonItem != null && !"".equals(jsonItem) && !"".equals(key)) {
                                mapData.put(key, gson.fromJson(jsonItem, clazzMap.get(key)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.isSuccess(response.body(), mapData);
                } else {
                    callBack.isSuccess(response.body(), null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.e(t, "网络请求失败:\n " + call.request().url().toString());
                callBack.onFailure(call, t);
            }
        });
        return call;
    }

    /**
     * POST  BODY 请求
     *
     * @param paramsMap 参数 必传 HTTP_URL 及 HTTP_DOMAIN_NAME
     * @param callBack  回调
     * @param body      多个body
     * @return 返回Call
     */
    public static Call<String> postBody(ParamsMap paramsMap, final HttpCallBack callBack, String... body) {
        Retrofit retrofit = getRetrofit(paramsMap);
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendPostBody(HTTP_URL, body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Logger.d("网络请求成功:\n " + call.request().url().toString());
                Logger.json(response.body());
                HashMap<String, Object> mapData = new HashMap<>();
                callBack.isSuccess(response.body(), null);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.e(t, "网络请求失败:\n " + call.request().url().toString());
                callBack.onFailure(call, t);
            }
        });

        return call;
    }

    /**
     * get请求 参数 必传 HTTP_URL 及 HTTP_DOMAIN_NAME
     *
     * @param paramsMap 参数
     * @param callBack  回调
     * @return CALL
     */
    public static Call<String> get(ParamsMap paramsMap, final HttpCallBack callBack) {
        Retrofit retrofit = getRetrofit(paramsMap);
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendGet(HTTP_URL, paramsMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Logger.d("网络请求成功:\n " + call.request().url().toString());
                Logger.json(response.body());
                HashMap<String, Object> mapData = new HashMap<>();
                callBack.isSuccess(response.body(), null);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.e(t, "网络请求失败:\n " + call.request().url().toString());
                callBack.onFailure(call, t);
            }
        });

        return call;
    }

    /**
     * 获取Retrofit实例
     *
     * @return 获取Retrofit实例
     */
    private static Retrofit getRetrofit(ParamsMap paramsMap) {
        HTTP_URL = paramsMap.getHTTP_URL();
        HTTP_DOMAIN_NAME = paramsMap.getHTTP_DOMAIN_NAME();

        if (null == HTTP_DOMAIN_NAME || "".equals(HTTP_DOMAIN_NAME)) {
            Logger.e("请配置域名地址");
            return null;
        }
        if (null == HTTP_URL || "".equals(HTTP_URL)) {
            Logger.e("请配置访问Action");
            return null;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTP_DOMAIN_NAME)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 回调接口
     */
    public interface HttpCallBack {
        void isSuccess(String data, Map<String, Object> mapData);

        void onFailure(Call<String> call, Throwable t);

    }

    /**
     * 获取Body的JsonStr
     *
     * @param obj Body
     * @return Json字符串
     */
    public static String getBodyJsonStr(Object obj) {
        if (obj != null) {
            return gson.toJson(obj);
        }
        return "";
    }


    /**
     * @param jsonData jsonString数据
     * @param clazzMap 解析后的map
     * @return  Map<String, Object>
     */
    public static Map<String, Object> getDataMap(String jsonData, Map<String, Type> clazzMap) {
        HashMap<String, Object> mapData = new HashMap<>();
        mapData.put("jsonData", jsonData);
        if (clazzMap != null && clazzMap.keySet().size() > 0) {
            for (String key : clazzMap.keySet()) {
                try {
                    String jsonItem = JsonUtils.getJsonStr(jsonData, key);
                    if (key != null && jsonItem != null && !"".equals(jsonItem) && !"".equals(key)) {
                        mapData.put(key, gson.fromJson(jsonItem, clazzMap.get(key)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mapData;
    }

}
