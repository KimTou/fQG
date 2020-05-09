<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布二手商品</title>
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
<body>
<h2>发布二手商品</h2>

<div  style="text-align: right">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >搜索二手商品</a></p><br>
</div>

<form  enctype="multipart/form-data" method="post">
<%--    <input type="hidden" class="form-control" id="user_id" value="${param.userId}">--%>
    <div class="form-group">
        <label for="product_name">商品名</label>
        <input type="text" style="width:40%" class="form-control" name="product_name" id="product_name" placeholder="请输入商品名" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_kind">商品种类</label>
        <input type="text" style="width:40%" class="form-control" name="product_kind" id="product_kind" placeholder="请输入商品种类" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_price">商品价格</label>
        <input type="number" min="0" step="0.01" style="width:40%" class="form-control" name="product_price" id="product_price" placeholder="请输入商品价格" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_amount">商品数量</label>
        <input type="number" value="1"  min="0" step="1" style="width:40%" class="form-control" name="product_amount" id="product_amount" placeholder="请输入商品数量" autocomplete="off">
    </div>

    <button type="button" id="submit_btn" class="btn btn-default">提交商品信息</button>
</form>

<div class="jumbotron" style="text-align: right">
    <h1>QG闲鱼<small>一个优质的二手交易平台</small></h1>
</div>


<script type = "text/javascript" src="js/release.js"></script>

</body>
</html>
