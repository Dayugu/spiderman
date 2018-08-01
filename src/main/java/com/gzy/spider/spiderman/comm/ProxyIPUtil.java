package com.gzy.spider.spiderman.comm;

import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyIPUtil {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);
    static String KEY_PROXY_IP = "proxy_ip";//代理IP的key

    /**
     * 验证代理IP的可用性
     */
    public static void  checkProxyIp(){
        //获取代理IP池的大小


        Page page = new Page();
        page.setUrl("https://www.baidu.com/");
        page.setHost("sp0.baidu.com");
        page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537.36");

        while (true){
            long scard = RedisUtil.scard(KEY_PROXY_IP);

            if (scard <= 0 ){
                System.out.println("-----------代理池为空------------");
                break;
            }
            //多线程验证
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    System.out.println("Thread :"+Thread.currentThread().getName()+"开始验证");
                    PageDownloadUtil.downloadPage(page);
                    System.out.println("Thread :"+Thread.currentThread().getName()+"验证结束");

                }
            });
        }
    }

    public static void main(String[] args) {

        ProxyIPUtil.checkProxyIp();

    }

}
