package com.salmon.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.salmon.RpcApplication;
import com.salmon.config.RpcConfig;
import com.salmon.constant.RpcConstant;
import com.salmon.model.RpcRequest;
import com.salmon.model.RpcResponse;
import com.salmon.model.ServiceMetaInfo;
import com.salmon.registry.Registry;
import com.salmon.registry.RegistryFactory;
import com.salmon.serializer.Serializer;
import com.salmon.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author Salmon
 * @since 2024-04-26
 */
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
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

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
            // todo 暂时先取第一个节点，后续通过负载均衡来获取
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发起 HTTP 请求，远程调用服务的方法
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                if (httpResponse.isOk()) {
                    byte[] result = httpResponse.bodyBytes();
                    // 反序列化 RPC 响应
                    RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                    return rpcResponse.getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
