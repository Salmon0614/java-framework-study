package com.salmon.fault.tolerant.impl;

import com.salmon.fault.tolerant.TolerantStrategy;
import com.salmon.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 *
 * @author Salmon
 * @since 2024-05-15
 */
@Slf4j
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务异常", e);
    }
}
