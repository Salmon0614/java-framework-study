package com.salmon.fault.retry;

import cn.hutool.core.util.StrUtil;
import com.salmon.fault.retry.impl.NoRetryStrategy;
import com.salmon.spi.SpiLoader;

/**
 * 重试策略工厂（用于获取重试器对象）
 *
 * @author Salmon
 * @since 2024-05-14
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取重试策略实例
     *
     * @param key 重试策略键名
     * @return 指定的重试策略实现
     */
    public static RetryStrategy getInstance(String key) {
        if (StrUtil.isBlank(key)) {
            return DEFAULT_RETRY_STRATEGY;
        }
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
