<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<script>
    $(function () {
        $("#button1").click(function () {
            //$("#div1").slideToggle("slow");
            $("#jump1").load("/ajax/content.txt");
        });
    });
</script>
<div id="div1">冲冲冲！</div>
<div id="div2">kkk！</div>
<p>这是另一个段落。</p>
<input type="button" value="点我" id="button1">

<script>
    // //获取button对象并绑定单击事件
    // $("#button1").click(function () {
    //     alert("nice");
    // });
</script>
<div style="color:#00FF00">
    <h3>This is a header</h3>
    <p>This is a paragraph.</p>
</div>
<input type="text" value="hhh你真无语">
<div class="news">
    <h2>News headline 1</h2>
    <p id="jump1">some text. some text. some text...</p>
    ...hahaha
</div>

</body>
</html>
