/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.income;

import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class IncomeDAO {
    private final String GET_SELLER_INCOME = "SELECT CAST(bi.time AS DATE) AS sellDate, SUM(biDet.total) AS totalSell\n" +
                                            "FROM tblBill bi\n" +
                                            "JOIN tblBillDetail biDet\n" +
                                            "ON bi.BillID = biDet.billID\n" +
                                            "JOIN tblProducts pro\n" +
                                            "ON pro.productID = biDet.productID\n" +
                                            "JOIN tblProductSeller proSel\n" +
                                            "ON proSel.productID = biDet.productID\n" +
                                            "WHERE proSel.selPhone = ? AND isActive = 1 AND statusID = ?\n" +
                                            "GROUP BY CAST(bi.time AS DATE)";
    
    public List<IncomeDTO> getSellerIncome(String selPhone, int statusID) throws SQLException {
        List<IncomeDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_SELLER_INCOME);
                ptm.setString(1, selPhone);
                ptm.setInt(2, statusID);
                rs = ptm.executeQuery();
                while (rs.next()) {                    
                    Date date = rs.getDate("sellDate");
                    double total = rs.getDouble("totalSell");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new IncomeDTO(selPhone, date, total));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
}
