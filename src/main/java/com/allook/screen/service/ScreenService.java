package com.allook.screen.service;

import java.util.List;
import java.util.Map;

import com.allook.local.bean.DictionariesBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.screen.bean.ScreenBean;
/**
 * 大屏Service接口类
 * @作者 栗超
 * @时间 2018年7月6日 下午2:52:29
 * @说明
 */
public interface ScreenService {
	 /**
	  * 刷新大屏
	  * @param bean
	  * @return
	  * @throws Exception
	  */
     public String           refreshScreen(ScreenBean bean) throws Exception;
     /**
      * 查询屏幕配置信息:屏号或者模块名（可能多条数据）
      * @param bean
      * @return
      * @throws Exception
      */
     public List<?> 		 getScreenSetting(ScreenBean bean) throws Exception;
     /**
      * 查询数据字典
      * @param bean
      * @return
      * @throws Exception
      */
     public	List<?>     	 getDictValuesByNames(DictionariesBean bean)  throws Exception;
     /**
      * 获取用户实时地理位置及相关信息
      * @param bean
      * @return
      * @throws Exception
      */
     public	List<?>          getUserTask(DistributionTaskBean bean)  throws Exception;
     /**
      * 是否允许通话
      * @return
      * @throws Exception
      */
     public	boolean          isAllowCall()  throws Exception;
     /**
      * 根据屏号获取屏幕配置信息
      * @param screenNum
      * @return
      * @throws Exception
      */
     public ScreenBean 	   getPageFlagSetting(String screenNum) throws Exception;
     
     /**
      * 地方舆论详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailDFYL(String key) throws Exception;
     
     /**
      * 新闻热榜详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailQWRD(String key) throws Exception;
     /**
      * 本地新闻详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailBDXW(String key) throws Exception;
     /**
      * 资讯热榜详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailZXRB(String key) throws Exception;
     /**
      * 新闻选题详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailXWXT(String key) throws Exception;
     
     /**
      * 传习中心详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     getDetailCXZX(String key) throws Exception;
     
     
     /**
      * 发布次数
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     publishCount(ScreenBean screenBean) throws Exception;
     
     /**
      * 公众账号
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     wechatAccount(ScreenBean screenBean) throws Exception;
     
     /**
      * 发布稿件
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     publishManu(ScreenBean screenBean) throws Exception;
     
     /**
      * 审核稿件
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     examineManu(ScreenBean screenBean) throws Exception;
     
     
     /**
      * V3.0
      *   爆料管理详情数据
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     getDetailBLGL(String key) throws Exception;
     
     /**
      * V3.0
      *   通讯联动管理详情数据
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     getDetailTXLD(String key) throws Exception;
     
     /**
      * V3.0
      *   考勤管理详情页数据
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>     getDetailKQGL(PunchBean bean) throws Exception;
}
