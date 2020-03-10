package com.allook.local.mapper;

import com.allook.mobile.bean.UserBean;

/**
 * 本地接口Dao层
 * @作者 栗超
 * @时间 2018年5月24日 下午2:44:17
 * @说明
 */
public interface LocalSqlMapper {
	 public UserBean 	getUserInfo(UserBean bean) throws Exception;
}
