package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */
import com.easyarch.model.Message;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleClientHandler extends SimpleChannelInboundHandler<Message> {

    public static ConcurrentHashMap<String,Message> localMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

//        System.out.println(ms);

        System.out.println("收到服务器的数据:"+msg.getObj());

    }
}