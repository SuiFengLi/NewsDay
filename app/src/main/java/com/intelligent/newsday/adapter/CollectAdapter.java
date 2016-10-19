package com.intelligent.newsday.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.bean.SearchInfo;
import com.intelligent.newsday.util.SearchDatabaseUtil;

import java.util.List;

/**
 * Created by ljh on 2016/9/20.
 */
public class CollectAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<SearchInfo> datas;
    private SearchDatabaseUtil searchDatabaseUtil;
    public CollectAdapter(Context context, List<SearchInfo> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
        searchDatabaseUtil = new SearchDatabaseUtil(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SearchInfo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_collect,null);
        TextView tv = (TextView) convertView.findViewById(R.id.item_collect_tv);
        ImageView iv = (ImageView) convertView.findViewById(R.id.item_collect_iv);
        tv.setText(datas.get(position).getTitle());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDatabaseUtil.delete(datas.get(position).getTitle());
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
