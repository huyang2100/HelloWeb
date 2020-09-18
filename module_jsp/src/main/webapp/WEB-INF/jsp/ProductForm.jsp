<%--
  Created by IntelliJ IDEA.
  User: huyang
  Date: 2020/9/16
  Time: 6:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品表单</title>
    <style type="text/css">
        @import url(/css/main.css);
    </style>
</head>
<body>

<div id="global">

    <form action="product_save.action" method="post">
        <fieldset>
            <legend>添加一个产品</legend>
            <p>
                <label for="name">名称：</label>
                <input type="text" id="name" name="name" tabindex="1"/>
            </p>
            <p>
                <label for="name">描述：</label>
                <input type="text" id="description" name="description" tabindex="2"/>
            </p>
            <p>
                <label for="name">价格：</label>
                <input type="text" id="price" name="price" tabindex="3"/>
            </p>

            <p id="buttons">
                <input type="reset" id="reset" tabindex="4" value="清空">
                <input type="submit" id="submit" tabindex="5" value="添加">
            </p>
        </fieldset>
    </form>
</div>

</body>
</html>
