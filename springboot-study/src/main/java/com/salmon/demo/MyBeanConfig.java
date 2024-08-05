package com.salmon.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyBean myBean() {
        return new MyBean();
    }
}