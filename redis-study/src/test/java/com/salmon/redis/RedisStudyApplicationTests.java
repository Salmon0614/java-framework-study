package com.salmon.redis;

import com.salmon.redis.model.User;
import com.salmon.redis.service.UserService;
import com.salmon.redis.utils.UserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class RedisStudyApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        User user = User.builder()
                .id(1001L)
                .username("Salmon")
                .account("1179732961@qq.com")
                .secret("xxxxxx")
                .password("xxxxxxx")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .loginTime(LocalDateTime.now())
                .build();
        UserHolder.setUser(user);
        userService.signInByDate(LocalDateTime.now().minusDays(4));
        userService.signInByDate(LocalDateTime.now().minusDays(2));
        userService.signInByDate(LocalDateTime.now().minusDays(1));
        userService.signInByDate(LocalDateTime.now());
        System.out.println("连续签到：" + userService.getSignCount() + "天");
    }

}
