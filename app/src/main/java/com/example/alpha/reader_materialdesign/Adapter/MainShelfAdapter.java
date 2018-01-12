package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.BookReadActivity;
import com.example.alpha.reader_materialdesign.Domain.Book;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2017/12/10.
 */

public class MainShelfAdapter extends RecyclerView.Adapter<MainShelfAdapter.ViewHolder>{
    private ArrayList<Book> list;

    public MainShelfAdapter(ArrayList<Book> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fileopen_item, parent, false);
        final ViewHolder holder = new ViewHolder(view); //要添加上final，否则数据会循环出现

        //响应主页面单击书本操作
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAdapterPosition();
                String name = list.get(position).getName();

                Intent intent = new Intent(parent.getContext(), BookReadActivity.class);
                intent.putExtra("bookName", name);
                parent.getContext().startActivity(intent);


                //ArrayList<Book> books = (ArrayList<Book>) DataSupport.where("name = ?", name).find(Book.class);
                //String filePath = books.get(0).getPath();
                //HwTxtPlayActivity.loadTxtFile(parent.getContext(), filePath);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = list.get(position).getName();
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
            name = itemView.findViewById(R.id.file_name);
        }
    }
}
