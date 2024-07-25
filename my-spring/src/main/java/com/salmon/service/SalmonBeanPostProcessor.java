package com.salmon.service;

import com.salmon.service.UserService;
import org.spring.beans.BeansException;
import org.spring.beans.factory.config.BeanPostProcessor;
import org.spring.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 自定义Bean修改扩展点
 *
 * @author Salmon
 * @since 2024-07-25
 */
@Component
public class SalmonBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化前");
        if (beanName.equals("userService")) {
            ((UserService) bean).setName("Salmon学spring");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化后");
        if (beanName.equals("userService")) {
            // 使用jdk动态代理
            Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
