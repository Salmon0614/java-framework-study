package org.spring.simple;

import org.spring.beans.factory.InitializingBean;
import org.spring.beans.factory.annotation.Autowired;
import org.spring.beans.factory.config.BeanPostProcessor;
import org.spring.context.annotation.Scope;
import org.spring.stereotype.Component;
import org.spring.simple.annotation.ComponentScan;
import org.spring.beans.factory.config.BeanDefinition;
import org.spring.utils.StringUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Salmon
 * @since 2024-07-24
 */
public class SalmonApplicationContext {

    private Class<?> configClass;
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
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
                // 实例化之前处理
                Object beanObj = createBean(beanName, beanDefinition);  // 单例Bean对象
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
                            // 如果当前bean实现了BeanPostProcessor接口
                            if (BeanPostProcessor.class.isAssignableFrom(beanClass)) {
                                BeanPostProcessor postProcessor = (BeanPostProcessor) beanClass.getDeclaredConstructor().newInstance();
                                beanPostProcessors.add(postProcessor);
                                continue;
                            }
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
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }

    }

    /**
     * '
     * 创建Bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义信息
     * @return bean对象
     */
    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        //如果bean需要代理，则直接返回代理对象
//        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
//        if (bean != null) {
//            return bean;
//        }
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 真正创建bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义信息
     * @return bean对象
     */
    public Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Object instance = beanClass.getDeclaredConstructor().newInstance();

            // 依赖注入
            for (Field declaredField : beanClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(instance, bean);
                }
            }
            // Aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // BeanPostProcessor 初始化之前处理
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // BeanPostProcessor 初始化后处理
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
                return createBean(beanName, beanDefinition);
            }
        } else {
            throw new RuntimeException("不存在该Bean");
        }
    }
}
