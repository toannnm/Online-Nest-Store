/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;

import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.Account;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SendSmsServlet", urlPatterns = {"/SendSmsServlet"})
public class SendSmsServlet extends HttpServlet {

    public static final String ACCOUNT_SID = "AC18fbe6abbf5bde29bdcec02de4d9741b";
    public static final String AUTH_TOKEN = "eb60b0243f3cc32b6441c6e1ee5c37cd";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String result = "notfound";
        try {
            String phone = request.getParameter("phone");
            AccountDAO dao = new AccountDAO();
            AccountDTO acc = dao.getUserByPhone(phone);
            if (acc != null) {
                int convert = Integer.parseInt(phone);
                String newConvert = "+84" + convert;
                Message message = Message.creator(new PhoneNumber(newConvert),
                        new PhoneNumber("+19302033329"),
                        "Mật khẩu của bạn là: " + acc.getPassword() + " .\nXin vui lòng không chia sẻ cho ai khác.").create();
                System.out.println(message.getSid());
                result = "sent";
            }
        } catch (Exception e) {
            log("Error at SendSMSController: " + e.toString());
        } finally {
            response.getWriter().write(result);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
