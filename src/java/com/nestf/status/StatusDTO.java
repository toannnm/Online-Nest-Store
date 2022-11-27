/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.status;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class StatusDTO implements Serializable{
    private int statusID;
    private String status;

    public StatusDTO() {
    }
    public StatusDTO(int statusID, String status) {
        this.statusID = statusID;
        this.status = status;
    }

    public int getStatusID() {
        return statusID;
    }
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
