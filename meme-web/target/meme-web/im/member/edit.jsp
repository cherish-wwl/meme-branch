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
			<input type="hidden" name="memberid" id="memberid" value="${object.memberid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">账号名称:</label>
					<div class="col-xs-9">
						<input id="account" name="account" type="text" value="${object.account}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">昵称:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="nickname" name="nickname" type="text" value="${object.nickname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">性别:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="sex" name="sex" type="select" selected="${object.sex}" dictGroupCode="SEX" defaultOption="性别" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">邮箱:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="email" name="email" type="text" value="${object.email}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">手机号码:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="cellphone" name="cellphone" type="text" value="${object.cellphone}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">个性签名:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sign" name="sign" type="text" value="${object.sign}" class="form-control">
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
<script type="text/javascript" src="<%=basePath%>im/member/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>