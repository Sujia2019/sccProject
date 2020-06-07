package com.example.sccproject.service;

import com.easyarch.model.Message;
import com.easyarch.model.PlayerInfo;
import com.easyarch.model.code.CODE;
import com.example.sccproject.GameHallActivity;
import com.example.sccproject.fragment.GameFragment;

public class UserMessageReceiver extends MessageReceiver {
    public UserMessageReceiver() {

    }

    @Override
    public void handler(int code,Message msg) {
        System.out.println("HANDLER");
        if(code == CODE.LOGIN){
            handleLogin(msg);
        }else if(code == CODE.REGIST){
            handleRegist(msg);
        }else if (code == CODE.REGIST_PHONE){
            handleCodeRegist(msg);
        }else if(code ==  CODE.LOGIN_PHONE){
            handleCodeLogin(msg);
        }else if(code == CODE.UPDATE){
            handleUpdate(msg);
        }else if(code == CODE.SAVE){
            handleSave(msg);
        }else{
            msg.setObj("ERROR");
        }
    }

    private void handleCodeRegist(Message msg) {
    }

    private void handleCodeLogin(Message msg) {
    }

    private void handleUpdate(Message msg) {
    }

    private void handleSave(Message msg) {
    }

    private void handleRegist(Message msg) {
    }

    private void handleLogin(Message msg){
        try{
            //有错误就捕捉，没错误就说明有玩家信息返回
            PlayerInfo player = (PlayerInfo) msg.getObj();
            System.out.println("player："+player);
            //传消息
            GameFragment fragment = new GameFragment();
            fragment.setPlayer(player);
            //切换fragment
            GameHallActivity.replaceFragment(fragment);
        }catch(Exception e){
            //发送广播
//            Intent intent=new Intent();
//            intent.putExtra("LoginError", msg.getObj().toString());
//            intent.setAction("com.example.sccproject.ConnectService");
//            sendBroadcast(intent);
//            Toast.makeText(context,msg.getObj().toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
