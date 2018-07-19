package com.gzy.spider.spiderman.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {

        Person student = new Student("张三");

        InvocationHandler invocationHandler = new StuInvocationHandler<Person>(student);

        Person stuProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, invocationHandler);

        stuProxy.giveMoney();
    }
}
