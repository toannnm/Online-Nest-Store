/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.bill;

import com.nestf.billdetail.BillDetailDTO;
import com.nestf.status.StatusDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BillDTO implements Serializable {

    private int billID;
    private String cusPhone;
    private String address;
    private String transactionID;
    private StatusDTO status;
    private Date time;
    private double total;
    private String cancelReason;

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }
    private List<BillDetailDTO> detail;

    public BillDTO() {
    }

    public BillDTO(int billID, String cusPhone, String address, String transactionID, StatusDTO status, Date time, double total, List<BillDetailDTO> detail) {
        this.billID = billID;
        this.cusPhone = cusPhone;
        this.address = address;
        this.transactionID = transactionID;
        this.status = status;
        this.time = time;
        this.total = total;
        this.detail = detail;
    }

    public BillDTO(int billID, String cusPhone, String address, String transactionID, StatusDTO status, Date time, double total, String cancelReason, List<BillDetailDTO> detail) {
        this.billID = billID;
        this.cusPhone = cusPhone;
        this.address = address;
        this.transactionID = transactionID;
        this.status = status;
        this.time = time;
        this.total = total;
        this.cancelReason = cancelReason;
        this.detail = detail;
    }
    
    public BillDTO(int billID, String cusPhone, String address, String transactionID, StatusDTO status, Date time, double total, List<BillDetailDTO> detail, String cancelReason) {
        this.billID = billID;
        this.cusPhone = cusPhone;
        this.address = address;
        this.transactionID = transactionID;
        this.status = status;
        this.time = time;
        this.total = total;
        this.detail = detail;
        this.cancelReason = cancelReason;
    }
   
    public void setBillID(int billID) {
        this.billID = billID;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDetail(List<BillDetailDTO> detail) {
        this.detail = detail;
    }

    public int getBillID() {
        return billID;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public String getAddress() {
        return address;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public Date getTime() {
        return time;
    }

    public double getTotal() {
        return total;
    }

    public List<BillDetailDTO> getDetail() {
        return detail;
    }

      
}
