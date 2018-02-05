package com.example.alpha.reader_materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alpha.reader_materialdesign.Adapter.CommentAdapter;
import com.example.alpha.reader_materialdesign.Adapter.ShortCommentsAdapter;
import com.example.alpha.reader_materialdesign.Domain.BookBasicInfomation;
import com.example.alpha.reader_materialdesign.Domain.BookCommentItem;
import com.example.alpha.reader_materialdesign.Domain.BookView;
import com.example.alpha.reader_materialdesign.Domain.ShortComment;
import com.example.alpha.reader_materialdesign.Utils.BookUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FindBookDetailActivity extends AppCompatActivity {
    BookView bookInfomation;
    Context context;
    String url;
    TextView bookName;
    TextView score;
    RatingBar ratingBar;
    TextView evaluateNumber;
    ImageView img;
    TextView writer;
    TextView publisher;
    TextView pages;
    TextView binding;
    TextView publishYear;
    LinearLayout findBookDetailTags;
    Button[] testButtons;
    TagFlowLayout mFlowLayout;
    TextView bookDescription;
    RecyclerView shortCommentsView;
    TextView viewMoreCommments;
    RecyclerView commentsView;
    TextView viewAllCommments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra("url");

        setContentView(R.layout.activity_find_book_detail);
        context = this;
        bookName = findViewById(R.id.findBookDetailBookName);
        score = findViewById(R.id.findBookDetailRatingScore);
        ratingBar = findViewById(R.id.findBookDetailRatingBar);
        evaluateNumber = findViewById(R.id.findBookDetailEvaluateNumber);
        writer = findViewById(R.id.findBookDetailWriter);
        publisher = findViewById(R.id.findBookDetailPublisher);
        pages = findViewById(R.id.findBookDetailPages);
        binding = findViewById(R.id.findBookDetailBindings);
        publishYear = findViewById(R.id.findBookDetailPublishYear);
        img = findViewById(R.id.findBookDetailImg);
        findBookDetailTags = findViewById(R.id.findBookDetailTags);
        mFlowLayout = findViewById(R.id.id_flowlayout);
        bookDescription = findViewById(R.id.findBookDetailIntroduction);

        viewMoreCommments = findViewById(R.id.findBookMoreCommments);

        shortCommentsView =  (RecyclerView)findViewById(R.id.findBookShortComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shortCommentsView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        shortCommentsView.addItemDecoration(mDivider);

        commentsView = findViewById(R.id.findBookComments);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        commentsView.setLayoutManager(layoutManager1);
        DividerItemDecoration mDivider1 = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        commentsView.addItemDecoration(mDivider1);

        viewAllCommments = findViewById(R.id.findBookCommments);



        new getBookView().execute();

        new getBookTags().execute();


        new getBookDescription().execute();


        new getShortComments().execute();

        new getMoreShortComments().execute();

        new getComments().execute();

        new getAllComments().execute();
    }

    class getBookView extends AsyncTask<Void, Void, BookBasicInfomation>{

        @Override
        protected BookBasicInfomation doInBackground(Void... voids) {
            try {
                return BookUtil.getBookBasicInformation(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(BookBasicInfomation bookBasicInfomation){
            super.onPostExecute(bookBasicInfomation);
            bookName.setText(bookBasicInfomation.getBookName());
            score.setText(bookBasicInfomation.getScore());
            ratingBar.setRating(Float.parseFloat(bookBasicInfomation.getScore()) / 2);
            evaluateNumber.setText("评价人数：" + bookBasicInfomation.getEvaluateNumber() + "人评价");
            writer.setText("作者:" + bookBasicInfomation.getWriter());
            publisher.setText("出版社：" + bookBasicInfomation.getPublisher());
            pages.setText("页数：" + bookBasicInfomation.getPages() + "页");
            binding.setText("装帧：" + bookBasicInfomation.getBinding());
            publishYear.setText("出版时间：" + bookBasicInfomation.getPublishYear());
            Glide.with(context).load(bookBasicInfomation.getImgUrl()).into(img);

            /*
            testButton.setText("魔幻");
            testButton.setWidth(20);
            testButton.setHeight(10);
            findBookDetailTags.addView(testButton);
            */
        }
    }

    class getBookTags extends AsyncTask<Void, Void, LinkedHashMap<String, String>>{

        @Override
        protected LinkedHashMap<String, String> doInBackground(Void... voids) {
            return BookUtil.getBookTags(url);
        }

        @Override
        protected void onPostExecute(LinkedHashMap<String, String> tags) {
            super.onPostExecute(tags);

            testButtons = new Button[tags.size()];
            final ArrayList<String> tagName = new ArrayList<>();
            final ArrayList<String> tagUrl = new ArrayList<>();
            for(Map.Entry<String, String> entry: tags.entrySet()){
                tagName.add(entry.getKey());
                tagUrl.add(entry.getValue());
            }
            mFlowLayout.setAdapter(new TagAdapter<String>(tagName) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    Button button = (Button) LayoutInflater.from(context).inflate(R.layout.tags_button, mFlowLayout, false);
                    button.setText(tagName.get(position));
                    return button;
                }
            });
            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Intent intent = new Intent(parent.getContext(), CommunityActivity.class);
                    intent.putExtra("url", tagUrl.get(position));
                    parent.getContext().startActivity(intent);
                    return true;
                }
            });
        }
    }

    class getBookDescription extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return BookUtil.getBookDescription(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RichText.fromHtml(s).into(bookDescription);
        }
    }

    class getShortComments extends AsyncTask<Void, Void, ArrayList<ShortComment>> {


        @Override
        protected ArrayList<ShortComment> doInBackground(Void... voids) {
            return BookUtil.getShortComments(url);
        }

        @Override
        protected void onPostExecute(ArrayList<ShortComment> shortComments) {
            super.onPostExecute(shortComments);
            ShortCommentsAdapter adapter = new ShortCommentsAdapter(shortComments);
            shortCommentsView.setAdapter(adapter);

        }

    }

    class getMoreShortComments extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return BookUtil.getMoreShortComments(url);
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            viewMoreCommments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommunityActivity.class);
                    intent.putExtra("url", s);
                    context.startActivity(intent);
                }
            });
        }
    }

    class getComments extends AsyncTask<Void, Void, ArrayList<BookCommentItem>> {


        @Override
        protected ArrayList<BookCommentItem> doInBackground(Void... voids) {
            return BookUtil.getBookCommentsArr(url);
        }

        @Override
        protected void onPostExecute(ArrayList<BookCommentItem> comments) {
            super.onPostExecute(comments);
            CommentAdapter adapter = new CommentAdapter(comments);
            commentsView.setAdapter(adapter);
        }

    }

    class getAllComments extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return BookUtil.getMoreComments(url);
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            viewAllCommments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommunityActivity.class);
                    intent.putExtra("url", s);
                    context.startActivity(intent);
                }
            });
        }
    }
}
