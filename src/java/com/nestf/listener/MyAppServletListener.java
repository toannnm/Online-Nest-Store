package com.nestf.listener;

import com.nestf.util.PropertiesFileHelper;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class MyAppServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploying....");
        ServletContext context = sce.getServletContext();
        try {
            String siteMapLocation = context.getInitParameter("SITEMAPS_PATH");
            Properties siteMap = PropertiesFileHelper.getProperties(context, siteMapLocation);
            context.setAttribute("SITEMAP", siteMap);
            String authenticationPropertyLocation = context.getInitParameter("AUTHENTICATION_PATH");
            Properties authenticationProperty = PropertiesFileHelper.getProperties(context, authenticationPropertyLocation);
            context.setAttribute("AUTHENTICATION", authenticationProperty);
        } catch (IOException ex) {
            context.log("MyAppServletListener_IO" + ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Undeployed...");
    }
}
