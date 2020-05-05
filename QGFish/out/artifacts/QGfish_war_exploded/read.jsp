<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品详情</title>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body onload="read(${param.productId})">
<input type="hidden" value="${param.userId}">
<h1 style="text-align: center">商品详细信息</h1><br>
<div style="text-align: right">
<p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp?userId=${param.userId}" role="button" >返回</a></p><br>
</div>
<table border="1" class="table table-bordered table-hover" style="word-break: break-all;word-wrap: break-word;table-layout: fixed">

    <tr class="info">
        <th>商品编号</th>
        <th>商品名</th>
        <th>种类</th>
        <th>价格</th>
        <th>数量</th>
        <th>出货量</th>
        <th>星级</th>
        <th>图片</th>
        <th>卖家id</th>
        <th>评论</th>
        <th>操作</th>
    </tr>
    <tbody id="t_body">

    </tbody>

</table>

<script>
    //一进页面就自动执行

    let serverUrl = 'http://localhost:8080/QGfish/'

    function read(productId) {

        let data = {
            productId:productId,
        }

        $.ajax({
            url: serverUrl + "/user/read",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if (data.status == 1) {
                    var table = "";
                    product=data.data;
                        table += "<tr>" +
                            "<td>" + product.productId + "</td>" +
                            "<td>" + product.productName + "</td>" +
                            "<td>" + product.productKind + "</td>" +
                            "<td>" + product.productPrice + "</td>" +
                            "<td>" + product.productAmount + "</td>" +
                            "<td>" + product.productSold + "</td>" +
                            "<td>" + product.productStarLevel + "</td>" +
                            "<td><a href="+product.productPicture+" target='_blank'><img width='110px' height='110px' src="+product.productPicture+"></a></td>" +
                            "<td>" + product.productSeller + "</td>" +
                            "<td>" + product.productComment + "</td>" +
                            "<td><button class='btn btn-default ' onclick='read(id)' id='"+product.productId+"'>加入购物车</button>&nbsp;<button class='btn btn-default' onclick='read(id)' id="+product.productId+ ">提交订单</button></td>"+
                            "</tr>";

                    $("#t_body").html(table);
                } else {
                    alert(data.message);
                }
            }
        })
    }
</script>
</body>
</html>
