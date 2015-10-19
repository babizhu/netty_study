package org.bbz.netty.study.bindcallback;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.SocketAddress;

/**
 * Created by liu_k on 2015/10/16.
 * 在启动的时候，serverBootstrap.bind之后，可以针对绑定函数设置相应的回调函数
 */
public class Launcher{

    public static void main( String[] args ){
        new Launcher().run();
    }
    private void run(){
        int port = 8000;
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group( bossGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .childHandler( new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel( SocketChannel ch ) throws Exception{
                            // ch.pipeline().addLast( new DiscardServerHandler() );
                        }
                    } )
                    .option( ChannelOption.SO_BACKLOG, 128 )
                    .childOption( ChannelOption.SO_KEEPALIVE, true );

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind( port ).addListener(
                    new ChannelFutureListener(){
                @Override
                public void operationComplete( ChannelFuture future )
                        throws Exception{
                    if( future.isSuccess() ) {
                        Channel channel = future.channel();
                        SocketAddress socketAddress = channel.remoteAddress() == null ? channel.localAddress() : channel.remoteAddress();
                        System.out.println( socketAddress );
                    }else {
                        System.out.println( "出错啦：" + future.cause() );
                    }
                }
            } ).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch( InterruptedException e ) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


}
