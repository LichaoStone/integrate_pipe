package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;
/**
 * 轻快发布Bean类
 * @作者 栗超
 * @时间 2018年5月14日 上午9:05:30
 * @说明
 */
public class MobileBean extends BaseEntity{
	private static final long serialVersionUID = 1631289609624126768L;
	/**用户key**/
	private String userKey;
	/**通话头像**/
    private String recordImg;
    /**来电名称**/
    private String recordName;
    /**通话时长**/
    private String recordLength;
    /**通话开始时间（例:时分秒  09:21）**/
    private String recordTime;
    /**接听状态(1:已接;2未接)**/
    private String recordStatus;
    /**通话开始时间(例:月日星期 3/27 周二)**/
    private String recordDate;
    
    /**通知key**/
    private String noticeKey;
    
	public String getRecordImg() {
		return recordImg;
	}
	public void setRecordImg(String recordImg) {
		this.recordImg = recordImg;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getRecordLength() {
		return recordLength;
	}
	public void setRecordLength(String recordLength) {
		this.recordLength = recordLength;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getNoticeKey() {
		return noticeKey;
	}
	public void setNoticeKey(String noticeKey) {
		this.noticeKey = noticeKey;
	}
}
