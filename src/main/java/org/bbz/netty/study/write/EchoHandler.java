package org.bbz.netty.study.write;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;

/**
 * Created by liu_k on 2015/10/16.
 */
public class EchoHandler extends ChannelHandlerAdapter{
    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception{
//        ByteBuf data = (ByteBuf) msg;
//        System.out.println( data.toString( Charset.defaultCharset()  ) );
//        ctx.writeAndFlush( msg );
        super.channelRead( ctx, msg );//如果注释了这一行代码，pipeline中的下一个处理器EchoHandler1.channelRead不会执行
    }

    @Override
    public void write( ChannelHandlerContext ctx, Object msg, ChannelPromise promise ) throws Exception{
        ByteBuf data = (ByteBuf) msg;
        System.out.println( "EchoHandler.write:" + data.toString( Charset.defaultCharset()  ) );
        super.write( ctx, msg, promise );//注释此行，则不会向客户端写数据
    }

    static int i =0;
    @Override
    public void read( ChannelHandlerContext ctx ) throws Exception{
        System.out.println( "EchoHandler.read :" + i++ );
        super.read( ctx );//注释此行代码，无论哪个handler的channelRead方法都收不到任何数据了
    }
}
