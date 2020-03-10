package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;
/**
 * 用户Bean
 * @作者 栗超
 * @时间 2018年5月21日 下午2:39:34
 * @说明
 */
public class UserBean extends BaseEntity{
	private static final long serialVersionUID = 3693638774393472421L;
	private String userKey;
    private String userName;
    private String realName;
    private String qkAccountNumber;
    private String qkAccountKey;
    private String password;
    private String gender;
    private String headImgUrl;
    private String phoneNumber;
    private String userType;
    private String taskStatisticsShowStatus;
    private String productStatisticsShowStatus;
    private String pageFlageUniqueKey;
    
    
    /**
     * 网易云信token
     * @return
     */
    private String wyyxToken;
    
    private String taskOrderNum;
    private String email;
	
    private String appKey;
    private String initiativeId;
    private String passiveId;
    private String command;
    private String netEASEKey;
    
    
    private String webCallFlag;
    
    
    private String roleKey;
    
	public String getNetEASEKey() {
		return netEASEKey;
	}
	public void setNetEASEKey(String netEASEKey) {
		this.netEASEKey = netEASEKey;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getInitiativeId() {
		return initiativeId;
	}
	public void setInitiativeId(String initiativeId) {
		this.initiativeId = initiativeId;
	}
	public String getPassiveId() {
		return passiveId;
	}
	public void setPassiveId(String passiveId) {
		this.passiveId = passiveId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getQkAccountNumber() {
		return qkAccountNumber;
	}
	public void setQkAccountNumber(String qkAccountNumber) {
		this.qkAccountNumber = qkAccountNumber;
	}
	public String getQkAccountKey() {
		return qkAccountKey;
	}
	public void setQkAccountKey(String qkAccountKey) {
		this.qkAccountKey = qkAccountKey;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTaskStatisticsShowStatus() {
		return taskStatisticsShowStatus;
	}
	public void setTaskStatisticsShowStatus(String taskStatisticsShowStatus) {
		this.taskStatisticsShowStatus = taskStatisticsShowStatus;
	}
	public String getProductStatisticsShowStatus() {
		return productStatisticsShowStatus;
	}
	public void setProductStatisticsShowStatus(String productStatisticsShowStatus) {
		this.productStatisticsShowStatus = productStatisticsShowStatus;
	}
	public String getWyyxToken() {
		return wyyxToken;
	}
	public void setWyyxToken(String wyyxToken) {
		this.wyyxToken = wyyxToken;
	}
	public String getTaskOrderNum() {
		return taskOrderNum;
	}
	public void setTaskOrderNum(String taskOrderNum) {
		this.taskOrderNum = taskOrderNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPageFlageUniqueKey() {
		return pageFlageUniqueKey;
	}
	public void setPageFlageUniqueKey(String pageFlageUniqueKey) {
		this.pageFlageUniqueKey = pageFlageUniqueKey;
	}
	public String getWebCallFlag() {
		return webCallFlag;
	}
	public void setWebCallFlag(String webCallFlag) {
		this.webCallFlag = webCallFlag;
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
}
