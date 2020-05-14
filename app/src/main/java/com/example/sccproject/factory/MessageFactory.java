package com.example.sccproject.factory;

import com.easyarch.model.Message;
import com.example.sccproject.net.NettyClient;

public abstract class MessageFactory {
    protected Message msg;
    void sendMessage(){
        NettyClient.sendMessage(msg);
    }
    void setMsgCode(int code) {
        this.msg.setMsgCode(code);
    }

    void setObject(Object object) {
        this.msg.setObj(object);
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
