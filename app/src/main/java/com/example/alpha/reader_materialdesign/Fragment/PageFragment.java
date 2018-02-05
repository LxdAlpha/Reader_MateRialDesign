package com.example.alpha.reader_materialdesign.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alpha.reader_materialdesign.Adapter.MainCommunityAdapter;
import com.example.alpha.reader_materialdesign.Adapter.MainFindAdapter;
import com.example.alpha.reader_materialdesign.Adapter.MainShelfAdapter;
import com.example.alpha.reader_materialdesign.Domain.Book;
import com.example.alpha.reader_materialdesign.Domain.MainCommunity;
import com.example.alpha.reader_materialdesign.Domain.MainCommunityDataListener;
import com.example.alpha.reader_materialdesign.R;
import com.example.alpha.reader_materialdesign.Utils.CommunityUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alpha on 2017/11/30.
 */

public class PageFragment extends Fragment{
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    View view;
    ArrayList<Book> list = new ArrayList<>();
    ArrayList<MainCommunity> mainCommunityList = new ArrayList<>();
    ArrayList<String> findList = new ArrayList<>();
    public static PageFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        View view = inflater.inflate(R.layout.activity_main_viewer, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("第" + mPage + "页");
        return view;
        */
        if(mPage == 1){
            view = inflater.inflate(R.layout.activity_main_shelf, container, false);
            initShelf();
            RecyclerView recyclerView = view.findViewById(R.id.main_shelf_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDivider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(mDivider);
            MainShelfAdapter adapter = new MainShelfAdapter(list);
            recyclerView.setAdapter(adapter);
        }else if(mPage == 2){
            view = inflater.inflate(R.layout.activity_main_community, container, false);
            refreshMainCommunity(view);
            //设置下拉刷新,
            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_main_community);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshMainCommunity(view);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else if(mPage == 3){
            view = inflater.inflate(R.layout.activity_main_shelf, container, false);
            initFind();
            RecyclerView recyclerView = view.findViewById(R.id.main_shelf_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDivider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(mDivider);
            MainFindAdapter adapter = new MainFindAdapter(findList);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void initShelf(){
        list.clear();
        /*
        for(int i = 0; i < 20; i++){
            list.add("测试数据" + i);
        }
        */
        list = (ArrayList<Book>) DataSupport.findAll(Book.class);
    }

    private void initCommunity() {
        mainCommunityList.clear();
        //mainCommunityList = CommunityUtil.getMainCommunityItem();
        LoadTask loadTask = new LoadTask();
        loadTask.setMainCommunityDataListener(new MainCommunityDataListener() {
            @Override
            public void getData(ArrayList<MainCommunity> inList) {
                mainCommunityList = inList;
            }
        });
        loadTask.execute();
        while(mainCommunityList.size() == 0){ //等待LoadTask线程将数据获取完毕并执行回调函数将数据回送

        }
    }

    class LoadTask extends AsyncTask{
        ArrayList<MainCommunity> list = null;
        private MainCommunityDataListener mainCommunityDataListener;
        @Override
        protected ArrayList<MainCommunity> doInBackground(Object[] objects) {
            try {
                list = CommunityUtil.getMainCommunityItem();
                //mainCommunityList = list;
                //Log.d("lxd", mainCommunityList.size() + "长度1");
                mainCommunityDataListener.getData(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        public void setMainCommunityDataListener(MainCommunityDataListener mainCommunityDataListener) {
            this.mainCommunityDataListener = mainCommunityDataListener;
        }
    }

    private void refreshMainCommunity(View view){ //同时为初次加载数据和下拉刷新数据服务
        initCommunity();
        RecyclerView recyclerView = view.findViewById(R.id.main_shelf_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        MainCommunityAdapter adapter = new MainCommunityAdapter(mainCommunityList);
        recyclerView.setAdapter(adapter);
    }

    private void initFind(){
        findList.clear();
        String[] items = {"新书速递", "最受关注图书排行榜", "Top250排行榜"};
        for(int i = 0; i < items.length; i++){
            findList.add(items[i]);
        }
    }
}
