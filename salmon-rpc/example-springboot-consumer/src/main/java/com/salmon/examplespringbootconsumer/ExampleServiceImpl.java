package com.salmon.examplespringbootconsumer;

import com.salmon.model.User;
import com.salmon.salmonrpc.springboot.starter.annotation.RpcReference;
import com.salmon.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 测试 rpc 调用
 *
 * @author Salmon
 * @since 2024-05-16
 */
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("Salmon");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
