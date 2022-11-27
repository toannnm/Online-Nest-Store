/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.product;

import com.nestf.category.CategoryDTO;
import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDAO {

    public static final String REGEX = "-(ptth)";

    public static final String GET_LIST = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE pro.status = 1\n"
            + "ORDER BY pro.quantity DESC, pro.productID";

    public static final String FILTER_CATE = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE pro.categoryID = ? AND pro.status = 1\n"
            + "ORDER BY pro.quantity DESC, pro.productID";
    public static final String FILTER_PRICE = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE price BETWEEN ? AND ? AND pro.status = 1\n"
            + "ORDER BY pro.quantity DESC, pro.productID";
    public static final String FILTER_BOTH = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE pro.categoryID = ?\n"
            + "AND price BETWEEN ? AND ? AND pro.status = 1"
            + "ORDER BY pro.quantity DESC, pro.productID";
    public static final String GET_PRO_DETAIL = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE pro.productID = ?";

    public static final String GET_LIST_RELATE = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE cat.categoryName = ? AND pro.status = 1\n"
            + "AND pro.productID != ?";

    public static final String SEARCH_PRODUCT = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM tblProducts pro\n"
            + "INNER JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE name LIKE ? AND pro.status = 1\n"
            + "ORDER BY pro.quantity DESC, pro.productID";

    public static final String GET_BEST_SELL_LIST = "SELECT TOP(4) pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM (SELECT productID, COUNT(productID) as sellQuantity\n"
            + "       FROM tblBillDetail\n"
            + "       group by productID) as tab\n"
            + "JOIN tblProducts pro\n"
            + "ON pro.productID = tab.productID\n"
            + "JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE pro.status = 1\n"
            + "ORDER BY sellQuantity DESC";

    public List<ProductDTO> getListProduct() throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        int categoryID = rs.getInt("categoryID");
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return list;
    }

    public List<ProductDTO> getFilterProductCategory(int categoryID) throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(FILTER_CATE);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return list;
    }

    public List<ProductDTO> getFilterProductPrice(int priceMin, int priceMax) throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(FILTER_PRICE);
                ptm.setInt(1, priceMin);
                ptm.setInt(2, priceMax);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        int categoryID = rs.getInt("categoryID");
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return list;
    }

    public List<ProductDTO> getFilterProductBoth(int categoryID, int priceMin, int priceMax) throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(FILTER_BOTH);
                ptm.setInt(1, categoryID);
                ptm.setInt(2, priceMin);
                ptm.setInt(3, priceMax);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return list;
    }

    public ProductDTO getProductDetail(int productID) throws SQLException {
        ProductDTO producDetail = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PRO_DETAIL);
                ptm.setInt(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String selPhone = rs.getString("selPhone");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    double discountPrice = rs.getDouble("discountPrice");
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    String statusStr = rs.getString("status");
                    String[] imageLink = image.split(REGEX);
                    boolean status = false;
                    if (statusStr.equalsIgnoreCase("1")) {
                        status = true;
                    }
                    producDetail = new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink);
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
        return producDetail;
    }

    public List<ProductDTO> getListRelateProduct(String category, int productID) throws SQLException {
        List<ProductDTO> listRelate = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_RELATE);
                ptm.setString(1, category);
                ptm.setInt(2, productID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int relatedID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        int categoryID = rs.getInt("categoryID");
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (listRelate == null) {
                            listRelate = new ArrayList<>();
                        }
                        listRelate.add(new ProductDTO(relatedID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return listRelate;

    }

    public List<ProductDTO> searchProduct(String search) throws SQLException {
        List<ProductDTO> searchList = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        int categoryID = rs.getInt("categoryID");
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (searchList == null) {
                            searchList = new ArrayList<>();
                        }
                        searchList.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return searchList;
    }

    public List<ProductDTO> getBestSellList() throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BEST_SELL_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    boolean status = rs.getBoolean("status");
                    if (status) {
                        int productID = Integer.parseInt(rs.getString("productID"));
                        String selPhone = rs.getString("selPhone");
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = Integer.parseInt(rs.getString("quantity"));
                        int categoryID = rs.getInt("categoryID");
                        String categoryName = rs.getString("categoryName");
                        double discountPrice = rs.getDouble("discountPrice");
                        String productDes = rs.getString("productDes");
                        String image = rs.getString("image");
                        String[] imageLink = image.split(REGEX);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, ".", status, imageLink));
                    }
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
        return list;
    }
    
    private final String GET_SELLER_BEST_SELL = "SELECT TOP(2) pro.productID, proSel.selPhone, name, price, quantity, cat.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n"
            + "FROM (SELECT productID, COUNT(productID) as sellQuantity\n"
            + "       FROM tblBillDetail\n"
            + "       group by productID) as tab\n"
            + "JOIN tblProducts pro\n"
            + "ON pro.productID = tab.productID\n"
            + "JOIN tblCategory cat\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "JOIN tblProductSeller proSel\n"
            + "ON pro.productID = proSel.productID\n"
            + "WHERE proSel.selPhone = ?\n"
            + "ORDER BY sellQuantity DESC";

    public List<ProductDTO> sellerBestSellList(String selPhone) throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_SELLER_BEST_SELL);
                ptm.setString(1, selPhone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = Integer.parseInt(rs.getString("productID"));
                    String name = rs.getString("name");
                    double price = Double.parseDouble(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    String category = rs.getString("categoryName");
                    double discountPrice = Double.parseDouble(rs.getString("discountPrice"));
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    String[] imageLink = image.split(REGEX);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, category), discountPrice, productDes, ".", status, imageLink));
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
        return list;
    }
    
    private final String GET_SELLER_PRODUCT_LIST = "SELECT pro.productID, proSel.selPhone, name, price, quantity, cate.categoryID, categoryName, discountPrice, productDes, image, detailDes, pro.status\n" +
                                                "FROM tblProducts pro\n" +
                                                "JOIN tblProductSeller proSel\n" +
                                                "ON pro.productID = proSel.productID\n" +
                                                "JOIN tblCategory cate\n" +
                                                "ON cate.categoryID = pro.categoryID\n" +
                                                "WHERE selPhone = ?";
    
    public List<ProductDTO> getSellerProductList(String selPhone) throws SQLException {
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_SELLER_PRODUCT_LIST);
                ptm.setString(1, selPhone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = Integer.parseInt(rs.getString("productID"));
                    String name = rs.getString("name");
                    double price = Double.parseDouble(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    String category = rs.getString("categoryName");
                    double discountPrice = Double.parseDouble(rs.getString("discountPrice"));
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    String[] imageLink = image.split(REGEX);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, category), discountPrice, productDes, ".", status, imageLink));
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
        return list;
    }
    
    private final String UPDATE_QUANTITY = "UPDATE tblProducts\n" +
                                            "SET quantity = ?\n" +
                                            "WHERE productID = ?";
    public void updateQuantity(int productID, int newQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUANTITY);
                ptm.setInt(1, newQuantity);
                ptm.setInt(2, productID);
                ptm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
    }
}
