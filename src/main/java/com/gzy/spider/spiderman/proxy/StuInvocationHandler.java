package com.gzy.spider.spiderman.proxy;

import com.gzy.spider.spiderman.comm.MonitorUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler<T> implements InvocationHandler {

    T target;

    public StuInvocationHandler(T target){
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理执行"+method.getName()+"方法");

        MonitorUtil.start();//开始计时

        Object result = method.invoke(target, args);

        MonitorUtil.finish(method.getName());//结束计时

        return result;
    }
}
