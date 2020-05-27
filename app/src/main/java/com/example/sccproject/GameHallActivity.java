package com.example.sccproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sccproject.fragment.FirstFragment;
import com.example.sccproject.music.MusicServer;
import com.example.sccproject.net.NettyClient;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 游戏大厅
 */
@SuppressLint("Registered")
public class GameHallActivity extends AppCompatActivity {

    private static FragmentManager fm ;
    private ImageButton btn ;
    private static NettyClient nettyClient;
    private static boolean isNetOk = false;
    public static String xxx = "正在连接网络";
    private Intent intent;
    private Timer timer = new Timer();
    private TimerTask timerTask;


    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        intent = new Intent(GameHallActivity.this, MusicServer.class);
        super.onCreate(savedInstanceState);
        startService(intent);
        setContentView(R.layout.layout_main);
        fm = getSupportFragmentManager();
        FirstFragment fragment = (FirstFragment)createFragment();
        toastInternet();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                nettyClient = new NettyClient("47.93.225.242",8888);
                Looper.prepare();
                try {
                    //模拟网络连接 成功的话才会显示PLAY按钮
                    Thread.sleep(600);
//                    if(NettyClient.isOk){
                        Message msg = Message.obtain();
                        fragment.mHandler.sendMessage(msg);
//                    }else{
//                        xxx="网络连接超时";
//                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toastInternet();
                Looper.loop();
            }
        };
        timer.schedule(timerTask,10);
//        AsyncTask.execute(() -> {
//            nettyClient = new NettyClient("47.93.225.242",8888);
//            Looper.prepare();
//            if(!NettyClient.isOk){
//                toastInternet();
//            }else{
//                System.out.println("false");
//            }
//            Looper.loop();
//        });
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        timer.cancel();
        super.onDestroy();
    }

    public static void replaceFragment(Fragment fragment){
        fm.beginTransaction().replace(R.id.layout_container,fragment).commit();
    }

    public static Fragment createFragment(){
        Fragment fragment = fm.findFragmentById(R.id.layout_container);
        if(fragment == null){
            fragment = new FirstFragment();
            //first
            fm.beginTransaction().add(R.id.layout_container,fragment).commit();
        }
        return fragment;
    }

    public void toastInternet(){
        Toast.makeText(GameHallActivity.this,xxx,Toast.LENGTH_SHORT).show();
    }


}
