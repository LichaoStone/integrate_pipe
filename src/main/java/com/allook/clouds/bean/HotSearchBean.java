package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 网络热搜排行Bean类
 * @作者 栗超
 * @时间 2018年5月14日 上午8:44:14
 * @说明
 */
public class HotSearchBean extends BaseEntity{
	private static final long serialVersionUID = 127351037169522660L;
	private String hotSearchKey;
	private String showStatus;
	/**热搜内容**/
    private String hotSearchContent;
    /**热度**/
    private String hotDegree;
    /**来源**/
    private String source;
	public String getHotSearchContent() {
		return hotSearchContent;
	}
	public void setHotSearchContent(String hotSearchContent) {
		this.hotSearchContent = hotSearchContent;
	}
	public String getHotDegree() {
		return hotDegree;
	}
	public void setHotDegree(String hotDegree) {
		this.hotDegree = hotDegree;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getHotSearchKey() {
		return hotSearchKey;
	}
	public void setHotSearchKey(String hotSearchKey) {
		this.hotSearchKey = hotSearchKey;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
}
