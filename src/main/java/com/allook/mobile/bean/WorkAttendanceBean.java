package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;
/**
 * @时间 2019-07-31
 * @版本 V3.1
 * @作者  栗超
 * @说明
 * 		考勤打卡记录 	   
 */
public class WorkAttendanceBean extends BaseEntity{
	private static final long serialVersionUID = -7592263168019275753L;
	/**
	 * 主键key
	 */
	private String punchId;
	/**
	 * 用户key
	 */
	private String userId;
	/**
	 * 打卡年份
	 */
	private String punchYear;
	/**
	 * 打卡月份
	 */
	private String punchMonth;
	/**
	 * 打卡时间，格式2019-01-01
	 */
	private String punchDate;
	/**
	 * 上午上班时间，格式08:00
	 */
	private String morningOnWork;
	/**
	 * 上午上班是否迟到，0未迟到，1迟到
	 */
	private String morningLate;
	/**
	 * 上午上班打卡地点
	 */
	private String morningOnWorkAddr;
	/**
	 * 上班是否范围外打卡，0否，1是
	 */
	private String morningOnWorkOutside;
	/**
	 * 上午上班范围外打卡原因
	 */
	private String morningOnWorkOutsideReason;
	/**
	 * 上午上班备注
	 */
	private String morningOnWorkRemarks;
	/**
	 * 上午下班时间，格式08:00
	 */
	private String morningOffWork;
	/**
	 * 上午下班是否迟到，0未迟到，1迟到
	 */
	private String morningEarly;
	/**
	 * 上午下班打卡地点
	 */
	private String morningOffWorkAddr;
	/**
	 * 上午下班是否范围外打卡，0否，1是
	 */
	private String morningOffWorkOutside;
	/**
	 * 上午下班范围外打卡原因
	 */
	private String morningOffWorkOutsideReason;
	/**
	 * 上午下班备注
	 */
	private String morningOffWorkRemarks;
	/**
	 * 下午上班时间，格式08:00
	 */
	private String noonOnWork;
	/**
	 * 下午上班是否迟到，0未迟到，1迟到
	 */
	private String noonLate;
	/**
	 * 下午上班打卡地点
	 */
	private String noonOnWorkAddr;
	/**
	 * 下班是否范围外打卡，0否，1是
	 */
	private String noonOnWorkOutside;
	/**
	 * 下午上班范围外打卡原因
	 */
	private String noonOnWorkOutsideReason;
	/**
	 * 下午上班备注
	 */
	private String noonOnWorkRemarks;
	/**
	 * 下午下班时间，格式08:00
	 */
	private String noonOffWork;
	/**
	 * 下午下班是否迟到，0未迟到，1迟到
	 */
	private String noonEarly;
	/**
	 * 下午下班打卡地点
	 */
	private String noonOffWorkAddr;
	/**
	 * 下午下班是否范围外打卡，0否，1是
	 */
	private String noonOffWorkOutside;
	/**
	 * 下午下班范围外打卡原因
	 */
	private String noonOffWorkOutsideReason;
	/**
	 * 下午下班备注
	 */
	private String noonOffWorkRemarks;
	
	/**
	 * 打卡类型: 0上午上班;1上午下班;2下午上班;3下午下班
	 */
	private String punchType;
	
	public String getPunchId() {
		return punchId;
	}
	public void setPunchId(String punchId) {
		this.punchId = punchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPunchYear() {
		return punchYear;
	}
	public void setPunchYear(String punchYear) {
		this.punchYear = punchYear;
	}
	public String getPunchMonth() {
		return punchMonth;
	}
	public void setPunchMonth(String punchMonth) {
		this.punchMonth = punchMonth;
	}
	public String getPunchDate() {
		return punchDate;
	}
	public void setPunchDate(String punchDate) {
		this.punchDate = punchDate;
	}
	public String getMorningOnWork() {
		return morningOnWork;
	}
	public void setMorningOnWork(String morningOnWork) {
		this.morningOnWork = morningOnWork;
	}
	public String getMorningLate() {
		return morningLate;
	}
	public void setMorningLate(String morningLate) {
		this.morningLate = morningLate;
	}
	public String getMorningOnWorkAddr() {
		return morningOnWorkAddr;
	}
	public void setMorningOnWorkAddr(String morningOnWorkAddr) {
		this.morningOnWorkAddr = morningOnWorkAddr;
	}
	public String getMorningOnWorkOutside() {
		return morningOnWorkOutside;
	}
	public void setMorningOnWorkOutside(String morningOnWorkOutside) {
		this.morningOnWorkOutside = morningOnWorkOutside;
	}
	public String getMorningOnWorkOutsideReason() {
		return morningOnWorkOutsideReason;
	}
	public void setMorningOnWorkOutsideReason(String morningOnWorkOutsideReason) {
		this.morningOnWorkOutsideReason = morningOnWorkOutsideReason;
	}
	public String getMorningOnWorkRemarks() {
		return morningOnWorkRemarks;
	}
	public void setMorningOnWorkRemarks(String morningOnWorkRemarks) {
		this.morningOnWorkRemarks = morningOnWorkRemarks;
	}
	public String getMorningOffWork() {
		return morningOffWork;
	}
	public void setMorningOffWork(String morningOffWork) {
		this.morningOffWork = morningOffWork;
	}
	public String getMorningEarly() {
		return morningEarly;
	}
	public void setMorningEarly(String morningEarly) {
		this.morningEarly = morningEarly;
	}
	public String getMorningOffWorkAddr() {
		return morningOffWorkAddr;
	}
	public void setMorningOffWorkAddr(String morningOffWorkAddr) {
		this.morningOffWorkAddr = morningOffWorkAddr;
	}
	public String getMorningOffWorkOutside() {
		return morningOffWorkOutside;
	}
	public void setMorningOffWorkOutside(String morningOffWorkOutside) {
		this.morningOffWorkOutside = morningOffWorkOutside;
	}
	public String getMorningOffWorkOutsideReason() {
		return morningOffWorkOutsideReason;
	}
	public void setMorningOffWorkOutsideReason(String morningOffWorkOutsideReason) {
		this.morningOffWorkOutsideReason = morningOffWorkOutsideReason;
	}
	public String getMorningOffWorkRemarks() {
		return morningOffWorkRemarks;
	}
	public void setMorningOffWorkRemarks(String morningOffWorkRemarks) {
		this.morningOffWorkRemarks = morningOffWorkRemarks;
	}
	public String getNoonOnWork() {
		return noonOnWork;
	}
	public void setNoonOnWork(String noonOnWork) {
		this.noonOnWork = noonOnWork;
	}
	public String getNoonLate() {
		return noonLate;
	}
	public void setNoonLate(String noonLate) {
		this.noonLate = noonLate;
	}
	public String getNoonOnWorkAddr() {
		return noonOnWorkAddr;
	}
	public void setNoonOnWorkAddr(String noonOnWorkAddr) {
		this.noonOnWorkAddr = noonOnWorkAddr;
	}
	public String getNoonOnWorkOutside() {
		return noonOnWorkOutside;
	}
	public void setNoonOnWorkOutside(String noonOnWorkOutside) {
		this.noonOnWorkOutside = noonOnWorkOutside;
	}
	public String getNoonOnWorkOutsideReason() {
		return noonOnWorkOutsideReason;
	}
	public void setNoonOnWorkOutsideReason(String noonOnWorkOutsideReason) {
		this.noonOnWorkOutsideReason = noonOnWorkOutsideReason;
	}
	public String getNoonOnWorkRemarks() {
		return noonOnWorkRemarks;
	}
	public void setNoonOnWorkRemarks(String noonOnWorkRemarks) {
		this.noonOnWorkRemarks = noonOnWorkRemarks;
	}
	public String getNoonOffWork() {
		return noonOffWork;
	}
	public void setNoonOffWork(String noonOffWork) {
		this.noonOffWork = noonOffWork;
	}
	public String getNoonEarly() {
		return noonEarly;
	}
	public void setNoonEarly(String noonEarly) {
		this.noonEarly = noonEarly;
	}
	public String getNoonOffWorkAddr() {
		return noonOffWorkAddr;
	}
	public void setNoonOffWorkAddr(String noonOffWorkAddr) {
		this.noonOffWorkAddr = noonOffWorkAddr;
	}
	public String getNoonOffWorkOutside() {
		return noonOffWorkOutside;
	}
	public void setNoonOffWorkOutside(String noonOffWorkOutside) {
		this.noonOffWorkOutside = noonOffWorkOutside;
	}
	public String getNoonOffWorkOutsideReason() {
		return noonOffWorkOutsideReason;
	}
	public void setNoonOffWorkOutsideReason(String noonOffWorkOutsideReason) {
		this.noonOffWorkOutsideReason = noonOffWorkOutsideReason;
	}
	public String getNoonOffWorkRemarks() {
		return noonOffWorkRemarks;
	}
	public void setNoonOffWorkRemarks(String noonOffWorkRemarks) {
		this.noonOffWorkRemarks = noonOffWorkRemarks;
	}
	public String getPunchType() {
		return punchType;
	}
	public void setPunchType(String punchType) {
		this.punchType = punchType;
	}
}
