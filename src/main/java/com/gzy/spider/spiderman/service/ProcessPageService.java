package com.gzy.spider.spiderman.service;

import com.gzy.spider.spiderman.entity.Page;

public interface ProcessPageService {

    /**
     * 解析网易云音乐
     * @param page
     */
    public void processWYMusic(Page page);

    /**
     *
     * 解析代理IP网站-西刺代理IP页面
     * @param page
     */
    public void processProxyIP(Page page);
}
