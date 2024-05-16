package com.salmon.bootstrap;

import com.salmon.RpcApplication;
import com.salmon.config.RegistryConfig;
import com.salmon.config.RpcConfig;
import com.salmon.model.ServiceMetaInfo;
import com.salmon.model.ServiceRegisterInfo;
import com.salmon.registry.LocalRegistry;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.server.HttpServer;
import com.salmon.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 *
 * @author Salmon
 * @since 2024-05-16
 */
public class ProviderBootstrap {

    /**
     * 初始化
     *
     * @param serviceRegisterInfoList 服务注册列表
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();

        // 注册服务到注册中心
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            // 注册服务
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.registry(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
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
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动 TCP 服务
        HttpServer httpServer = new VertxTcpServer();
        httpServer.doStart(rpcConfig.getServerPort());
    }
}
