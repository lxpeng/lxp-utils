package com.yonyou.lxp.lxp_utils.net;

import java.util.Map;

/**
 * 作者： liuxiaopeng on 16/6/30.
 * 描述：
 */

public interface ICallBack<T> {
    /**
     * 回调
     * @param data 数据
     * @param isOk 成功或失败
     */
    public void onResult(T data, Map<String, Object> mapData, boolean isOk);

}
