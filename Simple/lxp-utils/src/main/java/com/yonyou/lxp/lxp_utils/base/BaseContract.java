package com.yonyou.lxp.lxp_utils.base;

/**
 * 作者： liuxiaopeng on 16/8/9.
 * 描述：
 */

public interface BaseContract {
    interface BaseView {
        /**
         * 程序错误返回
         *
         * @param error
         */
        void refreshError(String error);
    }

    interface BasePresenter {

    }
}
