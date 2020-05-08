<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息修改</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body onload="write()">
<div class="container" style="text-align: center">
    <form enctype="multipart/form-data" method="post">
        <input type="hidden" class="form-control" id="user_id" value="">
        <h2>用户信息修改</h2>
        <div style="text-align: right">
            <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
        </div>
        <div class="form-group">
            <label for="user_name">用户名</label>
            <input type="text" class="form-control" id="user_name" value="" >
        </div>
        <div class="form-group">
            <label for="user_email">邮箱</label>
            <input type="text" class="form-control" id="user_email" value="">
        </div>
        <div class="form-group">
            <label for="user_phone">手机号码</label>
            <input type="text" class="form-control" id="user_phone" value="">
        </div>
        <div class="form-group">
            <label for="user_address">住址</label>
            <input type="text"  class="form-control" id="user_address" value="">
        </div>

        <br><button type="submit" id="submit_btn" class="btn btn-default">保存修改信息</button>
    </form>
</div>


<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
<%--    导入cookie插件--%>
<script type="text/javascript" src="js/jquery.cookie.min.js"></script>

<script>
    //设置serverUrl
    let serverUrl = 'http://localhost:8080/QGfish/'

    //获取提交按钮
    let form_btn = document.getElementById('submit_btn')

    //给按钮添加点击事件
    form_btn.onclick = function() {
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
                    //继续定位user的id
                    $(window).attr("location", serverUrl + "/using.jsp");
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
                $("#user_id").val(data.data.userId);
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
