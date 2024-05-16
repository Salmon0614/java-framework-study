package com.salmon;

import com.salmon.bootstrap.ProviderBootstrap;
import com.salmon.impl.UserServiceImpl;
import com.salmon.model.ServiceRegisterInfo;
import com.salmon.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易服务提供者示例
 *
 * @author Salmon
 * @since 2024-05-02
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> objectServiceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(objectServiceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
