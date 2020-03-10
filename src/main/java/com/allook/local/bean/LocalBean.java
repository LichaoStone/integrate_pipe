package com.allook.local.bean;

import com.allook.frame.BaseEntity;

public class LocalBean extends BaseEntity{
	private static final long serialVersionUID = 6950962824413386643L;
	
	private String modelType;

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}
