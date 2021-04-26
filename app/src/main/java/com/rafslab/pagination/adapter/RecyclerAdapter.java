package com.rafslab.pagination.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rafslab.pagination.DetailsActivity;
import com.rafslab.pagination.R;
import com.rafslab.pagination.model.Data;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Data> dataList;

    public static final int viewTypeContent = 0;
    public static final int viewTypeLoading = 1;
    boolean isLoading = false;

    public RecyclerAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case viewTypeContent:
                return new ContentViewHolder(inflater.inflate(R.layout.list_item, parent, false));
            case viewTypeLoading:
                return new LoadingViewHolder(inflater.inflate(R.layout.loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading) {
            return position == dataList.size() - 1 ? viewTypeLoading : viewTypeContent;
        } else {
            return viewTypeContent;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
    public void addItems(List<Data> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoading = true;
        dataList.add(new Data());
        notifyItemInserted(dataList.size() - 1);
    }
    public void removeLoading() {
        isLoading = false;
        int position = dataList.size() - 1;
        Data item = getItem(position);
        if (item != null) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }
    Data getItem(int position) {
        return dataList.get(position);
    }

    class ContentViewHolder extends ViewHolder {
        private ImageView profile;
        private TextView title, description;
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Data data = dataList.get(position);
            Glide.with(mContext).load(data.getProfile()).into(profile);
            title.setText(data.getTitle());
            description.setText(data.getDesc());
            itemView.setOnClickListener(v->{
                Intent i = new Intent(mContext, DetailsActivity.class);
                i.putExtra("profile", data.getProfile());
                i.putExtra("title", data.getTitle());
                i.putExtra("desc", data.getDesc());
                ActivityOptionsCompat compat =ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, profile, "ProfileTransition");
                mContext.startActivity(i, compat.toBundle());
            });
        }
        @Override
        protected void clear() {

        }
    }
    static class LoadingViewHolder extends ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }
    abstract static class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        protected abstract void clear();
        public void onBind(int position) {
            this.position = position;
            clear();
        }
        public int getCurrentPosition() {
            return position;
        }
    }
}
