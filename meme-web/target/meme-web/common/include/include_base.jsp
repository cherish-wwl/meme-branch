<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<%--
<link href="<%=basePath%>static/js/webuploader/webuploader.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/webuploader/webuploader.js" type="text/javascript"></script>
--%>
<link href="<%=basePath%>static/js/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/uploadify/jquery.uploadify.js" type="text/javascript"></script>

<link href="<%=basePath%>static/css/base.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/validation/css/validation.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/validation/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/additional-methods.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/localization/messages_zh.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/validate-func.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.validate.method.extend.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.cascade.select.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.map.js" type="text/javascript"></script>
