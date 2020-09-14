<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/14
  Time: 9:42 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Asynchronous servlet</title>
</head>
<body>
Main thread: ${requestScope.mainThread}
<br/>
Worker thread: ${requestScope.workerThread}

<hr>
<c:forEach begin="1" end="500" var="num">
    ${num}<br>
</c:forEach>
</body>
</html>
