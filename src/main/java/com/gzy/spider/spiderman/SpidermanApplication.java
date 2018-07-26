package com.gzy.spider.spiderman;

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

@SpringBootApplication
@RestController
public class SpidermanApplication {

	public static void main(String[] args) throws Exception{

	    SpringApplication.run(SpidermanApplication.class, args);

        String url = "https://music.163.com/#/artist?id=12270575";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        String host = "music.163.com";
        Document document = Jsoup.connect(url).header("Host", host).userAgent("userAgent").get();

        System.out.println("------------");
        System.out.println(document.toString());
        System.out.println("------------");

        Element elementById = document.getElementById("artist-top50");

        if (elementById != null){
            System.out.println("-----"+elementById.text());
        }



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



}
