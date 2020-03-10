package com.allook.mobile.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.allook.frame.aspect.annotation.PaginationController;
import com.allook.frame.page.PageContext;
import com.allook.local.bean.DictionariesBean;
import com.allook.local.bean.SelectedTopicBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.MobileBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.UserBean;
import com.allook.mobile.bean.UserCheckBean;
import com.allook.mobile.service.MobileService;
import com.allook.screen.bean.ScreenBean;
import com.allook.screen.service.ScreenService;
import com.allook.socket.WebScoketHandler;
import com.allook.utils.DateUtil;
import com.allook.utils.PkCreat;
import com.allook.utils.PropertiesUtil;


/**
 * 轻快发布接口
 * @作者 栗超
 * @时间 2018年5月12日 下午3:29:24
 * @说明
 * 	
 * 	  融媒V3.1移动端在轻快发布中分离，拆分为新的App,此程序废弃。  
 * 										 --栗超 2019-07-08
 */
@Controller
@RequestMapping("/mobile")
public class MobileController extends BaseController{
	public static final Logger logger = Logger.getLogger(MobileController.class);
	
	@Autowired
	private MobileService mobileService;
	@Autowired
    private WebScoketHandler handler;
	@Autowired
	private ScreenService screenService;
	
	/**
	 * 接收实时地理坐标
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/location/geoDetail")
    private  void geoDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("longitude") String longitude,//经度
    		@RequestParam("latitude") String  latitude, //经度
    		@RequestParam("userKey") String   userKey){
		    logger.info("【推送地理位置】接收实时地理坐标:longitude="+longitude
		    		+",latitude="+latitude
		    		+",userKey="+userKey);
    	try {
    		DistributionTaskBean distributionTaskBean=new DistributionTaskBean();
    		distributionTaskBean.setLng(longitude);
    		distributionTaskBean.setLat(latitude);
    		distributionTaskBean.setUserKey(userKey);
    		distributionTaskBean.setImgDomain(PropertiesUtil.getValue("imgIP"));
			List<DistributionTaskBean> resultList=(List<DistributionTaskBean>) screenService.getUserTask(distributionTaskBean);
			
			 ScreenBean screenBeanParam=new ScreenBean();
			 screenBeanParam.setShowTypeName(MODULE.WCDD);
			 List<ScreenBean> screenBeanList;
			try {
				screenBeanList = (List<ScreenBean>) this.screenService.getScreenSetting(screenBeanParam);
				if(screenBeanList != null){
					   if(screenBeanList.size() > 0){
						   logger.info("【推送地理位置】发送地理坐标:【"+MODULE.WCDD+"】,找到配置个数："+screenBeanList.size());
						   ScreenBean info = screenBeanList.get(0);
						   String pageFlag = info.getScreenNum(); 
						   
						  
						   TextMessage listMessage = new TextMessage(handler.makeListResponseJson(
					    			CODE.SUCCESS, 
					    			MODULE.WCDD,
					    			ACTION.DETAIL, 
					    			MSG_STATUS.SUCCESS, 
					    			0,
					    			resultList
					    			)
					    		);
						   
						   //通知大屏
						   if (!this.handler.sendMessageToPage(
					        		pageFlag, 
					        		ACTION.DETAIL,
					        		listMessage
					        		)){
							   		sendJson(getJson(CODE.SUCCESS,RET.SEND_MESSAGE_ERROR),response);
							   		return;
						   }
						   
					   }
				   }
			} catch (Exception e1) {
				logger.error("【推送地理位置】刷新数据失败:",e1);
				sendJson(getJson(CODE.FAIL,RET.FAIL),response);
			}
			
			//返回消息
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		} catch (Exception e) {
			logger.error("【推送地理位置】接收实时地理坐标失败:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
    }
	
	/**
	 * 获取该用户的通话记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/call/callRecords")
	@PaginationController
    private  void callRecords(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userKey") 	  		  String userKey,
    		@RequestParam("pageNumber") 		  Integer pageNumber,
    		@RequestParam("pageSize") 	          Integer pageSize){
		
		logger.info("【获取用户通话记录】接收参数:userKey="+userKey
				+",pageNumber="+pageNumber
				+",pageSize="+pageSize);
		
		//设置分页
		PageContext pageContext=setPagination(pageNumber,pageSize);
		
		String json="";
		try {
			MobileBean bean=new MobileBean();
			bean.setUserKey(userKey);
			bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
			
			List<Map<String,Object>> resultList=(List<Map<String, Object>>) mobileService.callRecords(bean);
			logger.info("【获取用户通话记录】resultList数据:"+resultList);
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,pageContext.getTotalRows());
			//json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>());
			logger.error("【获取该用户通话记录】失败:",e);
		}finally {
			sendJson(json, response);
		}
    }
	
	
	/**
	 * 获取外采任务未读条数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/home/noRead")
    private  void homeNoRead(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userKey") String userKey){
		
		logger.info("【获取外采任务未读条数】userKey:"+userKey);
		MobileBean bean=new MobileBean();
		bean.setUserKey(userKey);
		
		String json="";
		try {
			Integer count=mobileService.homeNoRead(bean);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("noReadJobCount", count);
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,map);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【获取外采任务未读条数】:",e);
		}finally {
			sendJson(json, response);
		}
    }
	
	/**
	 * 外采任务设置为已读
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/externalTask/readJob")
    private  void externalTaskReadJob(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("distributionTaskKey") String distributionTaskKey,
    		@RequestParam("userKey") 			 String userKey){
		logger.info("【设置外采任务为已读】distributionTaskKey:"+distributionTaskKey
				+",userKey="+userKey);
		
		DistributionTaskBean bean=new DistributionTaskBean();
		bean.setDistributionTaskKey(distributionTaskKey);
		bean.setUserKey(userKey);
		
		String json="";
		try {
			mobileService.externalTaskReadJob(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【设置外采任务为已读】出错:",e);
		}finally {
			sendJson(json, response);
		}
    }
	
	
	/**
	 * 通过本地服务器获取用户详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/home/userDetailInfo")
    private  void homeUserDetailInfo(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("qkAccountKey") String qkAccountKey){
		logger.info("【获取用户详情】接收参数:qkAccountKey="+qkAccountKey);
		
		UserBean bean=new UserBean();
		bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
		bean.setQkAccountKey(qkAccountKey);
		String json="";
		try {
			UserBean resultBean=mobileService.homeUserDetailInfo(bean);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			if (resultBean!=null) {
				//组装返回数据
				resultMap.put("userKey",resultBean.getUserKey());
				resultMap.put("userName",resultBean.getUserName());
				resultMap.put("realName",resultBean.getRealName());
				resultMap.put("headImgUrl",resultBean.getHeadImgUrl());
				resultMap.put("userType",resultBean.getUserType());
				resultMap.put("qkAccountNumber",resultBean.getQkAccountNumber());
				resultMap.put("qkAccountKey",resultBean.getQkAccountKey());
				resultMap.put("status",resultBean.getStatus());
				resultMap.put("wyyxToken",resultBean.getWyyxToken());
				
				DictionariesBean diBean=new DictionariesBean();
				diBean.setDictionariesName("'punchFlag'");
				List<DictionariesBean> list=(List<DictionariesBean>) screenService.getDictValuesByNames(diBean);
				if(!isNullList(list)){
					resultMap.put("punchFlag",list.get(0).getDictionariesValue());
				}else{
					resultMap.put("punchFlag","");
				}
				
				resultMap.putAll(mobileService.getPunchSetting());
			}
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new Object());
			logger.error("【获取用户详情】出错:",e);
		}finally {
			sendJson(json, response);
		}
    }
	
	
	/**
	 * 通过本地服务器网易云信注册账号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/home/regWyxx")
    private  void homeRegWyxx(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userKey") String userKey,
    		@RequestParam("userHeadImg") String userHeadImg,
    		@RequestParam("userName") String userName){
		logger.info("【网易云账号注册】接收参数:userKey="+userKey
				+",userHeadImg="+userHeadImg
				+",userName="+userName);
		
		String json="";
		try {
			UserBean userBean=new UserBean();
			userBean.setUserKey(userKey);
			userBean.setHeadImgUrl(userHeadImg);
			userBean.setUserName(userName);
			
			mobileService.updateYxToken(userBean);
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new Object());
			logger.error("【网易云账号注册】失败:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 删除附件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/file/delete")
    private  void fileDelete(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userFileKey") String userFileKey){
		logger.info("【删除附件】接收参数:userFileKey="+userFileKey);
		FileBean fileBean=new FileBean();
		fileBean.setUserFileKey(userFileKey);
		
		String json="";
		try {
			mobileService.fileDelete(fileBean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【删除附件】失败:",e);
		}finally {
			sendJson(json, response);
		}
    }
	
	/**
	 * 修改外采任务的状态。
	 * 该接口使用各地方台配置的域名地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/externalTask/modify")
    private void externalTaskModify(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("distributionTaskKey") 										 String distributionTaskKey,
    		@RequestParam("userKey") 		 											 String userKey,
    		@RequestParam("userTaskStatus") 											 String userTaskStatus,
    		@RequestParam(value = "reason",required = false,defaultValue = "")  		 String reason){
		logger.info("【修改外采任务】接收参数:distributionTaskKey="+distributionTaskKey
				+",userKey="+userKey
				+",userTaskStatus="+userTaskStatus
				+",reason="+reason);
		
		DistributionTaskBean bean=new DistributionTaskBean();
		bean.setDistributionTaskKey(distributionTaskKey);
		bean.setUserKey(userKey);
		bean.setReason(reason);
		bean.setUserTaskStatus(userTaskStatus);
		 
		String json="";
		try {
			mobileService.externalTaskModify(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【修改外采任务】:失败",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 上传附件完成时，调用该接口将附件信息插入数据库。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/file/add")
    private  void fileAdd(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userFileName") 	String userFileName,
    		@RequestParam("userKey") 		String userKey,
    		@RequestParam("fileUrl") 		String fileUrl,
    		@RequestParam("size") 			String size,
    		@RequestParam("creator") 		String creator,
    		@RequestParam("distributionTaskKey") 		String distributionTaskKey,
    		@RequestParam("imageUrl") 		String imageUrl,
    		@RequestParam("remarks") 		String remarks,
    		@RequestParam("fileType") 		String fileType,
    		@RequestParam("duration") 		String duration
    		){
		
		if (remarks!=null&&!"".equals(remarks)) {
			try {
				remarks=URLDecoder.decode(remarks,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("【附件入库】remark解码出错:",e);
			}
		}
		
		logger.info("【附件入库】接收参数:userFileName="+userFileName
				+",userKey="+userKey
				+",fileUrl="+fileUrl
				+",size="+size
				+",creator"+creator
				+",distributionTaskKey="+distributionTaskKey
				+",imageUrl="+imageUrl
				+",remarks="+remarks
				+",fileType="+fileType
				+",duration="+duration);
		
		FileBean bean=new FileBean();
		bean.setUserFileKey(PkCreat.getTablePk());
		bean.setUserFileName(userFileName);
		bean.setUserKey(userKey);
		bean.setFileUrl(fileUrl);
		bean.setOrderNum(ORDER_NUMBER_DEFAULT);
		bean.setShowStatus(SHOW_STATUS.SHOW);
		bean.setCreator(creator);
		String time=DateUtil.getTimeToSec();
		bean.setCreateTime(time);
		bean.setUpdateTime(time);
		bean.setDistributionTaskKey(distributionTaskKey);
		bean.setImageUrl(imageUrl);
		bean.setRemarks(remarks);
		bean.setFileType(fileType);
		bean.setDuration(duration);
		bean.setSize(size);
		
		//2019-07-01 栗超 
		//bean.setUploadType("3");    //轻快发布
		//bean.setSourceType("2");    //专题
		
		
		String json="";
		try {
			mobileService.fileAdd(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【附件入库】失败:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 用来修改附件的名称
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/file/update")
    private  void fileUpdate(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userFileKey") 	String userFileKey,
    		@RequestParam("userFileName") 	String userFileName,
    		@RequestParam("remarks") 		String remarks,
    		@RequestParam("size") 			String size
    		){
		
		logger.info("【修改附件】接收参数:userFileName="+userFileName
				+",userFileKey="+userFileKey
				+",remarks="+remarks
				+",size="+size
			);
		
		FileBean bean=new FileBean();
		bean.setUserFileKey(userFileKey);
		bean.setUserFileName(userFileName);
		bean.setRemarks(remarks);
		bean.setSize(size);
		bean.setUpdateTime(DateUtil.getTimeToSec());
		
		String json="";
		try {
			mobileService.fileUpdate(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【修改附件】:失败",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 获取外采任务的详情信息。
	 * 该请求所使用的地址为 各地方台配置的域名地址。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/externalTask/detail")
    private  void externalTaskDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("distributionTaskKey") 	String distributionTaskKey,
    		@RequestParam("userKey") 				String  userKey){
		logger.info("【获取外采任务详情】接收参数:distributionTaskKey="+distributionTaskKey
				+",userKey:"+userKey);
		
		DistributionTaskBean bean=new DistributionTaskBean();
		bean.setDistributionTaskKey(distributionTaskKey);
		bean.setUserKey(userKey);
		
		String json="";
		try {
			DistributionTaskBean resultBean=mobileService.externalTaskDetail(bean);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			if (resultBean!=null) {
				resultMap.put("createTime",resultBean.getDistributionTaskTime());//需要的是任务开始时间
				resultMap.put("distributionTaskTitle",resultBean.getDistributionTaskTitle()); 
				resultMap.put("address",resultBean.getAddress()); 
				resultMap.put("distributionTaskRemarks",resultBean.getDistributionTaskRemarks()); 
				resultMap.put("userTaskStatus",resultBean.getUserTaskStatus()); 
				resultMap.put("distributionTaskKey",resultBean.getDistributionTaskKey()); 
			}
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new Object());
			logger.error("【获取外采任务详情】失败:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 获取当前外采任务的附件列表， 该附件列表为已上传完成并已入库的附件内容，
	 * 未上传完成的内容由客户端保存在本地。 该接口地址为 各地方台配置的域名地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v3/file/list",produces="text/json;charset=UTF-8")
    private  void fileList(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userKey")  			  String userKey,
    		@RequestParam("distributionTaskKey")  String distributionTaskKey,
    		@RequestParam("pageNumber") 		  Integer pageNumber,
    		@RequestParam("pageSize") 	      	  Integer pageSize){
		logger.info("【获取附件列表】接收参数:distributionTaskKey="+distributionTaskKey
				+",userKey="+userKey
				+",pageNumber="+pageNumber
				+",pageSize="+pageSize);
		
		FileBean bean=new FileBean();
		bean.setUserKey(userKey);
		bean.setDistributionTaskKey(distributionTaskKey);
		bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
		bean.setFileDomain(PropertiesUtil.getValue("imgDomainMobile"));
		
		//设置分页
		PageContext pageContext=setPagination(pageNumber,pageSize);
		
		String json="";
		try {
			List<Map<String,Object>> resultList = (List<Map<String,Object>>) mobileService.fileList(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,pageContext.getTotalRows()); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>(),0);
			logger.error("【获取附件列表】:失败",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 外采任务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/v3/externalTask/list")
    private  void externalTasLlist(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "distributionTaskTitle",required = false) 	String  distributionTaskTitle,
    		@RequestParam(value = "taskStatus",required = false) 			    String  taskStatus,
    		@RequestParam("userKey") 											String  userKey,
    		@RequestParam("pageNumber") 		  								Integer pageNumber,
    		@RequestParam("pageSize") 	      									Integer pageSize){
		
		logger.info("【获取任务列表】接收参数:distributionTaskTitle="+distributionTaskTitle
				+",taskStatus="+taskStatus
				+",userKey="+userKey
				+",pageNumber="+pageNumber
				+",pageSize="+pageSize);
		
		DistributionTaskBean bean=new DistributionTaskBean();
		bean.setDistributionTaskTitle(distributionTaskTitle);
		bean.setTaskStatus(taskStatus);
		bean.setUserKey(userKey);
		//设置分页
		PageContext pageContext=setPagination(pageNumber,pageSize);
		
		String json="";
		try {
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) mobileService.externalTasLlist(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,pageContext.getTotalRows()); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>());
			logger.error("【获取任务列表】失败:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 通过本地服务器获取当前小编最近七天考核信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/check/userCheckInfo")
    private  void checkUserCheckInfo(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("qkAccountKey") 	String qkAccountKey){
		logger.info("【获取小编考核信息】接收参数:qkAccountKey="+qkAccountKey);
		
		UserCheckBean bean=new UserCheckBean();
		bean.setQkAccountKey(qkAccountKey);
		
		String json="";
		try {
			UserCheckBean resultBean=mobileService.checkUserCheckInfo(bean);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			if (resultBean!=null) {
				resultMap.put("userKey", resultBean.getUserKey());
				resultMap.put("userName", resultBean.getUserName());
				resultMap.put("newsCount", resultBean.getNewsCount());
				resultMap.put("pvCount", resultBean.getPvCount());
				resultMap.put("uvCount", resultBean.getUvCount());
				resultMap.put("perviews", resultBean.getPerviews());
			}
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new Object());
			logger.error("【获取小编考核信息】失败:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 通过本地服务器获取最近排行榜信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/check/rankList")
    private  void checkRankList(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("type") 				  String type,
    		@RequestParam("pageNumber") 		  Integer pageNumber,
    		@RequestParam("pageSize") 	          Integer pageSize){
		
		logger.info("【排行】接收参数:type="+type
				+",pageNumber="+pageNumber
				+",pageSize="+pageSize);
		//设置分页
		PageContext pageContext=setPagination(pageNumber,pageSize);
		
		UserCheckBean bean=new UserCheckBean();
		bean.setType(type);
		bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
		String json="";
		try {
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) mobileService.checkRankList(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,pageContext.getTotalRows()); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>(),0);
			logger.error("【排行】查询数据出错:",e);
		}finally {
			sendJson(json,response);
		}
    }
	
	/**
	 * 获取部门列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/v3/common/departmentList")
	public void departmentList(HttpServletRequest request,HttpServletResponse response){
		String json="";
		try {
			
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) mobileService.getDepartmentList();
			
			Integer size=0;
			if(!isNullList(resultList)){
				size=resultList.size();
			}
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,size); 
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>(),0);
			logger.error("【部门】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	/**
	 * 我的选题列表
	 * 
	 * 返回参数:
	 * 	   
	 * {
	 *	   "code": 200
	 *	    "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功"
	 *	        "totalCount": 1,
	 *	        "list": [{
	 *	            "selectTopicId": "12dedqsdagqweewq",
	 *	            "selectTopicTitle": "选题名称",
	 *	            "createTime": "2019 03.12  09:00:12",
	 *	            "status":"1"
	 *	        }],
	 *	    },
	 *	}
	 * @param request
	 * @param response
	 * @param userKey
	 * @param pageNumber
	 * @param pageSize
	 */
	@RequestMapping("/v3/selectTopic/list")
	public void selectTopicList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("userKey") 											String userKey,
			@RequestParam("pageNumber") 		  								Integer pageNumber,
    		@RequestParam("pageSize") 	      									Integer pageSize){
		logger.info("【选题】接收参数:userKey="+userKey);
		SelectedTopicBean bean = new SelectedTopicBean();
		bean.setCreator(userKey);
		PageContext pageContext=setPagination(pageNumber,pageSize);
		String json="";
		try {
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) mobileService.getSelectTopicList(bean);
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,getTotalCount(resultList));
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>(),0);
			logger.error("【选题】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	/**
	 * 获取选题详情
	 * @param request
	 * @param response
	 * @param selectTopicId
	 */
	@RequestMapping("/v3/selectTopic/detail")
	public void selectTopicDetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("selectTopicId") String selectTopicId){
		logger.info("【选题】接收参数:selectTopicId="+selectTopicId);
		SelectedTopicBean bean = new SelectedTopicBean();
		bean.setSelectedTopicKey(selectTopicId);
		String json="";
		try {
			bean = mobileService.getSelectedTopic(bean);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			if(bean != null){
				resultMap.put("selectTopicTitle", bean.getSelectedTopicTitile());
				resultMap.put("createTime", bean.getCreateTime());
				resultMap.put("status", bean.getTopicStatus());
				resultMap.put("remarks", bean.getSelectedTopicRemarks());
				resultMap.put("departmentName", bean.getDepartmentName());
				resultMap.put("descriptions",bean.getSelectedTopicDescript());
			}
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new Object());
			logger.error("【选题】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	/**
	 * 新增报题
	 * 
	 * 返回参数:
	 * 		{
	 *		    "results": {
	 *		        "ret": 100,
	 *		        "msg": "成功"
	 *		    },
	 *		    "code": 200
	 *		}
	 *
	 * @param request
	 * @param response
	 * @param selectTopicTitle
	 * @param selectTopicDes
	 * @param departmentId
	 * @param remarks
	 * @param userKey
	 */
	@RequestMapping("/v3/selectTopic/add")
	public void selectTopicAdd(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("selectTopicTitle") String selectTopicTitle,
			@RequestParam("selectTopicDes") String selectTopicDes,
			@RequestParam("departmentId") String departmentId,
			@RequestParam(value = "remarks",required = false) String remarks,
			@RequestParam("userKey") String userKey){
		logger.info("【报题】接收参数:selectTopicTitle="+selectTopicTitle
				+",selectTopicDes="+selectTopicDes
				+",departmentId="+departmentId
				+",remarks="+remarks
				+",userKey="+userKey);
		SelectedTopicBean bean = new SelectedTopicBean();
		bean.setSelectedTopicKey(PkCreat.getTablePk());
		bean.setSelectedTopicTitile(selectTopicTitle);
		bean.setSelectedTopicDescript(selectTopicDes);
		bean.setDepartmentKey(departmentId);
		bean.setSelectedTopicRemarks(remarks);
		bean.setCreator(userKey);
		String json = "";
		try {
			mobileService.addTopic(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【报题】新建数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	/**
	 * 审核列表
	 * @param request
	 * @param response
	 * @param selectTopicId
	 */
	@RequestMapping("/v3/selectTopic/auditLogList")
	public void selectTopicAuditLogList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("selectTopicId") String selectTopicId){
		logger.info("【审核】接收参数:selectTopicId="+selectTopicId);
		SelectedTopicBean bean = new SelectedTopicBean();
		bean.setSelectedTopicKey(selectTopicId);
		String json = "";
		try {
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) mobileService.getAuditList(bean);
			
			Integer size=0;
			if(!isNullList(resultList)){
				size=resultList.size();
			}
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,size);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>());
			logger.error("【审核】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	/**
	 * 查询打卡记录列表
	 * @param request
	 * @param response
	 * @param userKey
	 * @param date
	 */
	@RequestMapping("/punch/list")
	public void punchList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("userKey") String userKey, @RequestParam("date") String date){
		logger.info("【考勤】接口参数:userKey="+userKey+",date="+date);
		PunchBean bean = new PunchBean();
		String dates[] = date.split("-");
		if(dates!=null && dates.length == 2){
			bean.setPunchYear(dates[0]);
			bean.setPunchMonth(dates[1]);
		}else if(dates!=null && dates.length == 3){
			bean.setPunchYear(dates[0]);
			bean.setPunchMonth(dates[1]);
			bean.setPunchDate(date);
		}
		
		bean.setUserKey(userKey);
		String json = "";
		try {
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) mobileService.getPunchList(bean);
			
			Integer size=0;
			if(!isNullList(resultList)){
				size=resultList.size();
			}
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultList,size);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>());
			logger.error("【考勤】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	@RequestMapping("/punch/add")
	public void punchAdd(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("userKey") String userKey, @RequestParam("time") String time,
			@RequestParam("location") String location, @RequestParam("status") String status,
			@RequestParam(value = "remarks",required = false) String remarks){
		logger.info("【打卡】接口参数:userKey="+userKey+",location="+location+",status="+status+",remarks="+remarks);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime[] = sdf.format(new Date()).split(" ");
		String punchDate = nowTime[0];
		String punchTime = nowTime[1].split(":")[0]+":"+nowTime[1].split(":")[1];
		
		PunchBean bean = new PunchBean();
		bean.setUserKey(userKey);
		bean.setPunchDate(punchDate);
		String json = "";
		try {
			Map<String, Object> map = mobileService.getPunchSetting();
			//有打卡记录，当前打卡按下班计算，更新打卡记录
			if(mobileService.checkPunch(bean) > 0){
				bean.setWorkOffTime(punchTime);
				bean.setWorkOffPunchLocation(location);
				
				if("0".equals(status)){
					bean.setWorkOffIsFar("0");
				} else {
					bean.setWorkOffIsFar("1");
					bean.setWorkOffFarReason(remarks);
				}
				
				if(DateUtil.timeLate(punchTime, (String) map.get("outTime")) < 0){
					bean.setIsEarly("0");
				} else {
					bean.setIsEarly("1");
				}
				
				mobileService.updatePunch(bean);
			} else {
				
				//没有打卡记录，按上班算，插入打卡记录
				bean.setWorkOnTime(punchTime);
				bean.setWorkOnPunchLocation(location);
				if("0".equals(status)){
					bean.setWorkOnIsFar("0");
				} else {
					bean.setWorkOnIsFar("1");
					bean.setWorkOnFarReason(remarks);
				}
				if(DateUtil.timeLate(punchTime, (String) map.get("inTime")) <= 0){
					bean.setIsLate("0");
				} else {
					bean.setIsLate("1");
				}
				
//				bean.setPunchYear(new Date().getYear() + "");
//				bean.setPunchMonth(new Date().getMonth() + "");
				
				bean.setPunchYear(DateUtil.getCurrYear());
				bean.setPunchMonth(DateUtil.getCurrMonth());
				
				
				bean.setPunchId(PkCreat.getTablePk());
				mobileService.addPunch(bean);
			}
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【打卡】新建数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	
	public static Integer getTotalCount(List<?> list){
		Integer count=0;
		if(!isNullList(list)){
			count=list.size();
		}
		
		return count;
	}
	
}
