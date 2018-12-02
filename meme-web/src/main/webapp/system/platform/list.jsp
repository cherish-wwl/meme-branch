<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
</head>

<body>
<table id="grid" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="id" sortable="true" align="center" width="20%" checkbox="true">ID</th>
			<th field="name" sortable="true" align="center" width="25%">平台名称</th>
			<th field="sortno" sortable="true" align="center" width="15%">排序号</th>
			<th field="isOpenString" sortable="false" align="center" width="13%">是否开放</th>
			<th field="remark" sortable="true" align="center" width="25%">备注</th>
			<th field="createtime" sortable="true" align="center" width="20%">创建日期</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="isopen" type="select" dictGroupCode="PLATFORM_IS_OPEN" defaultOption="平台类型" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="javascript:add($('#grid'),$('#win'),'<%=basePath%>system/platform/editView.do','<%=basePath%>system/platform/add.do',{'title':'新增平台'});" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>system/platform/editView.do','<%=basePath%>system/platform/edit.do',{'title':'修改平台'})" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>system/platform/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/platform/list.do',
        width:'auto',
        height:'auto',
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
        pagination: true,
        pageSize:20,
        singleSelect: false,
        striped: true,
        fitColumns: true,
        border:true
	});
});
</script>