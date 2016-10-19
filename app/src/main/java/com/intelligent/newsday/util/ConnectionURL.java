package com.intelligent.newsday.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ljh on 2016/9/17.
 */
public class ConnectionURL {
    //频道新闻API_易源
    public static final String YIYUAN_URL="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    //key.
    public static final String APIKEY ="c7ee1c59186cf4989d9536280599c92e";


    public static String findNewsByName(String newsName,int page){
        return YIYUAN_URL+"?channelName="+changeNameToUTF(newsName)+"&page="+page;
    }

    public static String changeNameToUTF(String name){

        try {
           name = URLEncoder.encode(name,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
            return name;
    }

}
