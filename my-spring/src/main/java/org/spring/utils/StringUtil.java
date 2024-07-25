package org.spring.utils;

public class StringUtil {

    /**
     * 将字符串的首字母转换为大写
     *
     * @param input 输入的字符串
     * @return 首字母大写的字符串
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * 将字符串的首字母转换为小写
     *
     * @param input 输入的字符串
     * @return 首字母小写的字符串
     */
    public static String uncapitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}