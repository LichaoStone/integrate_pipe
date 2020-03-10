package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;

public class PunchBean extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4437768771139477910L;

	private String userKey;
	
	private String punchYear;
	
	private String punchMonth;
	
	private String punchDate;
	
	private String workOnTime;
	
	private String workOffTime;
	
	private String isLate;
	
	private String isEarly;
	
	private String workOnIsFar;
	
	private String workOffIsFar;
	
	private String workOnFarReason;
	
	private String workOffFarReason;
	
	private String workOnPunchLocation;
	
	private String workOffPunchLocation;
	
	private String punchId;

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

	public String getWorkOnTime() {
		return workOnTime;
	}

	public void setWorkOnTime(String workOnTime) {
		this.workOnTime = workOnTime;
	}

	public String getWorkOffTime() {
		return workOffTime;
	}

	public void setWorkOffTime(String workOffTime) {
		this.workOffTime = workOffTime;
	}

	public String getIsLate() {
		return isLate;
	}

	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}

	public String getIsEarly() {
		return isEarly;
	}

	public void setIsEarly(String isEarly) {
		this.isEarly = isEarly;
	}

	public String getWorkOnIsFar() {
		return workOnIsFar;
	}

	public void setWorkOnIsFar(String workOnIsFar) {
		this.workOnIsFar = workOnIsFar;
	}

	public String getWorkOffIsFar() {
		return workOffIsFar;
	}

	public void setWorkOffIsFar(String workOffIsFar) {
		this.workOffIsFar = workOffIsFar;
	}

	public String getWorkOnFarReason() {
		return workOnFarReason;
	}

	public void setWorkOnFarReason(String workOnFarReason) {
		this.workOnFarReason = workOnFarReason;
	}

	public String getWorkOffFarReason() {
		return workOffFarReason;
	}

	public void setWorkOffFarReason(String workOffFarReason) {
		this.workOffFarReason = workOffFarReason;
	}

	public String getWorkOnPunchLocation() {
		return workOnPunchLocation;
	}

	public void setWorkOnPunchLocation(String workOnPunchLocation) {
		this.workOnPunchLocation = workOnPunchLocation;
	}

	public String getWorkOffPunchLocation() {
		return workOffPunchLocation;
	}

	public void setWorkOffPunchLocation(String workOffPunchLocation) {
		this.workOffPunchLocation = workOffPunchLocation;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getPunchId() {
		return punchId;
	}

	public void setPunchId(String punchId) {
		this.punchId = punchId;
	}
}
