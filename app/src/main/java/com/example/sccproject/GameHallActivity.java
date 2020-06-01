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
import android.widget.Toast;

import com.example.sccproject.fragment.FirstFragment;
import com.example.sccproject.music.MusicServer;
import com.example.sccproject.net.NettyClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 游戏大厅
 */
@SuppressLint("Registered")
public class GameHallActivity extends AppCompatActivity {

    private static FragmentManager fm ;
    public static String xxx = "正在连接网络";
    private Intent intent;
    private Timer timer = new Timer();
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(10);


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
        //网络
        new Thread(new NettyClient("47.93.225.242", 8888)).start();
        //网络检查
        service.scheduleWithFixedDelay(new Beat(),3,3, TimeUnit.SECONDS);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Looper.prepare();
                while (true) {
                    if (NettyClient.isOk) {
                        Message msg = Message.obtain();
                        fragment.mHandler.sendMessage(msg);
                        break;
                    }
                }
                toastInternet();
                Looper.loop();
            }
        };
        timer.schedule(timerTask,1000);
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

    static class Beat implements Runnable{

        @Override
        public void run() {
            if(!NettyClient.isOk){
                new Thread(new NettyClient("47.93.225.242", 8888)).start();
            }
        }
    }

}
