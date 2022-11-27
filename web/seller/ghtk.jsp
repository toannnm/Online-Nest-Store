
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="img/Logo-GHTK.webp" type="image/x-icon" />
        <title>Giao Hàng Tiết Kiệm</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"
              integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="text-center"><img src="img/Logo-GHTK.webp" style=" width: 6rem;"> Giao hàng tiết kiệm</h1>
        <table class="table">
            <thead>
                <tr class="text-center">
                    <th>STT</th>
                    <th>Sản phẩm</th>
                    <th>Số điện thoại KH</th>
                    <th>Địa chỉ</th>
                    <th>Ngày đặt</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${requestScope.LIST_DELIVERY_ORDER != null}">
                    <c:forEach var="order" varStatus="counter" items="${requestScope.LIST_DELIVERY_ORDER}">
                        <tr>
                            <td class="text-center">${counter.count}</td>
                            <td>
                                <c:forEach var="product" items="${order.detail}">
                                    ${product.product.name} <br>
                                </c:forEach>
                            </td>
                            <td class="text-center">${order.cusPhone}</td>
                            <td class="text-center">${order.address}</td>
                            <td class="text-center"><fmt:formatDate value="${order.time}" pattern="dd-MM-yyyy" /> </td>
                            <td class="text-center">
                                <a href="GHTKConfirm?orderID=${order.billID}&statusID=4">
                                    <button class="btn btn-dark">
                                        Đã giao
                                    </button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>

        <!-- Bootstrap js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js"
                integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk"
        crossorigin="anonymous"></script>

    </body>
</html>
