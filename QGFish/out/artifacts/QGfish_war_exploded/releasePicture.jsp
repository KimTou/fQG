<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传商品图片</title>
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
<h1>为你的商品上传图片吧</h1><br>
<form action="${pageContext.request.contextPath}/releaseServlet" enctype="multipart/form-data" method="post">
<div class="form-group">
    <input type="hidden" class="form-control" name="productId" value="${param.productId}">
    <input type="hidden" class="form-control" name="userId" value="${param.userId}">
    <label for="product_picture">上传商品图片</label>
    <input type="file" multiple="multiple" id="product_picture" name="picture" >

    <br><p class="help-block">请选择一张图片或者不选</p><br>
</div>
<button type="submit"  id="submit_btn" class="btn btn-default btn-lg">提交图片信息</button>
</form>

<div class="jumbotron" style="text-align: right">
    <h1>QG闲鱼<small>一个优质的二手交易平台</small></h1>
</div>


</body>
</html>
