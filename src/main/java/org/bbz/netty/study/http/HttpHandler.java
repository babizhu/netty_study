package org.bbz.netty.study.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * Created by liu_k on 2015/10/20.
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpRequest>{
    //private static Unpooled.
    private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
    private static ByteBuf HTTP_BUF = Unpooled.wrappedBuffer(CONTENT);

    //private String response = "200 OK";
    @Override
    protected void messageReceived( ChannelHandlerContext ctx, HttpRequest msg ) throws Exception{
        System.out.println( msg.getClass().getName() );
        System.out.println( msg );
        writeResponse( ctx );
    }

    private void writeResponse( ChannelHandlerContext ctx ){

        FullHttpResponse response = new DefaultFullHttpResponse( HttpVersion.HTTP_1_0, HttpResponseStatus.OK );
        response.content().writeBytes( "刘老爷".getBytes() );


        Unpooled.copyMedium( 2 );

        ctx.writeAndFlush( response ).addListener( ChannelFutureListener.CLOSE );
    }

}
