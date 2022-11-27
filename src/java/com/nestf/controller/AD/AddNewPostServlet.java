/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD;

import com.nestf.account.AccountDAO;
import com.nestf.account.AccountDTO;
import com.nestf.dao.ADMIN.PostDAOAdmin;
import com.nestf.error.ADMIN.PostError;
import com.nestf.post.PostDTO;
import com.nestf.util.MyAppConstant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author toanm
 */
@MultipartConfig
@WebServlet(name = "AddNewPostServlet", urlPatterns = {"/AddNewPostServlet"})
public class AddNewPostServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding("utf-8");
//        ServletContext context = request.getServletContext();
//        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
//        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_POST_PAGE);
//        String phone = request.getParameter("adPhone");
//        AccountDAO dao = new AccountDAO();
//        AccountDTO admin = dao.getUserByPhone(phone);
//        String title = request.getParameter("title");
//        boolean status = true;
//        String content = request.getParameter("content");
//        String thumbnail = request.getParameter("thumbnail");
//        PostError error = new PostError();
//        boolean foundErr = false;
//
//        try {
//            if (title.isEmpty()) {
//                error.setTitle("Nhập tiêu đề bài viết!");
//                foundErr = true;
//            }
//            if (content.isEmpty()) {
//                error.setContent("Nhập nội dung bài viết!");
//                foundErr = true;
//            }
//            if (thumbnail.isEmpty()) {
//                error.setThumbnail("Nhập đường dẫn ảnh của bài viết!");
//                foundErr = true;
//            }
//            PostDTO dto = new PostDTO(admin, title, status, content, thumbnail);
//            request.setAttribute("POST_DETAIL", dto);
//            if (foundErr) {
//                request.setAttribute("POST_ERROR", error);
//                url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_POST_PAGE);
//            } else {
//                HttpSession session = request.getSession();
//                if (dto != null) {
//                    request.setAttribute("PREVIEW_POST", dto);
//                }
//                PostDAOAdmin daoA = new PostDAOAdmin();
//                PostDTO post = daoA.insertPost(dto);
//                if (post != null) {
//                    request.setAttribute("POST_DETAIL", post);
//                }
//                List<PostDTO> listActivePost = PostDAOAdmin.getPostListActive();
//                session.setAttribute("LIST_POST", listActivePost);
//                List<PostDTO> listPending = PostDAOAdmin.getPostListNonActive();
//                session.setAttribute("LIST_PENDING_POST", listPending);
//            }
//        } catch (NamingException ex) {
//        } catch (SQLException ex) {
//            log("Error at AddNewPostServlet_SQL: " + ex.getMessage());
//        } finally {
//
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddNewPostServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(AddNewPostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_POST_PAGE);
        String phone = null ;
        String title = null;
        boolean status = true;
        String content = null;
        String thumbnail = null;
        PostError error = new PostError();
        boolean foundErr = false;

        try {

            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Configure a repository (to ensure a secure temp location is used)
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> items = upload.parseRequest(request);

            // Process the uploaded items
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    String fieldName = new String(item.getFieldName().getBytes("ISO-8859-1"), "utf-8");
                    String fieldValue = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
                    if (fieldName.equals("title")) {
                        title = fieldValue;
                        if (title.isEmpty()) {
                            error.setTitle("Nhập tiêu đề bài viết!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("content")) {
                        content = fieldValue;
                        if (content.isEmpty()) {
                            error.setContent("Nhập nội dung bài viết!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("adPhone")) {
                        phone = fieldValue;
                    } 
                } else {
                    // file
                    String fileName = new String(item.getFieldName().getBytes("ISO-8859-1"), "utf-8");
                    String fileValue = new String(item.getName().getBytes("ISO-8859-1"), "utf-8");
                    if (!(fileValue == null || fileValue.equals(""))) {
                        if (fileName.equals("thumbnail")) {
                            thumbnail = fileValue;
                        }
                        Path path = Paths.get(fileValue);
                        String storePath = servletContext.getRealPath("/img");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        Path checkPath = Paths.get(storePath + "/" + path.getFileName());
                        boolean exists = Files.exists(checkPath);
                        if (!exists) {
                            item.write(uploadFile);
                        }
                    }
                }
            }

            if (thumbnail.isEmpty()) {
                error.setThumbnail("Nhập đường dẫn ảnh của bài viết!");
                foundErr = true;
            } else {
                thumbnail = "img/" + thumbnail;
            }

            AccountDAO dao = new AccountDAO();
            AccountDTO admin = dao.getUserByPhone(phone);
            PostDTO dto = new PostDTO(admin, title, status, content, thumbnail);
            request.setAttribute("POST_DETAIL", dto);
            if (foundErr) {
                request.setAttribute("POST_ERROR", error);
                url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_POST_PAGE);
            } else {
                HttpSession session = request.getSession();
                if (dto != null) {
                    request.setAttribute("PREVIEW_POST", dto);
                }
                PostDAOAdmin daoA = new PostDAOAdmin();
                PostDTO post = daoA.insertPost(dto);
                if (post != null) {
                    request.setAttribute("POST_DETAIL", post);
                }
                List<PostDTO> listActivePost = PostDAOAdmin.getPostListActive();
                session.setAttribute("LIST_POST", listActivePost);
                List<PostDTO> listPending = PostDAOAdmin.getPostListNonActive();
                session.setAttribute("LIST_PENDING_POST", listPending);
            }
        } catch (NamingException ex) {
            log("Error at AddNewPostServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("Error at AddNewPostServlet_SQL: " + ex.getMessage());
        } catch (Exception ex) {
            log("Error at AddNewPostServlet_EX: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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
