package com.example.sccproject.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alienware on 2020/4/10.
 */

public class FirstFragment extends Fragment {
    private ImageButton btn;
    private static int count = 0;

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
        View view2 = View.inflate(this.getContext(),R.layout.layout_regist,null);
//        view.setBackgroundResource(R.drawable.title);
//        view.getBackground().setAlpha(200);
//        final AlertDialog dialog = builder.create();
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE);
        final EditText editText = (EditText) view.findViewById(R.id.userid);
        final EditText editPwd = (EditText) view.findViewById(R.id.userpwd);
        final EditText editPwd1 = (EditText)view2.findViewById(R.id.userpwd1);
        final EditText editPwd2 = (EditText)view2.findViewById(R.id.userpwd2);
        Button bLogin = (Button)view.findViewById(R.id.login);

        bLogin.setOnClickListener(v->{
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"confirm",Toast.LENGTH_SHORT).show();
            GameHallActivity.replaceFragment(new GameFragment());
            dialog.cancel();
        });
        Button bRegist = (Button)view2.findViewById(R.id.regist_button);
        bRegist.setOnClickListener(v -> {
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"Register---UserId:"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
        });
        Button bRegistCode = (Button)view2.findViewById(R.id.regist_button_code);

        bRegistCode.setOnClickListener(v -> {
            if(count==0){
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editPwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                editPwd2.setVisibility(View.GONE);
                bRegistCode.setText(R.string.send);
                count++;
            }else{
                Toast.makeText(FirstFragment.this.getContext(),"发送验证码"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }

        });
        editText.setEnabled(true);
        dialog.setConfirmButton(R.string.loginb, sweetAlertDialog -> {
            dialog.setContentView(view);
            dialog.setConfirmButton(R.string.loginb,sweetAlertDialog1 -> {
            });
        });
        dialog.setNeutralButton(R.string.reigistb, sweetAlertDialog -> {
            dialog.setContentView(view2);
            System.out.println("Resistor");
        });
        dialog.show();
    }




}
