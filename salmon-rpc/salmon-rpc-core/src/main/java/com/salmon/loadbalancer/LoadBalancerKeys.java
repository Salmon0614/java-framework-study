package com.salmon.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author Salmon
 * @since 2024-05-14
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";
    /**
     * 随机
     */
    String RANDOM = "random";
    /**
     * 一致性哈希
     */
    String CONSISTENT_HASH = "consistentHash";
}
