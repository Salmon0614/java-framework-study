package com.salmon.serializer;

import cn.hutool.core.util.StrUtil;
import com.salmon.spi.SpiLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 *
 * @author Salmon
 * @since 2024-05-03
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key 指定序列化器键名
     * @return 序列化实现实例
     */
    public static Serializer getInstance(String key) {
        if (StrUtil.isBlank(key)) {
            return DEFAULT_SERIALIZER;
        }
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
