/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;

import com.nestf.account.AccountDTO;
import com.nestf.bill.BillDAO;
import com.nestf.bill.BillDTO;
import com.nestf.billdetail.BillDetailDAO;
import com.nestf.billdetail.BillDetailDTO;
import com.nestf.cart.CartDAO;
import com.nestf.cart.CartItemDTO;
import com.nestf.product.ProductDTO;
import com.nestf.voucher.VoucherDAO;
import com.nestf.voucher.VoucherDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
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
@WebServlet(name = "ConfirmCheckOutServlet", urlPatterns = {"/ConfirmCheckOutServlet"})
public class ConfirmCheckOutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "error.jsp";
    private static final String LOAD_BILL = "LoadBillServlet";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            AccountDTO customer = (AccountDTO) session.getAttribute("USER");
            String phone = customer.getPhone();
            String address = customer.getAddress();
            double total = Double.parseDouble(request.getParameter("total"));
            String transactionID = request.getParameter("transactionID");
            if (request.getParameter("address").length() > 0) {
                address = request.getParameter("address");
            }
            List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("CART");

            BillDAO billDAO = new BillDAO();
            int billID = billDAO.checkOut(phone, address, transactionID, total);

            BillDetailDAO billDetailDAO = new BillDetailDAO();
            boolean check = true;
            for (CartItemDTO cartItem : cart) {
                ProductDTO product = cartItem.getProduct();
                double price = product.getPrice();
                if (product.getDiscountPrice() > 0) {
                    price = product.getDiscountPrice();
                }
                BillDetailDTO billDetail = new BillDetailDTO(billID, cartItem.getProduct(), cartItem.getAmount(), price, price*cartItem.getAmount());
                if (!billDetailDAO.insertBillDetail(billDetail)) {
                    check = false;
                }
            }
            if (check) {
                if (request.getParameter("voucherID") != null) {
                    int voucherID = Integer.parseInt(request.getParameter("voucherID"));
                    VoucherDAO voucherDAO = new VoucherDAO();
                    voucherDAO.RemoveVoucher(voucherID);
                }
                CartDAO cartDAO = new CartDAO();
                for (CartItemDTO cartItem: cart) {
                    cartDAO.removeItemFromCart(cartItem.getProduct().getProductID(), phone);
                }
                VoucherDAO voucherDAO = new VoucherDAO();
                voucherDAO.loadVoucherWallet(phone);
                List<VoucherDTO> voucherWallet = voucherDAO.getList();
                session.setAttribute("VOUCHER_WALLET", voucherWallet);
                session.removeAttribute("CART");
                url = LOAD_BILL;
            }
        } catch (NamingException ex) {
            Logger.getLogger(ConfirmCheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmCheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(url);
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
