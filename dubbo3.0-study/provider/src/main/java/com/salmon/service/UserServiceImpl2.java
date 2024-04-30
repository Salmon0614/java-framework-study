package com.salmon.service;

import com.salmon.entity.User;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@DubboService(version = "2.0")
public class UserServiceImpl2 implements UserService {

    @Override
    public User getUser(String uid) {
        return new User(uid, "Salmon2");
    }
}
