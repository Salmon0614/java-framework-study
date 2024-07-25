package com.salmon.service;

import org.spring.stereotype.Component;

/**
 * @author Salmon
 * @since 2024-07-25
 */
@Component
public class OrderService {

    public void createOrder(Integer userId) {
        System.out.println(userId + "下单了");
    }
}
