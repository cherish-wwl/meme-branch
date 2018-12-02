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
			<th field="id" sortable="true" align="center" width="10%" checkbox="true">ID</th>
			<th field="name" sortable="true" align="center" width="30%">职位名</th>
			<th field="deptName" sortable="true" align="center" width="35%">所属部门</th>
			<th field="remark" sortable="true" align="center" width="30%">备注</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="javascript:add($('#grid'),$('#editWindow'),'<%=basePath%>system/position/editView.do?organid=${organid}','<%=basePath%>system/position/add.do');" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#editWindow'),'<%=basePath%>system/position/editView.do','<%=basePath%>system/position/edit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>system/position/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="editWindow">
<iframe id="editFrame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/position/list.do?organid=${organid}',
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