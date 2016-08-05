package com.yonyou.lxp.lxp_utils.listener;

import android.view.View;

/**
 * 作者： liuxiaopeng on 16/6/28.
 * 描述：
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {

    }

    public abstract void onNoDoubleClick(View v);
}
