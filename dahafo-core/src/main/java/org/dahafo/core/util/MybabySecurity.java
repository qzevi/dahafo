package org.dahafo.core.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class MybabySecurity {
	
	/**
	 * 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception{
		final String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKBYu9GIA/zfELJlsCne8impTKdv"+
				 "GXuYY3UiJk/SVKd5WW+sPbvGWvUklNQMH8P8d5JyfJYX8b/VK2iDwgOEW4CMQ+0PZxgn3tnbArlQ"+
				 "KfhsG5vTwjmtuNX0u5TjVVClFPLbon1DWioSHsfthYPPRRjPs79nfBzq5qZGHb5hBkqbAgMBAAEC"+
				 "gYAsEvfdSkO+WBogzy6y86MgrHZzd2hLvsFnyvE485ltRz+8IF3/pTAPPfTirEp29ohvaqSwJPjA"+
				 "P/nkioLjsJfttYyYtTq9StG8cuRzowJcZJi80aWsL1wLTawbbeR3LgE9AJ0UOTJ8lKpyMkjZJ1r5"+
				 "iWv2YuSv5koIbHPCQ2kQwQJBAPxcYUCQjzEk8tl+gmGM8/ufPzNRerNQOsi7rmr8XjZ3CvhQdqL7"+
				 "qty2rw+fwJN4sDW6l7GJkVJ5MQaFr7w16ZECQQCiqKwZJFHNYq9Cwugf5dDBBMTYkUq+mtGpWbpF"+
				 "/AwtEafaw546LkRxehODVD+MzOEMuKrlZcIYeJ4rcKAQdXtrAkEA8KOyhNuT/3vJKwYMk7LR0KV4"+
				 "oZnXk/c0Jsd4uUHvIKaBSmmHx0COHCsDYbWm77jM5l8IWV3hujAgRKTj/2e1UQJACRlKVzWy5bxJ"+
				 "dYCVn4MphEKSCFNITRU3+TMtulB5FeRQ8WnT5glEhtx/Tu9dhrDHPgDuflqM2vWUU3W/XmxT9QJB"+
				 "AMy3NcXt1oWc/0Y2QRVexP00kHYFOdXK6abGEckbYgKY6g5q/NcESdfaA9kn85JgEVMLb5QmcMoK"+
				 "HXyJ2gkdIi8=";
		return  encryptBASE64(encryptByPrivateKey(data.getBytes(), privateKey)) ;
	}
	protected static String decode(String encodedDate) throws Exception {
		final  String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgWLvRiAP83xCyZbAp3vIpqUynbxl7mGN1IiZP"+
				 "0lSneVlvrD27xlr1JJTUDB/D/HeScnyWF/G/1Stog8IDhFuAjEPtD2cYJ97Z2wK5UCn4bBub08I5"+
				 "rbjV9LuU41VQpRTy26J9Q1oqEh7H7YWDz0UYz7O/Z3wc6uamRh2+YQZKmwIDAQAB";
		return new String (decryptByPublicKey(decryptBASE64(encodedDate),publicKey));
	}
	
	 /**
     * 加密<br>
     * 用私钥加�?
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解�?
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加�?
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 解密<br>
     * 用公钥解�?
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        // 对密钥解�?
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解�?
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
	private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
	public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
