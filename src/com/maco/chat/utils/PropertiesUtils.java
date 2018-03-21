package com.maco.chat.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	private static final String CHAT = "chat.properties";
	
	/**
	 * 获取配置文件的值
	 * @param key
	 * @return
	 */
	public String getText(String key) {

		Properties properties = new Properties();
		try {
			InputStream input = new BufferedInputStream(new FileInputStream(
					this.getClass().getResource("/").getPath() + CHAT));
			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

}
