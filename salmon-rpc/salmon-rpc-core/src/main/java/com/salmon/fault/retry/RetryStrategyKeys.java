package com.salmon.fault.retry;

/**
 * 重试策略键名常量
 *
 * @author Salmon
 * @since 2024-05-14
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
