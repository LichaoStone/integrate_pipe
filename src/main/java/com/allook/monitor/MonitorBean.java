package com.allook.monitor;
/**
 * 监控Bean类
 * @作者 栗超
 * @时间 2018年7月13日 下午1:24:39
 * @说明
 */
public class MonitorBean {
	/**
	 * 邮件标题
	 */
	private String emailTitle;
	/**
	 * 邮件正文
	 */
	private String emailContent;
	
	
	
	public String getEmailTitle() {
		return emailTitle;
	}
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	
}
