<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>评价页面</title>
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

        <h2>商品评价</h2>
        <div style="text-align: right">
            <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
        </div>
        <label for="select">为你购买的商品打个分吧</label>
        <select id="select" class="form-control">
            <option value="0" selected>无</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
         </select><br><br><br><br>

        <div class="form-group">
            <label for="comment">来写下你的评价吧</label>
            <input type="text" class="form-control" id="comment" value="" >
        </div>


        <br><button type="button" id="btn" class="btn btn-default btn-lg" onclick="comfirmOrder(${param.shoppingId})">提交</button>

</div>

<script>

    let serverUrl = 'http://localhost:8080/QGfish/'

    function comfirmOrder(shoppingId) {

        let data = {
            shoppingId:shoppingId,
            score:$("#select").val(),
            comment:"（"+$("#comment").val()+"）"
        }

        $.ajax({
            url: serverUrl + "/user/comfirmOrder",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                //alert(data.message)
            }
        })
        $(window).attr("location", serverUrl + "userOrder.jsp");
    }
</script>

</body>
</html>
