package com.yonyou.lxp.lxp_utils.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yonyou.lxp.lxp_utils.listener.NoDoubleClickListener;
import com.yonyou.lxp.lxp_utils.utils.AppUtils;
import com.yonyou.lxp.lxp_utils.view.FrescoImageView;

/**
 * ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private static View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View v) {
        super(v);
        this.mViews = new SparseArray<View>();
        // setTag
        mConvertView.setTag(this);
        this.mContext = context;
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context     上下文
     * @param convertView 父控件
     * @param parent      父控件
     * @param layoutId    itemID
     * @return ViewHolder
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            return new ViewHolder(context, mConvertView);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 获取父类View
     *
     * @return mConvertView
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId ID
     * @return View
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
     * @param viewId TextViewID
     * @param text   context
     * @return ViewHolder
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId     SimpleDraweeViewID
     * @param drawableId drawableId
     * @return ViewHolder
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        SimpleDraweeView view = getView(viewId);
        Uri uri = Uri.parse("res://" + AppUtils.getPagerName(mContext) + "/" + drawableId);
        view.setImageURI(uri);
        return this;
    }

//    /**
//     * 为ImageView设置图片
//     *
//     * @param viewId bm
//     * @return
//     */
//    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
//        SimpleDraweeView view = getView(viewId);
//        Fresco.
//        DraweeController draweeController =Fresco.newDraweeControllerBuilder().build();
//        view.setController(draweeController);
//        return this;
//    }

    /**
     * 为ImageView设置图片
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     * 本地文件	file://	FileInputStream
     * Content provider	content://	ContentResolver
     * asset目录下的资源	asset://	AssetManager
     * res目录下的资源	res://	Resources.openRawResource
     *
     * @param viewId ID
     * @param url    图片路径
     * @return ViewHolder
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        SimpleDraweeView view = getView(viewId);
        Uri uri = Uri.parse(url);
        view.setImageURI(uri);
        return this;
    }

    /**
     * 为SimpleDraweeView设置图片
     *
     * @param viewId ViewId
     * @param url    URL
     * @return ViewHolder
     */
    public ViewHolder setImageGifByUrl(int viewId, String url) {
        FrescoImageView view = getView(viewId);
        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        view.setImageGifURI(uri,controller);
        return this;
    }

    /**
     * 为SimpleDraweeView设置图片
     *
     * @param viewId     ViewId
     * @param drawableId ResourceID
     * @return ViewHolder
     */
    public ViewHolder setImageGifResource(int viewId, int drawableId) {
        FrescoImageView view = getView(viewId);
        Uri uri = Uri.parse("res://" + AppUtils.getPagerName(mContext) + "/" + drawableId);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        view.setImageGifURI(uri,controller);
        return this;
    }

    /**
     * 设置布局隐藏或显示
     *
     * @param viewId ViewID
     * @param GONE   显示隐藏
     * @return ViewHolder
     */
    public ViewHolder setViewHide(int viewId, int GONE) {
        getView(viewId).setVisibility(GONE);
        return this;
    }

    /**
     * 设置字体颜色 textview
     *
     * @param viewId  TextViewID
     * @param colorid 颜色ID
     * @return ViewHolder
     */
    public ViewHolder setTextColor(int viewId, int colorid) {
        TextView view = getView(viewId);
        view.setTextColor(colorid);
        return this;
    }

    /**
     * @param viewId ViewID
     * @param id     资源ID
     * @return ViewHolder
     */
    public ViewHolder setBackgroundResource(int viewId, int id) {
        View view = getView(viewId);
        view.setBackgroundResource(id);
        return this;
    }

    /**
     * 设置布局的 gravity
     *
     * @param viewId ID
     * @param id     Gravity.LEFT
     * @return ViewHolder
     */
    public ViewHolder setLinearLayoutGravity(int viewId, int id) {
        LinearLayout view = getView(viewId);
        view.setGravity(id);
        return this;
    }


    /**
     * 点击事件
     *
     * @param viewId    ViewID
     * @param myOnClick 点击事件
     */
    public void setOnlick(int viewId, final OnClick myOnClick) {
        View view = getView(viewId);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                myOnClick.OnClick();
            }
        });
    }

    /**
     * 点击事件接口
     */
    public interface OnClick {
        public void OnClick();
    }
}