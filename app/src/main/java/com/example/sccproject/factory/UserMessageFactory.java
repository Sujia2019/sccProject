package com.example.sccproject.factory;


import com.easyarch.model.code.CODE;


public class UserMessageFactory extends MessageFactory {


    public UserMessageFactory(int code) {
        super(code);
    }

    public void userLogin(String userId,String userPwd){
        com.easyarch.model.UserInfo userInfo = new com.easyarch.model.UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setObject(userInfo);
        setCode(CODE.LOGIN);
    }

    public void userRegist(String userId,String userPwd){
        com.easyarch.model.UserInfo userInfo = new com.easyarch.model.UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setCode(CODE.REGIST);
        setObject(userInfo);
    }

}
