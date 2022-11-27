<%-- 
    Document   : newjsp
    Created on : Sep 20, 2022, 3:59:46 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <c:if test="${not empty sessionScope.USER}">
        <c:redirect url=""></c:redirect>
    </c:if>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Quên mật khẩu</title>
        <link rel="icon" href="img/logo.png" type="image/x-icon" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href=" https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />
        <link href="css/nestf.css" rel="stylesheet">
    </head>
    <body class="text-center">
        <div id="navbar" class="sticky-top">
            <nav class="navbar-expand bg-white navbar-light">
                <ul class="navbar">
                    <li class="nav-item col-2 d-inline-block">
                        <a href="home"><img src="img/logo.png" id="logo" class="col-3"></a>
                    </li>
                    <li class="nav-item col-1 d-inline-block">
                        <a href="shop" class="nav-link">Sản phẩm</a>
                    </li>
                    <li class="nav-item col-1 d-inline-block">
                        <a href="handbook" class="nav-link">Cẩm nang</a>
                    </li>
                    <li class="nav-item col-1 d-inline-block">
                        <a href="about" class="nav-link">Về chúng tôi</a>
                    </li>
                   <li class="nav-item col-3 d-inline-block text-center">
                    </li>
                    <li class="nav-item col-2 d-inline-block text-center">                      
                        <div><a href="login" class="nav-link"><i class="fas fa-user    "></i>Đăng nhập</a></div>
                    </li>
                    <li class="nav-item col-1 d-inline-block text-center">
                        <div> 
                        </div>                                                    
                    </li>
                    <li class="nav-item col-1 d-inline-block text-center">                      
                    </li>
                </ul>
            </nav>
        </div>
        <div class="d-inline-block col-4 p-4 rounded bg-light shadow border border-dark" id="login-form">
            <h1 class="mt-2">Quên mật khẩu</h1><br>
            <form>
                <div class="mb-3 text-center"><span>Số điện thoại</span><input type="text" oninput="checkBlank(this)" name="phone" value="" required class="col-5 ms-3 me-3"><button type="button" name="sendPass" disabled id="color-button" class=".countdown" onclick="getPassword(this)">GỬI MÃ</button><br></div>
                <span id="notification"></span>
            </form>
        </div>
        <div class="d-inline-block" id="sidepic">
            <img
                src="img/loginPic.jpg">
        </div> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="js/forgotPassword.js"></script>
    </body>
</html>
