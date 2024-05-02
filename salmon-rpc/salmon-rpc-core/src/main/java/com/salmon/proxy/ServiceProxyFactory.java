package com.salmon.proxy;

import com.salmon.RpcApplication;

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
     * @param serviceClass 代理的接口的 Class 对象
     * @param <T>          代理对象的目标类型
     * @return 代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类来获取 Mock 代理对象
     *
     * @param serviceClass 代理的接口的 Class 对象
     * @param <T>          代理对象的目标类型
     * @return 代理对象
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
