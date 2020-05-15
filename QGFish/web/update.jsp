<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息修改</title>
    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <%--    <script src="http://code.jquery.com/jquery-latest.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <%--    导入cookie插件--%>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>


</head>
<body onload="write()">
<div class="container" style="text-align: center">

        <h3>用户信息修改</h3>
        <div style="text-align: right">
            <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p>
        </div>
        <div style="text-align: left">
            <label id="user_exp"></label>
        </div>

    <label for="progress" style="float: left">经验值&nbsp;</label>
    <div class="progress" id="progress">

    </div>

        <div class="form-group">
            <label for="user_name">用户名</label>
            <input type="text" class="form-control" id="user_name" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="user_email">邮箱</label>
            <input type="text" class="form-control" id="user_email" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="user_phone">手机号码</label>
            <input type="text" class="form-control" id="user_phone" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="user_address">住址</label>
            <input type="text"  class="form-control" id="user_address" value="" autocomplete="off">
        </div>

        <br><button type="button" id="btn" onclick="update()" class="btn btn-default">保存修改信息</button>
</div>




<script>
    //设置serverUrl
    let serverUrl = 'http://localhost:8080/QGfish/'

    function update(){
        let data = {
            userId:$.cookie('userId'),
            userName: document.getElementById('user_name').value,
            email: document.getElementById('user_email').value,
            phone: document.getElementById('user_phone').value,
            address: document.getElementById('user_address').value
        }

        $.ajax({
            //请求的服务器地址
            url: serverUrl + "user/update",
            method: "POST",
            //请求头部
            headers: {
                //告诉服务端解析的是json数据
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            //选择同步请求，还是异步请求
            async: true,
            //请求成功响应后的回调函数
            success: function (data) {           //该data与上面的data不是同一个
                                                 //data是成功回调后后端返回的数据
                if (data.status == 1) {
                    alert(data.message)
                } else {
                    alert(data.message)
                }
            }
        })
    }

    //用户信息回显
    function write() {
        let data = {
            userId:$.cookie('userId'),
        }

        $.ajax({
            url: serverUrl + "/user/write",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                var div="";
                div += "<div class='progress-bar progress-bar-striped active' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+(data.data.exp%100)+"%;'>"+
                    (data.data.exp%100)+"%"+
                    "</div>";
                $("#user_exp").html("当前会员等级："+(data.data.exp-data.data.exp%100)/100);
                $("#progress").html(div);
                $("#user_name").val(data.data.userName);
                $("#user_email").val(data.data.email);
                $("#user_phone").val(data.data.phone);
                $("#user_address").val(data.data.address);
            }
        })
    }
</script>
</body>
</html>
