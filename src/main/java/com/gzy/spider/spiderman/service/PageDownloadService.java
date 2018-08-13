package com.gzy.spider.spiderman.service;

import com.gzy.spider.spiderman.entity.Page;
import org.springframework.stereotype.Service;


public interface PageDownloadService {

    /**
     *
     * @param page
     * @return
     */
    public Page downloadPage(Page page);

    /**
     * 更新代理IP池
     */
    public Page updateProxyIp();

    /**
     * 檢查代理IP是否可用
     * @param ipAndPort
     * @return
     */
    public Page checkProxyIP(String ipAndPort);



}
