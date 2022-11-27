/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.bill.BillDAO;
import com.nestf.billdetail.BillDetailDAO;
import com.nestf.billdetail.BillDetailDTO;
import com.nestf.product.ProductDAO;
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
@WebServlet(name = "ProcessOrderController", urlPatterns = {"/processOrder"})
public class ProcessOrderController extends HttpServlet {

    private final String ERROR = "allOrder";
    private final String SUCCESS = "allOrder";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        try {
            int orderId = Integer.parseInt(request.getParameter("orderID"));
            int statusId = Integer.parseInt(request.getParameter("statusID"));
            HttpSession session = request.getSession();
            AccountDTO seller = (AccountDTO) session.getAttribute("USER");
            String reason = request.getParameter("cancelReason");
            if (reason != null) {
                reason = "Người bán " + seller.getName() + " hủy đơn với lí do: " + reason;
            }
            boolean checkUpdate = false;
            BillDAO dao = new BillDAO();
            BillDetailDAO detailDAO = new BillDetailDAO();
            ProductDAO proDAO = new ProductDAO();
            if (reason == null) {
                checkUpdate = dao.updateBillStatus(orderId, statusId);
                if (statusId == 3) {
                    List<BillDetailDTO> listDetail = detailDAO.getBillDetail(orderId);
                    for (BillDetailDTO detail : listDetail) {
                        int newQuantity = detail.getProduct().getQuantity() - detail.getQuantity();
                        proDAO.updateQuantity(detail.getProduct().getProductID(), newQuantity);
                    }
                }
            } else {
                checkUpdate = dao.updateBillStatus(orderId, statusId, reason);
            }
            if (checkUpdate) {
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ProcessOrderController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).include(request, response);
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
