package com.allook.clouds.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allook.frame.BaseController;
import com.allook.screen.bean.ScreenBean;
import com.allook.screen.service.ScreenService;
import com.allook.utils.PkCreat;
import com.allook.utils.Productor;
/**
 * 开发给云端接口
 * @作者 栗超
 * @时间 2018年5月12日 下午3:29:24
 * @说明
 */
@Controller
@RequestMapping("/clouds")
public class CloudsController extends BaseController{
	public static final Logger logger = Logger.getLogger(CloudsController.class);
	
	@Autowired
	private ScreenService screenService;
	
	@RequestMapping("/refreshScreen")
    private  void refreshScreen(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("modelType") String modelType){
	   logger.info("【云端接口】刷新大屏数据:【"+modelType+"】");
	   
	   ScreenBean screenBeanParam=new ScreenBean();
	   screenBeanParam.setShowTypeName(modelType);
	   List<ScreenBean> screenBeanList;
	   try {
		   screenBeanList = (List<ScreenBean>) this.screenService.getScreenSetting(screenBeanParam);
			if(screenBeanList != null){
				   if(screenBeanList.size() > 0){
					   logger.info("【云端接口】找到配置个数："+screenBeanList.size());
					   Iterator<ScreenBean> screenBeanIt = screenBeanList.iterator();
					   while(screenBeanIt.hasNext()){
						   ScreenBean screenBean = screenBeanIt.next();
						   String json="";
							try {
								json=screenService.refreshScreen(screenBean);
							} catch (Exception e) {
								logger.error("【云端接口】刷新数据失败:",e);
								json=getJson(CODE.FAIL,RET.FAIL);
							}finally {
								 sendJson(json,response);
							}
					   }
				   }
			   }
		} catch (Exception e1) {
			logger.error("【云端接口】刷新数据失败:",e1);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 网络热搜排行云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushHotSearch")
    private  void pushHotSearch(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody String json){
		try {
			logger.info("【云端·网络热搜】接收到得参数:json="+json+",kafka主题:"+KAFKA_TOPIC.HOT_WORD);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.HOT_WORD);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·网络热搜】同步出错:",e);
		}
	}
	
	/**
	 * 全网实时热点
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushHotNews")
    private  void pushHotNews(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody String json){
		try {
			logger.info("【云端·全网实时热点】接收到得参数:json="+json+",kafka主题:"+KAFKA_TOPIC.INFORMATION);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.INFORMATION);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·全网实时热点】同步出错:",e);
		}
		
    }
	
	
	/**
	 * 地方舆论监控云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushLocalOpinion")
    private  void pushLocalOpinion(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody String json){
		try {
			logger.info("【云端·地方舆论监控】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.COMMENT);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.COMMENT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·地方舆论监控】同步出错:",e);
		}
    }
	
	/**
	 * 地方新闻搜索云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushLocalNews")
    private  void pushLocalNews(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody String json){
		try {
			logger.info("【云端·地方新闻搜索】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.INFORMATION);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.INFORMATION);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·地方新闻搜索】同步出错:",e);
		}
    } 
    
	/**
	 * 视频连接信息云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushVideoCallInfo")
    private  void pushVideoCallInfo(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody String json){
		
		try {
			logger.info("【云端·视频连接信息】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.VIDEO_CALL);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.VIDEO_CALL);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·视频连接】同步出错:",e);
		}
    }
    
	/**
	 * 生产力统计云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushProductivityStatistics")
    private  void pushProductivityStatistics(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody  String json
    		){
		try {
			logger.info("【云端·生产力统计】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.PRODUCT);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.PRODUCT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·生产力统计】同步出错:",e);
		}
    }
	
	/**
	 * 影响力统计云端推送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushEffectStatistics")
    private  void pushEffectStatistics(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody			String json){
		try {
			logger.info("【云端·影响力统计】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.EFFECT 
					);
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.EFFECT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·影响力统计】同步出错:",e);
		}
    }
	
	/**
	 * 资讯热榜
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushInformationHot")
    private  void pushInformationHot(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody 	String json){
		try {
			logger.info("【云端·资讯热榜】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.NEWS_HOT);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.NEWS_HOT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·资讯热榜】同步出错:",e);
		}
    }
	
	/**
	 * 视频热榜
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushVideoHot")
    private  void pushVideoHot(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody  String json){
		try {
			logger.info("【云端·视频热榜】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.VIDEO_HOT);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.VIDEO_HOT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·视频热榜】同步出错:",e);
		}
    }
	
	/**
	 * 爆料管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pushReport")
    private  void pushReport(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody  String json){
		try {
			logger.info("【云端·爆料管理】接收到得参数:json="+json
					+",kafka主题:"+KAFKA_TOPIC.REPORT);
			
			Productor.send(json,PkCreat.getTablePk(),KAFKA_TOPIC.REPORT);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			logger.error("【云端·爆料管理】同步出错:",e);
		}
    }
}
