<%--
  Created by IntelliJ IDEA.
  User: nikif
  Date: 15.06.2022
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Navigation -->
<header class="header">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo">
                YnAirline
                <div class="header__img"><img src="${pageContext.request.contextPath}/assets/img/icon.png"></div>
            </div>

            <div class="nav__link account">
                <div class="nav__link account-1">${pageContext.session.getAttribute("loginUser")}</div>
                <a style="text-decoration: none" class="btn2  btn--red" href="${pageContext.request.contextPath}/?command=LogOutCommand">Выход</a>
            </div>
        </div>
    </div>
</header>

