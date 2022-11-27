/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.product;

import com.nestf.category.CategoryDTO;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author DELL
 */
public class ProductDTO implements Serializable{
    private int productID;
    private String selPhone;
    private String name;
    private double price;
    private int quantity;
    private CategoryDTO category;
    private double discountPrice;
    private String productDes;
    private String image;
    private String detailDes;
    private boolean status;
    private String[] imagelink;
    private String selName;
    
    public ProductDTO() {
    }

    public ProductDTO(int productID, String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String image, String detailDes, boolean status) {
        this.productID = productID;
        this.selPhone = selPhone;
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

    public ProductDTO(String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String image, String detailDes, boolean status) {
       this.selPhone = selPhone;
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

    public ProductDTO(int productID, String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String detailDes, boolean status, String[] imagelink) {
        this.productID = productID;
        this.selPhone = selPhone;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discountPrice = discountPrice;
        this.productDes = productDes;
        this.detailDes = detailDes;
        this.status = status;
        this.imagelink = imagelink;
    }


    public ProductDTO(int productID, String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String detail, boolean status, String[] imageLink, String selName) {
        this.productID = productID;
        this.selPhone = selPhone;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discountPrice = discountPrice;
        this.productDes = productDes;
        this.detailDes = detail;
        this.status = status;
        this.imagelink = imageLink;
        this.selName = selName;
    }
    
    public ProductDTO(int productID, String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String image, String detail, boolean status, String[] imageLink, String selName) {
        this.productID = productID;
        this.selPhone = selPhone;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discountPrice = discountPrice;
        this.productDes = productDes;
        this.image = image;
        this.detailDes = detail;
        this.status = status;
        this.imagelink = imageLink;
        this.selName = selName;
    }

    public ProductDTO(String selPhone, String name, double price, int quantity, CategoryDTO category, double discountPrice, String productDes, String detailDes, boolean status, String[] imageLink, String selName) {
        this.selPhone = selPhone;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discountPrice = discountPrice;
        this.productDes = productDes;
        this.detailDes = detailDes;
        this.status = status;
        this.imagelink = imageLink;
        this.selName = selName;
    }
    
//  Admin 
    public ProductDTO(int productID, String name, CategoryDTO category, float price, float discountPrice, int quantity, String selName) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.selName = selName;
    }

    public String getSelName() {
        return selName;
    }

    public void setSelName(String selName) {
        this.selName = selName;
    }
    
    public String[] getImagelink() {
        return imagelink;
    }

    public void setImagelink(String[] imagelink) {
        this.imagelink = imagelink;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String printPrice(double price) {
        Locale vie = new Locale("vi", "VN");
        Currency vnd = Currency.getInstance(vie);
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(vie);
        return vndFormat.format(price).replace("đ", "₫");
    }
}
