package com.yonyou.lxp.lxp_utils.net;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： liuxiaopeng on 16/8/10.
 * 描述：网络请求参数
 */

public class ParamsMap extends HashMap<String, Object> {
    private String HTTP_DOMAIN_NAME = "HTTP_DOMAIN_NAME";
    private String HTTP_URL = "HTTP_URL";

    public ParamsMap(@NonNull String HTTP_DOMAIN_NAME, @NonNull String HTTP_URL) {
        this.clear();
        this.HTTP_DOMAIN_NAME = HTTP_DOMAIN_NAME;
        this.HTTP_URL = HTTP_URL;
    }

    public String getHTTP_DOMAIN_NAME() {
        return HTTP_DOMAIN_NAME;
    }

    public String getHTTP_URL() {
        return HTTP_URL;
    }

    @Override
    public ParamsMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
