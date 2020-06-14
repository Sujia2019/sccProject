package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyarch.model.PlayerInfo;
import com.example.sccproject.GameHallActivity;
import com.example.sccproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alienware on 2020/4/10.
 */

public class GameFragment extends Fragment {
    private ImageView soul ;
    private ScheduledExecutorService service ;

    private ImageButton fightButton ;//匹配
    private ImageButton adventureButton ;//探险
    private ImageButton storyButton ;//故事背景
    private ImageButton helpButton ;//帮助
    private ImageButton optionButton ;//选项
    private ImageButton shopButton;//商店
    private ImageButton headButton ;//个人头像按钮
    private TextView timer;
    private ImageButton matchCancel;
    private ConstraintLayout layoutMatch;
    private AnimationDrawable animationSoul;
    private ImageView infoView;
    private static boolean isMatching = false;
    private static boolean isInfoShow = false;
    private PlayerInfo player;


    private static int clicks = 0;


    public GameFragment(){

    }
    public void setPlayer(PlayerInfo player){
        this.player = player;
    }
    public PlayerInfo getPlayer(){
        return player;
    }

    @Override
    public void onStart() {
        super.onStart();
        service = Executors.newScheduledThreadPool(10);
//        service.scheduleWithFixedDelay(new SoulNormalTask(),250,250, TimeUnit.MILLISECONDS);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        soul = (ImageView)view.findViewById(R.id.cute_soul);
        soul.setImageDrawable(view.getResources().getDrawable(R.drawable.soul_flash));
        animationSoul = (AnimationDrawable)soul.getDrawable();
        animationSoul.start();
        soul.setOnClickListener(v -> {
            if(clicks>20){
                soul.setImageResource(R.drawable.soul_die1);
            }else{
                clicks++;
                animationSoul.stop();
                soul.setImageDrawable(view.getResources().getDrawable(R.drawable.soul_attack_flash));
                animationSoul = (AnimationDrawable)soul.getDrawable();
                animationSoul.setOneShot(true);
                animationSoul.start();
                service.schedule(()->{
                    soul.setImageDrawable(view.getResources().getDrawable(R.drawable.soul_flash));
                    animationSoul = (AnimationDrawable) soul.getDrawable();
                    animationSoul.setOneShot(false);
                    animationSoul.start();
                },1100,TimeUnit.MILLISECONDS);
            }
        });

        ImageView ghost = (ImageView) view.findViewById(R.id.cute_gost);
        AnimationDrawable animationGhost = (AnimationDrawable) ghost.getDrawable();
        animationGhost.start();


        adventureButton = (ImageButton)view.findViewById(R.id.start_adventure);
        adventureButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        float x1 = adventureButton.getX();
                        adventureButton.setTranslationY(-20);
                        GameHallActivity.replaceFragment(new ClimbFragment());
                        break;
                    case MotionEvent.ACTION_UP:
                        float x2 = adventureButton.getX();
//                        adventureButton.setRotationX(x2-25);
                        adventureButton.setTranslationY(20);
                        break;
                }
                return true;
            }
        });
        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMatching){
                    Toast t = Toast.makeText(getContext(),"~正在匹配不可以开启闯关(＾Ｕ＾)ノ~ＹＯ",Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }else{
                    System.out.println("adventure");
                    GameHallActivity.replaceFragment(new ClimbFragment());
                }
            }
        });

        matchCancel = (ImageButton)view.findViewById(R.id.match_cancle);
        matchCancel.setOnClickListener(v->{
            isMatching=false;
            //显示匹配布局
            layoutMatch.setVisibility(View.VISIBLE);
            //设置移动animation
            final Animation animation = new TranslateAnimation(0,0,0,-160);
            animation.setDuration(500);
            //启用
            layoutMatch.startAnimation(animation);
            layoutMatch.setVisibility(View.INVISIBLE);
        });

        timer = (TextView)view.findViewById(R.id.match_timer);


        storyButton = (ImageButton)view.findViewById(R.id.story);

        fightButton = (ImageButton)view.findViewById(R.id.battle);
        layoutMatch = (ConstraintLayout)view.findViewById(R.id.layout_match);
        layoutMatch.setVisibility(View.INVISIBLE);
        fightButton.setOnClickListener(v -> {
            if(!isMatching){
                //显示匹配布局
                layoutMatch.setVisibility(View.VISIBLE);
                //设置移动animation
                final Animation animation = new TranslateAnimation(0,0,-160,0);
                animation.setDuration(500);
                //启用
                layoutMatch.startAnimation(animation);
                isMatching=true;
            }else{
                Toast t = Toast.makeText(this.getContext(),"~正在匹配中不可以重复匹配(＾Ｕ＾)ノ~ＹＯ",Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
            }
        });

        infoView = (ImageView) view.findViewById(R.id.charater_info);
        infoView.setVisibility(View.INVISIBLE);
        headButton = (ImageButton)view.findViewById(R.id.player_head);
        headButton.setOnClickListener(v->{
            if(!isInfoShow){
                //显示info信息布局
                infoView.setVisibility(View.VISIBLE);
                //设置移动animation
                final Animation animation = new TranslateAnimation(-400,0,0,0);
                animation.setDuration(500);
                //启用
                infoView.startAnimation(animation);
                isInfoShow=true;
            }else{
                //显示info信息布局
                //设置移动animation
                final Animation animation = new TranslateAnimation(0,-400,0,0);
                animation.setDuration(500);
                //启用
                infoView.startAnimation(animation);
                infoView.setVisibility(View.INVISIBLE);
                isInfoShow = false;
            }
        });
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE);
        View viewOption = inflater.inflate(R.layout.layout_option,container,false);
        View viewHelper = inflater.inflate(R.layout.layout_help,container,false);
        //帮助按钮
        helpButton = (ImageButton)view.findViewById(R.id.help);
        helpButton.setOnClickListener(v->{
            dialog.setContentView(viewHelper);
            dialog.setCancelable(true);
            dialog.show();
        });
        //设置选项
        optionButton = (ImageButton)view.findViewById(R.id.options);
        optionButton.setOnClickListener(v->{
            dialog.setContentView(viewOption);
            dialog.setCancelable(true);
            dialog.show();
        });

        shopButton = (ImageButton)view.findViewById(R.id.shop);
        shopButton.setOnClickListener(v->{
            GameHallActivity.replaceFragment(new ShopFragment());
        });
        return view;
    }

    private void moveEffect(){

    }

    @Subscribe
    public void onEvent(PlayerInfo player){
        Toast.makeText(this.getContext(),player.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //    private void loadPlayerInfo(){
//        player.setClimbLevel(1);
//        player.setFightCount(0);
//        player.setUserId("1234567");
//        player.setUserName("test");
//        player.setWinCount(0);
//        player.setMoney(0);
//        player.setRank(0);
//    }
}
