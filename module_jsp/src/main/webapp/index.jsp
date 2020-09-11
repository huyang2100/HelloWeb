<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/9
  Time: 4:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${sessionScope.loginedIn==null? "<h1>请先登录</h1>" : "<h1>欢迎：</h1>"}
</body>
</html>
