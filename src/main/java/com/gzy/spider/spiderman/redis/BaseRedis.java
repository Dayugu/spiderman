package com.gzy.spider.spiderman.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

public abstract class BaseRedis {

	public static Logger log = LoggerFactory.getLogger(BaseRedis.class);
	private JedisPoolConfig config = new JedisPoolConfig();
	private String host;
	private static Boolean isCluster;
	private static Properties properties = new Properties();
	
    public BaseRedis() {
        String maxTotal = properties.getProperty("redis.pool.maxTotal");
        this.config.setMaxTotal(StringUtils.isNotBlank(maxTotal)?Integer.valueOf(maxTotal).intValue():20);
        String maxIdle = properties.getProperty("redis.pool.maxIdle");
        this.config.setMaxIdle(StringUtils.isNotBlank(maxIdle)?Integer.valueOf(maxIdle).intValue():20);
        String minIdle = properties.getProperty("redis.pool.minIdle");
        this.config.setMinIdle(StringUtils.isNotBlank(minIdle)?Integer.valueOf(minIdle).intValue():10);
        String maxWait = properties.getProperty("redis.pool.maxWait");
        this.config.setMaxWaitMillis(StringUtils.isNotBlank(maxWait)?(long)Integer.valueOf(maxWait).intValue():3000L);
        String testOnBorrow = properties.getProperty("redis.pool.testOnBorrow");
        this.config.setTestOnBorrow(StringUtils.isNotBlank(testOnBorrow)?Boolean.valueOf(testOnBorrow).booleanValue():true);
        this.host = properties.getProperty("redis.hosts");
        System.out.println("host:"+host);
        this.generatorJedis(this.config, this.host);
    }
    
    public abstract void generatorJedis(JedisPoolConfig var1, String var2);

    public abstract Long pttl(String var1);

    public abstract void expire(String var1, int var2);

    public abstract void del(String var1);

    public abstract Integer getIncrementId(String var1) throws Exception;

    public abstract Integer getIncrementId(String var1, int var2);

    public abstract void set(String var1, String var2, int var3);

    public abstract void hSet(String var1, String var2, String var3, int var4);

    public abstract void set(String var1, String var2) throws Exception;

    public abstract void hSet(String var1, String var2, String var3) throws Exception;

    public abstract void push(String var1, String var2) throws Exception;

    public abstract void push(String var1, byte[] var2);

    public abstract byte[] pop(byte[] var1);

    public abstract String lpop(String var1);
    
    public abstract String rpop(String var1);

    public abstract long llen(String var1);

    public abstract String get(String var1);

    public abstract String hGet(String var1, String var2);

    public abstract Object getObj(String var1);

    public abstract Object hGetObj(String var1, String var2);

    public abstract void setObj(String var1, Object var2, int var3);

    public abstract void hSetObj(String var1, String var2, Object var3, int var4);

    public abstract void setObj(String var1, Object var2);

    public abstract void hSetObj(String var1, String var2, Object var3);

    public abstract boolean exists(String var1);

    public abstract Set<String> getKeys(String var1);

    public abstract void delKeys(String var1);

    public abstract void delKeysByGetKeys(String var1);

    public abstract Map<String, String> hGetAll(String var1);

    public abstract void hSetString(String var1, String var2, String var3);

    public abstract long lPush(String var1, String... var2);

    public abstract long rPush(String var1, String... var2);

    public abstract List<String> sortList(String var1, SortingParams var2);

    public abstract long sortLong(String var1, SortingParams var2, String var3);

    public abstract void flushAll();

    public abstract void flushDB(int var1);
    
    public abstract List<String> lrange(String key,long start,long end);
    
    public abstract void sset(String key,String value);
    
    public abstract List<String> srandmember(String key, Integer count);
    
    public abstract void srem(String key,String value);
    
    public static BaseRedis getRedis(){
    	Object redis = null;
    	 if(isCluster != null && isCluster.booleanValue()) {
             redis = ClusterRedis.getInstance();
         }
    	 return (BaseRedis)redis;
    }
    
    static {
        InputStream in = BaseRedis.class.getResourceAsStream("/system/redis.properties");

        try {
            properties.load(in);
        } catch (IOException var2) {
            log.error("[Redis配置加载错误]:{}", ExceptionUtils.getStackTrace(var2));
        }

        String isClusterStr = properties.getProperty("redis.isCluster");
        isCluster = Boolean.valueOf(StringUtils.isEmpty(isClusterStr)?false:Boolean.valueOf(isClusterStr).booleanValue());
    }
}
