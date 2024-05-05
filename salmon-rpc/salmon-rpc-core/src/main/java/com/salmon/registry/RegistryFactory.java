package com.salmon.registry;

import com.salmon.spi.SpiLoader;

/**
 * 注册中心工厂（用于获取注册中心对象）
 *
 * @author Salmon
 * @since 2024-05-04
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取注册中心实例
     *
     * @param key 指定的注册中心
     * @return 注册中心实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
