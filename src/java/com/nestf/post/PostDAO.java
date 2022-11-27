/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.post;

import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class PostDAO {

    private static String POST_LIST = "SELECT [postID],[adPhone],[title],[postDate],[status],[content],[thumbnail] FROM [NestF].[dbo].[tblPost] Where status =1 ORDER BY postDate DESC";
    private static String POST_LIST_ALL = "SELECT [postID],[adPhone],[title],[postDate],[status],[content],[thumbnail] FROM [NestF].[dbo].[tblPost] WHERE [postID] = ? ORDER BY postDate DESC ";
    private static String POST_LIST_ACTIVE = "SELECT [postID],[adPhone],[title],[postDate],[status],[content],[thumbnail] FROM [NestF].[dbo].[tblPost] WHERE status=1 ORDER BY postDate DESC ";
    private static String POST = "SELECT [postID],[adPhone],[title],[postDate],[status],[content],[thumbnail] FROM [NestF].[dbo].[tblPost] WHERE [postID] = ? AND Status=1 ORDER BY postDate DESC";
    private static String POST_NON = "SELECT [postID],[adPhone],[title],[postDate],[status],[content],[thumbnail] FROM [NestF].[dbo].[tblPost] WHERE [postID] = ? AND Status=0 ORDER BY postDate DESC";

    public List<PostDTO> postList() throws SQLException, NamingException {
        List<PostDTO> postList = new ArrayList();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                stm = conn.createStatement();
                rs = stm.executeQuery(POST_LIST);
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String phone = rs.getString("adPhone");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO seller = dao.getUserByPhone(phone);
                    String title = rs.getString("title");
                    Date date = new Date(rs.getTimestamp("postDate").getTime());
                    boolean status = rs.getBoolean("status");
                    String content = rs.getString("content");
                    String thumbnail = rs.getString("thumbnail");
                    postList.add(new PostDTO(postID, seller, title, date, status, content, thumbnail));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return postList;
    }

    public PostDTO getPost(int postID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        PostDTO post = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(POST);
                ptm.setInt(1, postID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("adPhone");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO seller = dao.getUserByPhone(phone);
                    String title = rs.getString("title");
                    Date date = new Date(rs.getTimestamp("postDate").getTime());
                    boolean status = rs.getBoolean("status");
                    String content = rs.getString("content");
                    String thumbnail = rs.getString("thumbnail");
                    post = new PostDTO(postID, seller, title, date, status, content, thumbnail);
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
        return post;
    }

    public PostDTO getPostNonactive(int postID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        PostDTO post = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(POST_NON);
                ptm.setInt(1, postID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("adPhone");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO seller = dao.getUserByPhone(phone);
                    String title = rs.getString("title");
                    Date date = new Date(rs.getTimestamp("postDate").getTime());
                    boolean status = rs.getBoolean("status");
                    String content = rs.getString("content");
                    String thumbnail = rs.getString("thumbnail");
                    post = new PostDTO(postID, seller, title, date, status, content, thumbnail);
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
        return post;
    }

    public PostDTO getPostListAll(int postID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        PostDTO post = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(POST_LIST_ALL);
                ptm.setInt(1, postID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("adPhone");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO seller = dao.getUserByPhone(phone);
                    String title = rs.getString("title");
                    Date date = new Date(rs.getTimestamp("postDate").getTime());
                    boolean status = rs.getBoolean("status");
                    String content = rs.getString("content");
                    String thumbnail = rs.getString("thumbnail");
                    post = new PostDTO(postID, seller, title, date, status, content, thumbnail);
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
        return post;
    }

    public List<PostDTO> getRandomRecommendPost(int currentPostID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<PostDTO> list = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT TOP 3 * FROM tblPost WHERE postID != ? ORDER BY NEWID()";
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, currentPostID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String phone = rs.getString("adPhone");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO seller = dao.getUserByPhone(phone);
                    String title = rs.getString("title");
                    Date date = new Date(rs.getTimestamp("postDate").getTime());
                    boolean status = rs.getBoolean("status");
                    String content = rs.getString("content");
                    String thumbnail = rs.getString("thumbnail");
                    PostDTO post = new PostDTO(postID, seller, title, date, status, content, thumbnail);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(post);
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
}
