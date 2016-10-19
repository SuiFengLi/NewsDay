package com.intelligent.newsday.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.google.gson.Gson;
import com.intelligent.newsday.util.ConnectionURL;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;

/**
 * Created by ljh on 2016/9/17.
 */
public class MyApplication extends Application {
    private Gson gson;
    private RequestQueue requestQueue;

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    private static MyApplication myApplication;
    public static MyApplication getInstance(){
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication =this;
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ApiStoreSDK.init(this, ConnectionURL.APIKEY);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
//                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(50)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
//                .imageDownloader(new BaseImageDownloader(this))//default
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
                .writeDebugLogs()
                .build();
        // 2.将配置信息给予我们的ImageLoader对象
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        requestQueue.cancelAll(this);
    }
}
