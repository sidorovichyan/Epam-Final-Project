<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/744/744502.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/welcome.css"/>
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
    <title>YnAirline</title>
</head>
<style>
    @import url('https://fonts.googleapis.com/css?family=Montserrat:400,800');
</style>
<body>
<h2>YnAirline - Твоё лучшее место работы!<br> </h2>
        <c:if test="${param.wrongLogin}">
            <script>
                window.addEventListener("load",function(){
                    swal("Ошибка!", "Сотрудника с такими данными нет в системе", "error");
                });
            </script>
        </c:if>
        <c:if test="${param.wrongPassword}">
            <script>
                window.addEventListener("load",function(){
                    swal("Ошибка!", "Вы ввели не верный пароль", "error");
                });
            </script>
        </c:if>
        <c:if test="${param.serviceError}">
            <script>
                window.addEventListener("load",function(){
                    swal("Упс...", "Что-то пошло не так :(", "error");
                });
            </script>
        </c:if>
<div class="container" id="container">
    <div class="form-container sign-in-container">
        <form action="${pageContext.request.contextPath}/ServletPage" method="post">
            <h1>Вход</h1>
            <input type="hidden" name="command" value="logIn"/>
            <input name="loginForm" required id="login"  type="text" placeholder="ivanov@ynairline.by"/>
            <input name="loginPass" required id="password"  type="password" placeholder="Пароль" autocomplete="off"/>
            <button  id = "inp" type="submit" >Вход</button>
        </form>
    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-right">
                <h1>Добро пожаловать!</h1>
                <p>Введи свои данные для входа!</p>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
</body>
</html>
