package com.allook.screen.mapper;

import java.util.List;
import java.util.Map;

import com.allook.clouds.bean.EffectStatisticsBean;
import com.allook.frame.BaseEntity;
import com.allook.local.bean.ChartSettingBean;
import com.allook.local.bean.DictionariesBean;
import com.allook.local.bean.ListSettingBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.screen.bean.ScreenBean;
/**
 * 大屏 sqlMapper接口类
 * @作者 栗超
 * @时间 2018年7月6日 下午3:15:49
 * @说明
 */
public interface ScreenSqlMapper {
	 /**
	  * 获取屏幕配置信息
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?> 	getScreenSetting(ScreenBean bean) throws Exception;
	 /**
	  * 网络热搜
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getHotSearch(Map<?,?> map)throws Exception;
	 /**
	  * 地方舆论o
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getLocalOpinion(Map<?,?> map) throws Exception;
	 /**
	  * 本地新闻/全网热点
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getLocalNews(Map<?,?> map) throws Exception;
	 /**
	  * 生产力统计
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getProductivityStatistics(Map<?, ?> map) throws Exception;
	 /**
	  * 影响力统计
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getEffectStatistics(EffectStatisticsBean bean) throws Exception;
	 /**
	  * 任务统计
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public	List<?>  	getTaskStatistics(Map<?,?> map)  throws Exception;
	 /**
	  * 资讯热榜
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getInformationHot(Map<?,?> map) throws Exception;
	 /**
	  * 视频热榜
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  	getVideoHot(Map<?,?> map) throws Exception;
	 /**
	  * 新闻选题
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getSelectedTopic(Map<?,?> map)  throws Exception;
	 /**
	  * 获取用户实时地理位置及相关信息
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public	List<?>     getUserTask(DistributionTaskBean bean)  throws Exception;
	 /**
	  * 图标自定义-饼状图
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getChartCakeDataSetting(ChartSettingBean bean)  throws Exception;
	 /**
	  * 图标自定义-折线图和柱状图
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getChartDataSetting(ChartSettingBean bean)  throws Exception;
	 /**
	  * 列表自定义-列数据
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getListColumnSetting(ListSettingBean bean)  throws Exception;
	 /**
	  * 列表自定义-行数据
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getListRowSetting(ListSettingBean bean)  throws Exception;
	 /**
	  * 列表自定义-行总数
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>     getListRowSize(ListSettingBean bean)  throws Exception;
	 /**
	  * 根据name获取数据字典表对应value
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public	List<?>     getDictValuesByNames(DictionariesBean bean)  throws Exception;
	 public	String      getDictValuesByName(String name)  throws Exception;
	 /**
	  * 获取应用通话总时长
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public Integer     getSumCallTime(ScreenBean bean) throws Exception;
	 /**
	  * 列表自定义-列表属性
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public ListSettingBean      getListSetting(ListSettingBean bean)  throws Exception;
	 /**
	  * 图标自定义-图标属性
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public	ChartSettingBean     getChartSetting(ChartSettingBean bean)  throws Exception;
	 /**
	  * 根据屏号获取屏幕对应配置参数
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public ScreenBean 			 getPageFlagSetting(ScreenBean bean) throws Exception;
	 
	 /**
	  * 获取传习中心数据
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public List<?>  getCenterList(Map<?,?> map) throws Exception;
	 
	 /**
      * 地方舆论详情
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailDFYL(BaseEntity baseEntity) throws Exception;
    
     /**
      * 获取评论图片列表
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>       getImgeList(String key) throws Exception;
	 /**
      * 新闻热榜
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     getDetailQWRD(String key) throws Exception;
     /**
      * 本地新闻
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailBDXW(String key) throws Exception;
     /**
      * 资讯热榜
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>      getDetailZXRB(String key) throws Exception;
     /**
      * 新闻选题
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>      getDetailXWXT(String key) throws Exception;
     
     /**
      * 传习中心
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
     public List<?>    publishCount(ScreenBean screenBean) throws Exception;
     /**
      * 公众账号
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>     wechatAccount(ScreenBean screenBean) throws Exception;
     /**
      * 发布稿件
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>     publishManu(ScreenBean screenBean) throws Exception;
     /**
      * 发布稿件（微信矩阵websocket查询数据使用）
      * @param screenBean
      * @return
      * @throws Exception
      */
     public List<?>     publishManuWXJZ(ScreenBean screenBean) throws Exception;
     /**
      * 审核稿件
      * @param key
      * @return
      * @throws Exception
      */
     public List<?>     examineManu(ScreenBean screenBean) throws Exception;
     
     /**
      * 发布总次数
      * @param key
      * @return
      * @throws Exception
      */
     public Integer   publishTotalCount(String indexPage) throws Exception;
     
     /**
         * 公众号总个数
      * @return
      * @throws Exception
      */
     public Integer   wechatTotalCount() throws Exception;
     
     /**
      * 发布稿件总数
      * @return
      * @throws Exception
      */
     public Integer   publishManuTotalCount(String indexPage) throws Exception;
     
     /**
      * 审核稿件总数(审核通过和不通过的数据)
      * @return
      * @throws Exception
      */
     public Integer   examineManuTotalCount(String indexPage) throws Exception;
     
     /**
      * 获取爆料管理详情数据
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>  getDetailBLGL(String key) throws Exception;
     /**
      * 获取通讯联动管理详情数据
      * @param key
      * @return
      * @throws Exception
      */
     public Map<?,?>     getDetailTXLD(String key) throws Exception;
     
     /**
      * V3.0 
      * 获取考勤管理列表数据
      * @return
      * @throws Exception
      */
     public List<?>      getKaoQinList(PunchBean bean) throws Exception;
     public List<?>      getReportList(PunchBean bean) throws Exception;
     public List<?>      getClassifyTypeList(PunchBean bean) throws Exception;
     public List<?>      getReportStatistics(PunchBean bean) throws Exception;
     public List<?>		 getLDGLList(ScreenBean bean) throws Exception;
     public List<?>		 getFileByKey(ScreenBean bean) throws Exception;
     public List<?>		 getSubjectReportList(ScreenBean bean) throws Exception;
     
}
