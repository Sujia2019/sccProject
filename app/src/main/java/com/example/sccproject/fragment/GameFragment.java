package com.example.sccproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sccproject.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by alienware on 2020/4/10.
 */

public class GameFragment extends Fragment {
    private ImageView soul ;
    private int currentSoul = 0;
    private int[] souls = new int[]{R.drawable.soul1,R.drawable.soul3,R.drawable.soul2};
    private ScheduledExecutorService service ;
    public GameFragment(){

    }

    @Override
    public void onStart() {
        super.onStart();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new ChangeViewTask(),500,500, TimeUnit.MILLISECONDS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        soul = (ImageView)view.findViewById(R.id.cute_soul);
//        soul.setImageResource(R.drawable.soul1);
        return view;
    }


    private class ChangeViewTask implements Runnable{

        @Override
        public void run() {
            currentSoul = (currentSoul+1)%souls.length;
            soul.setImageResource(souls[currentSoul]);
        }
    }
//
//    private class UIHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    }


}
