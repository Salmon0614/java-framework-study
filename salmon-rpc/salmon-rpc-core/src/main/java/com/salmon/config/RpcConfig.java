package com.salmon.config;

import com.salmon.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author Salmon
 * @since 2024-05-02
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "salmon-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
}
