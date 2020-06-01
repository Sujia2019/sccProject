package com.example.sccproject.service;


import com.easyarch.model.CodeRequest;
import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;

public class UserMessageFactory extends MessageFactory {
    private CodeRequest codeRequest = new CodeRequest();

    private void userDefine(String userId, String userPwd){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(userPwd);
        setObject(userInfo);
    }
    //正常登录发送消息
    public void userNormalLogin(String userId,String userPwd){
        userDefine(userId,userPwd);
        setMsgCode(CODE.LOGIN);
        sendMessage();
    }

    //正常注册
    public void userNormalRegist(String userId,String userPwd){
        userDefine(userId,userPwd);
        setMsgCode(CODE.REGIST);
        sendMessage();
    }
    //发送验证码
    public void userCodeLogin(String phone){
        codeRequest.setPhoneNumber(phone);
        codeRequest.setStatus(CODE.SEND);
        codeRequest.setCode("0");
        setMsgCode(CODE.LOGIN_PHONE);
        setObject(codeRequest);
    }
    //验证登录
    public void userCodeLogin(String phone,String code){
        codeRequest.setPhoneNumber(phone);
        codeRequest.setCode(code);
        codeRequest.setStatus(CODE.VERIFY);
        setMsgCode(CODE.LOGIN_PHONE);
        setObject(codeRequest);
        sendMessage();
    }

    //
    public void userCodeRegist(String phone){
        codeRequest.setPhoneNumber(phone);
        codeRequest.setStatus(CODE.SEND);
        codeRequest.setCode("0");
        setMsgCode(CODE.REGIST_PHONE);
        setObject(codeRequest);
        sendMessage();
    }

    public void userCodeRegist(String phone,String code){
        codeRequest.setStatus(CODE.VERIFY);
        codeRequest.setPhoneNumber(phone);
        codeRequest.setCode(code);
        setMsgCode(CODE.REGIST_PHONE);
        setObject(codeRequest);
        sendMessage();
    }



}
