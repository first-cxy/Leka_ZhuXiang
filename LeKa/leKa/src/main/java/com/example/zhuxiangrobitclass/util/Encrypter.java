package com.example.zhuxiangrobitclass.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.util.Base64;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
/**
 * 
 * 加密与解�?
 *
 */
public class Encrypter {
	/**
	 * 使用DES加密和解密的方法
	 * @author:azhong User: Administrator Date: 2007-10-27 Time: 10:54:36
	 * @change:exmorning
	 * */
		private final String keyStr="aps tdkj";
	    private final byte[] DESkey = keyStr.getBytes("UTF-8");// 设置密钥，略�?
	    private final byte[] DESIV = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};// 设置向量，略�?
	 
	    private AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实�?
	    private Key key = null;
	 
	    public Encrypter() throws Exception {
	        DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
	        iv = new IvParameterSpec(DESIV);// 设置向量
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
	        key = keyFactory.generateSecret(keySpec);// 得到密钥对象
	 
	    }
	 
	    public String encode(String data) throws Exception {
	        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
	        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向�?
	        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
	        //Base64 base64Encoder = new Base64();
	        //return base64Encoder.encodeBase64String(pasByte);
	        return Base64.encodeToString(pasByte, Base64.DEFAULT);
	        
	    }
	 
	    public String decode(String data) throws Exception {
	        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
	        //Base64 base64Decoder = new Base64();
	        //byte[] pasByte = deCipher.doFinal(base64Decoder.decode(data));
	        byte[] pasByte = deCipher.doFinal(Base64.decode(data,Base64.DEFAULT));
	        return new String(pasByte, "UTF-8");
	    }
	    
	    public static void main(String[] args) {
	        try {
	        	System.out.println("hello");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}

