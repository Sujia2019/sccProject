package com.example.sccproject.net;

/**
 * Created by alienware on 2020/4/10.
 */

import com.easyarch.serialize.Serializer;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;
    private Serializer serializer;

    public NettyDecoder(Class<?> genericClass, final Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()<4){
            return ;
        }
        in.markReaderIndex();
        int dataLength=in.readInt();
        if (dataLength<0){
            ctx.close();
        }
        if (in.readableBytes()<dataLength) {
            in.markReaderIndex();
            return ;
        }
        byte [] data=new byte[dataLength];

//        System.out.println(dataLength);
        in.readBytes(data);
//        System.out.println(data);

        Object obj=serializer.deserializer(data,genericClass);
        out.add(obj);
    }
}