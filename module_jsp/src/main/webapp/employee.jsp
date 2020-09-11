<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/10
  Time: 9:57 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="x" items="${requestScope.capitals}" varStatus="num">
    ${num.count}---${x}<br/>
</c:forEach>

</body>
</html>
