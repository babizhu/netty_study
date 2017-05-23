package org.bbz.netty.study.db;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by liu_k on 2017/5/23.
 *
 */
public class WirteDBChannelInitializer extends ChannelInitializer{
    private final EventExecutorGroup group = new DefaultEventExecutorGroup( 16 );
    public WirteDBChannelInitializer(){
        System.out.println( "WirteDBChannelInitializer.WirteDBChannelInitializer" );
    }

    @Override
    protected void initChannel( Channel ch ) throws Exception{
        ch.pipeline().addLast( group, new WriteDBHandler() );
    }
}
