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

import com.example.sccproject.R;
import com.example.sccproject.service.UserMessageFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private EditText editLogin;
    private EditText editRegister;
    private Button returnRegister;
    private EditText editPwd;
    private EditText editPwd1;
    private EditText editPwd2;
    private Button sendCode;
    private Button bRegistCode;

    public FirstFragment() {

    }
    public Handler getHandler(){
        return mHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        //网络连接正常后点击play开始登录注册
        btn = (ImageButton) view.findViewById(R.id.playBtn);
        btn.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(v -> {
            v.clearAnimation();
            alertLogin();
        });

        return view;
    }

    private void alertLogin(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this.getContext());
        //两个视图 登录/注册的对话框
        View view = View.inflate(this.getContext(),R.layout.layout_login,null);
        View view2 = View.inflate(this.getContext(),R.layout.layout_regist,null);
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE);
        //输入登录的userId
        editLogin = (EditText) view.findViewById(R.id.userid);//登录id
        //输入注册的userId
        editRegister = (EditText)view2.findViewById(R.id.userid);
        //登录密码
        editPwd = (EditText) view.findViewById(R.id.userpwd);
        //注册密码
        editPwd1 = (EditText)view2.findViewById(R.id.userpwd1);
        //确认密码
        editPwd2 = (EditText)view2.findViewById(R.id.userpwd2);
        sendCode = (Button)view.findViewById(R.id.button_send);
        sendCode.setVisibility(View.INVISIBLE);

        returnRegister = (Button)view2.findViewById(R.id.regist_button_return);
        returnRegister.setVisibility(View.INVISIBLE);
        //注册按钮
        Button bRegister = (Button) view2.findViewById(R.id.regist_button);

        final Button loginCode = (Button)view.findViewById(R.id.login_code);
        //选择登陆方式
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
                sendCode.setOnClickListener(new SendCodeLogin());
                loginCode.setText(R.string.return_login);
                isCodeLogin=true;
            }
        });


        //普通登录
        Button bLogin = (Button)view.findViewById(R.id.login);
        bLogin.setOnClickListener(v->{
            String userId = editLogin.getText().toString();
            String pwd = editPwd.getText().toString();
            System.out.println(userId);
            System.out.println(pwd);
            //业务层发送登录请求
            userFactory.userNormalLogin(userId,pwd);
            //开启异步线程等待服务器返回

            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"confirm",Toast.LENGTH_SHORT).show();
//            //进入游戏界面
//            GameHallActivity.replaceFragment(new GameFragment());
//            dialog.cancel();
        });

        bRegister.setOnClickListener(v->{
            String userId = editRegister.getText().toString();
            String pwd = editPwd1.getText().toString();
            if(pwd.length()==0||userId.length()==0){
                Toast.makeText(FirstFragment.this.getContext(),"输入不可为空o~"+editLogin.getText().toString(),Toast.LENGTH_SHORT).show();
                return;
            }
            //普通注册
           if(!isCodeRegist){
               if(!pwd.equals(editPwd2.getText().toString())){
                    Toast.makeText(FirstFragment.this.getContext(),"两次密码输入不一致o~"+editLogin.getText().toString(),Toast.LENGTH_SHORT).show();
               }
               else{
                   userFactory.userNormalRegist(userId,pwd);
               }
            }
           //手机号注册
           else{
               if(!isPhone(userId)){
                   Toast.makeText(FirstFragment.this.getContext(),"手机号不符请重新输入o~"+editLogin.getText().toString(),Toast.LENGTH_SHORT).show();
                   return;
               }
               userFactory.userCodeRegist(userId,pwd);
           }
            //异步接收服务器返回
            //
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            Toast.makeText(FirstFragment.this.getContext(),"Register---UserId:"+editLogin.getText().toString(),Toast.LENGTH_SHORT).show();
        });

        bRegistCode = (Button)view2.findViewById(R.id.regist_button_code);
        bRegistCode.setOnClickListener(new ChangeRegistWay());
        returnRegister.setVisibility(View.INVISIBLE);
        //返回普通登录
        returnRegister.setOnClickListener(v -> {
            if(isCodeRegist){
                editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editPwd.setText(R.string.password);
                editPwd2.setVisibility(View.VISIBLE);
                bRegistCode.setEnabled(true);
                bRegistCode.setText(R.string.reigist_code);
                isCodeRegist=false;
                returnRegister.setVisibility(View.INVISIBLE);
                bRegistCode.setOnClickListener(new ChangeRegistWay());
            }
        });
        editLogin.setEnabled(true);
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

        editLogin.addTextChangedListener(new TextWatcher() {
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

        editRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bRegistCode.setEnabled(true);
                if(isCodeRegist){
                    bRegistCode.setOnClickListener(new SendCodeRegister());
                }
            }
        });

    }


    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }


    private class SendCodeLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String phoneNumber = editLogin.getText().toString();
            userFactory.userCodeLogin(phoneNumber);
        }
    }

    private class SendCodeRegister implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String phoneNumber = editRegister.getText().toString();
            userFactory.userCodeRegist(phoneNumber);
        }
    }

    private class ChangeRegistWay implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            editRegister.setInputType(InputType.TYPE_CLASS_NUMBER);
            editPwd1.setInputType(InputType.TYPE_CLASS_NUMBER);
            editPwd1.setHint(R.string.code);
            editPwd2.setVisibility(View.GONE);
            bRegistCode.setText(R.string.send);
            bRegistCode.setEnabled(false);
            isCodeRegist=true;
            returnRegister.setVisibility(View.VISIBLE);
        }
    }




}
