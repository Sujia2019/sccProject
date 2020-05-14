package com.example.sccproject.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import com.easyarch.model.UserInfo;
import com.example.sccproject.GameHallActivity;
import com.example.sccproject.R;
import com.example.sccproject.factory.UserMessageFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alienware on 2020/4/10.
 */

public class FirstFragment extends Fragment {
    private ImageButton btn;
    private static int count = 0;
    private static boolean isCodeRegist=false;
    private static boolean isCodeLogin=false;
    private UserMessageFactory userFactory;
//    public
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btn.setVisibility(View.VISIBLE);
            final Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(500);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            btn.startAnimation(animation);
            userFactory = new UserMessageFactory();
        }
    };
    public FirstFragment() {

    }
    public Handler getHandler(){
        return mHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        btn = (ImageButton) view.findViewById(R.id.playBtn);
        btn.setVisibility(View.INVISIBLE);
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
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE);
        final EditText editText = (EditText) view.findViewById(R.id.userid);//登录id
        final EditText editText1 = (EditText)view2.findViewById(R.id.userid);//注册id
        final EditText editPwd = (EditText) view.findViewById(R.id.userpwd);//登录密码
        final EditText editPwd1 = (EditText)view2.findViewById(R.id.userpwd1);//注册密码
        final EditText editPwd2 = (EditText)view2.findViewById(R.id.userpwd2);
        final Button sendCode = (Button)view.findViewById(R.id.button_send);
        sendCode.setVisibility(View.INVISIBLE);
        final Button loginCode = (Button)view.findViewById(R.id.login_code);
        loginCode.setOnClickListener(v->{
            if(isCodeLogin){
                editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editPwd.setHint(R.string.password);
                sendCode.setVisibility(View.INVISIBLE);
                loginCode.setText(R.string.code_login);
                isCodeLogin=false;
            }else{
                editPwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                editPwd.setHint(R.string.code);
                sendCode.setVisibility(View.VISIBLE);
                loginCode.setText(R.string.return_login);
                isCodeLogin=true;
            }
        });
        Button bLogin = (Button)view.findViewById(R.id.login);

        bLogin.setOnClickListener(v->{
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"confirm",Toast.LENGTH_SHORT).show();
            GameHallActivity.replaceFragment(new GameFragment());
            dialog.cancel();
        });
        Button bRegist = (Button)view2.findViewById(R.id.regist_button);
        bRegist.setOnClickListener(v->{
            if(isCodeRegist){
                UserInfo u = new UserInfo();
                u.setUserId(editText1.getText().toString());
                u.setUserPwd(editPwd1.getText().toString());

            }
        });
        bRegist.setOnClickListener(v -> {
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"Register---UserId:"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
        });
        Button bRegistCode = (Button)view2.findViewById(R.id.regist_button_code);
        bRegistCode.setOnClickListener(v -> {
            if(isCodeRegist){
                //发送验证码
                Toast.makeText(FirstFragment.this.getContext(),"发送验证码"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
            editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
            editPwd1.setInputType(InputType.TYPE_CLASS_NUMBER);
            editPwd1.setHint(R.string.code);
            editPwd2.setVisibility(View.GONE);
            bRegistCode.setText(R.string.send);
            bRegistCode.setEnabled(false);
            isCodeRegist=true;

        });
        Button returnRegist = (Button)view2.findViewById(R.id.regist_button_return);
        returnRegist.setOnClickListener(v -> {
            isCodeRegist=false;
            editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editPwd.setText(R.string.password);
            editPwd2.setVisibility(View.VISIBLE);
            bRegistCode.setEnabled(true);
            bRegistCode.setText(R.string.reigist_code);
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

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                bRegistCode.setEnabled(true);
            }
        });
    }





}
