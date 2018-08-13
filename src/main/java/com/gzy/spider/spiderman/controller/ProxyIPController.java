package com.gzy.spider.spiderman.controller;

import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import com.gzy.spider.spiderman.service.PageDownloadService;
import com.gzy.spider.spiderman.service.ProcessPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 代理IP控制类
 */
@Controller
@PropertySource("classpath:system/keys.properties")
public class ProxyIPController {


    @Value("${KEY_ProxyIP}")
    private String KEY_ProxyIP;
    //代理IP队列
    @Value("${KEY_PROXY_LIST}")
    private String KEY_PROXY_LIST;

    @Autowired
    private PageDownloadService pageDownloadService;

    @Autowired
    private ProcessPageService processPageService;

    @RequestMapping("/modifyProxyIP")
    public void modifyProxyIP(){

        //下载页面
        Page page = pageDownloadService.updateProxyIp();
        //解析页面
        processPageService.processProxyIP(page);

        //验证ip
        String ipAndPort = RedisUtil.lpop(KEY_PROXY_LIST);//从redis队列中依次获取ip

        pageDownloadService.checkProxyIP(ipAndPort);

    }

    @RequestMapping("/indexs")
    public String index(Model model){

        model.addAttribute("why",12);
        return "index";
    }


}
