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
			<th field="dictitemid" sortable="true" align="center" width="10%" checkbox="true">ID</th>
			<th field="dictitemname" sortable="true" align="center" width="25%">字典项名称</th>
			<th field="dictitemcode" sortable="true" align="center" width="25%">字典项编码</th>
			<th field="isdefaultString" sortable="false" align="center" width="10%">是否默认</th>
			<th field="sortno" sortable="true" align="center" width="8%">排序号</th>
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
		<a onclick="add()" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a onclick="modify()" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a onclick="del($('#grid'),'<%=basePath%>system/dictitem/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a onclick="batchImport()" class="easyui-linkbutton">批量导入</a>
	</div>
</div>
<div id="editWindow">
<iframe id="editFrame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function batchImport(){
	var winObj=$('#editWindow');
	var url='<%=basePath%>system/dictitem/importView.do?dictgroupid=${dictgroupid}';
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '批量导入',
	    width: 150,
	    height: 80,
	    closed: false,
	    modal: true,
	    collapsible: false
	});
}
function add(){
	var url='<%=basePath%>system/dictitem/editView.do?dictgroupid=${dictgroupid}';
	$('#editWindow').find('iframe').attr('src',url);
	$('#editWindow').dialog({
	    title: '新增',
	    width: 600,
	    height: 300,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					var formObj=$('#editWindow').find('iframe').contents().find('form');
					var validateObj=$('#editWindow').find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						$.ajax({
							url: '<%=basePath%>system/dictitem/add.do',
							type: 'post',
							dataType: 'json',
							data: formObj.serialize(),
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									$('#editWindow').dialog('close');
									$('#grid').datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error: function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){$('#editWindow').dialog('close');}
			}
	    ]
	});
}
function modify(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var id=null;
	var idVar=null;
	outerloop:
	for(var i=0;i<checkeds.length;i++){
		var j=0;
		for(var key in checkeds[i]){
			if(j==0){
				id=checkeds[i][key];
				idVar=key;
				break outerloop;
			}
		}
	}
	var url='<%=basePath%>system/dictitem/editView.do?dictgroupid=${dictgroupid}&id='+id;
	$('#editWindow').find('iframe').attr('src',url);
	$('#editWindow').dialog({
	    title: '修改',
	    width: 600,
	    height: 300,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'修改',
				iconCls:'icon-save',
				handler:function(){
					var formObj=$('#editWindow').find('iframe').contents().find('form');
					var validateObj=$('#editWindow').find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						$.ajax({
							url: '<%=basePath%>system/dictitem/edit.do',
							type:'post',
							dataType:'json',
							data: formObj.serialize(),
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									$('#editWindow').dialog('close');
									$('#grid').datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){$('#editWindow').dialog('close');}
			}
	    ]
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/dictitem/list.do?dictgroupid=${dictgroupid}',
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