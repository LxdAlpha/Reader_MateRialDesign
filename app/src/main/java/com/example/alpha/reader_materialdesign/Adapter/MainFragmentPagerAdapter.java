package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alpha.reader_materialdesign.Fragment.PageFragment;

/**
 * Created by Alpha on 2017/12/1.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{

    public final int COUNT = 3;
    private String[] titles = new String[]{"书架", "社区", "发现"};
    private Context context;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
