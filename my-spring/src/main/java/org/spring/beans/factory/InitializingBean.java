package org.spring.beans.factory;

/**
 * 初始化bean
 *
 * @author Salmon
 * @since 2024-07-25
 */
public interface InitializingBean {

	void afterPropertiesSet() throws Exception;
}