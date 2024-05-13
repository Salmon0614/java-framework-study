package com.salmon.server.tcp;

import com.salmon.server.HttpServer;
import com.salmon.server.handler.TcpServerHandler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

/**
 * 基于 Vert.x 的TCP服务器实现
 *
 * @author Salmon
 * @since 2024-05-07
 */
public class VertxTcpServer implements HttpServer {

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }

    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(new TcpServerHandler());

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.err.println("Failed to start TCP server: " + result.cause());
            }
        });
    }

}
