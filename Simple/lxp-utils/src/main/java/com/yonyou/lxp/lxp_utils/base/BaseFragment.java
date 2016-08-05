package com.yonyou.lxp.lxp_utils.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * 作者： liuxiaopeng on 16/8/5.
 * 描述：
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public BaseActivity mContext;
    public static Snackbar snackbar;

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    public String mParam1;
    public String mParam2;


    /**
     * 当前Fragment渲染的视图View
     **/
    public View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseActivity) getActivity();
        Bundle bundle = getArguments();
        if (null != bundle) {
            initParms(bundle);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = bindView();
        if (null == mView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
        } else {
            mContextView = mView;
        }
        ButterKnife.bind(this, mContextView);
        return mContextView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mContextView);
        doBusiness();
    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        if (null!=mContextView){
            return (T) mContextView.findViewById(resId);
        }
        return null;
    }


    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            widgetClick(v);
        }
    }

    /**
     * [业务操作]
     */
    public abstract void doBusiness();


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mContext, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * [简化Toast]
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (snackbar == null) {
            snackbar = Snackbar.make(mContextView, msg, Snackbar.LENGTH_SHORT);
        } else {
            snackbar.setText(msg);
        }
        snackbar.show();
    }


}
