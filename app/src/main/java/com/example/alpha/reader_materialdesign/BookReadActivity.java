package com.example.alpha.reader_materialdesign;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.alpha.reader_materialdesign.Adapter.BookReadPagerAdapter;
import com.example.alpha.reader_materialdesign.Domain.Book;
import com.example.alpha.reader_materialdesign.Domain.BookMark;
import com.example.alpha.reader_materialdesign.Domain.RecycleViewItemClickListener;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.util.TypedValue.COMPLEX_UNIT_PX;


public class BookReadActivity extends AppCompatActivity{

    BufferedReader br;
    File file;
    String line;
    StringBuffer sb;
    ProgressBar progressBar;
    TextView textView;
    float textViewSize;
    SeekBar seekBar;
    SeekBar brightness_seekbar;
    Button systemBightness;
    //TreeMap<Integer, String> chapters = null;
    //TextView previousChapter;
    //TextView nextChapter;
    static int count;
    Button textSizeDecrease;
    Button textSizeIncrease;
    TextView textSizeShow;

    Button changeColorButton1;
    Button changeColorButton2;
    Button changeColorButton3;
    Button changeColorButton4;
    Button changeColorButton5;

    Toolbar toolbar;

    Dialog mCameraDialog;
    Dialog mDialog;

    DrawerLayout drawer;

    TreeMap<Integer, String> chapterStore = new TreeMap<>();
    TreeMap<Double, String> chapterStorePercent = new TreeMap<>();

    //RecycleViewItemClickListener recycleViewItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);


        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int w_screen = dm.widthPixels;
        final int h_screen = dm.heightPixels;

        textView = findViewById(R.id.book_read_view);
        progressBar = findViewById(R.id.progress_bar);

        drawer = findViewById(R.id.drawer_layout);

        //chapters = new TreeMap<Integer, String>();

        //Log.d("lxd", textView.getScrollBarSize() + "总长度");
        //setActivityBrightness(0.1f);

        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textViewSize = textView.getLineCount() * textView.getLineHeight();
                if(textViewSize > 1){
                    //textView.scrollTo(0, new Double(textViewSize*0.400602).intValue());
                }
                Log.d("lxd", textViewSize + "总长度");
                Log.d("lxd", textView.getLineCount() + " textView.getLineCount()");
                //Log.d("lxd", textView.getLineCount() + "总行数");
                //Log.d("lxd", textView.getLineHeight() + "一行高度");
                //Log.d("lxd", textView.getTextSize() + "textSize");
            }
        });

        //textView.scrollTo(0, 168408/3*2);
        //textView.setTextSize(COMPLEX_UNIT_PX, 35);


        final float[] touchXY = new float[4];


        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchXY[0] = motionEvent.getX();
                        touchXY[1] = motionEvent.getY();
                        //Log.d("lxd", touchXY[0] + "先" + touchXY[1]);
                        break;
                    case MotionEvent.ACTION_UP:
                        touchXY[2] = motionEvent.getX();
                        touchXY[3] = motionEvent.getY();

                        if(Math.abs(touchXY[2] - touchXY[0]) < 50 && Math.abs(touchXY[3] - touchXY[1]) < 50
                                && touchXY[2] > w_screen/4 && touchXY[2] < w_screen/4*3
                                && touchXY[3] > h_screen/5*2 && touchXY[3] < h_screen/5*3){
                            //Log.d("lxd", "可判定为单独点击屏幕中央");
                            //setTopDialog();
                            setDialog();
                            setTopDialog();
                            //toolbar.setVisibility(View.VISIBLE);
                        }
                        Log.d("lxd", textView.getScrollX() + " yzhou " + textView.getScrollY());
                        //Log.d("lxd", "count = " + count);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        String name = getIntent().getStringExtra("bookName");

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        ArrayList<Book> books = (ArrayList<Book>) DataSupport.where("name = ?", name).find(Book.class);
        //ArrayList<Book> books = (ArrayList<Book>) DataSupport.findAll(Book.class);
        String filePath = books.get(0).getPath();
        sb = new StringBuffer();
        file = new File(filePath);
        line = "";
        LoadTask test = new LoadTask();
        test.execute();
        //count = test.count;
        //Log.d("lxd", "test.count" + test.count);

        //Log.d("lxd", textView.getScrollBarSize() + "");

        //Log.d("lxd", textView.getTextSize()+"");


        //由于chapterStorePercent须在加载玩内容后方有数据，如果在此处构造抽屉的话目录内容为空，故将下面代码注释去除移往LoadTask的onPostxcute内执行
        /*
        ViewPager viewPager = findViewById(R.id.viewPager_drawer);
        BookReadPagerAdapter adapter = new BookReadPagerAdapter(getSupportFragmentManager(), this, getIntent().getStringExtra("bookName"), chapterStorePercent);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout_drawer);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);*/


        //recycleViewItemClickListener = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewSize = textView.getLineCount() * textView.getLineHeight();
        if(textViewSize > 1){
            //textView.scrollTo(0, new Double(textViewSize*0.400602).intValue());
            Intent intent = getIntent();
            int bookMarkFlag = intent.getIntExtra("bookMarkFlag", 0);
            float bookMarkLocation = intent.getFloatExtra("bookMarkLocation", 0);
            Log.d("lxd", "bookMarkFlag "+ bookMarkFlag);
            if(bookMarkFlag == 1){
                Log.d("lxd", "书签跳转啦");
                textView.scrollTo(0, new Double(textViewSize*bookMarkLocation).intValue());
            }
        }
    }



    class LoadTask extends AsyncTask<Void, Integer, Boolean>{
        int count = 0;
        String pattern1 = "　　.+";
        Pattern r = Pattern.compile(pattern1);
        Matcher m = null;


        @Override
        protected Boolean doInBackground(Void... params) {



            try {
                br = new BufferedReader(new FileReader(file));
                while((line = br.readLine())!=null){
                    m = r.matcher(line);

                    if(!m.find() && !line.isEmpty()){
                        //Log.d("lxd", line);
                        //Log.d("lxd", count+"");
                        //chapters.put(count, line);
                        sb.append(line + "-------------------\n");

                        double a = line.length();
                        double b = 20.7143;//32 20.7143
                        chapterStore.put(count, line);
                        count = count + (int)(Math.ceil(a / b));

                    }else{
                        sb.append(line + "\n");

                        double a = line.length();
                        double b = 20.7143;//32 20.7143
                        count = count + (int)(Math.ceil(a / b));
                    }
                    //count++;
                }
                br.close();
                Log.d("lxd", count+"");
                //Log.d("lxd", count+"doINBack");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            String text = sb.toString();
            textView.setText(text);
            //CharSequence cg = textView.getText();
            //Log.d("lxd", cg.toString());
            //Log.d("lxd", count+"onPostExecute");
            BookReadActivity.count = count;

            textView.setBackgroundColor(Color.parseColor("#C7B78F"));
            textView.setTextColor(Color.parseColor("#534C3F"));

            DisplayMetrics dm =getResources().getDisplayMetrics();
            Log.d("lxd", "textView.getMaxWidth()/textView.getTextSize() " + (double)dm.widthPixels/(28+5.793));


            for(Map.Entry<Integer, String> entry : chapterStore.entrySet()){
                //Log.d("lxd", ((double)entry.getKey()/(double)count) + " " + entry.getValue());
                chapterStorePercent.put((double)entry.getKey()/(double)count, entry.getValue());
            }


            ViewPager viewPager = findViewById(R.id.viewPager_drawer);
            BookReadPagerAdapter adapter = new BookReadPagerAdapter(getSupportFragmentManager(), getParent(), getIntent().getStringExtra("bookName"), chapterStorePercent);


            //Log.d("lxd", recycleViewItemClickListener+"no1");
            //adapter.setRecycleViewItemClickListener(recycleViewItemClickListener);
            adapter.setRecycleViewItemClickListener(new RecycleViewItemClickListener() {
                @Override
                public void onItemClick(double chapterInPercent) {
                    drawer.closeDrawers();
                    textView.scrollTo(0, new Double(textViewSize*chapterInPercent).intValue());
                }

            });

            viewPager.setAdapter(adapter);

            TabLayout tabLayout = findViewById(R.id.tabLayout_drawer);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);




        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setDialog() {
        mCameraDialog = new Dialog(this, R.style.BottomDialog); //加载样式
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM); //窗口中的控件向下对齐
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);

        seekBar = root.findViewById(R.id.id_seekBar);
        seekBar.setMax((int) textViewSize);
        seekBar.setProgress(textView.getScrollY());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.scrollTo(0, seekBar.getProgress());
            }
        });

        brightness_seekbar = root.findViewById(R.id.brightness_seekBar);
        brightness_seekbar.setMax(255);
        brightness_seekbar.setProgress(getActivityBrightness());
        brightness_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("lxd", brightness_seekbar.getProgress()+"");
                setActivityBrightness(Float.parseFloat(brightness_seekbar.getProgress()+"")/255);
                brightness_seekbar.setProgress(getActivityBrightness());
            }
        });

        systemBightness = root.findViewById(R.id.systemBrightness);
        systemBightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToSystemBtrghtness();
                brightness_seekbar.setProgress(getActivityBrightness());
            }
        });


        textSizeShow = root.findViewById(R.id.textSizeShow);
        textSizeShow.setText(String.valueOf(Float.valueOf(textView.getTextSize()).intValue()));

        textSizeDecrease = root.findViewById(R.id.textSizeDecrease);
        textSizeDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int textSize = Integer.valueOf(textSizeShow.getText().toString());
               textSize--;
               textView.setTextSize(COMPLEX_UNIT_PX, Float.valueOf(textSize+""));
               textSizeShow.setText(textSize+"");
            }
        });
        textSizeIncrease = root.findViewById(R.id.textSizeIncrease);
        textSizeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textSize = Integer.valueOf(textSizeShow.getText().toString());
                textSize++;
                textView.setTextSize(COMPLEX_UNIT_PX, Float.valueOf(textSize+""));
                textSizeShow.setText(textSize+"");
            }
        });

        changeColorButton1 = root.findViewById(R.id.changeColorButton1);
        changeColorButton2 = root.findViewById(R.id.changeColorButton2);
        changeColorButton3 = root.findViewById(R.id.changeColorButton3);
        changeColorButton4 = root.findViewById(R.id.changeColorButton4);
        changeColorButton5 = root.findViewById(R.id.changeColorButton5);

        changeColorButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#F5F4F0"));
                textView.setTextColor(Color.parseColor("#3D3D3D"));
            }
        });
        changeColorButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#C7B78F"));
                textView.setTextColor(Color.parseColor("#534C3F"));
            }
        });
        changeColorButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#393331"));
                textView.setTextColor(Color.parseColor("#93918D"));
            }
        });
        changeColorButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#CCE8CF"));
                textView.setTextColor(Color.parseColor("#616962"));
            }
        });
        changeColorButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#001C29"));
                textView.setTextColor(Color.parseColor("#43555F"));
            }
        });

        mCameraDialog.show();


        /*

        for(Map.Entry<Integer, String> entry : chapters.entrySet()){
            //Log.d("lxd", "key=" + entry.getKey() + " value=" + entry.getValue());
        }
        */


        //previousChapter = root.findViewById(R.id.previousChapter);
        //nextChapter = root.findViewById(R.id.nextChapter);

        //final float nowPosition = textView.getScrollY() / textViewSize;

        /*
        previousChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> temp = new ArrayList<>();
                int index = 0;
                for(Map.Entry<Integer, String> entry : chapters.entrySet()){
                    temp.add(entry.getKey());
                    float calculateChapterLocation = Float.parseFloat(entry.getKey()+"") / Float.parseFloat(count+"");
                    index++;
                    if(calculateChapterLocation > nowPosition) {
                        break;
                    }
                }
                if(index <= 3) {
                    textView.scrollTo(0, 1);
                }
                else {
                    textView.scrollTo(0, (int) (textViewSize * temp.get(index - 1 - 2) / count));
                }
            }
        });
        nextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lxd", "nextChapter BookReadActivity.count" + BookReadActivity.count);
                ArrayList<Integer> temp = new ArrayList<>();
                int index = 0;
                float calculateChapterLocation = 0;
                for(Map.Entry<Integer, String> entry : chapters.entrySet()){
                    //Log.d("lxd", "key=" + entry.getKey() + " value=" + entry.getValue());
                    temp.add(entry.getKey());
                    //Log.d("lxd", "entry.getKey() = " + entry.getKey());
                    //Log.d("lxd", "entry.getValue() = " + entry.getValue());
                    //Log.d("lxd", "count = " + count);
                    calculateChapterLocation = Float.parseFloat(entry.getKey()+"") / Float.parseFloat(count+"");
                    index++;
                    if(calculateChapterLocation > nowPosition) {
                        Log.d("lxd", "calculateChapterLocation = " + calculateChapterLocation);
                        Log.d("lxd", "nowPosition = " + nowPosition);
                        break;
                    }
                }
                Log.d("lxd", "index = "+index);
                Log.d("lxd", "chapters.size() = "+chapters.size());
                if(index == chapters.size()) {

                }
                else {
                    Log.d("lxd",  "temp.size() " + temp.size());
                    Log.d("lxd", "temp.get(temp.size()-1) " + temp.get(temp.size()-1));
                    Log.d("lxd", "(temp.get(temp.size()-1) / count + 0.005) " + (temp.get(temp.size()-1) / count + 0.005));
                    Log.d("lxd", "temp.get(temp.size()-1) / count " + Float.parseFloat(temp.get(temp.size()-1)+"") / Float.parseFloat(count + ""));
                    Log.d("lxd", "count + "  + count);
                    Log.d("lxd", "temp.get(temp.size()-1) " + temp.get(temp.size()-1));
                    Log.d("lxd", "textViewSize * (temp.get(temp.size()-1) / count + 0.005  " + textViewSize * (Float.parseFloat(temp.get(temp.size()-1)+"") / Float.parseFloat(count + "") + 0.005));
                    Log.d("lxd", "ok");
                    textView.scrollTo(0, (int) (textViewSize * (temp.get(temp.size()-1) / count + 0.005) ));

                }

            }
        });
        */

    }

    private void setTopDialog(){
        mDialog = new Dialog(this, R.style.TopDialog); //加载样式
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.top_dialog, null);
        //初始化视图

        mDialog.setContentView(root);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.TOP); //窗口中的控件向下对齐
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);




        mDialog.setOnDismissListener(new Dialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //mCameraDialog.dismiss();
            }
        });





        ImageView back = root.findViewById(R.id.back_bookRead);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        ImageView bookMark = root.findViewById(R.id.bookMark_bookRead);
        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookMarkInputDialog();
            }
        });

        TextView tableOfContent = root.findViewById(R.id.tableOfContent);
        tableOfContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
                mDialog.dismiss();
                drawer.openDrawer(GravityCompat.START);
            }
        });

        mDialog.show();

    }

    private void showBookMarkInputDialog(){

        new MaterialDialog.Builder(this)
                .title("书签")
                .content("请输入书签名称")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .widgetColor(Color.parseColor("#000000"))
                .positiveColor(Color.parseColor("#000000"))
                .input("书签名字", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        BookMark bookMark = new BookMark();
                        bookMark.setBookName(getIntent().getStringExtra("bookName"));
                        bookMark.setMarkname(input.toString());
                        bookMark.setLocation((double) (textView.getScrollY()/textViewSize));
                        bookMark.save();
                    }
                }).show();

    }

    private void backActivity(){
        mCameraDialog.dismiss();
        mDialog.dismiss();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BookReadActivity.count = 0;
    }

    public int getActivityBrightness() {
        int screenBrightness = 0;
        Window localWindow = this.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        if (Math.abs(params.screenBrightness-(-1.0))<0.00000001){
            try {
                screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return screenBrightness;
        }
        return (int) (params.screenBrightness*255);
    }

    public void setActivityBrightness(float paramFloat) {
        Window localWindow = this.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        params.screenBrightness = paramFloat;
        localWindow.setAttributes(params);
    }

    public void setToSystemBtrghtness(){
        int screenBrightness = 0;
        Window localWindow = this.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        params.screenBrightness = Float.parseFloat(screenBrightness+"")/255f;
        localWindow.setAttributes(params);
    }

/*
    @Override
    public void onItemClick() {
        drawer.closeDrawers();
    }*/
}
