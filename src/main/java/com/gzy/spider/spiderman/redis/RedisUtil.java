package com.gzy.spider.spiderman.redis;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.SortingParams;

public class RedisUtil {
    private static BaseRedis redis = BaseRedis.getRedis();
    private static SimpleDateFormat agreementNumDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static String AGREEMENT_NUM_KEY = "%s_num_%s";
    private static String AGREEMENT_NUM_TEMPLATE = "%s%s%s";

    public RedisUtil() {
    }

    public static int pttl(String key) {
        Long pttl = redis.pttl(key);
        return pttl.intValue() / 1000;
    }

    public static void expire(String key, int liveTime) {
        redis.expire(key, liveTime);
    }

    public static void del(String key) {
        redis.del(key);
    }

    public static Integer getIncrementId(String key) throws Exception {
        return redis.getIncrementId(key);
    }

    public static Integer getIncrementId(String key, int liveTime) {
        return redis.getIncrementId(key, liveTime);
    }

    public static String getIncrementNumByDay(String module, String numPre, int length) {
        String dataStr = agreementNumDateFormat.format(new Date());
        String key = String.format(AGREEMENT_NUM_KEY, new Object[]{module, dataStr});
        Integer num = getIncrementId(key, 86400);
        String numStr = String.format("%0" + length + "d", new Object[]{num});
        return String.format(AGREEMENT_NUM_TEMPLATE, new Object[]{numPre, dataStr, numStr});
    }

    public static String getIncrementNumByDay(String numPre) {
        String dataStr = agreementNumDateFormat.format(new Date());
        String key = String.format(AGREEMENT_NUM_KEY, new Object[]{"all", dataStr});
        Integer num = getIncrementId(key, 86400);
        String numStr = String.format("%06d", new Object[]{num});
        return String.format(AGREEMENT_NUM_TEMPLATE, new Object[]{numPre, dataStr, numStr});
    }

    public static void set(String key, String value, int liveTime) {
        redis.set(key, value, liveTime);
    }

    public static void hSet(String key, String field, String value, int liveTime) {
        redis.hSet(key, field, value, liveTime);
    }

    public static void set(String key, String value) throws Exception {
        redis.set(key, value);
    }

    public static void hSet(String key, String field, String value) throws Exception {
        redis.hSet(key, field, value);
    }

    public static void push(String key, String value) throws Exception {
        redis.push(key, value);
    }

    public static void push(String key, byte[] value) {
        redis.push(key, value);
    }

    public static byte[] pop(byte[] key) {
        return redis.pop(key);
    }
    /**
     * 刪除鏈表开头元素，并返回该元素
     * @param key
     * @return
     */
    public static String lpop(String key) {
        return redis.lpop(key);
    }
    /**
     * 刪除鏈表结尾元素，并返回该元素
     * @param key
     * @return
     */
    public static String rpop(String key) {
        return redis.rpop(key);
    }
    /**
     * 返回链表的长度
     * @param key
     * @return
     */
    public static long llen(String key) {
        return redis.llen(key);
    }

    public static String get(String key) {
        return redis.get(key);
    }

    public static String hGet(String key, String field) {
        return redis.hGet(key, field);
    }

    public static Object getObj(String key) {
        return redis.getObj(key);
    }

    public static Object hGetObj(String key, String field) {
        return redis.hGetObj(key, field);
    }

    public static void setObj(String key, Object value, int liveTime) {
        redis.setObj(key, value, liveTime);
    }

    public static void hSetObj(String key, String field, Object value, int liveTime) {
        redis.hSetObj(key, field, value, liveTime);
    }

    public static void setObj(String key, Object value) {
        redis.setObj(key, value);
    }

    public static void hSetObj(String key, String field, Object value) {
        redis.hSetObj(key, field, value);
    }

    public static boolean exists(String key) {
        return redis.exists(key);
    }

    public static Set<String> getKeys(String pattern) {
        return redis.getKeys(pattern);
    }

    public static void delKeys(String keys) {
        redis.delKeys(keys);
    }

    public static void delKeysByGetKeys(String key) {
        redis.delKeysByGetKeys(key);
    }

    public static Map<String, String> hGetAll(String key) {
        return redis.hGetAll(key);
    }

    public void hSetString(String key, String field, String value) {
        redis.hSetString(key, field, value);
    }
    /**
     * 在链表开头添加元素
     * @param key
     * @param values 可以为数组
     * @return 链表长度
     */
    public static long lPush(String key, String... values) {
        return redis.lPush(key, values);
    }
    /**
     * 在链表开头添加元素
     * @param key
     * @param values 可以为数组
     * @return 链表长度
     */
    public static long rPush(String key, String... values) {
        return redis.rPush(key, values);
    }

    public static List<String> sortList(String key, SortingParams sortingParams) {
        return redis.sortList(key, sortingParams);
    }

    public static long sortLong(String key, SortingParams sortingParams, String dskKey) {
        return redis.sortLong(key, sortingParams, dskKey);
    }

    public static void flushAll() {
        redis.flushAll();
    }

    public static void flushDB(int db) {
        redis.flushDB(db);
    }
    /**
     * 查询指定起始和结束下标的链表元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(String key,long start,long end){
    	return redis.lrange(key, start, end);
    }
    /**
     * 添加元素到set集合中
     * @param key
     * @param vlaues
     */
    public static void sadd(String key,List<String> vlaues){
    	
    	for(String value:vlaues){
    		redis.sset(key, value);
    	}
    }
    
    
    public static List<String> srandmember(String key,Integer count){
    	return redis.srandmember(key, count);
    }
    
    /**
	 * 删除set集合中的指定元素
	 */
	public static void srem(String key, String value) {
		// TODO Auto-generated method stub
        System.out.println("删除set key:"+key+"value: "+value);
        redis.srem(key, value);
	}

    /**
     * 获取set中的元素数量
     * @param key
     * @return
     */
	public static long scard(String key){
        return redis.scard(key);
    }

}
