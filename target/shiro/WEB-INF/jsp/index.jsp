<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/11/29
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆成功首页</title>
</head>
<body>
欢迎光临:${user.realName}
${user}

<div><br><input id="but" value="退出" type="button" onclick="toLogin()" /></div>

<script>

    function toLogin() {
        window.location.href = "/logout";
    }
</script>
</body>
</html>
