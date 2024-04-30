package com.salmon.service;

import com.salmon.entity.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@Service
public class OrderService {

    @DubboReference(version = "2.0")
    private UserService userService;

    public String getOrder() {
        User user = userService.getUser("1");
        return user.getUsername();
    }
}
