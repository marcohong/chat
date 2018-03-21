package com.maco.chat.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.maco.chat.bean.DwrUser;

public class DWRUtils {
	
	/** 存放scriptSession用户对象 */
	public static final String SCRIPT_SESSION_USER = "script_wc_user";
	
	/** 用户标签sessionId(uid) */
	public static final String TAG = "user_tag";
	
	/** 调用js方法显示聊天内容 */
	public static final String BROWSER_JS_CONTENT = "content";
	
	/** 调用js方法显示用户列表信息 */
	public static final String BROWSER_JS_GETUSERS = "getUsers";
	
	/** 调用js方法设置客服信息 */
	public static final String BROWSER_JS_CHAT_SERVIVE = "chatService";
	
	/** 调用js方法设置用户信息 */
	public static final String BROWSER_JS_CHAT_USER = "chatUser";
	
	/** 消息类型：发送的消息是内容 */
	public static final int MSG_TYPE_CONTENT = 0;
	
	/** 消息类型：提示消息，例如：访问页面时发出的请求，对方返回问候语 */
	public static final int MSG_TYPE_PROMPT = 1;
	
	/** 在线用户 key代表sessionId */
	public static Map<String, DwrUser> onlineUsers = new HashMap<String, DwrUser>();
	/** 群聊用户sessionID */
	public static Set<String> groups = new HashSet<String>();
}
