package com.salmon;

import com.salmon.config.RegistryConfig;
import com.salmon.config.RpcConfig;
import com.salmon.impl.UserServiceImpl;
import com.salmon.model.ServiceMetaInfo;
import com.salmon.registry.LocalRegistry;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.server.HttpServer;
import com.salmon.server.tcp.VertxTcpServer;
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
        String serviceName = UserService.class.getName();
        LocalRegistry.registry(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        // 获取注册中心实例
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

        // 封装服务注册信息
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            // 注册服务
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        HttpServer httpServer = new VertxTcpServer();
        httpServer.doStart(rpcConfig.getServerPort());
    }
}
