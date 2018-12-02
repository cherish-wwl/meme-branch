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
			<th field="tempid" sortable="true" align="center" width="10%">模板编号</th>
			<th field="temname" sortable="true" align="center" width="20%">模板名称</th>
			<th field="url" sortable="true" align="center" width="50%">图片地址</th>
			<th field="sortno" sortable="true" align="center" width="10%">排序号</th>
			<th field="addtime" sortable="true" align="center" width="10%">添加时间</th>
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
		<a href="javascript:add($('#grid'),$('#win'),'<%=basePath%>im/member/bgtemplate/editView.do','<%=basePath%>im/member/bgtemplate/add.do');" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>im/member/bgtemplate/editView.do','<%=basePath%>im/member/bgtemplate/edit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>im/member/bgtemplate/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/bgtemplate/list.do',
        width:'auto',
        height:'auto',
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
        pagination: true,
        pageSize:20,
        singleSelect: true,
        striped: true,
        fitColumns: true,
        border:true
	});
});
</script>