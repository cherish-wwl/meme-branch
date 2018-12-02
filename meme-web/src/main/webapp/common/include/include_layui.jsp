<%@ page language="java" import="java.util.*,com.meme.core.cache.ParamsCache" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.mmgmmj.com/jsp/jstl/tag" prefix="express"%>
<%@ taglib uri="http://www.mmgmmj.com/jsp/jstl/functions" prefix="expressFunc"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + (request.getServerPort()!=80?(":"+request.getServerPort()):"") + request.getContextPath() +"/";
%>
<script type="text/javascript">
	window.GLOBAL_BASE_PATH = "<%=basePath%>";
</script>
<%-- <link rel="shortcut icon" href="<%=basePath%>/images/icons/favicon.ico" /> --%>
<script src="<%=basePath%>static/js/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/json2.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/jquery.tmpl.min.js" type="text/javascript"></script>

<%-- <link rel="shortcut icon" href="<%=basePath%>/images/icons/favicon.ico" /> --%>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="<%=basePath%>static/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>static/layui/css/layui.mobile.css">
<link rel="stylesheet" href="<%=basePath%>static/js/jquery_mobile/jquery.mobile-1.4.5.css">
<script src="<%=basePath%>static/js/jshelper.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/jquery_mobile/jquery.mobile-1.4.5.js" type="text/javascript"></script>
<script src="<%=basePath%>static/layui/layui.js"></script>
