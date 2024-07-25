package com.salmon.service;

import org.spring.simple.BeanNameAware;
import org.spring.beans.factory.annotation.Autowired;
import org.spring.stereotype.Component;

/**
 * @author Salmon
 * @since 2024-07-24
 */
@Component("userService")
//@Scope("prototype")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String name;

    public void userBuy() {
        System.out.println(name);
        System.out.println(beanName);
        orderService.createOrder(10001);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setName(String name) {
        this.name = name;
    }
}
