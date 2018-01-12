package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.alpha.reader_materialdesign.Domain.RecycleViewItemClickListener;
import com.example.alpha.reader_materialdesign.Fragment.BookReadDrawerFragment;

import java.util.TreeMap;

/**
 * Created by Alpha on 2017/12/23.
 */

public class BookReadPagerAdapter extends FragmentPagerAdapter{

    public final int COUNT = 2;
    private String[] titles = new String[]{"书签", "目录"};
    private Context context;
    private String bookName;
    private TreeMap<Double, String> chapterStore;
    private RecycleViewItemClickListener recycleViewItemClickListener;

    public BookReadPagerAdapter(FragmentManager fm, Context context, String InbookName, TreeMap<Double, String> inChapterStore) {
        super(fm);
        this.context = context;
        bookName = InbookName;
        chapterStore = inChapterStore;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return BookReadDrawerFragment.newMarkInstance(position + 1, bookName, recycleViewItemClickListener);
        }else if(position == 1){
            return BookReadDrawerFragment.newChapterInstance(position+1, chapterStore, recycleViewItemClickListener);
        }else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
        //return "中国";
    }

    public void setRecycleViewItemClickListener(RecycleViewItemClickListener recycleViewItemClickListener) {
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
