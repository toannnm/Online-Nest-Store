/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class FormatPrinter {
    public static String printDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd/MM/YYYY");
        String dateString = sdf.format(date);
        return dateString;
    }
    public static String printMoney(double money) {
        Locale vie = new Locale("vi", "VN");
        Currency vnd = Currency.getInstance(vie);
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(vie);
        return vndFormat.format(money).replace("đ", "₫");
    }
    public static String toUSD(double VNDmoney){
        double usd = VNDmoney/24000;
        NumberFormat usdFormat = NumberFormat.getInstance();
        usdFormat.setMaximumFractionDigits(2);
        return usdFormat.format(usd);
    }
    public static String noFraction(double num){
        return String.valueOf(num).split("\\.")[0];
    }
    public static String paypalUSD(double vnd){
        String usd = toUSD(vnd);
        return usd.replaceAll(",", "");
    }
}
