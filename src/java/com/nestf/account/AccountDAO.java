/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.account;

import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class AccountDAO {
              
    private static final String LOGIN = "SELECT * FROM [tblAccount] WHERE phone = ? AND password = ?";
    public AccountDTO checkLogin(String phone, String password) throws SQLException, NamingException {
        AccountDTO account = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, phone);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    account = new AccountDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getDouble(6), rs.getString(7), rs.getBoolean(8));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return account;
    }
    private static final String CHECK_DUPLICATE_ACCOUNT = "SELECT * FROM [tblAccount] WHERE [phone] = ?";
    public AccountDTO getUserByPhone(String phone) throws SQLException, NamingException {
        AccountDTO account = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE_ACCOUNT);
                ptm.setString(1, phone);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    account = new AccountDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getDouble(6), rs.getString(7));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return account;
    }
    private static final String INSERT = "INSERT INTO [tblAccount]([phone], [password], [name], [address], [gender], [point], [role]) "
            + "VALUES(?,?,?,?,?,?,?)";
    public boolean insert(AccountDTO account) throws SQLException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, account.getPhone());
                ptm.setString(2, account.getPassword());
                ptm.setString(3, account.getName());
                ptm.setString(4, account.getAddress());
                ptm.setBoolean(5, account.isGender());
                ptm.setDouble(6, account.getPoint());
                ptm.setString(7, account.getRole());
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    
    private static final String UPDATE_NAME = "UPDATE [tblAccount] SET [name]= ? "
            + " WHERE [phone]=?";    
    public boolean updateName(String phone, String newName) throws SQLException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_NAME);
                ptm.setString(1, newName);
                ptm.setString(2, phone);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
        }
        return check;
    }

    private static final String UPDATE_ADDRESS = "UPDATE [tblAccount] SET [address]=? "
            + " WHERE [phone]=?";   
    public boolean updateAddress(String phone, String newAddress) throws SQLException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_ADDRESS);
                ptm.setString(1, newAddress);
                ptm.setString(2, phone);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
        }
        return check;
    }

    private static final String UPDATE_PASSWORD = "UPDATE [tblAccount] SET [password]=? "
            + " WHERE [phone]=?";
    public boolean updatePassword(String phone, String newPass) throws SQLException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PASSWORD);
                ptm.setString(1, newPass);
                ptm.setString(2, phone);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
        }
        return check;
    }
    final String UPDATE_POINT = "UPDATE [tblAccount] SET [point] = ? WHERE phone = ?";
    public boolean updatePoint(double point, String phone) throws SQLException, NamingException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_POINT);
                ptm.setDouble(1, point);
                ptm.setString(2, phone);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
        }
        return check;
    }
}
