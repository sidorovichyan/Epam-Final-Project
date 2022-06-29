<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nikif
  Date: 23.06.2022
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="all-plane-list" class="col-12">
    <c:forEach var="plane" items="${requestScope.get('allPlanes')}">
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
    </c:forEach>
</div>