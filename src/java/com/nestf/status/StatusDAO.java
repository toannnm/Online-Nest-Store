/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.status;

import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class StatusDAO {

    public StatusDTO getStatus(int statusID) throws NamingException, SQLException {
        StatusDTO status = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String query = "SELECT status FROM tblStatus WHERE statusID = ?";
                ptm = conn.prepareStatement(query);
                ptm.setInt(1, statusID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    status = new StatusDTO(statusID, rs.getNString("status"));
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
        return status;
    }
}
