package com.salmon.service;

import com.salmon.service.UserService;
import org.spring.beans.BeansException;
import org.spring.beans.factory.config.BeanPostProcessor;
import org.spring.stereotype.Component;

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
        return bean;
    }
}
