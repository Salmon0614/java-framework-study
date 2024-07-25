package org.spring.beans.factory;

import org.spring.beans.BeansException;

/**
 * Bean容器
 *
 * @author Salmon
 * @since 2024-07-25
 */
public interface BeanFactory {

    /**
     * 获取bean
     *
     * @param name bean名称
     * @return bean对象
     * @throws BeansException bean不存在时
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据名称和类型查找bean
     *
     * @param name         名称
     * @param requiredType 类型
     * @return bean对象
     * @throws BeansException bean不存在时
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 根据类型获取bean
     *
     * @param requiredType 类型
     * @return bean对象
     * @throws BeansException bean不存在时
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 是否包含bean
     *
     * @param name bean名称
     * @return 是否包含
     */
    boolean containsBean(String name);
}
