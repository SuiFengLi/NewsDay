package com.intelligent.newsday.activity;


import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligent.newsday.R;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.bean.SearchInfo;
import com.intelligent.newsday.util.SearchDatabaseUtil;


public class ContentToURLActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar pb;
    private Toolbar toolbar;
    private FloatingActionButton faBtn;
    private SearchDatabaseUtil searchDatabaseUtil;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_content_to_url);
        searchDatabaseUtil = new SearchDatabaseUtil(this);
    }

    @Override
    public void initView() {
        webView = (WebView)this.findViewById(R.id.web_view);
        pb = (ProgressBar) this.findViewById(R.id.content_pb);
        toolbar = (Toolbar)this.findViewById(R.id.content_tool);
        faBtn = (FloatingActionButton) this.findViewById(R.id.content_float_btn);
    }

    @Override
    public void initData() {
        initToolBar();
         String url =  getIntent().getExtras().getString("url");
         String title = getIntent().getExtras().getString("title");
        final SearchInfo searchInfo = new SearchInfo(url,title);
        Log.i("url-----",url);
        webView.loadUrl(url.toString());
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( searchDatabaseUtil.addMsg(searchInfo)){
                    Toast.makeText(ContentToURLActivity.this,searchInfo.getTitle(),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ContentToURLActivity.this,"已经收藏过",Toast.LENGTH_LONG).show();
                }

            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.i("url should",url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);
                if (newProgress ==100){
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initToolBar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        drawerView();
    }


    private void drawerView(){
       DrawerLayout  content_drawer = (DrawerLayout)this.findViewById(R.id.content_drawer);
       NavigationView  content_naView =(NavigationView)this.findViewById(R.id.content_hidden_naView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,content_drawer,toolbar,R.string.app_name,R.string.app_name);
        drawerToggle.syncState();
        content_drawer.addDrawerListener(drawerToggle);
        content_naView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_settings:
                        Toast.makeText(ContentToURLActivity.this,"setting",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_collen:
                        startActivity(CollectActivity.class);
                        break;
                }
                return true;
            }
        });
        content_naView.getHeaderView(0).findViewById(R.id.login_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserLoginActivity.class);
                Toast.makeText(ContentToURLActivity.this,"login",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
