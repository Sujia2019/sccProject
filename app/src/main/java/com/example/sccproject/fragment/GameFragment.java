package com.example.sccproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sccproject.R;
import com.example.sccproject.music.MusicServer;

/**
 * Created by alienware on 2020/4/10.
 */

public class GameFragment extends Fragment {

    public GameFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        return view;
    }



}
