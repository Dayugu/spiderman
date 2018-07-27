package com.gzy.spider.spiderman.redis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

public class ClusterRedis extends BaseRedis {
    private JedisCluster jedis;

    private ClusterRedis() {
    }

    public static ClusterRedis getInstance() {
        return RedisGenerator.clusterRedis;
    }

    public void generatorJedis(JedisPoolConfig config, String hosts) {
        if(!StringUtils.isNotBlank(hosts)) {
            throw new RuntimeException("Redis Hosts不能为空");
        } else {
            HashSet hps = new HashSet();
            String[] hostsArr = hosts.split(",");
            String[] var5 = hostsArr;
            int var6 = hostsArr.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String hostPort = var5[var7];
                String[] hostArr = hostPort.split(":");
                HostAndPort hp0 = new HostAndPort(hostArr[0], Integer.valueOf(hostArr[1]).intValue());
                hps.add(hp0);
            }

            this.jedis = new JedisCluster(hps, config);
        }
    }

    public Long pttl(String key) {
        Long pttl = Long.valueOf(0L);

        try {
            pttl = this.jedis.pttl(key);
            log.info("[Redis获取剩余过期时间]key:{};time:{}", key, pttl);
        } catch (Exception var4) {
            log.error("Redis获取剩余过期时间异常:{}", ExceptionUtils.getStackTrace(var4));
        }

        return pttl;
    }

    public void expire(String key, int liveTime) {
        try {
            this.jedis.expire(key, liveTime);
            log.info("[Redis设置过期时间成功]key:{};liveTime:{}", key, Integer.valueOf(liveTime));
        } catch (Exception var4) {
            log.error("Redis设置过期时间异常:{}", ExceptionUtils.getStackTrace(var4));
        }

    }

    public void del(String key) {
        try {
            this.jedis.del(key);
            log.info("[Redis删除key]:{}", key);
        } catch (Exception var3) {
            log.error("Redis删除异常:{}", ExceptionUtils.getStackTrace(var3));
        }

    }

    public Integer getIncrementId(String key) throws Exception {
        if(!this.jedis.exists(key).booleanValue()) {
            this.set(key, "0");
        }

        this.jedis.incr(key);
        Integer r = Integer.valueOf(this.get(key));
        return r;
    }

    public Integer getIncrementId(String key, int liveTime) {
        if(!this.jedis.exists(key).booleanValue()) {
            this.set(key, "0", liveTime);
        }

        this.jedis.incr(key);
        Integer r = Integer.valueOf(this.get(key));
        return r;
    }

    public void set(String key, String value, int liveTime) {
        long st = System.currentTimeMillis();

        try {
            this.jedis.set(key, value);
            this.jedis.expire(key, liveTime);
        } catch (Exception var7) {
            log.error("Redis插入异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public void hSet(String key, String field, String value, int liveTime) {
        long st = System.currentTimeMillis();

        try {
            this.jedis.hset(key, field, value);
            this.jedis.expire(key, liveTime);
        } catch (Exception var8) {
            log.error("Redis插入异常:{}", ExceptionUtils.getStackTrace(var8));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public void set(String key, String value) throws Exception {
        try {
            this.jedis.set(key, value);
        } catch (Exception var4) {
            log.error("Redis插入异常:{}", ExceptionUtils.getStackTrace(var4));
        }

    }

    public void hSet(String key, String field, String value) throws Exception {
        try {
            this.jedis.hset(key, field, value);
        } catch (Exception var5) {
            log.error("Redis插入异常:{}", ExceptionUtils.getStackTrace(var5));
        }

    }

    public void push(String key, String value) throws Exception {
        long st = System.currentTimeMillis();

        try {
            this.jedis.lpush(key, new String[]{value});
            this.jedis.expire(key, 86400000);
        } catch (Exception var6) {
            log.error("Redis Push异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        log.info("入队列时间：" + (System.currentTimeMillis() - st));
    }

    public void push(String key, byte[] value) {
        long st = System.currentTimeMillis();

        try {
            this.jedis.lpush(key.getBytes(), new byte[][]{value});
            this.jedis.expire(key, 86400000);
        } catch (Exception var6) {
            log.error("Redis Push异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        log.info("入队列时间：" + (System.currentTimeMillis() - st));
    }

    public byte[] pop(byte[] key) {
        long st = System.currentTimeMillis();
        Object v = null;

        try {
            this.jedis.rpop(key);
        } catch (Exception var6) {
            log.error("Redis pop异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        return (byte[])v;
    }

    public String lpop(String key) {
        String v = "";

        try {
            v = this.jedis.lpop(key);
        } catch (Exception var4) {
            log.error("Redis pop异常:{}", ExceptionUtils.getStackTrace(var4));
        }

        return v;
    }
    
	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		 String v = "";

	        try {
	            v = this.jedis.rpop(key);
	        } catch (Exception var4) {
	        	var4.printStackTrace();
	            log.error("Redis pop异常:{}", ExceptionUtils.getStackTrace(var4));
	        }

	        return v;
	}
	
    public long llen(String key) {
        long v = 0L;

        try {
            v = this.jedis.llen(key).longValue();
        } catch (Exception var5) {
            log.error("Redis llen异常:{}", ExceptionUtils.getStackTrace(var5));
        }

        return v;
    }

    public String get(String key) {
        long st = System.currentTimeMillis();
        String value = "";

        try {
            value = this.jedis.get(key);
        } catch (Exception var6) {
            log.error("Redis get异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        log.info("redis读取时间：" + (System.currentTimeMillis() - st));
        return value;
    }

    public String hGet(String key, String field) {
        long st = System.currentTimeMillis();
        String value = "";

        try {
            value = this.jedis.hget(key, field);
        } catch (Exception var7) {
            log.error("Redis hGet异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        log.info("redis读取时间：" + (System.currentTimeMillis() - st));
        return value;
    }

    public Object getObj(String key) {
        long st = System.currentTimeMillis();
        Object obj = null;

        try {
           byte[] bs = this.jedis.get(key.getBytes());
           obj = SerializerUtil.deserializeObj(bs);
        } catch (Exception var6) {
            log.error("Redis getObj异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        log.info("redis读取时间：" + (System.currentTimeMillis() - st));
        return obj;
    }

    public Object hGetObj(String key, String field) {
        long st = System.currentTimeMillis();
        Object obj = null;

        try {
            String e = this.jedis.hget(key, field);
            if(e != null) {
                //obj = SerializeUtil.unserialize(e);
            }
        } catch (Exception var7) {
            log.error("Redis hGetObj异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        log.info("redis读取时间：" + (System.currentTimeMillis() - st));
        return obj;
    }

    public void setObj(String key, Object value, int liveTime) {
        long st = System.currentTimeMillis();

        try {
            this.jedis.set(key.getBytes(), SerializerUtil.serializeObj(value));
            this.jedis.expire(key.getBytes(), liveTime);
        } catch (Exception var7) {
            log.error("Redis设置存活时间异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public void hSetObj(String key, String field, Object value, int liveTime) {
        long st = System.currentTimeMillis();

        try {
            //this.jedis.hset(key, field, SerializeUtil.serializeString(value));
            this.jedis.expire(key.getBytes(), liveTime);
        } catch (Exception var8) {
            log.error("Redis设置存活时间异常:{}", ExceptionUtils.getStackTrace(var8));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public void setObj(String key, Object value) {
        long st = System.currentTimeMillis();

        try {
            this.jedis.set(key.getBytes(), SerializerUtil.serializeObj(value));
        } catch (Exception var6) {
            log.error("Redis保存异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public void hSetObj(String key, String field, Object value) {
        long st = System.currentTimeMillis();

        try {
            //this.jedis.hset(key, field, SerializeUtil.serializeString(value));
        } catch (Exception var7) {
            log.error("Redis保存异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        log.info("redis写入时间：" + (System.currentTimeMillis() - st));
    }

    public boolean exists(String key) {
        boolean b = false;

        try {
            b = this.jedis.exists(key).booleanValue();
        } catch (Exception var4) {
            log.error("Redis检查key是否存在异常:{}", ExceptionUtils.getStackTrace(var4));
        }

        return b;
    }

    public Set<String> getKeys(String pattern) {
        long st = System.currentTimeMillis();
        log.debug("Start getting keys...");
        TreeSet keys = new TreeSet();
        Map clusterNodes = this.jedis.getClusterNodes();
        pattern = pattern + "*";
        Iterator var6 = clusterNodes.keySet().iterator();

        while(var6.hasNext()) {
            String k = (String)var6.next();
            log.debug("Getting keys from: {}", k);
            JedisPool jp = (JedisPool)clusterNodes.get(k);
            Jedis connection = jp.getResource();

            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception var14) {
                log.error("Getting keys error: {}", var14);
            } finally {
                log.debug("Connection closed.");
                connection.close();
            }
        }

        log.debug("Keys gotten!");
        log.info("redis读取时间：" + (System.currentTimeMillis() - st));
        return keys;
    }

    public void delKeys(String keys) {
        try {
            Set e = this.getKeys(keys);
            if(e == null || e.size() == 0) {
                return;
            }

            Iterator var3 = e.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                this.del(key);
                log.info("清空Key:{}", key);
            }
        } catch (Exception var5) {
            log.error("Redis清空key异常:{}", ExceptionUtils.getStackTrace(var5));
        }

    }

    public void delKeysByGetKeys(String key) {
        Set keys = this.getKeys(key);
        Iterator iter = keys.iterator();

        while(iter.hasNext()) {
            this.del((String)iter.next());
        }

    }

    public Map<String, String> hGetAll(String key) {
        Map result = null;

        try {
            result = this.jedis.hgetAll(key);
        } catch (Exception var4) {
            log.error("Redis hGetAll异常:{}", ExceptionUtils.getStackTrace(var4));
        }

        return result;
    }

    public void hSetString(String key, String field, String value) {
        try {
            this.jedis.hset(key.getBytes(), field.getBytes(), value.getBytes());
        } catch (Exception var5) {
            log.error("Redis hSetString异常:{}", ExceptionUtils.getStackTrace(var5));
        }

    }

    public long lPush(String key, String... values) {
        long result = 0L;

        try {
            result = this.jedis.lpush(key, values).longValue();
        } catch (Exception var6) {
            log.error("Redis lPush异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        return result;
    }

    public long rPush(String key, String... values) {
        long result = 0L;

        try {
            result = this.jedis.rpush(key, values).longValue();
        } catch (Exception var6) {
            log.error("Redis rPush异常:{}", ExceptionUtils.getStackTrace(var6));
        }

        return 0L;
    }

    public List<String> sortList(String key, SortingParams sortingParams) {
        List result = null;

        try {
            result = this.jedis.sort(key, sortingParams);
        } catch (Exception var5) {
            log.error("Redis sortList异常:{}", ExceptionUtils.getStackTrace(var5));
        }

        return result;
    }

    public long sortLong(String key, SortingParams sortingParams, String dskKey) {
        long result = 0L;

        try {
            result = this.jedis.sort(key.getBytes(), sortingParams, dskKey.getBytes()).longValue();
        } catch (Exception var7) {
            log.error("Redis sortLong异常:{}", ExceptionUtils.getStackTrace(var7));
        }

        return result;
    }

    public void flushAll() {
        try {
            this.delKeys("");
            log.info("清空所有数据");
        } catch (Exception var2) {
            log.error("Redis清空所有数据异常:{}", ExceptionUtils.getStackTrace(var2));
        }

    }

    public void flushDB(int db) {
        this.flushAll();
        log.warn("Redis集群模式无db，现已清除所有数据");
    }

    private static class RedisGenerator {
        private static ClusterRedis clusterRedis = new ClusterRedis();

        private RedisGenerator() {
        }
    }

	@Override
	public List<String> lrange(String key,long start,long end) {
		List<String> lrange = this.jedis.lrange(key, start, end);
		return lrange;
	}
	/**
	 * 向set（无序）集合中添加元素
	 */
	@Override
	public void sset(String key, String value) {
		// TODO Auto-generated method stub
		this.jedis.sadd(key, value);
	}
	/**
	 * 随机获取set集合中的元素
	 */
	@Override
	public List<String> srandmember(String key, Integer count) {
		// TODO Auto-generated method stub
		List<String> srandmember = this.jedis.srandmember(key, count);
		return srandmember;
	}
	/**
	 * 删除set集合中的指定元素
	 */
	@Override
	public void srem(String key, String value) {
		// TODO Auto-generated method stub
		this.jedis.srem(key, value);
	}


}
