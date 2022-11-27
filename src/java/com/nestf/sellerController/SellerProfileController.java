/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.income.IncomeDAO;
import com.nestf.income.IncomeDTO;
import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NameIsDuy
 */
@WebServlet(name = "SellerProfileController", urlPatterns = {"/sellerProfile"})
public class SellerProfileController extends HttpServlet {

    private final String ERROR = "sellerPage";
    private final String SUCCESS = "seller/profile.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO seller = (AccountDTO) session.getAttribute("USER");
            ProductDAO dao = new ProductDAO();
            IncomeDAO incomeDAO = new IncomeDAO();
            List<ProductDTO> list = dao.getSellerProductList(seller.getPhone());
            int productCount = 0;
            for (ProductDTO pro : list) {
                productCount++;
            }
            List<IncomeDTO> deliveredIncome = incomeDAO.getSellerIncome(seller.getPhone(), 4);
            double deliveredTotal = 0;
            int curMonth = new Date().getMonth();
            for (IncomeDTO income : deliveredIncome) {
                if (income.getDate().getMonth() == curMonth) {
                    deliveredTotal += income.getTotal();
                }
            }
            request.setAttribute("PRODUCT_COUNT", productCount);
            request.setAttribute("INCOME_TOTAL", deliveredTotal);
            url = SUCCESS;
        } catch (Exception e) {
            log("Error at SellerProfileController: " + e.getMessage());
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
