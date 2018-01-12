package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.CommunityActivity;
import com.example.alpha.reader_materialdesign.Domain.MainCommunity;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/9.
 */

public class MainCommunityAdapter extends RecyclerView.Adapter<MainCommunityAdapter.ViewHolder>{
    private ArrayList<MainCommunity> list;

    public MainCommunityAdapter(ArrayList<MainCommunity> list){
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_community_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(), CommunityActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = list.get(position).getTitle();
        String content = list.get(position).getShortContent();
        String writer = list.get(position).getWriter();
        String time = list.get(position).getTime();
        holder.title.setText(title);
        holder.content.setText(content);
        holder.writer.setText(writer);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView title;
        TextView content;
        TextView writer;
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.main_community_item_title);
            content = itemView.findViewById(R.id.main_coomunity_item_content);
            writer = itemView.findViewById(R.id.main_community_item_writer);
            time = itemView.findViewById(R.id.main_community_item_time);
        }
    }
}

