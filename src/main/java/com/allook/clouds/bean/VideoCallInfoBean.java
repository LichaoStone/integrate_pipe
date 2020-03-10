package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 视频连接信息Bean类
 * @作者 栗超
 * @时间 2018年5月14日 上午8:52:34
 * @说明
 */
public class VideoCallInfoBean extends BaseEntity{
	private static final long serialVersionUID = 7694486141697654432L;
	
	private String conversationRecordKey;
	/**通话发起者ID**/
	private String initiativeId;
	/**通话发起者时长**/
	private String initiativeDuration;
	/**接听者ID**/
	private String passiveId;
	/**接听者时长**/
	private String passiveDuration;
	/**通道号**/
	private String channelId;
	/**此通通话/白板的通话时长，精确到秒，可转为Integer类型**/
	private String duration;
	/**为5，表示是实时音视频/白板时长类型事件**/
	private String eventType;
	/**是否是互动直播的音视频，0：否，1：是**/
	private String live;
	/**
	 * 表示通话/白板的参与者:
	 * 			accid为用户帐号； 如果是通话的发起者的话，
	 * 			caller字段为true，否则无caller字段； 
	 * 			duration表示对应accid用户的单方时长，其中白板消息暂无此单方时长的统计
     **/
	private String members;
	/**音视频发起时的自定义字段，可选，由用户指定**/
	private String ext;
	/**类型： AUDIO：表示音频通话； VEDIO：表示视频通话； DataTunnel：表示白板事件**/
	private String type;
	/**
	 * 若为true表示超长时长通话的过程中的抄送，缺省或者false表示普通时长通话的抄送或者超长时长通话的最后一次抄送
	 * */
	private String runing;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getLive() {
		return live;
	}
	public void setLive(String live) {
		this.live = live;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRuning() {
		return runing;
	}
	public void setRuning(String runing) {
		this.runing = runing;
	}
	public String getConversationRecordKey() {
		return conversationRecordKey;
	}
	public void setConversationRecordKey(String conversationRecordKey) {
		this.conversationRecordKey = conversationRecordKey;
	}
	public String getInitiativeId() {
		return initiativeId;
	}
	public void setInitiativeId(String initiativeId) {
		this.initiativeId = initiativeId;
	}
	public String getInitiativeDuration() {
		return initiativeDuration;
	}
	public void setInitiativeDuration(String initiativeDuration) {
		this.initiativeDuration = initiativeDuration;
	}
	public String getPassiveId() {
		return passiveId;
	}
	public void setPassiveId(String passiveId) {
		this.passiveId = passiveId;
	}
	public String getPassiveDuration() {
		return passiveDuration;
	}
	public void setPassiveDuration(String passiveDuration) {
		this.passiveDuration = passiveDuration;
	}
}
