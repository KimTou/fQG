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
<body onload="write(${param.productId})">
<div class="container" style="text-align: center">

    <h2>商品信息修改</h2>
    <div style="text-align: right">
        <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/myProduct.jsp" role="button" >返回</a></p>
    </div>

    <div class="form-group">
        <label for="product_name">商品名</label>
        <input type="text" class="form-control" id="product_name" value="" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_kind">商品种类</label>
        <input type="text" class="form-control" id="product_kind" value="" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_price">商品价格</label>
        <input type="number" min="0" step="0.01" style="width:40%" class="form-control" name="product_price" id="product_price" autocomplete="off">
    </div>
    <div class="form-group">
        <label for="product_amount">商品数量</label>
        <input type="number" min="0" step="1" style="width:40%" class="form-control" name="product_amount" id="product_amount" autocomplete="off">
    </div>

    <br><button type="button" id="btn" onclick="update(${param.productId})" class="btn btn-default">保存修改信息</button>

    <br><br><button type="button" id="btn2" onclick="updatePic(${param.productId})" class="btn btn-default">修改商品图片</button>

</div>


<script>
    //设置serverUrl
    let serverUrl = 'http://localhost:8080/QGfish/'

    function update(productId){
        let data = {
            productId:productId,
            productName: document.getElementById('product_name').value,
            productKind: document.getElementById('product_kind').value,
            productPrice: document.getElementById('product_price').value,
            productAmount: document.getElementById('product_amount').value
        }

        $.ajax({
            //请求的服务器地址
            url: serverUrl + "user/updateProduct",
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
                alert(data.message)
            }
        })
    }

    function write(productId) {
        let data = {
            productId:productId,
        }

        $.ajax({
            url: serverUrl + "/user/writeProduct",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                $("#product_name").val(data.data.productName);
                $("#product_kind").val(data.data.productKind);
                $("#product_price").val(data.data.productPrice);
                $("#product_amount").val(data.data.productAmount);
            }
        })
    }

    function updatePic(productId) {
        $(window).attr("location", serverUrl + "releasePicture.jsp?userId="+$.cookie('userId')+"&productId="+productId);
    }
</script>
</body>
</html>
