<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>QG闲鱼</title>
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
<body class="layui-bg-gray" onload="refresh(1)">

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
                    <a href="${pageContext.request.contextPath}/login.jsp" >注册账号</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <h2 style="text-align: center">商品列表</h2><br>

    <div style="text-align: right">
        <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/login.jsp" role="button" >还没有账号？来注册一个吧</a></p><br>
    </div>

    <label>
        <input type="radio" name="by" value="star_level" style="text-align: right" checked>按星级排序
    </label>
    <label>
        <input type="radio" name="by" value="sold" style="text-align: right">按销量排序
    </label>
    <label>
        <input type="radio" name="by" value="product_price" style="text-align: right">按价格排序
    </label>
    <label>
        <input type="radio" name="by" value="product_amount" style="text-align: right">按商品数量排序
    </label>


    <form class="navbar-form navbar-left" role="search">

        <div class="form-group">
            <label for="kind">种类</label>
            <input type="text" id="kind" class="form-control" placeholder="请输入商品种类" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="product_name">商品名</label>
            <input type="text" id="product_name" class="form-control" placeholder="请输入商品名" autocomplete="off">
        </div>
        <%--        查询按钮不能为submit，应该为button！！！--%>
        <button type="button" class="btn btn-default" onclick="refresh(1)">查询</button>
    </form>

    <table border="1" class="table table-bordered table-hover">


        <tr class="info">
            <th>商品编号</th>
            <th>商品名</th>
            <th>种类</th>
            <th>价格</th>
            <th>现有数量</th>
            <th>出货量</th>
            <th>星级</th>
            <th>图片</th>
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

    <div class="jumbotron" style="text-align: right">
        <h1>QG闲鱼<small>一个优质的二手交易平台</small></h1>
    </div>

</div>

<script>
    //一进页面就自动执行

    let serverUrl = 'http://localhost:8080/QGfish/'

    function refresh(num) {
        //refresh(num)传进来的num为当前页码
        if(num==null){
            num=1;
        }

        let data = {
            radio:$("input[name='by']:checked").val(),
            likeKind:$("#kind").val(),
            likeProductName:$("#product_name").val(),
            currentPage: num
        }

        $.ajax({
            url: serverUrl + "/user/findProductByPage",
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
                            "<td>" + list[i].productAmount + "</td>" +
                            "<td>" + list[i].productSold + "</td>" +
                            "<td>" + list[i].productStarLevel + "</td>" +
                            "<td><a href="+list[i].productPicture+" target='_blank'><img width='90px' height='90px' src="+list[i].productPicture+"></a></td>" +
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
                    $("#t_body").html(table);
                    $("#lis").html(li);
                    $("#totalPage").html("一共"+totalCount+"条记录，"+"共"+totalPage+"页");
                } else {
                    alert(data.message);
                }
            }
        })
    }




</script>

</body>
</html>