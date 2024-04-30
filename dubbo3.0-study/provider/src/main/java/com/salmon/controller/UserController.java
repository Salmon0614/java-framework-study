package com.salmon.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@RestController
public class UserController {
    // 无需传统http发起请求，而是使用rpc调用服务
//    @Resource
//    private UserServiceImpl userServiceImpl;
//
//    @GetMapping("/user")
//    public String getUser() {
//        return userServiceImpl.getUser();
//    }
}
