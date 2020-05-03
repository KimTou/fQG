<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>主页导航</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body class="layui-bg-gray">
<!-- 反色导航条组件  -->
<nav class="navbar navbar-inverse" style="margin-top: 0px;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">QG闲鱼</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/release.jsp?userId=${param.userId}">发布二手商品</a>
                </li>
                <li>
                    <a href="#">购物车</a>
                </li>
                <li>
                    <a href="#">我的订单</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/findServlet?userId=${param.userId}">个人信息</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/updatePassword.jsp?userId=${param.userId}">修改密码</a>
                </li>
            </ul>
            <form class="navbar-form navbar-right" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="搜索" >
                </div>
                <button type="submit" class="btn btn-default">查询</button>
            </form>
            <div style="text-align: right">
                <button id="user_btn" value="${param.userId}"></button>
            </div>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<div class="container">
    <h3 style="text-align: center">商品信息列表</h3>
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>编号</th>
            <th>商品名</th>
            <th>种类</th>
            <th>价格</th>
            <th>出货量</th>
            <th>星级</th>
        </tr>
        <c:forEach items="${user}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.sex}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-default btn-sm" href="update.jsp">查看详情</a>&nbsp;<a class="btn btn-default btn-sm" href="">购买</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <li>
            <a href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li>
            <a href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
</div>


<%--要在联网状态下使用--%>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://www.layuicdn.com/layui/layui.js"></script>
</body>
</html>