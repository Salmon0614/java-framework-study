package com.salmon.loadbalancer;

import cn.hutool.core.util.StrUtil;
import com.salmon.loadbalancer.impl.RoundRobinLoadBalancer;
import com.salmon.spi.SpiLoader;

/**
 * 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 *
 * @author Salmon
 * @since 2024-05-14
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取负载均衡实现实例
     *
     * @param key 负载均衡键名
     * @return 指定的负载均衡实现
     */
    public static LoadBalancer getInstance(String key) {
        if (StrUtil.isBlank(key)) {
            return DEFAULT_LOAD_BALANCER;
        }
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
