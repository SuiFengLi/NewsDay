package com.intelligent.newsday.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligent.newsday.R;
import com.intelligent.newsday.activity.ContentToURLActivity;
import com.intelligent.newsday.base.BaseRecyclerViewFootAdapter;
import com.intelligent.newsday.bean.NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.intelligent.newsday.bean.SearchInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by ljh on 2016/9/17.
 */
public class NewsContentAdapter extends BaseRecyclerViewFootAdapter<ContentlistBean> {
    private List<ContentlistBean> myDatas;
    private DisplayImageOptions options;
    private Context context;



    public NewsContentAdapter(List<ContentlistBean> mDatas, Context context, RecyclerView recyclerView) {
        super(mDatas, context, recyclerView);
        this.context =context;
        this.myDatas = mDatas;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)
                .showImageOnFail(R.drawable.img_news_lodinglose)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        return new NormalViewHolder(layoutInflater.inflate(R.layout.item_news_info, parent, false));
    }

    @Override
    public void onBindNoramlViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NormalViewHolder viewHolder = (NormalViewHolder) holder;
         final ContentlistBean contentlistBean = myDatas.get(position);
        viewHolder.item_new_title.setText(contentlistBean.getTitle());
        viewHolder.item_new_time.setText(contentlistBean.getPubDate());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInfo searchInfo = new SearchInfo(contentlistBean.getLink(),contentlistBean.getTitle());
                startActivityToWebView(searchInfo);
            }
        });
        List<ContentlistBean.ImageEntity> entity = myDatas.get(position).getImageurls();
        if (entity.size() != 0) {
            ImageLoader.getInstance().displayImage(contentlistBean.getImageurls().get(0).getUrl(),
                    viewHolder.item_new_icon, options);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_news_head, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        final ContentlistBean contentlistBean = myDatas.get(position);
        viewHolder.header_tv.setText(contentlistBean.getTitle());
        viewHolder.header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInfo searchInfo = new SearchInfo(contentlistBean.getLink(),contentlistBean.getTitle());
                startActivityToWebView(searchInfo);
            }
        });
        List<ContentlistBean.ImageEntity> entity = myDatas.get(position).getImageurls();
        if (entity.size() != 0) {
            ImageLoader.getInstance().displayImage(contentlistBean.getImageurls().get(0).getUrl(), viewHolder.header_img, options);
        }
    }

    private void startActivityToWebView(SearchInfo searchInfo){
        Intent intent = new Intent(context, ContentToURLActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",searchInfo.getUrl());
        bundle.putString("title",searchInfo.getTitle());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    private class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView item_new_title, item_new_time;
        public ImageView item_new_icon;
        public CardView cardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            item_new_title = (TextView) itemView.findViewById(R.id.item_new_title);
            item_new_time = (TextView) itemView.findViewById(R.id.item_new_time);
            item_new_icon = (ImageView) itemView.findViewById(R.id.item_new_icon);
            cardView = (CardView) itemView.findViewById(R.id.item_news_card_view);

        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_tv;
        public ImageView header_img;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            header_tv = (TextView) itemView.findViewById(R.id.item_head_text);
            header_img = (ImageView) itemView.findViewById(R.id.item_head_img);
        }
    }


}

