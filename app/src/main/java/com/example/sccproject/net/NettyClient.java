package com.example.sccproject.net;

import android.os.Handler;
import android.util.Log;

import com.easyarch.model.Message;
import com.example.sccproject.util.NetInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import static android.content.ContentValues.TAG;

/**
 * Created by alienware on 2020/4/10.
 */

public class NettyClient implements Runnable{
    private static volatile ChannelFuture future;
    private Handler view;
    public NettyClient(Handler handler){
        this.view = handler;
    }
    private void setHandler(Handler handler){
        this.view = handler;
    }

    @Override
    public void run() {
        Bootstrap client = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        client.group(group);
        client.channel(NioSocketChannel.class);
        client.handler(new ClientChannelInitializer(view))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        try {
            future = client.connect(NetInfo.host,NetInfo.port).sync();
            future.addListener((ChannelFutureListener) future -> {
                NetInfo.isConnect = future.isSuccess();
                if (future.isSuccess()) {
                    Log.d(TAG, "connect success !");
                } else {
                    Log.d(TAG, "connect failed !");
                }
            });
            System.out.println("------connect------");
        }catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public static void sendMessage(Message message){
        future.channel().writeAndFlush(message);
    }

}