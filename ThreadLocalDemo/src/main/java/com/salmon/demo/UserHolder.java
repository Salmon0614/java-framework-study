package com.salmon.demo;

import com.salmon.demo.model.User;

/**
 * 用户上下文持有者
 *
 * @author Salmon
 * @since 2024-05-25
 */
public class UserHolder {

    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}

/**
 * 测试
 */
class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            User user = new User();
            user.setId(1L);
            user.setName("Salmon");
            UserHolder.set(user);
            System.out.println("Thread A: " + UserHolder.get());
        }).start();

        new Thread(() -> {
            User user = new User();
            user.setId(2L);
            user.setName("薛之谦");
            UserHolder.set(user);
            System.out.println("Thread B: " + UserHolder.get());
        }).start();

        Thread.sleep(1000);

        System.out.println("Main: " + UserHolder.get());
    }
}