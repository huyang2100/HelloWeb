<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/11
  Time: 10:42 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Your referer header: ${header.referer}
<br>
<tags:doBodyDemo>
    ${header.referer}
</tags:doBodyDemo>

<a href="viewReferer.jsp">View</a> the referer as Session attribute.
</body>
</html>
