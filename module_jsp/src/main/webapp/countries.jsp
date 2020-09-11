<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/11
  Time: 11:21 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>国家列表</title>
</head>
<body>
    <ul>
        <c:forEach items="${applicationScope.countries}" var="country">
            <li>${country.value}</li>
        </c:forEach>
    </ul>
</body>
</html>
