<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_bootstrap.jsp"%>
<link href="<%=basePath%>static/js/easyui-1.5/themes/color.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/easyui-1.5/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/easyui-1.5/themes/metro/easyui.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/easyui-1.5/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/easyui-1.5/easyloader.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/easyui-1.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/util.js" type="text/javascript"></script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
			<input type="hidden" name="id" id="id" value="${object.id}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">平台名称:</label>
					<div class="col-xs-9">
						<input id="name" name="name" type="text" value="${object.name}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">是否开放平台:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="isopen" name="isopen" type="select" selected="${object.isopen}" dictGroupCode="PLATFORM_IS_OPEN" defaultOption="是否开放平台" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">备注:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="remark" name="remark" type="text" value="${object.remark}" class="form-control">
					</div>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>system/platform/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>