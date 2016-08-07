package com.yonyou.lxp.lxp_utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yonyou.lxp.lxp_utils.listener.NoDoubleClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/7/6.
 * RecyclerView 适配器
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<T> mDatas;
    private int mItemLayoutId = 0;


    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int position) {
        final ViewHolder viewHolder = getViewHolder(null, parent);
        return viewHolder;
    }

    /**
     * 绑定数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        convert(viewHolder, getItem(position), position);
        viewHolder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(mDatas.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                }
            }
        });
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != mOnItemLongClickListener) {
                    mOnItemLongClickListener.onItemLongClick(mDatas.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                }
                return false;
            }
        });
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 数据绑定
     *
     * @param helper
     * @param item
     */
    public abstract void convert(ViewHolder helper, T item, int position);

    /**
     * 创建ViewHolder
     *
     * @param convertView
     * @param parent
     * @return
     */
    private ViewHolder getViewHolder(View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId);
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(Object item, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(Object item, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    /**
     * item点击事件
     * @param mOnItemClickListener
     */
    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 长按点击事件
     * @param mOnItemLongClickListener
     */
    public void setmOnItemLongClickListener(OnRecyclerViewItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}
