package com.salmon.service;

import com.salmon.entity.User;

/**
 * @author Salmon
 * @since 2024-04-30
 */
public interface UserService {
    User getUser(String uid);
}
