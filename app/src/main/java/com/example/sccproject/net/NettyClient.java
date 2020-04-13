package com.example.sccproject.net;

import com.example.sccproject.model.Message;
import com.example.sccproject.net.serializer.GsonSerializer;
import com.example.sccproject.net.serializer.NettyDecoder;
import com.example.sccproject.net.serializer.NettyEncoder;

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
                ch.pipeline().addLast(new NettyEncoder(Message.class,new GsonSerializer()));
                ch.pipeline().addLast(new NettyDecoder(Message.class,new GsonSerializer()));

                ch.pipeline().addLast(new SimpleClientHandler());
            }
        }).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        try {
            future = client.connect(host,port).sync();

            System.out.println("------connect------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(final Message message){
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