package com.salmon.fault.tolerant;

import cn.hutool.core.util.StrUtil;
import com.salmon.fault.tolerant.impl.FailFastTolerantStrategy;
import com.salmon.spi.SpiLoader;

/**
 * 容错策略工厂（工厂模式，用于获取容错策略对象）
 *
 * @author Salmon
 * @since 2024-05-15
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取容错策略实例
     *
     * @param key 容错策略键名
     * @return 指定的容错策略实现
     */
    public static TolerantStrategy getInstance(String key) {
        if (StrUtil.isBlank(key)) {
            return DEFAULT_TOLERANT_STRATEGY;
        }
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
