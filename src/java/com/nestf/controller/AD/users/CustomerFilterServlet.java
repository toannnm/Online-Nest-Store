/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD.users;

import com.nestf.account.AccountDTO;
import com.nestf.dao.ADMIN.CustomerDAOAdmin;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CustomerFilterServlet", urlPatterns = {"/CustomerFilterServlet"})
public class CustomerFilterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.MANAGE_CUSTOMER_PAGE);
        String btAction = request.getParameter("btAction");

        boolean result = false;
        try {
            HttpSession session = request.getSession();
            List<AccountDTO> listCustomer = null;
            if (btAction.equals("all")) {
                listCustomer = CustomerDAOAdmin.getAllCustomer();
                session.setAttribute("LIST_CUSTOMER", listCustomer);
            } else if (btAction.equals("blocked")) {
                listCustomer = CustomerDAOAdmin.getBlockCustomer();
                session.setAttribute("LIST_CUSTOMER", listCustomer);
            }
//            if (btAction.equals("xacnhan")) {
//                listCustomer = CustomerDAOAdmin.getConfirmBill(1);
//                request.setAttribute("SORT_CUSTOMER", listCustomer);
//            }
//            if (btAction.equals("layhang")) {
//                listCustomer = CustomerDAOAdmin.getConfirmBill(2);
//                request.setAttribute("SORT_CUSTOMER", listCustomer);
//            }
//            if (btAction.equals("danggiao")) {
//                listCustomer = CustomerDAOAdmin.getConfirmBill(3);
//                request.setAttribute("SORT_CUSTOMER", listCustomer);
//            }
//            if (btAction.equals("dagiao")) {
//                listCustomer = CustomerDAOAdmin.getConfirmBill(4);
//                request.setAttribute("SORT_CUSTOMER", listCustomer);
//            }
//            if (btAction.equals("dahuy")) {
//                listCustomer = CustomerDAOAdmin.getConfirmBill(5);
//                request.setAttribute("SORT_CUSTOMER", listCustomer);
//            }


        } catch (SQLException e) {
            log("Error at CustomerFilterServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at CustomerFilterServlet_Naming: " + e.getMessage());
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
