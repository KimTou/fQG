<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <%--要在联网状态下使用--%>
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body onload="refresh(1)">

<h2 style="text-align: center">用户管理列表</h2><br>
<%--    <p><a class="btn btn-default btn-lg" role="button" id="refresh" type="hidden" >刷新待审核商品</a></p><br>--%>

<%--<select name="city" lay-verify="">--%>
<%--    <option value="">请选择一个城市</option>--%>
<%--    <option value="010">北京</option>--%>
<%--    <option value="021">上海</option>--%>
<%--    <option value="0571">杭州</option>--%>
<%--</select>--%>
<form class="navbar-form navbar-left" role="search">

    <label for="kind">种类</label>
    <select id="kind" class="button" onchange="getContent(this.value,this.options[this.selectedIndex].text)">
        <option value=1>a</option>
        <option value=2>b</option>
        <option value=3>c</option>
        <option value=4>d</option>
    </select>

    <div class="form-group">
        <label for="user_name">用户名</label>
        <input type="text" id="user_name" class="form-control" placeholder="搜索" >
    </div>
    <button type="submit" class="btn btn-default" onclick="">查询</button>
</form>

<table border="1" class="table table-bordered table-hover">
    <tr class="info">
        <th>用户编号</th>
        <th>用户名</th>
        <th>邮箱</th>
        <th>电话</th>
        <th>真实姓名</th>
        <th>状态</th>
        <th>审核操作</th>
    </tr>
    <tbody id="t_body">

    </tbody>
</table>

<nav aria-label="Page navigation" style="text-align: center">
    <ul class="pagination" id="lis">

    </ul>
</nav>

<script>
    //一进页面就自动执行

    let serverUrl = 'http://localhost:8080/QGfish/'

    function refresh(num) {
        //refresh(num)传进来的num为当前页码
        if(num==null){
            num=1;
        }

        let data = {
            currentPage: num
        }

        $.ajax({
            url: serverUrl + "/manager/findUserByPage",
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
                    $.each(list,function (i,rs) {
                        table += "<tr>" +
                            "<td>" + list[i].userId + "</td>" +
                            "<td>" + list[i].userName + "</td>" +
                            "<td>" + list[i].email + "</td>" +
                            "<td>" + list[i].phone + "</td>" +
                            "<td>" + list[i].realName + "</td>" +
                            "<td>" + list[i].condition + "</td>" +
                            "<td><button class='btn btn-default ' onclick='release(id)' id='"+list[i].userId+"'>禁用</button>&nbsp;<button class='btn btn-default' onclick='ban(id)' id="+list[i].userId+ ">恢复正常</button></td>"+
                            "</tr>";
                    })

                    for(var i=1;i<=totalPage;i++) {
                        li += "<li>" +
                            "<li><a href='javascript:void(0)' onclick='refresh(" + i + ")'>" + i + "</a></li>" +
                            "<li>";
                    }
                    $("#t_body").html(table);
                    $("#lis").html(li);
                } else {
                    alert(data.message);
                }
            }
        })
    }


    // 在方法中传入按钮的id，以便获取该商品的id
    function release(id) {
        let data = {
            productId:id
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
                // if (data.status == 1) {
                //     alert(data.message);
                // } else {
                //     alert(data.message);
                // }
                refresh();
            }
        })

    }

    // 在方法中传入按钮的id，以便获取该商品的id
    function ban(id) {
        let data = {
            productId:id
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
                refresh();
            }
        })
    }

</script>


</body>
</html>
