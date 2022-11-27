/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.bill.BillDAO;
import com.nestf.bill.BillDTO;
import com.nestf.income.IncomeDAO;
import com.nestf.income.IncomeDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
@WebServlet(name = "SellerIncomeController", urlPatterns = {"/selIncome"})
public class SellerIncomeController extends HttpServlet {

    private final String ERROR = "sellerPage";
    private final String SUCCESS = "seller/sellerIncome.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO seller = (AccountDTO) session.getAttribute("USER");
            IncomeDAO incomeDAO = new IncomeDAO();
            BillDAO billDAO = new BillDAO();
            List<IncomeDTO> onDeliveryIncome = incomeDAO.getSellerIncome(seller.getPhone(), 3);
            List<IncomeDTO> deliveredIncome = incomeDAO.getSellerIncome(seller.getPhone(), 4);
            List<BillDTO> listBill = billDAO.getListOrder(seller.getPhone());
            List<Integer> listIncomeMonth = null;
            for (IncomeDTO income : deliveredIncome) {
                if (listIncomeMonth == null) {
                    listIncomeMonth = new ArrayList<>();
                }
                if (!listIncomeMonth.contains(income.getDate().getMonth() + 1)) {
                    listIncomeMonth.add(income.getDate().getMonth() + 1);
                }
            }
            double onDeliveryTotal = 0;
            double deliveredTotal = 0;
            for (IncomeDTO income : onDeliveryIncome) {
                onDeliveryTotal += income.getTotal();
            }
            for (IncomeDTO income : deliveredIncome) {
                deliveredTotal += income.getTotal();
            }
            if (onDeliveryIncome != null || deliveredIncome != null) {
                request.setAttribute("ON_DELIVERY_TOTAL", onDeliveryTotal);
                request.setAttribute("DELIVERED_TOTAL", deliveredTotal);
                request.setAttribute("ON_DELIVERY_INCOME", onDeliveryIncome);
                request.setAttribute("DELIVERED_INCOME", deliveredIncome);
                request.setAttribute("LIST_ORDER", listBill);
                request.setAttribute("LIST_MONTH", listIncomeMonth);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Errot at SellerIncomeController: " + e.getMessage());
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
