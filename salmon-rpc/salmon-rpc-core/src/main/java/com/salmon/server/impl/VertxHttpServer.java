package com.salmon.server.impl;

import com.salmon.server.HttpServer;
import com.salmon.server.handler.HttpServerHandler;
import io.vertx.core.Vertx;

/**
 * 基于 Vert.x 的HTTP服务器实现
 *
 * @author Salmon
 * @since 2024-04-25
 */
public class VertxHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 Http 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
