/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD;

import com.google.gson.Gson;
import com.nestf.income.IncomeDAO;
import com.nestf.util.MyAppConstant;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LoadIncomeToChartServlet", urlPatterns = {"/LoadIncomeToChartServlet"})
public class LoadIncomeToChartServlet extends HttpServlet {

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
//        response.setContentType("application/json");
        ServletContext context = request.getServletContext();

        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.DASHBORAD_PAGE);
        PrintWriter out = response.getWriter();
        String jsonString = "";
        HttpSession session = request.getSession();
        IncomeDAO iDao = (IncomeDAO) session.getAttribute("INCOME");
//        try {
//            1.Admin go to dashboard
//            HttpSession session = request.getSession();

//            2. Admin see dashboard income
            Gson gson = new Gson();

            if (iDao == null) {
                iDao = new IncomeDAO();
            }

//            iDao.caculateIncomePerDay();
//            jsonString = gson.toJson(iDao.getIncomePerDay());
////          5. Update scope
//            session.setAttribute("INCOME", iDao.getIncomePerDay());
//        } catch (SQLException ex) {
//            log("Error at LoadIncomeToChartServlet_SQL: " + ex.getMessage());
//        } catch (NamingException ex) {
//            log("Error at LoadIncomeToChartServlet_Naming: " + ex.getMessage());
//        } finally {
////            6. forward to page
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
////               response.getWriter().write(jsonString);
//        }

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
