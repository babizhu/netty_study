package org.bbz.netty.study.db;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.bbz.netty.study.util.DatabaseUtil;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu_k on 2017/5/23.
 * 开启一个线程往数据库写入数据
 */
public class WriteDBHandler extends ChannelHandlerAdapter{
    //    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup( 16 );

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception{
        System.out.println( );
        String content = ((ByteBuf)msg).toString( Charset.defaultCharset());
        System.out.println(content + " : " + Thread.currentThread().getName());
        writeToDBWithThread( ctx, content );
//        new BlockingExecutorsUseCallerRun()
//        super.channelRead( ctx, msg );


    }

    private void writeToDBWithThread( ChannelHandlerContext ctx, String msg ){
        group.execute( () -> {
            try {
                TimeUnit.SECONDS.sleep( 1 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
//            doWriteDB( msg );
            String result = msg + " : " + Thread.currentThread().getName();
            ctx.writeAndFlush( Unpooled.wrappedBuffer( result.getBytes() ) );
        } );
    }

    private void doWriteDB( String msg ){
        Connection con = DatabaseUtil.INSTANCE.getConnection();
        PreparedStatement pst = null;
        try {
            String sql = "select " + msg;
            pst = con.prepareStatement( sql );
            pst.executeUpdate();
        } catch( SQLException e ) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.INSTANCE.close( null, pst, con );

        }
    }
}
