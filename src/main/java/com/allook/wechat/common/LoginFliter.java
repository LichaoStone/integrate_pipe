package com.allook.wechat.common;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.allook.frame.BaseController;
import com.allook.utils.SessionContext;
import com.allook.wechat.bean.LoginBean;

/**
 * 微信小程序-登陆过滤器
 * @author lichao
 *
 */
public class LoginFliter extends BaseController implements Filter {
	public static final Logger logger = Logger.getLogger(LoginFliter.class);
	
	
	/** 需要排除（不拦截）的URL的正则表达式 */
	private Pattern excepUrlPattern;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String excepUrlPattern = config.getInitParameter("excepUrlPattern");
		if (excepUrlPattern != null && !"".equals(excepUrlPattern)) {
			this.excepUrlPattern = Pattern.compile(excepUrlPattern);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
			
			String contentType = httpRequest.getContentType();
	        if (contentType != null && contentType.contains("multipart/form-data")) {
	            MultipartResolver resolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
	            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(httpRequest);
	            // 将转化后的 request 放入过滤链中
	            httpRequest = multipartRequest;
	        }
			
	        
			String JSESSION=httpRequest.getParameter("JSESSION");
			logger.info("【登陆校验】JSESSION="+JSESSION+",请求URL:"+httpRequest.getServletPath()+",ContentType:"+httpRequest.getContentType());
			session = SessionContext.getInstance().getSession(JSESSION);
			
			
			// 如果请求的路径是排除的URL时，则直接放行
			if (excepUrlPattern.matcher(httpRequest.getServletPath()).matches()) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}else{
				//只有登陆接口（login）不走以下程序
				logger.info("loginFliter... ...");
				if (session != null && session.getAttribute("loginBean") != null) {
					//单点登陆
					LoginBean loginBean=(LoginBean) session.getAttribute("loginBean");
					String userName=loginBean.getUserName();
					//请求用户token
					String token=httpRequest.getParameter("token");
					//该用户正在登陆的token
					String currenToken=(String) Constants.loginFlagMap.get(userName);
					
					if(currenToken==null||"".equals(currenToken)){
						currenToken="AAAAAAAAAAAAAA";
					}
					
					logger.info("【小程序】token="+token+",currenToken="+currenToken+",userName="+userName);
					
					if(!currenToken.equals(token)){
						//强制下线
						logger.info("【小程序】用户重复登陆，强制下线!!!!!");
						sendJson(getJson(CODE.SUCCESS,RET.MUST_OFFLINE),httpResponse);
						return;
					}else{
						filterChain.doFilter(httpRequest, httpResponse);
						return;
					}
				}else{
					//请求失效
					logger.info("【小程序】请求失效!!!!!!!!!!!!!");
					sendJson(getJson(CODE.SUCCESS,RET.SESSION_LOSE_EFFICACY),httpResponse);
					return;
				}
			}
		} catch (Exception e) {
			logger.error("【小程序】loginFliter出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),httpResponse);
		}
		
	}

	@Override
	public void destroy() {
		logger.info("【小程序】登陆过滤器销毁... ...");
	}

}
