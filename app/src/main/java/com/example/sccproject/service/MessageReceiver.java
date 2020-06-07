package com.example.sccproject.service;

import com.easyarch.model.Message;

public abstract class MessageReceiver {

    /*
    处理收到的消息并渲染前端
     */
    public MessageReceiver(){

    }
    abstract public void handler(int code,Message msg);
}
