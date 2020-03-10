package com.allook.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allook.frame.aspect.annotation.PaginationService;
import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.UserBean;
import com.allook.wechat.bean.ManuscriptBean;
import com.allook.wechat.bean.SubjectBean;
import com.allook.wechat.mapper.WechatSqlMapper;
import com.allook.wechat.service.WechatService;

@Service
public class WechatServiceImpl implements WechatService{
	 @Autowired
	 private WechatSqlMapper wechatSqlMapper;
	/**
	 * 获取用户详细信息
	 */
	@Override
	public UserBean getUserInfo(UserBean bean) {
		return wechatSqlMapper.getUserInfo(bean);
	}
	
	/**
	 * 修改用户信息
	 */
	@Override
	@Transactional
	public Integer modifyUserInfo(UserBean bean) {
		return wechatSqlMapper.modifyUserInfo(bean);
	}

	@Override
	public List<?> statistics(UserBean bean) {
		return wechatSqlMapper.statistics(bean);
	}
	
	/**
	 * 获取稿件列表和我的稿件列表
	 */
	@Override
	@PaginationService
	public List<?> getPapersList(ManuscriptBean bean) {
		return wechatSqlMapper.getPapersList(bean);
	}

	@Override
	public ManuscriptBean getPapersView(ManuscriptBean bean) {
		return wechatSqlMapper.getPapersView(bean);
	}

	@Override
	@PaginationService
	public List<?> getSubjectList(SubjectBean bean) {
		return wechatSqlMapper.getSubjectList(bean);
	}

	@Override
	public SubjectBean getSubjectView(SubjectBean bean) {
		return wechatSqlMapper.getSubjectView(bean);
	}

	@Override
	@Transactional
	public Integer addPapers(ManuscriptBean bean) {
		return wechatSqlMapper.addPapers(bean);
	}

	@Override
	@PaginationService
	public List<?> getSubjectRecommendList(SubjectBean bean) {
		return wechatSqlMapper.getSubjectRecommendList(bean);
	}

	@Override
	@Transactional
	public Integer deletePapers(ManuscriptBean bean) {
		return wechatSqlMapper.deletePapers(bean);
	}

	@Override
	@Transactional
	public Integer modifyPapers(ManuscriptBean bean) {
		return wechatSqlMapper.modifyPapers(bean);
	}

	@Override
	@PaginationService
	public List<?> getMaterialList(FileBean bean) {
		return wechatSqlMapper.getMaterialList(bean);
	}

	@Override
	@PaginationService
	public List<?> adoptionList(ManuscriptBean bean) {
		return wechatSqlMapper.adoptionList(bean);
	}

	@Override
	@Transactional
	public Integer modifyStatusPapers(ManuscriptBean bean) {
		return wechatSqlMapper.modifyStatusPapers(bean);
	}

	@Override
	@Transactional
	public Integer addMaterial(FileBean bean) {
		return wechatSqlMapper.addMaterial(bean);
	}
}
