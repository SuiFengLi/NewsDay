package com.intelligent.newsday.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ljh on 2016/8/31.
 */
public class SharedPreUtil {
    private Context context;
    private String lead_data = "lead_data";
    private String tab_data = "tab_data";
    private Set<String> tab_titles;

    public SharedPreUtil(Context context) {
        this.context = context;
    }

    public void setDataToShared(String versionName) {
        SharedPreferences sp = context.getSharedPreferences(lead_data, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("versionName", versionName);
        editor.commit();
    }

    public String getDataFromShared() {

        return context.getSharedPreferences(lead_data, Context.MODE_PRIVATE).getString("versionName", "");
    }

    public String getVersionName() {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public String getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode + "";
    }

    public Set<String> getSetTabTitles() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(tab_data, 0);
        tab_titles = sharedPreferences.getStringSet("menu", null);
        if (tab_titles == null) {
            tab_titles = new TreeSet<>();
            tab_titles.add("互联网");
            tab_titles.add("军事");
            tab_titles.add("娱乐");
            tab_titles.add("游戏");
            tab_titles.add("新闻");

        }
        return tab_titles;
    }

    public List<String> getListTabTitles() {
        List<String> tabTitles = new ArrayList<>();
        tab_titles = getSetTabTitles();
        for (String tab_title : tab_titles) {
            tabTitles.add(tab_title);
        }
        return tabTitles;
    }

    public void setTabTitles(Set<String> tabs) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(tab_data, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("menu", tabs);
        editor.commit();

    }


}



