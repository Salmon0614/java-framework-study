package com.salmon.service;

import com.salmon.User;
import com.salmon.UserRequest;
import com.salmon.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(UserRequest request) {
        User user = User.newBuilder().setUid(request.getUid()).setUsername("Salmon").build();
        return user;
    }
}
