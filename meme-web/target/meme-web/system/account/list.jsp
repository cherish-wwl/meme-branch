<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
<style type="text/css">
.tree .tree-title{height: 25px;line-height: 25px;}
.tree .tree-node-selected{height: 25px;line-height: 25px;}
.tree .tree-node{height: 25px;line-height: 25px;}
.tree .tree-file{margin-top:3px;}
.tree li span label{cursor:pointer;}
.tree a{text-decoration:none;}
.tree li a:link{color:black;}
.tree li a:visited{color:black;}
.tree li a:hover{color:black;}
.tree li a:active{color:black;}
</style>
</head>

<body class="easyui-layout">
	<div data-options="region:'west',split:true,collapsible:true" title="部门选择" style="width:17%;">
		<ul id="departmentList">
			
		</ul>
	</div>
	<div data-options="region:'center',split:true,collapsible:false" title="用户列表" style="width:80%;">
		<table id="grid" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="id" sortable="true" align="center" checkbox="true">ID</th>
					<th field="account" sortable="true" align="center" width="12%">账号</th>
					<th field="accountStateText" sortable="false" align="center" width="6%">账号状态</th>
					<th field="username" sortable="true" align="center" width="12%">姓名</th>
					<th field="sexText" sortable="false" align="center" width="6%">性别</th>
					<th field="cellphone" sortable="true" align="center" width="10%">手机号码</th>
					<th field="email" sortable="true" align="center" width="13%">邮箱</th>
					<th field="deptname" sortable="true" align="center" width="13%">部门</th>
					<th field="posname" sortable="true" align="center" width="10%">职位</th>
					<th field="createtime" sortable="true" align="center" width="16%">添加日期</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div class="express-search">
				<form id="searchForm" name="searchForm">
					<input type="hidden" id="deptid" name="deptid" value=""/>
					查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
					&nbsp;&nbsp;
					<express:SelectTag id="accountState" type="select" dictGroupCode="ACCOUNT_STATE" defaultOption="账号状态" cssClass="easyui-combobox"/>
					&nbsp;&nbsp;
					<express:SelectTag id="sex" type="select" dictGroupCode="SEX" defaultOption="性别" cssClass="easyui-combobox"/>
					&nbsp;&nbsp;
					<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					&nbsp;&nbsp;
					<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
				</form>
			</div>
			<div style="">
				<a href="javascript:add();" class="easyui-linkbutton" iconCls="icon-add">增加</a>
				<a href="javascript:modify()" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
				<a href="javascript:del($('#grid'),'<%=basePath%>system/account/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
				<a href="#" onclick="start()" class="easyui-linkbutton" iconCls="icon-clear">启用</a>
				<a href="#" onclick="stop()" class="easyui-linkbutton" iconCls="icon-clear">禁用</a>
				<a href="#" onclick="view($('#grid'),$('#win'),'<%=basePath%>system/account/view.do')" class="easyui-linkbutton">查看详细</a>
				<a href="#" onclick="userAuth()" class="easyui-linkbutton" iconCls="icon-clear">角色授权</a>
				<a href="#" onclick="passwordReset()" class="easyui-linkbutton">密码重置</a>
			</div>
		</div>
	</div>
	<div id="win">
		<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
var departmentListObj=$('#departmentList');
var accountGridObj=$('#grid');
var winObj=$('#win');
var selectedNode=null;
var roles=null;
function add(){
	var url='<%=basePath%>system/account/editView.do?organid=${organid}';
	$('#win').find('iframe').attr('src',url);
	$('#win').dialog({
	    title: '新增',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'新增',
				iconCls:'icon-save',
				handler:function(){
					var formObj=$('#win').find('iframe').contents().find('form');
					var validateObj=$('#win').find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: '<%=basePath%>system/account/add.do',
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
	var url='<%=basePath%>system/account/editView.do?organid=${organid}&id='+id;
	$('#win').find('iframe').attr('src',url);
	$('#win').dialog({
	    title: '修改',
	    width: 600,
	    height: 400,
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
							url: '<%=basePath%>system/account/edit.do',
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
function passwordReset(){
	var ids=selectMulti(accountGridObj);
	if(typeof ids =='undefined') return ;
	$.messager.prompt('提示', '请输入重置密码', function(input){
		if (input&&typeof input !='undefined'){
			$.ajax({
				url: '<%=basePath%>system/account/resetPassword.do',
				type: 'post',
				dataType: 'json',
				data:{'ids':ids,'password':input},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						$('#tree').tree('reload');
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
function roleCheck(){
	var treeObj=window.frames['frame'].treeObj;
	if(roles!=null){
		if(typeof treeObj !='undefined'){
			for(var i=0;i<roles.length;i++){
				var node=treeObj.tree('find',roles[i].id);
				treeObj.tree('check',node.target);
			}
		}
	}
}
function userAuth(){
	var id=selectOne(accountGridObj);
	if(typeof id =='undefined') return ;
	roles=null;
	$.ajax({
		url: '<%=basePath%>system/account/getAccountRoles.do',
		type: 'post',
		dataType: 'json',
		data:{'loginid':id},
		success: function(data){
			if(data&&data.length>0){
				roles=data;
			}
			//else{
			//	$.messager.alert('提示','获取用户角色失败，请稍后再试或者联系管理员处理','error');
			//}
		},
		error: function(){
			$.messager.alert('提示','操作失败','error');
		}
	});
	
	winObj.find('iframe').attr('src','<%=basePath%>system/role/listRolesView.do?type=checkbox&organid=${organid}');
	winObj.dialog({
	    title: '用户角色授权',
	    width: 400,
	    height: 250,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'选择',
				iconCls:'icon-save',
				handler:function(){
					var treeObj=window.frames['frame'].treeObj;
					var nodes=treeObj.tree('getChecked');
					var roleids=new Array();
					if(nodes&&nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							roleids.push(nodes[i].id);
						}
					}
					$.ajax({
        				url: '<%=basePath%>system/account/accountAuth.do',
        				type:'post',
        				dataType:'json',
        				data:{'roleids':roleids,'loginid':id},
        				success:function(data){
        					if(data.state==1){
        						$.messager.alert('提示',data.message,'info');
        						winObj.dialog('close');
        					}else if(data.state==0){
        						$.messager.alert('提示',data.message,'error');
        					}
        				},
        				error:function(){
        					$.messager.alert('提示','操作失败','error');
        				}
        			});
					/*else {
						$.messager.alert('提示','未选中授权角色','error');
					}*/
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
	treeCheck(roleCheck);
}
function start(){
	var ids=selectMulti(accountGridObj);
	if(typeof ids =='undefined') return ;
	$.messager.confirm('提示', '再次确认是否启用选中账号？', function(state){
		if (state){
			$.ajax({
				url: '<%=basePath%>system/account/start.do',
				type: 'post',
				dataType: 'json',
				data:{'ids':ids},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						accountGridObj.datagrid('reload');
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
function stop(){
	var ids=selectMulti(accountGridObj);
	if(typeof ids =='undefined') return ;
	$.messager.confirm('提示', '再次确认是否禁用选中账号？', function(state){
		if (state){
			$.ajax({
				url: '<%=basePath%>system/account/stop.do',
				type: 'post',
				dataType: 'json',
				data:{'ids':ids},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						accountGridObj.datagrid('reload');
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

$(function(){
	departmentListObj.tree({
		lines: true,
		url: '<%=basePath%>system/department/listDepts.do',
	    queryParams: {'organid':'${organid}'},
	    loadFilter: function (data) {
	    	return data;
	    },
	    onSelect:function(node){
	    	if(selectedNode==null||node.id!=selectedNode.id){
	    		selectedNode=node;
	    	}else{
	    		var selected=departmentListObj.tree('getSelected');
	    		if(selected!=null){
	    			if(node.id==selected.id) {
	    				selectedNode=null;
	    				departmentListObj.find('.tree-node-selected').removeClass('tree-node-selected');
	    			}
	    		}
	    	}
	    	var selected=departmentListObj.tree('getSelected');
	    	if(selected!=null){
	    		$('#deptid').val(selected.id);
	    	}else{
	    		$('#deptid').val('');
	    	}
	    	doSearch($('#searchForm'),$('#grid'));
	    }
	});
	
	accountGridObj.datagrid({
		method: 'post',
        url: '<%=basePath%>system/account/list.do?organid=${organid}',
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
