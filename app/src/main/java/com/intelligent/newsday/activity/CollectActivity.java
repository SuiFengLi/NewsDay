package com.intelligent.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligent.newsday.R;
import com.intelligent.newsday.adapter.CollectAdapter;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.bean.SearchInfo;
import com.intelligent.newsday.util.SearchDatabaseUtil;

import java.util.List;

import butterknife.BindView;

public class CollectActivity extends BaseActivity {
    private SearchDatabaseUtil searchDatabaseUtil;
    private List<SearchInfo> list;
    private Toolbar toolbar;
    private DrawerLayout collect_drawer;
    private NavigationView collect_naView;
    private CollectAdapter adapter;
    private ListView lv;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void initView() {
        lv = (ListView) this.findViewById(R.id.collect_lv);
        initToolBar();
        searchDatabaseUtil = new SearchDatabaseUtil(this);
        list = searchDatabaseUtil.showAll();
        adapter = new CollectAdapter(CollectActivity.this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityToWebView(list.get(position));
            }
        });

    }

    private void startActivityToWebView(SearchInfo searchInfo) {
        Intent intent = new Intent(this, ContentToURLActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", searchInfo.getUrl());
        bundle.putString("title", searchInfo.getTitle());
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public void initData() {

    }


    private void initToolBar() {
        toolbar = (Toolbar) this.findViewById(R.id.collect_tool);
        toolbar.setTitle("NewsDay");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        drawerView();
    }

    private void drawerView() {
        collect_drawer = (DrawerLayout) this.findViewById(R.id.collect_drawer);
        collect_naView = (NavigationView) this.findViewById(R.id.collect_hidden_naView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, collect_drawer, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();
        collect_drawer.addDrawerListener(drawerToggle);
        collect_naView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_settings:
                        Toast.makeText(CollectActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_collen:
                        startActivity(CollectActivity.class);
                        break;
                }
                return true;
            }
        });
        collect_naView.getHeaderView(0).findViewById(R.id.login_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserLoginActivity.class);
                Toast.makeText(CollectActivity.this, "login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
