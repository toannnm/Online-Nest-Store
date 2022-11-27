/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.voucher;

import com.nestf.util.DBHelper;
import com.nestf.vouchertype.VoucherTypeDAO;
import com.nestf.vouchertype.VoucherTypeDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class VoucherDAO {

    private List<VoucherDTO> list = null;

    public List<VoucherDTO> getList() {
        return list;
    }

    public void loadVoucherWallet(String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM tblVoucher WHERE cusPhone =? AND status = 1 ORDER BY expiredDate, typeID DESC";
                ptm = con.prepareStatement(sql);
                ptm.setString(1, phone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int voucherID = rs.getInt("voucherID");
                    VoucherTypeDAO dao = new VoucherTypeDAO();
                    VoucherTypeDTO voucherType = dao.getVoucher(rs.getInt("typeID"));
                    boolean status = rs.getBoolean("status");
                    Date date = rs.getDate("expiredDate");
                    if (date.compareTo(new Date()) > 0) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String expiredDate = formatter.format(date);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        VoucherDTO dto = new VoucherDTO(voucherID, voucherType, status, expiredDate);
                        list.add(dto);
                    } else {
                        boolean remove = RemoveVoucher(voucherID);
                    }
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
    }

    public VoucherDTO addVoucherToWaller(int typeID, String phone) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ptm = null;
        VoucherDTO voucher = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                LocalDate expiredDate = LocalDate.now().plusDays(30);
                java.sql.Date date = java.sql.Date.valueOf(expiredDate);
                String sql = "INSERT INTO tblVoucher (typeID, cusPhone, status, expiredDate) VALUES (?, ?, 1, ?)";
                ptm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ptm.setInt(1, typeID);
                ptm.setString(2, phone);
                ptm.setDate(3, date);
                if (ptm.executeUpdate() > 0) {
                    int voucherID = 0;
                    rs = ptm.getGeneratedKeys();
                    if (rs.next()) {
                        voucherID = rs.getInt(1);
                    }
                    VoucherTypeDAO dao = new VoucherTypeDAO();
                    VoucherTypeDTO voucherType = dao.getVoucher(typeID);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String exDate = formatter.format(date);
                    voucher = new VoucherDTO(voucherID, voucherType, true, exDate);
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
        return voucher;
    }

    public boolean RemoveVoucher(int voucherID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                LocalDate expiredDate = LocalDate.now().plusDays(30);
                java.sql.Date date = java.sql.Date.valueOf(expiredDate);
                String sql = "UPDATE tblVoucher SET status = 0 WHERE voucherID = ?";
                ptm = con.prepareStatement(sql);
                ptm.setInt(1, voucherID);
                if (ptm.executeUpdate() > 0) {
                    check = true;
                }
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return check;
    }
    private final String GET_VOUCHER = "SELECT * FROM tblVoucher WHERE voucherID = ?";

    public VoucherDTO getVoucherByID(int voucherID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        VoucherDTO voucher = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                ptm = con.prepareStatement(GET_VOUCHER);
                ptm.setInt(1, voucherID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    VoucherTypeDAO dao = new VoucherTypeDAO();
                    VoucherTypeDTO voucherType = dao.getVoucher(rs.getInt("typeID"));
                    boolean status = rs.getBoolean("status");
                    Date date = rs.getDate("expiredDate");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String expiredDate = formatter.format(date);
                    voucher = new VoucherDTO(voucherID, voucherType, status, expiredDate);
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
        return voucher;
    }
}
