package com.allook.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量接口
 * @作者 栗超
 * @时间 2018年5月12日 上午10:18:36
 * @说明
 */
public abstract class Constants {
	/**
	 * 页面标识名称
	 */
	public static final String CLIENT_ID="pageFlag";
	/**
	 * 初始化动作标识名称
	 */
	public static final String ACTION_INIT="actionFlag";
	
	/**
	 * 标志是否为视频通话大屏创建的websocket
	 */
	public static final String WEB_CALL_FLAG="webCallFlag";
	
	/**默认排序:10000**/
	public static final String ORDER_NUMBER_DEFAULT="10000";
	
	
	/**
	 * 展示状态
	 * @作者 栗超
	 * @时间 2018年5月22日 上午10:37:04
	 * @说明
	 */
	public  static  class SHOW_STATUS{
		/**显示**/
		public static final String  SHOW="1";
		/**隐藏**/
		public static final String  HIDDEN="0";
	}
	
	/**
	 * 系统状态码
	 * @作者 栗超
	 * @时间 2018年5月12日 上午10:34:42
	 * @说明
	 */
    public static  class CODE{
    	/**成功**/
    	public static final Integer  SUCCESS=200;
    	/**失败**/
    	public static final Integer  FAIL=500;
    }
    
    
    /**
     * 业务逻辑码
     * @作者 栗超
     * @时间 2018年5月12日 上午10:34:55
     * @说明
     */
    public static class RET{
    	/**************************公共-START*******************************/
    	/**成功**/
    	public static final Integer  SUCCESS=200;
    	/**失败**/
    	public static final Integer  FAIL=500;
    	/***************************公共-END******************************/
    	
    	
    	
    	/*************************大屏-START********************************/
    	/**请求非法**/
    	public static final Integer  ILLEGAL=201;
    	/**请求过期失效**/
    	public static final Integer  LOSE_EFFICACY=202;
    	/**缺少参数:_timestamp或_sign**/
    	public static final Integer  MISSING_PARAMETER=203;
    	/**大屏模块名称不正确**/
    	public static final Integer  MODULE_ERROR=204;
    	/**请求ACTION行为不存在:simple,detail,localVideoCall和notice**/
    	public static final Integer  ACTION_ERROR=205;
    	/**视频通话已经被锁定**/
    	public static final Integer  VIDEO_CALL_LOCK=206;
    	/**发送消息到大屏失败**/
    	public static final Integer  SEND_MESSAGE_ERROR=207;
    	/**不允许自动更新数据**/
    	public static final Integer  NOT_ALLOW_AUTO_REFRESH_SCREEN=208;
    	/**占线...**/
    	public static final Integer  CALL_BUSSY=3001;
    	/**视频通话时长不够**/
    	public static final Integer  NO_MORE_CALLTIME=3002;
    	/***************************大屏-END******************************/
    	
    	
    	/*************************微信小程序-START********************************/
    	
    	/**
    	 * @时间:2019-07-25
    	 * @作者:栗超
    	 * @说明:
    	 * 		融媒V3.1轻快发布中功能抽离出来单独行成客户端，登陆错误码与小程序共用 
    	 */
    	
    	/**登陆密码错误**/
    	public static final Integer ERROR_PASSWORD=401;
    	/**账号错误**/
    	public static final Integer NO_USER=402;
    	/**session过期,长时间未操作下线**/
    	public static final Integer SESSION_LOSE_EFFICACY=403;
    	/**其他用户登陆，强制下线**/
    	public static final Integer MUST_OFFLINE=405;
    	/**非通讯员**/
    	public static final Integer ERROR_ROLE=406;
    	/**用户禁用**/
    	public static final Integer USER_LOCKED=407;
    	/*************************微信小程序-END********************************/
    }
    
    /**
     * 类型
     * @作者 栗超
     * @时间 2018年7月18日 上午8:36:58
     * @说明
     */
    public static class TYPE{
    	/**
    	 * 后台发起视频通话
    	 */
    	public static final String   NET_CALL="netCall";
    	/**
    	 * 触摸屏发起视频通话
    	 */
    	public static final String 	 WEB_CALL="webCall";
    	/**
    	 * 大屏发送通话状态到后台，通信标志
    	 */
    	public static final String 	 RET="ret";
    	/**
    	 * 大屏挂断通话
    	 */
    	public static final String 	 WEB_HANGUP="webHangUp";
    	
    	/**
    	 * 占线和视频通话时长不够
    	 */
    	public static final String 	 NOT_CALL="notCall";
    }
    
    
    /**
     * 模块类型
     * @作者 栗超
     * @时间 2018年5月25日 下午5:28:49
     * @说明
     */
    public static class MODULE{
    	/**全网热点**/
     	public static final String QWRD = "qwrd";
     	/**本地新闻**/
     	public static final String BDXW = "bdxw";
     	/**网络热搜**/
     	public static final String WLRS = "wlrs";
     	/**地方舆论**/
     	public static final String DFYL = "dfyl";
     	/**新闻选题**/
     	public static final String XWXT = "xwxt";
     	/**外采调度**/
     	public static final String WCDD = "wcdd";
     	/**生产力统计**/
     	public static final String SCLTJ = "scltj";
     	/**影响力统计**/
     	public static final String YXLTJ = "yxltj";
     	/**任务统计**/
     	public static final String RWTJ = "rwtj";
     	/**资讯热榜**/
     	public static final String ZXRB = "zxrb";
     	/**视频热榜**/
     	public static final String SPRB = "sprb";
     	/**列表自定义**/
     	public static final String LBZDY = "lbzdy";
     	/**图表自定义**/
     	public static final String TBZDY = "tbzdy";
     	/**视频通话**/
     	public static final String VIDEOCALL = "videocall";
     	/**传习中心**/
     	public static final String CXZX = "cxzx";
     	/**V2.0 微信矩阵**/
     	public static final String WXJZ = "wxjz";
     	
     	/**V3.0 现场直播**/
     	public static final String XCZB="xczb";
     	/**V3.0 考勤管理**/
     	public static final String KQGL="kqgl";
     	/**V3.0 通讯联动**/
     	public static final String LDGL="ldgl";
     	/**V3.0 爆料管理**/
     	public static final String BLGL="blgl";
    }
    
    /**
     * kafka主题
     * @author lichao
     *
     */
    public static class KAFKA_TOPIC{
    	/**资讯**/
    	public static final String INFORMATION = "informationTopic";
    	/**网络热搜**/
    	public static final String HOT_WORD = "hotWordsTopic";
    	/**影响力统计**/
    	public static final String EFFECT = "effectTopic";
    	/**生产力统计**/
    	public static final String PRODUCT = "productTopic";
    	/**地方舆论监控**/
    	public static final String COMMENT = "commentTopic";
    	/**视频热榜**/
    	public static final String VIDEO_HOT = "videoHotTopic";
    	/**资讯热榜**/
    	public static final String NEWS_HOT = "newsHotTopic";
    	/**视频通话**/
    	public static final String VIDEO_CALL = "videoCallTopic";
    	/**爆料管理**/
    	public static final String REPORT= "reportTopic";
    	
    	
    }
 	
    /**
     * 操作类型
     * @作者 栗超
     * @时间 2018年6月1日 上午9:57:13
     * @说明
     */
    public static class ACTION{
    	public static final String SIMPLE = "simple";
    	/**详情信息**/
    	public static final String DETAIL = "detail";
    	/**后台连接:视频通话**/ 
    	public static final String LOCAL_VIDEOCALL = "localVideoCall";
    	/**后台连接:消息提醒**/ 
    	public static final String LOCAL_NOTICE = "notice";
    }
    
    
    public static class COMMAND{
    	public static final String CALL = "call";
    	public static final String HANGUP = "hangUp";
    }
    
    
    /**
     * 视频通话锁,默认关闭
     */
    public static  String VIDEO_CALL_LOCK="OFF";
    /**
     * 视频通话锁
     * @作者 栗超
     * @时间 2018年6月27日 下午5:21:39
     * @说明
     */
    public static class VIDEOCALLLOCK{
    	/**锁关闭**/
    	public static final String OFF = "OFF";
    	/**锁打开**/
    	public static final String ON  = "ON";
    }
    
    
    /**
     * 
     * @作者 栗超
     * @时间 2018年6月6日 下午6:50:36
     * @说明
     */
    public static class MSG_STATUS{
    	 /**处理成功**/
    	 public static final String SUCCESS = "OK";
    	 /**处理失败**/
    	 public static final String FAIL = "FAIL";
    }
    
    /**
     * 访问权限验证是否开启	
     * @作者 栗超
     * @时间 2018年7月16日 上午11:12:51
     * @说明
     */
	public  static  class ACCESS_PERMISSIONS{
		/**开启**/
		public static final String  ON="ON";
		/**关闭**/
		public static final String  OFF="OFF";
	}
    
	
	/**
     * 时间类型
     * @作者 栗超
     * @时间 2018年7月16日 上午11:12:51
     * @说明
     */
	public  static  class DATE_TYPE{
		/**昨天**/
		public static final String  YESTERDAY="0";
		/**最近七天**/
		public static final String  THE_LAST_SEVEN_DAYS="1";
		/**最近三十天**/
		public static final String  THE_LAST_THIRTY_DAYS="2";
		/**本月**/
		public static final String  CURRENT_MONTH="3";
		/**今天**/
		public static final String  TODAY="4";
	}
	
	
	/**
	 * 数据字典
	 * @author lichao
	 *
	 */
	public  static  class  DICTIONARIES{
		/**
		 * 微信发布次数
		 */
		public static final String WECHAT_PUBLISH_TIMES="wechat_publish_times";
		/**
		 * 微信发稿量
		 */
		public static final String  WECHAT_PUBLISH_COUNT="wechat_publish_count";
		/**
		 * 微信审核数
		 */
		public static final String  WECHAT_AUDITED_COUNT="wechat_audited_count";
		
		
		
		/*********************V3.0 START****************************/
		/**
		 * 每日发稿量
		 */
		public static final String  DAILY_DISPATCH="dailyDispatch";
		/**
		 * 每日发稿量打分占比
		 */
		public static final String DAILY_DISPATCH_RATIO="dailyDispatchRatio";
		/**
		 * 每日pv
		 */
		public static final String  DAILY_PV="dailyPv";
		/**
		 * 每日pv打分占比
		 */
		public static final String  DAILY_PV_RATIO="dailyPvRatio";
		/**
		 * 每日uv
		 */
		public static final String  DAILY_UV="dailyUv";
		/**
		 * 每日uv打分占比
		 */
		public static final String  DAILY_UV_DATIO="dailyUvDatio";
		
		/**
		 * 现场直播
		 */
		public static final String  DEFAULT_LIVE_STREAM="defaultLiveStream";
		public static final String  CUSTOME_LIVE_STREAM="customLiveStream";
		public static final String  SHOW_LIVE_STREAM="showLiveStream";
		/*********************V3.0 END****************************/
		
		
		/*********************V3.1 START****************************/
		
		/**
		 * 打卡经纬度
		 */
		public static final String  PUNCH_COORDINATE="punchCoordinate";
		
		/**
		 * 打卡范围
		 */
		public static final String  PUNCH_RANGE_RADIUS="punch_range_radius";
		
		/**
		 * 范围外打卡，用户需要填写备注
		 * 	  0:不需要 
		 *    1：需要
		 */
		public static final String  PUNCH_OUTSIDE_REASON_FLAG="punch_outside_reason_flag";
		/**
		 * 上午打卡时间段开始
		 */
		public static final String  PUNCH_MORNING_CARDING_BEGIN="punch_morning_carding_begin";
		/**
		 * 上午打卡时间段结束
		 */
		public static final String  PUNCH_MORNING_CARDING_END="punch_morning_carding_end";
		/**
		 * 下午打卡时间段开始
		 */
		public static final String  PUNCH_NOON_CARDING_BEGIN="punch_noon_carding_begin";
		/**
		 * 下午打卡时间段结束
		 */
		public static final String  PUNCH_NOON_CARDING_END="punch_noon_carding_end";
		
		/**
		 * 上午上班时间
		 */
		public static final String  PUNCH_MORNING_ON_TIME="punch_morning_on_time";
		/**
		 * 上午下班时间
		 */
		public static final String  PUNCH_MORNING_OFF_TIME="punch_morning_off_time";
		/**
		 * 下午上班时间
		 */
		public static final String  PUNCH_NOON_ON_TIME="punch_noon_on_time";
		/**
		 * 下午下班时间
		 */
		public static final String  PUNCH_NOON_OFF_TIME="punch_noon_off_time";
		
		/*********************V3.1 END****************************/
		
	}
        
    public static final Map<Integer,Object> CODE_INFO = new HashMap<Integer,Object>();
    public static final Map<Integer,Object> RET_INFO = new HashMap<Integer,Object>();
    public static final Map<Object,Object>  VIDEOCALL_INFO = new HashMap<Object,Object>();
    
    static{
    	//系统状态码信息
    	CODE_INFO.put(CODE.SUCCESS,"成功");
    	CODE_INFO.put(CODE.FAIL,"失败");
    	
    	//业务逻辑状态码信息
    	RET_INFO.put(RET.SUCCESS,"成功");
    	RET_INFO.put(RET.FAIL,"失败");
    	
    	RET_INFO.put(RET.ILLEGAL,"请求不合法");
    	RET_INFO.put(RET.LOSE_EFFICACY,"请求过期失效");
    	RET_INFO.put(RET.MISSING_PARAMETER,"缺少参数");
    	RET_INFO.put(RET.MODULE_ERROR,"模块名称错误");
    	RET_INFO.put(RET.VIDEO_CALL_LOCK,"视频通话已经被锁定");
    	RET_INFO.put(RET.SEND_MESSAGE_ERROR,"发送消息到大屏失败,请确定大屏是否正常打开");
    	RET_INFO.put(RET.CALL_BUSSY,"正在占线...");
    	RET_INFO.put(RET.NO_MORE_CALLTIME,"剩余通话时长不足");
    	RET_INFO.put(RET.NOT_ALLOW_AUTO_REFRESH_SCREEN,"大屏已停止自动更新");
    	RET_INFO.put(RET.ACTION_ERROR,"请求ACTION行为不存在");
    	
    	RET_INFO.put(RET.ERROR_PASSWORD,"密码错误");
    	RET_INFO.put(RET.NO_USER,"该用户不存在");
    	RET_INFO.put(RET.SESSION_LOSE_EFFICACY,"登陆过期");
    	RET_INFO.put(RET.MUST_OFFLINE,"强制下线");
    	RET_INFO.put(RET.ERROR_ROLE,"登陆用户非通讯员");
    	RET_INFO.put(RET.USER_LOCKED,"用户被禁用");
    	
    	
    	
    	VIDEOCALL_INFO.put("200","成功");
    	VIDEOCALL_INFO.put("1001","参数有误");
    	VIDEOCALL_INFO.put("1002","SDK连接失败");
    	VIDEOCALL_INFO.put("1003","账号密码有误");
    	VIDEOCALL_INFO.put("1004","当前系统不支持音视频功能，请使用win7、win10");
    	VIDEOCALL_INFO.put("1005","当前浏览器不支持音视频功能，请使用chrome浏览器");
    	VIDEOCALL_INFO.put("1006","检测PC-Agent插件失败");
    	VIDEOCALL_INFO.put("1007","账号在其它地方登录（被踢）");
    	VIDEOCALL_INFO.put("1008","被叫人离线");
    	VIDEOCALL_INFO.put("1009","无人接听（超时）");
    	VIDEOCALL_INFO.put("10010","对方忙碌（1、对方拒绝、2、对方正在通话）");
    	VIDEOCALL_INFO.put("10011","正在连线");
    	VIDEOCALL_INFO.put("10012","对方中止通话");
    	VIDEOCALL_INFO.put("2001","参数有误");
    	VIDEOCALL_INFO.put("2002","暂无连线");
    	VIDEOCALL_INFO.put("2003","执行挂断被叫账号与正在连线人账号不一致");
    	VIDEOCALL_INFO.put("9999","系统繁忙（未知错误）");
    }
    
}
