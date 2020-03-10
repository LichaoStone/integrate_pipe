package com.allook.monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.allook.utils.DateUtil;
import com.allook.utils.HttpUtils;
import com.allook.utils.MD5;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class MonitorClient {
	private static final String GET_CLIENTS="https://rmt1.hjxxwzx.cn/integrate_pipe/monitor/getClients";
	private static final String GET_CONNECTCOUNT="https://qkintegrate.qingk.cn/integrate_pipe/monitor/getConnectCount";
	private static final String GET_VIDEOCALLLOCK="https://qkintegrate.qingk.cn/integrate_pipe/monitor/getVideoCallLock";
	private static final String SET_VIDEOCALLLOCK="https://qkintegrate.qingk.cn/integrate_pipe/monitor/setVideoCallLock";
	private static final String GET_VIDEO_CALL_FLAG="http://10.0.219.25:8080/integrate_pipe/monitor/webCallSessionKey";
	
	
	private static String getNoParamURL(String url){
		String _timestamp=DateUtil.date2msec(DateUtil.getTimeToSec());
		String _sign=MD5.getMD5String("qingkintegrate_timestamp"+_timestamp+"qingkintegrate");
		return url+"?_timestamp="+_timestamp+"&_sign="+_sign;
	}
	
	private static List<String> getClients() throws Exception{
		String reString=HttpUtils.reqForGet(getNoParamURL(GET_CLIENTS));
		System.out.println(reString);
		reString=reString.substring(reString.indexOf("body")+8,reString.length());
		
		String[] clientsArr=reString.split(",");
		List<String> list=new ArrayList<String>();
		if (clientsArr!=null&&clientsArr.length>0) {
			for(int i=0;i<clientsArr.length;i++){
				String[] arr=clientsArr[i].split("=");
				list.add(arr[0]);
			}
		}
		
		Collections.sort(list);
		return list;
	}
	
	private static String getConnectCount() throws Exception{
		return HttpUtils.reqForGet(getNoParamURL(GET_CONNECTCOUNT));
	}
	
	private static String getVideoCallLock() throws Exception{
		return HttpUtils.reqForGet(getNoParamURL(GET_VIDEOCALLLOCK));
	}
	
	private static String setVideoCallLock(String status) throws Exception{
		String _timestamp=DateUtil.date2msec(DateUtil.getTimeToSec());
		String _sign=MD5.getMD5String("qingkintegratecommondadminstatus"+status+"_timestamp"+_timestamp+"qingkintegrate");
		return HttpUtils.reqForGet(SET_VIDEOCALLLOCK+"?_timestamp="+_timestamp+"&_sign="+_sign+"&status="+status+"&commond=admin");
	}
	
	private static String refreshScreen(String modelType){
		String _timestamp=DateUtil.date2msec(DateUtil.getTimeToSec());
		String _sign=MD5.getMD5String("qingkintegratemodelType"+modelType+"_timestamp"+_timestamp+"qingkintegrate");
		return HttpUtils.reqForGet("https://qkintegrate.qingk.cn/integrate_pipe/clouds/refreshScreen?_timestamp="+_timestamp+"&_sign="+_sign+"&modelType="+modelType);
	}
	
	
	private static String clearWebsocketSession(){
		String _timestamp=DateUtil.date2msec(DateUtil.getTimeToSec());
		String _sign=MD5.getMD5String("qingkintegrate_timestamp"+_timestamp+"qingkintegrate");
		return HttpUtils.reqForGet("https://qkdemo.qingk.cn/integrate_pipe/monitor/clearWebsocketSession?_timestamp="+_timestamp+"&_sign="+_sign);
	}
	
	
	private static String clearWebsocketSessionByKey(String key){
		String _timestamp=DateUtil.date2msec(DateUtil.getTimeToSec());
		String _sign=MD5.getMD5String("qingkintegratekey"+key+"_timestamp"+_timestamp+"qingkintegrate");
		return HttpUtils.reqForGet("https://qkintegrate.qingk.cn/integrate_pipe/monitor/clearWebsocketSessionByKey?_timestamp="+_timestamp+"&_sign="+_sign+"&key="+key);
	}
	
	/**
	 * ftp获取日志文件
	 * @return
	 * @throws JSchException
	 * @throws SftpException
	 */
	private static String getLogs() throws JSchException, SftpException{
		// ftp登录用户名
 		String userName = "root";
 		// ftp登录密码
 		String userPassword = "123abc";
 		Integer port = 22 ;
 		// ftp地址:直接IP地址
 		String server = "10.0.219.25";
 		// 创建的文件
 		String fileName = "clouds_info.log";
 		// 指定写入的目录
 		String path = "/webapp/tomcat8/logs/pipe/";
 		// 指定本地写入文件
 		String localPath="E:\\";
 		
 		
 		ChannelSftp sftp = null;
 		try {
 			JSch jsch = new JSch();
			jsch.getSession(userName, server, port);
 			
 			
 			Session sshSession = jsch.getSession(userName, server, port);
 			sshSession.setPassword(userPassword);
 			Properties sshConfig = new Properties();
 			sshConfig.put("StrictHostKeyChecking", "no");
 			sshSession.setConfig(sshConfig);
 			sshSession.connect();
 			Channel channel = sshSession.openChannel("sftp");
 			channel.connect();
 			sftp = (ChannelSftp) channel;
 			
 			
 			//4.指定要下载的目录
 			sftp.cd(path);
 			File file=new File("E:/clouds_info.log");
 			sftp.get("clouds_info.log", new FileOutputStream(file));
 			
 		} catch (IOException e) {
 		} finally {
 		}
 		
 		
		return localPath;
 	}
	
	public static void main(String[] args){
		try {
			//获取在线websocket
//			List<String> list=getClients();
//			System.out.println(list);
//			if (list!=null&&list.size()>0) {
//				for(Integer i=0;i<list.size();i++){
//					System.out.println(list.get(i));
//				}
//			}
			
			//获取在线总数
			//System.out.println(getConnectCount());
			//System.out.println(HttpUtils.reqForGet("https://www.baidu.com"));
			
			//获取视频通话锁状态
			//System.out.println(getVideoCallLock());
			
			//设置视频通话锁状态
			//System.out.println(setVideoCallLock("OFF"));
			
			//刷新屏幕数据
			//System.out.println(refreshScreen("qwrd"));
			
			//清空所有websocketsession连接
			//System.out.println(clearWebsocketSession());
			
			
			//根据key清空对应的websocketsession连接
			//System.out.println(clearWebsocketSessionByKey(""));
			
			//System.out.println(getLogs());
			
			//System.out.println(clearWebsocketSession());
			
			//String url="https://njcms.sdnjtv.com/integrate_pipe/home/publishCount";
			//System.out.println(HttpUtils.reqForGet(getNoParamURL(url)));
			//System.out.println(HttpUtils.reqForGet(getNoParamURL(GET_VIDEO_CALL_FLAG)));
			System.out.println(clearWebsocketSession());
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
