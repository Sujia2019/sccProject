package com.example.sccproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sccproject.fragment.FirstFragment;
import com.example.sccproject.fragment.GameFragment;
import com.example.sccproject.music.MusicServer;
import com.example.sccproject.net.NettyClient;


/**
 * 游戏大厅
 */
@SuppressLint("Registered")
public class GameHallActivity extends AppCompatActivity {

    private static FragmentManager fm ;
    private ImageButton btn ;
    private static NettyClient nettyClient;
    private static boolean isNetOk = false;


    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(GameHallActivity.this, MusicServer.class);
        super.onCreate(savedInstanceState);
        startService(intent);

        final Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        setContentView(R.layout.layout_main);
        fm = getSupportFragmentManager();

        //开启客户端线程
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //                nettyClient = new NettyClient("localhost",8888);
    ////                isNetOk = true;
    //            }
//        }).start();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                nettyClient = new NettyClient("localhost",8888);
//                Looper.prepare();
//                if(!NettyClient.isOk){
//
//                    toastInternet();
//                }
//                Looper.loop();
            }
        });

        createFragment();


    }

    public static void replaceFragment(){
        fm.beginTransaction().replace(R.id.layout_container,new GameFragment()).commit();
    }

    public static void createFragment(){
        Fragment fragment = fm.findFragmentById(R.id.layout_container);
        if(fragment == null){
            fragment = new FirstFragment();
            //first
            fm.beginTransaction().add(R.id.layout_container,fragment).commit();
        }
    }

    public void toastInternet(){
        Toast.makeText(GameHallActivity.this,"Error Internet",Toast.LENGTH_SHORT).show();

    }


}
