/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.post;

import com.nestf.account.AccountDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class PostDTO implements Serializable {

    private int postID;
    private AccountDTO seller;
    private String title;
    private Date postDate;
    private boolean status;
    private String content;
    private String thumbnail;

    public PostDTO() {
    }

    public PostDTO(int postID, AccountDTO seller, String title, Date postDate, boolean status, String content, String thumbnail) {
        this.postID = postID;
        this.seller = seller;
        this.title = title;
        this.postDate = postDate;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public PostDTO(int postID, AccountDTO seller, String title, boolean status, String content, String thumbnail) {
        this.postID = postID;
        this.seller = seller;
        this.title = title;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public PostDTO(AccountDTO seller, String title, Date postDate, boolean status, String content, String thumbnail) {
        this.seller = seller;
        this.title = title;
        this.postDate = postDate;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public PostDTO(AccountDTO seller, String title, boolean status, String content, String thumbnail) {
        this.seller = seller;
        this.title = title;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public PostDTO(int postID, String title, boolean status, String content, String thumbnail) {
        this.postID = postID;
        this.title = title;
        this.status = status;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public int getPostID() {
        return postID;
    }

    public AccountDTO getSeller() {
        return seller;
    }

    public String getTitle() {
        return title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public boolean isStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    public void setSeller(AccountDTO seller) {
        this.seller = seller;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
