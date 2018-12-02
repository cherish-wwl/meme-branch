<%@ page language="java" import="java.util.*,com.meme.core.cache.ParamsCache" pageEncoding="UTF-8"%>

<%@include file="/common/include/include_base.jsp"%>
<title><%=ParamsCache.get("PLATFORM_NAME").getValue()%></title>
<%-- <link rel="shortcut icon" href="<%=basePath%>/images/icons/favicon.ico" /> --%>
<link href="<%=basePath%>static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>