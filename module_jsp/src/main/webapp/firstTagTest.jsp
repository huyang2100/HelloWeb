<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/10
  Time: 5:06 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="easy" uri="/WEB-INF/mytags.tld" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>First Tag</title>
</head>
<body>
Hello!!!
<easy:firstTag></easy:firstTag>
<br/>

<easy:dataFormatter header="一线城市" items="北京,上海,广州,深圳"/>

<hr/>

<easy:select>
    <option value="${value}">${text}</option>
</easy:select>

<hr/>

${easy:reverseString("Hello World")}

<hr>
Today is: <my:firstTag/>

</body>
</html>
