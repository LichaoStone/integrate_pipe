package com.allook.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.allook.wechat.bean.StaticHtmlBean;


/**
 * 使用模板应用创建静态化页面
 * @author mayb
 *
 */
public class ThymleafEngineUtil {

	public static <T> void  processTmp(T bean, File file, String templateName) throws IOException {
		 //构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
      
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        
        //构造上下文Model
        Context context = new Context();
        context.setVariable("bean",bean);
       
        //渲染模板
        FileWriter writer = new FileWriter(file);
        templateEngine.process(templateName,context,writer);
	}
	
	
	public static void main(String[] args) {
		try {
			StaticHtmlBean	 nbv = new StaticHtmlBean();
			
			nbv.setManuContent("AAAAAAAAAAAAAA");
		    File file = new File("G://report/result.html");
		    
	        if(!file.exists()) {
	        	//file.mkdir();
	        	file.createNewFile();
	        }
	        
	        System.out.println("AAAAAAAAA");
			processTmp(nbv,file,"ztlb-manuscript");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
