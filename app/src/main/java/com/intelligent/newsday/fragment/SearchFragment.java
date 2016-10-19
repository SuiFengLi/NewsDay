package com.intelligent.newsday.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.activity.ContentToURLActivity;
import com.intelligent.newsday.adapter.CollectAdapter;
import com.intelligent.newsday.bean.SearchInfo;
import com.intelligent.newsday.util.SearchDatabaseUtil;

import java.util.List;

/**
 * Created by ljh on 2016/9/1.
 */
public class SearchFragment extends Fragment {
    private EditText editText;
    private TextView textView;
    private SearchDatabaseUtil searchDatabaseUtil;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String str = (String)msg.obj;
                    final List<String> list =searchDatabaseUtil.getTitle(str);
                    Log.i("SearchFragment",list.toString());
                    if (list.size()>0){
                        textView.setText(list.toString());

                    }else {
                        textView.setText("没有找到");
                    }
                    break;
                case 1:
                    textView.setText("");
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchDatabaseUtil = new SearchDatabaseUtil(getActivity());
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.fg_search_et);
        textView = (TextView) view.findViewById(R.id.fg_search_lv);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Message message = new Message();
                if (s.length()>0){
                    message.what = 0 ;
                    message.obj = s.toString();
                    handler.sendMessage(message);
                    Log.i("SearchFragment",s.toString());
                }else {
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void startActivityToWebView(SearchInfo searchInfo) {
        Intent intent = new Intent(getActivity(), ContentToURLActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", searchInfo.getUrl());
        bundle.putString("title", searchInfo.getTitle());
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

}



