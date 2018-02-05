package com.example.alpha.reader_materialdesign.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alpha.reader_materialdesign.Domain.FindBookItem;
import com.example.alpha.reader_materialdesign.FindBookDetailActivity;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/13.
 */

public class FindNewBookRecommendAdapter extends RecyclerView.Adapter<FindNewBookRecommendAdapter.ViewHolder>{
    private ArrayList<FindBookItem> list;

    public FindNewBookRecommendAdapter(ArrayList<FindBookItem> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_bookrecommend_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(), FindBookDetailActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = list.get(position).getTitle();
        //holder.name.setText(text);
        Glide.with(holder.view).load(list.get(position).getImgUrl()).into(holder.imageView);
        holder.title.setText(list.get(position).getTitle());
        holder.rating.setText("评分：" + list.get(position).getRating());
        holder.writer.setText(list.get(position).getWriter());
        holder.recommend.setText(list.get(position).getRecommend());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        //TextView name;
        ImageView imageView;
        TextView title;
        TextView rating;
        TextView writer;
        TextView recommend;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            //name = itemView.findViewById(R.id.find_bookRecommend_item_name);
            imageView = itemView.findViewById(R.id.find_bookrecommend_image);
            title = itemView.findViewById(R.id.newBookRecommendTitle);
            rating = itemView.findViewById(R.id.newBookRecommendRating);
            writer = itemView.findViewById(R.id.newBookRecommendWriter);
            recommend = itemView.findViewById(R.id.newBookRecommendRecommend);
        }
    }
}
