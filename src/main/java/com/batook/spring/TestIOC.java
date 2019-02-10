package com.batook.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOC {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println(context.getBean("factoryMethodBean"));
        System.out.println(context.getBean("instanceFactory"));
        System.out.println(context.getBean("instanceFactory2"));
    }
}

class MyBean {

}

class MyBean2 {

}

class StaticFactoryMethod {
    public static MyBean getInstance() {
        return new MyBean();
    }
}

class InstanceFactoryMethod {
    public MyBean createMyBean() {
        return new MyBean();
    }

    public MyBean2 createMyBean2() {
        return new MyBean2();
    }
}