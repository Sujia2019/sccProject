package com.example.sccproject.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sccproject.GameHallActivity;
import com.example.sccproject.R;

/**
 * Created by alienware on 2020/4/10.
 */

public class FirstFragment extends Fragment {
    private ImageButton btn;

    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        final Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        btn = (ImageButton) view.findViewById(R.id.playBtn);
        btn.startAnimation(animation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearAnimation();
                alertLogin();
            }
        });

        return view;
    }

    private void alertLogin(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this.getContext());
        View view = View.inflate(this.getContext(),R.layout.layout_login,null);
        view.setBackgroundResource(R.drawable.title);
        view.getBackground().setAlpha(200);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        final EditText editText = (EditText) view.findViewById(R.id.userid);
        final EditText editPwd = (EditText) view.findViewById(R.id.userpwd);
        editText.setEnabled(true);

        Button bConfirm = (Button)view.findViewById(R.id.confirm_button);
        Button bRegist = (Button)view.findViewById(R.id.regist_button);
        Button bLogin = (Button)view.findViewById(R.id.login_button);

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstFragment.this.getContext(),"confirm",Toast.LENGTH_SHORT).show();
                GameHallActivity.replaceFragment(new GameFragment());
                dialog.cancel();
            }
        });
        bRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstFragment.this.getContext(),"Register---UserId:"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstFragment.this.getContext(),"Login---UserId:"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }




}
