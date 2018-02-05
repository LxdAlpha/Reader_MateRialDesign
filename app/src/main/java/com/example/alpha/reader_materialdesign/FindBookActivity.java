package com.example.alpha.reader_materialdesign;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.alpha.reader_materialdesign.Adapter.FindNewBookRecommendAdapter;
import com.example.alpha.reader_materialdesign.Domain.FindBookItem;
import com.example.alpha.reader_materialdesign.Utils.RecommendUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindBookActivity extends AppCompatActivity {

    private ArrayList<FindBookItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);
        Toolbar toolbar = findViewById(R.id.bookRecommendToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        new LoadTask().execute();
        while(list.size() == 0){

        }

        RecyclerView recyclerView = findViewById(R.id.main_find_newBookShow_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        FindNewBookRecommendAdapter adapter = new FindNewBookRecommendAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    class LoadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if(getIntent().getIntExtra("kind", -1) == 1) {
                initBookRecommend();
            }else if(getIntent().getIntExtra("kind", -1) == 2){
                initBookConcerned();
            }else if(getIntent().getIntExtra("kind", -1) == 3){
                initBookTop250();
            }
            return null;
        }
    }

    private void initBookRecommend(){
        list.clear();
        try {
            list = RecommendUtil.crawlerExecute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initBookConcerned(){
        list.clear();
        list = RecommendUtil.getConcernedBook();
    }

    private void initBookTop250(){
        list.clear();
        list = RecommendUtil.getTop250Book();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
