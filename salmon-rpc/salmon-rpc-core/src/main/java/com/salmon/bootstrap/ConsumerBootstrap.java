package com.salmon.bootstrap;

import com.salmon.RpcApplication;

/**
 * 服务消费者启动类（初始化）
 *
 * @author Salmon
 * @since 2024-05-16
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
