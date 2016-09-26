package com.yonyou.lxp.lxp_utils.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author liuxiaopeng
 * @version V1.0.0
 * @time 16/9/22
 * @describe
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
