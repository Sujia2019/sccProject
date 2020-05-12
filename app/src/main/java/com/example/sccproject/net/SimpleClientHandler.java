package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleClientHandler extends SimpleChannelInboundHandler<com.easyarch.model.Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, com.easyarch.model.Message msg) throws Exception {

        System.out.println("收到服务器的数据:"+msg.getObj());

    }
}