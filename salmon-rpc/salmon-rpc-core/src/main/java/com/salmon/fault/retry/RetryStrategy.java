package com.salmon.fault.retry;

import com.salmon.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略
 *
 * @author Salmon
 * @since 2024-05-14
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable 重试的任务
     * @return rpc响应结果
     * @throws Exception 异常
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
