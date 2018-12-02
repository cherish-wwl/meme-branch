<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>异常页面</title>
</head>

<body>
	<% Exception ex = (Exception) request.getAttribute("exception");%>
	<h3 style="color:red;"><%=ex.getMessage()%></h3>
</body>
</html>
