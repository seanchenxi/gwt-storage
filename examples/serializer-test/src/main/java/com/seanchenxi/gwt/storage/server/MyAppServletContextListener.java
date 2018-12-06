package com.seanchenxi.gwt.storage.server;

import com.seanchenxi.gwt.storage.shared.StorageUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");
		StorageUtils.PolicyLoader.load(arg0.getServletContext(), "storage_test");
	}
}
