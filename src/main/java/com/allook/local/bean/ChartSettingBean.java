package com.allook.local.bean;

import com.allook.frame.BaseEntity;
/**
 * 图标自定义
 * @作者 栗超
 * @时间 2018年5月30日 下午5:03:14
 * @说明
 */
public class ChartSettingBean extends BaseEntity{
	private static final long serialVersionUID = 6896306631009117935L;
	
	private String chartSettingKey;
    private String chartSettingName;
    private String chartSettingType;
    private String showStatus;
    
    private String longitudinalKey;
    private String longitudinalKey2;
    private String longitudinalName;
    private String longitudinalName2;
    private String chartDataSettingKey;
    private String chartDataSettingKey2;
    private String chartDataSettingName;
    private String chartDataSettingValue;
    private String chartDataSettingValue2;
    
    /**折线图顶部名称序号**/
    private Integer cloumnOrderNum;
    /**横轴数据序号**/
    private Integer axisNum;
    private String columnName;
    private String columnNameKey;
    
    
    
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnNameKey() {
		return columnNameKey;
	}
	public void setColumnNameKey(String columnNameKey) {
		this.columnNameKey = columnNameKey;
	}
	public Integer getCloumnOrderNum() {
		return cloumnOrderNum;
	}
	public void setCloumnOrderNum(Integer cloumnOrderNum) {
		this.cloumnOrderNum = cloumnOrderNum;
	}
	public Integer getAxisNum() {
		return axisNum;
	}
	public void setAxisNum(Integer axisNum) {
		this.axisNum = axisNum;
	}
	public String getChartDataSettingKey2() {
		return chartDataSettingKey2;
	}
	public void setChartDataSettingKey2(String chartDataSettingKey2) {
		this.chartDataSettingKey2 = chartDataSettingKey2;
	}
	public String getChartDataSettingValue2() {
		return chartDataSettingValue2;
	}
	public void setChartDataSettingValue2(String chartDataSettingValue2) {
		this.chartDataSettingValue2 = chartDataSettingValue2;
	}
	public String getLongitudinalKey2() {
		return longitudinalKey2;
	}
	public void setLongitudinalKey2(String longitudinalKey2) {
		this.longitudinalKey2 = longitudinalKey2;
	}
	public String getLongitudinalName2() {
		return longitudinalName2;
	}
	public void setLongitudinalName2(String longitudinalName2) {
		this.longitudinalName2 = longitudinalName2;
	}
	public String getLongitudinalKey() {
		return longitudinalKey;
	}
	public void setLongitudinalKey(String longitudinalKey) {
		this.longitudinalKey = longitudinalKey;
	}
	public String getLongitudinalName() {
		return longitudinalName;
	}
	public void setLongitudinalName(String longitudinalName) {
		this.longitudinalName = longitudinalName;
	}
	public String getChartDataSettingKey() {
		return chartDataSettingKey;
	}
	public void setChartDataSettingKey(String chartDataSettingKey) {
		this.chartDataSettingKey = chartDataSettingKey;
	}
	public String getChartDataSettingName() {
		return chartDataSettingName;
	}
	public void setChartDataSettingName(String chartDataSettingName) {
		this.chartDataSettingName = chartDataSettingName;
	}
	public String getChartDataSettingValue() {
		return chartDataSettingValue;
	}
	public void setChartDataSettingValue(String chartDataSettingValue) {
		this.chartDataSettingValue = chartDataSettingValue;
	}
	public String getChartSettingKey() {
		return chartSettingKey;
	}
	public void setChartSettingKey(String chartSettingKey) {
		this.chartSettingKey = chartSettingKey;
	}
	public String getChartSettingName() {
		return chartSettingName;
	}
	public void setChartSettingName(String chartSettingName) {
		this.chartSettingName = chartSettingName;
	}
	public String getChartSettingType() {
		return chartSettingType;
	}
	public void setChartSettingType(String chartSettingType) {
		this.chartSettingType = chartSettingType;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
}
