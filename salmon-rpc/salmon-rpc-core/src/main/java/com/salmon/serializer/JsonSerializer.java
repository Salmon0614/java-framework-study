package com.salmon.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salmon.model.RpcRequest;
import com.salmon.model.RpcResponse;

import java.io.IOException;

/**
 * Json 序列化器
 *
 * @author Salmon
 * @since 2024-05-03
 */
public class JsonSerializer implements Serializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> classType) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, classType);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, classType);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, classType);
        }
        return obj;
    }

    /**
     * 由于 Object 的原始对象会被擦除，导致反序列化时会被作为 LinkedHashMap 无法转换成原始对象，因此这里做了特殊处理
     *
     * @param rpcRequest rpc 请求
     * @param type       类型
     * @param <T>        反序列化的类型
     * @return 反序列化数据
     * @throws IOException IO异常
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        // 循环处理每个参数的类型
        for (int i = 0; i < parameterTypes.length; i++) {
            // 获取当前参数类型
            Class<?> aClass = parameterTypes[i];
            // 数据类型不匹配，则需要处理下类型
            if (!aClass.isAssignableFrom(args[i].getClass())) {
                // 重新对该参数数据做序列化
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                // 再反序列化为正确类型
                args[i] = OBJECT_MAPPER.readValue(argBytes, aClass);
            }
        }
        return type.cast(rpcRequest);
    }

    /**
     * 由于 Object 的原始对象会被擦除，导致反序列化时会被作为 LinkedHashMap 无法转换成原始对象，因此这里做了特殊处理
     *
     * @param rpcResponse rpc 响应
     * @param type        类型
     * @param <T>         反序列化的类型
     * @return 反序列化数据
     * @throws IOException IO异常
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        // 响应数据重新序列化
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        // 根据响应携带的数据类型重新反序列化为正确的数据
        Object data = OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType());
        rpcResponse.setData(data);
        return type.cast(rpcResponse);
    }
}
