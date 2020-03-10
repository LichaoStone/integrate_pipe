package com.allook.mobile.bean;

import java.util.List;
import java.util.Map;

import com.allook.frame.BaseEntity;
/**
 * 消息提醒Bean
 * @作者 栗超
 * @时间 2018年6月7日 下午6:16:50
 * @说明
 */
public class NoticeBean extends BaseEntity{
	private static final long serialVersionUID = -7895421801498324268L;
	
	private String noticeKey;
	private String noticeContent;
	private String distributionTaskKey;
	private String selectedTopicKey;
	private String userKey;
	private String noticeType;
	
	private List<Map<String,Object>> paraList;
	
	private String relationshipKey;
	
	
	public String getRelationshipKey() {
		return relationshipKey;
	}
	public void setRelationshipKey(String relationshipKey) {
		this.relationshipKey = relationshipKey;
	}
	public List<Map<String, Object>> getParaList() {
		return paraList;
	}
	public void setParaList(List<Map<String, Object>> paraList) {
		this.paraList = paraList;
	}
	public String getNoticeKey() {
		return noticeKey;
	}
	public void setNoticeKey(String noticeKey) {
		this.noticeKey = noticeKey;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getDistributionTaskKey() {
		return distributionTaskKey;
	}
	public void setDistributionTaskKey(String distributionTaskKey) {
		this.distributionTaskKey = distributionTaskKey;
	}
	public String getSelectedTopicKey() {
		return selectedTopicKey;
	}
	public void setSelectedTopicKey(String selectedTopicKey) {
		this.selectedTopicKey = selectedTopicKey;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
}
