/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;

import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //        String url = (String) siteMap.get(MyAppConstant.LoginFeatures.LOGIN_ACTION);
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String LOAD_USER_CART = "LoadCartServlet";
    private static final String SELLER_PAGE = "sellerPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = LOGIN_PAGE;
        try {
            String phone = request.getParameter("phone").trim();
            String password = request.getParameter("password").trim();
            AccountDAO dao = new AccountDAO();
            AccountDTO user = dao.checkLogin(phone, password);
            if (user != null) {
                if (user.isStatus()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", user);
                    switch (user.getRole()) {
                        case "US":
                            url = LOAD_USER_CART;
                            break;
                        case "AD":
                            session.setAttribute("ADMIN", user);
                            url = (String) siteMap.get(MyAppConstant.AdminFeatures.INIT_ATTRIBUTE_ACTION);
                            break;
                        case "SE":
                            url = SELLER_PAGE;
                            break;
                    }
                } else request.setAttribute("ERROR", "Số điện thoại này đã bị chặn");
            } else {
                request.setAttribute("ERROR", "Sai mật khẩu hoặc số điện thoại!");
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
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
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
