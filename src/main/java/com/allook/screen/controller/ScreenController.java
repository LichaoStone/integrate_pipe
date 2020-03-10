package com.allook.screen.controller;

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

import com.allook.frame.BaseController;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.service.MobileService;
import com.allook.screen.bean.ScreenBean;
import com.allook.screen.service.ScreenService;
import com.allook.utils.AnalyseHtml;
import com.allook.utils.DateUtil;
import com.allook.utils.PropertiesUtil;

/**
 * 本地可视化接口
 * @作者 栗超
 * @时间 2018年5月12日 下午3:29:24
 * @说明
 */
@Controller
@RequestMapping("/screen")
public class ScreenController extends BaseController{
	public static final Logger logger = Logger.getLogger(ScreenController.class);
	
	@Autowired
	ScreenService screenService;
	
	@Autowired
	MobileService mobileService;
	
	/**
	 * 大屏获取详情数据
	 * @param request
	 * @param response
	 * @param module 模块名(例如:ZXRB 资讯热榜)
	 * @param key 对应详情信息主键key 
	 */
	@RequestMapping("/getDetails")
    private void getDetails(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("module") 			 String module,
    		@RequestParam("key") 			 	 String key){
		logger.info("【获取详情】module="+module+",key="+key);
	
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if (MODULE.DFYL.equals(module)) {//地方舆论
				resultMap=(Map<String, Object>) screenService.getDetailDFYL(key);
			}else if (MODULE.QWRD.equals(module)) {//新闻热榜
				resultMap=(Map<String, Object>) screenService.getDetailQWRD(key);
			}else if (MODULE.BDXW.equals(module)) {//本地新闻
				resultMap=(Map<String, Object>) screenService.getDetailBDXW(key);
			}else if (MODULE.ZXRB.equals(module)) {//资讯热榜
				resultMap=(Map<String, Object>) screenService.getDetailZXRB(key);
			}else if (MODULE.XWXT.equals(module)) {//新闻选题
				resultMap=(Map<String, Object>) screenService.getDetailXWXT(key);
			}else if (MODULE.CXZX.equals(module)) {//传习中心
				resultMap=(Map<String, Object>) screenService.getDetailCXZX(key);
			}else if (MODULE.BLGL.equals(module)) {//爆料管理
				/**
				 * 
				 * 返回JSON:
				 *   {
				 *	  "code":200,
				 *	  "results":{
				 *	        "ret": 100,
				 *	        "msg": "成功",
				 *	        "body":{
				 *	            "reportKey":'223123123123123',                           //报料key
				 *	            "reportTitle":'习近平：打好决胜全面建成小康社会三大攻坚战',			 //报料标题
				 *	            "createTime":'2018-08-28 17:48:29',						 //时间
				 *	            "contactWay":'18299999999',								 //联系方式
				 *	            "status": "已处理",										 //状态（已处理，未处理等）
				 *	            "content": "",											 //报料内容
				 *	            "replyContent": "回复内容"									 //回复内容
				 *	        }
				 *	    }
				 *	  }
				 */
				resultMap=(Map<String, Object>) screenService.getDetailBLGL(key);
			}else if (MODULE.LDGL.equals(module)) {//通讯联动
				/**
				 *
				 * 返回JSON:
				 * 
				 * {
				 *	    "code":200,
				 *	    "results":{
				 *	        "ret":200,
				 *	        "msg":"成功",
				 *	        "body":{
				 *	            "hotspotNewsClueDescript":"稿件内容",
				 *	            "createTime":"创建时间",
				 *	            "html_url":"页面url",
				 *	            "thumImg":"图标",
				 *	            "hotspotNewsClueTitle":"稿件名称",
				 *	            "source":"专题"
				 *	        }
				 *	    }
				 *	}
				 */
				resultMap=(Map<String, Object>) screenService.getDetailTXLD(key);
				
				
				String hotspotNewsClueDescript=(String) resultMap.get("hotspotNewsClueDescript");
				String projectUrl=PropertiesUtil.getValue("projectUrl");
				String cloudeHtmlUrl=PropertiesUtil.getValue("cloudeHtmlUrl");
				String dataFiles=cloudeHtmlUrl.split("/")[0];
				
				if(hotspotNewsClueDescript!=null&&!"".equals(hotspotNewsClueDescript)){
					List<String> contentList=AnalyseHtml.getHtmlByPath(request.getSession().getServletContext().getRealPath("/").replace("integrate_pipe",projectUrl)+"/"+dataFiles+"/"+hotspotNewsClueDescript,false,"div[class='previewDetailContent']");
					if(!isNullList(contentList)){
						resultMap.put("hotspotNewsClueDescript",contentList.get(0));
					}else{
						resultMap.put("hotspotNewsClueDescript","");
					}
				}
				
			}else if (MODULE.KQGL.equals(module)) {//考勤管理
				/**
				 *  JSON格式同websocket格式相同:
				 *  
				 *  {
				 *	    "code": 200,
				 *	    "results": {
				 *	      "ret": 100,
				 *	      "msg": "成功",
				 *	      "totalCount": 10,
				 *	      "list": [{
				 *	            "userKey":'aoduawtvvuqcspcsbdcupprxxpprqbcu'，
				 *	            "userIcon":'http://image.qingk.cn/image/z/1551776343715.png',
				 *	            "userName":'杰克马',
				 *	            "startTime": '08:25',
				 *	            "startStatus": 1,
				 *	            "endTime": '17:02',
				 *	            "endStatus": 1,
				 *	            "checkFlag":false
				 *	              },
				 *	            {
				 *	            "userKey":'aoduawtvvuqcspcsbdcupprxxpprqbcu'，
				 *	            "userIcon":'http://image.qingk.cn/image/z/1551776343715.png',
				 *	            "userName":'杰克马',
				 *	            "startTime": '08:25',
				 *	            "startStatus": 1,
				 *	            "endTime": '16:50',
				 *	            "endStatus": 0,
				 *	            "checkFlag":false
				 *	              },
				 *	            ...
				 *	        ]
				 *	    }
				 *	  }
				 */
				PunchBean bean=new PunchBean();
				bean.setPunchDate(DateUtil.getTody());
				bean.setImgDomain(PropertiesUtil.getValue("imgIP"));
				List<Map<String,Object>> resultList=(List<Map<String, Object>>) screenService.getDetailKQGL(bean);
				sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultList),response);
				return;
				
			}else{
				sendJson(getJson(CODE.SUCCESS,RET.MODULE_ERROR),response);
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultMap),response);
		} catch (Exception e) {
			logger.error("【获取详情】出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 发布次数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/publishCount")
	private void publishCount(HttpServletRequest request,HttpServletResponse response,ScreenBean screenBean) {
		String returnJson="";
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			
			resultMap=(Map<String, Object>) screenService.publishCount(screenBean);
			returnJson=getJson(CODE.SUCCESS, RET.SUCCESS,resultMap);
		} catch (Exception e) {
			logger.error("【发布次数】获取数据失败:",e);
			returnJson=getJson(CODE.FAIL,RET.FAIL);
		}finally {
			sendJson(returnJson,response);
		}
		
	}
	
	/**
	 * 公众号数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechatAccount")
	private void wechatAccount(HttpServletRequest request,HttpServletResponse response) {
		String returnJson="";
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			
			ScreenBean screenBean=new ScreenBean();
			resultMap=(Map<String, Object>) screenService.wechatAccount(screenBean);
			returnJson=getJson(CODE.SUCCESS, RET.SUCCESS,resultMap);
		} catch (Exception e) {
			logger.error("【公众号数】获取数据失败:",e);
			returnJson=getJson(CODE.FAIL,RET.FAIL);
		}finally {
			sendJson(returnJson,response);
		}
	}
	
	/**
	 * 发布稿件数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/publishManu")
	private void publishManu(HttpServletRequest request,HttpServletResponse response,ScreenBean screenBean) {
		String returnJson="";
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			
			resultMap=(Map<String, Object>) screenService.publishManu(screenBean);
			returnJson=getJson(CODE.SUCCESS, RET.SUCCESS,resultMap);
		} catch (Exception e) {
			logger.error("【发布稿件数】获取数据失败:",e);
			returnJson=getJson(CODE.FAIL,RET.FAIL);
		}finally {
			sendJson(returnJson,response);
		}
	}
	
	/**
	 * 审核稿件数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/examineManu")
	private void examineManu(HttpServletRequest request,HttpServletResponse response,ScreenBean screenBean) {
		String returnJson="";
		try {
			Map<String,Object> resultMap=new HashMap<String, Object>();
			
			resultMap=(Map<String, Object>) screenService.examineManu(screenBean);
			returnJson=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap);
		} catch (Exception e) {
			logger.error("【审核稿件数】获取数据失败:",e);
			returnJson=getJson(CODE.FAIL,RET.FAIL);
		}finally {
			sendJson(returnJson,response);
		}
	}
	
		
}
