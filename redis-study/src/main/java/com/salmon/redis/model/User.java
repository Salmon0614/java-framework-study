package com.salmon.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Salmon
 * @since 2024-05-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String username;

    private String account;

    private String password;

    private LocalDateTime loginTime;

    private String secret;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
