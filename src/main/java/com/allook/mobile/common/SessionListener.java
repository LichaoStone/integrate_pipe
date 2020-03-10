package com.allook.mobile.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.allook.utils.SessionContext;
import com.allook.wechat.bean.LoginBean;
public class SessionListener implements HttpSessionListener{
	public static final Logger logger = Logger.getLogger(SessionListener.class);
	
	 private SessionContext myc = SessionContext.getInstance();  
	 
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();  
        myc.addSession(session);  
		logger.info("【客户端App】sessionCreated");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LoginBean loginBean=(LoginBean) se.getSession().getAttribute("loginBean");
		logger.info("【客户端App】loginBean:"+loginBean);
		
		if(loginBean!=null){
			Constants.mobileLoginFlagMap.remove(loginBean.getUserName());
			logger.info("【客户端App】】session失效，移除该用户mobileLoginFlagMap数据:"+loginBean.getUserName());
		}
	
		HttpSession session = se.getSession();  
        myc.delSession(session);  
        session.invalidate();
	}
}
