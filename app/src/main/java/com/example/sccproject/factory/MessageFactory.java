package com.example.sccproject.factory;

public abstract class MessageFactory {

    protected com.easyarch.model.Message message;

    public MessageFactory(int code){
        message = new com.easyarch.model.Message();
        message.setMsgCode(code);
    }
    public MessageFactory(){
        message = new com.easyarch.model.Message();
    }

    public com.easyarch.model.Message getMessage(){
        return message;
    }
    public void setMessage(com.easyarch.model.Message message){
        this.message = message;
    }

    void setObject(Object obj){
        this.message.setObj(obj);
    }

    void setCode(int code){
        this.message.setMsgCode(code);
    }
}
