package redis;

import com.gzy.spider.spiderman.SpidermanApplication;
import com.gzy.spider.spiderman.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
