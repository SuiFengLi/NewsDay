package com.intelligent.newsday.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ljh on 2016/9/11.
 */
public class ImageLoaderUtil {
    private ImageView imageView;
    private String urlStr;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public void showHttpBitmapFromURLByThread(final String urlStr, ImageView imageView){
        this.imageView =imageView;
        this.urlStr =urlStr;
        new Thread(){
            @Override
            public void run() {
                super.run();
                InputStream inputStream = null;
                Bitmap bitmap = null;
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                    inputStream = new BufferedInputStream(connection.getInputStream());
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    Message message= Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
