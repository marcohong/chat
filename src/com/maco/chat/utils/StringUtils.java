package com.maco.chat.utils;


public class StringUtils {
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int len;
		if (str == null || (len = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < len; i++) {
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return false;
	}
}
