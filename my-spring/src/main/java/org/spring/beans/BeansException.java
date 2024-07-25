package org.spring.beans;

/**
 * Bean异常
 *
 * @author Salmon
 * @since 2024-07-25
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}