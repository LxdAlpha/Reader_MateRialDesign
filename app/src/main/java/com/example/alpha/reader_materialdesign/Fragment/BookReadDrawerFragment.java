package com.example.alpha.reader_materialdesign.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alpha.reader_materialdesign.Adapter.BookReadDrawerChapterDataAdapter;
import com.example.alpha.reader_materialdesign.Adapter.BookReadDrawerMarkDataAdapter;
import com.example.alpha.reader_materialdesign.BookReadActivity;
import com.example.alpha.reader_materialdesign.Domain.BookMark;
import com.example.alpha.reader_materialdesign.Domain.RecycleViewItemClickListener;
import com.example.alpha.reader_materialdesign.Domain.SerializableMap;
import com.example.alpha.reader_materialdesign.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Alpha on 2017/12/23.
 */

public class BookReadDrawerFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    public static final String BOOK_NAME = "book_name";
    public static final String CHAPTERSTORE_NAME = "chapterStore";
    private int mPage;
    View view;
    ArrayList<BookMark> list = new ArrayList<>();
    String bookName;
    TreeMap<Double, String> chapterStore;
    static RecycleViewItemClickListener context;


    public static BookReadDrawerFragment newMarkInstance(int page, String bookName, RecycleViewItemClickListener inContext){
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        args.putString(BOOK_NAME, bookName);
        BookReadDrawerFragment fragment = new BookReadDrawerFragment();
        fragment.setArguments(args);

        context = inContext;

        return fragment;
    }

    public static BookReadDrawerFragment newChapterInstance(int page, TreeMap<Double, String> chapterStore, RecycleViewItemClickListener inContext){
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(chapterStore);
        args.putSerializable(CHAPTERSTORE_NAME, serializableMap);
        BookReadDrawerFragment fragment = new BookReadDrawerFragment();
        fragment.setArguments(args);

        context = inContext;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
        if(mPage == 1) {
            bookName = getArguments().getString(BOOK_NAME);
        }else if(mPage == 2){
            chapterStore = ((SerializableMap) getArguments().getSerializable(CHAPTERSTORE_NAME)).getMap();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mPage == 1){
            view = inflater.inflate(R.layout.activity_bookread_drawer, container, false);
            initBookMark();
            RecyclerView recyclerView = view.findViewById(R.id.bookread_drawer_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDivider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(mDivider);
            BookReadDrawerMarkDataAdapter adapter = new BookReadDrawerMarkDataAdapter(list);

            adapter.setRecycleViewItemClickListener(context);

            recyclerView.setAdapter(adapter);
        }else if(mPage == 2){
            view = inflater.inflate(R.layout.activity_bookread_drawer, container, false);
            RecyclerView recyclerView = view.findViewById(R.id.bookread_drawer_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDivider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(mDivider);
            BookReadDrawerChapterDataAdapter adapter = new BookReadDrawerChapterDataAdapter(chapterStore);


            adapter.setRecycleViewItemClickListener(context);

            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void initBookMark(){
        list.clear();
        /*
        for(int i = 0; i < 20; i++){
            list.add("测试数据" + i);
        }
        */
        list = (ArrayList<BookMark>) DataSupport.where("bookName = ?", bookName).find(BookMark.class);
    }
}
