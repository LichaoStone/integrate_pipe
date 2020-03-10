package com.allook.mobile.bean;

import com.allook.frame.BaseEntity;

public class DepartmentBean extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592263168019275753L;

	private String departmentKey;
	
	private String departmentName;

	public String getDepartmentKey() {
		return departmentKey;
	}

	public void setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
