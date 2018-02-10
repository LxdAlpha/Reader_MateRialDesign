package com.example.alpha.reader_materialdesign;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alpha.reader_materialdesign.Adapter.FileOpenRecyclerAdapter;
import com.example.alpha.reader_materialdesign.Utils.Util;

import java.io.File;
import java.util.ArrayList;

public class FileOpenActivity extends AppCompatActivity {

    //private ArrayList<String> list = new ArrayList<>();
    private ArrayList<File> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_file);
        Toolbar toolbar = findViewById(R.id.fileOpenToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //返回按钮

        init();
        RecyclerView recyclerView = findViewById(R.id.open_file_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);

        FileOpenRecyclerAdapter adapter = new FileOpenRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    //响应点击Toolbar返回按钮事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Intent intent = new Intent(this, MainActivity.class);
                //startActivity(intent);
                finish();
                Log.d("lxd", "finish");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        //ArrayList<File> files = (ArrayList<File>) Util.getSuffixFile(new ArrayList<File>(), "/storage/emulated/legacy/Download", ".txt");
        //Log.d("lxd", String.valueOf(Environment.getExternalStorageDirectory()));
        ArrayList<File> files = (ArrayList<File>) Util.getSuffixFile(new ArrayList<File>(), String.valueOf(Environment.getExternalStorageDirectory()), ".txt");
        //ArrayList<File> files = (ArrayList<File>) Util.getSuffixFile(new ArrayList<File>(), "/sdcard", ".txt");
        /*
        for(int i = 0; i < files.size(); i++){
            list.add(files.get(i).getName());
        }*/
        list = files;
    }
}
