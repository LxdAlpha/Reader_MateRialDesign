package com.example.alpha.reader_materialdesign.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alpha.reader_materialdesign.Domain.ShortComment;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/31.
 */

public class ShortCommentsAdapter extends RecyclerView.Adapter<ShortCommentsAdapter.ViewHolder>{

    private ArrayList<ShortComment> list;

    public ShortCommentsAdapter(ArrayList<ShortComment> ShortComments) {
        list = ShortComments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.short_comment_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!list.get(position).getShortCommentWriterPortrait().equals(""))
            Glide.with(holder.view).load(list.get(position).getShortCommentWriterPortrait()).into(holder.image);
        holder.writer.setText(list.get(position).getShortCommentWriterName());
        holder.time.setText(list.get(position).getShortCommentTime());
        holder.content.setText(list.get(position).getShortCommentContent());
        holder.vote.setText(list.get(position).getShortCommentVoteCount()+"赞");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView image;
        TextView writer;
        TextView time;
        TextView content;
        TextView vote;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.writer_image);
            writer = itemView.findViewById(R.id.shortCommentWriterName);
            time = itemView.findViewById(R.id.shortCommentTime);
            content = itemView.findViewById(R.id.shortCommentContent);
            vote = itemView.findViewById(R.id.shortCommentVoteCount);
        }
    }

}
