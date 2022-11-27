/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;


import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import com.nestf.account.AccountError;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateCustomerPasswordServlet", urlPatterns = {"/UpdateCustomerPasswordServlet"})
public class UpdateCustomerPasswordServlet extends HttpServlet {
    
    private static final String ERROR = "account.jsp";
    private static final String SUCCESS = "LogoutServlet";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;        
        try {            
            HttpSession session = request.getSession();
            AccountDTO customer = (AccountDTO) session.getAttribute("USER");
            String newPass = request.getParameter("newPass").trim();          
            boolean check = true;
            String curPassword = customer.getPassword();          
            if (!curPassword.equals(newPass)) {               
                String phone = customer.getPhone();
                AccountDAO dao = new AccountDAO();
                if (dao.updatePassword(phone, newPass)) {
                    url = SUCCESS;
                }
            } else {
                request.setAttribute("ERROR", "Mật khẩu cũ không trùng khớp");
            }
        } catch (Exception e) {
            log("Error at UpdateCustomerPassword: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
