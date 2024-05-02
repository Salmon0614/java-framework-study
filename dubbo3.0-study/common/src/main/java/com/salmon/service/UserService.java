package com.salmon.service;

import com.salmon.entity.User;
import com.salmon.entity.UserRequest;
import org.apache.dubbo.common.stream.StreamObserver;

/**
 * @author Salmon
 * @since 2024-04-30
 */
public interface UserService {

    // UNARY
    User getUser(UserRequest request);

    // SERVER_STREAM 服务端流
    default void sayHelloServerStream(String name, StreamObserver<String> response) {
    }

    // CLIENT_STREAM / BI_STREAM 客户端流
    default StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return response;
    }

}
