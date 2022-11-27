/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD.profile;

import com.nestf.account.AccountDTO;
import com.nestf.dao.ADMIN.SellerDAOAdmin;
import com.nestf.error.ADMIN.AdminError;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/UpdateProfileServlet"})
public class UpdateProfileServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.EDIT_PROFILE_PAGE);
        HttpSession session = request.getSession();
        AccountDTO dto = (AccountDTO) session.getAttribute("ADMIN");

        String name = request.getParameter("txtFullname");
        String password = request.getParameter("txtPassword");
        String rePassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("txtPhone");
        SellerDAOAdmin dao = new SellerDAOAdmin();

        AdminError error = new AdminError();
        boolean foundErr = false;
        try {
            if (dto.getName().equalsIgnoreCase(name)
                    && dto.getPassword().equalsIgnoreCase(password)
                    && dto.getPhone().equalsIgnoreCase(phone)
                    && password.equalsIgnoreCase(rePassword)) {
                url = (String) siteMap.get(MyAppConstant.AdminFeatures.PROFILE_PAGE);
            } else {
                if (!password.equalsIgnoreCase(rePassword)) {
                    foundErr = true;
                    error.setPasswordError("Mật khẩu phải trùng nhau");
                }

                if (phone.length() != 10) {
                    foundErr = true;
                    error.setPhoneError("Số điện thoại bao gồm 10 số");
                }
                if (name.length() < 3 && name.length() > 50) {
                    foundErr = true;
                    error.setNameError("Độ dài của tên từ 3 đến 50 ký tự");
                }
                if (dao.getExistPhone(phone)) {
                    error.setPhoneError("Số điện thoại đã tồn tại");
                }
                if (foundErr) {
//            store err to request, fw to errors
                    request.setAttribute("CREATE_ERR", error);
                    url = (String) siteMap.get(MyAppConstant.AdminFeatures.EDIT_PROFILE_PAGE);
                } else {
                    dto.setName(name);
                    dto.setPassword(password);
                    dto.setPhone(phone);
                    boolean result = dao.updateAdminProflie(dto, name, password, phone);
                    if (result) {
                        url = (String) siteMap.get(MyAppConstant.AdminFeatures.PROFILE_PAGE);
                    }
                }
            }
            session.setAttribute("ADMIN", dto);
        } catch (NamingException ex) {
            log("UpdateProfileServlet _ Naming " + ex.getMessage());
        } catch (SQLException ex) {
            log("UpdateProfileServlet _ SQL " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
