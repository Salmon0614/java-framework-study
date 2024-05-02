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
 * @since 2024-05-02
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.registry(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServerImpl();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
