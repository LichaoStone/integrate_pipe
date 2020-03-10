package com.allook.wechat.bean;

import java.util.List;

import com.allook.frame.BaseEntity;

public class SubjectBean extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String subId;
	/**
	 * 专题名称
	 */
	private String subTitle;
	/**
	 * 创建时间
	 */
	private String subCreateTime;
	/**
	 * 内容
	 */
	private String subContent;
	/**
	 * 备注
	 */
	private String subRemark;
	private String subUserId;
	/**
	 * 显示状态（0:隐藏；1：显示）
	 */
	private String showStatus;
	
	
	private List<ReportBean> reportList;
	
	private String manuscriptCount;
	
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getSubCreateTime() {
		return subCreateTime;
	}
	public void setSubCreateTime(String subCreateTime) {
		this.subCreateTime = subCreateTime;
	}
	public String getSubContent() {
		return subContent;
	}
	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}
	public String getSubRemark() {
		return subRemark;
	}
	public void setSubRemark(String subRemark) {
		this.subRemark = subRemark;
	}
	public String getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(String subUserId) {
		this.subUserId = subUserId;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public List<ReportBean> getReportList() {
		return reportList;
	}
	public void setReportList(List<ReportBean> reportList) {
		this.reportList = reportList;
	}
	public String getManuscriptCount() {
		return manuscriptCount;
	}
	public void setManuscriptCount(String manuscriptCount) {
		this.manuscriptCount = manuscriptCount;
	}
	
}
