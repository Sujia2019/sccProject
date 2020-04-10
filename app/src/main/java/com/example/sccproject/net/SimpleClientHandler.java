package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */

import com.example.sccproject.model.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleClientHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        System.out.println("收到服务器的数据:"+msg.getObj());

    }
}