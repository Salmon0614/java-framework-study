package com.salmon.service;

import com.salmon.entity.User;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@Service
public class OrderService {

    @DubboReference(version = "2.0")
    private UserService userService;

    public String getOrder() {
//        User user = userService.getUser("1");

//        userService.sayHelloServerStream("Salmon", new StreamObserver<String>() {
//            @Override
//            public void onNext(String data) {
//                // 服务端返回的数据
//                System.out.println("接收到结果：" + data);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("complete");
//            }
//        });

        // 双端流
        StreamObserver<String> streamObserver = userService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("客户端接收数据："+data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("客户端接收完成");
            }
        });
        // 客户端向服务端发送数据
        streamObserver.onNext("1");
        streamObserver.onNext("2");
        streamObserver.onNext("3");
        streamObserver.onCompleted();
        return "success";
    }
}
