package org.spring.beans.factory.config;

import org.spring.beans.BeansException;

/**
 * 实例化回调bean的修改扩展点
 *
 * @author Salmon
 * @since 2024-07-25
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean实例化之前执行
     *
     * @param beanClass bean类型
     * @param beanName  bean名称
     * @return bean对象
     * @throws BeansException bean异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * bean实例化之后，设置属性之前执行
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @throws BeansException bean异常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

//	/**
//	 * bean实例化之后，设置属性之前执行
//	 *
//	 * @param pvs
//	 * @param bean
//	 * @param beanName
//	 * @return
//	 * @throws BeansException
//	 */
//	PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
//			throws BeansException;

    /**
     * 提前暴露bean
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return bean对象
     * @throws BeansException bean异常
     */
    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

}