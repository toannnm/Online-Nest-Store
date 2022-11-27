/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.cart;

import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import com.nestf.util.DBHelper;
import java.io.Serializable;
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
public class CartDAO implements Serializable {

    private List<CartItemDTO> cart;

    public void setCarts(List<CartItemDTO> cart) {
        this.cart = cart;
    }

    public List<CartItemDTO> getCarts() {
        return cart;
    }

    public CartItemDTO getItemByID(int productID) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductID() == productID) {
                return cart.get(i);
            }
        }
        return null;
    }
    public double getCartTotal(List<CartItemDTO> cart){
        double total = 0;
        for (CartItemDTO item : cart) {
            double price = item.getProduct().getDiscountPrice() != 0 ? item.getProduct().getDiscountPrice() : item.getProduct().getPrice();
            double thisItemTotal = price*item.getAmount();
            total+= thisItemTotal;
        }
        return total;
    }
    public String AddItem(ProductDTO product, int amount, int newQuantity, String phone) throws NamingException, SQLException {
        String notification = null;
        CartItemDTO item = getItemByID(product.getProductID());
        if (getItemByID(product.getProductID()) == null) {
            if (amount <= product.getQuantity()) {
                CartItemDTO newItem = new CartItemDTO(product, amount);
                if (addItemToCart(newItem, phone)) {
                    cart.add(newItem);
                    notification = "success";
                }
            } else {
                notification = "fail";
            }
        } else {
            int newAmount;
            if (newQuantity == -1) {
                newAmount = item.getAmount() + amount;
            } else {
                newAmount = newQuantity;
            }
            if (newAmount <= item.getProduct().getQuantity()) {
                item.setAmount(newAmount);
                if (updateItemAmount(item, phone)) {
                    int index = cart.indexOf(item);
                    cart.set(index, item);
                    notification = "success";
                }
            } else {
                notification = "fail";
            }
        }

        return notification;
    }

    public String removeItem(int productID, String phone) throws SQLException, NamingException {
        String notification = null;
        CartItemDTO item = getItemByID(productID);
        if (item != null) {
            removeItemFromCart(productID, phone);
            cart.remove(item);
            notification = "fail=false";
        }
        return notification;
    }
    private static final String LOAD_CART = "Select productID, amount From tblCart Where cusPhone = ?";

    public void loadCart(String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                statement = con.prepareStatement(LOAD_CART);
                statement.setString(1, phone);
                rs = statement.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    ProductDAO dao = new ProductDAO();
                    ProductDTO product = dao.getProductDetail(productID);
                    int amount = rs.getInt("amount");
                    CartItemDTO dto = new CartItemDTO(product, amount);
                    if (this.cart == null) {
                        this.cart = new ArrayList<>();
                    }
                    this.cart.add(dto);
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
    }
    private static final String INSERT = "INSERT INTO tblCart (cusPhone, productID, amount) VALUES (?, ?, ?)";

    public boolean addItemToCart(CartItemDTO item, String phone) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                ptm = con.prepareStatement(INSERT);
                ptm.setString(1, phone);
                ptm.setInt(2, item.getProduct().getProductID());
                ptm.setInt(3, item.getAmount());
                check = ptm.executeUpdate() > 0;
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

    private static final String UPDATE = "UPDATE tblCart SET amount = ? WHERE cusPhone = ? AND productID = ?";

    public boolean updateItemAmount(CartItemDTO item, String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                ptm = con.prepareStatement(UPDATE);
                ptm.setInt(1, item.getAmount());
                ptm.setString(2, phone);
                ptm.setInt(3, item.getProduct().getProductID());
                check = ptm.executeUpdate() > 0;
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

    private static final String REMOVE = "Delete From tblCart Where cusPhone = ? And productID = ? ";

    public boolean removeItemFromCart(int productID, String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                statement = con.prepareStatement(REMOVE);
                statement.setString(1, phone);
                statement.setInt(2, productID);
                int affectRow = statement.executeUpdate();
                if (affectRow > 0) {
                    return true;
                }
            }
        } finally {
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
