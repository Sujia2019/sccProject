package com.example.sccproject.net;

import android.os.Handler;

import com.easyarch.model.Message;
import com.easyarch.serialize.imp.ProtoStuffSerializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Handler view;
    ClientChannelInitializer(Handler handler){
        this.view = handler;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyEncoder(Message.class,new ProtoStuffSerializer()));
        pipeline.addLast(new NettyDecoder(Message.class,new ProtoStuffSerializer()));
        pipeline.addLast(new SimpleClientHandler(view));
        pipeline.addLast(new IdleStateHandler(30, 10, 0));
    }
}
