package com.intelligent.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.diy.FlowLayoutCallBack;
import com.intelligent.newsday.ui.LabelFlowLayout;
import com.intelligent.newsday.util.SharedPreUtil;

import java.util.Set;

public class LabelActivity extends BaseActivity {
    private String mNames[] = {"互联网", "安卓","军事", "娱乐", "新闻", "游戏", "地产","热点"};
    private LabelFlowLayout mFlowLayout;
    private ListView lv;
    private Toolbar toolbar;
    private SharedPreUtil sharedPreUtil;
    private Set<String> str;
    private boolean needUpdate;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_label);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) this.findViewById(R.id.label_tool_bar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(getDrawable(R.drawable.btn_return));
        toolbar.setBackgroundColor(getResources().getColor(R.color.text_color_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("needUpdate", needUpdate);
                setResult(101, intent);
                finish();
            }
        });

    }

    @Override
    public void initData() {
        lv = (ListView) this.findViewById(R.id.label_lv);
        mFlowLayout = (LabelFlowLayout) findViewById(R.id.label_flow_layout);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mNames);
        lv.setAdapter(adapter);
        sharedPreUtil = new SharedPreUtil(this);
        str = sharedPreUtil.getSetTabTitles();
        mFlowLayout.getSetData(str);
        mFlowLayout.setCallBack(new FlowLayoutCallBack() {
            @Override
            public void callBack() {
                needUpdate = true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (str.add(adapter.getItem(position))) {
                    mFlowLayout.getSetData(str);
                    needUpdate = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreUtil.setTabTitles(str);
    }

    //    private void initMFlowLayout(){
//        mFlowLayout = (LabelFlowLayout) findViewById(R.id.label_flow_layout);
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.leftMargin = 15;
//        lp.rightMargin = 15;
//        lp.topMargin = 15;
//        lp.bottomMargin = 15;
//        for (int i = 0; i < mNames.length; i++) {
//            TextView view = new TextView(this);
//            view.setText(mNames[i]);
//            view.setTextColor(Color.BLACK);
//            view.setBackground(getDrawable(R.drawable.label_background));
//            view.setPadding(10,10,10,10);
//            mFlowLayout.addView(view, lp);
//        }
//    }


}
