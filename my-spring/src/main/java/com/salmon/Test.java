package com.salmon;

import com.salmon.service.UserService;
import org.spring.simple.SalmonApplicationContext;

/**
 * 测试类
 *
 * @author Salmon
 * @since 2024-07-24
 */
public class Test {

    public static void main(String[] args) {
        SalmonApplicationContext applicationContext = new SalmonApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
//        Object userService1 = applicationContext.getBean("userService");
//        Object userService2 = applicationContext.getBean("userService");
//        System.out.println(userService);
//        System.out.println(userService1);
//        System.out.println(userService2);
        userService.userBuy();
    }
}
