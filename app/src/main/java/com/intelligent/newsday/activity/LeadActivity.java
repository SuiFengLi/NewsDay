package com.intelligent.newsday.activity;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.intelligent.newsday.R;
import com.intelligent.newsday.adapter.LeadViewPagerAdapter;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.util.SharedPreUtil;

import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreUtil sharedPreUtil;
    private Button btn_lead_to_logo;
    private ViewPager vp_lead;
    private List<View> views;
    private LeadViewPagerAdapter adapter;
    private int[] ivIds = {R.drawable.lead_1, R.drawable.lead_2, R.drawable.lead_3};

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_lead);
        sharedPreUtil = new SharedPreUtil(this);
        sharedPreUtil.setDataToShared(sharedPreUtil.getVersionName());
        Log.i("LeadActivity", sharedPreUtil.getVersionName());
    }

    public void initView() {
        vp_lead = (ViewPager) this.findViewById(R.id.vp_lead);
        btn_lead_to_logo = (Button) this.findViewById(R.id.btn_lead_to_logo);
        btn_lead_to_logo.setOnClickListener(this);
    }

    public void initData() {
        views = new ArrayList<>();
        View view = null;
        for (int i = 0; i < 3; i++) {
            view = LayoutInflater.from(this).inflate(R.layout.iv_lead, null);
            view.findViewById(R.id.iv_lead).setBackgroundResource(ivIds[i]);
            views.add(view);
        }

        adapter = new LeadViewPagerAdapter(views);
        vp_lead.setAdapter(adapter);
        vp_lead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2)
                    btn_lead_to_logo.setVisibility(View.VISIBLE);
                else
                    btn_lead_to_logo.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lead_to_logo:
                startActivity(LogoActivity.class);
                finish();
                break;

        }
    }
}
