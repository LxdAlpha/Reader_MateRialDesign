package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.BookReadActivity;
import com.example.alpha.reader_materialdesign.Domain.RecycleViewItemClickListener;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Alpha on 2018/1/6.
 */

public class BookReadDrawerChapterDataAdapter extends RecyclerView.Adapter<BookReadDrawerChapterDataAdapter.ViewHolder> {

    private TreeMap<Double, String> chapterStore;
    private ArrayList<Double> chapterPercentages;
    private ArrayList<String> chapterNames;
    private RecycleViewItemClickListener recycleViewItemClickListener;

    public BookReadDrawerChapterDataAdapter(TreeMap<Double, String> chapterStore) {
        this.chapterStore = chapterStore;
        chapterPercentages = new ArrayList<>();
        chapterNames = new ArrayList<>();
        for(Map.Entry<Double, String> entry: chapterStore.entrySet()){
            chapterPercentages.add(entry.getKey());
            chapterNames.add(entry.getValue());

        }
    }

    @Override
    public BookReadDrawerChapterDataAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_open, parent, false);
        final BookReadDrawerChapterDataAdapter.ViewHolder holder = new BookReadDrawerChapterDataAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                double percentage = chapterPercentages.get(position);
                //Log.d("lxd", chapterNames.get(position));
                recycleViewItemClickListener.onItemClick(percentage);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BookReadDrawerChapterDataAdapter.ViewHolder holder, int position) {
        String text = chapterNames.get(position);
        holder.name.setText(text);
    }

    @Override
    public int getItemCount() {
        return chapterNames.size();
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
