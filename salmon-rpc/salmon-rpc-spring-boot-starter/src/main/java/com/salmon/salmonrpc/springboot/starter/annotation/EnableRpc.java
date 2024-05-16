package com.salmon.salmonrpc.springboot.starter.annotation;

import com.salmon.salmonrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.salmon.salmonrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.salmon.salmonrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 Rpc 注解
 *
 * @author Salmon
 * @since 2024-05-16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     */
    boolean needServer() default true;
}
