/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.income;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author DELL
 */
public class IncomeDTO implements Serializable{
    private String selPhone;
    private Date date;
    private double total;

    public IncomeDTO() {
    }

    public IncomeDTO(String selPhone, Date date, double total) {
        this.selPhone = selPhone;
        this.date = date;
        this.total = total;
    }

    public String getSelPhone() {
        return selPhone;
    }

    public void setSelPhone(String selPhone) {
        this.selPhone = selPhone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "IncomeDTO{ selPhone=" + selPhone + ", date=" + date + ", total=" + total + '}';
    }
    
}
