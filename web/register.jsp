<%-- 
    Document   : newjsp2
    Created on : Sep 20, 2022, 3:59:58 PM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <c:if test="${not empty sessionScope.USER}">
        <c:redirect url=""></c:redirect>
    </c:if>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Đăng ký</title>
        <meta name="description" content="">
        <link rel="icon" href="img/logo.png" type="image/x-icon" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
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
                    <li class="nav-item col-3 d-inline-block">                      
                    </li>
                    <li class="nav-item col-2 d-inline-block">
                        <div ><a href="login" class="nav-link"><i class="fas fa-user    "></i>Đăng nhập</a></div>
                    </li>
                    <li class="nav-item col-1 d-inline-block">                      
                    </li>
                    <li class="nav-item col-1 d-inline-block">
                    </li>
                </ul>
            </nav>
        </div>
        <div class="d-inline-block container-fluid" id="register-form">
            <img src="img/account.svg" alt="">
            <h1>Đăng ký</h1>
            <form action="registerAction" method="POST" id="reg-form">
                <div class="row m-auto col-6">
                    <div class="col-4">
                        <span class="d-inline-block m-3">Họ và tên</span>
                        <br>
                        <span class="d-inline-block m-3">Giới tính</span>
                        <br>
                        <span class="m-3 d-inline-block">Số điện thoại</span>
                        <br>
                        <span class="m-3 d-inline-block">Mật khẩu</span>
                        <br>
                        <span class="m-3 d-inline-block">Xác nhận mật khẩu</span>
                        <br>
                        <span class="d-inline-block m-3">Địa chỉ</span>
                    </div>
                    <div class="col-8">
                        <input type="text" id="name" name="name" oninput="validateForm()" minlength="6" maxlength="30" placeholder="6 - 30 ký tự" class="m-3 me-0">
                        <i id="name-tooltip" style="display:none;" class="fa-solid fa-circle-exclamation text-danger" data-bs-custom-class="error-tooltip" data-bs-toggle="tooltip" data-bs-placement="right" title=""></i><br>
                        <div id="gender" class="m-3">
                            <input type="radio" id="male" checked="" name="gender" value="1">
                            <label for="male">Nam&emsp;</label>
                            <input type="radio" id="female" name="gender" value="0">
                            <label for="female">Nữ</label>
                        </div>
                        <input type="number" name="phone" value="${requestScope.PHONE}" required oninput="validateForm()" maxlength="10" placeholder="10 chữ số" class="m-3 me-0">
                        <i id="error-tooltip" style="display:none;" class="fa-solid fa-circle-exclamation text-danger" data-bs-custom-class="error-tooltip" data-bs-toggle="tooltip" data-bs-placement="right" title="${requestScope.ERROR}"></i><br>                       
                        <input type="password" class="m-3 me-0" name="password" minlength="6" maxlength="20" id="password" placeholder="6 - 20 ký tự" oninput="validateForm()">
                        <i id="password-tooltip" style="display:none;" class="fa-solid fa-circle-exclamation text-danger" data-bs-custom-class="error-tooltip" data-bs-toggle="tooltip" data-bs-placement="right" title="Mật khẩu quá ngắn"></i><br>
                        <input type="password" class="m-3 me-0" name="confirm" minlength="6" maxlength="20" id="confirm" oninput="validateForm()">
                        <i id="similar-tooltip" style="display:none;" class="fa-solid fa-circle-exclamation text-danger" data-bs-custom-class="error-tooltip" data-bs-toggle="tooltip" data-bs-placement="right" title="Không trùng khớp với mật khẩu"></i><br>
                        <input type="text" class="m-3 me-0" name="address" oninput="validateForm()" minlength="20" maxlength="70" placeholder="20 - 70 ký tự">
                        <i id="address-tooltip" style="display:none;" class="fa-solid fa-circle-exclamation text-danger" data-bs-custom-class="error-tooltip" data-bs-toggle="tooltip" data-bs-placement="right" title=""></i><br><br>
                    </div>
                </div>   

                <a href="home"><input type="button" value="HUỶ" id="link-button"></a>
                <input type="submit" disabled value="ĐĂNG KÝ" id="color-button" name="submitButton">
            </form>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.2/js/bootstrap.bundle.min.js" integrity="sha512-BOsvKbLb0dB1IVplOL9ptU1EYA+LuCKEluZWRUYG73hxqNBU85JBIBhPGwhQl7O633KtkjMv8lvxZcWP+N3V3w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="js/register.js"></script>
        <script>
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl)
            })
        </script>
    </body>
</html>
