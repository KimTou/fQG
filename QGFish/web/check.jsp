<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>商品列表</title>
    <%--要在联网状态下使用--%>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>

</head>
<body onload="refresh(1)">

<div class="container">
    <h2 style="text-align: center">商品审核列表</h2><br>

    <div style="text-align: right">
        <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/managerUsing.jsp" role="button">返回主界面</a></p><br>
    </div>

    <table border="1" class="table table-bordered table-hover">
        <tr class="info">
            <th>商品编号</th>
            <th>商品名</th>
            <th>种类</th>
            <th>价格</th>
            <th>数量</th>
            <th>卖家编号</th>
            <th>图片</th>
            <th>审核操作</th>
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
</div>

<script>
    let serverUrl = 'http://localhost:8080/QGfish/'


    function refresh(currentPage) {

        let data = {
            currentPage:currentPage

        }
        $.ajax({
            url: serverUrl + "/manager/check",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if (data.status == 1) {
                    var str = "";
                    var list = data.data;
                    $.each(list,function (i,rs) {
                        str += "<tr>" +
                            "<td>" + list[i].productId + "</td>" +
                            "<td>" + list[i].productName + "</td>" +
                            "<td>" + list[i].productKind + "</td>" +
                            "<td>" + list[i].productPrice + "</td>" +
                            "<td>" + list[i].productAmount + "</td>" +
                            "<td>" + list[i].productSeller + "</td>" +
                            "<td><a href="+list[i].productPicture+" target='_blank'><img width='80px' height='80px' src="+list[i].productPicture+"></a></td>" +
                            "<td><button class='btn btn-default ' onclick='release(id)' id='"+list[i].productId+"'>允许发布</button>&nbsp;<button class='btn btn-default' onclick='ban(id)' id="+list[i].productId+ ">禁止发布</button></td>"+
                            "</tr>";
                    })

                    for(var i=1;i<=totalPage;i++) {
                        if(i==currentPage){
                            li += "<li>" +
                                "<li class='active'><a href='javascript:void(0)' onclick='refresh(" + i + ")'>" + i + "</a></li>" +
                                "<li>";
                        }
                        else {
                            li += "<li>" +
                                "<li><a href='javascript:void(0)' onclick='refresh(" + i + ")'>" + i + "</a></li>" +
                                "<li>";
                        }
                    }

                    console.log(str);
                    $("#t_body").html(str);
                    $("#lis").html(li);
                    $("#totalPage").html("一共"+totalCount+"条记录，"+"共"+totalPage+"页");
                } else {
                    alert(data.message);
                }
            }
        })
    }

    // 在方法中传入按钮的id，以便获取该商品的id
    function release(productId) {
        let data = {
            productId:productId
        }

        $.ajax({
            url: serverUrl + "manager/release",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {

            }
        })
            refresh(1);
    }

    // 在方法中传入按钮的id，以便获取该商品的id
    function ban(productId) {
        let data = {
            productId:productId
        }

        $.ajax({
            url: serverUrl + "/manager/ban",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {

            }
        })
        refresh(1);
    }


</script>

</body>
</html>