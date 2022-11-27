/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD;

import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import com.nestf.dao.ADMIN.SellerDAOAdmin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.joda.time.DateTimeFieldType.year;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddNewSeller", urlPatterns = {"/AddNewSeller"})
public class AddNewSeller extends HttpServlet {

    private static final String SUCCESS = "manageSellerPage";
    private static final String ERROR = "manageSellerPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String phone = request.getParameter("phone").trim();
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            int point = 0;
            String role = "SE";
            AccountDAO dao = new AccountDAO();
            AccountDTO acc = new AccountDTO(phone, password, name, address, gender, point, role);
            if (dao.getUserByPhone(phone) != null) {
                request.setAttribute("ERROR", "Số điện thoại đã có người đăng ký");
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                dao.insert(acc);
                int month = java.time.LocalDateTime.now().getMonth().getValue();
                int year = java.time.LocalDateTime.now().getYear();
                String choosetime = "" + year + "-" + month;
                List<AccountDTO> manageSeller = SellerDAOAdmin.getListSellerIncome(month, year);
                HttpSession session = request.getSession();
                session.setAttribute("MANAGE_SELLER", manageSeller);
                session.setAttribute("MONTH", choosetime);
                url = SUCCESS;
                response.sendRedirect(url);
            }

        } catch (Exception e) {
            log("Error at RegisterServlet: " + e.toString());
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
