package com.salmon.service;

import com.salmon.model.User;

/**
 * 用户服务
 *
 * @author Salmon
 * @since 2024-04-25
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user 查询条件
     * @return 用户信息
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     *
     * @return 短整型数字
     */
    default short getNumber() {
        return 1;
    }
}
