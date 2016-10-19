package com.intelligent.newsday.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ljh on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
        initView();
        initData();
    }
    public abstract void setContentLayout();
    public abstract void initView();
    public abstract void initData();

    /**
     * 跳转
     * @param targeClass
     */

    public void startActivity(Class<?> targeClass ){
        Intent intent = new Intent(this,targeClass);
        startActivity(intent);
    }



}
