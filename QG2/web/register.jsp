<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>

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

    <h3>请填写注册信息</h3>
    <form action="/QG2/user/register" method="get">
        用户名:<input type="text" name="username"><br>
        密码:<input type="password" name="password"><br>
        邮箱:<input type="text" name="email"><br>
        验证码:<input type="text" name="checkCode"><br>
        <img id="img" src="/QG2/checkCodeServlet" title="看不清换一张"><br><br>

        <input type="submit" value="提交注册信息"><br>
        <input type="button" value="返回" onClick="window.location.href='/QG2/login.jsp'">
    </form>

</body>
</html>
