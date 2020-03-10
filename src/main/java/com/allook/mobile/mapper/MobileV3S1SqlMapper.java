package com.allook.mobile.mapper;

import java.util.List;
import java.util.Map;

import com.allook.local.bean.DictionariesBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.WorkAttendanceBean;

public interface MobileV3S1SqlMapper {
	List<?> getSettingInfo(DictionariesBean bean);
	Integer isPunchRecord(WorkAttendanceBean bean);
	Integer addPunch(WorkAttendanceBean bean) throws Exception;
	Integer updatePunch(WorkAttendanceBean bean) throws Exception;
   /**
	 * 查询打卡列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getPunchList(PunchBean bean) throws Exception;
	public Map<?,?> getMessage() throws Exception;
}
