package com.glanway.jty.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密解密工具类（AES）
 * @author tianxuan
 * @Time 2016/4/21
 */
public final class AESUtil {
	
	private static final int KEY_SIZE = 128;

	private AESUtil() {
	}
	
	/**
	 * 
	* @Title: encrypt
	* @Description: 加密
	* @param content 加密内容
	* @param passkey 密钥
	* @return String
	* @throws
	 */
	public static String encrypt(String content, String passkey) {
		try {
			SecretKeySpec key = initKey(passkey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
			byte[] result = cipher.doFinal(byteContent);
			String encryptResultStr = Base64.encodeToUrlSafeString(result);
			return encryptResultStr; // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* @Title: decrypt
	* @Description: 解密
	* @param content 密文
	* @param passkey 密钥
	* @param @return
	* @return String
	* @throws
	 */
	public static String decrypt(String content, String passkey) {
		try {
			SecretKey key =initKey(passkey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key); // 初始化
			byte[] decryptFrom = Base64.decode(content,true);
			byte[] result = cipher.doFinal(decryptFrom);
			return new String(result,"utf-8"); // 解密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取key
	 * @param passkey
	 * @return
	 * @throws NoSuchAlgorithmException
     */
	public static SecretKeySpec initKey(String passkey) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(passkey.getBytes());
		kgen.init(KEY_SIZE, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		return new SecretKeySpec(enCodeFormat, "AES");
	}
}

