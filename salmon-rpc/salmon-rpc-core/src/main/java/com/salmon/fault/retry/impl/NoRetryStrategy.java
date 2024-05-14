package com.salmon.fault.retry.impl;

import com.salmon.fault.retry.RetryStrategy;
import com.salmon.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 不重试 - 重试策略
 *
 * @author Salmon
 * @since 2024-05-14
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
