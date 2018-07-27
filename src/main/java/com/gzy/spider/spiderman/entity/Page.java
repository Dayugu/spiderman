package com.gzy.spider.spiderman.entity;

import java.io.Serializable;

/**
 * 页面信息实体
 */
public class Page implements Serializable{

    private String url;//页面路径

    private String host;//

    private String userAgent;//

    private String content;//页面内容

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUseAgent() {
        return userAgent;
    }

    public void setUseAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", host='" + host + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
