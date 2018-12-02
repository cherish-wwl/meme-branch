<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_bootstrap.jsp"%>
<style type="text/css">
.input-group-addon{min-width:125px;text-align:right;}
</style>
</head>
<body>
<div align="center">
<form id="editForm" method="post">
<input type="hidden" name="id" id="id" value="${object.id}"/>
<input type="hidden" name="menuid" id="menuid" value="${menuid}"/>
<input type="hidden" name="platformid" id="platformid" value="${platformid}"/>
	<br/>
    <div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">操作名称<label style="color:red;">*</label>：</span>
		<input id="name" name="name" type="text" value="${object.name}" class="form-control" placeholder="请输入操作名称">
		</div>
	</div>
	<div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">页面URL：</span>
		<input id="url" name="viewurl" type="text" value="${object.viewurl}" class="form-control" placeholder="请输入页面URL">
		</div>
	</div>
	<div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">操作URL：</span>
		<input id="url" name="url" type="text" value="${object.url}" class="form-control" placeholder="请输入操作URL">
		</div>
	</div>
	<div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">操作码：</span>
		<input id="opcode" name="opcode" type="text" value="${object.opcode}" class="form-control" placeholder="请输入操作码">
		</div>
	</div>
	<div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">js脚本：</span>
		<input id="jscontent" name="jscontent" type="text" value="${object.jscontent}" class="form-control" placeholder="请输入js脚本">
		</div>
	</div>
	<div class="form-group">
    	<div class="input-group col-xs-10">
		<span class="input-group-addon">排序号：</span>
		<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control" placeholder="请输入排序号">
		</div>
	</div>
</form>
</div>
</body>
</html>
