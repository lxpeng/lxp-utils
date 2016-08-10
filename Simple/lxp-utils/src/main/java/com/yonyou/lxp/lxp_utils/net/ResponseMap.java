package com.yonyou.lxp.lxp_utils.net;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 作者： liuxiaopeng on 16/8/10.
 * 描述：返回值
 */
public class ResponseMap extends HashMap<String, Type> {

    @Deprecated
    @Override
    public Type put(String key, Type value) {
        return super.put(key, value);
    }

    public ResponseMap putData(String key, Type value) {
        super.put(key, value);
        return this;
    }
}
