package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncoder extends MessageToByteEncoder<Object> {
    private Class<?> genericClass;
    private com.easyarch.serialize.Serializer serializer;

    public NettyEncoder(Class<?> genericClass, com.easyarch.serialize.Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (genericClass.isInstance(msg)){
            byte[] data=serializer.serializer(msg);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}