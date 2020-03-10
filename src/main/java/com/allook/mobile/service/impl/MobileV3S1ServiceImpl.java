package com.allook.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allook.frame.Constants.DICTIONARIES;
import com.allook.local.bean.DictionariesBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.WorkAttendanceBean;
import com.allook.mobile.common.Constants.PUNCH_TYPE;
import com.allook.mobile.mapper.MobileV3S1SqlMapper;
import com.allook.mobile.service.MobileV3S1Service;
import com.allook.screen.mapper.ScreenSqlMapper;
import com.allook.utils.DateUtil;
import com.allook.utils.PkCreat;
@Service
public class MobileV3S1ServiceImpl implements MobileV3S1Service{
	@Autowired
    private ScreenSqlMapper screenSqlMapper;
	
	@Autowired
    private MobileV3S1SqlMapper mobileV3S1SqlMapper;
	
	@Override
	public Map<?,?> getSettingInfo() throws Exception{
		DictionariesBean bean =new DictionariesBean();
		//查询字典表数据
		bean.setDictionariesName(new StringBuffer()
				.append("'").append(DICTIONARIES.PUNCH_COORDINATE).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_RANGE_RADIUS).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_OUTSIDE_REASON_FLAG).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_MORNING_CARDING_BEGIN).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_MORNING_CARDING_END).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_NOON_CARDING_BEGIN).append("'")
				.append(",")
				.append("'").append(DICTIONARIES.PUNCH_NOON_CARDING_END).append("'").toString()
				);
		
		List<DictionariesBean> dictList=(List<DictionariesBean>) screenSqlMapper.getDictValuesByNames(bean);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(dictList!=null&&dictList.size()>0){
			for(DictionariesBean tmpBean:dictList){
				
				String name=tmpBean.getDictionariesName();
				String value=tmpBean.getDictionariesValue();
				
				if(DICTIONARIES.PUNCH_COORDINATE.equals(name)){
					resultMap.put("location",value);
				}else if(DICTIONARIES.PUNCH_RANGE_RADIUS.equals(name)){
					resultMap.put("radius",value);
				}else if(DICTIONARIES.PUNCH_OUTSIDE_REASON_FLAG.equals(name)){
					resultMap.put("isRemark",value);
				}else if(DICTIONARIES.PUNCH_MORNING_CARDING_BEGIN.equals(name)){
					resultMap.put("morningTimeBegin",value);
				}else if(DICTIONARIES.PUNCH_MORNING_CARDING_END.equals(name)){
					resultMap.put("morningTimeEnd",value);
				}else if(DICTIONARIES.PUNCH_NOON_CARDING_BEGIN.equals(name)){
					resultMap.put("noonTimeBegin",value);
				}else if(DICTIONARIES.PUNCH_NOON_CARDING_END.equals(name)){
					resultMap.put("noonTimeEnd",value);
				}
			}
		}
		
 		return resultMap;
	}

	
	@Override
	@Transactional
	public Integer addPunch(Map<String,Object> map) throws Exception {
		String userKey=(String) map.get("userKey");
		String location=(String) map.get("location");
		String status=(String) map.get("status");
		String punchType=(String) map.get("punchType");
		String remarks=(String) map.get("remarks");
		
		//组织参数
		WorkAttendanceBean bean=new WorkAttendanceBean();
		bean.setPunchId(PkCreat.getTablePk());
		bean.setUserId(userKey);
		bean.setPunchYear(DateUtil.getCurrYear());
		bean.setPunchMonth(DateUtil.getCurrMonth());
		bean.setPunchDate(DateUtil.getTody());
		bean.setPunchType(punchType);
		
		//范围外打卡，用户需要填写备注 0:不需要 1：需要
		String punch_outside_reason_flag=screenSqlMapper.getDictValuesByName(DICTIONARIES.PUNCH_OUTSIDE_REASON_FLAG);
		if("0".equals(punch_outside_reason_flag)){
			remarks="待报备";
		}
		
		if(PUNCH_TYPE.MORNING_WORK_ON.equals(punchType)){//上午上班时间
			bean.setMorningOnWork(DateUtil.getCurrTime());
			bean.setMorningOnWorkAddr(location);
			bean.setMorningOnWorkOutside(status);
			bean.setMorningOnWorkOutsideReason(remarks);
			bean.setMorningOnWorkRemarks(remarks);
			
			String punch_morning_on_time=screenSqlMapper.getDictValuesByName(DICTIONARIES.PUNCH_MORNING_ON_TIME);
			if(DateUtil.timeLate(DateUtil.getCurrTime(),punch_morning_on_time)>0){//上午迟到
				bean.setMorningLate("1");
			}else{//上午未迟到
				bean.setMorningLate("0");
			}
		}else if(PUNCH_TYPE.MORNING_WORK_OFF.equals(punchType)){//上午下班时间
			bean.setMorningOffWork(DateUtil.getCurrTime());
			bean.setMorningOffWorkAddr(location);
			bean.setMorningOffWorkOutside(status);
			bean.setMorningOffWorkOutsideReason(remarks);
			bean.setMorningOffWorkRemarks(remarks);
			
			String punch_morning_off_time=screenSqlMapper.getDictValuesByName(DICTIONARIES.PUNCH_MORNING_OFF_TIME);
			if(DateUtil.timeLate(DateUtil.getCurrTime(),punch_morning_off_time)<0){//上午早退
				bean.setMorningEarly("1");
			}else{//上午未早退
				bean.setMorningEarly("0");
			}
		}else if(PUNCH_TYPE.NOON_WORK_ON.equals(punchType)){	//下午上班时间
			bean.setNoonOnWork(DateUtil.getCurrTime());
			bean.setNoonOnWorkAddr(location);
			bean.setNoonOnWorkOutside(status);
			bean.setNoonOnWorkOutsideReason(remarks);
			bean.setNoonOnWorkRemarks(remarks);
			
			String punch_noon_on_time=screenSqlMapper.getDictValuesByName(DICTIONARIES.PUNCH_NOON_ON_TIME);
			if(DateUtil.timeLate(DateUtil.getCurrTime(),punch_noon_on_time)>0){//下午迟到
				bean.setNoonLate("1");
			}else{//下午未迟到
				bean.setNoonLate("0");
			}
		}else if(PUNCH_TYPE.NOON_WORK_OFF.equals(punchType)){	//下午下班时间
			bean.setNoonOffWork(DateUtil.getCurrTime());
			bean.setNoonOffWorkAddr(location);
			bean.setNoonOffWorkOutside(status);
			bean.setNoonOffWorkOutsideReason(remarks);
			bean.setNoonOffWorkRemarks(remarks);
			
			String punch_noon_off_time=screenSqlMapper.getDictValuesByName(DICTIONARIES.PUNCH_NOON_OFF_TIME);
			if(DateUtil.timeLate(DateUtil.getCurrTime(),punch_noon_off_time)<0){//下午早退
				bean.setNoonEarly("1");
			}else{//下午未早退
				bean.setNoonEarly("0");
			}
		}
		
		
		Integer count=0;
		if(isPunchRecord(bean)){//存在记录，修改记录
			count=mobileV3S1SqlMapper.updatePunch(bean);
		}else{//不存在记录，新增打卡记录
			count=mobileV3S1SqlMapper.addPunch(bean);
		}
		
		return count;
	}
	
    
	/**
	 * 判断该用户当日是否已经有打卡记录
	 * @param userKey
	 * @return
	 */
	public boolean isPunchRecord(WorkAttendanceBean bean){
		boolean resFlag=false;
		Integer count=mobileV3S1SqlMapper.isPunchRecord(bean);
		if(count>0){
			resFlag=true;
		}
		return resFlag;
	}


	@Override
	public List<?> getPunchList(PunchBean bean) throws Exception {
		return mobileV3S1SqlMapper.getPunchList(bean);
	}


	@Override
	public Map<?, ?> getMessage() throws Exception {
		return mobileV3S1SqlMapper.getMessage();
	}
}
