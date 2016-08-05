package com.yonyou.lxp.lxp_utils.net;


import android.util.Log;

import com.google.gson.Gson;
import com.yonyou.lxp.lxp_utils.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 描述：
 */

public class Http {
    private HashMap<String, Object> map = new HashMap<String, Object>();
    private static Gson gson = new Gson();
    private static final String HTTP_DOMAIN_NAME = "https://carapptest.gtmc.com.cn/";

    /**
     * @param url      要请求的方法
     * @param clazzMap 需要转化的实体类或List
     * @param callBack 回调函数
     * @return 返回字符串及转化的数据 如果传入clazzMap为null 返回的mapData也为null
     */
    public Call<String> post(String url, final Map<String, Type> clazzMap, final HttpCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTP_DOMAIN_NAME)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        HttpService service = retrofit.create(HttpService.class);

//        Subscription sub = service.sendPost(url, map)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(s -> Log.d("httpJsonData", s))
//                .doOnNext(s -> {
//                    if (!isSuccess(s)) {
//                        Log.e("httpJsonData", getJsonStr(s, "errMsg"));
//                    }
//                })
//                .filter(s -> isSuccess(s))
//                .subscribe(s ->getDataMap(s,clazzMap),throwable -> Log.e("网络错误",throwable.getMessage()));


        Call<String> call = service.sendPost(url, map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Http.onResponse", call.request().url().toString() + "\n" + response.body());
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
                Log.e("Http.onResponse", call.request().url().toString() + "\n" + t.getMessage());
                callBack.onFailure(call, t);
            }
        });

        return call;
    }

    /**
     * 增加一个参数
     *
     * @param key
     * @param value
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
     * @param jsonData
     * @param clazzMap
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
