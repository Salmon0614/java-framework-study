package com.salmon;

import com.salmon.bootstrap.ConsumerBootstrap;
import com.salmon.model.User;
import com.salmon.proxy.ServiceProxyFactory;
import com.salmon.service.UserService;

/**
 * 简易服务消费者示例
 *
 * @author Salmon
 * @since 2024-05-02
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();
        // 获取代理
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
