package com.salmon.redis.service.impl;

import com.salmon.redis.annotation.RedissonLock;
import com.salmon.redis.service.UserService;
import com.salmon.redis.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 用户服务实现
 *
 * @author Salmon
 * @since 2024-05-17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户签到
     *
     * @return 是否成功
     */
    @Override
    public Boolean signIn() {
        // 获取日期
        LocalDateTime now = LocalDateTime.now();
        return signInByDate(now);
    }

    /**
     * 指定日期签到
     *
     * @param date 指定日期 ":yyyyMM"，如：:202405
     * @return 是否成功
     */
    @Override
    public Boolean signInByDate(LocalDateTime date) {
        // 获取登录用户
        Long userId = UserHolder.getUser().getId();
        // 拼接 redis key
        String keySuffix = date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_IN_" + userId + keySuffix;
        // 获取当前是本月的第几天
        int dayOfMonth = date.getDayOfMonth();
        // 写入redis
        redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return true;
    }

    /**
     * 统计用户连续签到的天数
     *
     * @return 天数
     */
    @Override
    public Integer getSignCount() {
        // 获取登录用户
        Long userId = UserHolder.getUser().getId();
        // 获取日期
        LocalDateTime now = LocalDateTime.now();
        // 拼接 redis key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "USER_SIGN_IN_" + userId + keySuffix;
        // 获取当前是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        List<Long> result = redisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        //没有签到记录
        if (CollectionUtils.isEmpty(result)) {
            return 0;
        }
        // 获取结果
        Long num = result.get(0);
        if (num == null || num == 0) {
            return 0;
        }
        // 循环遍历
        int count = 0;
        while (true) {
            // 让这个数字与1做与运算，得到四种的最后一个bit位，判断这个数字是否为0
            if ((num & 1) == 0) {
                // 如果为0，则签到结束
                break;
            } else {
                count++;
            }
            // 无符号右移
            num >>>= 1;
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(key = "#uid")
    public void applyApprove(Long uid) {
        System.out.println(uid);
    }

}
