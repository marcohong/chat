package com.maco.chat.server;

import com.maco.chat.bean.DwrMessage;
/**
 * 聊天接口定义
 * @className IChatManager.java
 * @author Maco
 * @version V1.0
 * @date 2015年5月14日 下午7:25:46
 */
public abstract interface IChatManager {
	
	/**
	 * 获取在线用户
	 */
	public abstract void getOnlineUsers();
	
	/**
	 * 设置群聊
	 * @param isGroup 是否群聊0默认否 1是
	 * @return true设置成功
	 */
	public abstract boolean setGroupChat(int isGroup);
	
	/**
	 * 发送消息
	 * @param receivers 接收者（可以多个用户），空值或-1代表群发消息（加入群的人）
	 * @param message 消息内容
	 */
	public abstract void send(String[] receivers, DwrMessage message);
	
	/**
	 * 初始化页面数据
	 */
	public abstract void loadPage();
	
	/**
	 * 移除用户，针对刷新或关闭页面<br/>
	 * 如果页面刷新或者关闭则调用此方法
	 * @param uid 用户sessionId
	 */
	public abstract void removeUser(String uid);
	
	/**
	 * 关闭聊天，减少客服连接数，针对客服<br/>
	 * 客服在后台手动触发关闭聊天窗口
	 * @param sid 客服sessionId
	 * @param uid 用户sessionId
	 */
	public abstract void closeChat(String sid, String uid);
}
