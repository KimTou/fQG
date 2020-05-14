<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>登陆页面</title>
    <link type="text/css" rel="stylesheet" href="css/login_register.css">

</head>
<body>
<!--注册时显示的画面-->
<div class="wrap" id="wrap">
    <div class="container" id="RegisterContainer">
        <h1>请填写注册信息</h1>

        <input type="text" placeholder="请填写用户名" class="input_email " id="re_username" name="emailNumber" autocomplete="off"/>
        <div class="container_error_notice_01">
        </div>

        <input type="password" placeholder="请填写密码" class="input_password" id="re_password" name="password" autocomplete="off"/>
        <div class="container_error_notice_02">
            <span id="error_notice_02" class="error_notice"></span>
        </div>

        <input type="text" placeholder="请填写邮箱" class="input_email " id="re_email" name="emailNumber" autocomplete="off"/>
        <div class="container_error_notice_01">
        </div>

        <input type="text" placeholder="请填写电话号码" class="input_email " id="re_phone" name="emailNumber" autocomplete="off"/>
        <div class="container_error_notice_01">
        </div>

        <input type="text" placeholder="请填写住址" class="input_email " id="re_address" name="emailNumber" autocomplete="off"/>
        <div class="container_error_notice_01">
        </div>

        <input type="text" placeholder="请输入验证码" class="form-control " id="re_checkCode" name="emailNumber" autocomplete="off">
        <img class="img" id="img" src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清换一张"><br>

        <button class="btn btn-default btn-lg" type="submit" id="re">提交注册信息</button>
        <p class="change_link" >
            <span class="text">已经有账号了 ? <span id="jump_to_denglu" ><a onclick="return false;" class="a">进入登陆</a></span></span>
        </p>
    </div>

    <!--登陆时显示的画面-->
    <div class="container_denglu" id="LoginContainer">
        <h1>QG闲鱼</h1>

        <input type="text" placeholder="请输入用户名" class="input_email" name="EmailNumber_login" id="lg_username" autocomplete="off"/>
        <div class="container_error_notice_01">
            <span id="error_notice_login_01" class="error_notice"></span>
        </div>

        <input type="password" placeholder="请输入密码" class="input_password" name="password_login" id="lg_password" autocomplete="off"/>
        <div class="container_error_notice_02">
            <span id="error_notice_login_02" class="error_notice"></span>
        </div>

<%--        <input type="text" placeholder="请输入验证码" class="form-control " id="lg_checkCode" name="EmailNumber_login" autocomplete="off">--%>
<%--        <img class="img" id="lg_img" src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清换一张"><br>--%>

        <button class="btn btn-default btn-lg" id="lg">登陆</button>

        <br><br>
        <label for="checkbox" >是否记住登陆状态
            <input type="checkbox" id="checkbox" ></label>

        <br><br>
        <a href="${pageContext.request.contextPath}/visitorUsing.jsp" role="button">游客模式</a><br><br>

        <a href="${pageContext.request.contextPath}/email.jsp" role="button">忘记密码</a>

        <p class="change_link" >
            <span class="text">还没有账号 ? <span id="jump_to_zhuce"><a onclick="return false;" class="a">进入注册</a></span></span>
        </p>
    </div>

    <ul>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>

<div class="pointer1" id="pointer1"></div>

<div class="pointer2" id="pointer2"></div>

<div class="pointer3" id="pointer3"></div>

<div class="pointer4" id="pointer4"></div>


<script type = "text/javascript" src="js/login.js"></script>
<!-- 1. 导入CSS的全局样式 -->
<%--    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<!-- 2. jQuery导入，建议使用1.9以上的版本 -->
<%--    <script src="http://code.jquery.com/jquery-latest.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<%--    导入cookie插件--%>
<script type="text/javascript" src="js/jquery.cookie.min.js"></script>

<script type = "text/javascript" src="js/behaviour.js"></script>

<script>
    $("#img").click(function () {
        this.src="${pageContext.request.contextPath}/checkCodeServlet?time="+new Date().getTime();
    });
</script>


</body>
</html>