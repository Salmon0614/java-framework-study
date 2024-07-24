package com.salmon;

import com.spring.SalmonApplicationContext;

/**
 * 测试类
 *
 * @author Salmon
 * @since 2024-07-24
 */
public class Test {

    public static void main(String[] args) {
        SalmonApplicationContext applicationContext = new SalmonApplicationContext(AppConfig.class);
        Object userService = applicationContext.getBean("userService");
    }
}
