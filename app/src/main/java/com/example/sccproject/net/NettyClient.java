package com.example.sccproject.net;

import com.example.sccproject.GameHallActivity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by alienware on 2020/4/10.
 */

public class NettyClient{
    private String host;
    private int port;
    private static volatile ChannelFuture future;
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,200,
            TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(5));
    public static boolean isOk;
    public NettyClient(String host,int port){
        this.host = host;
        this.port = port;
        init();
    }
    private void init() {
        Bootstrap client = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        client.channel(NioSocketChannel.class);

        client.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyEncoder(com.easyarch.model.Message.class,new com.easyarch.serialize.imp.GsonSerializer()));
                ch.pipeline().addLast(new NettyDecoder(com.easyarch.model.Message.class,new com.easyarch.serialize.imp.GsonSerializer()));

                ch.pipeline().addLast(new SimpleClientHandler());
            }
        }).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        try {
            future = client.connect(host,port).sync();
            System.out.println("------connect------");
            isOk = true;
            GameHallActivity.xxx="Netty";
        } catch (Exception e) {
            isOk = false;
//            System.out.println("xxx");
            GameHallActivity.xxx=e.getMessage();
//            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void sendMessage(final com.easyarch.model.Message message){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    future.channel().writeAndFlush(message).sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}