package org.spring;

import org.spring.context.annotation.Scope;
import org.spring.stereotype.Component;
import org.spring.annotation.ComponentScan;
import org.spring.beans.factory.config.BeanDefinition;
import org.spring.utils.StringUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Salmon
 * @since 2024-07-24
 */
public class SalmonApplicationContext {

    private Class<?> configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); // bean定义
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>(); // 单例池

    public SalmonApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan 注解 --> 扫描路径 ---> 扫描
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new RuntimeException("没有找到指定注解");
        }
        ComponentScan componentScanAnnotation = configClass.getDeclaredAnnotation(ComponentScan.class);
        String basePackage = componentScanAnnotation.value();  // 扫描路径
        // 扫描路径
        scan(basePackage);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            BeanDefinition beanDefinition = beanDefinitionEntry.getValue();
            if (beanDefinition.isSingleton()) {
                Object beanObj = createBean(beanDefinition);  // 单例Bean对象
                singletonObjects.put(beanName, beanObj);
            }
        }
    }

    /**
     * 扫包
     *
     * @param basePackage 包名
     */
    public void scan(String basePackage) {
        // 如：com/salmon/service
        basePackage = basePackage.replace(".", File.separator);
        // 三种类加载器
        // Bootstrap ---> jre/lib
        // Ext ---> jre/ext/lib
        // App ---> classpath
        ClassLoader classLoader = this.getClass().getClassLoader(); // 拿到当前的类加载器 app
        URL resource = classLoader.getResource(basePackage);
        if (resource == null) {
            throw new RuntimeException("包名路径不存在");
        }
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                // 全路径，如：/Users/chenxiangwen/Desktop/java_study/java-framework-study/my-spring/target/classes/com/salmon/service/UserService.class
                String absolutePath = f.getAbsolutePath();
                if (absolutePath.endsWith(".class")) {
                    // 如：com/salmon/service/UserService
                    String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    // 如：com.salmon.service.UserService
                    className = className.replace(File.separator, ".");
                    try {
                        Class<?> beanClass = classLoader.loadClass(className);
                        if (beanClass.isAnnotationPresent(Component.class)) {
                            // 表示这个类是一个Bean
                            // 解析类，看看是单例创建，还是使用原型创建（prototype）
                            // 封装Bean的定义，之后要做的处理 ---- BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
                            Component componentAnnotation = beanClass.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnnotation.value(); // bean名称
                            if (beanName == null || beanName.isEmpty()) {
                                beanName = beanClass.getSimpleName();
                                // 首字母转小写
                                beanName = StringUtil.uncapitalizeFirstLetter(beanName);
                            }
                            if (beanClass.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnnotation = beanClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }

    }

    /**
     * 创建Bean
     *
     * @return bean对象
     */
    public Object createBean(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Object instance = beanClass.getDeclaredConstructor().newInstance();
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.isSingleton()) {
                return singletonObjects.get(beanName);
            } else {
                // 创建Bean对象
                return createBean(beanDefinition);
            }
        } else {
            throw new RuntimeException("不存在该Bean");
        }
    }
}
