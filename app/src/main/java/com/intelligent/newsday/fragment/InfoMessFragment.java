package com.intelligent.newsday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.intelligent.newsday.R;
import com.intelligent.newsday.activity.LabelActivity;
import com.intelligent.newsday.adapter.InfoMessFragmentAdapter;
import com.intelligent.newsday.util.SharedPreUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2016/9/1.
 */
public class InfoMessFragment extends Fragment {
    private List<Fragment> fragments;
    private List<String> titles;
    private TabLayout fg_info_tab;
    private ViewPager fg_info_vp;
    private InfoMessFragmentAdapter adapter;
    private SharedPreUtil sharedPreUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreUtil = new SharedPreUtil(getActivity());
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fg_info_tab = (TabLayout) view.findViewById(R.id.fg_info_tab);
        fg_info_vp = (ViewPager) view.findViewById(R.id.fg_info_vp);
        view.findViewById(R.id.fg_info_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "123123123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LabelActivity.class);
                startActivityForResult(intent, 100);

            }
        });
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles = sharedPreUtil.getListTabTitles();
        for (int i = 0; i < titles.size(); i++) {
            NewsFragment temp = new NewsFragment();
            Bundle tabTitle = new Bundle();
            tabTitle.putString("newsName",titles.get(i));
            Log.i("titlesssssss",titles.get(i));
            temp.setArguments(tabTitle);
            fragments.add(temp);

        }
        adapter = new InfoMessFragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        fg_info_vp.setAdapter(adapter);
        fg_info_tab.setupWithViewPager(fg_info_vp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101 && data.getBooleanExtra("needUpdate", false)) {
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
            titles = sharedPreUtil.getListTabTitles();
            for (int i = 0; i < titles.size(); i++) {
                NewsFragment temp = new NewsFragment();
                Bundle tabTitle = new Bundle();
                tabTitle.putString("newsName",titles.get(i));
                temp.setArguments(tabTitle);
                fragments.add(temp);
                Log.i("titles-----",titles.get(i));

            }
            adapter = new InfoMessFragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
            fg_info_vp.setAdapter(adapter);
        }

    }


}
