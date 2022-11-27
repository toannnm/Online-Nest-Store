/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD.users;

import com.nestf.account.AccountDTO;
import com.nestf.billdetail.BillDetailDAO;
import com.nestf.billdetail.BillDetailDTO;
import com.nestf.dao.ADMIN.CustomerDAOAdmin;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author NameIsDuy
 */
@WebServlet(name = "CustomerDetailServlet", urlPatterns = {"/CustomerDetailServlet"})
public class CustomerDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.MANAGE_CUSTOMER_PAGE);
        try {
            String cusPhone = request.getParameter("phone");
            List<AccountDTO> listDetail = CustomerDAOAdmin.getDetailList(cusPhone);
            BillDetailDAO billDAO = new BillDetailDAO();
            List<BillDetailDTO> listBillDetail = null;
            List<BillDetailDTO> listCusBillDetail = new ArrayList<>();
            for (AccountDTO acc : listDetail) {
                if (acc.getBillStatus().equals("Đã giao") || acc.getBillStatus().equals("Đã hủy")) {
                    listBillDetail = billDAO.getBillDetail(acc.getBillID());
                    listCusBillDetail.addAll(listBillDetail);
                }
            }
            if (listDetail != null) {
                request.setAttribute("BILL_DETAIL_LIST", listCusBillDetail);
                request.setAttribute("CUS_DETAIL_LIST", listDetail);
            }
        } catch (SQLException e) {
            log("Error at BlockCustomerServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at BlockCustomerServlet_Naming: " + e.getMessage());
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
