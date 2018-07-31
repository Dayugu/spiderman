package com.gzy.spider.spiderman;

import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.service.PageDownloadService;
import com.gzy.spider.spiderman.service.ProcessPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 解析页面
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcesTest {

    @Autowired
    private PageDownloadService pageDownloadService;

    @Autowired
    private ProcessPageService processPageService;

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
        page.setUrl("https://music.163.com/#/playlist?id=2327225847");
        page.setHost("music.163.com");
        page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537.36");

        pageDownloadService.downloadPage(page);

        processPageService.processWYMusic(page);


    }



    @Test
    public void test(){
        String regx = "\\W$";
        String str = "1.2秒";

        String s = str.replaceAll(regx, "");

        System.out.println("正则表达式：s="+s);


    }


}
