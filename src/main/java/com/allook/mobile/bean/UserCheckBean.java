package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;

public class UserCheckBean extends BaseEntity{
	private static final long serialVersionUID = -6326111191391175046L;
	
	private String userKey;
	private String assessmentKey;
	private String qkAccountKey;
	private String newsCount;
	private String pvCount;
	private String uvCount;
	private String perviews;
	private String updateTime;
	private String type;
	private String userName;
	private String headImgUrl;
	
	
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getAssessmentKey() {
		return assessmentKey;
	}
	public void setAssessmentKey(String assessmentKey) {
		this.assessmentKey = assessmentKey;
	}
	public String getQkAccountKey() {
		return qkAccountKey;
	}
	public void setQkAccountKey(String qkAccountKey) {
		this.qkAccountKey = qkAccountKey;
	}
	public String getNewsCount() {
		return newsCount;
	}
	public void setNewsCount(String newsCount) {
		this.newsCount = newsCount;
	}
	public String getPvCount() {
		return pvCount;
	}
	public void setPvCount(String pvCount) {
		this.pvCount = pvCount;
	}
	public String getUvCount() {
		return uvCount;
	}
	public void setUvCount(String uvCount) {
		this.uvCount = uvCount;
	}
	public String getPerviews() {
		return perviews;
	}
	public void setPerviews(String perviews) {
		this.perviews = perviews;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
