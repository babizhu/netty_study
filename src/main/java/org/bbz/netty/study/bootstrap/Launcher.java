package org.bbz.netty.study.bootstrap;

import java.io.IOException;

/**
 * Created by liu_k on 2015/10/15.
 *
 * 学习proxyStudy的启动代码方式
 * 也就是说启动类是server的一个内部类
 *
 */
public class Launcher{
    public static void main( String[] args ) throws IOException{
        IServerBootstrap bootstrap = DefaultServer.bootstrapFromFile( "file2path" );
        bootstrap.withName( "TestServer" )
                .withAllowLocalOnly( false );
        bootstrap.start();
    }
}
