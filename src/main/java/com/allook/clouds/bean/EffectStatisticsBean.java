package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 影响力统计
 * @作者 栗超
 * @时间 2018年5月18日 下午2:04:00
 * @说明
 */
public class EffectStatisticsBean extends BaseEntity implements Comparable<EffectStatisticsBean>{
	private static final long serialVersionUID = 1835234336406439040L;
	/**主键key**/
	private String effectStatisticsKey;
	/**pv数量**/
	private String pvCount;
	/**uv数量**/
	private String uvCount;
	/**统计时间**/
	private String statisticsTime;
	
	
	
	public String getPvCount() {
		return pvCount;
	}
	public void setPvCount(String pvCount) {
		this.pvCount = pvCount;
	}
	public String getUvCount() {
		return uvCount;
	}
	public void setUvCount(String uvCount) {
		this.uvCount = uvCount;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	public String getEffectStatisticsKey() {
		return effectStatisticsKey;
	}
	public void setEffectStatisticsKey(String effectStatisticsKey) {
		this.effectStatisticsKey = effectStatisticsKey;
	}
	
	@Override
	public int compareTo(EffectStatisticsBean o) {
		return this.statisticsTime.compareTo(((EffectStatisticsBean)o).statisticsTime);
	}
}
