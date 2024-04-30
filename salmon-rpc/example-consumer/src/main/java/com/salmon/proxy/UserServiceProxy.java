package com.salmon.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.salmon.model.RpcRequest;
import com.salmon.model.RpcResponse;
import com.salmon.model.User;
import com.salmon.serializer.JdkSerializer;
import com.salmon.serializer.Serializer;
import com.salmon.service.UserService;

import java.io.IOException;

/**
 * 静态代理
 *
 * @author Salmon
 * @since 2024-04-26
 */
public class UserServiceProxy implements UserService {

    // 指定序列化器
    final Serializer serializer = new JdkSerializer();

    @Override
    public User getUser(User user) {
        // 发起请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发起 HTTP 请求，远程调用服务的方法
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute();
            byte[] result = httpResponse.bodyBytes();
            // 反序列化 RPC 响应
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
