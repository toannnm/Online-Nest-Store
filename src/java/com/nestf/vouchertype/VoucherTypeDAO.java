/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.vouchertype;

import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class VoucherTypeDAO {

    List<VoucherTypeDTO> list = null;

    public List<VoucherTypeDTO> getList() {
        return list;
    }

    public void loadVoucher() throws NamingException, SQLException {
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM tblVoucherType ORDER BY quantity";
                stm = con.createStatement();
                rs = stm.executeQuery(sql);
                while (rs.next()) {
                    int typeID = rs.getInt("typeID");
                    String voucherName = rs.getString("voucherName");
                    Double saleValue = rs.getDouble("saleValue");
                    int quantity = rs.getInt("quantity");
                    int point = rs.getInt("point");
                    boolean status = rs.getBoolean("status");
                    VoucherTypeDTO dto = new VoucherTypeDTO(typeID, voucherName, saleValue, quantity, point, status);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public VoucherTypeDTO getVoucher(int typeID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM tblVoucherType WHERE typeID = ?";
                ptm = con.prepareStatement(sql);
                ptm.setInt(1, typeID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String voucherName = rs.getString("voucherName");
                    Double saleValue = rs.getDouble("saleValue");
                    int quantity = rs.getInt("quantity");
                    int point = rs.getInt("point");
                    boolean status = rs.getBoolean("status");
                    VoucherTypeDTO dto = new VoucherTypeDTO(typeID, voucherName, saleValue, quantity, point, status);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return null;
    }
    public boolean updateQuantity(int typeID, int newQuantity) throws NamingException, SQLException{
        Boolean check = false;      
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblVoucherType SET quantity = ? WHERE typeID = ?";
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, newQuantity);
                ptm.setInt(2, typeID);             
                if(ptm.executeUpdate() > 0){
                    check = true;
                }
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
    public boolean updateVoucherType(VoucherTypeDTO voucherType) throws NamingException, SQLException{
        Boolean check = false;      
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblVoucherType SET voucherName = ?, saleValue = ?, quantity = ?, point = ?, status = ? WHERE typeID = ?";
                ptm = conn.prepareStatement(sql);
                ptm.setNString(1, voucherType.getVoucherName());
                ptm.setDouble(2, voucherType.getSaleValue());
                ptm.setInt(3, voucherType.getQuantity());
                ptm.setInt(4, voucherType.getPoint());
                ptm.setBoolean(5, voucherType.isStatus());
                ptm.setInt(6, voucherType.getTypeID());             
                if(ptm.executeUpdate() > 0){
                    check = true;
                }
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
    public boolean AddVoucherType(VoucherTypeDTO voucherType) throws NamingException, SQLException{
        Boolean check = false;      
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "Insert tblVoucherType VALUES (?, ?, ? , ?, ?)";
                ptm = conn.prepareStatement(sql);
                ptm.setNString(1, voucherType.getVoucherName());
                ptm.setDouble(2, voucherType.getSaleValue());
                ptm.setInt(3, voucherType.getQuantity());
                ptm.setInt(4, voucherType.getPoint());
                ptm.setBoolean(5, voucherType.isStatus());             
                if(ptm.executeUpdate() > 0){
                    check = true;
                }
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
}
