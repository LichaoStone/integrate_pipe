package com.allook.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.allook.wechat.bean.StaticHtmlBean;

public class FileUtil {
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	/**
	 * 创建html静态文件
	 * @param request
	 * @param content html内容
	 * @return
	 */
	public static String createHtmlFile(HttpServletRequest request,StaticHtmlBean bean) {
		String htmlUrl = null ;
		String projectUrl=PropertiesUtil.getValue("projectUrl");
		String htmlSavePath = request.getSession().getServletContext().getRealPath("/").replace("integrate_pipe",projectUrl)+PropertiesUtil.getValue("cloudeHtmlUrl");
		String fileName = PkCreat.getTablePk()+".html" ;
		
		try {
			
			FileUtil.createFile(htmlSavePath,fileName);
			File file = new File(htmlSavePath+fileName);
			ThymleafEngineUtil.processTmp(bean,file,"ztlb-manuscript");
	        htmlUrl = PropertiesUtil.getValue("cloudeHtmlUrl").replace("datafiles","")+fileName ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlUrl ;	
	}
	
	
	/**
     * 复制文件
     * @param sourceFile 源文件地址及文件名
     * @param targetFile 目标文件地址及文件名
     * @throws IOException 
     */
	public static void copyFile(String sourceFile, String targetFile) throws IOException {
		logger.info("【复制文件】开始");
		logger.info("【复制文件】原文件："+sourceFile);
		logger.info("【复制文件】目标文件:"+targetFile);
		
		BufferedInputStream inBuff = null;
	    BufferedOutputStream outBuff = null;
        // 新建文件输入流并对它进行缓冲
        inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
        // 新建文件输出流并对它进行缓冲
        outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        
        // 刷新此缓冲的输出流
        outBuff.flush();
        // 关闭流
        if (inBuff != null) {
        	inBuff.close();
        }  
        if (outBuff != null) {
            outBuff.close();
        }
	    
	    logger.info("【复制文件】结束");
	}
	
	
	public static File createFile(String path,String fileName) throws IOException {
		File folder  = new File(path);
		if (!folder.isDirectory() && !folder.exists()) {
			folder.mkdirs();
		}
		File targetFile = new File(path+fileName);
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		return targetFile ;
	} 
	
	
	public static void mergeFile(String path,String targetPath,String targetFile) throws Exception {
		File file = new File(path);
		String[] fileNames = file.list();
		Arrays.sort(fileNames);
		byte[] bytes = new byte[1024*1024];
		int length;
		FileInputStream temp = null;//分片文件
		File targetFolder = new File(targetPath);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}
		FileOutputStream outputStream = new FileOutputStream(targetFile, true);
		for(String tempFileName : fileNames) {
			File tempFile = new File(path+tempFileName);
			temp = new FileInputStream(tempFile);
			while((length = temp.read(bytes))!=-1){
				outputStream.write(bytes, 0, length);					 
			}
			temp.close();
			tempFile.delete();
		}
		outputStream.flush();
		outputStream.close();
	}
}
