package org.bbz.netty.study.NioEventGroup;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu_k on 2015/10/15.
 */
public class Launcher{

    private static class A{
        private A(){
            System.out.println( "A.A" );
        }
    }

    private static class B{
        A a;
        private B(){
            a = new A();
            System.out.println( "B.B" );
        }
    }
    private static final int OUTGOING_WORKER_THREADS = 20;

    public static void main( String[] args ) throws InterruptedException{
        new B();
        NioEventLoopGroup outboundWorkerGroup = new NioEventLoopGroup( OUTGOING_WORKER_THREADS );
        System.out.println( outboundWorkerGroup.executorCount() );//线程数量
        Set<EventExecutor> children = outboundWorkerGroup.children();
        for( EventExecutor child : children ) {
            System.out.println( child );
            System.out.println( child.children() );
        }

        for( int i = 0; i < 40; i++ ) {
            EventLoop next = outboundWorkerGroup.next();

            next.execute( () -> {
                System.out.println( "eventLoop:" + Thread.currentThread().getName() );
                try {
                    Thread.sleep( 100000 );
                } catch( InterruptedException e ) {
                    e.printStackTrace();
                }
            } );
        }

        System.out.println(outboundWorkerGroup.children().size());
        outboundWorkerGroup.execute( () -> System.out.println( Thread.currentThread().getName() ) );
        outboundWorkerGroup.execute( () -> System.out.println( Thread.currentThread().getName() ) );
        outboundWorkerGroup.execute( () -> System.out.println( Thread.currentThread().getName() ) );


        outboundWorkerGroup.schedule( () -> {
                    int i = 90;
                    System.out.println( "scheduleAtFixedRate:" + i++ );
                },
                9, TimeUnit.SECONDS );

        Future<?> future = outboundWorkerGroup.shutdownGracefully();
        //future.sync();


        Thread.sleep( 1000000 );
        outboundWorkerGroup.awaitTermination( 1, TimeUnit.DAYS );

    }
}
