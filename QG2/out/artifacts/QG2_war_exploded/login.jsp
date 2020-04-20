<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>

    <%--设置切换验证码图片--%>
    <script>
        window.onload=function () {
            document.getElementById("img").onclick=function () {
                this.src="/QG2/checkCodeServlet?time="+new Date().getTime();
            }
        }
    </script>
</head>
<body>

        <h3>用户登录</h3>
        <form action="/QG2/user/login" method="get">
            用户名:<input type="text" name="username"  placeholder="请输入用户名"><br>
            密码:<input type="password" name="password" placeholder="请输入密码"><br>
            验证码:<input type="text" name="checkCode" placeholder="请输入验证码"><br>
            <img id="img" src="/QG2/checkCodeServlet" title="看不清换一张"><br><br>

            <input type="submit" value="登录">
            <input type="button" value="注册" onClick="window.location.href='/QG2/register.jsp'">
        </form>

</body>
</html>
