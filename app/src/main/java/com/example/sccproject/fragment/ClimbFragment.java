package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sccproject.R;
import com.example.sccproject.util.FlashRunnable;
import com.example.sccproject.util.TurnHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private boolean isRight = true;//人物是否朝着右边走
    private static volatile boolean isStop = true;//人物是否停止奔跑
    private static int index = 0;//奔跑初态

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

    private int[] monsterNormal = new int[]{
            R.drawable.bat_idle1,R.drawable.bat_idle2,R.drawable.bat_idle3,R.drawable.bat_idle4
    };

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        monster = (ImageView)view.findViewById(R.id.monster);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role.setImageResource(roleMove[0]);
                //图像镜像翻转
                role.setImageBitmap(TurnHandler.flipImage(role.getScrollX(),role.getScrollY(),role.getDrawingCache()));
                //图像移动
                role.setTranslationX(-20);
                role.postInvalidate();
                role.setImageResource(humanBorn[16]);
                role.postInvalidate();
            }
        });
//        buttonLeft.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                int index = 0;
//                                int length = roleMove.length;
//                                while (isStop) {
//                                    role.setImageResource(roleMove[index]);
//                                    role.setImageBitmap(TurnHandler.flipImage(role.getDrawingCache()));
//                                    role.setTranslationX(-20);
//                                    index = (index + 1) % length;
//                                }
//                            }
//                        }).start();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    isStop = false;
//                    //停下
//                    role.setImageResource(humanBorn[16]);
//                    role.setImageBitmap(TurnHandler.flipImage(role.getDrawingCache()));
//                    break;
//                case MotionEvent.ACTION_UP:
//                    isStop = false;
//                    //停下
//                    role.setImageResource(humanBorn[16]);
//                    role.setImageBitmap(TurnHandler.flipImage(role.getDrawingCache()));
//                    break;
//                default:
//                    break;
//            }
//            return true;
//        });

        buttonRight.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    service.execute(new FlashRunnable(roleMove,0,role));
                    //图像移动
                    role.setX(role.getX()+20);
                    break;
                case MotionEvent.ACTION_MOVE:
                    isStop = false;
                    //停下
                    service.execute(new FlashRunnable(humanBorn,16,role));
//                    role.setImageResource(humanBorn[16]);
                    break;
                case MotionEvent.ACTION_UP:
                    isStop = false;
                    //停下
                    service.execute(new FlashRunnable(humanBorn,16,role));
//                    role.setImageResource(humanBorn[16]);
                    break;
                default:
                    break;
            }
            return true;
        });

//        buttonRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                role.setImageResource(roleMove[0]);
//                //图像移动
//                role.setTranslationX(20);
//                role.postInvalidate();
//                role.setImageResource(humanBorn[16]);
//                role.postInvalidate();
//            }
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FlashRunnable monsterFly = new FlashRunnable(monsterNormal,0,monster);
        Runnable r = () -> {
            int index = 0;
            int length = humanBorn.length;
            while (index!=length-1){
                role.setImageResource(humanBorn[index]);
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
            }
            role.setImageBitmap(TurnHandler.turnToOther(role.getScrollX(),role.getScrollY(),role.getDrawingCache(),R.drawable.role_stuck));
        };
        service = Executors.newScheduledThreadPool(20);
        service.execute(r);
//        service.scheduleWithFixedDelay(roleBorn,200,200,TimeUnit.MILLISECONDS);
        service.scheduleWithFixedDelay(monsterFly,180,180, TimeUnit.MILLISECONDS);

    }
}
