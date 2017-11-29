<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/11/29
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页</title>
</head>
<body>
<div class="fix-box">
    <p class="logo">会员登录</p>
    <form action="/doLogin" method="post">
        <span>用户名：</span><input class="name" tip="请输入用户名" id="username" name="username" type="text"/></p>
        <span>密码：</span><input class="pass" tip="请输入密码" id="password" name="password" type="password"/>
        </p>
        <label><input type="checkbox" id="traderLogin" name="traderLogin" value="1"/>记住用户</label><br><br>
        <input class="btn" type="submit" value="登录"/>
    </form>
</div>
</body>
</html>
