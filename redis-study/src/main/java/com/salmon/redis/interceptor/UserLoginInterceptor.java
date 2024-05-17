package com.salmon.redis.interceptor;

import com.salmon.redis.model.User;
import com.salmon.redis.utils.UserHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * 用户登录拦截器
 *
 * @author Salmon
 * @since 2024-05-17
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果请求是controller处理方法
        if (handler instanceof HandlerMethod) {
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
        }
        return true;
    }
}
