/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.bill.BillDAO;
import com.nestf.bill.BillDTO;
import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SellerHomeController", urlPatterns = {"/sellerPage"})
public class SellerHomeController extends HttpServlet {

    private final String ERROR = "login.jsp";
    private final String SUCCESS = "seller/sellerIndex.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO seller = (AccountDTO) session.getAttribute("USER");
            BillDAO billDAO = new BillDAO();
            ProductDAO productDAO = new ProductDAO();
            List<BillDTO> orderList = billDAO.getListOrder(seller.getPhone());
            List<ProductDTO> bestSellList = productDAO.sellerBestSellList(seller.getPhone());
            int waitConfirmCount = 0;
            int waitDeliveryCount = 0;
            int processedCount = 0;
            int cancelOrderCount = 0;
            int outOfStockCount = 0;
            for (BillDTO bill : orderList) {
                switch (bill.getStatus().getStatusID()) {
                    case 1: {
                        waitConfirmCount++;
                        break;
                    }
                    case 2: {
                        waitDeliveryCount++;
                        break;
                    }
                    case 3: {
                        processedCount++;
                        break;
                    }
                    case 5: {
                        cancelOrderCount++;
                        break;
                    }
                }
            }
            for (ProductDTO product : productDAO.getSellerProductList(seller.getPhone())) {
                if (product.getQuantity() == 0) {
                    outOfStockCount++;
                }
            }
            if (orderList != null) {
                request.setAttribute("COUNT_1", waitConfirmCount);
                request.setAttribute("COUNT_2", waitDeliveryCount);
                request.setAttribute("COUNT_3", processedCount);
                request.setAttribute("COUNT_4", cancelOrderCount);
                request.setAttribute("COUNT_5", outOfStockCount);
                request.setAttribute("BEST_SELL_LIST", bestSellList);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at SellerHomeController: " + e.getMessage());
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
