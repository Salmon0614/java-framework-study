package com.salmon.demo;


public class MyBean {
    public MyBean() {
        System.out.println("MyBean 实例化");
    }

    public void init() {
        System.out.println("MyBean 初始化");
    }

    public void destroy() {
        System.out.println("MyBean 销毁");
    }
}