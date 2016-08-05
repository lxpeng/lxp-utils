package com.yonyou.lxp.lxp_utils.net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 作者： liuxiaopeng on 16/6/28.
 * 描述：
 */

public interface HttpService {
    @POST("appservice/{urlPath}.json")
    Call<String> sendPost(@Path(value = "urlPath", encoded = true) String urlPath, @QueryMap Map<String, Object> options);

    @GET("appservice/{urlPath}.json")
    Call<String>  sendGet(@Path(value = "urlPath", encoded = true) String urlPath, @QueryMap Map<String, Object> options);
}
