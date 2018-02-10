package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.FindBookActivity;
import com.example.alpha.reader_materialdesign.FindBookActivityB;
import com.example.alpha.reader_materialdesign.FindBookActivityC;
import com.example.alpha.reader_materialdesign.R;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/13.
 */

public class MainFindAdapter extends RecyclerView.Adapter<MainFindAdapter.ViewHolder>{
    private ArrayList<String> list;

    public MainFindAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_find_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if(position == 1){
                    Intent intent = new Intent(parent.getContext(), FindBookActivityB.class);
                    intent.putExtra("kind", position + 1);
                    parent.getContext().startActivity(intent);
                }else if(position == 0){
                    Intent intent = new Intent(parent.getContext(), FindBookActivity.class);
                    intent.putExtra("kind", position + 1);
                    parent.getContext().startActivity(intent);
                }else if (position == 2){
                    Intent intent = new Intent(parent.getContext(), FindBookActivityC.class);
                    intent.putExtra("kind", position + 1);
                    parent.getContext().startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = list.get(position);
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
            name = itemView.findViewById(R.id.main_find_item_name);
        }
    }
}
