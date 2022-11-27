/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.billdetail;

import com.nestf.product.ProductDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author DELL
 */
public class BillDetailDTO implements Serializable{
    private int billID;
    private ProductDTO product;
    private int quantity;
    private double price;
    private double total;

    public BillDetailDTO() {
    }
    
    public BillDetailDTO(ProductDTO product, int quantity, double price, double total) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public BillDetailDTO(int billID, ProductDTO product, int quantity, double price, double total) {
        this.billID = billID;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public int getBillID() {
        return billID;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
