package com.example.sccproject.factory;


import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;

public class UserMessageFactory extends MessageFactory {

    private void userDefine(String userId, String userPwd){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setObject(userInfo);
    }
    public void userNormalLogin(String userId,String userPwd){
        userDefine(userId,userPwd);
        setMsgCode(CODE.LOGIN);
        sendMessage();
    }

    public void userNormalRegist(String userId,String userPwd){
        userDefine(userId,userPwd);
        setMsgCode(CODE.REGIST);
        sendMessage();
    }
//    public void userCodeLogin(String userId,String userPwd){
//        userDefine(userId,userPwd);
//        setMsgCode(CODE.LOGIN_PHONE);
//        sendMessage();
//    }
//
//    public void userCodeRegist(String userId,String userPwd){
//        userDefine(userId,userPwd);
//        setMsgCode(CODE.REGIST_PHONE);
//        sendMessage();
//    }

}
