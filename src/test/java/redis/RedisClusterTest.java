package redis;

import com.gzy.spider.spiderman.SpidermanApplication;
import com.gzy.spider.spiderman.comm.PageDownloadUtil;
import com.gzy.spider.spiderman.entity.Page;
import com.gzy.spider.spiderman.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpidermanApplication.class)
public class RedisClusterTest {

    @Test
    public void test(){

        try {
            RedisUtil.set("product","xiaoxiao");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String product = RedisUtil.get("product");
        System.out.println("product:"+product);


    }

    @Test
    public void addProxyIp(){
        String key ="proxy_ip";
        List<String> ipList = new ArrayList<>();
        /*ipList.add("223.146.111.190:808");
        ipList.add("117.86.203.61:18118");
        ipList.add("106.8.17.64:60443");
        ipList.add("106.56.102.102:808");
        ipList.add("139.129.99.9:3218");
        ipList.add("101.236.21.22:8866");
        ipList.add("106.75.71.122:80");
        ipList.add("101.236.60.225:8866");
        ipList.add("118.190.95.35:9001");
        ipList.add("221.228.17.172:8181");*/


        //RedisUtil.sadd(key,ipList);

        List<String> srandmember = RedisUtil.srandmember(key, 1);
        srandmember.forEach(ip -> System.out.println(ip));
    }

    @Test
    public void downloadPage(){

        Page page = new Page();
        page.setUrl("https://music.163.com/");
        page.setHost("music.163.com");
        page.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537");

        Page newPage = PageDownloadUtil.downloadPage(page);

        System.out.println(newPage);
    }



}
