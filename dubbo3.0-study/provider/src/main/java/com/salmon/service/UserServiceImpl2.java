//package com.salmon.service;
//
//import com.salmon.User;
//import com.salmon.UserRequest;
//import com.salmon.UserService;
//import org.apache.dubbo.common.stream.StreamObserver;
//import org.apache.dubbo.config.annotation.DubboService;
//
///**
// * @author Salmon
// * @since 2024-04-30
// */
//@DubboService(version = "2.0")
//public class UserServiceImpl2 implements UserService {
//
//    @Override
//    public User getUser(UserRequest request) {
//        return User.newBuilder().setUid(request.getUid()).setUsername("Salmon2").build();
//    }
//
//    // SERVER_STREAM
//    public void sayHelloServerStream(String name, StreamObserver<String> response) {
//        // 一次请求可以多次响应数据
//        response.onNext(name + " hello");
//        response.onNext(name + " world");
//        // 结束响应
//        response.onCompleted();
//    }
//
//    // CLIENT_STREAM
//    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
//        return new StreamObserver<String>() {
//            @Override
//            public void onNext(String data) {
//                System.out.println("服务端接收到的数据：" + data);
//                // 服务端向客户端发送数据
//                response.onNext("响应结果：" + data);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("服务端接收完成");
//            }
//        };
//    }
//}
