package com.salmon.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.salmon.RpcApplication;
import com.salmon.model.RpcRequest;
import com.salmon.model.RpcResponse;
import com.salmon.serializer.Serializer;
import com.salmon.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发起 HTTP 请求，远程调用服务的方法
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
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
