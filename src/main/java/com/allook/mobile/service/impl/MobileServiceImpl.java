package com.allook.mobile.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.allook.frame.Constants.ACTION;
import com.allook.frame.aspect.annotation.PaginationService;
import com.allook.local.bean.DictionariesBean;
import com.allook.local.bean.SelectedTopicBean;
import com.allook.local.mapper.LocalSqlMapper;
import com.allook.mobile.bean.DepartmentBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.MobileBean;
import com.allook.mobile.bean.NoticeBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.UserBean;
import com.allook.mobile.bean.UserCheckBean;
import com.allook.mobile.mapper.MobileSqlMapper;
import com.allook.mobile.service.MobileService;
import com.allook.screen.mapper.ScreenSqlMapper;
import com.allook.socket.WebScoketHandler;
import com.allook.utils.CheckSumBuilder;
import com.allook.utils.DateUtil;
import com.allook.utils.PkCreat;
import com.allook.utils.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * 轻快发布Service实现类
 * @作者 栗超
 * @时间 2018年5月14日 上午9:13:15
 * @说明
 */
@Service
public class MobileServiceImpl implements MobileService{
	public static final Logger logger = Logger.getLogger(MobileServiceImpl.class);
	@Autowired
    private MobileSqlMapper mobileSqlMapper;
	@Autowired
    private LocalSqlMapper localSqlMapper;
	@Autowired
    private ScreenSqlMapper screenSqlMapper;
	@Autowired
	private WebScoketHandler handler;
    
	@Override
	@PaginationService
	public List<?> callRecords(MobileBean bean) throws Exception {
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		
		List<MobileBean> dataList=(List<MobileBean>) mobileSqlMapper.callRecords(bean);
		if (dataList!=null&&dataList.size()>0) {
			for(MobileBean tempBean:dataList){
				   Map<String,Object> map=new HashMap<String,Object>();
				   
	               String createTime=tempBean.getCreateTime();
	               String duration=tempBean.getRecordLength();
	               
	               map.put("recordImg",tempBean.getRecordImg());
	               map.put("recordName",tempBean.getRecordName());
	               //通话时长
	               map.put("recordLength",DateUtil.second2HourMinSecond(
        				   	Integer.parseInt(duration)/2
        				   ));
	               map.put("recordTime",tempBean.getRecordTime());
	               map.put("recordStatus",tempBean.getRecordStatus());
	               //通话开始时间（月日星期 例：3/27 周二）
	               map.put("recordDate",tempBean.getRecordDate()
	            		   +DateUtil.getWeekOfDate(createTime));
	               
	               resultList.add(map);
			}
		}
		
		return resultList;
	}

	@Override
	public Integer homeNoRead(MobileBean bean) throws Exception {
		return mobileSqlMapper.homeNoRead(bean);
	}

	@Override
	public Integer externalTaskReadJob(DistributionTaskBean bean) throws Exception {
		return mobileSqlMapper.externalTaskReadJob(bean);
	}

	@Override
	public UserBean homeUserDetailInfo(UserBean bean) throws Exception {
		return mobileSqlMapper.homeUserDetailInfo(bean);
	}

	@Override
	@Transactional
	public Integer fileDelete(FileBean bean) throws Exception {
		return mobileSqlMapper.fileDelete(bean);
	}

	@Override
	@Transactional
	public Integer externalTaskModify(DistributionTaskBean bean) throws Exception {
		
		if ("2".equals(bean.getUserTaskStatus())){//撤销中，需要往消息通知表中插入数据，并且通知后台消息提醒数据+1
			 //设置撤销时间
			 bean.setRevokeTime(DateUtil.getTimeToSec()); 
			
			 //获取选题key
			 DistributionTaskBean tempBean=mobileSqlMapper.externalTaskDetail(bean);
			 
			 UserBean userBean=new UserBean();
			 userBean.setUserKey(bean.getUserKey());
			 userBean.setImgDomain(PropertiesUtil.getValue("imgIP"));
			 userBean=localSqlMapper.getUserInfo(userBean);
			 
			 //获取选题名称
			 SelectedTopicBean selectedTopicBean=new SelectedTopicBean();
			 selectedTopicBean.setSelectedTopicKey(tempBean.getSelectedTopicKey());
			 selectedTopicBean=mobileSqlMapper.getSelectedTopic(selectedTopicBean);
			 String noticContent=selectedTopicBean.getSelectedTopicTitile()+","+userBean.getRealName()+"申请撤销"+tempBean.getDistributionTaskTitle();
			 
			 //添加消息提醒内容
			 NoticeBean noticeBean=new NoticeBean();
			 String noticeKey=PkCreat.getTablePk();
			 noticeBean.setNoticeKey(noticeKey);
			 noticeBean.setNoticeContent(noticContent);
			 noticeBean.setDistributionTaskKey(bean.getDistributionTaskKey());
			 noticeBean.setSelectedTopicKey(tempBean.getSelectedTopicKey());
			 noticeBean.setUserKey(bean.getUserKey());
			 noticeBean.setStatus("0");//未读
			 noticeBean.setNoticeType("1");//任务撤销
			 noticeBean.setCreator(bean.getUserKey());
			 noticeBean.setCreateTime(DateUtil.getTimeToSec());
			 mobileSqlMapper.addNotice(noticeBean);
			 
			 NoticeBean noticesBean=new NoticeBean();
			 noticesBean.setNoticeKey(noticeKey);
			 List<Map<String,Object>> userList=(List<Map<String,Object>>) mobileSqlMapper.getAppUserKeys(noticesBean);
			 if (userList!=null) {
				 NoticeBean addNoticeRelationshipBean=new NoticeBean();
				 addNoticeRelationshipBean.setParaList(userList);
				 mobileSqlMapper.addNoticeRelationship(addNoticeRelationshipBean);
			}
			 
			 
			 //通知后台消息提醒 +1
			 Map<String, Object> map = new HashMap<String, Object>();
	    	 map.put("code",200);
	    	 Map<String, Object> map1 = new HashMap<String, Object>();
	    	 map1.put("ret",200);
	    	 map1.put("msg",noticContent);
	    	 map1.put("action","notice");
	    	 map.put("results", map1);
			 TextMessage listMessage = new TextMessage(net.sf.json.JSONObject.fromObject(map).toString());
		     	
	    	 //向所有打开pageFlage的浏览器发送消息
	         boolean sendFlag2 = this.handler.sendMessageToPage(
	        		"qingkintegrate", 
	        		ACTION.LOCAL_NOTICE,
	        		listMessage
	        		);
	         logger.info("sendFlag2:"+sendFlag2);
		}
		
		Integer count=mobileSqlMapper.externalTaskModify(bean);
		return count;
	}

	@Override
	@Transactional
	public Integer fileAdd(FileBean bean) throws Exception {
		//修改任务关系表is_return_content=1
		DistributionTaskBean distributionTaskBean=new DistributionTaskBean();
		distributionTaskBean.setUserKey(bean.getUserKey());
		distributionTaskBean.setDistributionTaskKey(bean.getDistributionTaskKey());
		mobileSqlMapper.updateIsReturnContent(distributionTaskBean);
		
		return mobileSqlMapper.fileAdd(bean);
	}

	@Override
	@Transactional
	public Integer fileUpdate(FileBean bean) throws Exception {
		return mobileSqlMapper.fileUpdate(bean);
	}

	@Override
	public DistributionTaskBean externalTaskDetail(DistributionTaskBean bean) throws Exception {
		return mobileSqlMapper.externalTaskDetail(bean);
	}

	@Override
	@PaginationService
	public List<?> fileList(FileBean bean) throws Exception {
		List<FileBean> list=(List<FileBean>) mobileSqlMapper.fileList(bean);
		
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if (list!=null&&list.size()>0) {
			for(FileBean tempBean:list){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("userFileKey",tempBean.getUserFileKey());
				map.put("distributionTaskKey",tempBean.getDistributionTaskKey());
				map.put("userFileName",tempBean.getUserFileName());
				map.put("imageUrl",tempBean.getImageUrl());
				map.put("fileUrl",tempBean.getFileUrl());
				map.put("fileType",tempBean.getFileType());
				map.put("createTime",tempBean.getCreateTime());
				map.put("size",tempBean.getSize());
				map.put("duration",tempBean.getDuration());
				map.put("remarks",tempBean.getRemarks());
				
				resultList.add(map);
			}
		}
		
		return resultList;
	}

	@Override
	@PaginationService
	public List<?> externalTasLlist(DistributionTaskBean bean) throws Exception {
		List<DistributionTaskBean> dataList=(List<DistributionTaskBean>) mobileSqlMapper.externalTasLlist(bean);
		
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if (dataList!=null&&dataList.size()>0) {
			for(DistributionTaskBean tempBean:dataList){
				Map<String,Object> map=new HashMap<String,Object>(); 
				 String distribution_task_time=tempBean.getDistributionTaskTime();
				 map.put("distributionTaskKey",tempBean.getDistributionTaskKey());
				 map.put("distributionTaskTime",DateUtil.getMMDDHHmm(distribution_task_time));
				 map.put("distributionTaskTitle",tempBean.getDistributionTaskTitle());
				 map.put("userTaskStatus",tempBean.getUserTaskStatus());
				 
				 resultList.add(map);
			}
		}
		
		return resultList;
	}

	@Override
	public UserCheckBean checkUserCheckInfo(UserCheckBean bean) throws Exception {
		return mobileSqlMapper.checkUserCheckInfo(bean);
	}

	@Override
	@PaginationService
	public List<?> checkRankList(UserCheckBean bean) throws Exception {
		List<UserCheckBean> list=(List<UserCheckBean>) mobileSqlMapper.checkRankList(bean);
		
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if (list!=null&&list.size()>0) {
			for(UserCheckBean tempBean:list){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("userKey",tempBean.getUserKey());
				map.put("userName",tempBean.getUserName());
				map.put("headImgUrl",tempBean.getHeadImgUrl());
				map.put("newsCount", tempBean.getNewsCount());
				map.put("pvCount", tempBean.getPvCount());
				map.put("uvCount", tempBean.getUvCount());
				map.put("perviews", tempBean.getPerviews());
				
				resultList.add(map);
			}
		}
		
		return resultList;
	}

	@Override
	@Transactional
	public UserBean updateYxToken(UserBean tmpbean) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = PropertiesUtil.getValue("regWyxxURL");
        logger.info("【网易云账号注册】URL:"+url);
        HttpPost httpPost = new HttpPost(url);

        //获取appSecret和appKey
		DictionariesBean dictionariesBean=new DictionariesBean();
		dictionariesBean.setDictionariesName("'appKey','appSecret'");
		List<DictionariesBean> dictionariesList=(List<DictionariesBean>) screenSqlMapper.getDictValuesByNames(dictionariesBean);
		String appKey="";
		String appSecret="";
		if (dictionariesList!=null&&dictionariesList.size()>0) {
			for(DictionariesBean tempBean:dictionariesList){
				String dictionaries_name=tempBean.getDictionariesName();
				String dictionaries_value=tempBean.getDictionariesValue();
				
				if ("appSecret".equals(dictionaries_name)) {
					 appSecret=dictionaries_value;
				}else if ("appKey".equals(dictionaries_name)) {
					 appKey=dictionaries_value;
				}
			}
		}
		
        logger.info("【网易云账号注册】appKey="+appKey
        		+",appSecret="+appSecret);
        
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime()/1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret,nonce,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", tmpbean.getUserKey()));
        nvps.add(new BasicNameValuePair("name", tmpbean.getUserName()));
        nvps.add(new BasicNameValuePair("icon", tmpbean.getHeadImgUrl()));
    
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	    HttpResponse httpResponse = httpClient.execute(httpPost);
	    String responseString = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
	    logger.info("【网易云账号注册】"+responseString);
	    
	    //解析json 如果已经注册重新获取下token
		JSONObject obj = JSONObject.fromObject(responseString);
		String code = obj.getString("code");
		
	    if(code != null && "414".equals(code)){
	    	  String refreshUrl = PropertiesUtil.getValue("refreshUrl");
	    	  logger.info("【网易云账号注册】refreshUrl:"+refreshUrl);
	    	  
	          HttpPost refreshPost = new HttpPost(refreshUrl);
	          // 设置请求的header
	          refreshPost.addHeader("AppKey", appKey);
	          refreshPost.addHeader("Nonce", nonce);
	          String refreshCurTime = String.valueOf((new Date()).getTime() / 1000L);
	          refreshPost.addHeader("CurTime", refreshCurTime);
	          checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,refreshCurTime);//参考 计算CheckSum的java代码

	          refreshPost.addHeader("CheckSum", checkSum);
	          refreshPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	          
	          refreshPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	          HttpResponse  refreshResponse = httpClient.execute(refreshPost);
			  String refreshResponseString = EntityUtils.toString(refreshResponse.getEntity(), "utf-8");
			  logger.info("【网易云账号注册】refreshResponseString:"+refreshResponseString);
			  
			  JSONObject refreshJson = JSONObject.fromObject(refreshResponseString);
			  JSONObject infoJson = JSONObject.fromObject(refreshJson.getString("info"));
			  String token = infoJson.getString("token");
			  String accid = infoJson.getString("accid");
			  
			  tmpbean.setWyyxToken(token);
			  tmpbean.setUserKey(accid);
			  
			  Integer updateCode = mobileSqlMapper.updateUserInfo(tmpbean);
			  logger.info("【网易云账号注册】updateCode:" + updateCode);
	    }else if("200".equals(code)){
	    	  JSONObject infoJson = JSONObject.fromObject(obj.getString("info"));
	    	  String token = infoJson.getString("token");
	    	  String accid = infoJson.getString("accid");
	    	  
	    	  tmpbean.setWyyxToken(token);
			  tmpbean.setUserKey(accid);
			  
			  Integer updateCode =  mobileSqlMapper.updateUserInfo(tmpbean);
			  logger.info("【网易云账号注册】updateCode:" + updateCode);
	    }
	    
	    return tmpbean;
	}

	@Override
	@Transactional
	public int addNotice(NoticeBean tmpbean) throws Exception {
		return mobileSqlMapper.addNotice(tmpbean);
	}

	@Override
	public SelectedTopicBean getSelectedTopic(SelectedTopicBean tmpbean) throws Exception {
		return mobileSqlMapper.getSelectedTopic(tmpbean);
	}
	
	@Override
	public List<?> getDepartmentList() throws Exception{
		List<DepartmentBean> list = (List<DepartmentBean>) mobileSqlMapper.getDepartmentList();
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if(list != null){
			for(DepartmentBean bean: list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("departmentId", bean.getDepartmentKey());
				map.put("departmentName", bean.getDepartmentName());
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	@Override
	public List<?> getSelectTopicList(SelectedTopicBean bean) throws Exception{
		List<SelectedTopicBean> list = (List<SelectedTopicBean>) mobileSqlMapper.getSelectTopicList(bean);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(list != null){
			for(SelectedTopicBean tmpBean: list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("selectTopicId", tmpBean.getSelectedTopicKey());
				map.put("selectTopicTitle", tmpBean.getSelectedTopicTitile());
				map.put("createTime", tmpBean.getCreateTime());
				map.put("status", tmpBean.getTopicStatus());
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	@Override
	@Transactional
	public int addTopic(SelectedTopicBean bean) throws Exception{
		return mobileSqlMapper.addTopic(bean);
	}
	
	@Override
	public List<?> getAuditList(SelectedTopicBean bean) throws Exception{
		List<SelectedTopicBean> list = (List<SelectedTopicBean>) mobileSqlMapper.getAuditList(bean);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(list != null){
			for(SelectedTopicBean tmpBean: list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("auditTime", tmpBean.getAuditTime());
				map.put("auditName", tmpBean.getAuditName());
				if(tmpBean.getAuditStatus().equals("0")){
					map.put("auditStatus", "撤回");
				} else if(tmpBean.getAuditStatus().equals("1")){
					map.put("auditStatus", "送审");
				} else if(tmpBean.getAuditStatus().equals("2")){
					map.put("auditStatus", "一审通过");
				} else if(tmpBean.getAuditStatus().equals("3")){
					map.put("auditStatus", "二审通过");
				} else if(tmpBean.getAuditStatus().equals("4")){
					map.put("auditStatus", "三审通过");
				} else if(tmpBean.getAuditStatus().equals("5")){
					map.put("auditStatus", "终审通过");
				} else if(tmpBean.getAuditStatus().equals("-2")){
					map.put("auditStatus", "二审驳回");
				} else if(tmpBean.getAuditStatus().equals("-3")){
					map.put("auditStatus", "三审驳回");
				} else if(tmpBean.getAuditStatus().equals("-4")){
					map.put("auditStatus", "终审驳回");
				}
				map.put("refuseReason", tmpBean.getRefuseReason());
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	@Override
	public Map<String, Object> getPunchSetting() throws Exception{
		DictionariesBean tmp1 = mobileSqlMapper.getDictonaries("workOnTime");
		DictionariesBean tmp2 = mobileSqlMapper.getDictonaries("workOffTime");
		DictionariesBean tmp3 = mobileSqlMapper.getDictonaries("punchCoordinate");
		Map<String, Object> map = new HashMap<String, Object>();
		if(tmp1 != null){
			map.put("inTime", tmp1.getDictionariesValue());
		} else {
			map.put("inTime", "");
		}
		if(tmp2 != null){
			map.put("outTime", tmp2.getDictionariesValue());
		} else {
			map.put("outTime", "");
		}
		if(tmp3 != null){
			map.put("punchLocation", tmp3.getDictionariesValue());
		} else {
			map.put("punchLocation", "");
		}
		return map;
	}
	
	@Override
	public List<?> getPunchList(PunchBean bean) throws Exception{
		List<PunchBean> list = (List<PunchBean>) mobileSqlMapper.getPunchList(bean);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(list != null){
			for(PunchBean tmpBean: list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("inTime",tmpBean.getWorkOnTime());
				map.put("inLocation", tmpBean.getWorkOnPunchLocation());
				String inStatus = "-1";
				if("0".equals(tmpBean.getIsLate()) && "0".equals(tmpBean.getWorkOnIsFar())){
					inStatus = "0";
				}
				if("1".equals(tmpBean.getIsLate()) && "0".equals(tmpBean.getWorkOnIsFar())){
					inStatus = "1";
				}
				if("0".equals(tmpBean.getIsLate()) && "1".equals(tmpBean.getWorkOnIsFar())){
					inStatus = "2";
				}
				if("1".equals(tmpBean.getIsLate()) && "1".equals(tmpBean.getWorkOnIsFar())){
					inStatus = "3";
				}
				map.put("inStatus", inStatus);
				map.put("inRemarks", tmpBean.getWorkOnFarReason());
				map.put("outTime", tmpBean.getWorkOffTime());
				map.put("outLocation", tmpBean.getWorkOffPunchLocation());
				String outStatus = "-1";
				if("0".equals(tmpBean.getIsEarly()) && "0".equals(tmpBean.getWorkOffIsFar())){
					outStatus = "0";
				}
				if("1".equals(tmpBean.getIsEarly()) && "0".equals(tmpBean.getWorkOffIsFar())){
					outStatus = "1";
				}
				if("0".equals(tmpBean.getIsEarly()) && "1".equals(tmpBean.getWorkOffIsFar())){
					outStatus = "2";
				}
				if("1".equals(tmpBean.getIsEarly()) && "1".equals(tmpBean.getWorkOffIsFar())){
					outStatus = "3";
				}
				map.put("outStatus", outStatus);
				map.put("outRemarks", tmpBean.getWorkOffFarReason());
				map.put("date", tmpBean.getPunchDate());
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	@Override
	public int checkPunch(PunchBean bean) throws Exception{
		return mobileSqlMapper.checkPunchByDate(bean);
	}
	
	@Override
	@Transactional
	public int addPunch(PunchBean bean) throws Exception{
		return mobileSqlMapper.addPunch(bean);
	}
	
	@Override
	@Transactional
	public int updatePunch(PunchBean bean) throws Exception{
		return mobileSqlMapper.updatePunch(bean);
	}
}
