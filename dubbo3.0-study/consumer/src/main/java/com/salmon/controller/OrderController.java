package com.salmon.controller;

import com.salmon.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order")
    public String getOrder() {
        return orderService.getOrder();
    }
}
