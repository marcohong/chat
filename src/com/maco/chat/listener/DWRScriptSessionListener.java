package com.maco.chat.listener;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;

import com.maco.chat.utils.DWRUtils;
/**
 * 监听ScriptSession活动，页面刷新时或断开时会调用sessionDestroyed方法移除离线用户，然后重新创建
 * @author Maco
 *
 */
public class DWRScriptSessionListener implements ScriptSessionListener {

    /**
     * sessionCreated创建事件
     */
	@Override
	public void sessionCreated(ScriptSessionEvent event) {
	}
	
	/**
	 * sessionDestroyed销毁事件
	 */
	@Override
	public void sessionDestroyed(ScriptSessionEvent event) {
		WebContext context = WebContextFactory.get();
		HttpSession session = context.getSession();
		DWRUtils.onlineUsers.remove(session.getId());
	}
}
