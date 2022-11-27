/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;

import com.nestf.account.AccountDTO;
import com.nestf.cart.CartDAO;
import com.nestf.cart.CartItemDTO;
import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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

    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        String url = request.getHeader("Referer");
        String check = "fail";
        try {
            HttpSession session = request.getSession(false);
            if (request.getParameter("productID") != null) {
                int productID = Integer.parseInt(request.getParameter("productID"));
                ProductDAO pDao = new ProductDAO();
                ProductDTO product = pDao.getProductDetail(productID);
                if (product != null) {
                    AccountDTO customer = (AccountDTO) session.getAttribute("USER");
                    String phone = customer.getPhone();
                    CartDAO cartDAO = new CartDAO();
                    List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("CART");
                    if (cart == null) {
                        System.out.println(productID);
                        cart = new ArrayList<>();
                    }
                    cartDAO.setCarts(cart);
                    int newQuantity = -1;
                    int amount = 1;
                    if (url.contains("cart")) {
                        newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
                    } else {
                        amount = Integer.parseInt(request.getParameter("quantity"));
                    }
                    check = cartDAO.AddItem(product, amount, newQuantity, phone);
                    cart = cartDAO.getCarts();
                    session.setAttribute("CART", cart);
                } else {
                    response.setStatus(400);
                }
            }
        } catch (SQLException ex) {
            log("Error at AddtoCartServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("Error at AddtoCartServlet_Naming: " + ex.getMessage());
        } finally {
            if (request.getParameter("buynow") != null) {
                response.sendRedirect(LOAD_BILL);
            } else {
                response.getWriter().write(check);
            }
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
