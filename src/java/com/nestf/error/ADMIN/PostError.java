/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.error.ADMIN;

/**
 *
 * @author toanm
 */
public class PostError {

    private int postID;
    private String seller;
    private String title;
    private String postDate;
    private String status;
    private String content;
    private String thumbnail;

    public PostError() {
    }

    public PostError(int postID, String seller, String title, String postDate, String status, String content, String thumbnail) {
        this.postID = postID;
        this.seller = seller;
        this.title = title;
        this.postDate = postDate;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
}
