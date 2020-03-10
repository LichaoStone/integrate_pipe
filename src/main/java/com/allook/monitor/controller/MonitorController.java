package com.allook.monitor.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketSession;

import com.allook.frame.BaseController;
import com.allook.frame.Constants;
import com.allook.socket.WebScoketHandler;



@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController{
	public static final Logger logger = Logger.getLogger(MonitorController.class);
	@Autowired
    private WebScoketHandler handler;
	
	
	@RequestMapping("/login")
	public String  login(Model model,HttpServletRequest req,HttpServletResponse rep) {
		return "/monitor/login";
	}
	
	
	/**
	 * 获取在线websocketsession集合
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getClients")
    private  void getClients(HttpServletRequest request,HttpServletResponse response){
		printlnLog(request);
		try {
			    Class clazz = Class.forName("cn.qingk.socket.WebScoketHandler");
			    Field[] fields = clazz.getFields();
			    
			    Map<String, WebSocketSession> map=new ConcurrentHashMap<String, WebSocketSession>();
			    for(Field field : fields){
			    	String name=field.getName();
			    	if ("clients".equals(name)) {
			    		 map=(Map<String, WebSocketSession>) field.get(name);
				    	logger.info(map.toString());
						sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,map.toString()),response);
					}
			    }
		} catch (Exception e) {
			logger.error("【获取在线屏幕集合】出错:",e);
		}
    }
	
	/**
	 * 获取在线websocketsession总数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getConnectCount")
    private  void getConnectCount(HttpServletRequest request,HttpServletResponse response){
		printlnLog(request);
		try {
			    Class clazz = Class.forName("cn.qingk.socket.WebScoketHandler");
			    Field[] fields = clazz.getFields();
			    
			    for(Field field : fields){
			    	String name=field.getName();
			    	if ("connectCount".equals(name)){
				    	logger.info("【监控】getClients:"+field.get(name).toString());
						sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,field.get(name).toString()),response);
					}
			    }
		} catch (Exception e) {
			logger.error("【监控】getClients:",e);
		}
    }
	
	/**
	 * 获取视频通话锁状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getVideoCallLock")
    private  void getVideoCallLock(HttpServletRequest request,HttpServletResponse response){
		printlnLog(request);
		sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,Constants.VIDEO_CALL_LOCK),response);
    }
	
	/**
	 * 修改视频通话锁状态
	 * @param request
	 * @param response
	 * @param request ON/OFF
	 */
	@RequestMapping("/setVideoCallLock")
    private  void setVideoCallLock(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("status") 	String status
    		,@RequestParam("commond") 	String commond){
		printlnLog(request);
		if ("admin".equals(commond)){
			Constants.VIDEO_CALL_LOCK=status;
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,Constants.VIDEO_CALL_LOCK),response);
		}else{
			sendJson(getJson(CODE.SUCCESS,RET.FAIL,Constants.VIDEO_CALL_LOCK),response);
		}
    }
	
	/**
	 * 断开websocketSession连接
	 * @param request
	 * @param response
	 */
	@RequestMapping("/clearWebsocketSession")
    private  void clearWebsocketSession(HttpServletRequest request,HttpServletResponse response){
		printlnLog(request); 
		Map<String, WebSocketSession> clients=handler.clients;
		 if (clients!=null&&clients.size()>0){
			 for (Entry<String, WebSocketSession> entry : clients.entrySet()) {
			            WebSocketSession closeSession = entry.getValue();
			            if(closeSession.isOpen()){    
			            	try {
								closeSession.close();
							} catch (IOException e) {
								logger.error("【关闭连接】出错:",e);
								sendJson(getJson(CODE.FAIL,RET.FAIL),response);
							}  
			            }    
		     }
		 }
		 
		 sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
    }
	
	
	/**
	 * 根据屏幕连接标注关闭指定websocketSession连接
	 * @param request
	 * @param response
	 */
	@RequestMapping("/clearWebsocketSessionByKey")
    private  void clearWebsocketSessionByKey(HttpServletRequest request,HttpServletResponse response,String key){
		 printlnLog(request);
		 Map<String, WebSocketSession> clients=handler.clients;
		 WebSocketSession closeSession=clients.get(key);
		 
		 try {
			 	if(closeSession!=null){
			 		if (closeSession.isOpen()) {
				 		closeSession.close();
				 		sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
					}
			 	}else{
			 			sendJson(getJson(CODE.SUCCESS,RET.FAIL,"未找到key值对应的websocketSession信息"),response);
			 	}
			} catch (IOException e) {
				logger.error("【根据key关闭连接】出错:",e);
				sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			} 
    }
	
	
	@RequestMapping("/webCallSessionKey")
    private  void webCallSessionKey(HttpServletRequest request,HttpServletResponse response){
		 printlnLog(request);
		 String webCallSessionKey=handler.webCallSessionKey;
		 sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,webCallSessionKey),response);
    }
	
	/**
	 * 打印从request中获取的信息
	 * @param request
	 */
	private static void printlnLog(HttpServletRequest request){
			String requestURI=request.getRequestURI();
		    String ip = request.getHeader("x-forwarded-for");  
	         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             ip = request.getHeader("Proxy-Client-IP");  
	         }  
	         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             ip = request.getHeader("WL-Proxy-Client-IP");  
	         }  
	         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             ip = request.getHeader("HTTP_CLIENT_IP");  
	         }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	         }  
	         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             ip = request.getRemoteAddr();  
	         }  
	         
	         String remoteHost=request.getRemoteHost();
	         int remotePort=request.getRemotePort();
	         
	         logger.info("【URL】"+requestURI+",【IP】"+ip+",【HOST】"+remoteHost+",【PORT】"+remotePort);
	}
}
