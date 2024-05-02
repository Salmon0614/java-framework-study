package com.salmon;

import com.salmon.config.RpcConfig;
import com.salmon.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author Salmon
 * @since 2024-05-02
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
//        ...
    }
}
