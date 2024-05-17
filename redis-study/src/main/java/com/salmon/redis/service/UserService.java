package com.salmon.redis.service;

import java.time.LocalDateTime;

/**
 * 用户服务
 *
 * @author Salmon
 * @since 2024-05-17
 */
public interface UserService {

    /**
     * 签到
     *
     * @return 是否成功
     */
    Boolean signIn();

    /**
     * 指定日期签到
     *
     * @param date 指定日期 ":yyyyMM"，如：:202405
     * @return 是否成功
     */
    Boolean signInByDate(LocalDateTime date);

    /**
     * 统计用户连续签到的天数
     *
     * @return 天数
     */
    Integer getSignCount();
}
