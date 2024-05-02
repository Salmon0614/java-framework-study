package com.salmon.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 *
 * @author Salmon
 * @since 2024-05-02
 */
public class ConfigUtils {

    /**
     * @param tClass 指定配置类
     * @param prefix 配置文件加载前缀
     * @param <T>    类
     * @return 配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置
     *
     * @param tClass      指定配置类
     * @param prefix      配置文件加载前缀
     * @param environment 运行环境：如prod、dev、test
     * @param <T>         类
     * @return 配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        // application-dev.properties
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}
