package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.BookReadActivity;
import com.example.alpha.reader_materialdesign.Domain.BookMark;
import com.example.alpha.reader_materialdesign.Domain.RecycleViewItemClickListener;
import com.example.alpha.reader_materialdesign.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by Alpha on 2017/12/23.
 */

public class BookReadDrawerMarkDataAdapter extends RecyclerView.Adapter<BookReadDrawerMarkDataAdapter.ViewHolder>{

    private ArrayList<BookMark> list;
    private RecycleViewItemClickListener recycleViewItemClickListener;

    public BookReadDrawerMarkDataAdapter(ArrayList<BookMark> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_open, parent, false);
        final BookReadDrawerMarkDataAdapter.ViewHolder holder = new BookReadDrawerMarkDataAdapter.ViewHolder(view); //要添加上final，否则数据会循环出现

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String name = list.get(position).getMarkname();
                ArrayList<BookMark> bookMark = (ArrayList<BookMark>) DataSupport.where("markname = ?", name).find(BookMark.class);
                Double location = bookMark.get(0).getLocation();
                //Log.d("lxd", location+"");

                recycleViewItemClickListener.onItemClick(location);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = list.get(position).getMarkname();
        //String text = list.get(position);
        holder.name.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.bookMark_name);
        }
    }

    public void setRecycleViewItemClickListener(RecycleViewItemClickListener recycleViewItemClickListener) {
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
