package com.yonyou.lxp.lxp_utils.utils;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lxp.lxp_utils.R;

/**
 * 作者： liuxiaopeng on 16/7/1.
 * 描述：
 */

public class BetterPopupWindow {
    /**
     * 触发view
     **/
    protected final View anchor;
    private final PopupWindow window;
    /**
     * PopupWindow的根view
     **/
    private View root;
    private Drawable background = null;
    private final WindowManager windowManager;


    private int width = WindowManager.LayoutParams.WRAP_CONTENT;
    private int hight = WindowManager.LayoutParams.WRAP_CONTENT;

    /**
     * Create a BetterPopupWindow
     *
     * @param anchor the view that the BetterPopupWindow will be displaying 'from'
     */
    public BetterPopupWindow(View anchor) {
        this.anchor = anchor;
        this.window = new PopupWindow(anchor.getContext());

        // when a touch even happens outside of the window
        // make the window go away
        //触摸PopupWindow边缘，PopupWindow消失
        this.window.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    BetterPopupWindow.this.window.dismiss();
                    return true;
                }
                return false;
            }
        });

        this.windowManager = (WindowManager) this.anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
        onCreate();
    }

    /**
     * 重写这个方法-当你想在PopupWindow创建的时候
     * Anything you want to have happen when created. Probably should create a view and setup the event listeners on
     * child views.
     */
    protected void onCreate() {
    }

    /**
     * In case there is stuff to do right before displaying.
     * 在PopupWindow显示前你想做什么
     */
    protected void onShow() {
    }

    private void preShow() {
        if (this.root == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        onShow();
// PopupWindow#setBackgroundDrawable为了使PopupWindow正常工作
        if (this.background == null) {
            this.window.setBackgroundDrawable(new BitmapDrawable());
        } else {
            this.window.setBackgroundDrawable(this.background);
        }

        // if using PopupWindow#setBackgroundDrawable this is the only values of the width and hight that make it work
        // otherwise you need to set the background of the root viewgroup
        // and set the popupwindow background to an empty BitmapDrawable
        this.window.setWidth(width);
        this.window.setHeight(hight);
        this.window.setTouchable(true);
        this.window.setFocusable(true);
        this.window.setOutsideTouchable(true);

        this.window.setContentView(this.root);
    }


    /**
     * 设置 WindowManager.LayoutParams.WRAP_CONTENT   MATCH_PARENT
     * 默认WRAP_CONTENT
     *
     * @param width LayoutParams
     * @param hight LayoutParams
     */
    public void setMeasure(int width, int hight) {
        this.width = width;
        this.hight = hight;
    }

    public void setBackgroundDrawable(Drawable background) {
        this.background = background;
    }

    /**
     * Sets the content view. Probably should be called from {@link}
     *
     * @param root the view the popup will display
     */
    public void setContentView(View root) {
        this.root = root;
        this.window.setContentView(root);
    }

    /**
     * Will inflate and set the view from a resource id
     *
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        LayoutInflater inflator =
                (LayoutInflater) this.anchor.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.setContentView(inflator.inflate(layoutResID, null));
    }

    /**
     * If you want to do anything when {@link} is called
     * PopupWindow取消监听
     *
     * @param listener
     */
    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.window.setOnDismissListener(listener);
    }

    /**
     * 下方显示
     * Displays like a popdown menu from the anchor view
     */
    public void showLikePopDownMenu() {
        this.showLikePopDownMenu(0, 0);
    }

    /**
     * Displays like a popdown menu from the anchor view.
     *
     * @param xOffset offset in X direction
     * @param yOffset offset in Y direction
     */
    public void showLikePopDownMenu(int xOffset, int yOffset) {
        this.preShow();

        this.window.setAnimationStyle(R.style.Animations_PopDownMenu);

        this.window.showAsDropDown(this.anchor, xOffset, yOffset);
    }

    /**
     * 某个地方显示(某人底部对齐点击view)
     * Displays like a QuickAction from the anchor view.
     */
    public void showLikeQuickAction() {
        this.showLikeQuickAction(0, 0);
    }

    /**
     * Displays like a QuickAction from the anchor view.
     *
     * @param xOffset offset in the X direction
     * @param yOffset offset in the Y direction
     */
    public void showLikeQuickAction(int xOffset, int yOffset) {
        this.preShow();

        this.window.setAnimationStyle(R.style.Animations_GrowFromBottom);

        int[] location = new int[2];
        this.anchor.getLocationOnScreen(location);

        Rect anchorRect =
                new Rect(location[0], location[1], location[0] + this.anchor.getWidth(), location[1]
                        + this.anchor.getHeight());

        this.root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int rootWidth = this.root.getMeasuredWidth();
        int rootHeight = this.root.getMeasuredHeight();

        int screenWidth = this.windowManager.getDefaultDisplay().getWidth();
        int screenHeight = this.windowManager.getDefaultDisplay().getHeight();

        int xPos = ((screenWidth - rootWidth) / 2) + xOffset;
        int yPos = anchorRect.top - rootHeight + yOffset;

        // display on bottom
        if (rootHeight > anchorRect.top) {
            yPos = anchorRect.bottom + yOffset;
            this.window.setAnimationStyle(R.style.Animations_GrowFromTop);
        }

        this.window.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }


    public void dismiss() {
        this.window.dismiss();
    }
}
