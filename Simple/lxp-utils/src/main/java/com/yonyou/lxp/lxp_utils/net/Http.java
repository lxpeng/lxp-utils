package com.yonyou.lxp.lxp_utils.net;


import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yonyou.lxp.lxp_utils.Bean;
import com.yonyou.lxp.lxp_utils.utils.AppUtils;
import com.yonyou.lxp.lxp_utils.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * 作者： liuxiaopeng on 16/6/28.
 * 描述：网络请求
 */

public class Http {
    private HashMap<String, Object> map = new HashMap<String, Object>();
    private static Gson gson = new Gson();
    private String HTTP_DOMAIN_NAME = "";

    public void setHTTP_DOMAIN_NAME(String HTTP_DOMAIN_NAME) {
        this.HTTP_DOMAIN_NAME = HTTP_DOMAIN_NAME;
    }

    private static final String TAG = "Http";

    /**
     * @param url      要请求的方法
     * @param clazzMap 需要转化的实体类或List
     * @param callBack 回调函数
     * @return 返回字符串及转化的数据 如果传入clazzMap为null 返回的mapData也为null
     */
    public Call<String> post(String url, final Map<String, Type> clazzMap, final HttpCallBack callBack) {
        Retrofit retrofit = getRetrofit();
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendPost(url, map);
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
     * @param url      url
     * @param callBack 回调
     * @param body     多个body
     * @return
     */
    public Call<String> postBody(String url, final HttpCallBack callBack, String... body) {
        Retrofit retrofit = getRetrofit();
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendPostBody(url, body);
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
     * get 请求
     *
     * @param url      url
     * @param callBack 回调
     * @return
     */
    public Call<String> get(String url, final HttpCallBack callBack) {
        Retrofit retrofit = getRetrofit();
        HttpService service = retrofit.create(HttpService.class);
        Call<String> call = service.sendGet(url, map);
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
    private Retrofit getRetrofit() {
        if (null == HTTP_DOMAIN_NAME || HTTP_DOMAIN_NAME.equals("")) {
            Logger.e(TAG, "请配置HTTP_DOMAIN_NAME,通过setHTTP_DOMAIN_NAME配置");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTP_DOMAIN_NAME)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 增加一个参数
     *
     * @param key   stringKey
     * @param value 数值
     */
    public void addParams(String key, Object value) {
        map.put(key, value);
    }

    /**
     * 每次新的请求前,请务必执行此方法,再添加参数啊
     */
    public void clearParameter() {
        map.clear();
    }


    /**
     * 回调接口
     */
    public interface HttpCallBack {
        void isSuccess(String data, Map<String, Object> mapData);

        void onFailure(Call<String> call, Throwable t);

    }

    /**
     * 回调接口
     */
    public interface HttpCallRxBack<T> {
        void isSuccess(T backInfo);

        void onFailure(Throwable t);
    }


    /**
     * 获取Body的JsonStr
     *
     * @param obj Body
     * @return Json字符串
     */
    public String getBodyJsonStr(Object obj) {
        if (obj != null) {
            return gson.toJson(obj);
        }
        return "";
    }


    /**
     * @param jsonData jsonString数据
     * @param clazzMap 解析后的map
     * @return
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
