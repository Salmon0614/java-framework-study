package com.salmon.controller;

import com.salmon.service.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@RestController
public class UserController {
    @Resource
    private UserServiceImpl userServiceImpl;

    @GetMapping("/user")
    public String getUser() {
        return userServiceImpl.getUser();
    }
}
