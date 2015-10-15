package org.bbz.netty.study.bootstrap;

/**
 * Created by liu_k on 2015/10/15.
 */
public interface IServerBootstrap{
    /**
     * 设置服务器的名字
     * @param name  服务器的名字
     * @return      返回自身，方便调用者链式处理
     */
    IServerBootstrap withName( String name );

    /**
     * <p>
     * Specify whether or not to only allow local connections.
     * </p>
     *
     * <p>
     * Default = true
     * </p>
     *
     * @param allowLocalOnly
     * @return
     */
    IServerBootstrap withAllowLocalOnly( boolean allowLocalOnly );

    /**
     * <p>
     * Build and starts the server.
     * </p>
     *
     * @return the newly built and started server
     */
    IServer start();
}
