package com.intelligent.newsday.base;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intelligent.newsday.R;

import java.util.List;

/**
 * Created by ljh on 2016/9/18.
 */
public abstract class BaseRecyclerViewFootAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> mDatas;
    private Context context;
    private RecyclerView recyclerView;
    protected LayoutInflater layoutInflater;

    // 标示
    private static final int HOLDER_TYPE_NORMAL = 0;
    private static final int HOLDER_TYPE_LOADING = 1;
    private static final int HOLDER_TYPE_HEADER = 2;

    private boolean isLoading = false;
    private boolean firstLoading = true;

    private LoadingViewHolder loadingViewHolder;

    public interface OnLoadingListener {
        void onLoading();
    }

    private OnLoadingListener onLoadingListener;

    public OnLoadingListener getOnLoadingListener() {
        return onLoadingListener;
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        setOnScollListener();
        this.onLoadingListener = onLoadingListener;
    }

    public void notifyLoading() {
        if (mDatas.size() != 0 && mDatas.get(mDatas.size() - 1) != null) {
            mDatas.add(null);
            notifyItemInserted(mDatas.size() - 1);

        }

    }


    //数据加载成功
    public void setLoadingComplete() {
        if (mDatas.size() > 0 && mDatas.get(mDatas.size() - 1) == null) {
            isLoading = false;
            mDatas.remove(mDatas.size() - 1);
            notifyItemRemoved(mDatas.size() - 1);
        }

    }

    //加载失败
    public void setLoadingError() {
        if (loadingViewHolder != null) {
            isLoading = false;
            loadingViewHolder.item_recycle_loading_pg.setVisibility(View.GONE);
            loadingViewHolder.item_recycle_loading_tv.setText("加载失败，请点击屏幕进行重新的加载！");
            loadingViewHolder.item_recycle_loading_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLoadingListener != null) {
                        isLoading = true;
                        loadingViewHolder.item_recycle_loading_pg.setVisibility(View.VISIBLE);
                        loadingViewHolder.item_recycle_loading_tv.setText("正在加载 请稍后");
                        onLoadingListener.onLoading();
                    }
                }
            });
        }
    }

    //没有更多的数据
    public void setLoadingNoMore() {
        isLoading = false;
        if (loadingViewHolder != null) {
            loadingViewHolder.item_recycle_loading_pg.setVisibility(View.GONE);
            loadingViewHolder.item_recycle_loading_tv.setText("没有更多的数据！");
        }
    }

    public void setOnScollListener() {
        if (recyclerView == null) {
            return;
        }
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                    if (!isLoading && !firstLoading) {
                        notifyLoading();
                        isLoading = true;
                        if (loadingViewHolder != null) {
                            loadingViewHolder.item_recycle_loading_pg.setVisibility(View.VISIBLE);
                            loadingViewHolder.item_recycle_loading_tv.setText("正在加载 请稍后");

                        }
                        if (onLoadingListener != null) {
                            onLoadingListener.onLoading();
                        }
                    }
                }
                if (firstLoading)
                    firstLoading = false;
            }
        });
    }

    //添加数据
    public void addAllData(List<T> data) {
        if (data != null)
            this.mDatas.addAll(data);
        notifyDataSetChanged();

    }

    //清理数据
    public void clearAllData() {
        if (this.mDatas != null)
            this.mDatas.clear();
        notifyDataSetChanged();

    }


    public List<T> getAllData(){
        return this.mDatas;
    }

    public BaseRecyclerViewFootAdapter(List<T> mDatas, Context context, RecyclerView recyclerView) {
        this.mDatas = mDatas;
        this.context = context;
        this.recyclerView = recyclerView;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        T t = mDatas.get(position);
        if (t == null) {
            return HOLDER_TYPE_LOADING;
        } else if (position == 0) {
            return HOLDER_TYPE_HEADER;
        } else {
            return HOLDER_TYPE_NORMAL;
        }
    }

    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent);

    public abstract void onBindNoramlViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent);

    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case HOLDER_TYPE_HEADER:
                viewHolder = onCreateHeaderViewHolder(parent);
                break;
            case HOLDER_TYPE_NORMAL:
                viewHolder = onCreateNormalViewHolder(parent);
                break;
            case HOLDER_TYPE_LOADING:
                viewHolder = onCreateLoadingViewHolder(parent);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HOLDER_TYPE_HEADER:
                onBindHeaderViewHolder(holder, position);
                break;
            case HOLDER_TYPE_NORMAL:
                onBindNoramlViewHolder(holder, position);
                break;
            case HOLDER_TYPE_LOADING:
                onBindLoadingViewHolder(holder, position);
                break;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public TextView item_recycle_loading_tv;
        public ProgressBar item_recycle_loading_pg;
        public RelativeLayout item_recycle_loading_layout;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            item_recycle_loading_pg = (ProgressBar) itemView.findViewById(R.id.item_recycle_loading_pg);
            item_recycle_loading_tv = (TextView) itemView.findViewById(R.id.item_recycle_loading_tv);
            item_recycle_loading_layout = (RelativeLayout) itemView.findViewById(R.id.item_recycle_loading_layout);
        }

    }

    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent) {

        return new LoadingViewHolder(layoutInflater.inflate(R.layout.item_news_foot, parent, false));
    }

    public void onBindLoadingViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

}
