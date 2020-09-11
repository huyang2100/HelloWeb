<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/10
  Time: 2:00 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>book list</title>
</head>
<body>
Books in Simple Table
<table border="1px">
    <tr>
        <td>ISBN</td>
        <td>Title</td>
    </tr>

    <c:forEach items="${requestScope.books}" var="book">
        <tr>
            <td>${book.isbn}</td>
            <td>${book.title}</td>
        </tr>
    </c:forEach>
</table>

<br/>

Books in Styled Table
<table border="1px">
    <tr style="background-color: #ababff">
        <td>ISBN</td>
        <td>Title</td>
    </tr>

    <c:forEach items="${requestScope.books}" var="book" varStatus="status">
        <c:if test="${status.count%2 == 0}">
            <tr style="background-color: cadetblue;">
        </c:if>
        <c:if test="${status.count%2 != 0}">
            <tr style="background-color: antiquewhite">
        </c:if>

        <td>${book.isbn}</td>
        <td>${book.title}</td>
        </tr>
    </c:forEach>
</table>

<br/>
ISBNs only:
<c:forEach items="${requestScope.books}" var="book" varStatus="status">
    ${book.isbn}<c:if test="${!status.last}">, </c:if>
</c:forEach>
</body>
</html>
