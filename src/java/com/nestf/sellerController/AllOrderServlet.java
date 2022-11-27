/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.bill.BillDAO;
import com.nestf.bill.BillDTO;
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
@WebServlet(name = "AllOrderServlet", urlPatterns = {"/allOrder"})
public class AllOrderServlet extends HttpServlet {

    private static final String ERROR = "seller/sellerIndex.jsp";
    private static final String SUCCESS = "seller/order.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            BillDAO dao = new BillDAO();
            HttpSession session = request.getSession();
            String selPhone = ((AccountDTO) session.getAttribute("USER")).getPhone();
            List<BillDTO> listOrder = dao.getListOrder(selPhone);
            int counterConfirm = 0;
            int counterWaitDelivery = 0;
            int counterDelivery = 0;
            if (listOrder != null) {
                for (BillDTO billDTO : listOrder) {
                    int status = billDTO.getStatus().getStatusID();
                    switch (status) {
                        case 1: {
                            counterConfirm++;
                            break;
                        }
                        case 2: {
                            counterWaitDelivery++;
                            break;
                        }
                        case 3: {
                            counterDelivery++;
                            break;
                        }
                    }
                }
                request.setAttribute("COUNTER_1", counterConfirm);
                request.setAttribute("COUNTER_2", counterWaitDelivery);
                request.setAttribute("COUNTER_3", counterDelivery);
                request.setAttribute("ORDER_LIST", listOrder);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at AllOrderServlet: " + e.getMessage());
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
