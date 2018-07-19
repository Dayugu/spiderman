package redis;

import com.gzy.spider.spiderman.SpidermanApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import com.gzy.spider.spiderman.entity.User;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpidermanApplication.class)
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){

        redisTemplate.opsForValue().set("sex","man");

        System.out.println("------------"+redisTemplate.opsForValue().get("sex"));
        Assert.assertEquals("man",redisTemplate.opsForValue().get("sex"));
    }

    @Test
    public void testObject(){
        User user = new User("xsasadwa","頂頂","12361388888",18,1);

        ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user",user);

        User u = valueOperations.get("user");

        System.out.println("========user:"+u);

    }

    @Test
    public void testExpire() throws InterruptedException {
        User user = new User("sss","可乐","12361388888",18,1);

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user1",user);
        valueOperations.set("expire",user,1000, TimeUnit.MILLISECONDS);

        Thread.sleep(100);

        Boolean expire = redisTemplate.hasKey("expire");

        if (expire){
            System.out.println("exists is true");
        }else {
            System.out.println("exists is false");
        }

    }

    @Test
    public void testKeys(){
        Set keys = redisTemplate.keys("e");
        keys.stream().forEach(key ->{
            System.out.println(key);
        });

    }

    /**
     * 测试redisTemplate 删除
     * 测试结果：redisTemplate测试通过，但是直接操作reids库仍然存在
     */
    @Test
    public void testDelete(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("sex","woman");

        Boolean flag = redisTemplate.delete("sex");
        Object sex = valueOperations.get("sex");

        if (flag){
            System.out.println("delete is success . sex is "+sex);
        }else {
            System.out.println("delete is false . sex is "+sex);
        }

    }

    @Test
    public void testHash(){
        HashOperations hash = redisTemplate.opsForHash();

        //hash.put("hash","you","you");
        //hash.put("hash","hello","hello");
        //hash.put("hash","hello2","hello world");
        //String value = (String)hash.get("hash", "hello");
        //System.out.println("value:"+value);

        Set hash1 = hash.keys("hash");//返回指定哈希表key的所有域
        Iterator iterator = hash1.iterator();
        while (iterator.hasNext()){
            System.out.println("method:keys() key: hash   fileId: "+iterator.next());
        }

        List list = hash.multiGet("hash", Arrays.asList("hello", "you"));
        list.stream().forEach(val ->{
            System.out.println("method:multiGet()  key: hash filed:hello,you value:"+val);
        });

        List values = hash.values("hash");
        values.forEach(value ->{
            System.out.println("method:values() key:hash value: "+value);
        });

    }


    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();

        Long size = listOperations.leftPushAll("good",Arrays.asList("good", "good", "study"));

        System.out.println("method:leftPushAll()  size:"+size);

        List values = listOperations.range("good", 0, size);
        if (values!=null && !values.isEmpty()){
            values.forEach(val ->{
                System.out.println("method: range(), result is list, val:"+val);
            });
        }
    }

    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();

        //setOperations.add("setNum","1");
        //setOperations.add("setNum","2");
        setOperations.add("setNum","3");

        Set setNum = setOperations.members("setNum");
        Iterator iterator = setNum.iterator();
        while (iterator.hasNext()){
            System.out.println("set method:members() values:"+iterator.next());
        }

    }

    @Test
    public void testDifference(){
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("num","1");
        setOperations.add("num","5");
        setOperations.add("num","3");

        Set difference = setOperations.difference("setNum", "num");
        Iterator iterator = difference.iterator();
        while (iterator.hasNext()){
            System.out.println("set difference() values:"+iterator.next());
        }

    }

    @Test
    public void testUnion(){
        SetOperations setOperations = redisTemplate.opsForSet();

        Set union = setOperations.union("num", "setNum");
        union.stream().forEach(val ->{
            System.out.println("set method:union() values:"+val);
        });
    }

    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();


        zSetOperations.add("zset","how",1);
        zSetOperations.add("zset","?",4);
        zSetOperations.add("zset","are",2);
        zSetOperations.add("zset","you",3);

        Set zset = zSetOperations.range("zset", 0, 3);
        zset.stream().forEach(set ->{
            System.out.println("zset val:"+set);
        });
        System.out.println("------------华丽分割线-----------");
        Set zset1 = zSetOperations.rangeByScore("zset", 0, 5);
        zset1.stream().forEach(val ->{
            System.out.println("zset by score val: "+val);
        });


    }















}
