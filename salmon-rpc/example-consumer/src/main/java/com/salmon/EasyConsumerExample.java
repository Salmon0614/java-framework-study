package com.salmon;

import com.salmon.model.User;
import com.salmon.proxy.ServiceProxyFactory;
import com.salmon.proxy.UserServiceProxy;
import com.salmon.service.UserService;

/**
 * 简易服务消费者示例
 *
 * @author Salmon
 * @since 2024-04-25
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Salmon");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(user.getName());
        } else {
            System.out.println("user == null");
        }
    }
}