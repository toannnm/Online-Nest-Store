/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.productseller;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class ProductSellerDTO implements Serializable{
    private int productSellerID;
    private int productID;
    private String selPhone;
    private boolean isActive;

    public ProductSellerDTO() {
    }

    public ProductSellerDTO(int productSellerID, int productID, String selPhone, boolean isActive) {
        this.productSellerID = productSellerID;
        this.productID = productID;
        this.selPhone = selPhone;
        this.isActive = isActive;
    }

    public int getProductSellerID() {
        return productSellerID;
    }

    public void setProductSellerID(int productSellerID) {
        this.productSellerID = productSellerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getSelPhone() {
        return selPhone;
    }

    public void setSelPhone(String selPhone) {
        this.selPhone = selPhone;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
