package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.sccproject.GameHallActivity;
import com.example.sccproject.R;
import com.example.sccproject.model.PlayerInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by alienware on 2020/4/10.
 */

public class GameFragment extends Fragment {
    private ImageView soul ;
    private ScheduledExecutorService service ;

    private ImageButton fightButton ;
    private ImageButton adventureButton ;
    private ImageButton storyButton ;
    private ImageButton helpButton ;
    private ImageButton optionButton ;
    private ImageButton headButton ;
    private AnimationDrawable animationSoul;

    public static PlayerInfo player ;


    private static int clicks = 0;


    public GameFragment(){
        player = new PlayerInfo();
        loadPlayerInfo();
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
                System.out.println("adventure");
                GameHallActivity.replaceFragment(new ClimbFragment());
            }
        });

        storyButton = (ImageButton)view.findViewById(R.id.story);
        fightButton = (ImageButton)view.findViewById(R.id.battle);
        headButton = (ImageButton)view.findViewById(R.id.player_head);
        helpButton = (ImageButton)view.findViewById(R.id.help);
        optionButton = (ImageButton)view.findViewById(R.id.options);

        return view;
    }

    private void moveEffect(){

    }

    private void loadPlayerInfo(){
        player.setClimbLevel(1);
        player.setFightCount(0);
        player.setUserId("1234567");
        player.setUserName("test");
        player.setWinCount(0);
        player.setMoney(0);
        player.setRank(0);
    }
}
