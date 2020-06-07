package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */

import android.os.Handler;

import com.easyarch.model.Message;
import com.example.sccproject.service.UserMessageReceiver;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleClientHandler extends SimpleChannelInboundHandler<Message> {

    public static ConcurrentHashMap<String,Message> localMap = new ConcurrentHashMap<>();
    private UserMessageReceiver userMessageReceiver ;
    private Handler view;

    SimpleClientHandler(Handler view){
        this.view = view;
        this.userMessageReceiver = new UserMessageReceiver();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        int code = msg.getMsgCode();
        System.out.println("code:"+code);
        System.out.println("obj:"+msg.getObj());
        //通过handler向ui发送消息
        android.os.Message message = new android.os.Message();
        message.obj = msg;
        view.sendMessage(message);

//        //线程池提交
//        NettyClient.executor.submit(() -> {
//            if(code <= CODE.USER_TYPE){
//                System.out.println("SUBMIT");
//                userMessageReceiver.handler(code,msg);
//            }
//            //聊天
////            else if (code <= CODE.CHAT_TYPE){
////            msg = chatFactory.handle(msg);
////            }
//            //打机器人
//            else if (code == CODE.FIGHT) {
////            msg = monsterFactory.handle(msg);
//            }
//            //匹配
//            else if (code <= CODE.MATCH_TYPE) {
////            msg = matchFactory.handle(msg);
//            }
//
//            else {
////            msg = exceptionFactory.handle(msg);
//            }
//        });
//
//        System.out.println("收到服务器的数据:"+msg.getObj());
    }
}