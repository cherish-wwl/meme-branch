<%@ page language="java" import="java.util.*,com.meme.core.cache.ParamsCache" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path ;
%>
<!DOCTYPE HTML>
<html>
<head>
	<title><%=ParamsCache.get("PLATFORM_NAME").getValue()%></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=basePath%>/static/js/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath%>/static/css/signin.css">
	<script src="<%=basePath%>/static/js/jquery-2.2.4.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/static/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="<%=basePath%>/static/js/bootstrap/js/bootstrap.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
	<div class="container">

      <form class="form-signin" id="loginForm" method="post" action="<%=basePath%>/system/login.do">
        <h2 class="form-signin-heading" align="center"><%=ParamsCache.get("PLATFORM_NAME").getValue()%></h2>
        <label for="account" class="sr-only">账号</label>
        <input type="text" id="account" name="account" class="form-control" placeholder="账号" required autofocus>
        <br/>
        <label for="password" class="sr-only">密码</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="密码" required>
        <br/>
        <label for="validateCode" class="sr-only">验证码</label>
        <div style="width:100%;height:60px;">
        	<input type="text" id="validateCode" name="validateCode" class="form-control" placeholder="验证码" style="width:150px;float:left;">
        	<img onclick="refreshImage(this)" src="<%=basePath%>/system/validateCode.do" width="150" height="45" style="float:left;">
        </div>
        <button id="loginBtn" class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>

    </div>
</body>
</html>
<script type="text/javascript">
var tips='${message}';
if(tips!='') alert(tips);
function refreshImage(obj){
	obj.src='<%=basePath%>/system/validateCode.do?'+Math.random();
}
</script>