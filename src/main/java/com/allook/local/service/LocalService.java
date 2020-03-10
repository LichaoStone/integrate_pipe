package com.allook.local.service;

import com.allook.mobile.bean.UserBean;
/**
 * 本地管理后台接口Service接口
 * @作者 栗超
 * @时间 2018年5月23日 下午2:24:06
 * @说明
 */
public interface LocalService {
	 UserBean  	 getUserInfo(UserBean bean)  throws Exception;
}
