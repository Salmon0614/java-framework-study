package org.spring.beans.factory.config;

import org.spring.beans.BeansException;

/**
 * 用于修改实例化后的bean的修改扩展点
 *
 * @author Salmon
 * @since 2024-07-25
 */
public interface BeanPostProcessor {

    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 处理后的bean对象
     * @throws BeansException bean异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 处理后的bean对象
     * @throws BeansException bean异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
