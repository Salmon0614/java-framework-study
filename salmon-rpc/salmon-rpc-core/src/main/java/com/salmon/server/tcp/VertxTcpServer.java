package com.salmon.server.tcp;

import com.salmon.server.HttpServer;
import com.salmon.server.handler.TcpServerHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;

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
        server.connectHandler(socket -> {
            // 构造 parser
            RecordParser parser = RecordParser.newFixed(8);
            parser.setOutput(new Handler<Buffer>() {
                // 初始化
                int size = -1;
                // 一次完整的读取（头 + 体）
                Buffer resultBuffer = Buffer.buffer();

                @Override
                public void handle(Buffer buffer) {
                    // 1. 每次循环，首先读取消息头
                    if (-1 == size) {
                        // | 0x0 | body.length | body.data |
                        // 发送的消息头是有两个整数，每个整数占用 4 个字节，消息头部总共占 8 个字节
                        // 读取消息体长度（读取第二个整数，从下标为 4 的位置读取一个整数（32位的整数值））
                        size = buffer.getInt(4);
                        // 将其设置为下次要读取的消息长度
                        parser.fixedSizeMode(size);
                        // 写入头信息到结果
                        resultBuffer.appendBuffer(buffer);
                    } else {
                        // 2. 然后读取消息体
                        // 写入体信息到结果
                        resultBuffer.appendBuffer(buffer);
                        System.out.println(resultBuffer.toString());
                        // 重置一轮，下次重新先读取消息头
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });

            socket.handler(parser);
        });


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
