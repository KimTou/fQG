<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单修改</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <%--    导入cookie插件--%>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>

</head>
<body onload="write(${param.shoppingId})">
<div class="container" style="text-align: center">
    <form enctype="multipart/form-data" method="post">
        <input type="hidden" class="form-control" id="user_id" value="">
        <h2>订单修改</h2>
        <div style="text-align: right">
            <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
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
            <input type="number" min="0" step="0.01" class="form-control" id="product_price" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="buy_amount">购买数量</label>
            <input type="number" value="1"  min="0" step="1" class="form-control" id="buy_amount" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="total_price">总金额</label>
            <input type="number" min="0" step="0.01" class="form-control" id="total_price" value="" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="address">送货地址</label>
            <input type="text"  class="form-control" id="address" value="" autocomplete="off">
        </div>

        <br><button type="button" id="btn" class="btn btn-default" onclick="updateShopping(${param.shoppingId})">保存修改信息</button>
    </form>
</div>


<script>
    //设置serverUrl
    let serverUrl = 'http://localhost:8080/QGfish/'

    function updateShopping(shoppingId) {
        let data = {
            shoppingId:shoppingId,
            productName: document.getElementById('product_name').value,
            productKind: document.getElementById('product_kind').value,
            productPrice: document.getElementById('product_price').value,
            buyAmount: document.getElementById('buy_amount').value,
            totalPrice: document.getElementById('total_price').value,
            address: document.getElementById('address').value
        }

        $.ajax({
            //请求的服务器地址
            url: serverUrl + "user/update",
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
                //data是成功回调后后端返回的数据
                if (data.status == 1) {
                    alert(data.message)
                    //继续定位user的id
                    $(window).attr("location", serverUrl + "/using.jsp");
                } else {
                    alert(data.message)
                }
            }
        })
    }

    //订单信息回显
    function write(shoppingId) {
        let data = {
            shoppingId:shoppingId
        }

        $.ajax({
            url: serverUrl + "/user/write",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                $("#user_id").val(data.data.userId);
                $("#user_name").val(data.data.userName);
                $("#user_email").val(data.data.email);
                $("#user_phone").val(data.data.phone);
                $("#user_address").val(data.data.address);
            }
        })
    }
</script>
</body>
</html>
