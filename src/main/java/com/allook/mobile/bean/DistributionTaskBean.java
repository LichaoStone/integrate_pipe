package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;

public class DistributionTaskBean extends BaseEntity{
	private static final long serialVersionUID = 4035436583031727917L;
	
	private String distributionTaskKey;
    private String distributionTaskTitle;
    private String address;
    private String distributionTaskTime;
    private String selectedTopicKey;
    private String distributionTaskRemarks;
    private String taskStatus;
    private String userKey;
    private String realName;
    private String userTaskStatus;
    private String reason;
    private String showStatus;
    private String isReturnContent;
    
    private Integer taskTotalCount;
    private Integer taskDoneCount;
    private String  taskDoneRate;
    private String lng;
    private String lat;
    private String headImgUrl;
    
    private String revokeTime;
    
    
    
	public String getRevokeTime() {
		return revokeTime;
	}
	public void setRevokeTime(String revokeTime) {
		this.revokeTime = revokeTime;
	}
	public Integer getTaskTotalCount() {
		return taskTotalCount;
	}
	public void setTaskTotalCount(Integer taskTotalCount) {
		this.taskTotalCount = taskTotalCount;
	}
	public Integer getTaskDoneCount() {
		return taskDoneCount;
	}
	public void setTaskDoneCount(Integer taskDoneCount) {
		this.taskDoneCount = taskDoneCount;
	}
	public String getTaskDoneRate() {
		return taskDoneRate;
	}
	public void setTaskDoneRate(String taskDoneRate) {
		this.taskDoneRate = taskDoneRate;
	}
	public String getDistributionTaskKey() {
		return distributionTaskKey;
	}
	public void setDistributionTaskKey(String distributionTaskKey) {
		this.distributionTaskKey = distributionTaskKey;
	}
	public String getDistributionTaskTitle() {
		return distributionTaskTitle;
	}
	public void setDistributionTaskTitle(String distributionTaskTitle) {
		this.distributionTaskTitle = distributionTaskTitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistributionTaskTime() {
		return distributionTaskTime;
	}
	public void setDistributionTaskTime(String distributionTaskTime) {
		this.distributionTaskTime = distributionTaskTime;
	}
	public String getSelectedTopicKey() {
		return selectedTopicKey;
	}
	public void setSelectedTopicKey(String selectedTopicKey) {
		this.selectedTopicKey = selectedTopicKey;
	}
	public String getDistributionTaskRemarks() {
		return distributionTaskRemarks;
	}
	public void setDistributionTaskRemarks(String distributionTaskRemarks) {
		this.distributionTaskRemarks = distributionTaskRemarks;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUserTaskStatus() {
		return userTaskStatus;
	}
	public void setUserTaskStatus(String userTaskStatus) {
		this.userTaskStatus = userTaskStatus;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public String getIsReturnContent() {
		return isReturnContent;
	}
	public void setIsReturnContent(String isReturnContent) {
		this.isReturnContent = isReturnContent;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
}
