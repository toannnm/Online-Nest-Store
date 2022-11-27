/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD;

import com.nestf.category.CategoryDAO;
import com.nestf.category.CategoryDTO;
import com.nestf.dao.ADMIN.ProductDAOAdmin;
import com.nestf.dao.ADMIN.SellerDAOAdmin;
import com.nestf.dao.ADMIN.PostDAOAdmin;
import com.nestf.product.ProductDTO;
import com.nestf.account.AccountDTO;
import com.nestf.dao.ADMIN.CustomerDAOAdmin;
import com.nestf.post.PostDTO;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "InitAttributeServlet", urlPatterns = {"/InitAttributeServlet"})
public class InitAttributeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();

        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.REVENUE_BILLCOUNT);

        try {
            HttpSession session = request.getSession();
            List<CategoryDTO> listCategory = CategoryDAO.getListCategory();
            session.setAttribute("LIST_CATEGORY", listCategory);
            List<AccountDTO> listSeller = SellerDAOAdmin.getListSeller();
            session.setAttribute("LIST_SELLER", listSeller);
            List<ProductDTO> listProduct = ProductDAOAdmin.getListActiveProduct();
            session.setAttribute("LIST_PRODUCT", listProduct);
            List<ProductDTO> listNonActicve = ProductDAOAdmin.getListNonActiveProduct();
            session.setAttribute("LIST_PENDING", listNonActicve);
            List<AccountDTO> listActiveCustomer = CustomerDAOAdmin.getAllCustomer();
            session.setAttribute("LIST_CUSTOMER", listActiveCustomer);
            List<AccountDTO> listSuccessCusOrder = CustomerDAOAdmin.getSuccessList();
            session.setAttribute("SUCCESS_ORDER_LIST", listSuccessCusOrder);
            List<AccountDTO> listCancelOrder = CustomerDAOAdmin.getCancelList();
            session.setAttribute("CANCEL_ORDER_LIST", listCancelOrder);
            
            List<AccountDTO> listBlockCustomer = CustomerDAOAdmin.getBlockCustomer();
            session.setAttribute("BLOCK_CUSTOMER", listBlockCustomer);
            
            int month = java.time.LocalDateTime.now().getMonth().getValue(); 
            int year = java.time.LocalDateTime.now().getYear(); 
            String choosetime = "" + year + "-" + month;
            List<AccountDTO> manageSeller = SellerDAOAdmin.getListSellerIncome(month, year);
            session.setAttribute("MANAGE_SELLER", manageSeller);
            session.setAttribute("MONTH", choosetime);
           
            List<PostDTO> listActivePost = PostDAOAdmin.getPostListActive();
            session.setAttribute("LIST_POST", listActivePost);
            List<PostDTO> listPending = PostDAOAdmin.getPostListNonActive();
            session.setAttribute("LIST_PENDING_POST", listPending);
        } catch (Exception e) {
            log("Error at InitAttributeServlet: " + e.toString());
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
