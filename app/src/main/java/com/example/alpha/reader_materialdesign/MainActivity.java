package com.example.alpha.reader_materialdesign;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.alpha.reader_materialdesign.Adapter.MainFragmentPagerAdapter;
import com.example.alpha.reader_materialdesign.Wifi.Constants;
import com.example.alpha.reader_materialdesign.Wifi.PopupMenuDialog;
import com.example.alpha.reader_materialdesign.Wifi.WebService;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import org.litepal.tablemanager.Connector;


public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Connector.getDatabase();

        ViewPager viewPager = findViewById(R.id.viewPager);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        context = this;
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                return true;
            case R.id.features:
                View view = findViewById(R.id.features);
                showPopupMenu(view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.features_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.account:
                        //Toast.makeText(MainActivity.this, "66", Toast.LENGTH_SHORT).show();
                        SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                        String name = pref.getString("login", "");
                        if(name.equals("false") || name.equals("")){
                            Intent acccountIntent = new Intent(MainActivity.this, AccountActivity.class);
                            startActivity(acccountIntent);
                        }else if(name.equals("true")){
                            Intent hadLoginIntent = new Intent(MainActivity.this, HadLoginActivity.class);
                            startActivity(hadLoginIntent);
                        }
                        return true;
                    case R.id.openLocalBook:
                        Intent intent = new Intent(MainActivity.this, FileOpenActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.transportBookByWifi:
                        Toast.makeText(MainActivity.this, "88", Toast.LENGTH_SHORT).show();
                        WebService.start(context);
                        new PopupMenuDialog(context).builder().setCancelable(false).setCanceledOnTouchOutside(false).show();
                        return true;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "99", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
    }


    @Subscribe(tags = {@Tag(Constants.RxBusEventType.POPUP_MENU_DIALOG_SHOW_DISMISS)})
    public void onPopupMenuDialogDismiss(Integer type) {
        if (type == Constants.MSG_DIALOG_DISMISS) {
            WebService.stop(this);
        }
    }
}
