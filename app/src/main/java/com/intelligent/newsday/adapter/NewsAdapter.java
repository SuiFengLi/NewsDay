package com.intelligent.newsday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.bean.NewsInfo;
import com.intelligent.newsday.bean.NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.intelligent.newsday.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by ljh on 2016/9/11.
 */
public class NewsAdapter extends BaseAdapter {
    private List<ContentlistBean> infos;
    private Context context;
    private ImageLoaderUtil imageLoaderUtil;
    public NewsAdapter(Context context,List<ContentlistBean> infos) {
        this.context = context;
        this.infos = infos;
        imageLoaderUtil = new ImageLoaderUtil();
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public ContentlistBean getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView ==null){
            convertView  = LayoutInflater.from(context).inflate(R.layout.item_news_info,null,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.item_new_title);
            viewHolder.time = (TextView)convertView.findViewById(R.id.item_new_time);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_new_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ContentlistBean info = getItem(position);
        viewHolder.title.setText(info.getTitle());
        viewHolder.time.setText(info.getPubDate());
        viewHolder.icon.setTag(info.getImageurls());
        imageLoaderUtil.showHttpBitmapFromURLByThread(info.getImageurls().get(0).getUrl(),viewHolder.icon);
        return convertView;
    }


    class ViewHolder {
        TextView title,time;
        ImageView icon;
    }

}
