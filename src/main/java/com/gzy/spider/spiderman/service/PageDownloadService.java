package com.gzy.spider.spiderman.service;

import com.gzy.spider.spiderman.entity.Page;

public interface PageDownloadService {

    /**
     *
     * @param url
     * @return
     */
    public Page downloadPage(String url);



}
