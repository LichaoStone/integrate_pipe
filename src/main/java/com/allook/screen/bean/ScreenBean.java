package com.allook.screen.bean;

import com.allook.frame.BaseEntity;
/**
 * 大屏Bean
 * @作者 栗超
 * @时间 2018年5月28日 下午1:45:31
 * @说明
 */
public class ScreenBean extends BaseEntity{
	private static final long serialVersionUID = -6483585725071956271L;
	/**屏幕展示模块配置key**/
	private String screenShowModelKey;
	/**任务统计屏幕数据显示key**/
	private String screenShowSettingKey;
	/**屏幕名称**/
	private String screenName;
	/**屏幕展示条数**/
	private String showCount;
	/**高亮秒数**/
	private String highlightSecond;
	/**
	 * 展示类型（
	 * 		qwrd：全网热点；
	 * 		wlrs：网络热搜；
	 * 		bdxw：本地线索；
	 * 		dfyl：地方舆论；
	 * 		xwxt：新闻选题；
	 * 		wcdd：外采调度；
	 * 		rwtj：任务统计；
	 * 		scltj：生产力统计；
	 * 		yxltj：影响力统计；
	 * 		zxrb：咨询热榜；
	 * 		sprb：视频热榜；
	 * 		lbzdy：列表自定义；
	 * 		tbzdy：图表自定义）
	 * **/
	private String showTypeName;
	/**自定义key**/
	private String userDefinedKey;
	/**屏幕编号**/
	private String screenNum;
	/**类型（
	 * 		0：全网实时热点；
	 * 		1：网络热搜排行；
	 * 		2：本地新闻线索； 
	 * 		3：地方舆论监控；
	 * 		4：新闻选题；
	 * 		5：外采调度；
	 * 		6：任务统计；
	 * 		7：生产力统计；
	 * 		8：资讯热榜；
	 * 		9：视频热榜；
	 * 		10）
	 * **/
	private String showType;
	/**
	 * 统计时间类型
	 * 		0：昨天任务量统计；
	 * 		1：最近7天任务量统计；
	 * 		2：最近30天任务量统计；
	 * 		3：本月任务量统计
	 * */
	private String timeType;
	public String getScreenShowModelKey() {
		return screenShowModelKey;
	}
	public void setScreenShowModelKey(String screenShowModelKey) {
		this.screenShowModelKey = screenShowModelKey;
	}
	public String getScreenShowSettingKey() {
		return screenShowSettingKey;
	}
	public void setScreenShowSettingKey(String screenShowSettingKey) {
		this.screenShowSettingKey = screenShowSettingKey;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getShowCount() {
		return showCount;
	}
	public void setShowCount(String showCount) {
		this.showCount = showCount;
	}
	public String getHighlightSecond() {
		return highlightSecond;
	}
	public void setHighlightSecond(String highlightSecond) {
		this.highlightSecond = highlightSecond;
	}
	public String getShowTypeName() {
		return showTypeName;
	}
	public void setShowTypeName(String showTypeName) {
		this.showTypeName = showTypeName;
	}
	public String getUserDefinedKey() {
		return userDefinedKey;
	}
	public void setUserDefinedKey(String userDefinedKey) {
		this.userDefinedKey = userDefinedKey;
	}
	public String getScreenNum() {
		return screenNum;
	}
	public void setScreenNum(String screenNum) {
		this.screenNum = screenNum;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
}
