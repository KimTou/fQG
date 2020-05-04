<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>用户列表</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 1. 导入CSS的全局样式 -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>

<input type="hidden" id="page" value="1">
    <h2 style="text-align: center">商品审核列表</h2><br>
    <p><a class="btn btn-default btn-lg" role="button" id="refresh">刷新待审核商品</a></p><br>
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
        <ul class="pagination">
            <li>
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
                <a href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>


    <script>
        let serverUrl = 'http://localhost:8080/QGfish/'

        //获取两个提交按钮
        let btn = document.getElementById('refresh')

        btn.onclick = function refresh() {
            //获取登陆各个输入框的值，存放在data对象中
            let data = {
                page: document.getElementById('page').value

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
                                "<td><img width='150px' height='120px' src="+list[i].productPicture+"></td>" +
                                "<td><button class='btn btn-default ' onclick='release(id)' id='"+list[i].productId+"'>允许发布</button>&nbsp;<button class='btn btn-default' onclick='ban(id)' id="+list[i].productId+ ">禁止发布</button></td>"+
                                "</tr>";
                        })
                        // for(var k in data){
                        //     $.each(data.data,function(index,item){
                        //         alert(item.productId);
                        //
                        //     });
                        // }
                        console.log(str);
                        $("#t_body").html(str);
                    } else {
                        alert(data.message);
                    }
                    return
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
                    btn.click();
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
                    btn.click();
                }
            })
        }


    </script>

<%--要在联网状态下使用--%>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://www.layuicdn.com/layui/layui.js"></script>
</body>
</html>