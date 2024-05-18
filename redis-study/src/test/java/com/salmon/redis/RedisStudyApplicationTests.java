package com.salmon.redis;

import com.salmon.redis.model.ChatMessageReq;
import com.salmon.redis.model.User;
import com.salmon.redis.service.UserService;
import com.salmon.redis.utils.RedisUtils;
import com.salmon.redis.utils.UserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisStudyApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testRedisson() {
        userService.applyApprove(1L);
    }

    @Test
    public void redis() {
        ChatMessageReq request = new ChatMessageReq("test", 1L, 2L);
//        redisTemplate.opsForValue().set("chat_message_req:1001", request);

        // utils
        RedisUtils.set("124", 1, 5, TimeUnit.SECONDS);
        RedisUtils.set("123", "salmon", 5, TimeUnit.MINUTES);
        Long expire = RedisUtils.getExpire("124", TimeUnit.DAYS);
        System.out.println(expire);
        RedisUtils.set("uid", request);
        ChatMessageReq chatMessageReq = RedisUtils.get("uid", ChatMessageReq.class);
        String str = RedisUtils.getStr("123");
        System.out.println(str);
    }

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
