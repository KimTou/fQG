<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>QG闲鱼聊天室</title>
    　　　　　　<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
    　　　　　　<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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
<body>

<h2 style="text-align: center">
    QG闲鱼聊天室
</h2>
<div style="text-align: right">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/using.jsp" role="button" >返回主界面</a></p><br>
</div>
<div style="text-align: right">
<button class="btn btn-default btn-sm" onclick="closeWebSocket()" n>关闭聊天室连接</button>
</div>

<label for="text" class="col-sm-2 control-label">发送消息</label>
<textarea id="text" placeholder="快来发言吧"></textarea>
<style type="text/css">
    #text{width:400px; height:100px;}
</style><hr/>

<button class="btn btn-default btn-lg" onclick="send()">群发消息</button>

<button class="btn btn-default btn-lg" onclick="sendOne()">发送私信</button>

<hr/>


<div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        //携带用户id
        websocket = new WebSocket("ws://localhost:8080/QGfish/websocket/"+$.cookie('userId'));
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("聊天室连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("进入聊天室");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("聊天室连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        var userId=$.cookie('userId');

        let data={
            from:userId,
            message:message,
            type:1
        }
        websocket.send(JSON.stringify(data));
    }

    //发送私信
    function sendOne() {
        var to;
        //第一个参数是提示文字，第二个参数是文本框中默认的内容
        to =prompt("请输入私信人的id","");
        if(isNaN(to))
        {
            alert("输入的不是正整数");
            return false;
        }
        if (!(/(^[0-9]*[1-9][0-9]*$)/.test(to))){
            alert("输入的不是正整数");
            return false;
        }

        var message = document.getElementById('text').value;
        var userId=$.cookie('userId');

        let data={
            from:userId,
            to:to,
            message:message,
            type:2
        }
        websocket.send(JSON.stringify(data));
    }
</script>
</html>