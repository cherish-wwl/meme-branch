<%@ page language="java" import="java.util.*,com.meme.im.pojo.MemeCrawler,com.meme.core.util.HtmlUtil" pageEncoding="UTF-8"%>
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
<%@include file="/common/include/include-qiniu.jsp"%>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
			<input type="hidden" name="id" id="id" value="${id}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">会员尾号:</label>
					<div class="col-xs-9">
						<input type="checkbox" name="num" value="0"/>0&nbsp;&nbsp;
						<input type="checkbox" name="num" value="1"/>1&nbsp;&nbsp;
						<input type="checkbox" name="num" value="2"/>2&nbsp;&nbsp;
						<input type="checkbox" name="num" value="3"/>3&nbsp;&nbsp;
						<input type="checkbox" name="num" value="4"/>4&nbsp;&nbsp;
						<input type="checkbox" name="num" value="5"/>5&nbsp;&nbsp;
						<input type="checkbox" name="num" value="6"/>6&nbsp;&nbsp;
						<input type="checkbox" name="num" value="7"/>7&nbsp;&nbsp;
						<input type="checkbox" name="num" value="8"/>8&nbsp;&nbsp;
						<input type="checkbox" name="num" value="9"/>9&nbsp;&nbsp;
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">所选栏目:</label>
					<div class="col-xs-9">
						<express:SelectTag id="type" name="type" type="select" dictGroupCode="HOMEPAGE_TYPE" defaultOption="请选择" cssClass="form-control"/>
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