/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nestf.controller.AD;

import com.nestf.account.AccountDTO;
import com.nestf.category.CategoryDAO;
import com.nestf.category.CategoryDTO;
import com.nestf.dao.ADMIN.ProductDAOAdmin;
import com.nestf.dao.ADMIN.SellerDAOAdmin;
import com.nestf.error.ADMIN.ProductError;
import com.nestf.product.ProductDTO;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author DELL
 */
@MultipartConfig
@WebServlet(name = "AddNewProductServlet", urlPatterns = {"/AddNewProductServlet"})
public class AddNewProductServlet extends HttpServlet {

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
//        request.setCharacterEncoding("utf-8");
//        ServletContext context = request.getServletContext();
//        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
//        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_PRODUCT_PAGE);
//
//        String name = request.getParameter("txtName");
//        double price = !request.getParameter("numprice").isEmpty() ? Double.parseDouble(request.getParameter("numprice")) : 0;
//        int quantity = !request.getParameter("quantity").isEmpty() ? Integer.parseInt(request.getParameter("quantity")) : 0;
//        double discountPrice = !request.getParameter("disPrice").isEmpty() ? Double.parseDouble(request.getParameter("disPrice")) : 0;
//        String productDes = request.getParameter("productdesc");
//        String image1 = request.getParameter("image1");
//        String image2 = request.getParameter("image2");
//        String image3 = request.getParameter("image3");
//        String image4 = request.getParameter("image4");
//        String image5 = request.getParameter("image5");
//        String detailDes = request.getParameter("detaildesc");
//        boolean status = true;
//        String categoryName = request.getParameter("categoryName");
//        String selName = request.getParameter("selName");
//
//        String btAction = request.getParameter("btAction");
//
//        ProductError error = new ProductError();
//        boolean foundErr = false;
//
//        try {
//            if (name.isEmpty()) {
//                error.setName("Nhập tên sản phẩm!");
//                foundErr = true;
//            }
//            if (price == 0.0) {
//                error.setPrice("Nhập giá sản phẩm!");
//                foundErr = true;
//            }
//            if (quantity == 0) {
//                error.setQuantity("Nhập số lượng!");
//                foundErr = true;
//            }
//            if (discountPrice > price) {
//                error.setDiscountPrice("Giá mới thấp hơn giá gốc hoặc không có");
//                foundErr = true;
//            }
//            if (productDes.isEmpty()) {
//                error.setProductDes("Nhập mô tả sản phẩm!");
//                foundErr = true;
//            }
//            if (image1.isEmpty() && image2.isEmpty() && image3.isEmpty() && image4.isEmpty() && image5.isEmpty()) {
//                error.setImage("Nhập link ảnh");
//                foundErr = true;
//            }
//            if (categoryName.isEmpty()) {
//                error.setCategory("Chọn category name");
//                foundErr = true;
//            }
//            if (selName.isEmpty()) {
//                error.setSellerID("Chọn seller");
//                foundErr = true;
//            }
//            CategoryDTO category = CategoryDAO.getCategoryID(categoryName);
////            TH thêm mới category
//            if (category == null) {
//                category = new CategoryDTO(categoryName);
//            }
//            String[] imageLink = {image1, image2, image3, image4, image5};
//            String selPhone = null;
//            if(!selName.isEmpty()){
//                selPhone = SellerDAOAdmin.getSellerGivenName(selName).getPhone();
//            }
//            if (selPhone == null) {
//                error.setSellerID("Chọn seller");
//                foundErr = true;
//            }
//            ProductDTO dto = new ProductDTO( selPhone, name, price, quantity, category, discountPrice, productDes, detailDes, status, imageLink, selName);
//            request.setAttribute("PRODUCT_DETAIL", dto);
//
//            if (foundErr) {
//                request.setAttribute("PRODUCT_ERR", error);
//                url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_PRODUCT_PAGE);
//            } else {
//                HttpSession session = request.getSession();
////                1. Tạo Productdto tạm thời chứa các entity 
//                if (dto != null) {
//                    request.setAttribute("PREVIEW_PRODUCT", dto);
//                }
//                if (btAction.equalsIgnoreCase("Submit")) {
////                    1. Xem same category ko
//                    
////                    Nếu dto ko co cateID => new category => insert db 
//                    if (dto.getCategory().getCategoryID() == 0) {
//                        CategoryDAO.insertCategory(dto.getCategory().getCategoryName());
//                    }
//                    ProductDAOAdmin dao = new ProductDAOAdmin();
//                    String image = "";
//                    for (int i = 0; i < imageLink.length; i++) {
//                        if (imageLink[i] != null) {
//                            image += imageLink[i] + ProductDAOAdmin.INSERT_REGEX;
//                        }
//                    }
//                    dto.setImage(image);
//                    
////                2. Insert dto vào DB
////                3. Update lai product detail
//                    ProductDTO productDetail = dao.insertProduct(dto);
//                    if (productDetail != null) {
//                        request.setAttribute("PRODUCT_DETAIL", productDetail);
//                        request.setAttribute("SUBMIT_PRODUCT", productDetail);
//                    }
//                    
////                4. Add product to pending or accepted 
//                    List<ProductDTO> listAccepted;
//                    List<ProductDTO> listPending;
//
//                    listAccepted = ProductDAOAdmin.getListActiveProduct();
//                    session.setAttribute("LIST_PRODUCT", listAccepted);
//
//                    listPending = ProductDAOAdmin.getListNonActiveProduct();
//                    session.setAttribute("LIST_PENDING", listPending);
//
//                    List<AccountDTO> listSeller = SellerDAOAdmin.getListSeller();
//                    session.setAttribute("LIST_SELLER", listSeller);
//
//                    List<CategoryDTO> listCategory = CategoryDAO.getListCategory();
//                    session.setAttribute("LIST_CATEGORY", listCategory);
//                }
//            }
//
//        } catch (NamingException ex) {
//            log("AddNewProductServlet _ Naming " + ex.getMessage());
//        } catch (SQLException ex) {
//            log("AddNewProductServlet _ SQL " + ex.getMessage());
//        } finally {
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITEMAP");
        String url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_PRODUCT_PAGE);

        String name = null;
        double price = 0;
        int quantity = 0;
        double discountPrice = 0;
        String productDes = null;
        String detailDes = "";
        boolean status = true;
        String categoryName = null;
        String selName = null;
        String selPhone = null;

        String image1 = null;
        String image2 = null;
        String image3 = null;
        String image4 = null;
        String image5 = null;

        String btAction = null;
        ProductError error = new ProductError();
        boolean foundErr = false;
        CategoryDTO category = null;

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
                    if (fieldName.equals("txtName")) {
                        name = fieldValue;
                        if (name.isEmpty()) {
                            error.setName("Nhập tên sản phẩm!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("numprice")) {
                        price = !fieldValue.isEmpty() ? Double.parseDouble(fieldValue) : 0;
                        if (price == 0.0) {
                            error.setPrice("Nhập giá sản phẩm!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("quantity")) {
                        quantity = !fieldValue.isEmpty() ? Integer.parseInt(fieldValue) : 0;
                        if (quantity == 0) {
                            error.setQuantity("Nhập số lượng!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("disPrice")) {
                        discountPrice = !fieldValue.isEmpty() ? Double.parseDouble(fieldValue) : 0;
                        if (discountPrice > price) {
                            error.setDiscountPrice("Giá mới thấp hơn hoặc bằng giá gốc");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("productdesc")) {
                        productDes = fieldValue;
                        if (productDes.isEmpty()) {
                            error.setProductDes("Nhập mô tả sản phẩm!");
                            foundErr = true;
                        }
                    } else if (fieldName.equals("categoryName")) {
                        categoryName = fieldValue;
                        if (categoryName.isEmpty()) {
                            error.setCategory("Chọn category name");
                            foundErr = true;
                        } else {
                            category = CategoryDAO.getCategoryID(categoryName);
                            //            TH thêm mới category
                            if (category == null) {
                                category = new CategoryDTO(categoryName);
                            }
                        }
                    } else if (fieldName.equals("selName")) {
                        selName = fieldValue;
                        if (selName.isEmpty()) {
                            error.setSellerID("Chọn seller");
                            foundErr = true;
                        } else {
                            selPhone = SellerDAOAdmin.getSellerGivenName(selName).getPhone();
                            if (selPhone == null) {
                                error.setSellerID("Chọn seller");
                                foundErr = true;
                            }
                        }
                    } else if (fieldName.equals("btAction")) {
                        btAction = fieldValue;
                        break;
                    }
                } else {
                    // file
                    String fileName = new String(item.getFieldName().getBytes("ISO-8859-1"), "utf-8");
                    String fileValue = new String(item.getName().getBytes("ISO-8859-1"), "utf-8");
                    if (!(fileValue == null || fileValue.equals(""))) {
                        if (fileName.equals("image1")) {
                            image1 = fileValue;
                        } else if (fileName.equals("image2")) {
                            image2 = fileValue;
                        } else if (fileName.equals("image3")) {
                            image3 = fileValue;
                        } else if (fileName.equals("image4")) {
                            image4 = fileValue;
                        } else if (fileName.equals("image5")) {
                            image5 = fileValue;
                        }
                        Path path = Paths.get(fileValue);
                        String storePath = servletContext.getRealPath("/img");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        Path checkPath = Paths.get(storePath + "/" + path.getFileName());
                        boolean exists = Files.exists(checkPath);
                        if (!exists) {
                            item.write(uploadFile);
                        }
                        System.out.println(storePath + "\\" + path.getFileName());
                    }
                }
            }
            HttpSession session = request.getSession();
            String[] oldImageLink = (String[]) session.getAttribute("IMAGE_LINK");

            // Lưu tên ảnh
            String urlImage = "img/";
            String[] imageLink = {image1, image2, image3, image4, image5};

            for (int i = 0; i < 5;) {
                if (imageLink[i] == null || imageLink[i].isEmpty()) {
                    if (oldImageLink != null) {
                        if (i >= oldImageLink.length) {
                            i++;
                        }
                        for (; i < oldImageLink.length;) {
                            if (oldImageLink[i] != null && !oldImageLink[i].isEmpty()) {
                                imageLink[i] = oldImageLink[i];
                            }
                            i++;
                            break;
                        }
                    } else {
                        i++;
                    }
                } else {
                    imageLink[i] = urlImage + imageLink[i];
                    i++;
                }
            }
            int count = 0;
            for (int i = 0; i < 5; i++) {
                if (imageLink[i] == null || imageLink[i].isEmpty() || imageLink[i].equals("")) {
                    count++;
                }
            }
            if (count == 5) {
                error.setImage("Thêm ảnh");
                foundErr = true;
            }

            ProductDTO dto = new ProductDTO(selPhone, name, price, quantity, category, discountPrice, productDes, detailDes, status, imageLink, selName);
            request.setAttribute("PRODUCT_DETAIL", dto);
            session.setAttribute("IMAGE_LINK", imageLink);
            if (foundErr) {
                request.setAttribute("PRODUCT_ERR", error);
                url = (String) siteMap.get(MyAppConstant.AdminFeatures.ADD_PRODUCT_PAGE);
            } else {

//                1. Tạo Productdto tạm thời chứa các entity 
                if (dto != null) {
                    request.setAttribute("PREVIEW_PRODUCT", dto);
                }
                if (btAction.equalsIgnoreCase("Submit")) {
//                    1. Xem same category ko

//                    Nếu dto ko co cateID => new category => insert db 
                    if (dto.getCategory().getCategoryID() == 0) {
                        CategoryDAO.insertCategory(dto.getCategory().getCategoryName());
                    }
                    ProductDAOAdmin dao = new ProductDAOAdmin();
                    String image = "";
                    for (int i = 0; i < imageLink.length; i++) {
                        if (imageLink[i] != null) {
                            image += imageLink[i] + ProductDAOAdmin.INSERT_REGEX;
                        }
                    }
                    dto.setImage(image);

//                2. Insert dto vào DB
//                3. Update lai product detail
                    ProductDTO productDetail = (ProductDTO) request.getAttribute("PRODUCT_DETAIL");
                    productDetail = dao.insertProduct(dto);
                    if (productDetail != null) {
                        request.setAttribute("PRODUCT_DETAIL", dto);
                        request.setAttribute("SUBMIT_PRODUCT", dto);
                    }

//                4. Add product to pending or accepted 
                    List<ProductDTO> listAccepted;
                    List<ProductDTO> listPending;

                    listAccepted = ProductDAOAdmin.getListActiveProduct();
                    session.setAttribute("LIST_PRODUCT", listAccepted);

                    listPending = ProductDAOAdmin.getListNonActiveProduct();
                    session.setAttribute("LIST_PENDING", listPending);

                    List<AccountDTO> listSeller = SellerDAOAdmin.getListSeller();
                    session.setAttribute("LIST_SELLER", listSeller);

                    List<CategoryDTO> listCategory = CategoryDAO.getListCategory();
                    session.setAttribute("LIST_CATEGORY", listCategory);
                    session.removeAttribute("IMAGE_LINK");
                }
            }

        } catch (NamingException ex) {
            log("AddNewProductServlet _ Naming " + ex.getMessage());
        } catch (SQLException ex) {
            log("AddNewProductServlet _ SQL " + ex.getMessage());
        } catch (FileUploadException ex) {
            Logger.getLogger(AddNewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AddNewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
