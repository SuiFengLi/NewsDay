package com.intelligent.newsday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.intelligent.newsday.R;
import com.intelligent.newsday.adapter.NewsContentAdapter;
import com.intelligent.newsday.base.BaseRecyclerViewFootAdapter;
import com.intelligent.newsday.base.MyApplication;
import com.intelligent.newsday.bean.NewsInfo;
import com.intelligent.newsday.bean.NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.intelligent.newsday.util.ConnectionURL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ljh on 2016/9/12.
 */
public class NewsFragment extends Fragment {
    private RecyclerView fg_rv_news;
    private String newsName;
    private Gson gson;
    private RequestQueue requestQueue;
    private List<ContentlistBean> contentlistBeans;
    private NewsContentAdapter adapter;
    private int nowPage = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int allPage;
    private final int ADD_DATA_FLAG = 0; //上拉加载
    private final int UPDATE_DATA_FLAG = 1; //下拉刷新

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsName = getArguments().getString("newsName");
        Log.i("titles", newsName);
        gson = MyApplication.getInstance().getGson();
        requestQueue = MyApplication.getInstance().getRequestQueue();
        return inflater.inflate(R.layout.fragment_news, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fg_rv_news = (RecyclerView) view.findViewById(R.id.fg_rv_news);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fg_refreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.text_color_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getYiYuanNewsInfo(UPDATE_DATA_FLAG, 1);
            }
        });

        contentlistBeans = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        fg_rv_news.setLayoutManager(layoutManager);
        fg_rv_news.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsContentAdapter(contentlistBeans, getActivity(), fg_rv_news);
        adapter.setOnLoadingListener(new BaseRecyclerViewFootAdapter.OnLoadingListener() {
            @Override
            public void onLoading() {
                getYiYuanNewsInfo(ADD_DATA_FLAG, nowPage);
            }
        });

        fg_rv_news.setAdapter(adapter);
        getYiYuanNewsInfo(ADD_DATA_FLAG, nowPage);

        // new NewsAsync().execute("http://apis.baidu.com/txapi/keji/keji?key=2bf845320bc3e1b4844af875faaa2125&num=50");
    }


    //    class NewsAsync extends AsyncTask<String, Integer, List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean>> {
//
//        @Override
//        protected void onPostExecute(List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> newsInfos) {
//            super.onPostExecute(newsInfos);
//            NewsAdapter adapter = new NewsAdapter(getActivity(), newsInfos);
//            fg_rv_news.setAdapter(adapter);
//        }
//
//        @Override
//        protected List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> doInBackground(String... params) {
//            JsonUtil jsonUtil = new JsonUtil();
//            return jsonUtil.getNewsListFromJSON(jsonUtil.getJSONStr(params[0]));
//        }
//    }




    private void getYiYuanNewsInfo(final int getDataType, int pager) {
        if (allPage > 0 && nowPage > allPage ) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingNoMore();
        }
        StringRequest stringRequest = new StringRequest(ConnectionURL.findNewsByName(newsName, pager), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                NewsInfo newsInfo = gson.fromJson(response, NewsInfo.class);
                Log.i("NewsFragment", newsInfo.toString());
                contentlistBeans = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
                allPage = newsInfo.getShowapi_res_body().getPagebean().getAllPages();
                onGetDataSuccess(getDataType);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getDefult(getDataType);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", ConnectionURL.APIKEY);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void getDefult(int getDataType){
        switch (getDataType){
            case ADD_DATA_FLAG:
                adapter.setLoadingError();
                break;
            case UPDATE_DATA_FLAG:
                swipeRefreshLayout.setRefreshing(false);
                break;

        }
    }

    private void onGetDataSuccess(int getDataType){
        if (contentlistBeans==null||contentlistBeans.size()==0)
            return;
        Iterator<ContentlistBean> iterator = contentlistBeans.iterator();
        ContentlistBean temp =null;
        while (iterator.hasNext()){
            temp = iterator.next();
            if (temp==null||temp.getImageurls().size()==0||temp.getImageurls().get(0).getUrl()==null)
                iterator.remove();
        }
        switch (getDataType){
            case ADD_DATA_FLAG:
                adapter.setLoadingComplete();
                if (contentlistBeans!=null)
                    adapter.addAllData(contentlistBeans);
                    nowPage++;
                break;
            case UPDATE_DATA_FLAG:
                if (contentlistBeans!=null){
                    adapter.clearAllData();
                    adapter.addAllData(contentlistBeans);
                }
                swipeRefreshLayout.setRefreshing(false);
                nowPage=2;
                break;

        }

    }


}
