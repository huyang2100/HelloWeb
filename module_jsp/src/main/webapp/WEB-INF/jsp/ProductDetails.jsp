<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/16
  Time: 6:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>产品详情</title>
    <style type="text/css">
        @import url(/css/main.css);
    </style>
</head>
<body>
<div id="global">
    <h4>产品已经被添加！</h4>
    <p>
        <h5>产品信息：</h5>
        名称：${product.name}<br/>
        描述：${product.description}<br/>
        价格：${product.price}<br/>
    </p>
</div>
</body>
</html>
