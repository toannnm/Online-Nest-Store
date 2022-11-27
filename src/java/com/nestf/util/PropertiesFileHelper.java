/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author DELL
 */
public class PropertiesFileHelper {
    
    public static Properties getProperties(ServletContext context, String fileRelativePath) throws IOException{
        InputStream input = context.getResourceAsStream(fileRelativePath);
        Properties prop = null;
        prop = new Properties();
        prop.load(input);
        return prop;
    }
    
}
