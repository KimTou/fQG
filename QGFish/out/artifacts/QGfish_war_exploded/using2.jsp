<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<!-- json动态数据表格 -->
<table class="layui-table"
       lay-data="{width: 1300, height:360, url:'selectAll', page:true, id:'test'}"
       lay-filter="demo">
    <thead>
    <tr>
        <th
                lay-data="{field:'id', width:70, sort: true,align:'center', fixed: true,page: true}">ID</th>
        <th lay-data="{field:'name', width:100,align:'center'}">客户名</th>
        <th
                lay-data="{field:'password', width:100, align:'center',sort: true,edit:'text'}">密码</th>
        <th lay-data="{field:'creatDate', width:160,align:'center'}">注册时间</th>
        <th lay-data="{field:'lastLoginTime', width:160,align:'center'}">上次登录时间</th>
        <th
                lay-data="{field:'address', width:100,align:'center',edit:'text'}">地址</th>
        <th lay-data="{field:'email',align:'center', width:100,edit:'text'}">邮箱</th>
        <th
                lay-data="{field:'telephone', width:120,align:'center', sort: true,edit:'text'}">手机号</th>
        <th
                lay-data="{fixed: 'right', width:178, align:'center', toolbar: '#barDemo'}">操作</th>
    </tr>
    </thead>
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="tableSelect">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="tableUpdate">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="tableDelete">删除</a>
</script>
<!-- json动态数据表格 -->

<script>
    layui.use('table', function() {
        var table = layui.table;
        //监听表格复选框选择
        table.on('checkbox(demo)', function(obj) {
            console.log(obj)
        });
        //监听工具条
        table.on('tool(demo)', function(obj) {
            var data = obj.data;
            if (obj.event === 'tableSelect') {
                //layer.msg('name：' + data.name + ' 的查看操作');
                layer.open({
                    type : 2,
                    title : '客户信息',
                    content : 'tableSelect?name=' + data.name,
                    shade : 0.5,
                    area : [ '600px', '400px' ],
                    maxmin : true
                });
            } else if (obj.event === 'tableDelete') {
                layer.confirm('真的删除行么', function(index) {
                    obj.del();
                    $.ajax({
                        type : "get",
                        url : "tableDelete?name=" + data.name,
                        dataType : "json"
                    });
                    layer.close(index);
                });

            } else if (obj.event === 'tableUpdate') {
                //layer.alert('编辑行：<br>'+ JSON.stringify(data))
                $.ajax({
                    type : "post",
                    data : data,
                    url : "tableUpdate",
                    dataType : "json"
                });

            }
        });
    });
</script>

</body>
</html>
