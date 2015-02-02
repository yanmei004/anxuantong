package com.hengtong.library.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class DesUtil {
	 private final static String DES = "DES";
	 /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
//        byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
    	 byte[] bt = encrypt(data, key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(String data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data.getBytes("utf-8"));
    }
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
	
	/**
	 * desd的加密方式 注销
	 */
//	//加密	
//	public byte[] desCrypto(byte[] datasource, String password) {              
//	    try{  
//	    SecureRandom random = new SecureRandom();  
//	    DESKeySpec desKey = new DESKeySpec(password.getBytes());  
//	    //创建一个密匙工厂，然后用它把DESKeySpec转换成  
//	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
//	    SecretKey securekey = keyFactory.generateSecret(desKey);  
//	    //Cipher对象实际完成加密操作  
//	    Cipher cipher = Cipher.getInstance("DES");  
//	    //用密匙初始化Cipher对象  
//	    cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
//	    //现在，获取数据并加密  
//	    //正式执行加密操作  
//	    return cipher.doFinal(datasource);  
//	    }catch(Throwable e){  
//	            e.printStackTrace();  
//	    }  
//	    return null;  
//	} 
//	
//	//解密
//	private byte[] decrypt(byte[] src, String password) throws Exception {  
//	    // DES算法要求有一个可信任的随机数源  
//	    SecureRandom random = new SecureRandom();  
//	    // 创建一个DESKeySpec对象  
//	    DESKeySpec desKey = new DESKeySpec(password.getBytes());  
//	    // 创建一个密匙工厂  
//	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
//	    // 将DESKeySpec对象转换成SecretKey对象  
//	    SecretKey securekey = keyFactory.generateSecret(desKey);  
//	    // Cipher对象实际完成解密操作  
//	    Cipher cipher = Cipher.getInstance("DES");  
//	    // 用密匙初始化Cipher对象  
//	    cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
//	    // 真正开始解密操作  
//	    return cipher.doFinal(src);  
//	}  
    }
