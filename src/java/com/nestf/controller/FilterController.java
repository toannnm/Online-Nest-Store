/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller;

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

/**
 *
 * @author Mr Duy
 */
@WebServlet(name = "FilterController", urlPatterns = {"/FilterController"})
public class FilterController extends HttpServlet {

    public static final String SUCCESS = "shopFilter.jsp";
    public static final String ERROR = "shopFilter.jsp";
    private static final String SHOP_CONTROLLER = "ShopPageController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String categoryFilter = request.getParameter("categoryFilter");
            int category = 0;
            int priceMin = 0, priceMax = 0;
            ProductDAO dao = new ProductDAO();
            String priceFilter = request.getParameter("priceFilter");
            List<ProductDTO> listPro = null;

            if (!"empty".equals(categoryFilter) && priceFilter.equals("empty")) {
                switch (categoryFilter) {
                    case "yenTho": {
                        category = 1;
                        break;
                    }
                    case "yenTinhChe": {
                        category = 2;
                        break;
                    }
                    case "yenChungTuoi": {
                        category = 3;
                        break;
                    }
                    case "yenChungSan": {
                        category = 4;
                        break;
                    }
                }
                
                 listPro = dao.getFilterProductCategory(category);
            } else if (!priceFilter.equals("empty") && categoryFilter.equals("empty")) {
                switch (priceFilter) {
                    case "below1": {
                        priceMax = 1000000;
                        break;
                    }
                    case "1to2": {
                        priceMin = 1000000;
                        priceMax = 2000000;
                        break;
                    }
                    case "2to3": {
                        priceMin = 2000000;
                        priceMax = 3000000;
                        break;
                    }
                    case "3to4": {
                        priceMin = 3000000;
                        priceMax = 4000000;
                        break;
                    }
                    case "over4": {
                        priceMin = 4000000;
                        priceMax = 10000000;
                        break;
                    }
                }
                listPro = dao.getFilterProductPrice(priceMin, priceMax);
            } else if (!categoryFilter.equals("empty") && !priceFilter.equals("empty")) {
                switch (categoryFilter) {
                    case "yenTho": {
                        category = 1;
                        break;
                    }
                    case "yenTinhChe": {
                        category = 2;
                        break;
                    }
                    case "yenChungTuoi": {
                        category = 3;
                        break;
                    }
                    case "yenChungSan": {
                        category = 4;
                        break;
                    }
                }
                switch (priceFilter) {
                    case "below1": {
                        priceMax = 1000000;
                        break;
                    }
                    case "1to2": {
                        priceMin = 1000000;
                        priceMax = 2000000;
                        break;
                    }
                    case "2to3": {
                        priceMin = 2000000;
                        priceMax = 3000000;
                        break;
                    }
                    case "3to4": {
                        priceMin = 3000000;
                        priceMax = 4000000;
                        break;
                    }
                    case "over4": {
                        priceMin = 4000000;
                        priceMax = 10000000;
                        break;
                    }
                }
                listPro = dao.getFilterProductBoth(category, priceMin, priceMax);
            } else if (categoryFilter.equals("empty") && priceFilter.equals("empty")){
                response.sendRedirect(SHOP_CONTROLLER);
            }
            if (listPro != null && listPro.size() > 0) {
                    request.setAttribute("LIST_PRODUCT_FILTER", listPro);
                    url = SUCCESS;
                }
        } catch (Exception e) {
            e.printStackTrace();
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
