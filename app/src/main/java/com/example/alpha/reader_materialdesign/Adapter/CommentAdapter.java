package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.CommunityActivity;
import com.example.alpha.reader_materialdesign.Domain.BookCommentItem;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/2/2.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private ArrayList<BookCommentItem> list;

    public CommentAdapter(ArrayList<BookCommentItem> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), CommunityActivity.class);
                intent.putExtra("url", list.get(holder.getAdapterPosition()).getBookCommentItemUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(list.get(position).getBookCommentItemShortContent());
        holder.name.setText(list.get(position).getBookCoomentItemWriter());
        holder.time.setText(list.get(position).getBookCommentItemTime());
        holder.title.setText(list.get(position).getBookCommentItemTitle());
        holder.support.setText(list.get(position).getBookCommentItemSupport() + "顶");
        holder.oppose.setText(list.get(position).getBookCommentItemOppose() + "踩");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView time;
        TextView content;
        TextView support;
        TextView oppose;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.commentTitle);
            name = itemView.findViewById(R.id.commentWriterName);
            time = itemView.findViewById(R.id.commentTime);
            content = itemView.findViewById(R.id.commentContent);
            support = itemView.findViewById(R.id.commentSupport);
            oppose = itemView.findViewById(R.id.commentOppose);
        }
    }
}
