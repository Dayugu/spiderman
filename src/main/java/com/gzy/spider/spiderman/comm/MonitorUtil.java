package com.gzy.spider.spiderman.comm;

/**
 * 方法计时工具类
 */
public class MonitorUtil {

    private static ThreadLocal<Long> thread = new ThreadLocal<Long>();
    //开始计时
    public static void start(){
        thread.set(System.currentTimeMillis());
    }
    //结束计时并打印耗时时间
    public static void finish(String methodName){
        long endTime = System.currentTimeMillis();

        System.out.println("methodName:"+methodName+"耗时 "+(endTime-thread.get())+"ms");

    }

}
