package com.maco.chat.bean;

import com.maco.chat.utils.DateUtils;

public class DwrMessage {

	private String senderId;//发送者ID
	private String sender;//发送者昵称
	private String content;//发送内容
	private Integer msgType = 0;//消息类型，0私聊、1群聊
	private String sendTime;//发送时间

	public DwrMessage() {
		super();
	}

	public DwrMessage(String senderId, String sender, String content, Integer msgType) {
		super();
		this.senderId = senderId;
		this.sender = sender;
		this.content = content;
		this.msgType = msgType;
		this.sendTime = DateUtils.getStringDate();
	}

	public DwrMessage(String content, Integer msgType) {
		super();
		this.content = content;
		this.msgType = msgType;
	}

	public DwrMessage(String sender, String content, Integer msgType) {
		super();
		this.sender = sender;
		this.content = content;
		this.msgType = msgType;
		this.sendTime = DateUtils.getStringDate();
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "Message [senderId=" + senderId + ", sender=" + sender
				+ ", content=" + content + ", msgType=" + msgType
				+ ", sendTime=" + sendTime + "]";
	}

}
