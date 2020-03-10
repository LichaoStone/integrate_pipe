package com.allook.screen.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.allook.clouds.bean.EffectStatisticsBean;
import com.allook.clouds.bean.LocalNewsBean;
import com.allook.clouds.bean.ProductivityStatisticsBean;
import com.allook.frame.BaseController;
import com.allook.frame.BaseEntity;
import com.allook.frame.aspect.annotation.PaginationService;
import com.allook.frame.page.PageContext;
import com.allook.local.bean.ChartSettingBean;
import com.allook.local.bean.DictionariesBean;
import com.allook.local.bean.ListSettingBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.screen.bean.ScreenBean;
import com.allook.screen.mapper.ScreenSqlMapper;
import com.allook.screen.service.ScreenService;
import com.allook.socket.WebScoketHandler;
import com.allook.utils.AnalyseHtml;
import com.allook.utils.DateUtil;
import com.allook.utils.MD5;
import com.allook.utils.PropertiesUtil;


/**
 * 大屏Service类
 * @作者 栗超
 * @时间 2018年6月6日 下午7:19:29
 * @说明
 */
@Service
public class ScreenServiceImpl extends BaseController implements ScreenService{
	public static final Logger logger = Logger.getLogger(ScreenServiceImpl.class);
	
    @Autowired
    private ScreenSqlMapper screenSqlMapper;
    @Autowired
	private WebScoketHandler handler;
    
    
	@Override
	@PaginationService
	public String refreshScreen(ScreenBean screenBean) throws Exception{
		
 		String json="";
		int showCount = 10;
		if(null != screenBean.getShowCount()){
			showCount =Integer.parseInt(screenBean.getShowCount());
		}
		
		logger.info("【大屏接口】刷新大屏数据:展示条数="+screenBean.getShowCount()
				+"高亮时间:"+screenBean.getHighlightSecond()
				+",大屏号:"+screenBean.getScreenNum()
				+",模块名称:"+screenBean.getScreenName()
				+",显示条数："+showCount);
		
		//设置分页
		PageContext pageContext= setPagination(0,showCount);
		
		
		boolean sengMseeageFlag=false;
		Map<String,String> paraMap=new HashMap<String,String>();
		if (MODULE.WLRS.equals(screenBean.getShowTypeName())){//网络热搜
			List<Map<String,Object>> hotSearchList=(List<Map<String, Object>>) screenSqlMapper.getHotSearch(paraMap);
			
			/**
			 * JSON串格式:
			 * 	{
					"msg": "OK",
					"code": 200,
					"action": "detail",
					"type": "wlrs",
					"body": {
						"totalCount": 8794,
						"list": [{
							"hotSearchKey": "b6c135327f1b4db3afab5d7e1ea35737",
							"hotDegree": "100.00",
							"createTime": "2018-07-04 14",
							"hotSearchContent": "3天打印1套别墅",
							"showStatus": "1",
							"source": "weibo"
						}, {
							"hotSearchKey": "5c3c2bc9bdcd44478c000354d3cfa652",
							"hotDegree": "99.67",
							"createTime": "2018-07-04 14",
							"hotSearchContent": "微信支付被曝漏洞",
							"showStatus": "1",
							"source": "baidu"
						}
								 ... ...]
					}
				}
			 */
			sengMseeageFlag=sendMessage(MODULE.WLRS,hotSearchList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.BDXW.equals(screenBean.getShowTypeName())) {//本地新闻
			paraMap.put("imgDomain",PropertiesUtil.getValue("imgIP"));
			paraMap.put("type","1");
			List<LocalNewsBean> resultList=(List<LocalNewsBean>) screenSqlMapper.getLocalNews(paraMap);
			
			/**
			 * 返回JSON串格式:
			 * 
			 * {
					"msg": "OK",
					"code": 200,
					"action": "detail",
					"type": "bdxw",
					"body": {
						"totalCount": 29291,
						"list": [{
							"hotspotNewsClueKey": "a0c445520f48422a8cf2b0c1a65cf009",
							"hotspotNewsClueDescript": "“从线下开门即拿的智能货柜，到线上的电商零售，再到商品的区块链溯源，这当中始终有着一条智慧供应链将合适的商品在合适的时间、合适的地点交到合适的人手上，而供应链正",
							"createTime": "2018-07-04 13:28:38",
							"thumImg": "http://10.10.49.202/cloudfiles/image/65925e82542d40f48f765ee200f1f1ec.jpg",
							"hotspotNewsClueTitle": "京东裴健：从线性到网状供应网络智慧供应链可大幅降低社会成本",
							"source": "央广网"
						}, {
							"hotspotNewsClueKey": "0a1ca9ce12c24fcab8f6d7571beb9ca4",
							"hotspotNewsClueDescript": "习近平致信祝贺《求是》暨《红旗》杂志创刊60周年强调 不断提高理论宣传水平 更好服务党和国家工作大局 中共中央总书记、国家主席、中央军委主席习近平4日致信祝贺党",
							"createTime": "2018-07-04 14:58:37",
							"thumImg": "",
							"hotspotNewsClueTitle": "习近平致信祝贺《求是》暨《红旗》杂志创刊60周年",
							"source": "中国西藏网"
						}......]
					}
				}
			 */
			
			sengMseeageFlag=sendMessage(MODULE.BDXW,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.QWRD.equals(screenBean.getShowTypeName())) {//全网热点
			paraMap.put("imgDomain",PropertiesUtil.getValue("imgIP"));
			paraMap.put("type","0");
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) screenSqlMapper.getLocalNews(paraMap);
			
			/**
			 * 返回JSON串格式:
			 * 
			 * {
					"msg": "OK",
					"code": 200,
					"action": "detail",
					"type": "qwrd",
					"body": {
						"totalCount": 29291,
						"list": [{
							"hotspotNewsClueKey": "a0c445520f48422a8cf2b0c1a65cf009",
							"hotspotNewsClueDescript": "“从线下开门即拿的智能货柜，到线上的电商零售，再到商品的区块链溯源，这当中始终有着一条智慧供应链将合适的商品在合适的时间、合适的地点交到合适的人手上，而供应链正",
							"createTime": "2018-07-04 13:28:38",
							"thumImg": "http://10.10.49.202/cloudfiles/image/65925e82542d40f48f765ee200f1f1ec.jpg",
							"hotspotNewsClueTitle": "京东裴健：从线性到网状供应网络智慧供应链可大幅降低社会成本",
							"source": "央广网"
						}, {
							"hotspotNewsClueKey": "0a1ca9ce12c24fcab8f6d7571beb9ca4",
							"hotspotNewsClueDescript": "习近平致信祝贺《求是》暨《红旗》杂志创刊60周年强调 不断提高理论宣传水平 更好服务党和国家工作大局 中共中央总书记、国家主席、中央军委主席习近平4日致信祝贺党",
							"createTime": "2018-07-04 14:58:37",
							"thumImg": "",
							"hotspotNewsClueTitle": "习近平致信祝贺《求是》暨《红旗》杂志创刊60周年",
							"source": "中国西藏网"
						}......]
					}
				}
			 */
			sengMseeageFlag=sendMessage(MODULE.QWRD,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.DFYL.equals(screenBean.getShowTypeName())) {//地方舆论
			paraMap.put("userHeadIconLocalIp", PropertiesUtil.getValue("userHeadIconLocalIp"));//默认头像IP
			paraMap.put("userHeadIconQingkIp", PropertiesUtil.getValue("userHeadIconQingkIp"));//自定义头像
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) screenSqlMapper.getLocalOpinion(paraMap);
			sengMseeageFlag=sendMessage(MODULE.DFYL,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.XWXT.equals(screenBean.getShowTypeName())) {//新闻选题
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) screenSqlMapper.getSelectedTopic(paraMap);
			sengMseeageFlag=sendMessage(MODULE.XWXT,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.WCDD.equals(screenBean.getShowTypeName())) {//外采调度（测试使用）
			sengMseeageFlag=sendMessage(MODULE.WCDD,new ArrayList<>(),pageContext,screenBean.getScreenNum());
		}else if (MODULE.SCLTJ.equals(screenBean.getShowTypeName())) {//生产力统计
			ProductivityStatisticsBean productivityStatisticsBean=new ProductivityStatisticsBean();
			BaseEntity baseEntity=DateUtil.getBeginEndDate(screenBean.getTimeType(),MODULE.SCLTJ);
			
			/**
			 * V3.0
			 * 增加了返回字段:
			 *       pv、uv和score(打分)  
			 *       
			 * 说明:
			 *    1.打分计算公式
			 *    		（实际发稿量/规定发稿量 *打分占比+实际PV/规定PV*打分占比+实际UV/规定UV*打分占比）*100分
			 *      
			 * 
			 */
			paraMap.put("beginDate", baseEntity.getBeginDate());
			paraMap.put("endDate", baseEntity.getEndDate());
			paraMap.put("userHeadIconLocalIp",PropertiesUtil.getValue("userHeadIconLocalIp"));
			paraMap.put("userHeadIconQingkIp",PropertiesUtil.getValue("userHeadIconQingkIp"));
			List<Map<String,String>> dataList=(List<Map<String,String>>) screenSqlMapper.getProductivityStatistics(paraMap);
			
			//查询字典表数据
			DictionariesBean bean=new DictionariesBean();
			bean.setDictionariesName(new StringBuffer()
					.append("'").append(DICTIONARIES.DAILY_DISPATCH).append("'")
					.append(",")
					.append("'").append(DICTIONARIES.DAILY_DISPATCH_RATIO).append("'")
					.append(",")
					.append("'").append(DICTIONARIES.DAILY_PV).append("'")
					.append(",")
					.append("'").append(DICTIONARIES.DAILY_PV_RATIO).append("'")
					.append(",")
					.append("'").append(DICTIONARIES.DAILY_UV).append("'")
					.append(",")
					.append("'").append(DICTIONARIES.DAILY_UV_DATIO).append("'").toString()
					);
			List<DictionariesBean> dictList=(List<DictionariesBean>) screenSqlMapper.getDictValuesByNames(bean);
			
			
	    	//每日发稿量
	    	String dailyDispatch="0";
	    	//每日发稿量打分占比
	    	String dailyDispatchRatio="0";
	    	//每日pv
	    	String dailyPv="0";
	    	//每日pv打分占比
	    	String dailyPvRatio="0";
	    	//每日uv
	    	String dailyUv="0";
	    	//每日uv打分占比
	    	String dailyUvDatio="0";
	    	dailyDispatch=screenSqlMapper.getDictValuesByName(DICTIONARIES.DAILY_DISPATCH);
	    	if(!isNullList(dictList)){
	    		for(DictionariesBean tmpBean:dictList){
	    			String name=tmpBean.getDictionariesName();
	    			String value=tmpBean.getDictionariesValue();
	    			
	    			if(DICTIONARIES.DAILY_DISPATCH.equals(name)){
	    				dailyDispatch=value;
	    			}else if(DICTIONARIES.DAILY_DISPATCH_RATIO.equals(name)){
	    				dailyDispatchRatio=value;
	    			}else if(DICTIONARIES.DAILY_PV.equals(name)){
	    				dailyPv=value;
	    			}else if(DICTIONARIES.DAILY_PV_RATIO.equals(name)){
	    				dailyPvRatio=value;
	    			}else if(DICTIONARIES.DAILY_UV.equals(name)){
	    				dailyUv=value;
	    			}else if(DICTIONARIES.DAILY_UV_DATIO.equals(name)){
	    				dailyUvDatio=value;
	    			}
	    		}
	    	}
	    	
	    	
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(int i=0;i<dataList.size();i++){
					Map<String,String> tmpMap=dataList.get(i);
					//实际发稿量
					Object count=tmpMap.get("count");
					String headImgUrl=tmpMap.get("headImgUrl");
					
					String qk_account_name=(String) tmpMap.get("qk_account_name");
					//实际pv
					Object pvCount=tmpMap.get("pv_count");
					//实际uv
					Object uvCount=tmpMap.get("uv_count");
					
					logger.info("【生产力统计】dailyDispatch="+dailyDispatch+",dailyPv="+dailyPv+",dailyUv="+dailyUv);
					//实际发稿量/规定发稿量 *打分占比
					BigDecimal dailyResult = div(String.valueOf(count),dailyDispatch,dailyDispatchRatio,0);
					//实际PV/规定PV*打分占比
		    		BigDecimal pvResult =div(String.valueOf(pvCount),dailyPv,dailyPvRatio,0);  
		    		//实际UV/规定UV*打分占比
		    		BigDecimal uvResult =div(String.valueOf(uvCount),dailyUv,dailyUvDatio,0);  
		    		
		    		
		    		//（实际发稿量/规定发稿量 *打分占比+实际PV/规定PV*打分占比+实际UV/规定UV*打分占比）*100分
		    		int days= DateUtil.daysBetween(baseEntity.getBeginDate(),baseEntity.getEndDate());
		    		String result = dailyResult.add(pvResult).add(uvResult).multiply(new BigDecimal("100")).divide(new BigDecimal(String.valueOf(days)),0,BigDecimal.ROUND_HALF_UP).toString();
					
		    		Map<String,Object> valueMap=new HashMap<String,Object>();
		    		valueMap.put("count",count);
		    		valueMap.put("qk_account_name", qk_account_name);
		    		valueMap.put("pv", pvCount);
		    		valueMap.put("uv", uvCount);
		    		valueMap.put("score", result);
		    		valueMap.put("headImgUrl", headImgUrl);
		    		resultList.add(valueMap);
				}
			}
			
			sengMseeageFlag=sendMessage(MODULE.SCLTJ,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.YXLTJ.equals(screenBean.getShowTypeName())) {//影响力统计
			EffectStatisticsBean effectStatisticsBean=new EffectStatisticsBean();
			List<EffectStatisticsBean> effectStatisticsList=(List<EffectStatisticsBean>) screenSqlMapper.getEffectStatistics(effectStatisticsBean);
			
			//排序
			Collections.sort(effectStatisticsList);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if (!isNullList(effectStatisticsList)) {
				for(EffectStatisticsBean tempBean:effectStatisticsList){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("createTime",tempBean.getCreateTime());
					map.put("effectStatisticsKey",tempBean.getEffectStatisticsKey());
					map.put("pvCount",tempBean.getPvCount());
					map.put("statisticsTime",tempBean.getStatisticsTime());
					map.put("uvCount",tempBean.getUvCount());
					resultList.add(map);
				}
			}
			
			
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			outMap.put("type", MODULE.YXLTJ);
			outMap.put("action", ACTION.DETAIL);
			outMap.put("msg", MSG_STATUS.SUCCESS);
			outMap.put("dataType","dataList");
	    	
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("totalCount",pageContext.getTotalRows());
			innerMap.put("list", resultList);
			outMap.put("body", innerMap);
	    	TextMessage listMessage = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	         
	    	
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessage
		         		);
			
			
			//总pv,uv,注册数和注册数是否展示
			DictionariesBean dictionariesBean=new DictionariesBean();
			dictionariesBean.setDictionariesName("'pv_count','uv_count','register','showRegisterStatus'");
			List<DictionariesBean> sumCountList=(List<DictionariesBean>) screenSqlMapper.getDictValuesByNames(dictionariesBean);
			
			List<Map<String,Object>> sumCountListNew=new ArrayList<Map<String,Object>>();
			if (!isNullList(sumCountList)) {
				for(DictionariesBean tempBean:sumCountList){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("createTime",tempBean.getCreateTime());
					map.put("dictionariesKey",tempBean.getDictionariesKey());
					map.put("dictionariesName",tempBean.getDictionariesName());
					map.put("dictionariesValue",tempBean.getDictionariesValue());
					sumCountListNew.add(map);
				}
			}
			
			
		
			Map<String,Object> outMap2 = new HashMap<String,Object>();
			outMap2.put("code", CODE.SUCCESS);
			outMap2.put("type", MODULE.YXLTJ);
			outMap2.put("action", ACTION.DETAIL);
			outMap2.put("msg", MSG_STATUS.SUCCESS);
			outMap2.put("dataType","dataCount");
	    	
			Map<String,Object> innerMap2 = new HashMap<String,Object>();
			innerMap2.put("totalCount",pageContext.getTotalRows());
			innerMap2.put("list", sumCountListNew);
			outMap2.put("body", innerMap2);
	    	TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap2).toString());
	         
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
	        
		}else if (MODULE.RWTJ.equals(screenBean.getShowTypeName())) {//任务统计
			BaseEntity baseEntity=DateUtil.getBeginEndDate(screenBean.getTimeType(),MODULE.RWTJ);
			
			paraMap.put("beginDate", baseEntity.getBeginDate());
			paraMap.put("endDate", baseEntity.getEndDate());
			List<DistributionTaskBean> resultList=(List<DistributionTaskBean>) screenSqlMapper.getTaskStatistics(paraMap);
			sengMseeageFlag=sendMessage(MODULE.RWTJ,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.ZXRB.equals(screenBean.getShowTypeName())) {//资讯热榜
			paraMap.put("imgDomain",PropertiesUtil.getValue("imgIP"));
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) screenSqlMapper.getInformationHot(paraMap);
			sengMseeageFlag=sendMessage(MODULE.ZXRB,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.SPRB.equals(screenBean.getShowTypeName())) {//视频热榜
			paraMap.put("imgDomain",PropertiesUtil.getValue("imgIP"));
			List<Map<String,Object>> resultList=(List<Map<String,Object>>) screenSqlMapper.getVideoHot(paraMap);
			
			
			//金山播放规则，临时修改  20181107
			//栗超
			//http://db.qingk.cn:80/vod/online/ceshizu/420f13b7a534aa524164b45fcf6dfdbc.mp4/playlist.m3u8
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			if (!isNullList(resultList)) {
				for(Map map:resultList) {
					String SDUrl=(String) map.get("SDUrl");
					
					if (SDUrl!=null&&!"".equals(SDUrl)) {
						SDUrl=SDUrl+"?pno="+SDUrl.substring(33,SDUrl.length()-51);
					}
					
					map.put("SDUrl",SDUrl);
					dataList.add(map);
				}
			}
			
			
			sengMseeageFlag=sendMessage(MODULE.SPRB,dataList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.TBZDY.equals(screenBean.getShowTypeName())) {//图表自定义
			pageContext.setPaginationController(false);
			
			ChartSettingBean chartSettingBean=new ChartSettingBean();
			chartSettingBean.setChartSettingKey(screenBean.getUserDefinedKey());
			chartSettingBean=screenSqlMapper.getChartSetting(chartSettingBean);
			
			
			String chart_setting_type=chartSettingBean.getChartSettingType();//如果不显示为空
			 //组织返回字符串
			Map<String,Object> jsonObj = new HashMap<String,Object>();
			if ("0".equals(chart_setting_type)){//饼状图
				  ChartSettingBean paraBean=new ChartSettingBean();
				  paraBean.setChartSettingKey(screenBean.getUserDefinedKey());
				  List<ChartSettingBean> resultList=(List<ChartSettingBean>) screenSqlMapper.getChartCakeDataSetting(paraBean);
				  
				  
				  Map<String,Object> map=new HashMap<String,Object>();
				  List<String> labelList=new ArrayList<String>();
				  List<String> valueList=new ArrayList<String>();
				  if (!isNullList(resultList)) {
					 for(ChartSettingBean tempBean:resultList){
						 String label=tempBean.getChartDataSettingName();
						 String value=tempBean.getChartDataSettingValue();
						 
						 labelList.add(label);
						 valueList.add(value);
					 }
				  }
				  map.put("labels",labelList);
				  map.put("values",valueList);
				  map.put("name","");
				  List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				  list.add(map);
				  
				  
			   
		    	jsonObj.put("code",CODE.SUCCESS);
		    	jsonObj.put("type",MODULE.TBZDY);
		    	jsonObj.put("action",ACTION.DETAIL);
		    	jsonObj.put("msg",MSG_STATUS.SUCCESS);
		    	jsonObj.put("chartType",chart_setting_type);
		    	
		    	
		    	Map<String,Object> contentObj = new HashMap<String,Object>();
		    	if (labelList!=null&&labelList.size()>0) {
		    		contentObj.put("totalCount",labelList.size());
				}else{
					contentObj.put("totalCount",0);
				}
		    	
		    	contentObj.put("list",list);
		    	jsonObj.put("body",contentObj);
			}else if ("1".equals(chart_setting_type)||"2".equals(chart_setting_type)) {//折线图和柱状图
				ChartSettingBean paraBean=new ChartSettingBean();
				paraBean.setChartSettingKey(screenBean.getUserDefinedKey());
				List<ChartSettingBean> resultList=(List<ChartSettingBean>) screenSqlMapper.getChartDataSetting(paraBean);
				
				
				 Map<String,Object> map=new HashMap<String,Object>();
				 Map<String,Object> map2=new HashMap<String,Object>();
				 
				 List<String> labelList=new ArrayList<String>();
				 List<String> valueList=new ArrayList<String>();
				 
				 List<String> labelList2=new ArrayList<String>();
				 List<String> valueList2=new ArrayList<String>();
				if (!isNullList(resultList)) {
					for(ChartSettingBean tempBean:resultList){
						 Integer cloumnOrderNum=tempBean.getCloumnOrderNum();
						 String  columnName=tempBean.getColumnName();
						 Integer axisNum=tempBean.getAxisNum();
						 String  chart_data_setting_name=tempBean.getChartDataSettingName();
						 String  chart_data_setting_value=tempBean.getChartDataSettingValue();
						 
						 if (cloumnOrderNum==1){//目前只有两个
							 map.put("name",columnName);
							 labelList.add(chart_data_setting_name);
							 valueList.add(chart_data_setting_value);
						 }else if (cloumnOrderNum==2) {
							 map2.put("name",columnName);
							 labelList2.add(chart_data_setting_name);
							 valueList2.add(chart_data_setting_value);
						}
					}
				}
				
				if (labelList!=null&&labelList.size()>0) {
					map.put("labels", labelList);
					map.put("values", valueList);
				}
				
				if (labelList2!=null&&labelList2.size()>0) {
					map2.put("labels", labelList2);
					map2.put("values", valueList2);
				}
				
				
				/**
				 * 
				 * JSON串要求格式:
				
				    {
					    "code": 200,
					    "results": {
					      "ret": 100,
					      "msg": "成功",
					      "totalCount": 10（横坐标数据长度）,
					      "chartType": '自定图标类型（0：饼状图；1：折线图；2：柱状图）'，
					      "list": [{
					            "name": '纵坐标名称1（饼图为空即可）'
					            "labels": [横坐标1， 横坐标2， 横坐标3， ……]
					            "values": [12， 23， 34， ……],
					            },
					            {
					            "name": '纵坐标名称2（饼图为空即可）'
					            "labels": [横坐标1， 横坐标2， 横坐标3， ……]
					            "values": [122， 232， 342， ……],
					            }
					            ]
					    }
					  }
				 */
				
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list.add(map);
				list.add(map2);
				  
		    	jsonObj.put("code",CODE.SUCCESS);
		    	jsonObj.put("type",MODULE.TBZDY);
		    	jsonObj.put("action",ACTION.DETAIL);
		    	jsonObj.put("msg",MSG_STATUS.SUCCESS);
		    	jsonObj.put("chartType",chart_setting_type);
		    	
		    	
		    	Map<String,Object>  contentObj = new HashMap<String,Object>();
		    	if (!isNullList(labelList)) {
		    		contentObj.put("totalCount",labelList.size());
				}else{
					contentObj.put("totalCount",0);
				}
		    	
		    	contentObj.put("list",list);
		    	jsonObj.put("body",contentObj);
			}
			
			
	    	TextMessage listMessage = new TextMessage(net.sf.json.JSONObject.fromObject(jsonObj).toString());
	    	
	    	//向所有打开pageFlage（P1,P2）的浏览器发送消息
	    	sengMseeageFlag= this.handler.sendMessageToPage(
	        		screenBean.getScreenNum(), 
	        		ACTION.DETAIL,
	        		listMessage
	        		);
		}else if (MODULE.LBZDY.equals(screenBean.getShowTypeName())) {//列表自定义
			pageContext.setPaginationController(false);
			
			ListSettingBean listSettingBean=new ListSettingBean();
			listSettingBean.setListSettingKey(screenBean.getUserDefinedKey());
			listSettingBean=screenSqlMapper.getListSetting(listSettingBean);
			
			String listSettingType=listSettingBean.getListSettingType();
			String showStatus=listSettingBean.getShowStatus();
			logger.info("【自定义列表】列表类型(0:单列多行;1:两列多行;2:三列多行;):"+listSettingType+",是否显示:"+showStatus);
			
			
			List<Map<String,String>> resultList=new ArrayList<Map<String,String>>();
			String coloumOneName="";
			String coloumTwoName="";
			String coloumThrName="";
			
			if (SHOW_STATUS.SHOW.equals(showStatus)) {//显示
				if ("0".equals(listSettingType)) {//单列多行
					List<ListSettingBean> listRowSettingList=(List<ListSettingBean>) screenSqlMapper.getListRowSetting(listSettingBean);
					
					if (!isNullList(listRowSettingList)) {
						for(ListSettingBean tempBean:listRowSettingList){
							Map<String,String> map=new HashMap<String,String>();
							map.put("coloumOneData",tempBean.getListRowSettingValue());
							map.put("coloumTwoData","");
							map.put("coloumThrData","");
							resultList.add(map);
						}
					}
				}else{
					List<ListSettingBean> listSettingList=(List<ListSettingBean>) screenSqlMapper.getListColumnSetting(listSettingBean);
					Integer count=1;
					
					String list_column_setting_key="";
					String list_column_setting_key2="";
					String list_column_setting_key3="";
					Integer listSettingSize=0;
					if (!isNullList(listSettingList)) {
						listSettingSize=listSettingList.size();
						
						for(ListSettingBean tempBean:listSettingList){
							if (count==1) {
								coloumOneName=tempBean.getListColumnSettingName();
								list_column_setting_key=tempBean.getListColumnSettingKey();
							}else if (count==2) {
								coloumTwoName=tempBean.getListColumnSettingName();
								list_column_setting_key2=tempBean.getListColumnSettingKey();
							}else if (count==3) {
								coloumThrName=tempBean.getListColumnSettingName();
								list_column_setting_key3=tempBean.getListColumnSettingKey();
							}
							
							count++;
						}
					}
					
					ListSettingBean paraBean=new ListSettingBean();
					paraBean.setListSettingKey(screenBean.getUserDefinedKey());
					List<ListSettingBean> resultRowSizeList=(List<ListSettingBean>) screenSqlMapper.getListRowSize(paraBean);
					Integer resultRowSize=0;
					if (resultRowSizeList!=null) {
						resultRowSize=resultRowSizeList.size();
					}
					
					String  coloumOneData="";
					String  coloumTwoData="";
					String  coloumThrData="";
					if (!isNullList(listSettingList)){
						Integer whileCount=resultRowSize/listSettingSize;
						logger.info("resultRowSize="+resultRowSize);
						logger.info("listSettingSize="+listSettingSize);
						logger.info("whileCount="+whileCount);
						
						for(Integer tempCount=1;tempCount<=whileCount;tempCount++){
							ListSettingBean tempBean=new ListSettingBean();
							tempBean.setCountNum(tempCount);
							tempBean.setListSettingKey(screenBean.getUserDefinedKey());
							List<ListSettingBean> resultRowList=(List<ListSettingBean>) screenSqlMapper.getListRowSetting(tempBean);
							
							
							if (!isNullList(resultRowList)) {
								Map<String,String> map=new HashMap<String,String>();
								for(ListSettingBean tempBean2:resultRowList){
									String list_column_setting_key_row=tempBean2.getListColumnSettingKey();
									
									if (list_column_setting_key_row.equals(list_column_setting_key)) {
										coloumOneData=tempBean2.getListRowSettingValue();
										map.put("coloumOneData", coloumOneData);
									}else if (list_column_setting_key_row.equals(list_column_setting_key2)) {
										coloumTwoData=tempBean2.getListRowSettingValue();
										map.put("coloumTwoData", coloumTwoData);
									}else if (list_column_setting_key_row.equals(list_column_setting_key3)) {
										coloumThrData=tempBean2.getListRowSettingValue();
										map.put("coloumThrData", coloumThrData);
									}
								}
								
								map.put("listSearchKey",tempBean.getListColumnSettingKey());
								resultList.add(map);
								
							}
						}
					}
				}
			}
			
			/**
			 * 
			 * JSON串要求格式:
			
			{
			    "code": 200,
			    "results": {
			      "ret": 100,
			      "msg": "成功",
			      "totalCount": 10,
			      "listType":'3',
			      "coloumOneName":'第一列名字',
			      "coloumTwoName":'第二列名字',
			      "coloumThrName":'第三列名字',
			      "list": [{
			            "listSearchKey":'aoduawtvvuqcspcsbdcupprxxpprqbcu'，
			            "coloumOneData":'我是列表标题',
			            "coloumTwoData":'10102',
			            "coloumThrData": '10102'
			            },
			            ...
			            ]
			    }
			  }
			 */
			
			
			Map<String,Object>  jsonObj = new HashMap<String,Object>();
	    	jsonObj.put("code",CODE.SUCCESS);
	    	jsonObj.put("type",MODULE.LBZDY);
	    	jsonObj.put("action",ACTION.DETAIL);
	    	jsonObj.put("msg",MSG_STATUS.SUCCESS);
	    	
	    	jsonObj.put("listType",listSettingType);
	    	jsonObj.put("coloumOneName",coloumOneName);
	    	jsonObj.put("coloumTwoName",coloumTwoName);
	    	jsonObj.put("coloumThrName",coloumThrName);
	    	
	    	Map<String,Object>  contentObj = new HashMap<String,Object>();
	    	if (resultList!=null&&resultList.size()>0) {
	    		contentObj.put("totalCount",resultList.size());
			}else{
				contentObj.put("totalCount",0);
			}
	    	contentObj.put("list",resultList);
	    	jsonObj.put("body",contentObj);
	    	
	    	TextMessage listMessage = new TextMessage(net.sf.json.JSONObject.fromObject(jsonObj).toString());

	    	
	    	//向所有打开pageFlage（P1,P2）的浏览器发送消息
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		screenBean.getScreenNum(), 
	        		ACTION.DETAIL,
	        		listMessage
	        		);
		}else if (MODULE.CXZX.equals(screenBean.getShowTypeName())){//传习中心
			 paraMap.put("imgDomain",PropertiesUtil.getValue("imgIP"));
			 List<Map<String,Object>> resultList=(List<Map<String, Object>>) screenSqlMapper.getCenterList(paraMap);
			 sengMseeageFlag=sendMessage(MODULE.CXZX,resultList,pageContext,screenBean.getScreenNum());
		}else if (MODULE.WXJZ.equals(screenBean.getShowTypeName())){//V2.0 微信矩阵
			 Integer publishTimes=screenSqlMapper.publishTotalCount("indexPage");
			 Integer wechatAccount=screenSqlMapper.wechatTotalCount();
			 Integer publishManuscript=screenSqlMapper.publishManuTotalCount("indexPage");
			 Integer examineManuscript=screenSqlMapper.examineManuTotalCount("indexPage");
			 
			 ScreenBean WXJZBean=new ScreenBean();
			 List<Map<String,Object>> resultList=(List<Map<String, Object>>) screenSqlMapper.publishManuWXJZ(WXJZBean);
			
			 
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			outMap.put("type", MODULE.WXJZ);
			outMap.put("action",ACTION.DETAIL);
			outMap.put("msg",  MSG_STATUS.SUCCESS);
	    	
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("publishTimes", 		publishTimes);
			innerMap.put("wechatAccount", 		wechatAccount);
			innerMap.put("publishManuscript", 	publishManuscript);
			innerMap.put("examineManuscript", 	examineManuscript);
			innerMap.put("list", resultList);
			outMap.put("body", innerMap);
			
			logger.info("【微信矩阵】map="+outMap);
			
	    	TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
		}else if (MODULE.XCZB.equals(screenBean.getShowTypeName())){//V3.0  现场直播
			pageContext.setPaginationService(false);
			String defaultLiveStream=screenSqlMapper.getDictValuesByName(DICTIONARIES.DEFAULT_LIVE_STREAM);
			String customLiveStream=screenSqlMapper.getDictValuesByName(DICTIONARIES.CUSTOME_LIVE_STREAM);
			String showLiveStream=screenSqlMapper.getDictValuesByName(DICTIONARIES.SHOW_LIVE_STREAM);
			
			if("customLiveStream".equals(showLiveStream)){
				defaultLiveStream=customLiveStream;
			}
			
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			outMap.put("type", MODULE.XCZB);
			outMap.put("action",ACTION.DETAIL);
			outMap.put("msg",  MSG_STATUS.SUCCESS);
			
            logger.info("【现场直播】defaultLiveStream="+defaultLiveStream);
            
			if (defaultLiveStream!=null && defaultLiveStream.indexOf(PropertiesUtil.getValue("qn_cdn_domain")) >= 0) {
					String key = PropertiesUtil.getValue("cdn_key");
					String tmp[] = defaultLiveStream.split(PropertiesUtil.getValue("qn_cdn_domain"));
					if (tmp.length > 1) {
						String channel = tmp[1];
						long l = System.currentTimeMillis() / 1000
								+ Integer.parseInt(PropertiesUtil.getValue("qn_cdn_time"));
						String t = Long.toHexString(l).toLowerCase();
						String sign = MD5.getMD5String(key + channel + t).toLowerCase();
						defaultLiveStream = defaultLiveStream + "?sign=" + sign + "&t=" + t;
						logger.info("defaultLiveStream=" + defaultLiveStream);
					}
			} else if (defaultLiveStream!=null && defaultLiveStream.indexOf(PropertiesUtil.getValue("qn24_cdn_domain")) >= 0) {
				String key = PropertiesUtil.getValue("cdn_key");
				String tmp[] = defaultLiveStream.split(PropertiesUtil.getValue("qn24_cdn_domain"));
				if (tmp.length > 1) {
					String channel = tmp[1];
					long l = System.currentTimeMillis() / 1000
							+ Integer.parseInt(PropertiesUtil.getValue("qn_cdn_time"));
					String t = Long.toHexString(l).toLowerCase();
					String sign = MD5.getMD5String(key + channel + t).toLowerCase();
					defaultLiveStream = defaultLiveStream + "?sign=" + sign + "&t=" + t;
					logger.info("defaultLiveStream=" + defaultLiveStream);
				}
			}
			
			//网宿的直播流处理
			else if(defaultLiveStream!=null&&defaultLiveStream.indexOf(PropertiesUtil.getValue("cdn_domain")) >= 0){
			    long l = (new Date().getTime()/1000);
			    String uri = defaultLiveStream.split(PropertiesUtil.getValue("cdn_domain"))[1];
			    String cdndes = MD5.getMD5String("mbpallook"+uri+l);
			    defaultLiveStream = defaultLiveStream + "?token=" + cdndes + "&time=" + l;
			// 金山的直播流处理
			} else if(defaultLiveStream!=null&&defaultLiveStream.indexOf(PropertiesUtil.getValue("js_cdn_domain")) >= 0){
			    String key = PropertiesUtil.getValue("cdn_key");
			    String tmp[] = defaultLiveStream.split(PropertiesUtil.getValue("js_cdn_domain"));
			    if(tmp.length > 1){
				String channel = tmp[1];
				String t = (System.currentTimeMillis()/1000+Integer.parseInt(PropertiesUtil.getValue("js_cdn_time")))+"";
				String sign = MD5.getMD5String(key+channel+t);
				sign = sign.substring(8, 24);
				defaultLiveStream = defaultLiveStream+"?k="+sign+"&t="+t;
				logger.info("defaultLiveStream=" + defaultLiveStream);
			    }
			//七牛的直播流加密
			} else if(defaultLiveStream!=null&& defaultLiveStream.indexOf(PropertiesUtil.getValue("qn_cdn_domain")) >= 0){
			    String key = PropertiesUtil.getValue("cdn_key");
			    String tmp[] = defaultLiveStream.split(PropertiesUtil.getValue("qn_cdn_domain"));
			    if(tmp.length > 1){
					String channel = tmp[1];
					long l = System.currentTimeMillis()/1000+Integer.parseInt(PropertiesUtil.getValue("qn_cdn_time"));
					String t = Long.toHexString(l).toLowerCase();
					String sign = MD5.getMD5String(key+channel+t).toLowerCase();
					defaultLiveStream = defaultLiveStream+"?sign="+sign+"&t="+t;
					logger.info("defaultLiveStream=" + defaultLiveStream);
			    }
			} else if(defaultLiveStream!=null&& defaultLiveStream.indexOf(PropertiesUtil.getValue("qn24_cdn_domain")) >= 0){
			    String key = PropertiesUtil.getValue("cdn_key");
			    String tmp[] = defaultLiveStream.split(PropertiesUtil.getValue("qn24_cdn_domain"));
			    if(tmp.length > 1){
					String channel = tmp[1];
					long l = System.currentTimeMillis()/1000+Integer.parseInt(PropertiesUtil.getValue("qn_cdn_time"));
					String t = Long.toHexString(l).toLowerCase();
					String sign = MD5.getMD5String(key+channel+t).toLowerCase();
					defaultLiveStream = defaultLiveStream+"?sign="+sign+"&t="+t;
					logger.info("defaultLiveStream=" + defaultLiveStream);
			    }
			}
//			}else if(defaultLiveStream!=null&&defaultLiveStream.indexOf(PropertiesUtil.getValue("qingk_cdn_domain")) >= 0){
//			    url = QingkLiveUrl.getLiveUrl(bean.getQuery_udid(),defaultLiveStream);
//			    mp.setSDUrl(defaultLiveStream);
//			} else if(defaultLiveStream!=null&&defaultLiveStream.indexOf("zb.allook.cn") >= 0){
//			    //第一视听直播流加密
//			    try {
//				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//				String operateId = df.format(new Date())+(int)(Math.random()*10);
//				String des = DESN1.toHexString(DESN1.encrypt(String.valueOf(System.currentTimeMillis())))+"@" + operateId + "@14@" + PkCreat.getTablePk();
//				//10期修改，加密串增加终端和版本信息，日志监控系统使用
//				des += "@wap@1.0";
//				String tmp[] = defaultLiveStream.split("/");
//				if(tmp.length>=5 && (tmp[3].equals("live") || tmp[3].equals("qxzb"))){
//				    des = des + "@" + tmp[4];
//				}
//				defaultLiveStream = defaultLiveStream + "?" + DESN1.toHexString(DESN1.encrypt(des));
//				logger.info("defaultLiveStream=" + defaultLiveStream);
//			    } catch (Exception e) {
//				e.printStackTrace();
//					logger.error("第一视听直播流加密出错："+e.getMessage());
//			    }
//			}
			
			
			
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("playUrl",defaultLiveStream);
			outMap.put("body", innerMap);
			
			logger.info("【现场直播】map="+outMap);
			
			TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
		}else if (MODULE.KQGL.equals(screenBean.getShowTypeName())){//V3.0 考勤管理 
			PunchBean bean=new PunchBean();
			bean.setPunchDate(DateUtil.getTody());
			bean.setImgDomain(PropertiesUtil.getValue("imgIp"));
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) screenSqlMapper.getKaoQinList(bean);
			
			logger.info("【考勤管理】dataList="+dataList);
			
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(int i=0;i<dataList.size();i++){
					Map<String,Object> tmpMap=dataList.get(i);
					Object isLateO=tmpMap.get("isLate");
					Integer isLate=99;
					if(isLateO!=null&&!"".equals(isLateO)){
						isLate=(Integer) tmpMap.get("isLate");
					}
					
					Object workOnIsFarO=tmpMap.get("workOnIsFar");
					Integer workOnIsFar=99;
					if(workOnIsFarO!=null&&!"".equals(workOnIsFarO)){
						workOnIsFar=(Integer)tmpMap.get("workOnIsFar");
					}
					
					Object isEarlyO= tmpMap.get("isEarly");
					Integer isEarly=99;
					if(isEarlyO!=null&&!"".equals(isEarlyO)){
						isEarly=(Integer) tmpMap.get("isEarly");
					}
					
					Object workOffIsFarO= tmpMap.get("workOffIsFar");
					Integer workOffIsFar=99;
					if(workOffIsFarO!=null&&!"".equals(workOffIsFarO)){
						workOffIsFar=(Integer) tmpMap.get("workOffIsFar");
					}
					
					logger.info("【考勤管理】isLate="+isLate+",isEarly="+isEarly+",workOnIsFar="+workOnIsFar+",workOffIsFar="+workOffIsFar);
					
					String startStatus = "-1";
					if(isLate==0 && workOnIsFar==0){
						startStatus = "0";
					}
					if(isLate==1 && workOnIsFar==0){
						startStatus = "1";
					}
					if(isLate==0 && workOnIsFar==1){
						startStatus = "2";
					}
					if(isLate==1 && workOnIsFar==1){
						startStatus = "3";
					}
					tmpMap.put("startStatus", startStatus);
					
					
					String endStatus = "-1";
					if(isEarly==0 && workOffIsFar==0){
						endStatus = "0";
					}
					if(isEarly==1 && workOffIsFar==0){
						endStatus = "1";
					}
					if(isEarly==0 && workOffIsFar==1){
						endStatus = "2";
					}
					if(isEarly==1 && workOffIsFar==1){
						endStatus = "3";
					}
					tmpMap.put("endStatus", endStatus);
					
					resultList.add(tmpMap);
				}
			}
			
			
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			outMap.put("type", MODULE.KQGL);
			outMap.put("action",ACTION.DETAIL);
			outMap.put("msg",  MSG_STATUS.SUCCESS);
			

			//Map<String,Object> innerMap = new HashMap<String,Object>();
			outMap.put("list", resultList);
			
			logger.info("【现场直播】map="+outMap);
			
			TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
	    	
		}else if (MODULE.LDGL.equals(screenBean.getShowTypeName())){//V3.0 通讯联动
			/**
			* 
			*     {
			*		"code": 200,
			*		"results": {
			*			"ret": 100,
			*			"msg": "成功",
			*			"totalCount": 10,
			*			"list": [{
			*				"subject": {
			*					"subjectKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*					"subjectTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0",
			*					"manuscriptCount": "1"
			*				},
			*				"checkFlag": false,
			*				"reportList": [{
			*						"reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*						"reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0",
			*						"publishTime": "2018-04-03 09:21:34",
			*						"reportor": "pony",
			*						"checkFlag": false
			*					},
			*					{
			*						"reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*						"reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-1",
			*						"publishTime": "2018-04-03 09:21:34",
			*						"reportor": "pony",
			*						"checkFlag": false
			*					}
			*				]
			*			}
			*           ...
			*    ]
			*		}
			*	}
			*  
			* 
			* 
			* 修改为:
			*     {
			*		    "code": 200,
			*		    "results": {
			*		        "ret": 100,
			*		        "msg": "成功",
			*		        "totalCount": 10,
			*		        "list": [{
			*		            "subjectKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*		            "subjectTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0",
			*		            "manuscriptCount": "1",
			*		            "checkFlag": false,
			*		            "reportList": [
			*		                {
			*		                    "reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*		                    "reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0",
			*		                    "publishTime": "2018-04-03 09:21:34",
			*		                    "reportor": "pony",
			*		                    "checkFlag": false
			*		                },
			*		                {
			*		                    "reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*		                    "reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-1",
			*		                    "publishTime": "2018-04-03 09:21:34",
			*		                    "reportor": "pony",
			*		                    "checkFlag": false
			*		                },
			*		                ...
			*		            ]
			*		        },
			*		        ...
			*		        ]
			*		    }
			*		}
			*
			*
			*   后修改为：
			*     {
			*	    "code": 200,
			*	    "results": {
			*	        "ret": 100,
			*	        "msg": "成功",
			*	        "totalCount": 10,
			*	        "topicList":[
			*	            {
			*	                "subjectKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*	                "subjectTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0",
			*	                "manuscriptCount": "1",
			*	                "checkFlag": false,
			*	            },
			*	            ...
			*	        ],
			*	        "reportList":[
			*	            {
			*	                "reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*	                "reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0",
			*	                "publishTime": "2018-04-03 09:21:34",
			*	                "reportor": "pony",
			*	                "checkFlag": false
			*	            },
			*	            {
			*	                "reportKey": "aoduawtvvuqcspcsbdcupprxxpprqbcu",
			*	                "reportTitle": "习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0习近平：打好决胜全面建成小康社会三大攻坚战0-0",
			*	                "publishTime": "2018-04-03 09:21:34",
			*	                "reportor": "pony",
			*	                "checkFlag": false
			*	            },
			*	            ...
			*	        ]
			*	    }
			*	}
			*
			*/
			
			ScreenBean bean=new ScreenBean();
			//专题list
			List<Map<String,Object>> topicList=(List<Map<String,Object>>) screenSqlMapper.getLDGLList(bean);
			
			String key="''";
			if(!isNullList(topicList)){
				for(int i=0;i<topicList.size();i++){
					Map<String,Object> tmpMap=topicList.get(i);
					String subjectKey=(String) tmpMap.get("subjectKey");
					key=key+",'"+subjectKey+"'";
				}
			}
			bean.setKey(key);
			logger.info("【通讯联动管理】专题key="+key);
			
			//稿件list
			pageContext.setPaginationController(false);
			pageContext.setPaginationService(false);
			List<Map<String,Object>> reportList=(List<Map<String,Object>>) screenSqlMapper.getSubjectReportList(bean);
			
			logger.info("【通讯联动管理】topicList="+topicList+",reportList="+reportList);
			
			
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("ret",RET.SUCCESS);
			innerMap.put("msg",MSG_STATUS.SUCCESS);
			
			if(!isNullList(topicList)){
				innerMap.put("totalCount",topicList.size());
			}else{
				innerMap.put("totalCount",0);
			}
			innerMap.put("topicList",topicList);
			innerMap.put("reportList",reportList);
			
			
			outMap.put("results", innerMap);
			logger.info("【通讯联动管理】map="+outMap);
			
			TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
	    	
		}else if (MODULE.BLGL.equals(screenBean.getShowTypeName())){//V3.0 爆料管理
			
		  /**
			* JSON格式:
			* 		{
			*			"code": 200,
			*			"results": {
			*				"ret": 100,
			*				"msg": "成功",
			*				"reportList": [{
			*					"reportKey": "报料Key",
			*					"reportTitle": "报料标题",
			*					"createTime": "报料时间",
			*					"status": "报料状态（已审核、待处理、已处理等）",
			*					"reportClassifyName": ""
			*				}],
			*				"classifyList": [{
			*					"reportClassifyKey": "报料分类Key"，
			*					"reportClassifyName": "报料分类名称",
			*					"totalProblem": "问题总量"，
			*					"reversionRate": "回复率 18%"
			*		
			*				}],
			*				"statisticsInfo": {
			*					"expiryDate": '截止日期2019.3.3',
			*					"totalCount": 诉求总量 100,
			*					"resolvedCount": 已解决诉求 20,
			*				}
			*			}
			*		}
			*
			*     
			*     
			*/
			
			PunchBean bean=new PunchBean();
			List<Map<String,Object>> reportList=(List<Map<String,Object>>) screenSqlMapper.getReportList(bean);
			List<Map<String,Object>> classifyTypeList=(List<Map<String,Object>>) screenSqlMapper.getClassifyTypeList(bean);
			List<Map<String,Object>> reportStatusticsList=(List<Map<String,Object>>) screenSqlMapper.getReportStatistics(bean);
			
			logger.info("【爆料管理】reportList="+reportList+",reportStatusticsList="+reportStatusticsList+",classifyTypeList="+classifyTypeList);
			
			
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("code", CODE.SUCCESS);
			
			Map<String,Object> innerMap = new HashMap<String,Object>();
			innerMap.put("ret", RET.SUCCESS);
			innerMap.put("msg", MSG_STATUS.SUCCESS);
			innerMap.put("reportList", reportList);
			
			Map<String,Object> tMap=new HashMap<String,Object>();
			if(!isNullList(reportStatusticsList)){
				tMap=reportStatusticsList.get(0);
			}
			innerMap.put("statisticsInfo", tMap);
			innerMap.put("classifyList", classifyTypeList);
			
			outMap.put("results",innerMap);
			
			logger.info("【爆料管理】map="+outMap);
			
			TextMessage listMessageCount = new TextMessage(net.sf.json.JSONObject.fromObject(outMap).toString());
	    	sengMseeageFlag=this.handler.sendMessageToPage(
	        		 	screenBean.getScreenNum(), 
		         		ACTION.DETAIL,
		         		listMessageCount
		         		);
		}else{
			json=getJson(CODE.SUCCESS,RET.MODULE_ERROR);
			return json;
		}
		
		
		if (sengMseeageFlag) {
			json=getJson(CODE.SUCCESS,RET.SUCCESS);
		}else {
			json=getJson(CODE.SUCCESS,RET.SEND_MESSAGE_ERROR);
		}
		
		return json;
	}
	
	
	  public boolean sendMessage(String module,List<?> resultList,PageContext pageContext,String pageFlag) throws Exception{
	     	TextMessage listMessage = new TextMessage(handler.makeListResponseJson(
	     			CODE.SUCCESS, 
	     			module,
	     			ACTION.DETAIL, 
	     		    MSG_STATUS.SUCCESS, 
	     			pageContext.getTotalRows(),
	     			resultList
	     			)
	     		);
	 	    
	     	//向所有打开pageFlage（P1,P2）的浏览器发送消息
	         boolean sendFlag = this.handler.sendMessageToPage(
	         		pageFlag, 
	         		ACTION.DETAIL,
	         		listMessage
	         		);
	         return sendFlag;
	 	}
	 	
	 	
		@Override
		public List<?> getScreenSetting(ScreenBean bean) throws Exception {
			return screenSqlMapper.getScreenSetting(bean);
		}
		

		@Override
		public List<?> getUserTask(DistributionTaskBean bean) throws Exception {
			return screenSqlMapper.getUserTask(bean);
		}


		@Override
		public boolean isAllowCall() throws Exception {
			boolean resFlag=false;
			
			//判断剩余通话时长
 			DictionariesBean dictBean=new DictionariesBean();
 			dictBean.setDictionariesName("'callTotalTime'");
 			List<DictionariesBean> sumCountList=(List<DictionariesBean>) screenSqlMapper.getDictValuesByNames(dictBean);
			    
 			Integer callTotalTime=0;//单位秒
 			if (sumCountList!=null&&sumCountList.size()>0) {
 				callTotalTime=Integer.parseInt(sumCountList.get(0).getDictionariesValue())*60;
			}
 			
 			ScreenBean screenBean=new ScreenBean();
 			Integer callFinshTime=screenSqlMapper.getSumCallTime(screenBean);//单位秒
 			
 			if (callFinshTime<callTotalTime) {
 				 resFlag=true;
			}
 			logger.info("【是否允许通话isAllowCall】通话总时长:"+callTotalTime+",已经使用时长:"+callFinshTime+",是否可以通话:"+resFlag);
			return resFlag;
		}


		@Override
		public List<?> getDictValuesByNames(DictionariesBean bean) throws Exception {
			return screenSqlMapper.getDictValuesByNames(bean);
		}

		/**
		 * 获取屏幕配置信息
		 * @param screenNum
		 * @return
		 * @throws Exception
		 */
		public ScreenBean getPageFlagSetting(String screenNum) throws Exception{
			ScreenBean screenBean=new ScreenBean();
		    screenBean.setScreenNum(screenNum);
		    List<ScreenBean> screenBeanList=(List<ScreenBean>) screenSqlMapper.getScreenSetting(screenBean);
		    if (!isNullList(screenBeanList)) {
				 screenBean=screenBeanList.get(0);
			}
		    
			return screenBean;
		}


		@Override
		public Map<?,?> getDetailQWRD(String key) throws Exception {
			Map<String,Object>  resultMap=(Map<String, Object>) screenSqlMapper.getDetailQWRD(key);
			String htmlUrl=PropertiesUtil.getValue("imgIP")+(String) resultMap.get("html_url");
			List<String> contentList=AnalyseHtml.getHtmlByRequest(htmlUrl,false,"div[id=informationInfoContainer]"); 
			if (!isNullList(contentList)) {
				resultMap.put("hotspotNewsClueDescript",contentList.get(0));
			}else {
				resultMap.put("hotspotNewsClueDescript",null);
			}
			
			return resultMap;
		}


		@Override
		public Map<?,?> getDetailBDXW(String key) throws Exception {
			Map<String,Object>  resultMap=(Map<String, Object>) screenSqlMapper.getDetailBDXW(key);
			String htmlUrl=PropertiesUtil.getValue("imgIP")+(String) resultMap.get("html_url");
			List<String> contentList=AnalyseHtml.getHtmlByRequest(htmlUrl,false,"div[id=informationInfoContainer]"); 
			if (!isNullList(contentList)) {
				resultMap.put("hotspotNewsClueDescript",contentList.get(0));
			}else {
				resultMap.put("hotspotNewsClueDescript",null);
			}
 			
			return resultMap;
		}


		@Override
		public Map<?,?> getDetailZXRB(String key) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap=(Map<String, Object>) screenSqlMapper.getDetailZXRB(key);
			
			//重新组装静态文件地址
			String informationContent=PropertiesUtil.getValue("staticHtmlIp")+(String) resultMap.get("informationContent");
			resultMap.put("informationContent",informationContent);
			
			return resultMap;
		}


		@Override
		public Map<?,?> getDetailXWXT(String key) throws Exception {
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) screenSqlMapper.getDetailXWXT(key);
			
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			
			String selectedTopicTitle="";
			String selectedTopicDescript="";
			String departmentName="";
			String remark="";
			Integer taskTotalCount=0;
			Integer topicStatus=0;
			if (dataList!=null&&dataList.size()>0) {
				for(int i=0;i<dataList.size();i++) {
					 Map<String,Object> map=dataList.get(i);
					
					 selectedTopicTitle=(String) map.get("selectedTopicTitle");
					 selectedTopicDescript=(String) map.get("selectedTopicDescript");
					 departmentName=(String) map.get("departmentName");
					 remark=(String) map.get("remark");
					 taskTotalCount=(Integer) map.get("taskTotalCount");
					 topicStatus=(Integer) map.get("topicStatus");
					
					
					String taskName=(String) map.get("taskName");
					Integer taskStatus=(Integer) map.get("taskStatus");
					String reporter=(String) map.get("reporter");
					Map<String,Object> tempMap=new HashMap<String,Object>();
					tempMap.put("taskName",taskName);
					tempMap.put("taskStatus",taskStatus);
					tempMap.put("reporter",reporter);
					resultList.add(tempMap);
				}
			}
			
			//组织返回数据
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("selectedTopicTitle", selectedTopicTitle);
			resultMap.put("selectedTopicDescript", selectedTopicDescript);
			resultMap.put("departmentName", departmentName);
			resultMap.put("remark", remark);
			resultMap.put("taskTotalCount", taskTotalCount);
			resultMap.put("topicStatus", topicStatus);
			
			if (resultList!=null&&!resultList.isEmpty()) {
				resultMap.put("list", resultList);
			}else {
				resultMap.put("list",null);
			}
			
			return resultMap;
		}


		@Override
		public Map<?,?> getDetailCXZX(String key) throws Exception {
			Map<String,Object> resultMap=(Map<String, Object>) screenSqlMapper.getDetailCXZX(key);
			
			//重新组装静态文件地址
			String hotspotNewsClueDescript=PropertiesUtil.getValue("staticHtmlIp")
					+(String) resultMap.get("hotspotNewsClueDescript");
			resultMap.put("hotspotNewsClueDescript",hotspotNewsClueDescript);
			return resultMap;
		}


		@Override
		public Map<?, ?> publishCount(ScreenBean screenBean) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			
			//总量
			Integer totalCount=screenSqlMapper.publishTotalCount(null);
			resultMap.put("totalCount",totalCount);
			
			//开始、结束时间
			String timeType=screenSqlMapper.getDictValuesByName(DICTIONARIES.WECHAT_PUBLISH_TIMES);
			BaseEntity baseEntity=DateUtil.getBeginEndDate(timeType,null);
			screenBean.setBeginDate(baseEntity.getBeginDate());
			screenBean.setEndDate(baseEntity.getEndDate());
			
			//返回数据
			List<Map<String,Object> > dataList=(List<Map<String, Object>>) screenSqlMapper.publishCount(screenBean);
			resultMap.put("list",dataList);
			resultMap.put("day", timeType);
			return resultMap;
		}


		@Override
		public Map<?, ?> wechatAccount(ScreenBean screenBean) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			
			//总量
			Integer totalCount=screenSqlMapper.wechatTotalCount();
			resultMap.put("totalCount",totalCount);
			
			//返回数据
			List<Map<String,Object> > dataList=(List<Map<String, Object>>)screenSqlMapper.wechatAccount(screenBean);
			resultMap.put("list",dataList);
			
			return resultMap;
		}


		@Override
		public Map<?, ?> publishManu(ScreenBean screenBean) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			//总量
			Integer totalCount=screenSqlMapper.publishManuTotalCount(null);
			resultMap.put("totalCount",totalCount);
			
			
			//开始、结束时间
			String timeType=screenSqlMapper.getDictValuesByName(DICTIONARIES.WECHAT_PUBLISH_COUNT);
			BaseEntity baseEntity=DateUtil.getBeginEndDate(timeType,null);
			screenBean.setBeginDate(baseEntity.getBeginDate());
			screenBean.setEndDate(baseEntity.getEndDate());
			
			//返回数据
			List<Map<String,Object> > dataList=(List<Map<String, Object>>)screenSqlMapper.publishManu(screenBean);
			resultMap.put("list",dataList);
			resultMap.put("day", timeType);
			return resultMap;
		}
		
		


		@Override
		public Map<?, ?> examineManu(ScreenBean screenBean) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			//总量
			Integer totalCount=screenSqlMapper.examineManuTotalCount(null);
			resultMap.put("totalCount",totalCount);
			
			//开始、结束时间
			String timeType=screenSqlMapper.getDictValuesByName(DICTIONARIES.WECHAT_AUDITED_COUNT);
			BaseEntity baseEntity=DateUtil.getBeginEndDate(timeType,null);
			screenBean.setBeginDate(baseEntity.getBeginDate());
			screenBean.setEndDate(baseEntity.getEndDate());
			
			//返回数据
			List<Map<String,Object> > dataList=(List<Map<String, Object>>) screenSqlMapper.examineManu(screenBean);
			resultMap.put("list",dataList);
			resultMap.put("day", timeType);
			return resultMap;
		}


		@Override
		public Map<?, ?> getDetailDFYL(String key) throws Exception {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			
			BaseEntity baseEntity=new BaseEntity();
			baseEntity.setKey(key);
			String userHeadIconLocalIp=PropertiesUtil.getValue("userHeadIconLocalIp");
			String userHeadIconQingkIp=PropertiesUtil.getValue("userHeadIconQingkIp");
			
			baseEntity.setUserHeadIconQingkIp(userHeadIconQingkIp);
			baseEntity.setUserHeadIconLocalIp(userHeadIconLocalIp);
			
			Map<String,Object>  dataMap=(Map<String, Object>) screenSqlMapper.getDetailDFYL(baseEntity);
			List<Map<String,Object>> imageList=(List<Map<String, Object>>) screenSqlMapper.getImgeList(key);
			
			if (dataMap!=null&&!dataMap.isEmpty()) {
				resultMap.putAll(dataMap);
			}
			
			resultMap.put("list",imageList);
			return resultMap;
		}
		
		
		
		/** 
	     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	     * 定精度，以后的数字四舍五入。 
	     * @param v1 实际发稿量/实际PV/实际UV 
	     * @param v2 规定发稿量/规定PV/规定UV
	     * @param v3 实际发稿量占比/实际pV/规定UV*
	     * @param scale 表示表示需要精确到小数点以后几位。 
	     * @return 两个参数的商 
	     */ 
	    public static BigDecimal div(String v1,String v2,String v3,int scale){
	    	if(org.apache.commons.lang.StringUtils.isEmpty(v1)){
	    		v1="0";
	    	}
	        BigDecimal b1 = new BigDecimal(v1); 
	        if(org.apache.commons.lang.StringUtils.isEmpty(v2)){
	    		v1="0";
	    	}
	        BigDecimal b2 = new BigDecimal(v2); 
	        if(org.apache.commons.lang.StringUtils.isEmpty(v3)){
	    		v1="0";
	    	}
	        BigDecimal b3 = new BigDecimal(v3); 
	        BigDecimal b4 = new BigDecimal("100");
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).multiply(b3).divide(b4,scale,BigDecimal.ROUND_HALF_UP); 
	    }


		@Override
		public Map<?, ?> getDetailBLGL(String key) throws Exception {
			ScreenBean bean=new ScreenBean();
			bean.setKey(key);
			bean.setImgDomain(PropertiesUtil.getValue("reportImgDomain"));
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) screenSqlMapper.getFileByKey(bean);
			
			Map<String,Object> dataMap=new HashMap<String, Object>();
			dataMap=(Map<String, Object>) screenSqlMapper.getDetailBLGL(key);
			dataMap.put("reportImgs",dataList);
			
			return dataMap;
		}


		@Override
		public Map<?, ?> getDetailTXLD(String key) throws Exception {
			return screenSqlMapper.getDetailTXLD(key);
		}


		@Override
		public List<?> getDetailKQGL(PunchBean bean) throws Exception {
			List<Map<String,Object>> dataList=(List<Map<String, Object>>) screenSqlMapper.getKaoQinList(bean);
					
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			if(!isNullList(dataList)){
				for(int i=0;i<dataList.size();i++){
					Map<String,Object> tmpMap=dataList.get(i);
					Object isLateO=tmpMap.get("isLate");
					Integer isLate=99;
					if(isLateO!=null&&!"".equals(isLateO)){
						isLate=(Integer) tmpMap.get("isLate");
					}
					
					Object workOnIsFarO=tmpMap.get("workOnIsFar");
					Integer workOnIsFar=99;
					if(workOnIsFarO!=null&&!"".equals(workOnIsFarO)){
						workOnIsFar=(Integer)tmpMap.get("workOnIsFar");
					}
					
					Object isEarlyO= tmpMap.get("isEarly");
					Integer isEarly=99;
					if(isEarlyO!=null&&!"".equals(isEarlyO)){
						isEarly=(Integer) tmpMap.get("isEarly");
					}
					
					Object workOffIsFarO= tmpMap.get("workOffIsFar");
					Integer workOffIsFar=99;
					if(workOffIsFarO!=null&&!"".equals(workOffIsFarO)){
						workOffIsFar=(Integer) tmpMap.get("workOffIsFar");
					}
					
					logger.info("【考勤管理】isLate="+isLate+",isEarly="+isEarly+",workOnIsFar="+workOnIsFar+",workOffIsFar="+workOffIsFar);
					
					String startStatus = "-1";
					if(isLate==0 && workOnIsFar==0){
						startStatus = "0";
					}
					if(isLate==1 && workOnIsFar==0){
						startStatus = "1";
					}
					if(isLate==0 && workOnIsFar==1){
						startStatus = "2";
					}
					if(isLate==1 && workOnIsFar==1){
						startStatus = "3";
					}
					tmpMap.put("startStatus", startStatus);
					
					
					String endStatus = "-1";
					if(isEarly==0 && workOffIsFar==0){
						endStatus = "0";
					}
					if(isEarly==1 && workOffIsFar==0){
						endStatus = "1";
					}
					if(isEarly==0 && workOffIsFar==1){
						endStatus = "2";
					}
					if(isEarly==1 && workOffIsFar==1){
						endStatus = "3";
					}
					tmpMap.put("endStatus", endStatus);
					
					resultList.add(tmpMap);
				}
			}
			return resultList;
		}
		
		
		
//		public static String getLiveUrl(String udid, String url) {
//			String playUrl = "";
//			try {
//				// 加密密钥
//				String secretKey = PropertiesUtil.getValue("cdn_key");
//
//				// 过期时间(10分钟)
//				String timestamp = String.valueOf(System.currentTimeMillis());
//				timestamp = timestamp.substring(0, timestamp.length() - 3);
//				String expire = String.valueOf(Long.parseLong(timestamp) + Long.parseLong(PropertiesUtil.getValue("qingk_cdn_time")));
//
//				// 获取密钥
//				String md5 = safeUrlBase64Encode(MD5.getMD5String(secretKey + expire + udid));
//				playUrl = url + "?k=" + md5 + "&t=" + expire + "&u=" + udid;
//			} catch (Exception e) {
//			    logger.error(e.getMessage(), e);
//			}
//			
//			return playUrl;
//		}
		
		
//		/**
//		 * base64编码
//		 * @param data
//		 * @return
//		 */
//		private static String safeUrlBase64Encode(byte[] data) {
//			String encodeBase64 = new BASE64Encoder().encode(data);
//			String safeBase64Str = encodeBase64.replace('+', '-');
//			safeBase64Str = safeBase64Str.replace('/', '_');
//			safeBase64Str = safeBase64Str.replaceAll("=", "");
//			return safeBase64Str;
//		}

}
