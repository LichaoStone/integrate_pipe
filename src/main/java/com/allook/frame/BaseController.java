package com.allook.frame;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.allook.frame.page.PageContext;
import com.allook.mobile.bean.MobileBean;

/**
 * controller基础类
 * @作者 栗超
 * @时间 2018年5月12日 上午8:55:58
 * @说明
 */
public class BaseController extends Constants{
	private static final Logger logger = Logger.getLogger(BaseController.class);
	 
	 @InitBinder
	 public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
			if (request.getCharacterEncoding() == null || !request.getCharacterEncoding().toLowerCase().equals("utf-8")) {
				logger.info("getCharacterEncoding:" + request.getCharacterEncoding() + ":",new Exception("打印堆栈"));
			}
			
			request.setCharacterEncoding("utf-8");
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	 }
	 
	 /**
	  * 发送json
	  * @param json
	  * @param response
	  */
	 protected static void sendJson(String json, HttpServletResponse response){
		  try {
				logger.info("json===>\n" + json);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out;
				
				out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("BaseController.sendJson出错:",e);
			}
      }
	 
	 /**
	  * 组装返回json串
	  * @param ret
	  * @param msg
	  * @return
	  */
	  protected static String getJson(int CODE,int RET,List<?> resultList,Integer totalRow) {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("code", CODE);
	    	Map<String, Object> map1 = new HashMap<String, Object>();
	    	map1.put("ret", RET);
	    	map1.put("msg", RET_INFO.get(RET));
	    	if (resultList!=null&&resultList.size()>0) {
	    		map1.put("list", resultList);
	    		map1.put("totalCount",totalRow);
	    	}else{
	    		map1.put("list", "");
	    		map1.put("totalCount", 0);
	    	}
	    	map.put("results", map1);
	    	return net.sf.json.JSONObject.fromObject(map).toString();
	    	
	  }
	  
	  
	  protected static String getJson(int CODE,int RET,Object object) {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("code", CODE);
	    	Map<String, Object> map1 = new HashMap<String, Object>();
	    	map1.put("ret", RET);
	    	map1.put("msg", RET_INFO.get(RET));
	    	map1.put("body", object);
	    	map.put("results", map1);
	    	return net.sf.json.JSONObject.fromObject(map).toString();
	  }
	  
	  
	  protected static String getJson(int CODE,int RET) {
		    JSONObject jsonObj = new JSONObject();  
			jsonObj.put("code",CODE);
		    
	    	JSONObject jsonObj2 = new JSONObject();
	    	jsonObj2.put("ret",RET);
	    	jsonObj2.put("msg",RET_INFO.get(RET));
	    	
	    	jsonObj.put("results",jsonObj2);
	    	logger.info("组装完成返回json:" + jsonObj.toString());
	    	return jsonObj.toString();
	  }
	  
	  /**
	   * 分页
	   * @param page 第几页
	   * @param step	每页多少条
	   */
	  public synchronized PageContext setPagination(Integer pageNumber,Integer step) {
		  	
		  	if (step==null) {
		  		step=18;
			}
		  	
		  	PageContext pageContext=PageContext.getContext();
			pageContext.setPageStartRow(pageNumber);
			pageContext.setPageSize(step);
			pageContext.setPaginationController(true);
			
			return pageContext;
	  }
	  
	  /**
	   * 判断list集合是否为空
	   * 	空:true
	   * 	非空:false
	   * @param list
	   * @return
	   */
	  protected static boolean isNullList(List<?> list) {
		    boolean resFlag=true;
		    
		    if (list!=null&&list.size()>0) {
		    	resFlag=false;
			}
	    	return resFlag;
	  }
	  
	  
	  
	  public static void main(String[] args) {
		  List<MobileBean> list=new ArrayList<MobileBean>();
		  MobileBean bean=new MobileBean();
		  bean.set_appid("1111");
		  bean.set_secret("2222");
		  bean.setCreateTime("2018-05-15 23:22:00");
		  list.add(bean);
		  System.out.println(getJson(CODE.SUCCESS,RET.SUCCESS,list));
	  }
	  
	  
	  
}
