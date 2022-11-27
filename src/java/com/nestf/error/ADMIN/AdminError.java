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
public class AdminError {
    private String passwordError;
    private String nameError;
    private String phoneError;
    private String phoneErrorExist;
    
    public AdminError() {
    }

    public AdminError(String passwordError, String nameError, String phoneError, String phoneErrorExist) {
        this.passwordError = passwordError;
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.phoneErrorExist = phoneErrorExist;
    }
    
    

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getPhoneErrorExist() {
        return phoneErrorExist;
    }

    public void setPhoneErrorExist(String phoneErrorExist) {
        this.phoneErrorExist = phoneErrorExist;
    }
    
    

    @Override
    public String toString() {
        return "AdminError{" + "passwordError=" + passwordError + ", nameError=" + nameError + ", phoneError=" + phoneError + '}';
    }
    
}
