/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.category;

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
public class CategoryDAO {

    public static final String GET_LIST = "SELECT categoryID, categoryName\n"
            + "FROM tblCategory\n";

    public static final String GET_ID = "SELECT categoryID\n"
            + "FROM tblCategory\n"
            + "WHERE categoryName = ? ";

    public static final String GET_NAME = "SELECT categoryName\n"
            + "FROM tblCategory\n"
            + "WHERE categoryID = ? ";

    public static final String INSERT_CATEGORY = "INSERT INTO tblCategory(categoryName) "
            + "VALUES(?) ";

    private List<CategoryDTO> categoryID;

    public List<CategoryDTO> getCategoryID() {
        return categoryID;
    }

    public static List<CategoryDTO> getListCategory() throws SQLException, NamingException {
        List<CategoryDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    String categoryName = rs.getString("categoryName");
                    list.add(new CategoryDTO(categoryID, categoryName));
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

    public static CategoryDTO getCategoryID(String categoryName) throws SQLException, NamingException {
        CategoryDTO dto = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ID);
                ptm.setString(1, categoryName);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    dto = new CategoryDTO(categoryID, categoryName);
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
        return dto;
    }

    public static CategoryDTO getCategoryName(int categoryID) throws SQLException, NamingException {
        CategoryDTO dto = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_NAME);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String categoryName = rs.getString("categoryName");
                    dto = new CategoryDTO(categoryID, categoryName);
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
        return dto;
    }

    public static boolean insertCategory(String categoryName) throws SQLException, NamingException {
        boolean result = false;
        List<CategoryDTO> list = getListCategory();
        for (CategoryDTO dto : list) {
            if (dto.getCategoryName().equalsIgnoreCase(categoryName)) {
                return result;
            }
        }

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_CATEGORY);
                ptm.setString(1, categoryName);
                int affectRow = ptm.executeUpdate();
                if (affectRow > 0) {
                    result = true;
                    getListCategory();
                    return result;
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
        return result;
    }
}
