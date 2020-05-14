<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>找回密码-QG闲鱼</title>
    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <%--    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layui.css">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <%--    <script src="http://code.jquery.com/jquery-latest.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <%--    导入cookie插件--%>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>
    <%--    <script src="https://www.layuicdn.com/layui/layui.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layui.js"></script>

</head>
<body>
<br><br>
<h2 style="text-align: center">找回密码</h2><br><br><br>

<div style="text-align: right">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/login.jsp" role="button">返回</a></p><br>
</div>

<div style="text-align: center" >
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">邮箱地址</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" placeholder="请输入您的邮箱地址" autocomplete="off">
        </div>
    </div><br><br><br><br><br>


        <button type="button" class="btn btn-success" id="button"  width="100px" height="60px" onclick="findBackPassword()">找回密码</button>


    <br>
    <br>

</div>


<script>

    let serverUrl = 'http://localhost:8080/QGfish/'

    function findBackPassword() {

        let data = {
            email:$("#email").val()
        }

        $.ajax({
            url: serverUrl + "/user/findBackPassword",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if(data.status==true){
                    alert(data.message)
                    $(window).attr("location", serverUrl + "login.jsp");
                }
                else{
                    alert(data.message)
                }
            }
        })
    }
</script>

</body>
</html>