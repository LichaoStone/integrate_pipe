package com.allook.mobile.common;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @作者 lichao
 * @时间 2019-07-10
 * @版本 V3.1
s *
 */
public abstract class Constants{
	
	/**
	 * 用户登陆状态Map，用于校验用户单点登陆
	 */
	public static final Map<String,Object> mobileLoginFlagMap=new HashMap<String,Object>();
	
	/**
	 *  打卡类型 
	 */
	public static class PUNCH_TYPE{
		/**
		 * 上午上班
		 */
    	public static final String MORNING_WORK_ON= "0";
    	/**
    	 * 上午下班
    	 */
    	public static final String MORNING_WORK_OFF = "1";
    	/**
    	 * 下午上班
    	 */
    	public static final String NOON_WORK_ON = "2";
    	/**
    	 * 下午下班
    	 */
    	public static final String NOON_WORK_OFF = "3";
    }
}
