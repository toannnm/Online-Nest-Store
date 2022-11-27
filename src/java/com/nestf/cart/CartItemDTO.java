/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.cart;

import com.nestf.product.ProductDTO;
import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class CartItemDTO implements Serializable{
    private ProductDTO product;
    private int amount;

    public CartItemDTO() {
    }

    public CartItemDTO(ProductDTO product, int amount) {
        this.product = product;
        this.amount = amount;
    }

//    public CartItemDTO(ProductDTO product, int amount) {
//        this.product = product;
//        this.amount = amount;
//    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
