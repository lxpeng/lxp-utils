package com.yonyou.lxp.lxp_utils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by Administrator on 2015/7/6.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    public static View mConvertView;
    private Context mContext;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, View v) {
        super(v);
        this.mViews = new SparseArray<View>();
        // setTag
        mConvertView.setTag(this);
        this.mContext = context;
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (mConvertView == null) {
            mConvertView = convertView;
            return new ViewHolder(context, parent, layoutId, mConvertView);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(mContext).load(url).into(view);
        return this;
    }


    /**
     * 设置布局隐藏或显示
     *
     * @param viewId
     * @param GONE
     * @return
     */
    public ViewHolder setViewHide(int viewId, int GONE) {
        getView(viewId).setVisibility(GONE);
        return this;
    }

    /**
     * 设置字体颜色 textview
     *
     * @param viewId
     * @param colorid 颜色ID
     * @return
     */
    public ViewHolder setTextColor(int viewId, int colorid) {
        TextView view = getView(viewId);
        view.setTextColor(colorid);
        return this;
    }


    public ViewHolder setBackgroundResource(int viewId, int id) {
        View view = getView(viewId);
        view.setBackgroundResource(id);
        return this;
    }

    /**
     * 点击事件
     *
     * @param viewId
     * @param myOnClick
     */
    public void setOnlick(int viewId, final OnClick myOnClick) {
        View view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClick.OnClick();
            }
        });
    }

    public interface OnClick {
        public void OnClick();
    }

}