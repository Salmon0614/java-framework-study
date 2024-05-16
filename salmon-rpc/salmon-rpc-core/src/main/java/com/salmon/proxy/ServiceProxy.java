package com.salmon.proxy;

import cn.hutool.core.collection.CollUtil;
import com.salmon.RpcApplication;
import com.salmon.config.RpcConfig;
import com.salmon.constant.RpcConstant;
import com.salmon.fault.retry.RetryStrategy;
import com.salmon.fault.retry.RetryStrategyFactory;
import com.salmon.fault.tolerant.TolerantStrategy;
import com.salmon.fault.tolerant.TolerantStrategyFactory;
import com.salmon.loadbalancer.LoadBalancer;
import com.salmon.loadbalancer.LoadBalancerFactory;
import com.salmon.model.RpcRequest;
import com.salmon.model.RpcResponse;
import com.salmon.model.ServiceMetaInfo;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author Salmon
 * @since 2024-04-26
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @param proxy  代理对象
     * @param method 当前执行的方法
     * @param args   方法的参数
     * @return 返回方法的运行结果
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 获取到具体的注册中心实例
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        // 封装服务注册信息
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        // 获取该服务的所有节点
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }

        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        // rpc 请求
        RpcResponse rpcResponse;
        try {
            // 使用重试机制
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    // 发送 TCP 请求
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 使用容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}
