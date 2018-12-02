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
			<th field="id" sortable="true" align="center" width="8%" checkbox="true">ID</th>
			<th field="name" sortable="true" align="center" width="20%">单位名称</th>
			<th field="email" sortable="true" align="center" width="15%">联系邮箱</th>
			<th field="officephone" sortable="true" align="center" width="13%">联系电话</th>
			<th field="typeString" sortable="false" align="center" width="10%">类型</th>
			<th field="stateString" sortable="false" align="center" width="10%">状态</th>
			<th field="opentime" sortable="true" align="center" width="10%">开通时间</th>
			<th field="endtime" sortable="true" align="center" width="10%">关闭时间</th>
			<th field="createtime" sortable="true" align="center" width="10%">创建时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="organizationtype" type="select" dictGroupCode="ORGANIZATION_TYPE" defaultOption="单位类型" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<express:SelectTag id="organizationstate" type="select" dictGroupCode="ORGANIZATION_STATE" defaultOption="单位状态" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="add($('#grid'),$('#win'),'<%=basePath%>system/organization/editView.do','<%=basePath%>system/organization/add.do',{'title':'新增单位'})" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="#" onclick="modify($('#grid'),$('#win'),'<%=basePath%>system/organization/editView.do','<%=basePath%>system/organization/edit.do',{'title':'修改单位'})" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="#" onclick="del($('#grid'),'<%=basePath%>system/organization/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="#" onclick="auth('认证','<%=basePath%>system/organization/auth.do')" class="easyui-linkbutton">认证</a>
		<a href="#" onclick="auth('取消认证','<%=basePath%>system/organization/cancelAuth.do')" class="easyui-linkbutton">禁用</a>
		<a href="#" onclick="funcAuth()" class="easyui-linkbutton">功能授权</a>
		<a href="#" onclick="manageRole()" class="easyui-linkbutton">角色管理</a>
		<a href="#" onclick="manageDept()" class="easyui-linkbutton">部门管理</a>
		<a href="#" onclick="managePosition()" class="easyui-linkbutton">职位管理</a>
		<a href="#" onclick="manageUser()" class="easyui-linkbutton">用户管理</a>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function auth(action,url){
	var gridObj=$('#grid');
	var ids=selectMulti(gridObj);
	if(typeof ids =='undefined') return ;
	$.messager.confirm('提示', '再次确认是否'+action+'选中单位？', function(state){
		if (state){
			$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				data:{'ids':ids},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						gridObj.datagrid('reload');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error: function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
		}
	});
}
function funcAuth(){
	var organid=selectOne($('#grid'));
	if(typeof organid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>system/organization/funcAuthView.do?organid='+organid;
	var action='<%=basePath%>system/organization/funcAuth.do';
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	
	winObj.dialog({
	    title: '功能授权：'+checkeds[0].name,
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
						data:{'menuids':menuids,'delmenuids':delmenuids,'operationids':operationids,'deloperationids':deloperationids,'organid':organid},
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
function manageRole(){
	var organid=selectOne($('#grid'));
	if(typeof organid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>system/role/index.do?organid='+organid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '角色管理：'+checkeds[0].name,
	    width: 800,
	    height: 500,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    maximized:true
	});
}
function manageDept(){
	var organid=selectOne($('#grid'));
	if(typeof organid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>system/department/index.do?organid='+organid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '部门管理：'+checkeds[0].name,
	    width: 700,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false
	});
}
function managePosition(){
	var organid=selectOne($('#grid'));
	if(typeof organid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>system/position/index.do?organid='+organid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '职位管理：'+checkeds[0].name,
	    width: 700,
	    height: 500,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false
	});
}
function manageUser(){
	var organid=selectOne($('#grid'));
	if(typeof organid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>system/account/index.do?organid='+organid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '用户管理：'+checkeds[0].name,
	    width: 700,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
        maximized:true
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/organization/list.do',
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