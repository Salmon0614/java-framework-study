package org.spring.beans.factory.support;

import org.spring.beans.BeansException;
import org.spring.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册表接口
 *
 * @author Salmon
 * @since 2024-07-25
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注BeanDefinition
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName bean名称
     * @return bean定义信息
     * @throws BeansException 如果找不到BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 是否包含指定名称的BeanDefinition
     *
     * @param beanName bean名称
     * @return 是否包含
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回定义的所有bean的名称
     *
     * @return bean名称集合
     */
    String[] getBeanDefinitionNames();
}
