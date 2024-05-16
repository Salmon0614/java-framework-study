package com.salmon.fault.tolerant;

import com.salmon.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 *
 * @author Salmon
 * @since 2024-05-15
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return rpc 响应
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
