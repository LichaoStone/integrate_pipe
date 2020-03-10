package com.allook.wechat.mapper;

import java.util.List;

import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.UserBean;
import com.allook.wechat.bean.ManuscriptBean;
import com.allook.wechat.bean.SubjectBean;

public interface WechatSqlMapper {
	UserBean getUserInfo(UserBean bean);
	Integer modifyUserInfo(UserBean bean);
	List<?>  statistics(UserBean bean);
	List<?>  getPapersList(ManuscriptBean bean);
	ManuscriptBean getPapersView(ManuscriptBean bean);
	List<?> getSubjectList(SubjectBean bean);
	SubjectBean getSubjectView(SubjectBean bean);
	Integer addPapers(ManuscriptBean bean);
	Integer deletePapers(ManuscriptBean bean);
	Integer modifyPapers(ManuscriptBean bean);
	List<?> getSubjectRecommendList(SubjectBean bean);
	List<?> getMaterialList(FileBean bean);
	List<?> adoptionList(ManuscriptBean bean);
	Integer modifyStatusPapers(ManuscriptBean bean);
	Integer addMaterial(FileBean bean); 
}
