package com.gzy.spider.spiderman;

import com.gzy.spider.spiderman.comm.ProxyIPUtil;
import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.service.PageDownloadService;
import com.gzy.spider.spiderman.service.ProcessPageService;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 解析页面
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcesTest {

    ExecutorService executeService = Executors.newFixedThreadPool(5);
    @Autowired
    private PageDownloadService pageDownloadService;

    @Autowired
    private ProcessPageService processPageService;

    /**
     * 验证代理IP是否可用,如不可用则剔除IP代理池
     */
    @Test
    public void checkProxyIP(){

        executeService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread "+Thread.currentThread().getName());

                ProxyIPUtil.checkProxyIp();
            }
        });

    }


    /**
     * 解析西刺网的页面信息
     */
    @Test
    public void testprocessXICI(){

        Page page = pageDownloadService.updateProxyIp();

        //System.out.println("西刺网 content:"+page.getContent());

        processPageService.processProxyIP(page);

    }

    /**
     * 解析网易云音乐的歌单
     */
    @Test
    public void testWangyiyun(){
        Page page = new Page();
        page.setUrl("http://music.163.com/m/song?id=60192");
        page.setHost("music.163.com");
        page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537.36");

        pageDownloadService.downloadPage(page);

        System.out.println("-------------------------");
        System.out.println(page.getContent());
        //processPageService.processWYMusic(page);


    }



    @Test
    public void test(){
        String regx = "\\W$";
        String str = "1.2秒";

        String s = str.replaceAll(regx, "");

        System.out.println("正则表达式：s="+s);


    }


}
