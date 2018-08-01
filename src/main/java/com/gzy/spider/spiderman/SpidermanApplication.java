package com.gzy.spider.spiderman;

import com.gzy.spider.spiderman.comm.ProxyIPUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@RestController
public class SpidermanApplication {

    static ExecutorService executeService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception{

	    SpringApplication.run(SpidermanApplication.class, args);

        //检测代理IP的可用性
       /* executeService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread "+Thread.currentThread().getName());

            }
        });*/
        ProxyIPUtil.checkProxyIp();

    }

    @RequestMapping(value = "/hello/{name}")
    @Cacheable(value = "helloCache")
    public String hello(@PathVariable(value = "name") String name){
        System.out.println("不走缓存的"+System.currentTimeMillis());
        return "hello "+name;
    }

    @RequestMapping(value = "/condition/{name}")
    @Cacheable(value = "condition" ,condition = "#name.length()<=4")
    public String condition(@PathVariable(value = "name") String name){
        System.out.println(name+" 没走缓存"+System.currentTimeMillis());
        return "hello "+name;
    }


    public void checkProxyIP(){



    }


}
