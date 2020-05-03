<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传商品图片</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1>为你的商品添加图片吧</h1><br>
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

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>
