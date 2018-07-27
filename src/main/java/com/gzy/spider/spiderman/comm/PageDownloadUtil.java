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

    @Resource
    private RedisService redisService;

    /**
     * 从redis代理IP仓库中随机获取代理IP
     */
    public static HttpClientBuilder getProxyIp(String url,String ip,boolean flag){
        HttpClientBuilder builder = HttpClients.custom();
        if(flag){
            //-----获取动态IP----
            //redisService.srandomMember(proxyIp);
            List<String> srandmember = RedisUtil.srandmember("proxy", 1);
            if(srandmember.size()>0){
                ip=srandmember.get(0);
            }

            String[] split = ip.split(":");
            String proxy_ip = split[0];
            Integer proxy_port = Integer.parseInt(split[1]);
            HttpHost host = new HttpHost(proxy_ip,proxy_port);
        }
        //builder.setProxy(host);
        //-----获取动态IP结束
        return builder;

    }

    public static Page downloadPage(Page page){

            //Document document = Jsoup.connect(page.getUrl()).header("Host", page.getHost()).userAgent(page.getUseAgent()).get();
            //设置cook信息
            HttpGet request = new HttpGet(page.getUrl());
            request.addHeader("Host",page.getHost());
            request.setHeader("User-Agent",page.getUseAgent());

            HttpClientBuilder builder = HttpClients.custom();

            CloseableHttpClient client = builder.build();

            try {
                CloseableHttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                page.setContent(EntityUtils.toString(entity));
            } catch (HttpHostConnectException e){//如果当前代理IP不能使用，则将该IP从redisIP仓库中删除
                logger.error("HttpHostConnectException : PageDownloadUtil.downloadPage(),代理IP失效 ");
            } catch (ClientProtocolException e){
                logger.error("ClientProtocolException : PageDownloadUtil.downloadPage(),代理IP失效 ");
            } catch (IOException e){
                logger.error("IOException: PageDownloadUtil.downloadPage()");
            }

            return page;

    }

}
