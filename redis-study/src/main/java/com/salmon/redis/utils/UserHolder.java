package com.salmon.redis.utils;

import com.salmon.redis.model.User;

/**
 * @author Salmon
 * @since 2024-05-17
 */
public class UserHolder {

    private static final ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(User user) {
        THREAD_LOCAL.set(user);
    }

    public static User getUser() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
