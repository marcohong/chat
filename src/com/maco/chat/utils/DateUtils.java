package com.maco.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 获取现在时间
	 * @return 返回字符串格式 yyyy/MM/dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return formatter.format(currentTime);
	}
}
