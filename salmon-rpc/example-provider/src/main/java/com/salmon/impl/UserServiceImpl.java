package com.salmon.impl;

import com.salmon.model.User;
import com.salmon.service.UserService;

/**
 * 用户服务实现类
 *
 * @author Salmon
 * @since 2024-04-25
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("模拟：根据用户名查询信息..." + user.getName());
        return user;
    }
}
