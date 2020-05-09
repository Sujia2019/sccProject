package com.example.sccproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

public class TestActivity extends AppCompatActivity {
    private static int number = 5;
    private static int widths;
    private static int height;
    private static int level = 5;
    private static int mapWidths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metric = new DisplayMetrics();//获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(metric);//获取屏幕信息
        widths = metric.widthPixels;// 屏幕宽度（像素）
        height = metric.heightPixels;//屏幕高度
        mapWidths = level*widths;//地图长度
        //使用自定义的视图
//        setContentView(new MyView(this,5));
    }
}
