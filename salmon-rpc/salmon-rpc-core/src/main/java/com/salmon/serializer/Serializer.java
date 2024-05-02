package com.salmon.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 *
 * @author Salmon
 * @since 2024-04-25
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param object 待序列化对象
     * @param <T>    对象类型
     * @return 返回序列化后的字节数组
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes 数据
     * @param type  类型
     * @param <T>   类型
     * @return 反序列化对象
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
