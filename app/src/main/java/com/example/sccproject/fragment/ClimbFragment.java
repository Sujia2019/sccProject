package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.sccproject.R;
import com.example.sccproject.util.TurnHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static android.content.ContentValues.TAG;

/**
 * Created by alienware on 2020/4/13.
 */

public class ClimbFragment extends Fragment {
    private int widths;
    private int height;
    private Button buttonUp;
    private Button buttonDown;
    private Button buttonLeft;
    private Button buttonRight;
    private ScheduledExecutorService service ;
    private ImageView role;
    private ImageView monster;
    private ImageButton s1;
    private ImageButton s2;
    private ImageButton s3;
    private ImageView attack;
    private boolean isRight = true;//人物是否朝着右边走
    private static volatile boolean isStop = true;//true=stop;false=run

    private int[] humanBorn = new int[]{
            R.drawable.born1,R.drawable.born2,R.drawable.born3,R.drawable.born4,
            R.drawable.born5,R.drawable.born6,R.drawable.born7,R.drawable.born8,
            R.drawable.born9,R.drawable.born10,R.drawable.born11,R.drawable.born12,
            R.drawable.born13,R.drawable.born14,R.drawable.born15,R.drawable.born16,
            R.drawable.born17
    };
    private int[] roleMove = new int[]{
            R.drawable.run1,R.drawable.run2,R.drawable.run3,R.drawable.run4
    };
    private AnimationDrawable animationMonster;
    private AnimationDrawable animationRole;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        service = Executors.newScheduledThreadPool(20);
        View view = inflater.inflate(R.layout.fragment_climb,container,false);

        DisplayMetrics metric = new DisplayMetrics();//获取屏幕信息
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);//获取屏幕信息
        widths = metric.heightPixels;// 屏幕宽度（像素）
        height = metric.heightPixels;

        final ConstraintLayout layout = (ConstraintLayout)view.findViewById(R.id.frameLayout2);
        buttonUp = (Button)view.findViewById(R.id.button_up);
        buttonDown = (Button)view.findViewById(R.id.button_down);
        buttonLeft = (Button)view.findViewById(R.id.button_left);
        buttonRight = (Button)view.findViewById(R.id.button_right);
        role = (ImageView)view.findViewById(R.id.role);
        s1 = (ImageButton)view.findViewById(R.id.whirlwind);
        s2 = (ImageButton)view.findViewById(R.id.swift);
        s3 = (ImageButton)view.findViewById(R.id.strength);
        attack = (ImageView)view.findViewById(R.id.combo_attack);
        Runnable r = () -> {
            int index = 0;
            int length = humanBorn.length;
            while (index!=length-1){
                role.setImageResource(humanBorn[index]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
            }
            role.setVisibility(View.INVISIBLE);
            role = (ImageView)view.findViewById(R.id.role_stuck) ;
            role.setVisibility(View.VISIBLE);
            buttonRight.setEnabled(true);
            buttonLeft.setEnabled(true);
            buttonUp.setEnabled(true);
            buttonDown.setEnabled(true);
            s1.setEnabled(true);
            s2.setEnabled(true);
            s3.setEnabled(true);
            attack.setEnabled(true);
        };
        service.execute(r);
        monster = (ImageView)view.findViewById(R.id.monster);
        //代码方式添加帧动画
        animationMonster = new AnimationDrawable();
        animationRole = new AnimationDrawable();
        int t = 200;
        monster.setImageDrawable(view.getResources().getDrawable(R.drawable.bat_flash));
        animationMonster = (AnimationDrawable) monster.getDrawable();
        animationMonster.start();

        buttonUp.setOnClickListener(v -> {
            Log.d(TAG,"------BUTTON_UP------CLICK------");
        });

        buttonDown.setOnClickListener(v -> {
            Log.d(TAG,"------BUTTON_DOWN------CLICK------");
        });

        buttonLeft.setOnClickListener(v->{
            isRight=false;
            //图像镜像翻转
            role.setImageBitmap(TurnHandler.turn(role.getResources(),roleMove[0]));
            //图像移动
            role.setTranslationX(-20);
            role.postInvalidate();
            role.setImageResource(humanBorn[16]);
            role.postInvalidate();
            if(!isStop){
                isStop=true;
                service.submit(() -> role.setImageBitmap(TurnHandler.turn(role.getResources(),R.drawable.role_stuck)));
            }else{
                service.submit(() -> {
                    if(!animationRole.isRunning()){
                        role.setImageBitmap(TurnHandler.turn(role.getResources(),roleMove[0]));
                    }
                    try {
                        role.setX(role.getX()-10);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //图像移动
                    role.setImageBitmap(TurnHandler.turn(role.getResources(),R.drawable.role_stuck));
                });
            }
        });
        buttonLeft.setOnLongClickListener(v -> {
            isRight=false;
            isStop=false;
            service.submit(() -> {
                int i = 0;
                int length = roleMove.length;
                while (!isStop) {
                    //如果没做其他的
                    if(!animationRole.isRunning()){
                        role.setImageBitmap(TurnHandler.turn(role.getResources(),roleMove[i]));
                    }
                    role.setX(role.getX()-20);
                    i = (i+1)%length;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            return false;
        });

        buttonRight.setOnLongClickListener(v -> {
            isRight=true;
            isStop=false;
            service.submit(() -> {
                int i = 0;
                int length = roleMove.length;
                while (!isStop) {
                    //如果没做其他的
                    if(!animationRole.isRunning()){
                        role.setImageResource(roleMove[i]);
                    }
                    role.setX(role.getX()+20);
                    i = (i+1)%length;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            return false;
        });
        buttonRight.setOnClickListener( v->{
            if(!isStop){
                isStop=true;
                service.submit(() -> role.setImageResource(R.drawable.role_stuck));
            }else{
                service.submit(() -> {
                    if(!animationRole.isRunning()){
                        role.setImageResource(roleMove[0]);
                    }
                    try {
                        role.setX(role.getX()+10);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //图像移动
                    role.setImageResource(R.drawable.role_stuck);
                });
            }
        });

        s1.setOnClickListener(v -> {
//            role.setImageDrawable(R.drawable);
//            service.submit(()->{
//                int i = 0;
//                int length = role_whirlwinds.length;
//                while (i<length) {
//                    isDoSomething=true;
//                    role.setImageResource(role_whirlwinds[i]);
//                    try {
//                        Thread.sleep(80);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//                }
//                isDoSomething=false;
//                role.setImageResource(R.drawable.role_stuck);
//            });
            role.setImageDrawable(view.getResources().getDrawable(R.drawable.role_wind_flash));
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        s2.setOnClickListener(v -> {
            role.setImageDrawable(view.getResources().getDrawable(R.drawable.role_swift_flash));
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        s3.setOnClickListener(v -> {
            role.setImageDrawable(view.getResources().getDrawable(R.drawable.role_buff_flash));
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        attack.setOnClickListener(v->{
            role.setImageDrawable(view.getResources().getDrawable(R.drawable.role_combo_flash));
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        service.shutdown();
        super.onDestroy();
    }
    //    Handler handler = new Handler()
}
