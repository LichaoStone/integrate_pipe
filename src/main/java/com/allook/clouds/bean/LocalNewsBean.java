package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 地方新闻搜索Bean类
 * @作者 栗超
 * @时间 2018年5月14日 上午8:48:20
 * @说明
 */
public class LocalNewsBean extends BaseEntity{

	private static final long serialVersionUID = 7200895845067481730L;
	private String hotspotNewsClueKey;
	/**标题**/
	private String hotspotNewsClueTitle;
	/**简介**/
	private String hotspotNewsClueDescript;
	/**来源**/
	private String source;
	private String type;
	private String showStatus;
	
	/**缩略图**/
	private String thumImg;
	/**静态页url**/
	private String htmlUrl;
	
	public String getHotspotNewsClueTitle() {
		return hotspotNewsClueTitle;
	}
	public void setHotspotNewsClueTitle(String hotspotNewsClueTitle) {
		this.hotspotNewsClueTitle = hotspotNewsClueTitle;
	}
	public String getHotspotNewsClueDescript() {
		return hotspotNewsClueDescript;
	}
	public void setHotspotNewsClueDescript(String hotspotNewsClueDescript) {
		this.hotspotNewsClueDescript = hotspotNewsClueDescript;
	}
	public String getSource() {
		return source;      
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getHotspotNewsClueKey() {
		return hotspotNewsClueKey;
	}
	public void setHotspotNewsClueKey(String hotspotNewsClueKey) {
		this.hotspotNewsClueKey = hotspotNewsClueKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public String getThumImg() {
		return thumImg;
	}
	public void setThumImg(String thumImg) {
		this.thumImg = thumImg;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
}
