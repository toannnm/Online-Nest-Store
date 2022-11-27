/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.dao.ADMIN;

import com.nestf.account.AccountDTO;
import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ADMIN
 */
public class CustomerDAOAdmin {
    
    public static String GET_ALL_CUSTOMER = "SELECT phone, password, name, address, gender, point, role, status\n" +
                                            "FROM tblAccount\n" +
                                            "WHERE role = 'US'";

    public static String GET_SUCCESS_LIST = "SELECT a.phone, a.name, a.address, a.status, b.billID, s.status  as billStatus, b.total, b.cancelReason\n"
            + "FROM tblAccount a\n"
            + "Left JOIN tblBill b \n"
            + "ON a.phone = b.cusPhone\n"
            + "LEFT JOIN tblStatus s\n"
            + "ON b.statusID = s.statusID\n"
            + "WHERE a.role = 'US' AND b.statusID = 4";
    
    public static String GET_CANCEL_LIST = "SELECT a.phone, a.name, a.address, a.status, b.billID, s.status  as billStatus, b.total, b.cancelReason\n"
            + "FROM tblAccount a\n"
            + "Left JOIN tblBill b \n"
            + "ON a.phone = b.cusPhone\n"
            + "LEFT JOIN tblStatus s\n"
            + "ON b.statusID = s.statusID\n"
            + "WHERE a.role = 'US' AND b.statusID = 5 AND b.cancelReason NOT LIKE 'Người bán hủy đơn'";
    
    public static String GET_DETAIL_LIST = "SELECT a.phone, a.name, a.address, a.status, b.billID, s.status  as billStatus, b.total, b.cancelReason\n" +
                                            "FROM tblAccount a\n" +
                                            "Left JOIN tblBill b \n" +
                                            "ON a.phone = b.cusPhone\n" +
                                            "LEFT JOIN tblStatus s\n" +
                                            "ON b.statusID = s.statusID\n" +
                                            "WHERE a.role = 'US' AND a.phone = ?";

    public static String GET_BLOCKED_CUSTOMER = "SELECT a.phone, a.password, a.name, a.address, a.gender, a.point, a.role, a.status\n"
            + "FROM tblAccount a\n"
            + "WHERE a.status = 0 AND a.role = 'US'";

    public static String UNBLOCK_USER = "UPDATE tblAccount\n"
            + "SET status = 1 \n"
            + "WHERE phone = ?";

    public static String BLOCK_USER = "UPDATE tblAccount\n"
            + "SET status = 0 \n"
            + "WHERE phone = ?";
    
    public static String SORT_BILL = "SELECT a.phone, a.name, a.address, a.status, b.billID, s.status  as billStatus, b.total, b.cancelReason\n"
            + "FROM tblAccount a\n"
            + "Left JOIN tblBill b \n"
            + "ON a.phone = b.cusPhone\n"
            + "LEFT JOIN tblStatus s\n"
            + "ON b.statusID = s.statusID\n"
            + "WHERE a.role = 'US' AND s.statusID = ?";
    
    

    public static List<AccountDTO> getBlockCustomer() throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BLOCKED_CUSTOMER);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String phone = rs.getString("phone");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean gender = rs.getBoolean("gender");
                    double point = rs.getDouble("point");
                    boolean status = rs.getBoolean("status");
                    list.add(new AccountDTO(phone, password, name, address, gender, point, status));
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
        return list;
    }
    
    public static List<AccountDTO> getAllCustomer() throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_CUSTOMER);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String phone = rs.getString("phone");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean gender = rs.getBoolean("gender");
                    double point = rs.getDouble("point");
                    String role = rs.getString("role");
                    boolean status = rs.getBoolean("status");
                    list.add(new AccountDTO(phone, password, name, address, gender, point, role, status));
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
        return list;
    }

    public static List<AccountDTO> getSuccessList() throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                // a.phone, a.name, a.address, b.billID, s.status , b.total, b.cancelReason
                ptm = conn.prepareStatement(GET_SUCCESS_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean status = rs.getBoolean("status");
                    int billID = rs.getInt("billID");
                    String billStatus = rs.getString("billStatus");
                    String cancelReason = rs.getString("cancelReason");
                    double total = rs.getDouble("total");
                    list.add(new AccountDTO(phone, name, address, status, billID, billStatus, total, cancelReason));
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
        return list;
    }
    
    public static List<AccountDTO> getCancelList() throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                // a.phone, a.name, a.address, b.billID, s.status , b.total, b.cancelReason
                ptm = conn.prepareStatement(GET_CANCEL_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean status = rs.getBoolean("status");
                    int billID = rs.getInt("billID");
                    String billStatus = rs.getString("billStatus");
                    String cancelReason = rs.getString("cancelReason");
                    double total = rs.getDouble("total");
                    list.add(new AccountDTO(phone, name, address, status, billID, billStatus, total, cancelReason));
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
        return list;
    }
    
    public static List<AccountDTO> getDetailList(String cusPhone) throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                // a.phone, a.name, a.address, b.billID, s.status , b.total, b.cancelReason
                ptm = conn.prepareStatement(GET_DETAIL_LIST);
                ptm.setString(1, cusPhone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean status = rs.getBoolean("status");
                    int billID = rs.getInt("billID");
                    String billStatus = rs.getString("billStatus");
                    String cancelReason = rs.getString("cancelReason");
                    double total = rs.getDouble("total");
                    list.add(new AccountDTO(cusPhone, name, address, status, billID, billStatus, total, cancelReason));
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
        return list;
    }
    
    public static List<AccountDTO> getConfirmBill(int bill) throws NamingException, SQLException {
        List<AccountDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                // a.phone, a.name, a.address, b.billID, s.status , b.total, b.cancelReason
                ptm = conn.prepareStatement(SORT_BILL);
                ptm.setInt(1, bill);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean status = rs.getBoolean("status");
                    int billID = rs.getInt("billID");
                    String billStatus = rs.getString("billStatus");
                    String cancelReason = rs.getString("cancelReason");
                    double total = rs.getDouble("total");
                    list.add(new AccountDTO(phone, name, address, status, billID, billStatus, total, cancelReason));
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
        return list;
    }

    public static boolean blockCustomer(String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        if (phone == null) {
            return false;
        }

        try {
//        1. Make connection
            con = DBHelper.makeConnection();

            if (con != null) {
//          2. Create SQL Stirng . co khoang trang sau username
                statement = con.prepareStatement(BLOCK_USER);
                statement.setString(1, phone);

//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public static boolean unblockCustomer(String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        if (phone.isEmpty()) {
            return false;
        }

        try {
//        1. Make connection
            con = DBHelper.makeConnection();

            if (con != null) {
//          2. Create SQL Stirng . co khoang trang sau username
                statement = con.prepareStatement(UNBLOCK_USER);
                statement.setString(1, phone);

//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
