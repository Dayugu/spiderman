package com.gzy.spider.spiderman.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
/**
 * @version V1.0
 * @Description:
 * @Modified By:Gu Dayu
 */
public class SerializerUtil {

    /**
     * 序列化输出为Byte[]
     * @param object
     * @return byte[]
     */
    public static byte[] serializeObj(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
    }

    /**
     * 反序列化
     * @param bytes
     * @return object
     */
	public static Object deserializeObj(byte[] bytes) {
        if (bytes.length<=0 || bytes == null){
            return null;
        }
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败!", e);
        }
    }
	/**
	 * 序列化输出为String
	 * @param object
	 * @return
	 */
	public static String serializeObjToString(Object object){
		ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toString();
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
	}
}