<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container" style="text-align: center">
    <form enctype="multipart/form-data" method="post">
        <input type="hidden" class="form-control" id="user_id" value="${param.userId}">
        <h2>用户密码修改</h2><br>
        <div class="form-group">
            <label for="oldPassword">原来密码</label>
            <input type="text" class="form-control" id="oldPassword" placeholder="请输入原来的密码" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="newPassword1">新密码</label>
            <input type="password" class="form-control" id="newPassword1" placeholder="请输入新的密码" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="newPassword2">新密码</label>
            <input type="password" class="form-control" id="newPassword2" placeholder="请再次输入新的密码" autocomplete="off">
        </div>


        <br><button type="button" id="submitPw_btn" class="btn btn-default">提交修改密码</button>
    </form>
</div>
<script type = "text/javascript" src="js/updatePassword.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>
