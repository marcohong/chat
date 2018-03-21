package com.maco.chat.utils;

import java.util.Random;
/**
 * 随机产生函数。
 * <p>可以随机产生数字（int类型、string类型）、字母、数字加字母组合的随机数。</p>
 * @className RandomUtils.java
 * @author Maco
 * @version V1.0
 * @date 2015年5月14日 下午7:28:17
 */
public class RandomUtils {

	/**
	 * 随机产生自定义长度的数字，返回类型(int)
	 * @param length 自定义长度,最长9位
	 * @return 正整数
	 */
	public static Integer randomIntNum(int length){
		
		String num = "";
		
		if(length >= 0 && length <= 9){
			
			for (int i = 0; i < length; i++) {
				num += (int)(Math.random()*10);
			}
		}
		
		return Integer.valueOf(num);
	}
	
	/**
	 * 随机参数自定义长度的数字,返回类型(string)
	 * @param length 自定义长度
	 * @return 字符串
	 */
	public static String randomStrNum(int length){
		
		String num = "";
		for (int i = 0; i < length; i++) {
			num += (int)(Math.random()*10);
		}
		return num;
	}
	
	/**
	 * 随机参数自定义长度的字母，返回类型(string)
	 * @param length 自定义产度
	 * @return 随机字母
	 */
	public static String randomStrGen(int length){
		
		/*String str = "";
		for(int i=0;i<length;i++){ 
			str= str+(char) (Math.random ()*26+'a'); 
		} */
		StringBuffer buffer=new StringBuffer();
		//产生随机数函数
		Random random=new Random();
		//通过循环输出字符
		for(int i=0;i<length;i++){
			//产生随机数
			int number=random.nextInt(26);
			//随即抽取字符
			buffer.append((char) (97+number));
		}
		
		return buffer.toString();
	}
	
	/**
	 * 随机产生自定义长度的数字加字母组合的字符串，返回类型(string)
	 * @param length 产生字符串的长度
	 * @return 随机字母加数字组合
	 */
	public static String randStrNumGen(int length){
		
		Random r = new Random();
		
		char[] arrayStr = ("0123456789abcdefghijklmnopqrstuvwxyz" +
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		
		char[] randomBuffer = new char[length];
		
		for (int i = 0; i < randomBuffer.length; i++) {
			
			randomBuffer[i] = arrayStr[r.nextInt(71)];
		}
		
		return new String(randomBuffer);
	}
}
