package com.allook.mobile.service;

import java.util.List;
import java.util.Map;

import com.allook.mobile.bean.PunchBean;


public interface MobileV3S1Service {
	/**
	 * 获取打卡设置信息
	 * @return
	 * @throws Exception
	 */
	Map<?,?> getSettingInfo() throws Exception;
	/**
	 * 新增打卡记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer addPunch(Map<String,Object> map) throws Exception;
	
    /**
	 * 查询打卡列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getPunchList(PunchBean bean) throws Exception;
	public Map<?,?> getMessage() throws Exception;
}
