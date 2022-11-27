/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.billdetail;

import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import com.nestf.util.DBHelper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class BillDetailDAO {
    private static final String GET_LIST_BILL_DETAIL = "SELECT productID, quantity, price, total\n" +
                                                        "FROM tblBillDetail\n" +
                                                        "WHERE billID = ?";
    
    public List<BillDetailDTO> getBillDetail(int billID) throws NamingException, SQLException {
        List<BillDetailDTO> list = null;        
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_BILL_DETAIL);
                ptm.setInt(1, billID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    ProductDAO dao = new ProductDAO();
                    ProductDTO product = dao.getProductDetail(productID);
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    double total = rs.getDouble("total");
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    BillDetailDTO dto = new BillDetailDTO(billID, product, quantity, price, total);
                    list.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
    private final String INSERT = "INSERT tblBillDetail VALUES ( ?, ?, ?, ?, ?, ?)";
    public boolean insertBillDetail(BillDetailDTO billDetail) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try{
            conn = DBHelper.makeConnection();
            if (conn != null){
                ptm = conn.prepareStatement(INSERT);
                ptm.setInt(1, billDetail.getBillID());
                ptm.setInt(2, billDetail.getProduct().getProductID());
                ptm.setInt(3, billDetail.getQuantity());
                ptm.setDouble(4, billDetail.getPrice());
                ptm.setDouble(5, billDetail.getTotal());
                ptm.setString(6, billDetail.getProduct().getSelPhone());
                check = ptm.executeUpdate() > 0;
            }
        } finally{
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
    
    
    
}
