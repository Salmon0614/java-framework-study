package com.salmon.examplespringbootprovider;

import com.salmon.model.User;
import com.salmon.salmonrpc.springboot.starter.annotation.RpcService;
import com.salmon.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Salmon
 * @since 2024-05-16
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
