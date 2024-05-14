package com.salmon.fault.retry.impl;

import com.github.rholder.retry.*;
import com.salmon.fault.retry.RetryStrategy;
import com.salmon.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 固定时间间隔 - 重试策略
 *
 * @author Salmon
 * @since 2024-05-14
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy {

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                // 指定重试抛出的异常类型
                .retryIfExceptionOfType(Exception.class)
                // 指定等待策略，固定 3 秒
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                // 停止策略，超过最大重试次数停止
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                // 监听器，监听重试
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}", attempt.getAttemptNumber());
                    }
                }).build();
        return retryer.call(callable);
    }
}
