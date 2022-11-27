/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.dao.ADMIN;

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
public class ProductDAOAdmin {

    public static final String GET_CATEGORY_NAME_GIVEN_PRODUCTID = "Select distinct c.categoryName\n"
            + "From tblProducts p, tblCategory c\n"
            + "Where p.categoryID = c.categoryID\n"
            + "And p.productID = ?";

    public static final String GET_PRO_DETAIL = "SELECT ps.selPhone, pro.name, price, quantity, cat.categoryID, cat.categoryName, discountPrice, productDes, image, pro.status, ac.name as selName\n" +
"            FROM  tblProducts pro\n" +
"            INNER JOIN tblCategory cat\n" +
"            ON pro.categoryID = cat.categoryID\n" +
"            INNER JOIN tblProductSeller ps\n" +
"            ON pro.productID = ps.productID AND ps.isActive = 1\n" +
"            INNER JOIN tblAccount ac\n" +
"            ON ac.phone = ps.selPhone\n" +
"            AND pro.productID = ?";

    public static final String SET_STATUS_TRUE = "UPDATE tblProducts\n"
            + "SET status = 1\n"
            + "WHERE productID = ?";

    public static final String SET_STATUS_FALSE = "UPDATE tblProducts\n"
            + "SET status = 0\n"
            + "WHERE productID = ?";

    public static final String UPDATE_PRODUCT = "UPDATE tblProducts\n"
            + "SET name = ? , price = ? , quantity = ? , categoryID = ? , discountPrice = ? , productDes = ? , image = ? , status = ?\n"
            + "WHERE productID = ? ";

    public static final String GET_LIST_ACTIVE = "SELECT ps.selPhone, pro.productID, pro.name, price, quantity, cat.categoryID ,cat.categoryName, discountPrice, productDes, image, acc.name as selName\n"
            + "FROM  tblCategory cat\n"
            + "INNER JOIN tblProducts pro\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller ps\n"
            + "ON pro.productID = ps.productID\n"
            + "INNER JOIN tblAccount acc\n"
            + "ON ps.selPhone = acc.phone \n"
            + "AND pro.status = 1 AND ps.isActive = 1";

    public static final String GET_LIST_NON_ACTIVE = "SELECT ps.selPhone, pro.productID, pro.name, price, quantity, cat.categoryID ,cat.categoryName, discountPrice, productDes, image, acc.name as selName\n"
            + "FROM  tblCategory cat\n"
            + "INNER JOIN tblProducts pro\n"
            + "ON pro.categoryID = cat.categoryID\n"
            + "INNER JOIN tblProductSeller ps\n"
            + "ON pro.productID = ps.productID\n"
            + "INNER JOIN tblAccount acc\n"
            + "ON ps.selPhone = acc.phone \n"
            + "AND pro.status = 0";

    public static final String DEACTIVE_PRODUCT_SELLER = "UPdate tblProductSeller \n"
            + "SET isActive = 0\n"
            + "WHERE productID = ? AND selPhone = ? AND isActive = 1";

    public static final String GET_LIST_PRODUCT_OF_SELLER = "SELECT p.productID, p.name, c.categoryName, p.price, p.discountPrice, p.quantity, a.name as selName \n"
            + "            FROM tblProducts p\n"
            + "            Inner Join tblCategory c\n"
            + "            ON p.categoryID = c.categoryID\n"
            + "            INNER JOIN tblProductSeller s\n"
            + "            ON p.productID = s.productID\n"
            + "			INNER JOIN tblAccount a\n"
            + "            ON a.phone = s.selPhone\n"
            + "            WHERE p.status = 1 AND s.isActive = 1 AND s.selPhone = ?";

    public static final String INSERT_NEW_PRODUCT_SELLER = "Insert Into tblProductSeller(productID, selPhone, isActive) "
            + " Values(?,?,1)";

    public static final String REGEX = "-(ptth)";
    public static final String INSERT_REGEX = "-ptth";

    public static List<ProductDTO> getListActiveProduct() throws SQLException, NamingException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_ACTIVE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = Integer.parseInt(rs.getString("productID"));
                    String selPhone = rs.getString("selPhone");
                    String name = rs.getString("name");
                    float price = Float.parseFloat(rs.getString("price"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String categoryName = rs.getString("categoryName");
                    float discountPrice = Float.parseFloat(rs.getString("discountPrice"));
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    String[] imageLink = image.split(REGEX);
                    String selName = rs.getString("selName");
                    list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, image, ".", true, imageLink, selName));
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

    public static List<ProductDTO> getListNonActiveProduct() throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_NON_ACTIVE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = Integer.parseInt(rs.getString("productID"));
                    String selPhone = rs.getString("selPhone");
                    String name = rs.getString("name");
                    float price = Float.parseFloat(rs.getString("price"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String categoryName = rs.getString("categoryName");
                    float discountPrice = Float.parseFloat(rs.getString("discountPrice"));
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    String[] imageLink = image.split(REGEX);
                    String selName = rs.getString("selName");
                    list.add(new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, categoryName), discountPrice, productDes, image, ".", false, imageLink, selName));
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

    public static boolean checkSameCategory(ProductDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String categoryName = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CATEGORY_NAME_GIVEN_PRODUCTID);
                ptm.setInt(1, dto.getProductID());
                rs = ptm.executeQuery();
                if (rs.next()) {
                    categoryName = rs.getString("categoryName");
                }
                if (dto.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                    return true;
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
        return false;
    }

//    Chèn vào product + productSeller
    public ProductDTO insertProduct(ProductDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        if (dto == null) {
            return null;
        }

        try {
//        1. Make connection
            con = DBHelper.makeConnection();

            if (con != null) {
                String sql = "Insert Into tblProducts(name, price, quantity, categoryID, discountPrice,  productDes, image, detailDes, status) "
                        + "Values(?,?,?,?,?,?,?,?,?) ";
//          3. Create Statement Object
                statement = con.prepareStatement(sql);
                statement.setString(1, dto.getName());
                statement.setDouble(2, dto.getPrice());
                statement.setInt(3, dto.getQuantity());
                statement.setInt(4, dto.getCategory().getCategoryID());
                statement.setDouble(5, dto.getDiscountPrice());
                statement.setString(6, dto.getProductDes());
                statement.setString(7, dto.getImage());
                statement.setString(8, dto.getDetailDes());
                statement.setBoolean(9, dto.isStatus());

//          4. Execute Query
                int affectRow = statement.executeUpdate();
//          5. Process result
                if (affectRow > 0) {
                    int productID = getProductIDInDB(dto); // lay productID
                    dto.setProductID(productID);
                    insertNEWProductSeller(dto);
                    return dto;
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
        return null;
    }

//    TH1: Khi thêm mới 1 sản phẩm sẽ tự động insert vào ProductSeller với seller.status true
    public static boolean insertNEWProductSeller(ProductDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        if (dto == null) {
            return false;
        }

        try {
//        1. Make connection
            con = DBHelper.makeConnection();

            if (con != null) {
//          3. Create Statement Object
                statement = con.prepareStatement(INSERT_NEW_PRODUCT_SELLER);
                statement.setInt(1, dto.getProductID());
                statement.setString(2, dto.getSelPhone());

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

    public boolean updateProductWithSameSeller(ProductDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return false;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
//                String sql = "UPDATE tblProducts\n"
//                        + "SET name = ? , price = ? , quantity = ? , categoryID = ? , discountPrice = ? , productDes = ? , image = ? , status = ?\n"
//                        + "WHERE productID = ? ";
                statement = con.prepareStatement(UPDATE_PRODUCT);
                statement.setString(1, dto.getName());
                statement.setFloat(2, (float) dto.getPrice());
                statement.setInt(3, dto.getQuantity());
                statement.setInt(4, dto.getCategory().getCategoryID());
                statement.setFloat(5, (float) dto.getDiscountPrice());
                statement.setString(6, dto.getProductDes());
                statement.setString(7, dto.getImage());
                statement.setBoolean(8, dto.isStatus());
                statement.setInt(9, dto.getProductID());
//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
                    return true;
                }

            }// end if connection is not null

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

    public boolean updateProductWithDiffSellerInProductSeller(ProductDTO dto, String oldSelPhone) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return false;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
//                2.1 Gán isActive seller cũ = 0
                statement = con.prepareStatement(DEACTIVE_PRODUCT_SELLER);
                statement.setInt(1, dto.getProductID());
                statement.setString(2, oldSelPhone);
//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
                    updateProductWithSameSeller(dto);
//                    5.1 Gán isActive = 1 cho seller mới
                    insertNEWProductSeller(dto);
                    return true;
                }

            }// end if connection is not null

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

    public static ProductDTO getProductDetail(int productID) throws SQLException, NamingException {
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
                    double price = Double.parseDouble(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    String category = rs.getString("categoryName");
                    double discountPrice = Double.parseDouble(rs.getString("discountPrice"));
                    String productDes = rs.getString("productDes");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    String[] imageLink = image.split(REGEX);
                    String selName = rs.getString("selName");
                    producDetail = new ProductDTO(productID, selPhone, name, price, quantity, new CategoryDTO(categoryID, category), discountPrice, productDes, image, ".", status, imageLink, selName);
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
        return producDetail;
    }

    public boolean disableProduct(ProductDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return false;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
                statement = con.prepareStatement(SET_STATUS_FALSE);
                statement.setInt(1, dto.getProductID());

//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
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

    public static boolean acceptProduct(ProductDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return false;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
                statement = con.prepareStatement(SET_STATUS_TRUE);
                statement.setInt(1, dto.getProductID());

//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
                    return true;
                }

            }// end if connection is not null

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

    private int getProductIDInDB(ProductDTO dto) throws SQLException, NamingException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return 0;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
                String GET_PRODUCTID_IN_DB = "SELECT productID\n"
                        + "FROM tblProducts p\n"
                        + "WHERE p.name = ? \n"
                        + "AND p.price = ?\n"
                        + "AND p.quantity = ? \n"
                        + "AND p.categoryID = ? \n"
                        + "AND p.discountPrice = ?\n"
                        + "AND p.productDes = ? \n"
                        + "AND p.image = ?\n"
                        + "AND p.status = ?\n";

                statement = con.prepareStatement(GET_PRODUCTID_IN_DB);
                statement.setString(1, dto.getName());
                statement.setFloat(2, (float) dto.getPrice());
                statement.setInt(3, dto.getQuantity());
                statement.setInt(4, dto.getCategory().getCategoryID());
                statement.setFloat(5, (float) dto.getDiscountPrice());
                statement.setString(6, dto.getProductDes());
                statement.setString(7, dto.getImage());
                statement.setBoolean(8, dto.isStatus());

//          4. Execute Query
                rs = statement.executeQuery();

//          5. Process result
                if (rs.next()) {
                    return rs.getInt("productID");
                }

            }// end if connection is not null

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

    public static List<ProductDTO> getListProductOfSeller(String phone) throws SQLException, NamingException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_PRODUCT_OF_SELLER);
                ptm.setString(1, phone);
                rs = ptm.executeQuery();
                while (rs.next()) { //SELECT p.name, c.categoryName, p.price, p.discountPrice, p.quantity
                    int productID = Integer.parseInt(rs.getString("productID"));
                    String name = rs.getString("name");
                    String categoryName = rs.getString("categoryName");
                    float price = Float.parseFloat(rs.getString("price"));
                    float discountPrice = Float.parseFloat(rs.getString("discountPrice"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String selName = rs.getString("selName");
                    list.add(new ProductDTO(productID, name, new CategoryDTO(categoryName), price, discountPrice, quantity, selName));
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

    public boolean changeSeller(ProductDTO dto, String oldSelPhone) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        if (dto == null) {
            return false;
        }

        try {
//            1. make connection
            con = DBHelper.makeConnection();

//            2. Create sql string 
            if (con != null) {
//                2.1 Gán isActive seller cũ = 0
                statement = con.prepareStatement(DEACTIVE_PRODUCT_SELLER);
                statement.setInt(1, dto.getProductID());
                statement.setString(2, oldSelPhone);
//          4. Execute Query
                int affectRow = statement.executeUpdate();

//          5. Process result
                if (affectRow > 0) {
//                    5.1 Gán isActive = 1 cho seller mới
                    insertNEWProductSeller(dto);
                    return true;
                }

            }// end if connection is not null

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
