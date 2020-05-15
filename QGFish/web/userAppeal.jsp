<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>申诉系统</title>
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

<div class="container" style="text-align: center">

    <br><br><h2>填写你的申诉信息</h2>
    <div style="text-align: right">
        <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
    </div>

        <div class="form-group">
            <label for="appealTitle" class="col-sm-2 control-label">申诉标题</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="appealTitle" placeholder="请填写申诉的标题" autocomplete="off">
            </div>
        </div><br><br><br>

<%--    <div class="form-group">--%>
<%--        <label for="appealContent" class="col-sm-2 control-label">申诉内容</label>--%>
<%--        <div class="col-sm-10">--%>
<%--            <input type="text" class="form-control" id="appealContent" placeholder="请填写申诉的主要内容" style="width: 80%;height: 20%">--%>
<%--        </div>--%>
<%--    </div>--%>
    <label for="appealContent" class="col-sm-2 control-label">申诉内容</label>
    <textarea id="appealContent" placeholder="最好带有投诉商品的id或者投诉卖家的id"></textarea>
    <style type="text/css">
        #appealContent{width:800px; height:200px;}
    </style>


    <br><br><br><br><button type="button" id="btn" class="btn btn-default btn-lg" onclick="appeal()">提交申诉信息</button>

</div>

<script>

    let serverUrl = 'http://localhost:8080/QGfish/'

    function appeal() {

        var appealTitle=$("#appealTitle").val()
        var appealContent=$("#appealContent").val()

        if(appealTitle==null||appealTitle==""||appealContent==null||appealContent==""){
            alert("请填写完整信息")
            return false
        }

        let data = {
            userId:$.cookie('userId'),
            appealTitle:$("#appealTitle").val(),
            appealContent:$("#appealContent").val()
        }

        $.ajax({
            url: serverUrl + "/user/appeal",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if(data.status==true){
                    alert(data.message+"\n管理员会尽快处理的！");
                    $(window).attr("location", serverUrl + "using.jsp");
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
