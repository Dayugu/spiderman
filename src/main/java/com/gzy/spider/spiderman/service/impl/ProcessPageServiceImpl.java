package com.gzy.spider.spiderman.service.impl;

import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import com.gzy.spider.spiderman.service.ProcessPageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessPageServiceImpl implements ProcessPageService{
    //声明代理IP池的key
    final static String KEY_ProxyIP ="proxy_ip";

    @Override
    public void processWYMusic(Page page) {
        String content = page.getContent();
        if (content != null){
            Document parse = Jsoup.parse(content);
            System.out.println(parse.toString());
            Element elementById = parse.getElementById("song-list-pre-cache");

            //System.out.println(elementById.toString());
        }

    }

    @Override
    public void processProxyIP(Page page) {
        String content = page.getContent();

        if (content != null){
            Document parse = Jsoup.parse(content);

            //Element ipTable = parse.getElementById("ip_list");
            Elements trs = parse.select("#ip_list>tbody>tr");//获取整行数据

            String regx = "\\W$";//正则表达式

            List<String> ipList = new ArrayList<>();

            for(Element tr: trs){
                Elements ip = tr.select("td:eq(1)");//ip地址
                Elements port = tr.select("td:eq(2)");//端口
                Elements speed = tr.select("td:eq(6)>div");//反应速度

                if(speed != null && speed.first() != null){
                    String speedStr = speed.first().attr("title").replaceAll(regx, "");
                    double speedDouble = speedStr!="" ? Double.parseDouble(speedStr) : 1;//如果字符串为"",则返回2

                    if(speedDouble < 1){
                        ipList.add(ip.text()+":"+port.text());
                    }

                    System.out.println("代理ip： "+ ip.text()+":"+port.text()+" speed:"+speedDouble);
                }
            }

            RedisUtil.sadd(KEY_ProxyIP,ipList);

        }

    }
}
