/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.feedback;

/**
 *
 * @author DELL
 */
public class FeedbackDTO {
    private int feedbackID;
    private int productID;
    private String cusPhone;
    private String comment;

    public FeedbackDTO() {
    }

    public FeedbackDTO(int feedbackID, int productID, String cusPhone, String comment) {
        this.feedbackID = feedbackID;
        this.productID = productID;
        this.cusPhone = cusPhone;
        this.comment = comment;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
