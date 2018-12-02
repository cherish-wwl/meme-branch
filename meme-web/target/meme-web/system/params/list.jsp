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
			<th field="name" sortable="true" align="center" width="20%">参数名</th>
			<th field="value" sortable="true" align="center" width="35%">参数值</th>
			<th field="typeString" sortable="false" align="center" width="10%">分类</th>
			<th field="remark" sortable="true" align="center" width="33%">备注</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="dictgrouptype" type="select" dictGroupCode="DICTGROUP_TYPE" defaultOption="参数分类" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a onclick="add($('#grid'),$('#editWindow'),'<%=basePath%>system/params/editView.do','<%=basePath%>system/params/add.do')" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a onclick="modify($('#grid'),$('#editWindow'),'<%=basePath%>system/params/editView.do','<%=basePath%>system/params/edit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a onclick="del($('#grid'),'<%=basePath%>system/params/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a onclick="clearRules()" class="easyui-linkbutton" iconCls="icon-clear">清空验证规则</a>
	</div>
</div>
<div id="editWindow">
<iframe id="editFrame" scrolling="no" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function clearRules(){
	var url='<%=basePath%>system/params/clearRules.do';
	$.messager.confirm('提示', '再次确认是否清空所有验证规则？', function(state){
		if (state){
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
		}
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/params/list.do',
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