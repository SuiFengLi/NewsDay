package com.intelligent.newsday.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;


/**
 * Created by ljh on 2016/9/5.
 */
public class ScrollMenu extends HorizontalScrollView {
    private ViewGroup menu; //Menu布局
    private ViewGroup content; //主体布局
    private int menuWidth; //Menu的宽度
    private int screenWidth; //屏幕的宽度
    private int showMenuWidth; //滑动到多大距离显示Menu
    private int menuRightWidth; //距离屏幕右侧的距离
    private boolean isOnce; //是否第一次使用

    public ScrollMenu(Context context) {
        this(context,null);
    }

    public ScrollMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;

            menuRightWidth  = 100;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isOnce){
            ViewGroup viewGroup = (ViewGroup)getChildAt(0);
            menu =(ViewGroup)viewGroup.getChildAt(0);
            content =(ViewGroup)viewGroup.getChildAt(1);
            menuWidth = screenWidth -menuRightWidth;
            showMenuWidth = menuWidth/2;
            menu.getLayoutParams().width = menuWidth;
            content.getLayoutParams().width=screenWidth;
            isOnce =true;
        }
    }
}


