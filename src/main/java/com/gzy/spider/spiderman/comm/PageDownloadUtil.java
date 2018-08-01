package com.gzy.spider.spiderman.comm;


import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import com.gzy.spider.spiderman.service.RedisService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public class PageDownloadUtil {

    private static Logger logger = LoggerFactory.getLogger(PageDownloadUtil.class);

    private final static String proxyIp = "proxy_ip";//代理IP在redis缓存中的Key

    /**
     * 从redis代理IP仓库中随机获取代理IP
     */
    public static HttpClientBuilder getProxyIp(Page page,boolean flag){
        HttpClientBuilder builder = HttpClients.custom();
        if(flag){
            //-----获取动态IP----
            List<String> srandmember = RedisUtil.srandmember(proxyIp, 1);
            String ip="";
            if(srandmember.size()>0){
                ip=srandmember.get(0);
            }
            page.setProxyIP(ip);
            String[] split = ip.split(":");
            String proxy_ip = split[0];
            Integer proxy_port = Integer.parseInt(split[1]);
            HttpHost host = new HttpHost(proxy_ip,proxy_port);

            builder.setProxy(host);

            System.out.println("proxyId: "+proxy_ip+":"+proxy_port);
        }

        //-----获取动态IP结束
        return builder;

    }

    public static Page downloadPage(Page page){

            //设置cook信息
            HttpGet request = new HttpGet(page.getUrl());
            request.addHeader("Host",page.getHost());
            request.setHeader("User-Agent",page.getUserAgent());
            //获取动态代理IP
            String ip = "";
            HttpClientBuilder builder = getProxyIp(page,true);

            CloseableHttpClient client = builder.build();

            try {
                CloseableHttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                if (content.contains("很抱歉，你要查找的网页找不到")){
                    System.out.println("当前线程："+Thread.currentThread().getName()+" 代理IP："+page.getProxyIP()+"失效");
                    RedisUtil.srem(proxyIp,page.getProxyIP());
                    logger.error(" Exception 网页找不到 : PageDownloadUtil.downloadPage(),代理IP失效 ");
                }else {
                    page.setContent(content);

                    System.out.println("请求成功,代理IP："+page.getProxyIP());

                }

            } catch (HttpHostConnectException e){//如果当前代理IP不能使用，则将该IP从redisIP仓库中删除
                //删除不能使用的代理IP
                RedisUtil.srem(proxyIp,page.getProxyIP());
                logger.error("HttpHostConnectException : PageDownloadUtil.downloadPage(),代理IP失效 ");

            } catch (ClientProtocolException e){
                //删除不能使用的代理IP
                RedisUtil.srem(proxyIp,page.getProxyIP());
                logger.error("ClientProtocolException : PageDownloadUtil.downloadPage(),代理IP失效 ");

            } catch (IOException e){

                logger.error("IOException: PageDownloadUtil.downloadPage()");

            }

            return page;

    }

}
