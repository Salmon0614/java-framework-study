package com.salmon.server;

/**
 * HTTP 服务器接口
 *
 * @author Salmon
 * @since 2024-04-25
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port 端口
     */
    void doStart(int port);
}
