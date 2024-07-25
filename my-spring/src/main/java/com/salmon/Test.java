package com.salmon;

import com.salmon.service.UserInterface;
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
        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
        userService.userBuy(); // 先执行代理逻辑，再处理业务逻辑
    }
}
