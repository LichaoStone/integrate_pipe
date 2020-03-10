package com.allook.wechat.bean;

import com.allook.frame.BaseEntity;

public class ManuscriptBean extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String manuId;
	/**
	 * subject（主体表-主键）
	 */
	private String subId;
	/**
	 * 标题
	 */
	private String manuTitle;
	/**
	 * 内容
	 */
	private String manuContent;
	
	private String manuImgUrl;
	private String manuCreateTime;
	/**
	 * 创建人
	 */
	private String manuUserId;
	
	/**
	 * 提交状态：
	 * 	  0：未提交
	 *    1：已提交
	 */
	private String manuState;
	
	/**
	 * 选题/稿件
	 */
	private String manuUsage;
	
	/**
	 * 专题名称
	 * */
	private String subTitle;
	
	/**
	 * 类型
	 */
	private String type;
	
	private String customerImg;
	
	private String manuSubTime;
	
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getManuId() {
		return manuId;
	}
	public void setManuId(String manuId) {
		this.manuId = manuId;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getManuTitle() {
		return manuTitle;
	}
	public void setManuTitle(String manuTitle) {
		this.manuTitle = manuTitle;
	}
	public String getManuContent() {
		return manuContent;
	}
	public void setManuContent(String manuContent) {
		this.manuContent = manuContent;
	}
	public String getManuImgUrl() {
		return manuImgUrl;
	}
	public void setManuImgUrl(String manuImgUrl) {
		this.manuImgUrl = manuImgUrl;
	}
	public String getManuCreateTime() {
		return manuCreateTime;
	}
	public void setManuCreateTime(String manuCreateTime) {
		this.manuCreateTime = manuCreateTime;
	}
	public String getManuUserId() {
		return manuUserId;
	}
	public void setManuUserId(String manuUserId) {
		this.manuUserId = manuUserId;
	}
	public String getManuState() {
		return manuState;
	}
	public void setManuState(String manuState) {
		this.manuState = manuState;
	}
	public String getManuUsage() {
		return manuUsage;
	}
	public void setManuUsage(String manuUsage) {
		this.manuUsage = manuUsage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCustomerImg() {
		return customerImg;
	}
	public void setCustomerImg(String customerImg) {
		this.customerImg = customerImg;
	}
	public String getManuSubTime() {
		return manuSubTime;
	}
	public void setManuSubTime(String manuSubTime) {
		this.manuSubTime = manuSubTime;
	}
	
}
