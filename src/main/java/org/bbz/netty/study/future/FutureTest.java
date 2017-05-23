package org.bbz.netty.study.future;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liu_k on 2015/10/19.
 * 学习Future的用法
 */
public class FutureTest{
    public static void main( String[] args ) throws ExecutionException, InterruptedException{

        t1();
        ExecutorService service = Executors.newCachedThreadPool();
        List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        Future<Integer> future = service.submit( () -> {
            int sum = 0;
            for( Integer integer : list ) {
                sum += integer;
            }
            System.out.println( Thread.currentThread().getName() );
            Thread.sleep( 10000 );
            return sum;

        } );
        System.out.println( future.isDone() );
        System.out.println( "cancle : " + future.cancel( true ) );
//        System.out.println( future.get() );
        System.out.println( Thread.currentThread().getName() );

        init();
    }

    private static  void init(){
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
            ChannelFuture future = b.bind( port );

            ChannelFuture f = future.sync(); // (7)
            System.out.println( "future == futuer.sync() is " + (future == f) );

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

    private interface Listeners<F extends FutureWithListeners> extends EventListener{
        void operationComplete(F future) throws Exception;
    }

    private interface FutureWithListeners<V> extends Future<V>{
        void addListeners( Listeners listeners);
    }

    /**
     * 耗时任务快速返回一个future
     * @return
     */
    private FutureWithListeners<?> doSometingAnync(){
        FutureWithListeners<Void> futureWithListeners = new FutureWithListeners<Void>(){
            @Override
            public boolean cancel( boolean mayInterruptIfRunning ){
                return false;
            }

            @Override
            public boolean isCancelled(){
                return false;
            }

            @Override
            public boolean isDone(){
                return false;
            }

            @Override
            public Void get() throws InterruptedException, ExecutionException{
                return null;
            }

            @Override
            public Void get( long timeout, TimeUnit unit ) throws InterruptedException, ExecutionException, TimeoutException{
                return null;
            }

            @Override
            public void addListeners( Listeners listeners ){

            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute( () -> {
            //这是一个耗时任务

        });

        return null;
    }
    private static void t1(){
        //final Channel channel = channelFactory().newChannel();
        ExecutorService service = Executors.newCachedThreadPool();
        Future<?> future = service.submit( () -> {
            System.out.println();
        } );

        //future.
    }
}
