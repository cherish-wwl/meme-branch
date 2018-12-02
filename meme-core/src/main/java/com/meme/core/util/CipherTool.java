package com.meme.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 密钥工具类
 * @author hzl
 *
 */
public class CipherTool {

	/**
	 * 默认生成8位长度的盐值
	 * @return
	 */
	public static String createSalt(){
		return createSalt(8);
	}
	
	/**
	 * 生成盐值
	 * @param length，盐值字符串长度
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String createSalt(Integer length){
		StringBuffer salt=new StringBuffer();
		try{
			while(salt.length()<length){
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] result = md.digest(UUID.randomUUID().toString().replace("-", "").getBytes());
				salt.append(MD5Util.byteArrayToHexString(result));
			}
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt.toString().substring(0,length);
	}
}
