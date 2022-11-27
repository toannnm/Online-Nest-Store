/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.sellerController;

import com.nestf.account.AccountDTO;
import com.nestf.category.CategoryDTO;
import com.nestf.product.ProductDAO;
import com.nestf.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "ProductSellerController", urlPatterns = {"/productSeller"})
public class ProductSellerController extends HttpServlet {
    
    private final String ERROR = "seller/sellerIndex.jsp";
    private final String SUCCESS = "seller/productMangement.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO seller = (AccountDTO) session.getAttribute("USER");
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list = dao.getSellerProductList(seller.getPhone());
            List<ProductDTO> listOutOfStock = null;
            List<String> listCategory = null;
            for (ProductDTO pro : list) {
                if (pro.getQuantity() == 0) {
                    if (listOutOfStock == null) {
                        listOutOfStock = new ArrayList<>();
                    }
                    listOutOfStock.add(pro);
                }
                if (listCategory == null) {
                    listCategory = new ArrayList<>();
                }
                if (!listCategory.contains(pro.getCategory().getCategoryName())) {
                    listCategory.add(pro.getCategory().getCategoryName());
                }
            }
            if (list != null) {
                request.setAttribute("SELLER_PRODUCT_LIST", list);
                request.setAttribute("LIST_OUT_OF_STOCK", listOutOfStock);
                request.setAttribute("CATEGORY_LIST", listCategory);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ProductSellerController: " + e.getMessage());
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
