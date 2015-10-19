package org.bbz.netty.study.write;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

/**
 * Created by liu_k on 2015/10/16.
 * writeAndFlush的调用，会触发pipeline中上一个handler的write函数
 */
public class EchoHandler1 extends ChannelHandlerAdapter{
    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception{
        ByteBuf data = (ByteBuf) msg;
        System.out.println( "EchoHandler1.channelRead:" + data.toString( Charset.defaultCharset()  )  );
        ctx.writeAndFlush( msg );//writeAndFlush的调用，会触发pipeline中上一个(EchoHandler)handler的write函数
    }
}
