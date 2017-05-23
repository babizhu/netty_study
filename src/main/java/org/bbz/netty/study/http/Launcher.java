package org.bbz.netty.study.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_k on 2015/10/20.
 */
public class Launcher{

    static class Food{}
    static class Fruit extends Food{}
    static class Apple extends Fruit{}
    static class RedApple extends Apple{}
    static class BlueApple extends Apple{}
    static class RedFruit extends Fruit{}

    private void test( List<? extends Apple> apples){
        Apple apple = apples.get( 0 );
        Fruit fruit = apples.get( 1 );
        List<Apple> apples1 = new ArrayList<>(  );
        List<BlueApple> apples2 = new ArrayList<>(  );
        a( apples1 );
        a( apples2 );
    }


    private void a( List<? extends Apple> apples ){

    }

    private void test(){
        List<? super Apple> fruits = new ArrayList<>(  );
//        fruits.add( new Fruit() );
        fruits.add( new RedApple() );
        fruits.add( new BlueApple() );

        List<Apple> foods = new ArrayList<>(  );
        foods.add( new Apple() );
        foods.add( new BlueApple() );


        Object o = fruits.get( 0 );

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
                            ch.pipeline().addLast( new HttpServerCodec() );
                            ch.pipeline().addLast( new HttpHandler() );
                            // ch.pipeline().addLast( new DiscardServerHandler() );
                        }
                    } )
                    .option( ChannelOption.SO_BACKLOG, 128 )
                    .childOption( ChannelOption.SO_KEEPALIVE, true );

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind( port ).sync(); // (7)
            System.out.println( "web服务器开始启动，监听端口：" + port);
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

    public static void main( String[] args ){

        new Launcher().run();


    }
}
