package com.salmon;

import com.salmon.impl.UserServiceImpl;
import com.salmon.registry.LocalRegistry;
import com.salmon.server.HttpServer;
import com.salmon.server.impl.VertxHttpServerImpl;
import com.salmon.service.UserService;

/**
 * 简易服务提供者示例
 *
 * @author Salmon
 * @since 2024-04-25
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.registry(UserService.class.getName(), UserServiceImpl.class);

        // 启动 Web 服务
        HttpServer server = new VertxHttpServerImpl();
        server.doStart(8080);
    }
}