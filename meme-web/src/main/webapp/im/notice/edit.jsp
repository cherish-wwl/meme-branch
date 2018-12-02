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
			<form id="editForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="columnid" id="columnid" value="${object.columnid}"/>
			<input type="hidden" name="contentid" id="contentid" value="${object.contentid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">公告内容:</label>
					<div class="col-xs-9">
						<input id="content" name="content" type="text" value="${object.content}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">所属栏目分块:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="sectioncode" name="sectioncode" type="select" selected="${object.sectioncode}" dictGroupCode="NOTICE_COL" defaultOption="所属栏目分块" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
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