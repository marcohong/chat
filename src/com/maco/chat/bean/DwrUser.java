package com.maco.chat.bean;

public class DwrUser {

	private String id;// 可用sessionId作为唯一性
	private String name;// 昵称
	private boolean isGroupChat;//是否开启群聊，默认否
	private String ip;// IP地址

	public DwrUser() {
		super();
	}

	public DwrUser(String id, String name, String ip) {
		this.id = id;
		this.name = name;
		this.ip = ip;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isGroupChat() {
		return isGroupChat;
	}

	public void setGroupChat(boolean isGroupChat) {
		this.isGroupChat = isGroupChat;
	}

}
