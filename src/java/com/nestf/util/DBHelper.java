/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author DELL
 */
public class DBHelper implements Serializable{
    public static Connection makeConnection() throws NamingException, SQLException{
        
//        1. get current system file
        Context context = new InitialContext();
        
//        2. get tomcat context 
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        
//        3. get Datasource from container 
        DataSource dataSource = (DataSource)tomcatContext.lookup("NestF");
        
//        4. get Connection
        Connection connection = dataSource.getConnection();
        
        return connection;
        
////        1. Load Driver
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
////         2 Make connection String: protocol:ip:port;databaseName=db;
//            String url = "jdbc:sqlserver://localhost:1433;databaseName=SinhVien;instanceName=SQLEXPRESS";
//           
//
////        3. Open Connection
//            Connection conection = DriverManager.getConnection(url, "sa", "202512");
//            return conection;
        
    }
}
