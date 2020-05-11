package com.example.sccproject.factory;

import com.example.sccproject.model.Message;

public abstract class MessageFactory {

    protected Message message;

    public MessageFactory(int code){
        message = new Message();
        message.setMsgCode(code);
    }
    public MessageFactory(){
        message = new Message();
    }

    public Message getMessage(){
        return message;
    }
    public void setMessage(Message message){
        this.message = message;
    }

    void setObject(Object obj){
        this.message.setObj(obj);
    }

    void setCode(int code){
        this.message.setMsgCode(code);
    }
}
