package com.allook.mobile.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.allook.frame.BaseController;
import com.allook.frame.Constants.CODE;
import com.allook.frame.Constants.RET;
import com.allook.frame.page.PageContext;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.UserBean;
import com.allook.mobile.common.Constants;
import com.allook.mobile.service.MobileV3S1Service;
import com.allook.screen.service.ScreenService;
import com.allook.utils.PkCreat;
import com.allook.utils.PropertiesUtil;
import com.allook.wechat.bean.LoginBean;
import com.allook.wechat.service.WechatService;
/**
 * 	融媒移动端接口
 * 
 * @作者 lichao
 * @时间 2019-07-08
 * @版本 V3.1
 * @说明 
 */
@Controller
@RequestMapping("/mobile/v3s1")
public class MobileV3S1Controller extends BaseController{
	public static final Logger logger = Logger.getLogger(MobileV3S1Controller.class);
	
	@Autowired
	private MobileV3S1Service mobileV3S1Service;
	
	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private ScreenService screenService;
	
	/**
	 * 用户登陆
	 * 
	 * 返回参数:
	 * 		{
	 *		    "code": 200,
	 *		    "results": {
	 *		        "ret": 100,
	 *		        "msg": "登录成功",
	 *		        "body": {
	 *		            "customerName": "用户名：栗超",
	 *		            "customerId": 	"用户ID:9ccdfe90d00b4a57818e4a44e969d663",
	 *		            "customerImg": 	"用户头像:http://qkintegrate.qingk.cn/123.png",
	 *		            "mobile":		"手机号:13838383838",
	 *		            "email":		"邮箱:111111@qq.com"
	 *		            "roleType":		"角色类型：0",
	 * 					"token":		"f6af0c9fefd484fe72591b56da1beec4"
	 *		         }
	 *		    }
	 *		}	
	 * 	
	 * @说明
	 * 		roleType（为角色类型）:
	 * 			 0 ：编辑
	 * 			 1：值班编辑
	 * 			 2：值班编委
	 * 			 3：值班主任 
	 * 			 4 ：admin
	 * @param request
	 * @param response
	 * @param userName
	 * @param password
	 */
	@RequestMapping("/customer/login")
	@ResponseBody
    private void login(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("loginName") String loginName    	//用户名
    		,@RequestParam("password")  String password     	//密码
    		){
		try{
			
			//查询用户信息
			UserBean bean=new UserBean();
			bean.setUserName(loginName);
			bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
			bean=wechatService.getUserInfo(bean);
			
			if(bean!=null){
				if(password.equals(bean.getPassword())){
					String roleKey=bean.getRoleKey();
					logger.info("【客户端App登陆】角色roleKey（5表示通讯员）="+roleKey);
					if("5".equals(roleKey)){//通讯员角色
						String status=bean.getStatus();
						logger.info("【客户端App登陆】用户状态status(0：禁用;1：启用)="+status);
						if("1".equals(status)){//用户启用状态
							//设置session
							LoginBean loginBean=new LoginBean();       
							loginBean.setUserKey(bean.getUserKey());
							loginBean.setUserName(bean.getUserName());
							loginBean.setUserImg(bean.getHeadImgUrl());
							loginBean.setEmail(bean.getEmail());
							loginBean.setPhone(bean.getPhoneNumber());
							loginBean.setRealName(bean.getRealName());
							loginBean.setPassword(password);
							String token=PkCreat.getTablePk();
							loginBean.setToken(token);
							
							request.getSession().setAttribute("loginBean",loginBean);
							
							//记录用户为已登陆状态，用于判断单点登陆
							Constants.mobileLoginFlagMap.put(loginName,token);
							
							//登陆成功
							Map<String,Object> resultMap=new HashMap<String,Object>();
							resultMap.put("customerName", bean.getRealName());
							resultMap.put("customerId", bean.getUserKey());
							resultMap.put("customerImg", bean.getHeadImgUrl());
							resultMap.put("mobile", bean.getPhoneNumber());
							resultMap.put("email", bean.getEmail());
							resultMap.put("roleType", bean.getRoleKey());
							resultMap.put("token", token);
							
							sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultMap),response);
						}else{//用户禁用状态
							sendJson(getJson(CODE.SUCCESS,RET.USER_LOCKED),response);
						}
					}else{
						//用户角色错误（非通讯员角色）
						sendJson(getJson(CODE.SUCCESS,RET.ERROR_ROLE),response);
					}
				}else{
					//密码错误
					sendJson(getJson(CODE.SUCCESS,RET.ERROR_PASSWORD),response);
				}
			}else{
				sendJson(getJson(CODE.SUCCESS,RET.NO_USER),response);
			}
		}catch(Exception e) { 
			logger.error("【客户端App】登陆校验出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/customer/loginout")
	@ResponseBody
    private void logout(HttpServletRequest request,HttpServletResponse response
    		){
		try{
			LoginBean loginBean=(LoginBean)request.getSession().getAttribute("loginBean");
			
			//清空session
			request.getSession().removeAttribute("loginBean");
			//清空用户登陆信息
			Constants.mobileLoginFlagMap.remove(loginBean.getUserName());

			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【客户端App】登出出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 获取应用配置信息
	 * 
	 * 返回JSON:
	 * 	  {
	 *   	"results": {
	 *	        "ret": 100,
	 *	        "body": {
	 *	            "location": "12.1231231,132.123123123",
	 *	            "radius":500,
	 *	            "isRemark":false,
	 *	            "morningTimeBegin":"00:00",
	 *	            "morningTimeEnd":"12:00",
	 *	            "noonTimeBegin":"12:01",
	 *	            "noonTimeEnd":"24:00"
	 *	        },
	 *	        "msg": "成功"
	 *	    },
	 *	    "code": 200
	 *	}
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/common/getSettingInfo")
	@ResponseBody
    private void getSettingInfo(HttpServletRequest request,HttpServletResponse response
    		){
		try{
			Map<?,?> dataMap=mobileV3S1Service.getSettingInfo();
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,dataMap),response);
		}catch(Exception e) { 
			logger.error("【客户端App】获取应用配置信息出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 新增打卡记录
	 * 
	 * @说明:
	 * 		1.
	 * 
	 * @param request
	 * @param response
	 * @param userKey
	 * @param location
	 * @param status
	 * @param remarks
	 */
	@RequestMapping("/punch/add")
	public void punchAdd(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("userKey") String userKey, 			//用户key
			@RequestParam("location") String location, 			//打卡位置
			@RequestParam("status") String status,				//打卡位置是否在指定区域(正常：0; 范围外：1)
			@RequestParam("punchType") String punchType, 		//打卡类型: 0上午上班;1上午下班;2下午上班;3下午下班
			@RequestParam(value = "remarks",required = false) String remarks	//添加备注内容
			){
		
		logger.info("【打卡】接口参数:userKey="+userKey+",location="+location+",status="+status+",remarks="+remarks);
		
		String json = "";
		try {
			//组织参数
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put("userKey",userKey);
			paraMap.put("location",location);
			paraMap.put("status",status);
			paraMap.put("punchType",punchType);
			paraMap.put("remarks",remarks);
			
			mobileV3S1Service.addPunch(paraMap);
			
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL);
			logger.error("【打卡】新建数据出错:",e);
		} finally {
			sendJson(json,response);
		}
	}
	
	
	/**
	 * 外采任务列表
	 * 
	 * 返回参数:
	 * 	{
	 *	   "code": 200,
	 *	   "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功"
	 *	        "totalCount": 1,
	 *	        "list": [{
	 *	            "date": "2019-07-26",
	 *	            "morningOnTime": "08:02",
	 *	            "morningOnLocation": "济南市历下区经十路华特广场",
	 *	            "morningOnStatus": "0",
	 *	            "morningOnRemarks": "",
	 *	            "morningOffTime": "08:02",
	 *	            "morningOffLocation": "济南市历下区经十路华特广场",
	 *	            "morningOffStatus": "0",
	 *	            "morningOffRemarks": "",
	 *	            "noonOnTime": "15:02",
	 *	            "noonOnLocation": "济南历下区经十东路国奥城",
	 *	            "noonOnStatus": "2",
	 *	            "noonOnRemarks": "外出办公",
	 *	            "noonOffTime": "18:02",
	 *	            "noonOffLocation": "济南历下区经十东路国奥城",
	 *	            "noonOffStatus": "1",
	 *	            "noonOffRemarks": "外出办公",
	 *	        }],
	 *	    },
	 *	}
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/punch/list")
    private  void externalTasLlist(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("userKey") 									String  userKey,	//用户key
    		@RequestParam("date") 		  								String  date,		//年月日（ 例如：2019-07-26，2019-07）
    		@RequestParam("pageNumber") 		  						Integer pageNumber,
    		@RequestParam("pageSize") 	      							Integer pageSize
    		){
		
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
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) mobileV3S1Service.getPunchList(bean);
			
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
	
	
	/**
	 * 首页消息数量
	 * 
	 * 返回参数:
	 *  {
	 *	    "code": 200,
	 *	    "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功",
	 *	        "body": {
	 *	            "pendingCount": "10",
	 *	            "rejectiveCount": "10",
	 *	            "secondPendingCount": "10",
	 *	            "externalTaskCount":"6"
	 *	        },
	 *	    },
	 *	}
	 *
	 * @param request
	 * @param response
	 * @param userKey
	 * @param date
	 * @param pageNumber
	 * @param pageSize
	 */
	@RequestMapping("/home/message")
    private  void message(HttpServletRequest request,HttpServletResponse response
    		){
		String json = "";
		try {
			Map<String, Object> resultMap = (Map<String, Object>) mobileV3S1Service.getPunchList(bean);
			json=getJson(CODE.SUCCESS,RET.SUCCESS,resultMap);
			
		} catch (Exception e) {
			json=getJson(CODE.FAIL,RET.FAIL,new ArrayList<>());
			logger.error("【考勤】查询数据出错:",e);
		} finally {
			sendJson(json,response);
		}
    }
	
	
	
}
