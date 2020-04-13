package com.example.sccproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private ImageView ghost;
    private volatile int currentSoul = 0;
    private int currentGhost = 0;
    private int[] souls = new int[]{R.drawable.soul1,R.drawable.soul3,R.drawable.soul2};
    private int[] soulAttack = new int[]{R.drawable.soul_attack1,
            R.drawable.soul_attack2,
            R.drawable.soul_attack3,
            R.drawable.soul_attack4};
    private int[] ghosts = new int[]{R.drawable.ghostwalk1,
            R.drawable.ghostwalk2,
            R.drawable.ghostwalk3,
            R.drawable.ghostwalk4};
    private ScheduledExecutorService service ;
    private static volatile boolean flagNormal = true;
    private static volatile boolean flagAttack = false;

    private ImageButton fightButton ;
    private ImageButton adventureButton ;
    private ImageButton storyButton ;
    private ImageButton helpButton ;
    private ImageButton optionButton ;
    private ImageButton headButton ;

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
        service.scheduleWithFixedDelay(new SoulNormalTask(),250,250, TimeUnit.MILLISECONDS);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        soul = (ImageView)view.findViewById(R.id.cute_soul);

        soul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicks>20){
                    flagNormal = false;
                    flagAttack = false;
                    soul.setImageResource(R.drawable.soul_die1);
                }else{
                    clicks++;
                    currentSoul = 0;
                    service.execute(new SoulAttackTask());
                }
            }
        });

        ghost = (ImageView)view.findViewById(R.id.cute_gost);

        adventureButton = (ImageButton)view.findViewById(R.id.start_adventure);
        adventureButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        float x1 = adventureButton.getX();
                        adventureButton.setTranslationY(-20);
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


    private class SoulNormalTask implements Runnable{

        @Override
        public void run() {//        service.scheduleWithFixedDelay(new SoulAttackTask(),300,300,TimeUnit.MILLISECONDS);

            if(flagNormal){
                soul.setImageResource(souls[currentSoul]);
                currentSoul = (currentSoul+1)%souls.length;
            }
            ghost.setImageResource(ghosts[currentGhost]);
            currentGhost = (currentGhost+1)%ghosts.length;
        }
    }

    private class SoulAttackTask implements Runnable{

        @Override
        public void run() {
            flagAttack = true;
            flagNormal = false;

            while (flagAttack){

                soul.setImageResource(soulAttack[currentSoul]);
                currentSoul = currentSoul+1;

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                    if(currentSoul==soulAttack.length){
                        currentSoul = 0;
                        flagAttack = false;
                        flagNormal = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
