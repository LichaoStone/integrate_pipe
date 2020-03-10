package com.allook.local.bean;

import com.allook.frame.BaseEntity;
/**
 * 列表自定义
 * @作者 栗超
 * @时间 2018年6月4日 上午9:52:36
 * @说明
 */
public class ListSettingBean extends BaseEntity{
	private static final long serialVersionUID = 6009050152809147252L;
    /**列表自定义key**/
	private String listSettingKey;
	/**列表自定义名称**/
	private String listSettingName;
	/**列表自定义类型:0:单行多列;1：两行多列 2：三行多列**/
	private String listSettingType;
	/**张氏状态**/
	private String showStatus;
	/**列表自定义行表key**/
	private String listRowSettingKey;
	/**列表自定义行表值**/
	private String listRowSettingValue;
	/**列表自定义列表key**/
	private String listColumnSettingKey;
	/**列表自定义列表值**/
	private String listColumnSettingName;

	public String getListSettingKey() {
		return listSettingKey;
	}

	public void setListSettingKey(String listSettingKey) {
		this.listSettingKey = listSettingKey;
	}

	public String getListSettingName() {
		return listSettingName;
	}

	public void setListSettingName(String listSettingName) {
		this.listSettingName = listSettingName;
	}

	public String getListSettingType() {
		return listSettingType;
	}

	public void setListSettingType(String listSettingType) {
		this.listSettingType = listSettingType;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getListRowSettingKey() {
		return listRowSettingKey;
	}

	public void setListRowSettingKey(String listRowSettingKey) {
		this.listRowSettingKey = listRowSettingKey;
	}

	public String getListRowSettingValue() {
		return listRowSettingValue;
	}

	public void setListRowSettingValue(String listRowSettingValue) {
		this.listRowSettingValue = listRowSettingValue;
	}

	public String getListColumnSettingKey() {
		return listColumnSettingKey;
	}

	public void setListColumnSettingKey(String listColumnSettingKey) {
		this.listColumnSettingKey = listColumnSettingKey;
	}

	public String getListColumnSettingName() {
		return listColumnSettingName;
	}

	public void setListColumnSettingName(String listColumnSettingName) {
		this.listColumnSettingName = listColumnSettingName;
	}
}
