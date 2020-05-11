package com.example.sccproject.factory;

import com.example.sccproject.model.UserInfo;

public class UserMessageFactory extends MessageFactory {


    public UserMessageFactory(int code) {
        super(code);
    }

    public void userLogin(String userId,String userPwd){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setObject(userInfo);
        setCode(1);
    }

    public void userRegist(String userId,String userPwd){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setCode(0);
        setObject(userInfo);
    }

}
