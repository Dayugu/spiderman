package com.gzy.spider.spiderman;

import com.gzy.spider.spiderman.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * selenium测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest {

    static String KEY_PROXY_IP = "proxy_ip";
    static String ERROR_INFO = "代理服务器出现问题，或者地址有误";

    @Test
    public void testChrome() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", ".\\tools\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver(setProxyIP());
        //webDriver.manage().window().maximize();

        webDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        //webDriver.get("https://music.163.com/#/discover/toplist");
        webDriver.get("http://www.ceair.com/lowprice/bjs.html");

        Thread.sleep(2000);
        //System.out.println(webDriver.toString());
        System.out.println("-----------------------------------");
        //WebElement element = webDriver.findElement(By.xpath(".//*[@id='toplist']"));
        String pageSource = webDriver.getPageSource();
        //WebElement element = webDriver.findElement(By.id("content"));
        if (pageSource.contains(ERROR_INFO)){

        }
        System.out.println(pageSource);
        System.out.println("-----------------------------------");
       /* List<WebElement> elements = element.findElements(By.cssSelector("div>div>table>tbody>tr"));

        elements.forEach(ele -> {
            System.out.println("歌曲列表："+ele.toString());
        });*/


        System.out.println("当前打开页面的标题是： "+ webDriver.getTitle());

        //webDriver.quit();
    }

    public DesiredCapabilities setProxyIP(){

        List<String> list = RedisUtil.srandmember(KEY_PROXY_IP, 1);

        String ipAndPort = list.size()>0 ? list.get(0):"";
        System.out.println("ipAndPort:"+ipAndPort);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        proxy.setFtpProxy(ipAndPort).setHttpProxy(ipAndPort).setSslProxy(ipAndPort);
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);

        return desiredCapabilities;
    }



}
