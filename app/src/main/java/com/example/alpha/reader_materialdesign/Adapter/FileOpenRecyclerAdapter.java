package com.example.alpha.reader_materialdesign.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alpha.reader_materialdesign.Domain.Book;
import com.example.alpha.reader_materialdesign.R;
import com.example.alpha.reader_materialdesign.Utils.DialogUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Alpha on 2017/12/2.
 */

public class FileOpenRecyclerAdapter extends RecyclerView.Adapter<FileOpenRecyclerAdapter.ViewHolder>{

    private ArrayList<File> list;

    public FileOpenRecyclerAdapter(ArrayList<File> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fileopen_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //响应item点击事件
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int position = holder.getAdapterPosition();
                final String name = list.get(position).getName();
                //Log.d("lxd", list.get(position).getAbsolutePath());
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                DialogUtils.showNormalDialog(view.getContext(), "是否将《" + name + "》加入书架", new DialogUtils.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick() {
                        Book book = new Book();
                        /*
                        try {
                            book.setName(new String(list.get(position).getName().getBytes("GB2312"), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        */
                        book.setName(list.get(position).getName());
                        book.setPath(list.get(position).getAbsolutePath());


                        book.save();

/*                        List<Book> books = DataSupport.findAll(Book.class);
                        Log.d("lxd", books.get(books.size()-1).getName() + "cd");
                        Toast.makeText(view.getContext(), books.get(books.size()-1).getName() + "cd", Toast.LENGTH_SHORT).show();*/
                        Toast.makeText(view.getContext(), name + "成功加入书架", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelButtonClick() {
                        super.onCancelButtonClick();
                    }
                });
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = list.get(position).getName();
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
