    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.account;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class AccountDTO implements Serializable {

    private String phone;
    private String password;
    private String name;
    private String address;
    private boolean gender;
    private double point;
    private String role;
    private boolean status;

//    Admin
    private int selQuantity;
    private int billID;
    private String billStatus;
    private String cancelReason;
    private double total; 

    public AccountDTO() {
    }

    public double getPoint() {
        return point;
    }

    public AccountDTO(String phone, String password, String name, String address, boolean gender, double point, String role, boolean status) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.point = point;
        this.role = role;
        this.status = status;
    }

    public AccountDTO(String phone, String password, String name, String address, boolean gender, double point, String role) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.point = point;
        this.role = role;
    }

//    Admin: list seller
    public AccountDTO(String phone, String password, String name, String address, boolean gender, double point, String role, int selQuantity) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.point = point;
        this.role = role;
        this.selQuantity = selQuantity;
    }

//    Admin : All cus
    public AccountDTO(String phone, String name, String address, boolean status, int billID, String billStatus, double total, String cancelReason) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.status = status;
        this.billID = billID;
        this.billStatus = billStatus;
        this.total = total;
        this.cancelReason = cancelReason;
    }

// Admin : block list
    public AccountDTO(String phone, String password, String name, String address, boolean gender, double point, boolean status) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.point = point;
        this.status = status;
    }
    
//    Admin: manage seller
    public AccountDTO(String phone, String name, String address, boolean status, int selQuantity, double total) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.status = status;
        this.selQuantity = selQuantity;
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSelQuantity() {
        return selQuantity;
    }

    public void setSelQuantity(int selQuantity) {
        this.selQuantity = selQuantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AccountDTO{" + "phone=" + phone + ", password=" + password + ", name=" + name + ", address=" + address + ", gender=" + gender + ", point=" + point + ", role=" + role + '}';
    }

}
