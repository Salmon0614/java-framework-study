package com.salmon.registry;

import com.salmon.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心服务本地缓存
 *
 * @author Salmon
 * @since 2024-05-05
 */
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    private List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache 新的缓存列表
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     *
     * @return 缓存列表
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
