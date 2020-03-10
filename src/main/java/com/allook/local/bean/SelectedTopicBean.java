package com.allook.local.bean;

import com.allook.frame.BaseEntity;
/**
 * 选题
 * @作者 栗超
 * @时间 2018年5月30日 上午10:14:18
 * @说明
 */
public class SelectedTopicBean extends BaseEntity{
	private static final long serialVersionUID = -7724838262242229318L;
	/**主题**/
	private String selectedTopicKey;
	/**选题专题**/
	private String selectedTopicTitile;
	/**选题简介**/
	private String selectedTopicDescript;
	/**部门key**/
	private String departmentKey;
	private String departmentName;
	/**选题备注**/
	private String selectedTopicRemarks;
	/**显示状态**/
	private String showStatus;
	/**是否撤销**/
	private String topicStatus;
	/**任务数**/
	private String totalTask;
	
	private String createTime;
	/**审核时间**/
	private String auditTime;
	/**审核人**/
	private String auditName;
	/**审核状态**/
	private String auditStatus;
	/**拒绝原因**/
	private String refuseReason;
	
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getSelectedTopicKey() {
		return selectedTopicKey;
	}
	public void setSelectedTopicKey(String selectedTopicKey) {
		this.selectedTopicKey = selectedTopicKey;
	}
	public String getSelectedTopicTitile() {
		return selectedTopicTitile;
	}
	public void setSelectedTopicTitile(String selectedTopicTitile) {
		this.selectedTopicTitile = selectedTopicTitile;
	}
	public String getSelectedTopicDescript() {
		return selectedTopicDescript;
	}
	public void setSelectedTopicDescript(String selectedTopicDescript) {
		this.selectedTopicDescript = selectedTopicDescript;
	}
	public String getDepartmentKey() {
		return departmentKey;
	}
	public void setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
	}
	public String getSelectedTopicRemarks() {
		return selectedTopicRemarks;
	}
	public void setSelectedTopicRemarks(String selectedTopicRemarks) {
		this.selectedTopicRemarks = selectedTopicRemarks;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public String getTopicStatus() {
		return topicStatus;
	}
	public void setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
	}
	public String getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(String totalTask) {
		this.totalTask = totalTask;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
}
