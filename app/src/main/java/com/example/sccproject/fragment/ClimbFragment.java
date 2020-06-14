package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
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
import com.example.sccproject.util.ScreenListener;
import com.example.sccproject.util.SensorHelper;
import com.example.sccproject.util.TurnHandler;
import com.example.sccproject.util.WakeLocker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static android.content.ContentValues.TAG;

/**
 * Created by alienware on 2020/4/13.
 */

public class ClimbFragment extends Fragment implements SensorHelper.OnSensorChangeListener, ScreenListener.ScreenStateListener{
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
    private ImageButton menu;
    private ImageButton leaveGame;
    private ImageButton ok;
    private ImageButton resume;
    private ImageView board;
    private SensorEventListener sensorEventListener;
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
        monster.setImageResource(R.drawable.bat_flash);
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
            isRight=true;
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
            role.setImageResource(R.drawable.role_wind_flash);
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        s2.setOnClickListener(v -> {
            role.setImageResource(R.drawable.role_swift_flash);
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        s3.setOnClickListener(v -> {
            role.setImageResource(R.drawable.role_buff_flash);
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        attack.setOnClickListener(v->{
            role.setImageResource(R.drawable.role_combo_flash);
            animationRole = (AnimationDrawable) role.getDrawable();
            animationRole.start();
        });

        menu = view.findViewById(R.id.menu);
        leaveGame = view.findViewById(R.id.leave_game);
        ok = view.findViewById(R.id.ok);
        resume = view.findViewById(R.id.resume);
        board = view.findViewById(R.id.board);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        wakeLocker.release();
    }

    @Override
    public void onDestroy() {
        service.shutdown();
        super.onDestroy();
    }
    //    Handler handler = new Handler()
    private WakeLocker wakeLocker;
    private ScreenListener screenListener;
    private SensorHelper accelerometerHelper;
    private SensorHelper orientationHelper;
//    private TextView accelerometerX, accelerometerY, accelerometerZ;
//    private TextView orientationA, orientationB, orientationC;
    private ExecutorService executorService;
    private void openSensor(){
        WakeLocker.keepScreenBright(this.getActivity());//保持屏幕长亮
        screenListener = new ScreenListener(this.getActivity());
        screenListener.start(this);
        wakeLocker = new WakeLocker(this.getActivity(), WakeLocker.Type.KEEP_CPU_RUN);
        wakeLocker.acquire();
        accelerometerHelper = new SensorHelper(this.getActivity(), Sensor.TYPE_ACCELEROMETER);
        orientationHelper = new SensorHelper(this.getActivity(), Sensor.TYPE_ORIENTATION);
//        accelerometerX =  findViewById(R.id.main_txtAccelerometerX);
//        accelerometerY = findViewById(R.id.main_txtAccelerometerY);
//        accelerometerZ =  findViewById(R.id.main_txtAccelerometerZ);
//        orientationA =  findViewById(R.id.main_txtOrientationA);
//        orientationB =  findViewById(R.id.main_txtOrientationB);
//        orientationC =  findViewById(R.id.main_txtOrientationC);
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void onScreenOn() {
        System.out.println("屏幕已点亮");
    }

    @Override
    public void onScreenOff() {
        //在手机锁屏的时候，重新注册传感器可解决部分手机无法黑屏后收集数据的问题，
        System.out.println("屏幕已熄灭");
        accelerometerHelper.unregisterListener();
        accelerometerHelper.registerListener(this);
        orientationHelper.unregisterListener();
        orientationHelper.registerListener(this);
    }

    @Override
    public void onUserPresent() {
        System.out.println("屏幕已解锁");
    }

    @Override
    public void onSensorChanged(Sensor sensor, float[] values) {
        int sensorType = sensor.getType();
        showDataToView(sensorType, values);
    }
    private void showDataToView(int sensorType, float[] values) {
        float x = values[0];
        float y = values[1];
        float z = values[2];
        System.out.println(String.format("x=%f,y=%f,z=%f", x, y, z));
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            showAccelerometerDataToView(x, y, z);
        } else if (sensorType == Sensor.TYPE_ORIENTATION) {
            showOrientationDataToView(x, y, z);
        }
    }
    private void showAccelerometerDataToView(float x, float y, float z) {
        Log.d(TAG,"-X:"+x+" -Y:"+y+" -Z:"+z);
    }
    private void showOrientationDataToView(float x, float y, float z) {
        String orientationString = "";
        String topBottomString = "";
        String leftRightString = "";
        if (x == 0) {
            orientationString = "正北";
        } else if (x == 90) {
            orientationString = "正东";
        } else if (x == 180) {
            orientationString = "正南";
        } else if (x == 270) {
            orientationString = "正西";
        } else if (x < 90 && x > 0) {
            orientationString = "北偏东" + x + "度";
        } else if (x < 180 && x > 90) {
            orientationString = "南偏东" + (180 - x) + "度";
        } else if (x < 270 && x > 180) {
            orientationString = "南偏西" + (x - 180) + "度";
        } else if (x < 360 && x > 270) {
            orientationString = "北偏西" + (360 - x) + "度";
        }
        if (y == 0) {
            topBottomString = "手机头部或底部没有翘起";
        } else if (y > 0) {
            topBottomString = "底部向上翘起" + y + "度";
        } else if (y < 0) {
            topBottomString = "顶部向上翘起" + Math.abs(y) + "度";
        }
        if (z == 0) {
            leftRightString = "手机左侧或右侧没有翘起";
        } else if (z > 0) {
            leftRightString = "右侧向上翘起" + z + "度";
        } else if (z < 0) {
            leftRightString = "左侧向上翘起" + Math.abs(z) + "度";
        }
    }
}
