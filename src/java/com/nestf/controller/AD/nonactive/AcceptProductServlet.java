/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD.nonactive;

import com.nestf.dao.ADMIN.ProductDAOAdmin;
import com.nestf.error.ADMIN.ProductError;
import com.nestf.product.ProductDTO;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
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
@WebServlet(name = "AcceptProductServlet", urlPatterns = {"/AcceptProductServlet"})
public class AcceptProductServlet extends HttpServlet {

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
        request.setCharacterEncoding("utf-8");
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.PENDING_PRODUCT_PAGE);
        int productID = Integer.parseInt(request.getParameter("productID"));
        ProductError error = new ProductError();
        boolean foundErr = false;

        try {
            HttpSession session = request.getSession();
            ProductDTO dto = ProductDAOAdmin.getProductDetail(productID);
            if (dto.getName().isEmpty()) {
                error.setName("Thêm tên sản phẩm!");
                foundErr = true;
            }
            if (dto.getSelPhone().isEmpty()) {
                error.setSellerID("Chưa có seller!");
                foundErr = true;
            }
            if (dto.getCategory() == null) {
                error.setCategory("Chọn category name!");
                foundErr = true;
            }
            if (dto.getProductDes().isEmpty()) {
                error.setProductDes("Nhập mô tả sản phẩm!");
                foundErr = true;
            }
            if (dto.getImage().isEmpty()) {
                error.setProductDes("Thêm ảnh mô tả sản phẩm!");
                foundErr = true;
            }
            if (foundErr) {
                request.setAttribute("PRODUCT_ERR", error);
            } else {
//                Accept 1 product
//                1. Change product status
                ProductDAOAdmin.acceptProduct(dto);
//                ProductDAOAdmin.insertNEWProductSeller(dto);
                List<ProductDTO> listProduct = ProductDAOAdmin.getListActiveProduct();
                session.setAttribute("LIST_PRODUCT", listProduct);
                List<ProductDTO> listPending = ProductDAOAdmin.getListNonActiveProduct();
                session.setAttribute("LIST_PENDING", listPending);
            }

        } catch (SQLException e) {
            log("Error at AcceptProductServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at AcceptProductServlet_Naming: " + e.getMessage());
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
