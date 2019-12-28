package com.gentle.atcrowdfunding.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gentle.atcrowdfunding.consts.AppConsts;

/*
 * web组件创建的步骤：
 * 		1.创建类实现特定的接口
 * 		2.在web.xml中配置全类名
 */
public class AppStartupListener implements ServletContextListener {
    
    public void contextDestroyed(ServletContextEvent sce)  { 
    }
    
    public void contextInitialized(ServletContextEvent sce)  { 
    	//将base标签的路径存入
    	sce.getServletContext().setAttribute(AppConsts.CONTEXT_PATH, sce.getServletContext().getContextPath());
    }
	
}
