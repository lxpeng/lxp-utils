package com.yonyou.lxp.lxp_utils.view;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.yonyou.lxp.lxp_utils.listener.NoDoubleClickListener;

/**
 * @author liuxiaopeng
 * @version V1.0.0
 * @time 16/9/21
 * @describe 可设置空白页的RecyclerView
 */

public class SupportRecyclerView extends RecyclerView {
    private View mEmptyView;

    private View mErrorView;

    private View mNoDataView;

    private View mNoDataloadView;

    private boolean isError = false;

    private int mVisibility;

    final private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyView();
        }
    };

    public SupportRecyclerView(Context context) {
        super(context);
        mVisibility = getVisibility();
    }

    public SupportRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mVisibility = getVisibility();
    }

    public SupportRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVisibility = getVisibility();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        updateEmptyView();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mVisibility = visibility;
        updateErrorView();
        updateEmptyView();
    }

    /**
     * 刷新空白页面状态 是否显示
     */
    public void updateEmptyView() {
        if (mEmptyView != null && getAdapter() != null) {
            boolean isShowEmptyView = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(isShowEmptyView && !shouldShowErrorView() && (mVisibility == VISIBLE) ? VISIBLE : GONE);
            setmNoDataType(1);
            mNoDataView.setVisibility(View.VISIBLE);
            super.setVisibility((!isShowEmptyView && !shouldShowErrorView() && (mVisibility == VISIBLE)) ? VISIBLE : INVISIBLE);
        }
    }

    /**
     * 跟新状态是否展示错误页面
     */
    private void updateErrorView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
        }
    }

    private boolean shouldShowErrorView() {
        if (mErrorView != null && isError) {
            return true;
        }
        return false;
    }


    /**
     * 切换无数据页面状态
     *
     * @param type 0 显示加载页面 1显示无数据页面
     */
    private void setmNoDataType(int type) {
        if (type == 0) {
            if (null != mNoDataView) {
                mNoDataView.setVisibility(View.GONE);
            }
            if (null != mNoDataloadView) {
                mNoDataloadView.setVisibility(View.VISIBLE);
            }
        } else if (type == 1) {
            if (null != mNoDataView) {
                mNoDataView.setVisibility(View.VISIBLE);
            }
            if (null != mNoDataloadView) {
                mNoDataloadView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置空白页面
     * 如果该页面存在两个子view则可自动切换
     * view_nodata 无数据时显示
     * view_loading 无数据时点击则刷新 然后现象加载的加载页面
     *
     * @param emptyView 添加空白页面
     */
    public void setEmptyView(View emptyView, @Nullable @IdRes int view_nodata, @Nullable @IdRes int view_loading) {
        mEmptyView = emptyView;
        mNoDataView = mEmptyView.findViewById(view_nodata);
        mNoDataloadView = mEmptyView.findViewById(view_loading);

        mEmptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (null != mEmptyViewOnClickListener) {
                    setmNoDataType(0);
                    mEmptyViewOnClickListener.onEmptyViewOnClick();
                }
            }
        });


        updateEmptyView();
    }

    /**
     * 设置空白页面
     * 如果该页面存在两个子view则可自动切换
     *
     * @param emptyView 添加空白页面
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        mEmptyView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (null != mEmptyViewOnClickListener) {
                    setmNoDataType(0);
                    mEmptyViewOnClickListener.onEmptyViewOnClick();
                }
            }
        });


        updateEmptyView();
    }

    /**
     * 设置错误页面
     *
     * @param errorView 添加错误页面
     */
    public void setErrorView(View errorView) {
        mErrorView = errorView;
        mErrorView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (null != mErroViewOnClickListener) {
                    mErroViewOnClickListener.onErroViewOnClick();
                }
            }
        });
        updateErrorView();
        updateEmptyView();
    }

    /**
     * 显示错误界面
     */
    public void showErrorView() {
        isError = true;
        updateErrorView();
        updateEmptyView();
    }

    /**
     * 隐藏错误界面
     */
    public void hideErrorView() {
        isError = false;
        updateErrorView();
        updateEmptyView();
    }

    /**
     * 设置空白页面的点击事件
     *
     * @param mEmptyViewOnClickListener 空白页面点击事件
     */
    public void setEmptyViewOnClickListener(EmptyViewOnClickListener mEmptyViewOnClickListener) {
        this.mEmptyViewOnClickListener = mEmptyViewOnClickListener;
    }

    /**
     * 设置错误页面点击事件
     *
     * @param mErroViewOnClickListener 错误页面点击事件
     */
    public void setErroViewOnClickListener(ErroViewOnClickListener mErroViewOnClickListener) {
        this.mErroViewOnClickListener = mErroViewOnClickListener;
    }

    private EmptyViewOnClickListener mEmptyViewOnClickListener;
    private ErroViewOnClickListener mErroViewOnClickListener;

    public interface EmptyViewOnClickListener {
        void onEmptyViewOnClick();
    }

    public interface ErroViewOnClickListener {
        void onErroViewOnClick();
    }


}