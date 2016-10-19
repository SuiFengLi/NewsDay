package com.intelligent.newsday.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.diy.FlowLayoutCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ljh on 2016/9/5.
 */
public class LabelFlowLayout extends ViewGroup implements View.OnClickListener {
    private Set<String> set;
    private FlowLayoutCallBack callBack;
    //存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    public LabelFlowLayout(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public LabelFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public LabelFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        //父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;//自己测量的 宽度
        int height = 0;//自己测量的高度
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子view的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行时候
            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {//不换行情况
                //叠加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        mAllChildViews.clear();
        mLineHeight.clear();
        //获取当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        //记录当前行的view
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的Views
                mAllChildViews.add(lineViews);
                //重置行的宽高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置view的集合
                lineViews = new ArrayList();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        //设置子View的位置
        int left = 0;
        int top = 0;
        //获取行数
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            //当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断是否显示
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                //进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }

    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // TODO Auto-generated method stub

        return new MarginLayoutParams(getContext(), attrs);
    }

    public void getSetData(Set<String> set) {
        this.set = set;
        removeAllViews();
        MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for (String key : set) {
            TextView view = new TextView(getContext());
            view.setText(key);
            view.setTextColor(Color.WHITE);
            view.setBackgroundResource(R.drawable.label_background);
            view.setPadding(5, 5, 5, 5);
            view.setTag(key);
            view.setOnClickListener(this);
            addView(view, lp);
        }
//        invalidate();
    }

    public FlowLayoutCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(FlowLayoutCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onClick(View v) {
        set.remove(((TextView) v).getText());
        removeView(v);
        if (callBack != null)
            callBack.callBack();
    }


//    //存储所有子View
//    private List<List<View>> mAllChildViews = new ArrayList<>();
//    //每一行的高度
//    private List<Integer> mLineHeight = new ArrayList<>();
//
//    public LabelFlowLayout(Context context) {
//        this(context,null);
//    }
//
//    public LabelFlowLayout(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public LabelFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //父控件传进来的宽度和高度以及对应的测量模式
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
//
//        int width = 0; //自己测量的宽度
//        int height = 0; //自己测量的高度
//
//        int lineWidth = 0; //每一行的宽度
//        int lineHeight = 0;//每一行的高度
//
//        //获取子view的个数
//        int childView = getChildCount();
//        for (int i = 0 ; i<childView;i++){
//            View view = getChildAt(i);
//            //测量子View的宽和高
//            measureChild(view,widthMeasureSpec,heightMeasureSpec);
//            //得到LayoutParams
//            MarginLayoutParams lp = (MarginLayoutParams)getLayoutParams();
//            //子View占据的宽度
//            int childWidth = view.getMeasuredWidth() + lp.leftMargin +lp.rightMargin;
//            //  //子View占据的高度
//            int childHeight = view.getHeight() + lp.topMargin +lp.bottomMargin;
//            //换行时候
//            if(lineWidth+childWidth>sizeWidth){
//                //对比得到最大的宽度
//                width =Math.max(width,lineWidth);
//                //重置lineWidth
//                lineWidth = childWidth;
//                //记录行高
//                height += lineHeight;
//                lineHeight = childHeight;
//
//            }else {
//                //叠加宽度
//                lineWidth+=childWidth;
//                //得到最大行高
//                lineHeight = Math.max(lineHeight,childHeight);
//
//            }
//
//            //处理最后一个子View的情况
//            if(i == childView -1){
//                width = Math.max(width, lineWidth);
//                height += lineHeight;
//            }
//        }
//        //wrap_content
//        setMeasuredDimension(modeWidth==MeasureSpec.EXACTLY?sizeWidth:width,modeHeight==MeasureSpec.EXACTLY?sizeHeight:height);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        mAllChildViews.clear();
//        mLineHeight.clear();
//        //获得当前ViewGroup的宽度
//        int width = getWidth();
//
//        int lineWidth = 0 ;
//        int lineHeigh = 0;
//        //记录当前行的view
//        List<View> lineViews = new ArrayList<>();
//        int childView = getChildCount();
//        for (int i=0;i<childView;i++){
//            View view = getChildAt(i);
//            MarginLayoutParams lp =(MarginLayoutParams)view.getLayoutParams();
//            int childWidth = view.getMeasuredWidth();
//            int chileHeight = view.getMeasuredHeight();
//            //如果需要换行
//            if(childWidth+lineWidth+lp.leftMargin+lp.rightMargin>width){
//                //记录LineHeight
//                mLineHeight.add(lineHeigh);
//                //记录当前行的Views
//                mAllChildViews.add(lineViews);
//                //重置行的宽高
//                lineWidth = 0;
//                lineHeigh = chileHeight+lp.topMargin+lp.bottomMargin;
//                //重置view的集合
//                lineViews = new ArrayList<>();
//            }
//            lineWidth +=childWidth+lp.leftMargin+lp.rightMargin;
//            lineHeigh = Math.max(lineHeigh , chileHeight+lp.topMargin+lp.bottomMargin);
//            lineViews.add(view);
//        }
//        //处理最后一行
//        mLineHeight.add(lineHeigh);
//        mAllChildViews.add(lineViews);
//
//        //设置子View的位置
//        int left = 0 ;
//        int top = 0 ;
//        //获取行数
//        int lineCount = mAllChildViews.size();
//        for (int i = 0 ; i <lineCount ; i++){
//            //当前行的views和高度
//            lineViews =mAllChildViews.get(i);
//            lineHeigh = mLineHeight.get(i);
//            for (int j = 0 ; j <lineViews.size() ; j++){
//                View view = lineViews.get(j);
//                //判断是否显示
//                if (view.getVisibility() ==View.GONE){
//                    continue;
//                }
//                MarginLayoutParams lp = (MarginLayoutParams)view.getLayoutParams();
//                int viewLfet  = left + lp.leftMargin;
//                int viewTop = top + lp.topMargin;
//                int viewRight = viewLfet + view.getMeasuredWidth();
//                int viewBottom = viewTop +view.getMeasuredHeight();
//                //进行子View布局
//                view.layout(viewLfet,viewTop,viewRight,viewBottom);
//                left += view.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
//
//            }
//            left = 0;
//            top +=lineHeigh;
//
//        }
//    }
//
//    @Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new MarginLayoutParams(getContext(),attrs);
//    }

}
