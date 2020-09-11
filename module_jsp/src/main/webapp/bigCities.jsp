<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/10
  Time: 2:41 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Big cities</title>
    <style>
        table, tr, td {
            border: 1px solid #aaee77;
            padding: 3px;
        }
    </style>
</head>
<body>

${fn:escapeXml("use <br/>")}





<%--Capitals--%>
<%--<table>--%>
<%--    <tr style="background: #448755;color: white;font-weight: bold">--%>
<%--        <td>Country</td>--%>
<%--        <td>Capital</td>--%>
<%--    </tr>--%>

<%--    <c:forEach items="${requestScope.capitals}" var="mapItem">--%>
<%--        <tr>--%>
<%--            <td>${mapItem.key}</td>--%>
<%--            <td>${mapItem.value}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>

<%--<br/>--%>

<%--<br>--%>

<%--BigCities--%>
<%--<table>--%>
<%--    <tr style="background: #448755;color: white;font-weight: bold">--%>
<%--        <td>Country</td>--%>
<%--        <td>Capital</td>--%>
<%--    </tr>--%>

<%--    <c:forEach items="${requestScope.bigCities}" var="mapItem">--%>
<%--        <tr>--%>
<%--            <td>${mapItem.key}</td>--%>
<%--            <td>--%>
<%--                <c:forEach items="${mapItem.value}" var="city" varStatus="status">--%>
<%--                    ${city}<c:if test="${!status.last}">, </c:if>--%>
<%--                </c:forEach>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>

<%--<hr>--%>
<%--<fmt:formatNumber value="123456.78" pattern="#,#00.0#"/>--%>
<%--<fmt:formatNumber value="12" type="currency" currencyCode="CNY"/>--%>
<%--<fmt:formatNumber value="0.125" type="percent" minFractionDigits="3"/>--%>

<%--<hr>--%>
<%--<jsp:useBean id="now" class="java.util.Date"/>--%>
<%--Default: <fmt:formatDate value="${now}"/><br/>--%>
<%--Short: <fmt:formatDate value="${now}" dateStyle="short"/><br/>--%>
<%--Medium: <fmt:formatDate value="${now}" dateStyle="medium"/><br/>--%>
<%--Long: <fmt:formatDate value="${now}" dateStyle="long"/><br/>--%>
<%--Full: <fmt:formatDate value="${now}" dateStyle="full"/><br/>--%>

<%--<hr>--%>

<%--Default: <fmt:formatDate type="time" value="${now}"/><br/>--%>
<%--Short: <fmt:formatDate type="time" value="${now}" timeStyle="short"/><br/>--%>
<%--Medium: <fmt:formatDate type="time" value="${now}" timeStyle="medium"/><br/>--%>
<%--Long: <fmt:formatDate type="time" value="${now}" timeStyle="long"/><br/>--%>
<%--Full: <fmt:formatDate type="time" value="${now}" timeStyle="full"/><br/>--%>

<%--<hr>--%>
<%--Default: <fmt:formatDate type="both" value="${now}"/><br/>--%>
<%--Short date short time: <fmt:formatDate type="both" value="${now}" dateStyle="short" timeStyle="short"/><br/>--%>
<%--Long date long time format: <fmt:formatDate type="both" value="${now}" dateStyle="long" timeStyle="long"/><br/>--%>

<%--<hr>--%>
<%--Time zone CT: <fmt:formatDate type="time" value="${now}"--%>
<%--                              timeZone="CT"/><br/>--%>
<%--Time zone HST: <fmt:formatDate type="time" value="${now}"--%>
<%--                               timeZone="HST"/><br/>--%>

<%--<hr>--%>

<%--<fmt:formatDate type="both" value="${now}" pattern="dd.MM.yy"/> <br> <fmt:formatDate type="both" value="${now}" pattern="yyyy年MM月dd日 HH点mm分ss秒" />--%>
</body>
</html>
