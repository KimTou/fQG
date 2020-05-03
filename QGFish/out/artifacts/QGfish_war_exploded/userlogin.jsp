<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>登陆页面</title>
    <link type="text/css" rel="stylesheet" href="css/login_register.css">
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
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

        <input type="text" placeholder="请填写电话" class="input_email " id="re_phone" name="emailNumber" autocomplete="off"/>
        <div class="container_error_notice_01">
        </div>

        <input type="text" placeholder="请填写真实姓名" class="input_email " id="re_name" name="emailNumber" autocomplete="off"/>
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
        <h1>登陆</h1>

        <input type="text" placeholder="请输入用户名" class="input_email" name="EmailNumber_login" id="lg_username" autocomplete="off"/>
        <div class="container_error_notice_01">
            <span id="error_notice_login_01" class="error_notice"></span>
        </div>

        <input type="password" placeholder="请输入密码" class="input_password" name="password_login" id="lg_password" autocomplete="off"/>
        <div class="container_error_notice_02">
            <span id="error_notice_login_02" class="error_notice"></span>
        </div>

        <button class="btn btn-default btn-lg" id="lg">登陆</button>
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
<%--要在联网状态下使用--%>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type = "text/javascript" src="js/behaviour.js"></script>
<script>
    $("#img").click(function () {
        this.src="${pageContext.request.contextPath}/checkCodeServlet?time="+new Date().getTime();
     });
</script>
</body>
</html>