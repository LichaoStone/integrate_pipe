package com.allook.local.bean;

import com.allook.frame.BaseEntity;
/**
 * 数据字典Bean类
 * @作者 栗超
 * @时间 2018年5月31日 上午9:53:37
 * @说明
 */
public class DictionariesBean extends BaseEntity{
	private static final long serialVersionUID = -2105179264964123017L;
	
	private String dictionariesKey;
	private String dictionariesName;
	private String dictionariesValue;
	public String getDictionariesKey() {
		return dictionariesKey;
	}
	public void setDictionariesKey(String dictionariesKey) {
		this.dictionariesKey = dictionariesKey;
	}
	public String getDictionariesName() {
		return dictionariesName;
	}
	public void setDictionariesName(String dictionariesName) {
		this.dictionariesName = dictionariesName;
	}
	public String getDictionariesValue() {
		return dictionariesValue;
	}
	public void setDictionariesValue(String dictionariesValue) {
		this.dictionariesValue = dictionariesValue;
	}
}
