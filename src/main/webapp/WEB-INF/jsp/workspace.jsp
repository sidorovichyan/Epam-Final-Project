<%--
  Created by IntelliJ IDEA.
  User: nikif
  Date: 15.06.2022
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/744/744502.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/workspace.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
    <title>YnAirline</title>
</head>
<body>
<div class="wrapper">
    <c:if test="${param.error}">
        <script>
            window.addEventListener("load", function () {
                swal("Ошибка!", "Произошла неизвестная ошибка! Попробуйте снова...", "error");
            });
        </script>
    </c:if>
    <c:if test="${param.success}">
        <script>
            window.addEventListener("load", function () {
                swal("ОК", "Действие завершилось успешно", "success");
            });
        </script>
    </c:if>
    <c:import url="templates/header.jsp"/>
    <div class="content">
        <c:choose>
            <c:when test="${sessionScope.roleUser == 'dispatcher'}">
                <div class="container">
                    <div class="row py-3">
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}/?command=viewDeparturePlane">
                                <div class="mybutton but-plane mx-2">Список рейсов</div>
                            </a>
                        </div>
                    </div>
                    <div id="all-plane-list" class="col-12">
                        <c:forEach var="plane" items="${requestScope.get('allDeparturePlanes')}">
                            <a style="color: black" href="${pageContext.request.contextPath}/?command=viewPlane&planeId=${plane.id}">
                                <div class="list-box <c:if test="${plane.teamFull == true}">full-team</c:if> <c:if test="${plane.teamFull == false}">no-team</c:if> ">
                                    <div class="list-plane-img">
                                        <img src="${pageContext.request.contextPath}/assets/img/airplane.png">
                                    </div>
                                    <div class="list-plane-id">
                                        <p>${plane.id}</p>
                                    </div>
                                    <div class="list-plane-airport"><p>${plane.departureAirport}
                                        &#8594; ${plane.arrivalAirport}</p></div>
                                    <div class="list-plane-date"><p>${plane.departureDateTime}
                                        &#8594; ${plane.arrivalDateTime}</p></div>
                                    <div class="list-plane-model"><p>${plane.planeType}</p></div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                    <div id="plane-info" class="col-12 my-3">
                        <c:if test="${param.planeId != null}">
                            <div class="list-box" style="background: #3c97bf;">
                                <div class="list-plane-img">
                                    <img src="${pageContext.request.contextPath}/assets/img/airplane.png">
                                </div>
                                <div class="list-plane-id">
                                    <p>${pageContext.request.getAttribute('planeInfo').id}</p>
                                </div>
                                <div class="list-plane-airport">
                                    <p>${pageContext.request.getAttribute('planeInfo').departureAirport}
                                        &#8594; ${pageContext.request.getAttribute('planeInfo').arrivalAirport}</p>
                                </div>
                                <div class="list-plane-date">
                                    <p>${pageContext.request.getAttribute('planeInfo').departureDateTime}
                                        &#8594; ${pageContext.request.getAttribute('planeInfo').arrivalDateTime}</p>
                                </div>
                                <div class="list-plane-model">
                                    <p>${pageContext.request.getAttribute('planeInfo').planeType}</p>
                                </div>
                            </div>
                            <div class="plane-users my-3">
                                <div class="plane-users-dicr my-3">Состав бригады:</div>
                                <c:forEach var="userOnPlane" items="${requestScope.get('teamPlaneList')}">
                                    <div class="plane-users-box" style="background: #3c97bf;">
                                        <div class="col-2 plane-users-name">${userOnPlane.fullName}</div>
                                        <div class="col-3 plane-users-phone">${userOnPlane.phone}</div>
                                        <div class="col-3 plane-users-phone">${userOnPlane.address}</div>
                                        <div class="col-3 plane-users-pos">${userOnPlane.position}</div>
                                        <div class="col-1 plane-users-img"><img alt="Удалить" width="40px"
                                                                                src="${pageContext.request.contextPath}/assets/img/remove.png">
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${empty pageContext.request.getAttribute('teamPlaneList')}">
                                    <div class="col-12 my-4" style="text-align: center;font-size: 24px;font-weight: 600;color: grey">Команда пуста</div>
                                </c:if>

                            </div>
                            <div class="plane-users-buttons my-5">
                                <c:forEach items="${requestScope.get('planeInfo').numberPosition}" var="entry">
                                    <c:if test="${entry.value > 0}">
                                        <c:forEach var="i" begin="1" end="${entry.value}" step="1" varStatus="loop">
                                            <c:if test="${entry.key == 'Пилот'}">
                                                <a href="${pageContext.request.contextPath}/?command=viewEmployeeCanAddPlane&position=${entry.key}&planeId=${pageContext.request.getAttribute('planeInfo').id}">
                                                    <div class="add-to-plane">Добавить пилота</div>
                                                </a>
                                            </c:if>
                                            <c:if test="${entry.key == 'Штурман'}">
                                                <a href="${pageContext.request.contextPath}/?command=viewEmployeeCanAddPlane&position=${entry.key}&planeId=${pageContext.request.getAttribute('planeInfo').id}">
                                                    <div class="add-to-plane">Добавить штурмана</div>
                                                </a>
                                            </c:if>
                                            <c:if test="${entry.key == 'Радист'}">
                                                <a href="${pageContext.request.contextPath}/?command=viewEmployeeCanAddPlane&position=${entry.key}&planeId=${pageContext.request.getAttribute('planeInfo').id}">
                                                    <div class="add-to-plane">Добавить радиста</div>
                                                </a>
                                            </c:if>
                                            <c:if test="${entry.key == 'Стюардесса'}">
                                                <a href="${pageContext.request.contextPath}/?command=viewEmployeeCanAddPlane&position=${entry.key}&planeId=${pageContext.request.getAttribute('planeInfo').id}">
                                                    <div class="add-to-plane">Добавить стюорда</div>
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <c:if test="${param.position != null}">
                                <div class="plane-users my-3">
                                    <div class="col-12 py-1" style="border-bottom: 1px solid grey;float: left">
                                        <div class="col-2 plane-users-name disctip">ФИО</div>
                                        <div class="col-3 plane-users-phone disctip">Телефон</div>
                                        <div class="col-3 plane-users-name disctip">Адрес</div>
                                        <div class="col-3 plane-users-img disctip">Последний рейс</div>
                                    </div>
                                    <c:forEach items="${requestScope.get('canAddToPlaneList')}" var="entry">
                                        <div class="plane-users-box">
                                            <div class="col-2 plane-users-name">${entry.key.fullName}</div>
                                            <div class="col-3 plane-users-phone">${entry.key.phone}</div>
                                            <div class="col-3 plane-users-name">${entry.key.address}</div>
                                            <div class="col-3 plane-users-img">${entry.value}</div>
                                            <a href="${pageContext.request.contextPath}/?command=addToPlaneEmployee&planeId=${pageContext.request.getAttribute('planeInfo').id}&employeeId=${entry.key.id}"><div class="col-1 plane-users-img"><img width="40px" alt="Добавить" src="${pageContext.request.contextPath}/assets/img/add.png"></div></a>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${empty pageContext.request.getAttribute('canAddToPlaneList')}">
                                        <div class="col-12 my-4 py-5" style="text-align: center;font-size: 24px;font-weight: 600;color: grey">Нет подходящих</div>
                                    </c:if>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:when>

            <c:when test="${sessionScope.roleUser == 'admin'}">
                <div class="container">
                    <div class="row py-3">
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}/?command=viewUserList">
                                <div class="mybutton but-users mx-2">Список пользователей</div>
                            </a>
                            <div class="mybutton but-users mx-2 new-entity"
                                 onclick="changeWorkspace(this,'NewUser')">
                                Новый пользователь
                            </div>
                        </div>
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}/?command=viewAllPlaneList">
                                <div class="mybutton but-plane mx-2">Список рейсов</div>
                            </a>
                            <div class=" mybutton but-users mx-2 new-entity"
                                 onclick="changeWorkspace(this,'NewPlane')">
                                Новый рейс
                            </div>
                        </div>
                    </div>
                    <div class="row py-5">
                        <div id="user-list" class="col-12">
                            <c:if test="${param.command == 'viewUserList'}">
                                <div style="border-top: 1px solid grey" class="box-item-user disctip">
                                    <div class="item-user-img"></div>
                                    <div class="item-user-id">ID</div>
                                    <div class="item-user-login">Логин</div>
                                    <div class="item-user-role">Доступ</div>
                                </div>
                            </c:if>
                            <c:forEach var="user" items="${requestScope.get('users')}">
                                <a style="color: black"
                                   href="${pageContext.request.contextPath}/?command=viewUser&clientId=${user.id}">
                                    <div class="box-item-user">
                                        <div class="item-user-img"><img
                                                src="${pageContext.request.contextPath}/assets/img/user.png"></div>
                                        <div class="item-user-id">${user.id}</div>
                                        <div class="item-user-login">${user.login}</div>
                                        <div class="item-user-role">${user.role}</div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                        <c:import url="templates/planeList.jsp"/>
                        <div id="new-user" class="col-12">
                            <h4 class="form-opis">Создание пользователя</h4>
                            <div class="form-new-user-disc">Уважаемые Админы, при заполнении данных будьте придельно
                                внимательны!
                            </div>
                            <form class="form-new-user" action="${pageContext.request.contextPath}/ServletPage"
                                  method="post">
                                <input type="hidden" name="command" value="addNewUser"/>
                                <label>
                                    <input required name="login" placeholder="name@ynairline.by">
                                </label>
                                <label>
                                    <input required name="password" placeholder="Первоначальный пароль">
                                </label>
                                <label>
                                    <input required name="FIO" placeholder="Иванов Иван Иванович">
                                </label>
                                <label>
                                    <select required name="gender">
                                        <option selected disabled>Выберите пол</option>
                                        <option>Мужчина</option>
                                        <option>Женщина</option>
                                    </select>
                                </label>
                                <label>
                                    <select required name="position" id="positions" onchange="moreInfo(this)">
                                        <option selected disabled>Выберите должность</option>
                                        <option>Пилот</option>
                                        <option>Стюардесса</option>
                                        <option>Штурман</option>
                                        <option>Радист</option>
                                        <option>Диспетчер</option>
                                    </select>
                                </label>
                                <div id="info-pilot">
                                    <label>
                                        <input name="flying-hours" placeholder="Часы полёта">
                                    </label>
                                    <label>
                                        <input name="qualification" placeholder="Квалификация">
                                    </label>
                                </div>
                                <label>
                                    <input required name="age" placeholder="Возраст">
                                </label>
                                <label>
                                    <input id="online_phone" name="phone" type="tel" maxlength="50"
                                           autofocus="autofocus" required="required"
                                           value="+375(__)___-__-__"
                                           placeholder="+375(__)___-__-__">
                                </label>
                                <label>
                                    <input required name="address" placeholder="Город проживания (прим. Минск)">
                                </label>
                                <label>
                                    <button class="button-new-user mybutton new-entity" type="submit">Добавить
                                    </button>
                                </label>

                            </form>
                        </div>
                        <div id="new-plane" class="col-12">
                            <h4 class="form-opis">Новый рейс</h4>
                            <div class="form-new-user-disc">Уважаемые Админы, при заполнении данных будьте придельно
                                внимательны!
                            </div>
                            <form class="form-new-plane" action="${pageContext.request.contextPath}/ServletPage"
                                  method="post">
                                <input type="hidden" name="command" value="addNewPlane"/>
                                <label>
                                    <div class="discrip-new-plane"><img class="img-plane-form"
                                                                        src="${pageContext.request.contextPath}/assets/img/departure.png">
                                        <span>Отправление рейса</span></div>
                                </label>
                                <label>
                                    <input required name="departure_airport" placeholder="Аэрапорт отправления">
                                </label>
                                <label>
                                    <input value="YYYY-MM-DDThh:mm" required type="datetime-local"
                                           id="departure_date"
                                           name="departure_date">
                                </label>

                                <label>
                                    <div class="discrip-new-plane"><img class="img-plane-form"
                                                                        src="${pageContext.request.contextPath}/assets/img/arrival.png">
                                        <span>Прибытие рейса</span></div>
                                </label>
                                <label>
                                    <input required name="arrival_airport" placeholder="Аэрапорт прибытия">
                                </label>
                                <label>
                                    <input value="YYYY-MM-DDThh:mm" required type="datetime-local" id="arrival_date"
                                           name="arrival_date">
                                </label>


                                <label>
                                    <div class="discrip-new-plane"><img class="img-plane-form"
                                                                        src="${pageContext.request.contextPath}/assets/img/model.png">
                                        <span>Модель судна</span></div>
                                </label>
                                <label>
                                    <input class="margin-input" name="type-plane" required
                                           placeholder="Модель воздушного судна">
                                </label>
                                <label>
                                    <div class="discrip-new-plane"><img class="img-plane-form"
                                                                        src="${pageContext.request.contextPath}/assets/img/3340200.png">
                                        <span>Состав команды на рейс</span></div>
                                </label>
                                <label>
                                    <input min="1" max="20" class="margin-input" name="n-pilots" required
                                           type="number"
                                           placeholder="Кол-во пилотов">
                                </label>
                                <label>
                                    <input min="1" max="20" class="margin-input" name="n-navigator" required
                                           type="number" placeholder="Кол-во штурманов">
                                </label>
                                <label>
                                    <input min="1" max="20" class="margin-input" name="n-personal" required
                                           type="number" placeholder="Кол-во персонала">
                                </label>
                                <label>
                                    <input min="1" max="20" class="margin-input" name="n-radioman" required
                                           type="number" placeholder="Кол-во радистов">
                                </label>

                                <label>
                                    <button class="new-plane-button mybutton new-entity" type="submit">Добавить
                                    </button>
                                </label>
                            </form>
                        </div>
                        <div id="used-details" class="col-12">
                            <c:if test="${param.clientId != null}">
                                <h4 class="form-opis">Данные пользователя</h4>
                                <form class="form-new-user" action="${pageContext.request.contextPath}/ServletPage"
                                      method="post">
                                    <input type="hidden" name="command" value="changeUser"/>

                                    <label>
                                        <div class="input-descrip">Логин</div>
                                        <input disabled required name="login"
                                               value="${pageContext.request.getAttribute('user_details').login}"
                                               placeholder="name@ynairline.by">
                                    </label>
                                    <label>
                                        <div class="input-descrip">ФИО</div>
                                        <input required name="FIO"
                                               value="${pageContext.request.getAttribute('user_details').fullName}"
                                               placeholder="Иванов Иван Иванович">
                                    </label>
                                    <label>
                                        <div class="input-descrip">Пол</div>
                                        <input required name="gender"
                                               value="${pageContext.request.getAttribute('user_details').gender}">
                                    </label>
                                    <label>
                                        <div class="input-descrip">Должность</div>
                                        <input required name="position"
                                               value="${pageContext.request.getAttribute('user_details').position}">
                                    </label>
                                    <c:if test="${pageContext.request.getAttribute('user_details').position == 'Пилот'}">
                                        <div>
                                            <label>
                                                <div class="input-descrip">Налёт часов</div>
                                                <input name="flying-hours" placeholder="Часы полёта">
                                            </label>
                                            <label>
                                                <div class="input-descrip">Квалификация</div>
                                                <input name="qualification" placeholder="Квалификация">
                                            </label>
                                        </div>
                                    </c:if>
                                    <label>
                                        <div class="input-descrip">Возраст</div>
                                        <input required
                                               value="${pageContext.request.getAttribute('user_details').age}"
                                               name="age" placeholder="Возраст">
                                    </label>
                                    <label>
                                        <div class="input-descrip">Телефон</div>
                                        <input value="${pageContext.request.getAttribute('user_details').phone}"
                                               name="phone" type="tel" maxlength="50">
                                    </label>
                                    <label>
                                        <div class="input-descrip">Адрес</div>
                                        <input required
                                               value="${pageContext.request.getAttribute('user_details').address}"
                                               name="address" placeholder="Адресс места жительства">
                                    </label>
                                    <label>
                                        <button class="button-new-user mybutton new-entity" type="submit">Обновить
                                        </button>
                                    </label>

                                </form>
                            </c:if>
                        </div>
                    </div>


                </div>
            </c:when>

            <c:when test="${sessionScope.roleUser == 'user'}">
                your nav bar for user112
            </c:when>
        </c:choose>
    </div>
    <c:import url="templates/footer.jsp"/>
</div>

<script>
    function moreInfo(x) {
        var select = document.getElementById('positions');
        var option = select.options[select.selectedIndex].text;
        if (option === 'Пилот') {
            document.getElementById('info-pilot').style.display = 'block';
        } else {
            document.getElementById('info-pilot').style.display = 'none';
        }
    }

    function changeWorkspace(y, x) {
        if (x === 'NewUser') {
            document.getElementById('user-list').style.display = 'none';
            document.getElementById('all-plane-list').style.display = 'none';
            document.getElementById('new-plane').style.display = 'none';
            document.getElementById('used-details').style.display = 'none';

            document.getElementById('new-user').style.display = 'block';
        } else if (x === 'NewPlane') {
            document.getElementById('used-details').style.display = 'none';
            document.getElementById('all-plane-list').style.display = 'none';
            document.getElementById('user-list').style.display = 'none';
            document.getElementById('new-plane').style.display = 'block';
            document.getElementById('new-user').style.display = 'none';
        }


    }
</script>
<script type="text/javascript">
    function setCursorPosition(pos, e) {
        e.focus();
        if (e.setSelectionRange) e.setSelectionRange(pos, pos);
        else if (e.createTextRange) {
            var range = e.createTextRange();
            range.collapse(true);
            range.moveEnd("character", pos);
            range.moveStart("character", pos);
            range.select()
        }
    }

    function mask(e) {
        //console.log('mask',e);
        var matrix = this.placeholder,// .defaultValue
            i = 0,
            def = matrix.replace(/\D/g, ""),
            val = this.value.replace(/\D/g, "");
        def.length >= val.length && (val = def);
        matrix = matrix.replace(/[_\d]/g, function (a) {
            return val.charAt(i++) || "_"
        });
        this.value = matrix;
        i = matrix.lastIndexOf(val.substr(-1));
        i < matrix.length && matrix != this.placeholder ? i++ : i = matrix.indexOf("_");
        setCursorPosition(i, this)
    }

    window.addEventListener("DOMContentLoaded", function () {
        var input = document.querySelector("#online_phone");
        input.addEventListener("input", mask, false);
        input.focus();
        setCursorPosition(3, input);
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
