package com.allook.local.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;

import com.allook.frame.BaseController;
import com.allook.frame.Constants;
import com.allook.local.service.LocalService;
import com.allook.mobile.bean.UserBean;
import com.allook.screen.bean.ScreenBean;
import com.allook.screen.service.ScreenService;
import com.allook.socket.WebScoketHandler;
import com.allook.utils.PropertiesUtil;

/**
 * 本地管理系统controller层
 * @作者 栗超
 * @时间 2018年5月12日 下午3:29:24
 * @说明
 */
@Controller
@RequestMapping("/local")
public class LocalController extends BaseController{
	 public static final Logger logger = Logger.getLogger(LocalController.class);
    
	 @Autowired
	 private WebScoketHandler handler;
	 @Autowired
	 private ScreenService screenService;
	 @Autowired
	 private LocalService localService;
    
	 /**
	 * 网络热搜排行云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/refreshScreen")
    private  void refreshScreen(HttpServletRequest request,HttpServletResponse response,
    		 @RequestParam("screenNum") 	String screenNum,
    		 @RequestParam("actionFlag") 	String actionFlag){
		    logger.info("【刷新大屏】刷新大屏数据:【"+screenNum+"】,actionFlag="+actionFlag);
		   
		    String json="";
			try {
				
					ScreenBean screenBean=new ScreenBean();
				    screenBean.setScreenNum(screenNum);
				    List<ScreenBean> screenBeanList=(List<ScreenBean>) screenService.getScreenSetting(screenBean);
				    if (!isNullList(screenBeanList)) {
						 screenBean=screenBeanList.get(0);
					}
					
			    	if (ACTION.SIMPLE.equals(actionFlag)){//返回simple数据,用于后台模块切换，返回大屏高亮、显示条数等信息
				    	    Map<String, Object> infoMap = new HashMap<String, Object>();
							infoMap.put("type", screenBean.getShowTypeName());                    //模块（例:WCDD）
							infoMap.put("title", screenBean.getScreenName());                     //模块名称
							infoMap.put("highlightSecond", screenBean.getHighlightSecond());	  //高亮时间
							infoMap.put("showCount", screenBean.getShowCount());                  //显示条数
							
							//发送基本信息
							String returnJson = handler.makeInfoResponseJson(
			    						CODE.SUCCESS,
			    						screenBean.getShowTypeName(),
			    						ACTION.SIMPLE,
			    						MSG_STATUS.SUCCESS,
			    						infoMap
									);
							TextMessage returnMessage = new TextMessage(returnJson);
					        boolean flag=handler.sendMessageToPage(screenNum,ACTION.SIMPLE,returnMessage);
					        logger.info("【刷新大屏】发送simle数据标志:"+flag);
					        
					        if (flag){
					        	json=getJson(CODE.SUCCESS,RET.SUCCESS);
							}else {
								json=getJson(CODE.SUCCESS,RET.SEND_MESSAGE_ERROR);
							}
					}else if (ACTION.DETAIL.equals(actionFlag)){//刷新数据
						json=screenService.refreshScreen(screenBean);
						logger.info("【刷新大屏】发送detail数据:"+json);
					}else{
						json=getJson(CODE.SUCCESS, RET.ACTION_ERROR);
					}
				} catch (Exception e){
					logger.error("【刷新大屏】刷新数据失败:",e);
					json=getJson(CODE.FAIL,RET.FAIL);
				}finally {
					 sendJson(json,response);
				}
    }
	
	
	/**
	 * 视频连接
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/videoCall")
    private void videoCall(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("wyyxToken") 			 String wyyxToken,	
    		@RequestParam("command") 			 String command,
    		@RequestParam("initiativeId") 		 String initiativeId,
    		@RequestParam("passiveId") 			 String passiveId,
    		@RequestParam("appKey") 			 String appKey,
    		@RequestParam("netEASEKey") 	     String netEASEKey,
    		@RequestParam("pageFlageUniqueKey")  String pageFlageUniqueKey){
			
		    logger.info("【本地管理系统接口】视频链接数据:wyyxToken="+wyyxToken
		    		+",command="+command
		    		+",initiativeId="+initiativeId
		    		+",passiveId="+passiveId
		    		+",appKey="+appKey
		    		+",netEASEKey="+netEASEKey
		    		+",pageFlageUniqueKey="+pageFlageUniqueKey);
		
		    
		    String value=Constants.VIDEO_CALL_LOCK;
		    if (VIDEOCALLLOCK.OFF.equals(value)||value==null){
		    	Constants.VIDEO_CALL_LOCK=VIDEOCALLLOCK.ON;
		    	
				String json="";
				try {
					UserBean userBean=new UserBean();
					userBean.setUserKey(passiveId);
					userBean.setImgDomain(PropertiesUtil.getValue("imgIP"));
					userBean=localService.getUserInfo(userBean);
					
					List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
					Map<String,Object> paraMap=new HashMap<String,Object>();
					paraMap.put("appKey", appKey);							//应用key
					paraMap.put("command", command);						//操作:call/hangUp
					paraMap.put("passiveId", passiveId);					//接收任id
					paraMap.put("initiativeId", initiativeId);				//发起人id
					paraMap.put("wyyxToken", wyyxToken);					//发起人网易云信key
					paraMap.put("netEASEKey", netEASEKey);					//网易云唯一注册码
					paraMap.put("userName", userBean.getUserName());		//接收人账号
					paraMap.put("realName", userBean.getRealName());		//接收人姓名
					paraMap.put("headImgUrl", userBean.getHeadImgUrl());	//接收人头像
					paraMap.put("pageFlageUniqueKey", pageFlageUniqueKey);	//用于发送websocket使用，相当于屏号，标志唯一
					paraMap.put("userKey",passiveId);						 //接收任id
					resultList.add(paraMap);
				
					
					//组织返回websocket消息信息
			    	Map<String,Object> outMap = new HashMap<String,Object>();
			    	outMap.put("code", CODE.SUCCESS);
			    	outMap.put("type", TYPE.NET_CALL);
			    	outMap.put("action", ACTION.DETAIL); 
			    	outMap.put("msg", MSG_STATUS.SUCCESS);
			    	
			    	Map<String,Object> InnerMap = new HashMap<String,Object>();
			    	InnerMap.put("totalCount", resultList.size());
			    	InnerMap.put("list",resultList);
			    	outMap.put("body", InnerMap);
			    	
			    	TextMessage listMessage = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
			    	
			    	//获取大屏数
			    	 ScreenBean screenBeanParam=new ScreenBean();
					 screenBeanParam.setShowTypeName(MODULE.WCDD);
					 List<ScreenBean> screenBeanList;
					 try {
						screenBeanList = (List<ScreenBean>) this.screenService.getScreenSetting(screenBeanParam);
						if(screenBeanList != null){
							   if(screenBeanList.size() > 0){
								   logger.info("【视频通话】视频连接获取配置信息:【"+MODULE.WCDD+"】,找到配置个数："+screenBeanList.size());
								   ScreenBean info = screenBeanList.get(0);
								   //向所有打开pageFlage（P1,P2）的浏览器发送消息
							        boolean sendFlag2 = this.handler.sendMessageToPage(
							        		info.getScreenNum(), 
							        		ACTION.DETAIL,
							        		listMessage
							        		);
							        logger.info("【视频通话】视频连接sendFlag2:"+sendFlag2);
							        json=getJson(CODE.SUCCESS,RET.SUCCESS);
							   }
						   }
					 } catch (Exception e1) {
						logger.error("【视频通话】视频连接查找配置失败:",e1);
					 }
			} catch (Exception e) {
				logger.error("【视频通话】视频连接失败:",e);
				json=getJson(CODE.FAIL, RET.FAIL);
			}finally {
				sendJson(json,response);
				Constants.VIDEO_CALL_LOCK=VIDEOCALLLOCK.OFF;
			}
		}else {
			sendJson(getJson(CODE.SUCCESS,RET.VIDEO_CALL_LOCK),response);
		}
    }
}
