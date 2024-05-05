package com.salmon;

import com.salmon.config.RegistryConfig;
import com.salmon.config.RpcConfig;
import com.salmon.constant.RpcConstant;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 *
 * @author Salmon
 * @since 2024-05-02
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig 配置
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig);
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        // 根据注册中心类别获取注册中心实例
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        // 创建并注册 Shutdown Hook，JVM 退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 获取配置
     *
     * @return 配置
     */
    public static RpcConfig getRpcConfig() {
        // 双检锁检测
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
