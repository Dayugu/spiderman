package com.gzy.spider.spiderman.service.impl;

import com.gzy.spider.spiderman.comm.PageDownloadUtil;
import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import com.gzy.spider.spiderman.service.PageDownloadService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageDownloadServiceImpl implements PageDownloadService {

    //声明代理IP池的key
    final static String KEY_ProxyIP ="proxy_ip";

    @Override
    public Page downloadPage(Page page) {
        return PageDownloadUtil.downloadPage(page);
    }

    @Override
    public Page updateProxyIp() {
        Page page = new Page();
        page.setUrl("http://www.xicidaili.com/nn/");
        page.setHost("www.xicidaili.com");
        page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537.36");

        return  PageDownloadUtil.downloadPage(page);

    }




}
