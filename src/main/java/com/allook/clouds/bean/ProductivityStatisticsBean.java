package com.allook.clouds.bean;

import com.allook.frame.BaseEntity;
/**
 * 生产力统计
 * @作者 栗超
 * @时间 2018年5月18日 下午2:04:39
 * @说明
 */
public class ProductivityStatisticsBean extends BaseEntity{
	private static final long serialVersionUID = 7229632717867643532L;
	private String productivityStatisticsKey;
	/**轻快账号**/
    private String qkAccountNumber;
    /**发稿数量**/
    private String count;
    /**统计时间**/
    private String statisticsTime;
	
    private String qk_account_name;
   
    

	public String getQk_account_name() {
		return qk_account_name;
	}
	public void setQk_account_name(String qk_account_name) {
		this.qk_account_name = qk_account_name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getQkAccountNumber() {
		return qkAccountNumber;
	}
	public void setQkAccountNumber(String qkAccountNumber) {
		this.qkAccountNumber = qkAccountNumber;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	public String getProductivityStatisticsKey() {
		return productivityStatisticsKey;
	}
	public void setProductivityStatisticsKey(String productivityStatisticsKey) {
		this.productivityStatisticsKey = productivityStatisticsKey;
	}
	
}
