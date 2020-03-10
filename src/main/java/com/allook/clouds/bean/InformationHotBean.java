package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 资讯热榜
 * @作者 栗超
 * @时间 2018年5月18日 下午4:31:23
 * @说明
 */
public class InformationHotBean extends BaseEntity{

	private static final long serialVersionUID = 7557963419428745980L;
	private String informationHotKey;
	private String showStatus;
	/**同步时间**/
	private String synchronizationTime;
	/**标题**/
	private String informationTitle;
	/**pv数量**/
	private String pvCount;
	
	private String thumImg;
	
	
	
	public String getThumImg() {
		return thumImg;
	}
	public void setThumImg(String thumImg) {
		this.thumImg = thumImg;
	}
	public String getSynchronizationTime() {
		return synchronizationTime;
	}
	public void setSynchronizationTime(String synchronizationTime) {
		this.synchronizationTime = synchronizationTime;
	}
	public String getInformationTitle() {
		return informationTitle;
	}
	public void setInformationTitle(String informationTitle) {
		this.informationTitle = informationTitle;
	}
	public String getPvCount() {
		return pvCount;
	}
	public void setPvCount(String pvCount) {
		this.pvCount = pvCount;
	}
	public String getInformationHotKey() {
		return informationHotKey;
	}
	public void setInformationHotKey(String informationHotKey) {
		this.informationHotKey = informationHotKey;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	
}
