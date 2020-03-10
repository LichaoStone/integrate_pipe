package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 地方舆论监控Bean类
 * @作者 栗超
 * @时间 2018年5月14日 上午8:44:59
 * @说明
 */
public class LocalOpinionBean extends BaseEntity{
	private static final long serialVersionUID = 941302987474589373L;
	private String localOpinionKey;
	/**评论内容**/
    private String commentsContent;
    /**头像url**/
    private String headImgUrl;
    /**用户昵称**/
    private String nickName;
    /**展示转固态**/
    private String showStatus;
	public String getCommentsContent() {
		return commentsContent;
	}
	public void setCommentsContent(String commentsContent) {
		this.commentsContent = commentsContent;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLocalOpinionKey() {
		return localOpinionKey;
	}
	public void setLocalOpinionKey(String localOpinionKey) {
		this.localOpinionKey = localOpinionKey;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
}
