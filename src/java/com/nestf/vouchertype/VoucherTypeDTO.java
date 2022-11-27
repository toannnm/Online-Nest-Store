/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.vouchertype;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class VoucherTypeDTO implements Serializable{
    
    private int typeID;
    private String voucherName;
    private Double saleValue;
    private int quantity;
    private int point;
    private boolean status;

    public VoucherTypeDTO() {
    }

    public VoucherTypeDTO(String voucherName, Double saleValue, int quantity, int point, boolean status) {
        this.voucherName = voucherName;
        this.saleValue = saleValue;
        this.quantity = quantity;
        this.point = point;
        this.status = status;
    }

    public VoucherTypeDTO(int typeID, String voucherName, Double saleValue, int quantity, int point, boolean status) {
        this.typeID = typeID;
        this.voucherName = voucherName;
        this.saleValue = saleValue;
        this.quantity = quantity;
        this.point = point;
        this.status = status;
    }

    public Double getSaleValue() {
        return saleValue;
    }

    public boolean isStatus() {
        return status;
    }

    public void setSaleValue(Double saleValue) {
        this.saleValue = saleValue;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTypeID() {
        return typeID;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPoint() {
        return point;
    }

    
    
}
