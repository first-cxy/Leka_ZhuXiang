package com.example.zhuxiangrobitclass.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.R.integer;

public class MD5Encoder {
	public static String encode(String pwd){
		try {
			MessageDigest digest=MessageDigest.getInstance("MD5");
			byte[] bytes=digest.digest(pwd.getBytes());
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<bytes.length;i++){
				String temp=Integer.toHexString(0xff&bytes[i]);
				if(temp.length()==1){
					sb.append("0"+temp);
				}else{
					sb.append(temp);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
