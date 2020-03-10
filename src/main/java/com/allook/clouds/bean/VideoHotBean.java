package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;

public class VideoHotBean extends BaseEntity{
	private static final long serialVersionUID = -2428277569105653004L;
	private String videoHotKey;
	private String showStatus;
	/**同步时间**/
	private String synchronizationTime;
	/**标题**/
    private String informationTitle;
    /**播放数量**/
    private String playCount;
    /**来源**/
    private String source;
    /**视频url**/
    private String videoUrl;
    private String thumImg;
	public String getSynchronizationTime() {
		return synchronizationTime;
	}
	public void setSynchronizationTime(String synchronizationTime) {
		this.synchronizationTime = synchronizationTime;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getInformationTitle() {
		return informationTitle;
	}
	public void setInformationTitle(String informationTitle) {
		this.informationTitle = informationTitle;
	}
	public String getVideoHotKey() {
		return videoHotKey;
	}
	public void setVideoHotKey(String videoHotKey) {
		this.videoHotKey = videoHotKey;
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
	
}
