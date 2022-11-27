/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.productseller;

import com.nestf.category.CategoryDTO;
import com.nestf.product.ProductDTO;
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
 * @author DELL
 */
public class ProductSellerDAO {

    public static final String GET_SELLER = "SELECT PS.selPhone\n"
            + "FROM tblProductSeller ps\n"
            + "WHERE ps.productID = ? AND ps.isActive = 1";
    
    
    public static Boolean checkSameSeller(ProductDTO dto) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String selPhone= null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_SELLER);
                ptm.setInt(1, dto.getProductID());
                rs = ptm.executeQuery();
                if (rs.next()) {
                    selPhone = rs.getString("selPhone");
                }
                if(dto.getSelPhone().equalsIgnoreCase(selPhone)){
                    return true;
                }
            }
        }finally {
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
        return false;
    }
    
    public static String getSellerPhone(ProductDTO dto) throws SQLException{
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String selPhone= null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_SELLER);
                ptm.setInt(1, dto.getProductID());
                rs = ptm.executeQuery();
                if (rs.next()) {
                    selPhone = rs.getString("selPhone");
                    return selPhone;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return null;
    }
}
