package com.example.zhuxiangrobitclass.util;

import org.json.JSONObject;

public class StringUtils {

	/**
	 * 手机号校�?
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhoneNumber(String paramString) {
		String regex = "^1[3458]\\d{9}$|^(0\\d{2,4}-)?[2-9]\\d{6,7}(-\\d{2,5})?$";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 电话号校�?
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isTelPhoneNumber(String paramString) {
		String regex = "^(?!\\d+(-\\d+){3,})[48]00(-?\\d){7,10}$";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 邮箱校验
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isValidEmail(String paramString) {

		String regex = "^[[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}]";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 用户名验证：以字母开头，可输入字母�?数字和下划线�?
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isUserName(String paramString) {
		// 只允许字母和数字
		String regex = "^[a-zA-Z]+[a-zA-Z0-9_]*$";
		if (paramString.matches(regex) && paramString.length() >= 6
				&& paramString.length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 密码验证：可输入字母、数字和下划线；
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isPwd(String paramString) {
		// 只允许字母和数字
		String regex = "[a-zA-Z0-9_]*$";
		if (paramString.matches(regex) && paramString.length() >= 6
				&& paramString.length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 身份证校�?
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isIdCard(String paramString) {
		String regex = null;
		// if (paramString.length() == 15) {
		// regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		// } else if (paramString.length() == 18) {
		regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
		// }
		if (paramString.matches(regex) && paramString.length() == 18) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 带小数点的价格（price�?
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isNumPoint(String paramString) {
		String regex = "[0-9]\\d*\\.?\\d*";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 搜菜搜索框输入校�?
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isA_Z(String paramString) {
		String regex = "^[a-z,A-Z].*$";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}
	public static String StringToJson(String jsonString){
		String json="{\"json\":"+jsonString+"}";
		
		return json;
	}
}
