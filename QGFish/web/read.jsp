<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品详情</title>
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
<body onload="read(${param.productId})">

<h1 style="text-align: center">商品详细信息</h1><br>
<div style="text-align: right">
<p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
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
                            "<td><button class='btn btn-default ' onclick='addShopping(id)' id='"+product.productId+"'>加入购物车</button>&nbsp;<button class='btn btn-default' onclick='buy(id)' id="+product.productId+ ">购买</button></td>"+
                            "</tr>";

                    $("#t_body").html(table);
                } else {
                    alert(data.message);
                }
            }
        })
    }

    function addShopping(productId) {

        let data = {
            buyer:$.cookie('userId'),
            productId:productId,
        }

        $.ajax({
            url: serverUrl + "/user/addShopping",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                alert(data.message)
            }
        })
    }

    function buy(productId) {
        var buyAmount;
        //第一个参数是提示文字，第二个参数是文本框中默认的内容
        buyAmount =prompt("请输入购买的数量","");
        if(isNaN(buyAmount))
        {
            alert("输入的不是正整数");
            return false;
        }
        if (!(/(^[0-9]*[1-9][0-9]*$)/.test(buyAmount))){
            alert("输入的不是正整数");
            return false;
        }

        let data = {
            buyer:$.cookie('userId'),
            productId:productId,
            buyAmount:buyAmount
        }

        $.ajax({
            url: serverUrl + "/user/buy",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                alert(data.message)
            }
        })
    }

</script>
</body>
</html>
