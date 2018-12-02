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
			<input type="hidden" name="platformid" id="platformid" value="${platformid}"/>
			<input type="hidden" name="pid" id="pid" value="${pid}"/>
			<input type="hidden" name="hid" id="hid" value="${hid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">菜单名称:</label>
					<div class="col-xs-9">
						<input id="name" name="name" type="text" value="${object.name}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">菜单链接:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<c:choose>
							<c:when test="${empty object.url || object.url eq null }">
							<input id="url" name="url" type="text" value="#" class="form-control"/>
							</c:when>
							<c:otherwise>
							<input id="url" name="url" type="text" value="${object.url}" class="form-control"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">是否允许添加子菜单:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="isallowchild" name="isallowchild" type="select" selected="${object.isallowchild}" dictGroupCode="SF" defaultOption="是否允许添加子菜单" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="url" name="sortno" type="text" value="${object.sortno}" class="form-control"/>
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
<script type="text/javascript" src="<%=basePath%>system/menu/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
</script>