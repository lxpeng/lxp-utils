package com.yonyou.lxp.lxp_utils.net;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 作者： liuxiaopeng on 16/6/28.
 * 描述：
 */

public interface HttpService<T> {
    @POST("/{urlPath}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<String> sendPostBody(@Path(value = "urlPath", encoded = true) String urlPath, @Body String... body);

    @POST("/{urlPath}")
    Call<String> sendPost(@Path(value = "urlPath", encoded = true) String urlPath, @QueryMap Map<String, Object> options);

    @GET("/{urlPath}")
    Call<String> sendGet(@Path(value = "urlPath", encoded = true) String urlPath, @QueryMap Map<String, Object> options);

//    @POST("/{urlPath}")
//    Observable<T> sendRxPost(@Path(value = "urlPath", encoded = true) String urlPath, @QueryMap Map<String, Object> options);

}
