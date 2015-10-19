package org.bbz.netty.study.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liu_k on 2015/10/15.
 * 学习proxyStudy的启动代码方式
 * 也就是说启动类是server的一个内部类
 */
public class DefaultServer implements IServer{

    private static class DefaultBootstrap implements IServerBootstrap{

        private String name = "StudyServer";
        private boolean allowLocalOnly = true;
        private int port;

        public DefaultBootstrap( Properties props ){
            this.port = Integer.parseInt( props.getProperty( "PORT", "0" ) ) ;
        }

        @Override
        public IServerBootstrap withName( String name ){
            this.name = name;
            return this;
        }

        @Override
        public IServerBootstrap withAllowLocalOnly( boolean allowLocalOnly ){
            this.allowLocalOnly = allowLocalOnly;
            return this;
        }

        @Override
        public IServer start(){
            return build().start();
        }

        private DefaultServer build(){
            return new DefaultServer();
        }
    }

    private IServer start(){
        return null;
    }

    /**
     * Bootstrap a new {@link DefaultServer} using defaults from the
     * given file.
     *
     * @param path
     * @return
     */
    public static IServerBootstrap bootstrapFromFile( String path ) throws IOException{
        final File propsFile = new File( path );
        Properties props = new Properties();

        if( propsFile.isFile() ) {
            InputStream is = null;
            try {
                is = new FileInputStream( propsFile );
                props.load( is );
            } catch( final IOException e ) {
                e.printStackTrace();
            } finally {
                is.close();
            }
        }

        return new DefaultBootstrap( props );
    }

}
