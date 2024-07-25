package org.spring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.spring.beans.factory.config.BeanDefinition;
import org.spring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描classpath下有org.spring.stereotype.Component注解的类
 *
 * @author Salmon
 * @since 2024-07-25
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        // 扫描有org.spring.stereotype.Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
