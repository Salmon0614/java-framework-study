package com.spring;

/**
 * @author Salmon
 * @since 2024-07-24
 */
public class SalmonApplicationContext {

    private Class<?> configClass;

    public SalmonApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan 注解 --> 扫描路径 ---> 扫描
    }

    public Object getBean(String beanName) {
        return null;
    }
}
