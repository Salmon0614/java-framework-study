package com.salmon.salmonrpc.springboot.starter.bootstrap;

import com.salmon.RpcApplication;
import com.salmon.config.RegistryConfig;
import com.salmon.config.RpcConfig;
import com.salmon.model.ServiceMetaInfo;
import com.salmon.registry.LocalRegistry;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.salmonrpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Rpc 服务提供者启动
 *
 * @author Salmon
 * @since 2024-05-16
 */
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {

    /**
     * Bean 初始化后执行，注册服务
     *
     * @param bean     当前正在初始化的Bean对象
     * @param beanName 当前正在初始化的Bean的名称
     * @return 返回经过处理后的Bean对象
     * @throws BeansException BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        // 如果被标注为rpc服务
        if (rpcService != null) {
            // 需要注册的服务
            // 1. 获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            // 默认值处理，如果未指定，获取该类所实现的接口
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            // 2. 注册服务
            // 本地注册
            LocalRegistry.registry(serviceName, beanClass);

            // 全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            // 获取指定的服务注册中心实例
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            // 封装服务注册元信息
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceName(serviceName);
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
