package com.gzy.spider.spiderman;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程测试
 */
public class ThreadTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        for(int i = 0 ;i<100;i++){
            final int index = i;

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread: "+Thread.currentThread().getName()+" i="+index);
                }
            });

        }
    }


}
