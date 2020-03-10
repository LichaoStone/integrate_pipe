package com.allook.wechat.controller;

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
import com.allook.frame.aspect.annotation.PaginationController;
import com.allook.frame.page.PageContext;
import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.UserBean;
import com.allook.utils.AnalyseHtml;
import com.allook.utils.DateUtil;
import com.allook.utils.FileUtil;
import com.allook.utils.PkCreat;
import com.allook.utils.PropertiesUtil;
import com.allook.utils.SessionContext;
import com.allook.wechat.bean.LoginBean;
import com.allook.wechat.bean.ManuscriptBean;
import com.allook.wechat.bean.StaticHtmlBean;
import com.allook.wechat.bean.SubjectBean;
import com.allook.wechat.common.Constants;
import com.allook.wechat.service.WechatService;

/**
 * 通讯联动-微信小程序系统
 * @author lichao
 */
@Controller
@RequestMapping("/report")
public class WechatController extends BaseController{
	public static final Logger logger = Logger.getLogger(WechatController.class);
	
	@Autowired
	private WechatService wechatService;
	
	/**
	 * 登陆检验
	 * 
	 * 返回JSON:
	 * {
	 * 		"code": 200
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功"
	 *			"body": {
	 *				"token"："1e588d8842df4ea9b3d1ba9786f61196"
	 *			}
	 *		},
	 *	}
	 * 
	 * 备注:
	 *    token有效期为1天
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ucenter/login")
	@ResponseBody
    private void login(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("name") String userName    //用户名
    		,@RequestParam("pwd")  String password    //密码
    		){
		try{
			//查询用户信息
			UserBean bean=new UserBean();
			bean.setUserName(userName);
			bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
			bean=wechatService.getUserInfo(bean);
			
			if(bean!=null){
				if(password.equals(bean.getPassword())){
					String roleKey=bean.getRoleKey();
					logger.info("【登陆】角色roleKey（5表示通讯员）="+roleKey);
					if("5".equals(roleKey)){//通讯员角色
						String status=bean.getStatus();
						logger.info("【登陆】用户状态status(0：禁用;1：启用)="+status);
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
							Constants.loginFlagMap.put(userName,token);
							
							//登陆成功
							Map<String,Object> resultMap=new HashMap<String,Object>();
							resultMap.put("token", token);
							resultMap.put("JSESSION",request.getSession().getId());
							
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
			logger.error("【小程序】登陆校验出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ucenter/loginout")
	@ResponseBody
    private void logout(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam(value = "JSESSION")     String  JSESSION
    		){
		try{
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			//LoginBean loginBean=(LoginBean) request.getSession().getAttribute("loginBean");
			//清空session
			SessionContext.getInstance().getSession(JSESSION).removeAttribute("loginBean");
			//清空用户登陆信息
			Constants.loginFlagMap.remove(loginBean.getUserName());
			//使session失效
			SessionContext.getInstance().getSession(JSESSION).invalidate();

			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【小程序】登出出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 获取个人信息
	 * 
	 * 
	 * 返回JSON：
	 * 
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"body": {
	 *				"userImg":  "http://image.qingk.cn/image/z/1551776089392.png",
	 *				"realName": "顾晓梦",
	 *				"userName": "时光煮雨",
	 *				"phone":    "13628196663",
	 *				"email":    "guxiaomeng@163.com"
	 *			}
	 *		}
	 *	}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ucenter/info")
	@ResponseBody
    private void info(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam(value = "JSESSION")     String  JSESSION
    		){
		try{
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			//LoginBean loginBean=(LoginBean) request.getSession().getAttribute("loginBean");
			
			UserBean bean=new UserBean();
			bean.setUserName(loginBean.getUserName());
			bean.setUserHeadIconLocalIp(PropertiesUtil.getValue("userHeadIconLocalIp"));
			bean.setUserHeadIconQingkIp(PropertiesUtil.getValue("userHeadIconQingkIp"));
			
			bean=wechatService.getUserInfo(bean);
			
			//组织返回参数
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("userImg",bean.getHeadImgUrl());
			resultMap.put("realName",bean.getRealName());
			resultMap.put("userName",bean.getUserName());
			resultMap.put("phone",bean.getPhoneNumber());
			resultMap.put("email",bean.getEmail());
			
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,resultMap),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取个人信息出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 编辑用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ucenter/modify")
	@ResponseBody
    private void modify(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam(value = "userImg",required = false,defaultValue = "")     String  userImg     //用户头像
    		,@RequestParam(value = "realName",required = false,defaultValue = "")    String  realName    //用户名
    		,@RequestParam(value = "phone",required = false,defaultValue = "")       String  phone       //手机号
    		,@RequestParam(value = "email",required = false,defaultValue = "")       String  email       //邮箱
    		,@RequestParam(value = "JSESSION")     String  JSESSION
    		){
		try{
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			//LoginBean loginBean=(LoginBean) request.getSession().getAttribute("loginBean");
			
			UserBean bean=new UserBean();
			bean.setUserName(loginBean.getUserName());
			bean.setHeadImgUrl(userImg);
			bean.setRealName(realName);
			bean.setPhoneNumber(phone);
			bean.setEmail(email);
			wechatService.modifyUserInfo(bean);
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 获取统计数据
	 * 
	 * @param request
	 * @param response
	 * @说明
	 * 		统计类型type：
	 * 			 今日排行：today
	 * 			 总排行：    total
	 * 
	 * 
	 * 返回JSON格式:
	 *       {
	 *			"code": 200,
	 *			"results ": {
	 *				"ret": 100,
	 *				"msg": "成功",
	 *				"totalCount": 1,
	 *				"list": [{
	 *					"customerName":    "发稿人昵称",
	 *					"customerImg":     "发稿人头像",
	 *					"manuscriptCount": "发稿数量"
	 *				}]
	 *			}
	 *		}
	 *
	 *备注:
	 *		1、按照数量从大到小排列
	 *		2、发稿量以已提交稿件数量为准
	 */
	@RequestMapping("/manuscript/statistics")
	@ResponseBody
    private void statistics(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam(value = "type",required = false,defaultValue = "")  String  type
    		){
		try{
			UserBean bean=new UserBean();
			bean.setUserHeadIconLocalIp(PropertiesUtil.getValue("userHeadIconLocalIp"));
			bean.setUserHeadIconQingkIp(PropertiesUtil.getValue("userHeadIconQingkIp"));
			
			if("today".equals(type)){//统计当日
				String beginDate=DateUtil.getTody()+" 00:00:00";
				String endDate=DateUtil.getTody()+" 23:59:59";
				bean.setBeginDate(beginDate);
				bean.setEndDate(endDate);
			}
			
			List<UserBean> dataList=(List<UserBean>) wechatService.statistics(bean);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(UserBean tmpBean:dataList){
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("customerName",tmpBean.getUserName());
					resultMap.put("customerImg",tmpBean.getHeadImgUrl());
					resultMap.put("manuscriptCount",tmpBean.getCountNum());
					resultList.add(resultMap);
				}
			}
			
			Integer count=0;
			if(!isNullList(resultList)){
				count=resultList.size();
			}
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultList,count),response);
		}catch(Exception e) { 
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 *  获取稿件列表
	 * 
	 * 返回JSON:
	 * 
	 * {
	 *	    "code": 200,
	 *	    "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功",
	 *	        "totalCount": 1,
	 *	        "list": [{
	 *	            "manuscriptId":   "稿件id",
	 *	            "title":          "稿件标题",
	 *	            "customerName":   "发稿人昵称",
	 *	            "customerImg":    "发稿人头像",
	 *	            "createTime":     "发稿时间",
	 *	            "adoption":       "采用情况"
	 *	        }]
	 *	    }
	 *	}
	 * 
	 * 说明:
	 *      1、显示已提交的稿件。以稿件提交时间排序，最新提交的排前面。
	 *		2、topicId为空时，查询所有的结果；不为空，则查询该主题下的稿件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/manuscript/list")
	@PaginationController
	@ResponseBody
    private void getPapersList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("page")  	Integer  page
    		,@RequestParam("step")  	Integer  step
    		,@RequestParam(value="topicId",required=false,defaultValue="")   String   topicId
    		){
		
		   logger.info("【小程序】获取稿件列表页数据page="+page+",step="+step+",topicId="+topicId);
		   
		try{
			//设置分页
			PageContext pageContext=setPagination(page,step);
			
			ManuscriptBean bean=new ManuscriptBean();
			bean.setSubId(topicId);
			List<ManuscriptBean> dataList=(List<ManuscriptBean>) wechatService.getPapersList(bean);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(ManuscriptBean tmpBean:dataList){
					Map<String,Object> resultMap=new HashMap<String, Object>();
					resultMap.put("manuscriptId",tmpBean.getManuId());
					resultMap.put("title",tmpBean.getManuTitle());
					resultMap.put("customerName",tmpBean.getCreator());
					resultMap.put("customerImg",tmpBean.getManuImgUrl());
					resultMap.put("createTime",tmpBean.getManuCreateTime());
					resultMap.put("adoption",tmpBean.getManuUsage());
					resultMap.put("manuSubTime",tmpBean.getManuSubTime());
					resultList.add(resultMap);
				}
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultList,pageContext.getTotalRows()),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取稿件列表页数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 *  获取我的稿件列表
	 * 
	 * 返回JSON:
	 * 
	 * {
	 *	    "code": 200,
	 *	    "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功",
	 *	        "totalCount": 1,
	 *	        "list": [{
	 *	            "manuscriptId":   "稿件id",
	 *	            "title":          "稿件标题",
	 *	            "createTime":     "时间",
	 *	            "customerImg":    "发稿人头像",
	 *	            "adoption" :      "采用情况",
     *      		"status" :        "提交情况"
	 *	        }]
	 *	    }
	 *	}
	 * 
	 * 说明:
	 *    1、显示本账号创建的稿件。以稿件创建时间排序，最新创建的排前面。
	 *    
	 * @param request
	 * @param response
	 */
	@RequestMapping("/manuscript/myList")
	@PaginationController
	@ResponseBody
    private void getMyPapersList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("page")  	Integer  page
    		,@RequestParam("step")  	Integer  step
    		,@RequestParam("JSESSION")  String  JSESSION
    		){
		try{
			
			logger.error("【小程序】获取我的稿件列表页数据:page="+page+",step="+step);
			
			//设置分页
			PageContext pageContext=setPagination(page,step);
			
			//LoginBean loginBean=(LoginBean) request.getSession().getAttribute("loginBean");
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuUserId(loginBean.getUserKey());
			List<ManuscriptBean> dataList=(List<ManuscriptBean>) wechatService.getPapersList(bean);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(ManuscriptBean tmpBean:dataList){
					Map<String,Object> resultMap=new HashMap<String, Object>();
					resultMap.put("manuscriptId",tmpBean.getManuId());
					resultMap.put("title",tmpBean.getManuTitle());
					resultMap.put("createTime",tmpBean.getManuCreateTime());
					resultMap.put("adoption",tmpBean.getManuUsage());
					resultMap.put("status",tmpBean.getManuState());
					resultList.add(resultMap);
				}
			}
			
			sendJson(getJson(
						CODE.SUCCESS, 
						RET.SUCCESS,
						resultList,
						pageContext.getTotalRows()
						)
					,response);
			
		}catch(Exception e) { 
			logger.error("【小程序】获取我的稿件列表页数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 获取稿件详情数据
	 * 
	 * 返回JSON:
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"body": {
	 *				"manuscriptId":   "稿件id",
	 *				"title":          "稿件标题",
	 *				"topicId":        "专题id",
	 *				"topicTitle":     "专题名称",
	 *				"createTime":     "发稿时间",
	 *				"customerName":   "发稿人姓名",
	 *				"customerImg":    "发稿人头像",
	 *				"content":        "稿件内容",
	 *				"adoption":       "采用情况",
	 *				"status":         "提交情况"
	 *			}
	 *		}
	 *	}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/manuscript/detail")
	@ResponseBody
    private void getPapersView(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("manuscriptId")  	String  manuscriptId            //稿件ID
    		){
			
		logger.info("【小程序】获取稿件详情数据:manuscriptId="+manuscriptId);
		
		try{
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuId(manuscriptId);
			bean.setUserHeadIconLocalIp(PropertiesUtil.getValue("userHeadIconLocalIp"));
			bean.setUserHeadIconQingkIp(PropertiesUtil.getValue("userHeadIconQingkIp"));
			bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
			bean=wechatService.getPapersView(bean);
			
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(bean!=null){
				resultMap.put("manuscriptId",bean.getManuId());
				resultMap.put("title",bean.getManuTitle());
				resultMap.put("topicId",bean.getSubId());
				resultMap.put("topicTitle",bean.getSubTitle());
				resultMap.put("createTime",bean.getManuCreateTime());
				resultMap.put("customerName",bean.getCreator());
				resultMap.put("customerImg",bean.getCustomerImg());
				
				String cloudeHtmlUrl=PropertiesUtil.getValue("cloudeHtmlUrl");
				String dataFiles=cloudeHtmlUrl.split("/")[0];
				String projectUrl=PropertiesUtil.getValue("projectUrl");
				
				List<String> contentList=AnalyseHtml.getHtmlByPath(request.getSession().getServletContext().getRealPath("/").replace("integrate_pipe",projectUrl)+"/"+dataFiles+"/"+bean.getManuContent(),false,"div[class='previewDetailContent']");
				if(!isNullList(contentList)){
					resultMap.put("content",contentList.get(0));
				}else{
					resultMap.put("content","");
				}
				
				resultMap.put("adoption",bean.getManuUsage());
				resultMap.put("status",bean.getManuState());
				resultMap.put("manuscriptImg",bean.getManuImgUrl());
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultMap),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取稿件详情数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	
	/**
	 * 获取专题列表数据
	 * 
	 * 返回JSON
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"totalCount": 1,
	 *			"list": [{
	 *				"topicId":         "专题id",
	 *				"title":           "专题名称",
	 *				"createTime":      "时间",
	 *				"manuscriptCount": "回稿数量"
	 *			}]
	 *		}
	 *	}
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/topic/list")
	@ResponseBody
	@PaginationController
    private void getSubjectList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("page")  	Integer  page
    		,@RequestParam("step")  	Integer  step
    		){
		try{
			logger.info("【小程序】获取专题列表数据:page="+page+",step="+step);
			
			//设置分页
			PageContext pageContext=setPagination(page,step);
			
			SubjectBean bean=new SubjectBean();
			List<SubjectBean> dataList=(List<SubjectBean>) wechatService.getSubjectList(bean);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(SubjectBean tmpBean:dataList){
					Map<String,Object> resultMap=new HashMap<String, Object>();
					resultMap.put("topicId",tmpBean.getSubId());
					resultMap.put("title",tmpBean.getSubTitle());
					resultMap.put("createTime",tmpBean.getSubCreateTime());
					resultMap.put("manuscriptCount",tmpBean.getCountNum());
					resultList.add(resultMap);
				}
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultList,pageContext.getTotalRows()),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取专题列表数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 获取专题详情页数据
	 * 
	 * 返回JSON
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"body": {
	 *				"topicId": "专题id",
	 *				"title": "专题名称",
	 *				"createTime": "时间",
	 *				"manuscriptCount": "回稿数",
	 *				"content": "描述信息"
	 *			}
	 *		}
	 *	}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/topic/detail")
	@ResponseBody
    private void getSubjectView(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("topicId")  	String  topicId
    		){
		    logger.info("【小程序】获取专题详情页数据:topicId="+topicId);
		try{
			
			SubjectBean bean=new SubjectBean();
			bean.setSubId(topicId);
			bean=wechatService.getSubjectView(bean);
			
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(bean!=null){
				resultMap.put("topicId",bean.getSubId());
				resultMap.put("title",bean.getSubTitle());
				resultMap.put("createTime",bean.getSubCreateTime());
				resultMap.put("manuscriptCount",bean.getCountNum());
				resultMap.put("content",bean.getSubContent());
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultMap),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取专题详情页数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 发稿
	 * 
	 * 返回JSON 
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"body": {
	 *				"manuscriptId": "稿件id"
	 *			}
	 *		}
	 *	}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/manuscript/add")
	@ResponseBody
    private void index(HttpServletRequest request,HttpServletResponse response
    		){
			//获取参数
			String topicId=request.getParameter("topicId");				 //专题ID
			String manuscriptImg=request.getParameter("manuscriptImg");	 //缩略图	
			String title=request.getParameter("title");					 //标题
			String content=request.getParameter("content");				 //正文
			String type=request.getParameter("type"); 					 //类型:  add:发稿 reply：回稿
			String status=request.getParameter("status");				 //类型:  0:未提交 保存至我的稿件   1：提交 
			String JSESSION=request.getParameter("JSESSION");
		
			logger.info("【小程序】发稿:topicId="+topicId+",manuscriptImg="+manuscriptImg+",title="+title+",content="+content+",status="+status+",type="+type);
		
		try{
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			
			String manuId=PkCreat.getTablePk();
			
			ManuscriptBean bean=new ManuscriptBean();
			bean.setSubId(topicId);
			bean.setManuImgUrl(manuscriptImg);
			bean.setManuTitle(title);
			
			StaticHtmlBean staticHtmlBean=new StaticHtmlBean();
			staticHtmlBean.setManuContent(content);
			String htmlUrl=FileUtil.createHtmlFile(request,staticHtmlBean);
			
			bean.setManuContent(htmlUrl);
			bean.setManuId(manuId);
			bean.setManuState(status);
			//bean.setManuUsage("稿件");   	   //小程序只能是:   稿件
			bean.setManuCreateTime(DateUtil.getTimeToSec());
			bean.setManuUserId(loginBean.getUserKey());
			
			if("1".equals(status)){
				bean.setManuSubTime(DateUtil.getTimeToSec());
			}
			
			wechatService.addPapers(bean);
			
			Map<String,Object> resultMap=new HashMap<String, Object>();
			resultMap.put("manuscriptId",manuId);
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,resultMap),response);
		}catch(Exception e) { 
			logger.error("【小程序】发稿出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 删除稿件
	 * @param request
	 * @param response
	 * @param manuscriptId
	 */
	@RequestMapping("/manuscript/delete")
	@ResponseBody
    private void delete(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("manuscriptId")  			String  manuscriptId               //专题ID
    		){
		logger.info("【删除稿件】manuscriptId="+manuscriptId);
		
		try{
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuId(manuscriptId);
			
			wechatService.deletePapers(bean);
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【小程序】删除稿件出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 编辑稿件
	 * @param request
	 * @param response
	 * @param manuscriptId
	 */
	@RequestMapping("/manuscript/modify")
	@ResponseBody
    private void manuscriptModify(HttpServletRequest request,HttpServletResponse response
    		){
		
		String manuscriptId=request.getParameter("manuscriptId");
		String manuscriptImg=request.getParameter("manuscriptImg");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String status=request.getParameter("status");
		
		logger.info("【修改稿件】manuscriptId="+manuscriptId+",manuscriptImg="+manuscriptImg+",title="+title+",content="+content+",status="+status);
		
		try{
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuId(manuscriptId);
			bean.setManuImgUrl(manuscriptImg);
			bean.setManuTitle(title);
			
			StaticHtmlBean staticHtmlBean=new StaticHtmlBean();
			staticHtmlBean.setManuContent(content);
			String htmlUrl=FileUtil.createHtmlFile(request,staticHtmlBean);
			bean.setManuContent(htmlUrl);
			bean.setManuState(status);
			
			wechatService.modifyPapers(bean);
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【小程序】删除稿件出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 提交稿件，主要用于我的稿件中未提交的稿件进行提交
	 * @param request
	 * @param response
	 * @param manuscriptId
	 */
	@RequestMapping("/manuscript/submit")
	@ResponseBody
    private void manuscriptModify(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("manuscriptId")  			String  manuscriptId               //稿件ID
    		,@RequestParam("status")  					String  status               	   //状态 目前固定为 1
    		){
		logger.info("【修改稿件】manuscriptId="+manuscriptId+",status="+status);
		
		try{
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuId(manuscriptId);
			bean.setManuState(status);
			if("1".equals(status)){
				bean.setManuSubTime(DateUtil.getTimeToSec());
			}
			
			wechatService.modifyStatusPapers(bean);
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【小程序】删除稿件出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 通讯联动稿件采用情况
	 * @param request
	 * @param response
	 * @param manuscriptId
	 * 
	 * {
	 *	   "code": 200
	 *	    "results": {
	 *	        "ret": 100,
	 *	        "msg": "成功"
	 *	        "totalCount": 1,
	 *	        "list": [{
	 *	            "time": "采用时间",
	 *	            "customerName": "采用账号"
	 *	            "adoption" : "采用情况"
	 *	        }],
	 *	    },
	 *	}
	 * 
	 * 说明:
	 * 	  1、 排序：最早采用的排前面。最新采用的排后面
	 */
	@RequestMapping("/manuscript/adoptionList")
	@ResponseBody
    private void adoptionList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("manuscriptId")  			String  manuscriptId               //稿件ID
    		){
		
		logger.info("【修改稿件】manuscriptId="+manuscriptId);
		
		try{
			ManuscriptBean bean=new ManuscriptBean();
			bean.setManuId(manuscriptId);
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) wechatService.adoptionList(bean);
			
			Integer count=0;
			if(!isNullList(dataList)){
				count=dataList.size();
			}
			
			sendJson(getJson(CODE.SUCCESS, RET.SUCCESS,dataList,count),response);
		}catch(Exception e) { 
			logger.error("【小程序】查询通讯联动稿件采用情况失败:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	
	/**
	 * 素材列表页
	 * @param request
	 * @param response
	 * @param manuscriptId
	 * @param manuscriptImg
	 * @param title
	 * @param content
	 * @param status
	 */
	@RequestMapping("/material/list")
	@ResponseBody
	@PaginationController
    private void materialList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam(value = "topicId",required = false,defaultValue = "")     String  topicId
    		,@RequestParam("page")  					Integer  page               	  
    		,@RequestParam("step")  					Integer  step      
    		,@RequestParam(value = "JSESSION")     		String  JSESSION
    		){
		logger.info("【素材列表页】topicId="+topicId+",page="+page+",step="+step);
		
		try{
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
			PageContext pageContent=setPagination(page,step);
			
			FileBean bean=new FileBean();
			bean.setSubId(topicId);
			bean.setImgDomain(PropertiesUtil.getValue("imgDomainMobile"));
			bean.setUserKey(loginBean.getUserKey());
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) wechatService.getMaterialList(bean);
			
			
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,dataList,pageContent.getTotalRows()),response);
		}catch(Exception e) { 
			logger.error("【小程序】素材列表页查询失败:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	/**
	 * 增加素材
	 * @param request
	 * @param response
	 * @param topicId
	 * @param title
	 * @param content
	 * @param iconSrc
	 * @param materialSrc
	 * @param type
	 */
	@RequestMapping("/material/add")
	@ResponseBody
    private void addMaterial(HttpServletRequest request,HttpServletResponse response){
		
		//获取参数
		String topicId=request.getParameter("topicId");	//专题key 可为空
		String title=request.getParameter("title");		//素材名称
		String content=request.getParameter("content");	//文案内容/备注
		String iconSrc=request.getParameter("iconSrc");	//视频缩略图
		String materialSrc=request.getParameter("materialSrc"); //视频目录/图片目录
		String type=request.getParameter("type");				//类型 text:文案 img:图片 video：视频
		String JSESSION=request.getParameter("JSESSION");
		
	try{
		
			logger.info("【增加素材】topicId="+topicId+",title="+title+",content="+content+",iconSrc="+iconSrc+",materialSrc="+materialSrc+",type="+type);
		
			LoginBean loginBean=(LoginBean)SessionContext.getInstance().getSession(JSESSION).getAttribute("loginBean");
	
			String time=DateUtil.getTimeToSec();
			
			FileBean bean=new FileBean();
			bean.setUserFileKey(PkCreat.getTablePk());
			bean.setSubId(topicId);
			bean.setUserFileName(title);
			bean.setRemarks(content);
			bean.setImageUrl(iconSrc);
			bean.setFileUrl(materialSrc);
			bean.setFileType(type);
			bean.setCreateTime(time);
			bean.setUpdateTime(time);
			bean.setOrderNum(ORDER_NUMBER_DEFAULT);
			bean.setUserKey(loginBean.getUserKey());
			bean.setIsDelete("0");
			bean.setCreator(loginBean.getUserName());
			bean.setShowStatus("1");      //显示状态: 0:隐藏;1:显示
			bean.setUploadType("2");      //上传类型: 1:后台 ;2:小程序
			
			//2019-06-27   栗超 
			//专题key不为空的情况下，表中source_type为1，若为空，则表中source_type为空
			if(topicId!=null&&!"".equals(topicId)){
				bean.setSourceType("1");  		//专题
			}
			
			wechatService.addMaterial(bean);
			
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS),response);
		}catch(Exception e) { 
			logger.error("【小程序】增加素材:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
	
	/**
	 * 获取专题推荐列表数据
	 * 
	 * {
	 *		"code": 200,
	 *		"results": {
	 *			"ret": 100,
	 *			"msg": "成功",
	 *			"totalCount": 1,
	 *			"list": [{
	 *				"topicId":         "专题id",
	 *				"title":           "专题名称",
	 *				"createTime":      "时间",
	 *				"manuscriptCount": "回稿数量"
	 *			}]
	 *		}
	 *	}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/topic/recommendlist")
	@ResponseBody
	@PaginationController
    private void getSubjectRecommendList(HttpServletRequest request,HttpServletResponse response
    		,@RequestParam("page")  	Integer  page
    		,@RequestParam("step")  	Integer  step
    		){
		try{
			PageContext pageContent=setPagination(page,step);
			
			SubjectBean bean=new SubjectBean();
			List<Map<String,Object>> resultList=(List<Map<String, Object>>) wechatService.getSubjectRecommendList(bean);
			sendJson(getJson(CODE.SUCCESS,RET.SUCCESS,resultList,pageContent.getTotalRows()),response);
		}catch(Exception e) { 
			logger.error("【小程序】获取专题推荐列表数据出错:",e);
			sendJson(getJson(CODE.FAIL,RET.FAIL),response);
		}
	}
	
}
