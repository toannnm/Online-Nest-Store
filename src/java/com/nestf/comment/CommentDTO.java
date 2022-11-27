/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.comment;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CommentDTO implements Serializable{
    private int productID;
    private int customerPhone;
    private String comment;

    public CommentDTO() {
    }

    public CommentDTO(int productID, int customerPhone, String comment) {
        this.productID = productID;
        this.customerPhone = customerPhone;
        this.comment = comment;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(int customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentDTO{" + "productID=" + productID + ", customerPhone=" + customerPhone + ", comment=" + comment + '}';
    }
    
}
