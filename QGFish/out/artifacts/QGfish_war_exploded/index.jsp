<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>QG闲鱼首页</title>
    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <%--    <script src="http://code.jquery.com/jquery-latest.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

  </head>
  <body>
  <div  style="text-align: center">
    <p><a class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/login.jsp" role="button" >进入登录界面</a></p><br>
  </div>

  <!--  轮播图  , -->
  <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
      <li data-target="#carousel-example-generic" data-slide-to="1"></li>
      <li data-target="#carousel-example-generic" data-slide-to="2"></li>
    </ol>

    <!--  修改图片路径和要轮播的图片个数.  -->
    <div class="carousel-inner" role="listbox">
      <div class="item active">
        <img src="img/th8.jpg" alt="图片已丢失">
        <div class="carousel-caption">
        </div>
      </div>
      <div class="item">
        <img src="img/th7.jpg" alt="图片已丢失">
        <div class="carousel-caption">
        </div>
      </div>
      <div class="item">
        <img src="img/th9.jpg" alt="图片已丢失">
        <div class="carousel-caption">
        </div>
      </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">上一张</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">下一张</span>
    </a>
  </div>

  </body>

</html>
