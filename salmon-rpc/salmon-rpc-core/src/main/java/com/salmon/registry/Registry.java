package com.salmon.registry;

import com.salmon.config.RegistryConfig;
import com.salmon.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 *
 * @author Salmon
 * @since 2024-05-04
 */
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig 初始化配置
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo 服务注册信息
     * @throws Exception 异常
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo 待注销的服务信息
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return 某服务的所有节点信息
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();
}
