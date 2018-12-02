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
			<th field="dictgroupid" sortable="true" align="center" width="10%" checkbox="true">ID</th>
			<th field="dictgroupname" sortable="true" align="center" width="25%">字典组名</th>
			<th field="dictgroupcode" sortable="true" align="center" width="23%">字典组编码</th>
			<th field="typeString" sortable="false" align="center" width="20%">字典组分类</th>
			<th field="remark" sortable="true" align="center" width="30%">备注</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="dictgrouptype" type="select" dictGroupCode="DICTGROUP_TYPE" defaultOption="字典组分类" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="javascript:add($('#grid'),$('#dialog'),'<%=basePath%>system/dictgroup/editView.do','<%=basePath%>system/dictgroup/add.do');" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#dialog'),'<%=basePath%>system/dictgroup/editView.do','<%=basePath%>system/dictgroup/edit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>system/dictgroup/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="javascript:dictitemList()" class="easyui-linkbutton">字典项管理</a>
	</div>
</div>
<div id="dialog">
<iframe id="frame" scrolling="no" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function dictitemList(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var id=null;
	outerloop:
	for(var i=0;i<checkeds.length;i++){
		var j=0;
		for(var key in checkeds[i]){
			if(j==0){
				id=checkeds[i][key];
				break outerloop;
			}
		}
	}
	$('#dialog').find('iframe').attr('src','<%=basePath%>system/dictitem/index.do?dictgroupid='+id);
	$('#dialog').dialog({
	    title: '字典项管理',
	    width: 800,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/dictgroup/list.do',
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