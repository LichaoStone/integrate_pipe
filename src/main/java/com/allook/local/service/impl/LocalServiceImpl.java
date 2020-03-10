package com.allook.local.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allook.frame.aspect.annotation.PaginationService;
import com.allook.local.mapper.LocalSqlMapper;
import com.allook.local.service.LocalService;
import com.allook.mobile.bean.UserBean;
@Service
public class LocalServiceImpl implements LocalService{
	public static final Logger logger = Logger.getLogger(LocalServiceImpl.class);
    @Autowired
    LocalSqlMapper localSqlMapper;
    
	@Override
	@PaginationService
	public UserBean getUserInfo(UserBean bean) throws Exception{
		return localSqlMapper.getUserInfo(bean);
	}
}
