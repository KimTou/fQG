<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的订单</title>
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
<body onload="userOrder(1)">

<h1 style="text-align: center">我的订单</h1><br>
<div style="text-align: right">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
</div>
<table border="1" class="table table-bordered table-hover" style="word-break: break-all;word-wrap: break-word;table-layout: fixed">

    <tr class="info">
        <th>商品编号</th>
        <th>商品名</th>
        <th>种类</th>
        <th>价格</th>
        <th>购买数量</th>
        <th>总金额</th>
        <th>收货地址</th>
        <th>订单状态</th>
        <th>图片</th>
        <th>订单文件</th>
        <th>操作</th>
    </tr>
    <tbody id="t_body">

    </tbody>

</table>

<nav aria-label="Page navigation" style="text-align: center">
    <ul class="pagination" id="lis">

    </ul>

    <span style="font-size: 25px;margin-left: 5px;" id="totalPage">

        </span><br><br><br><br>

</nav>

<script>
    //一进页面就自动执行

    let serverUrl = 'http://localhost:8080/QGfish/'

    function userOrder(currentPage) {

        let data = {
            userId:$.cookie('userId'),
            currentPage:currentPage,
        }

        $.ajax({
            url: serverUrl + "user/userOrder",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if (data.status == 1) {
                    var table = "";
                    var li="";
                    var list = data.data.list;
                    var totalPage=data.data.totalPage;
                    var totalCount=data.data.totalCount;
                    var currentPage=data.data.currentPage;
                    $.each(list,function (i,rs) {
                            table += "<tr>" +
                                "<td>" + list[i].productId + "</td>" +
                                "<td>" + list[i].productName + "</td>" +
                                "<td>" + list[i].productKind + "</td>" +
                                "<td>" + list[i].productPrice + "</td>" +
                                "<td>" + list[i].buyAmount + "</td>" +
                                "<td>" + list[i].totalPrice + "</td>" +
                                "<td>" + list[i].address + "</td>" +
                                "<td>" + list[i].productCondition + "</td>" +
                                "<td><a href=" + list[i].productPicture + " target='_blank'><img width='90px' height='90px' src=" + list[i].productPicture + "></a></td>" +
                                "<td><a href='"+serverUrl+"/downLoadServlet?shoppingId="+list[i].shoppingId+"' >下载订单</a></td>" +
                                "<td><button class='btn btn-default ' onclick='comfirmOrder(id)' id='" + list[i].shoppingId + "'>确认收货</button>&nbsp;<button class='btn btn-default' onclick='rejectShopping(id)' id=" + list[i].shoppingId + ">取消订单</button></td>" +
                                "</tr>";

                    })

                    for(var i=1;i<=totalPage;i++) {
                        if(i==currentPage){
                            li += "<li>" +
                                "<li class='active'><a href='javascript:void(0)' onclick='userOrder(" + i + ")'>" + i + "</a></li>" +
                                "<li>";
                        }
                        else {
                            li += "<li>" +
                                "<li><a href='javascript:void(0)' onclick='userOrder(" + i + ")'>" + i + "</a></li>" +
                                "<li>";
                        }
                    }
                    // console.log(table);
                    // console.log(li);
                    $("#t_body").html(table);
                    $("#lis").html(li);
                    $("#totalPage").html("一共"+totalCount+"条记录，"+"共"+totalPage+"页");
                } else {
                    alert(data.message);
                }
            }
        })
    }

    function comfirmOrder(shoppingId) {
        $(window).attr("location", serverUrl + "evaluate.jsp?"+"shoppingId="+shoppingId);
    }

    function rejectShopping(shoppingId) {
        let data={
            shoppingId:shoppingId
        }

        $.ajax({
            url: serverUrl + "user/deleteInShopping",
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
        userOrder(1);
    }


</script>
</body>
</html>
