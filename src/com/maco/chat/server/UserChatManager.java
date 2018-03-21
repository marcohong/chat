package com.maco.chat.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.maco.chat.bean.DwrMessage;
import com.maco.chat.bean.DwrUser;
import com.maco.chat.utils.DWRUtils;
import com.maco.chat.utils.HttpUtil;
import com.maco.chat.utils.PropertiesUtils;
import com.maco.chat.utils.RandomUtils;
import com.maco.chat.utils.StringUtils;

/**
 * 前台聊天处理业务类
 * @className UserChatManager.java
 * @author Maco
 * @version V1.0
 * @date 2015年5月14日 下午7:16:33
 */
public class UserChatManager implements IChatManager {

	private static Logger logger = Logger.getLogger(UserChatManager.class);
	private static PropertiesUtils props = new PropertiesUtils(); 
	
	@Override
	public void getOnlineUsers() {
		//过滤推送用户，特殊需求时可以过滤只推向某些用户群，用字段标识
		ScriptSessionFilter filter = new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession session) {
				logger.info(session.getId());
				return true;
			}
		};
		Iterator<Map.Entry<String, DwrUser>> it = DWRUtils.onlineUsers.entrySet().iterator();
		List<DwrUser> list = new ArrayList<DwrUser>();
		while (it.hasNext()) {
			list.add(it.next().getValue());
		}
		//执行推送
		Browser.withAllSessionsFiltered(filter, runnable(DWRUtils.BROWSER_JS_GETUSERS, list));
	}

	@Override
	public void send(final String[] receivers, final DwrMessage message) {
		//设置过滤器
		ScriptSessionFilter filter = new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession session) {
				if(StringUtils.isBlank((String)session.getAttribute(DWRUtils.TAG))) {//获取sessionID判断是否为空
					return false;
				}else {
					//如果receivers包含-1或者为空，则群聊，推送消息设置排除自己
					if (receivers == null || receivers.length == 0 || receivers[0].equals("-1")) {
						return DWRUtils.groups.contains(session.getAttribute(DWRUtils.TAG)) && 
								!message.getSenderId().equals(session.getAttribute(DWRUtils.TAG));
					}else {//非群聊消息发送					
						return session.getAttribute(DWRUtils.TAG).equals(receivers[0]);
					}
				}
			}
		};
		//执行推送，调用过滤器
		Browser.withAllSessionsFiltered(filter, runnable(DWRUtils.BROWSER_JS_CONTENT, message));
	}

	@Override
	public void loadPage() {
		HttpSession session = WebContextFactory.get().getSession();
		DwrUser user = new DwrUser();
		Integer num = RandomUtils.randomIntNum(8);
		user.setName(props.getText("dwr.user.tourists") +"_" + num);
		user.setIp(HttpUtil.getIpAddr(WebContextFactory.get().getHttpServletRequest()));
		user.setId(session.getId());
		DWRUtils.onlineUsers.put(session.getId(), user);//添加到在线用户列表中
		getScriptSession().setAttribute(DWRUtils.SCRIPT_SESSION_USER, user);//添加到scriptSession中
		getScriptSession().setAttribute(DWRUtils.TAG, user.getId());//设置用户TAG对象，即sessionId(id)
		setUserInfo(user.getId());//设置个人信息
		getOnlineUsers();//推送给客服人员
	}
	
	/**
	 * 设置用户信息
	 * @param sessionId
	 */
	protected void setUserInfo(String sessionId) {
		Browser.withSession(getScriptSession().getId(), 
				runnable(DWRUtils.BROWSER_JS_CHAT_USER, DWRUtils.onlineUsers.get(sessionId)));
	}
	
	@Override
	public void removeUser(String uid) {
		DWRUtils.groups.remove(uid);
		DWRUtils.onlineUsers.remove(WebContextFactory.get().getSession().getId());
		getOnlineUsers();
	}

	@Override
	public void closeChat(String sid, String uid) {

	}
	
	@Override
	public boolean setGroupChat(int isGroup) {
		HttpSession session = WebContextFactory.get().getSession();
		DwrUser user = DWRUtils.onlineUsers.get(session.getId());
		if(user != null) {
			user.setGroupChat(isGroup != 0);
			if (isGroup == 0) {
				DWRUtils.groups.remove(user.getId());
			}else {				
				DWRUtils.groups.add(user.getId());
			}
		}else {
			return false;
		}
		DWRUtils.onlineUsers.put(user.getId(), user);
		return true;
	}
	/**
	 * 公用脚本调用 
	 * @param js 脚本函数
	 * @param content  输出内容
	 * @return
	 */
	protected Runnable runnable(final String js, final Object content) {
		Runnable run = new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			@Override
			public void run() {
				// 设置要调用的JS及参数
				script.appendCall(js, content);
				Collection<ScriptSession> scriptSessions = Browser
						.getTargetSessions();
				for (ScriptSession session : scriptSessions) {
					session.addScript(script);
				}
			}
		};
		return run;
	}

	/**
	 * 获取ScriptSession 
	 * @return
	 */
	protected ScriptSession getScriptSession() {
		return WebContextFactory.get().getScriptSession();
	}

}
