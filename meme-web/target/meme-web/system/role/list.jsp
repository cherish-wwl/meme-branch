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
			<th field="name" sortable="true" align="center" width="25%">角色名称</th>
			<th field="description" sortable="true" align="center" width="33%">角色描述</th>
			<th field="num" sortable="true" align="center" width="10%">可授权人数</th>
			<th field="starttime" sortable="true" align="center" width="15%">启用时间</th>
			<th field="endtime" sortable="true" align="center" width="15%">停用时间</th>
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
		<a href="javascript:add()" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify()" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>system/role/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="javascript:roleAuth()" class="easyui-linkbutton">角色授权</a>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function roleAuth(){
	var roleid=selectOne($('#grid'));
	if(typeof roleid=='undefined') return ;
	
	var url='<%=basePath%>system/role/funcAuthView.do?organid=${organid}&roleid='+roleid;
	var action='<%=basePath%>system/role/funcAuth.do';
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	
	winObj.dialog({
	    title: '角色授权',
	    width: 800,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'授权',
				iconCls:'icon-save',
				handler:function(){
					var addedMenus=window.frames['frame'].addedMenus;
					var deletedMenus=window.frames['frame'].deletedMenus;
					var addedOperations=window.frames['frame'].addedOperations;
					var deletedOperations=window.frames['frame'].deletedOperations;
					var menus=window.frames['frame'].menus;
					var operations=window.frames['frame'].operations;
					for(var i=0;i<menus.length;i++){
						var id=menus[i].id;
						if(addedMenus.containsKey(id)) addedMenus.remove(id);
					}
					for(var i=0;i<operations.length;i++){
						var id=operations[i].id;
						if(addedOperations.containsKey(id)) addedOperations.remove(id);
					}
					
					var menuids=new Array();
					var delmenuids=new Array();
					var operationids=new Array();
					var deloperationids=new Array();
					
					var keys=addedMenus.keySet();
					for(var key in keys){
						menuids.push(addedMenus.get(keys[key]));
					}
					
					var keys=deletedMenus.keySet();
					for(var key in keys){
						delmenuids.push(deletedMenus.get(keys[key]));
					}
					
					var keys=addedOperations.keySet();
					for(var key in keys){
						operationids.push(addedOperations.get(keys[key]));
					}
					
					var keys=deletedOperations.keySet();
					for(var key in keys){
						deloperationids.push(deletedOperations.get(keys[key]));
					}
					
					//alert(JSON.stringify(menuids));
					//alert(JSON.stringify(delmenuids));
					//alert(JSON.stringify(operationids));
					//alert(JSON.stringify(deloperationids));
					
					/**/
					$.ajax({
						url: action,
						type: 'post',
						dataType: 'json',
						data:{'menuids':menuids,'delmenuids':delmenuids,'operationids':operationids,'deloperationids':deloperationids,'roleid':roleid},
						success: function(data){
							if(data.state==1){
								$.messager.alert('提示',data.message,'info');
								winObj.dialog('close');
							}else if(data.state==0){
								$.messager.alert('提示',data.message,'error');
							}
						},
						error: function(){
							$.messager.alert('提示','操作失败','error');
						}
					});
					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
}
function add(){
	var url='<%=basePath%>system/role/editView.do?organid=${organid}';
	$('#win').find('iframe').attr('src',url);
	$('#win').dialog({
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
					var formObj=$('#win').find('iframe').contents().find('form');
					var validateObj=$('#win').find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: '<%=basePath%>system/role/add.do',
							type: 'post',
							dataType: 'json',
							data: data,
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									$('#win').dialog('close');
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
				handler:function(){$('#win').dialog('close');}
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
	var url='<%=basePath%>system/role/editView.do?organid=${organid}&id='+id;
	$('#win').find('iframe').attr('src',url);
	$('#win').dialog({
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
					var formObj=$('#win').find('iframe').contents().find('form');
					var validateObj=$('#win').find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: '<%=basePath%>system/role/edit.do',
							type:'post',
							dataType:'json',
							data: data,
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									$('#win').dialog('close');
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
				handler:function(){$('#win').dialog('close');}
			}
	    ]
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/role/list.do?organid=${organid}',
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