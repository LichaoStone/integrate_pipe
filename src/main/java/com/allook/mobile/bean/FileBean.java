package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;
/**
 * 文件bean
 * @作者 栗超
 * @时间 2018年5月28日 下午2:23:13
 * @说明
 */
public class FileBean extends BaseEntity{
	private static final long serialVersionUID = -7835289023424632534L;
	
	private String userFileKey;
	private String userFileName;
	private String userKey;
	private String userFolderKey;
	private String fileUrl;
	private String showStatus;
	private String imageUrl;
	private String fileType;
	private String distributionTaskKey;
	private String remarks;
	private String isDelete;
	
	private String duration;
	private String size;
	
	private String updateTime;
	/**
	 * 专题表外键
	 */
	private String subId;
	/**
	 * 类型:
	 * 	1.专题
	 *  2.选题
	 */
	private String sourceType;
	
	/**
	 * 上传类型；
	 *   1：后台
	 *   2：前端
	 */
	private String uploadType;
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSize() {	
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getUserFileKey() {
		return userFileKey;
	}
	public void setUserFileKey(String userFileKey) {
		this.userFileKey = userFileKey;
	}
	public String getUserFileName() {
		return userFileName;
	}
	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getUserFolderKey() {
		return userFolderKey;
	}
	public void setUserFolderKey(String userFolderKey) {
		this.userFolderKey = userFolderKey;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getDistributionTaskKey() {
		return distributionTaskKey;
	}
	public void setDistributionTaskKey(String distributionTaskKey) {
		this.distributionTaskKey = distributionTaskKey;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
}
