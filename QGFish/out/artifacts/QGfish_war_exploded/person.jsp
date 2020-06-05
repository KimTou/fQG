<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>用户信息管理系统</title>
    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <%--    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layui.css">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <%--    <script src="http://code.jquery.com/jquery-latest.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <%--    导入cookie插件--%>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>
    <%--    <script src="https://www.layuicdn.com/layui/layui.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layui.js"></script>

</head>
<body onload="refresh(1)">

<br><br><br><h2 style="text-align: center">用户管理列表</h2><br>

<div style="text-align: right">
<p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/managerUsing.jsp" role="button">返回主界面</a></p><br>
</div>

<%--    <p><a class="btn btn-default btn-lg" role="button" id="refresh" type="hidden" >刷新待审核商品</a></p><br>--%>

<%--<form class="navbar-form navbar-left" role="search">--%>

<%--    <div class="form-group">--%>
<%--        <label for="user_name">用户名</label>--%>
<%--        <input type="text" id="user_name" class="form-control" placeholder="搜索" >--%>
<%--    </div>--%>
<%--    <button type="button" class="btn btn-default" onclick="">查询</button>--%>
<%--</form>--%>

<table border="1" class="table table-bordered table-hover">
    <tr class="info">
        <th>用户编号</th>
        <th>用户名</th>
        <th>邮箱</th>
        <th>电话</th>
        <th>地址</th>
        <th>状态</th>
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


<script>
    //一进页面就自动执行

    let serverUrl = 'http://localhost:8080/QGfish/'

    function refresh(currentPage) {
        //refresh(currentPage)传进来的currentPage为当前页码
        if(currentPage==null){
            currentPage=1;
        }

        let data = {
            currentPage: currentPage
        }

        $.ajax({
            url: serverUrl + "manager/findUserByPage",
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
                        if(list[i].condition=="正常") {
                            table += "<tr>" +
                                "<td>" + list[i].userId + "</td>" +
                                "<td>" + list[i].userName + "</td>" +
                                "<td>" + list[i].email + "</td>" +
                                "<td>" + list[i].phone + "</td>" +
                                "<td>" + list[i].address + "</td>" +
                                "<td>" + list[i].condition + "</td>" +
                                "<td><button class='btn btn-default ' onclick='banUser(id)' id='" + list[i].userId + "'>禁用用户售卖</button></td>" +
                                "</tr>";
                        }
                        else{
                            table += "<tr>" +
                                "<td>" + list[i].userId + "</td>" +
                                "<td>" + list[i].userName + "</td>" +
                                "<td>" + list[i].email + "</td>" +
                                "<td>" + list[i].phone + "</td>" +
                                "<td>" + list[i].address + "</td>" +
                                "<td>" + list[i].condition + "</td>" +
                                "<td><button class='btn btn-default' onclick='recover(id)' id=" + list[i].userId + ">恢复用户售卖</button></td>" +
                                "</tr>";
                        }
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

    function recover(userId) {
        let data = {
            userId:userId
        }

        $.ajax({
            url: serverUrl + "manager/recover",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if (data.status == 1) {
                    alert(data.message);
                } else {
                    alert(data.message);
                }
                refresh(1);
            }
        })

    }

    function banUser(userId) {

        var label;
        //第一个参数是提示文字，第二个参数是文本框中默认的内容
        label =prompt("请输入禁用该用户的理由","");

        let data = {
            userId:userId,
            label:label
        }

        $.ajax({
            url: serverUrl + "manager/banUser",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                if (data.status == 1) {
                    alert(data.message);
                } else {
                    alert(data.message);
                }
            }
        })
        refresh(1);
    }

</script>


</body>
</html>
