package com.intelligent.newsday.activity;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.intelligent.newsday.R;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.fragment.HotTopicFragment;
import com.intelligent.newsday.fragment.InfoMessFragment;
import com.intelligent.newsday.fragment.SearchFragment;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RadioButton main_rb_info,main_rb_hot,main_rb_search;
    private Fragment[] fragments = new Fragment[3];
    private Toolbar toolbar;
    private DrawerLayout main_drawer;
    private NavigationView main_naView;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        main_rb_info= (RadioButton)this.findViewById(R.id.main_rb_info);
        main_rb_hot= (RadioButton)this.findViewById(R.id.main_rb_hot);
        main_rb_search= (RadioButton)this.findViewById(R.id.main_rb_search);
        main_rb_info.setOnClickListener(this);
        main_rb_hot.setOnClickListener(this);
        main_rb_search.setOnClickListener(this);
        fm = getSupportFragmentManager();
    }

    @Override
    public void initData() {

        initToolBar();
        choiceFragment(0);


    }

    private void initToolBar(){
        toolbar =(Toolbar)this.findViewById(R.id.main_tool_bar);
        toolbar.setTitle("NewsDay");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_share:
                        showShare();
                        break;
                }
                return true;
            }
        });
        drawerView();
    }

    private void drawerView(){
        main_drawer = (DrawerLayout)this.findViewById(R.id.main_drawer);
        main_naView =(NavigationView)this.findViewById(R.id.main_hidden_naView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,main_drawer,toolbar,R.string.app_name,R.string.app_name);
        drawerToggle.syncState();
        main_drawer.addDrawerListener(drawerToggle);
        main_naView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_settings:
                        Toast.makeText(MainActivity.this,"setting",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_collen:
                        startActivity(CollectActivity.class);
                        break;
                }
                return true;
            }
        });
        main_naView.getHeaderView(0).findViewById(R.id.login_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserLoginActivity.class);
                Toast.makeText(MainActivity.this,"login",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tool_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void HideAllFragment(){
        ft = fm.beginTransaction();
        for (int i=0;i<fragments.length;i++){
            if (fragments[i]!=null)
            ft.hide(fragments[i]);
        }
        ft.commit();
    }

    private void choiceFragment(int index){
        HideAllFragment();
        ft = fm.beginTransaction();
        if (fragments[index]==null){
            switch (index){
                case 0:
                    fragments[index]= new InfoMessFragment();
                    ft.add(R.id.main_fl,fragments[index]);
                    break;
                case 1:
                    fragments[index]= new HotTopicFragment();
                    ft.add(R.id.main_fl,fragments[index]);
                    break;
                case 2:
                    fragments[index]= new SearchFragment();
                    ft.add(R.id.main_fl,fragments[index]);
                    break;
            }
        }else {
            ft.show(fragments[index]);
        }
        ft.commit();

    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_rb_info:
                choiceFragment(0);
                break;
            case R.id.main_rb_hot:
                choiceFragment(1);
                break;
            case R.id.main_rb_search:
                choiceFragment(2);
                break;

        }
    }
}
