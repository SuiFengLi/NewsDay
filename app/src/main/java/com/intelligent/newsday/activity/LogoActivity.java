package com.intelligent.newsday.activity;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.intelligent.newsday.R;
import com.intelligent.newsday.base.BaseActivity;
import com.intelligent.newsday.util.SharedPreUtil;

public class LogoActivity extends BaseActivity implements Animation.AnimationListener {
    private ImageView iv_logo;
    private SharedPreUtil sharedPreUtil;


    @Override
    public void setContentLayout() {
        sharedPreUtil = new SharedPreUtil(this);
        String versionName = sharedPreUtil.getDataFromShared();
        if ( versionName.equals("") || !versionName.equals(sharedPreUtil.getVersionName())){
            startActivity(LeadActivity.class);

        }
        Log.i("LogoActivity",versionName+"---"+sharedPreUtil.getVersionName()+"--"+!versionName.equals(sharedPreUtil.getVersionName()));
        setContentView(R.layout.activity_logo);
    }

    public void initView(){
        iv_logo= (ImageView)this.findViewById(R.id.iv_logo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        iv_logo.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
