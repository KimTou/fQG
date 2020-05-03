<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息修改</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container" style="text-align: center">
<form enctype="multipart/form-data" method="post">
    <input type="hidden" class="form-control" id="user_id" value="${user.userId}">
    <h2>用户信息修改</h2>
    <div class="form-group">
        <label for="user_name">用户名</label>
        <input type="text" class="form-control" id="user_name" value="${user.userName}" >
    </div>
    <div class="form-group">
        <label for="user_email">邮箱</label>
        <input type="text" class="form-control" id="user_email" value="${user.email}">
    </div>
    <div class="form-group">
        <label for="user_phone">手机号码</label>
        <input type="text" class="form-control" id="user_phone" value="${user.phone}">
    </div>
    <div class="form-group">
        <label for="user_realName">真实姓名</label>
        <input type="text"  class="form-control" id="user_realName" value="${user.realName}">
    </div>

    <br><button type="submit" id="submit_btn" class="btn btn-default">保存修改信息</button>
</form>
</div>

<script type = "text/javascript" src="js/update.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>

</body>
</html>
