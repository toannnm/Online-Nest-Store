/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.bill;

import com.nestf.billdetail.BillDetailDAO;
import com.nestf.billdetail.BillDetailDTO;
import com.nestf.status.StatusDAO;
import com.nestf.status.StatusDTO;
import com.nestf.util.DBHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class BillDAO {

    private List<BillDTO> bills;

    public List<BillDTO> getBills() {
        return bills;
    }

    public BillDTO validOnProcessingOrder(int billID, String phone) throws NamingException, SQLException {
        getMyAllBills(phone);
        List<BillDTO> onProcessing = getThisStatusBills(bills, 1, 2);
        for (BillDTO bill : onProcessing) {
            if (bill.getBillID() == billID) {
                return bill;
            }
        }
        return null;
    }

    public boolean cancelOrder(int billID, String phone, String cancelReason) throws NamingException, SQLException {
        boolean check = false;
        BillDTO bill = validOnProcessingOrder(billID, phone);
        if (bill == null) {
            return check;
        }
        if (bill.getStatus().getStatusID() > 2) {
            return check;
        }
        if (updateOrderStatus(billID, 5)) {
            addCancelReason(billID, cancelReason);
            check = true;
        }
        return check;
    }

    public List<BillDTO> getThisStatusBills(List<BillDTO> bills, int... statusIDs) {
        List<BillDTO> atStatusBills = new ArrayList<>();
        for (BillDTO bill : bills) {
            for (int i = 0; i < statusIDs.length; i++) {
                if (bill.getStatus().getStatusID() == statusIDs[i]) {
                    atStatusBills.add(bill);
                    break;
                }
            }
        }
        return atStatusBills;
    }
    final String ALL_BILL = "SELECT billID, address, transactionID, statusID, time, total, cancelReason FROM tblBill WHERE cusPhone = ? ORDER BY time DESC";

    public void getMyAllBills(String phone) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                ptm = con.prepareStatement(ALL_BILL);
                ptm.setString(1, phone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int billID = rs.getInt("billID");
                    BillDetailDAO billDetailDAO = new BillDetailDAO();
                    List<BillDetailDTO> billDetail = billDetailDAO.getBillDetail(billID);
                    String address = rs.getNString("address");
                    String transactionID = rs.getString("transactionID");
                    int statusID = rs.getInt("statusID");
                    StatusDAO statusDAO = new StatusDAO();
                    StatusDTO status = statusDAO.getStatus(statusID);
                    Date date = new Date(rs.getTimestamp("time").getTime());
                    Double total = rs.getDouble("total");
                    String cancelReason = rs.getNString("cancelReason");
                    BillDTO dto = new BillDTO(billID, phone, address, transactionID, status, date, total, cancelReason, billDetail);
                    if (bills == null) {
                        bills = new ArrayList<>();
                    }
                    bills.add(dto);
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
    final String CHECK_OUT = "INSERT tblBill([cusPhone], [address],[transactionID], [statusID], [time], [total]) VALUES (?,?,?,1, GETDATE(),?) ";

    public int checkOut(String phone, String address, String transactionID, double total) {
// statusID
//=1:Chờ xác nhận
//=2:Chờ lấy hàng
//=3:Đang giao
//=4:Đã giao
//=5:đã hủy
//=6:Trả hàng/Hoàn tiền        
        int billID = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_OUT, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, phone);
                ptm.setNString(2, address);
                ptm.setString(3, transactionID);
                ptm.setDouble(4, total);
                int check = ptm.executeUpdate();
                if (check > 0) {
                    if (ptm.getGeneratedKeys().next()) {
                        billID = ptm.getGeneratedKeys().getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billID;
    }
    private final String UPDATE = "UPDATE tblBill SET statusID = ? WHERE billID = ?";

    public boolean updateOrderStatus(int billID, int statusID) throws NamingException, SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setInt(1, statusID);
                ptm.setInt(2, billID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    private final String CANCEL = "UPDATE tblBill SET cancelReason = ? WHERE billID = ?";

    public boolean addCancelReason(int billID, String cancelReason) throws NamingException, SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CANCEL);
                ptm.setString(1, cancelReason);
                ptm.setInt(2, billID);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    private static final String GET_LIST_ORDER = "SELECT bi.billID, cusPhone, bi.address, bi.transactionID, statusID, time, bi.total, cancelReason\n"
            + "FROM tblBill bi\n"
            + "JOIN tblBillDetail bilDet\n"
            + "ON bi.billID = bilDet.billID\n"
            + "JOIN tblProductSeller proSel\n"
            + "ON proSel.productID = bilDet.productID\n"
            + "WHERE proSel.selPhone = ? AND isActive = 1\n"
            + "GROUP BY bi.billID, cusPhone, bi.address, bi.transactionID, statusID, time, bi.total, cancelReason\n"
            + "ORDER BY statusID ASC, time DESC";

    public List<BillDTO> getListOrder(String selPhone) throws SQLException {
        List<BillDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            StatusDAO statusDAO = new StatusDAO();
            BillDetailDAO billDetailDAO = new BillDetailDAO();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_ORDER);
                ptm.setString(1, selPhone);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int billID = rs.getInt("billID");
                    String cusPhone = rs.getString("cusPhone");
                    String address = rs.getString("address");
                    String transactionID = rs.getString("transactionID");
                    int statusID = rs.getInt("statusID");
                    Date date = rs.getDate("time");
                    double total = rs.getDouble("total");
                    String reason = rs.getString("cancelReason");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    List<BillDetailDTO> detailList = billDetailDAO.getBillDetail(billID);
                    list.add(new BillDTO(billID, cusPhone, address, transactionID, statusDAO.getStatus(statusID), date, total, detailList, reason));
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

    private final String UPDATE_STATUS = "UPDATE tblBill\n"
            + "SET statusID = ?\n"
            + "WHERE billID = ?";

    public boolean updateBillStatus(int orderID, int statusID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_STATUS);
                ptm.setInt(1, statusID);
                ptm.setInt(2, orderID);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    private final String CANCEL_ORDER = "UPDATE tblBill\n"
            + "SET statusID = ?, cancelReason = ?\n"
            + "WHERE billID = ?";

    public boolean updateBillStatus(int orderID, int statusID, String reason) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CANCEL_ORDER);
                ptm.setInt(1, statusID);
                ptm.setString(2, reason);
                ptm.setInt(3, orderID);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    private final String GET_LIST_DELIVERY_ORDER = "SELECT billID, cusPhone, address, transactionID, statusID, time, total, cancelReason\n"
            + "FROM tblBill\n"
            + "WHERE statusID = 3";

    public List<BillDTO> getListDeliveryOrder() throws SQLException {
        List<BillDTO> list = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            StatusDAO statusDAO = new StatusDAO();
            BillDetailDAO billDetailDAO = new BillDetailDAO();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_LIST_DELIVERY_ORDER);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int billID = rs.getInt("billID");
                    String cusPhone = rs.getString("cusPhone");
                    String address = rs.getString("address");
                    String transactionID = rs.getString("transactionID");
                    int statusID = rs.getInt("statusID");
                    Date date = rs.getDate("time");
                    double total = rs.getDouble("total");
                    String reason = rs.getString("cancelReason");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    List<BillDetailDTO> detailList = billDetailDAO.getBillDetail(billID);
                    list.add(new BillDTO(billID, cusPhone, address, transactionID, statusDAO.getStatus(statusID), date, total, detailList, reason));
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
    private final String GET_TODAY_REVENUE = "SELECT sum(total) as revenue\n"
            + "FROM [NestF].[dbo].[tblBill]\n"
            + "WHERE CAST(tblBill.[time] as date) = CAST(GETDATE() as date)";

    public double getTodayRevenue() throws SQLException {
        double revenue = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_TODAY_REVENUE);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    revenue = rs.getDouble("revenue");
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
        return revenue;
    }
    private final String GET_THIS_YEAR_REVENUE = "SELECT sum(total) as revenue\n"
            + "FROM [NestF].[dbo].[tblBill]\n"
            + "where YEAR(tblBill.[time]) = YEAR(GETDATE())";

    public double getThisYearRevenue() throws SQLException {
        double revenue = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_THIS_YEAR_REVENUE);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    revenue = rs.getDouble("revenue");
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
        return revenue;
    }
    private final String GET_NUM_OF_BILLS_TODAY = "SELECT count(billID) as billCount\n"
            + "FROM [NestF].[dbo].[tblBill]\n"
            + "where CAST(tblBill.time as date) = CAST(GETDATE() as date)";

    public int getTodayNumOfBills() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_NUM_OF_BILLS_TODAY);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("billCount");
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
        return count;
    }
    private final String GET_NUM_OF_PENDING_BILLS_TODAY = "SELECT count(billID) as billCount\n"
            + "FROM [NestF].[dbo].[tblBill]\n"
            + "where CAST(tblBill.time as date) = CAST(GETDATE() as date) and statusID between 1 and 2";

    public int getTodayNumOfPendingBills() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_NUM_OF_PENDING_BILLS_TODAY);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("billCount");
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
        return count;
    }
    private final String GET_NUM_OF_PENDING_BILLS  = "SELECT count(billID) as billCount\n"
                                            + "FROM [NestF].[dbo].[tblBill]\n"
                                            + "where statusID between 1 and 2";
    public int getNumOfPendingBills() throws SQLException {
        int count = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_NUM_OF_PENDING_BILLS);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("billCount");
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
        return count;
    }
}
