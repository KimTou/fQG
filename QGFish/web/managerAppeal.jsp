<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>申诉系统</title>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.min.js"></script>

</head>
<body onload="getAppeal(1)">

<h1 style="text-align: center">申诉反馈</h1><br>
<div style="text-align: right">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/managerUsing.jsp" role="button" >返回主界面</a></p><br>
</div>
<table border="1" class="table table-bordered table-hover" style="word-break: break-all;word-wrap: break-word;table-layout: fixed">

    <tr class="info">
        <th>申诉编号</th>
        <th>申诉者id</th>
        <th>申诉标题</th>
        <th>申诉内容</th>
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

    function getAppeal(currentPage) {

        let data = {
            currentPage:currentPage,
        }

        $.ajax({
            url: serverUrl + "/manager/getAppeal",
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
                                "<td>" + list[i].id + "</td>" +
                                "<td>" + list[i].userId + "</td>" +
                                "<td>" + list[i].appealTitle + "</td>" +
                                "<td>" + list[i].appealContent + "</td>" +
                                "<td><button class='btn btn-default ' onclick='read(id)' id='" + list[i].id + "'>标记已读</button>&nbsp;" +
                                "</tr>";

                    })

                    for(var i=1;i<=totalPage;i++) {
                        if(i==currentPage){
                            li += "<li>" +
                                "<li class='active'><a href='javascript:void(0)' onclick='getAppeal(" + i + ")'>" + i + "</a></li>" +
                                "<li>";
                        }
                        else {
                            li += "<li>" +
                                "<li><a href='javascript:void(0)' onclick='getAppeal(" + i + ")'>" + i + "</a></li>" +
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

    //传入申诉编号
    function read(id) {

        let data = {
            id:id
        }

        $.ajax({
            url: serverUrl + "/manager/read",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            async: true,
            success: function (data) {
                getAppeal(1);
            }
        })
    }

</script>
</body>
</html>
