package com.allook.mobile.mapper;

import java.util.List;

import com.allook.local.bean.DictionariesBean;
import com.allook.local.bean.SelectedTopicBean;
import com.allook.mobile.bean.DistributionTaskBean;
import com.allook.mobile.bean.FileBean;
import com.allook.mobile.bean.MobileBean;
import com.allook.mobile.bean.NoticeBean;
import com.allook.mobile.bean.PunchBean;
import com.allook.mobile.bean.UserBean;
import com.allook.mobile.bean.UserCheckBean;
/**
 * 轻快发布Dao接口
 * @作者 栗超
 * @时间 2018年5月14日 上午9:12:17
 * @说明
 */
public interface MobileSqlMapper {
	/**
	 * 获取通话记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
    public List<?> callRecords(MobileBean bean) throws Exception;
    /**
     * 获取外采任务未读条数
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer homeNoRead(MobileBean bean) throws Exception;
    /**
     * 外采任务设置为已读
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer externalTaskReadJob(DistributionTaskBean bean) throws Exception;
    
    /**
     * 获取用户详情
     * @param bean
     * @return
     * @throws Exception
     */
    public UserBean homeUserDetailInfo(UserBean bean) throws Exception;
    /**
     * 删除附件
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer fileDelete(FileBean bean) throws Exception;
    /**
     * 修改外采任务的状态
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer externalTaskModify(DistributionTaskBean bean)throws Exception;
    
    /**
     * 上传附件信息入库
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer fileAdd(FileBean bean) throws Exception;
    /**
     * 修改附件名称
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer fileUpdate(FileBean bean) throws Exception;
    /**
     * 获取外采任务详情
     * @param bean
     * @return
     * @throws Exception
     */
    public DistributionTaskBean externalTaskDetail(DistributionTaskBean bean) throws Exception;
    /**
     * 获取外采任务的附件列表
     * @param bean
     * @return
     * @throws Exception
     */
    public List<?> fileList(FileBean bean) throws Exception;
    /**
     * 外采任务列表
     * @param bean
     * @return
     * @throws Exception
     */
    public List<?> externalTasLlist(DistributionTaskBean bean) throws Exception;
    /**
     *  获取小编近七天的考核信息
     * @param bean
     * @return
     * @throws Exception
     */
    public UserCheckBean checkUserCheckInfo(UserCheckBean bean) throws Exception;
    /**
     * 排行榜信息
     * @param bean
     * @return
     * @throws Exception
     */
    public List<?> checkRankList(UserCheckBean bean) throws Exception;
    
    /**
     * 更新用户云信注册密码
     * @param bean
     * @return
     * @throws Exception
     */
    public Integer updateUserInfo(UserBean bean) throws Exception;
    /**
     * @param tmpbean
     * @return
     * @throws Exception 
     */
	public int addNotice(NoticeBean tmpbean) throws Exception;
	/**
	 * 获取选题
	 * @param tmpbean
	 * @return
	 * @throws Exception
	 */
	public SelectedTopicBean getSelectedTopic(SelectedTopicBean tmpbean) throws Exception;
	
	/**
	 * 获取所有应用用户key值(t_user.user_type='1')
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getAppUserKeys(NoticeBean bean) throws Exception;
	
	/**
	 * 消息提醒关系表
	 * @param tmpbean
	 * @return
	 * @throws Exception
	 */
	public int addNoticeRelationship(NoticeBean noticeBean) throws Exception;
	
	public int updateIsReturnContent(DistributionTaskBean noticeBean) throws Exception;
	
	/**
	 * 获取部门列表
	 * @return
	 * @throws Exception
	 */
	public List<?> getDepartmentList() throws Exception;
	
	/**
	 * 获取我的选题列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getSelectTopicList(SelectedTopicBean bean) throws Exception;
	
	/**
	 * 新建报题
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int addTopic(SelectedTopicBean bean) throws Exception;
	
	/**
	 * 审核列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getAuditList(SelectedTopicBean bean) throws Exception;
	
	/**
	 * 获取字典表设置
	 * @return
	 * @throws Exception
	 */
	public DictionariesBean getDictonaries(String code) throws Exception;
	
	/**
	 * 查询打卡记录列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<?> getPunchList(PunchBean bean) throws Exception;
	
	/**
	 * 检查是否有打卡记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkPunchByDate(PunchBean bean) throws Exception;
	
	/**
	 * 插入打卡记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int addPunch(PunchBean bean) throws Exception;
	
	/**
	 * 更新打卡记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updatePunch(PunchBean bean) throws Exception;
}
