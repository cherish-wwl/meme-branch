<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_layui.jsp"%>
</head>

<body>
${requestScope.memberid}
<a href="<%=basePath%>member/logout">退出登录</a>
</body>
</html>