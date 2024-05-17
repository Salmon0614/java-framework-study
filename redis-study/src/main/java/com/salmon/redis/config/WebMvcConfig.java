package com.salmon.redis.config;

import com.salmon.redis.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @author Salmon
 * @since 2022/9/23 21:43
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor());
    }

}