package com.intelligent.newsday.util;

import com.intelligent.newsday.bean.NewsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2016/9/11.
 */
public class JsonUtil {

    public String getJSONStr(String urlStr) {
        String json = "";
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("apikey", ConnectionURL.APIKEY);

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(readLine);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = stringBuffer.toString();

        return json;
    }

    public List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> getNewsListFromJSON(String json) {
        List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> newsInfos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("newslist");
                JSONObject indexObject;
            NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean newsInfo;
                for (int i = 0; i < jsonArray.length(); i++) {
                    indexObject = jsonArray.getJSONObject(i);
                //    newsInfo = new NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean();
                 //   newsInfos.add(newsInfo);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsInfos;
    }


}
