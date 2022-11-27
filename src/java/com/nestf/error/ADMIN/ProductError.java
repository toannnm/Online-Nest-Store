/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.error.ADMIN;

/**
 *
 * @author DELL
 */
public class ProductError {
    private String sellerID;
    private String name;
    private String price;
    private String quantity;
    private String category;
    private String discountPrice;
    private String productDes;
    private String image;
    private String detailDes;
    private String status;

    public ProductError() {
    }

    public ProductError(String sellerID, String name, String price, String quantity, String category, String discountPrice, String productDes, String image, String detailDes, String status) {
        this.sellerID = sellerID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discountPrice = discountPrice;
        this.productDes = productDes;
        this.image = image;
        this.detailDes = detailDes;
        this.status = status;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDes() {
        return detailDes;
    }

    public void setDetailDes(String detailDes) {
        this.detailDes = detailDes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductError{" + "sellerID=" + sellerID + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", category=" + category + ", discountPrice=" + discountPrice + ", productDes=" + productDes + ", image=" + image + ", detailDes=" + detailDes + ", status=" + status + '}';
    }
    
    
    
}
