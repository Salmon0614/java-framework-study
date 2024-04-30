package com.salmon.proxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 *
 * @author Salmon
 * @since 2024-04-26
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类来获取代理对象
     *
     * @param serviceClass 服务类
     * @param <T>          服务接口类型
     * @return 代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
