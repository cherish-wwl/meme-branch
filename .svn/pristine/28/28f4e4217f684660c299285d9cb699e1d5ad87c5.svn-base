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
			<th field="memberid" sortable="true" align="center" width="6%">会员编号</th>
			<th field="account" sortable="true" align="center" width=6%>会员账号</th>
			<th field="nickname" sortable="true" align="center" width="6%">昵称</th>
			<th field="sexText" sortable="false" align="center" width="6%">性别</th>
			<th field="age" sortable="false" align="center" width="6%">年龄</th>
			<th field="address" sortable="false" align="center" width="6%">所在地</th>
			<th field="constellation" sortable="false" align="center" width="6%">星座</th>
			<th field="bloodType" sortable="false" align="center" width="6%">血型</th>
			<th field="emotionalState" sortable="false" align="center" width="6%">情感状态</th>
			<th field=hobby sortable="false" align="center" width="6%">兴趣爱好</th>
			<th field="school" sortable="false" align="center" width="6%">学校</th>
			<th field="job" sortable="false" align="center" width="6%">职业</th>
			<th field="income" sortable="false" align="center" width="6%">收入</th>
			<th field="height" sortable="false" align="center" width="6%">身高</th>
			<th field="weight" sortable="false" align="center" width="6%">体重</th>
			<th field="qqNumber" sortable="false" align="center" width="6%">QQ号码</th>
			<th field="wechatNumber" sortable="false" align="center" width="6%">微信号码</th>
			<th field="country" sortable="true" align="center" width="6%">国家</th>
			<th field="province" sortable="true" align="center" width="6%">省份</th>
			<th field="city" sortable="true" align="center" width="6%">城市</th>
			<th field="registertime" sortable="true" align="center" width="6%">注册时间</th>
			<th field="mtypeText" sortable="false" align="center" width="6%">会员类型</th>
			<th field="stateText" sortable="false" align="center" width="6%">账号状态</th>
			<th field="cellphone" sortable="true" align="center" width="6%">手机号码</th>
			<th field="email" sortable="true" align="center" width="6%">邮箱</th>
			<th field="sign" sortable="true" align="center" width="6%">个性签名</th>
			<th field="isValidate" sortable="true" align="center" width="6%">是否验证</th>
			<th field="consignee" sortable="consignee" align="center" width="6%">收货人</th>
			<th field="receiving_telephone" sortable="receiving_telephone" align="center" width="6%">收货电话</th>
			<th field="receiving_address" sortable="receiving_address" align="center" width="6%">收货地址</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="sex" type="select" dictGroupCode="SEX" defaultOption="性别" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<express:SelectTag id="mtype" type="select" dictGroupCode="MEMBER_ACCOUNT_TYPE" defaultOption="会员类型" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<express:SelectTag id="state" type="select" dictGroupCode="MEMBER_ACCOUNT_STATE" defaultOption="账号状态" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a onclick="addServiceAccount()" class="easyui-linkbutton" iconCls="icon-add">增加客服账号</a>
		<a onclick="editServiceAccount()" class="easyui-linkbutton" iconCls="icon-add">修改客服账号</a>
		<a onclick="del($('#grid'),'<%=basePath%>im/member/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="#" onclick="resetPassword()" class="easyui-linkbutton" iconCls="icon-undo">密码重置</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function addServiceAccount(){
	var winObj=$('#win');
	var gridObj=$('#grid');
	var url='<%=basePath%>im/member/add.do';
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '添加客服账号',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable: true,
	    resizable: true,
	    collapsible: true,
	    minimizable: false,
	    buttons:[
			{
				text:'添加',
				iconCls:'icon-save',
				handler:function(){
					var formObj=winObj.find('iframe').contents().find('form');
					var validateObj=winObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: '<%=basePath%>im/member/doAdd.do',
							type: 'post',
							dataType: 'json',
							data: data,
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									winObj.dialog('close');
									gridObj.datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error: function(e){
								$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
}
function editServiceAccount(){
	var winObj=$('#win');
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var memberid=selectOne(gridObj);
	if(null==memberid || typeof memberid == 'undefined') return ;
	if(checkeds[0].mtype != 1){
		$.messager.alert('提示','非网站客服账号禁止操作','error');
		return ;
	}
	var url='<%=basePath%>im/member/edit.do?memberid='+memberid;
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '修改客服账号',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable: true,
	    resizable: true,
	    collapsible: true,
	    minimizable: false,
	    buttons:[
			{
				text:'修改',
				iconCls:'icon-save',
				handler:function(){
					var formObj=winObj.find('iframe').contents().find('form');
					var validateObj=winObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: '<%=basePath%>im/member/doEdit.do',
							type: 'post',
							dataType: 'json',
							data: data,
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									winObj.dialog('close');
									gridObj.datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error: function(e){
								$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
}
function resetPassword(){
	var winObj=$('#win');
	var gridObj=$('#grid');
	var ids=selectMulti(gridObj);
	if(typeof ids =='undefined') return ;
	$.messager.prompt('提示', '请输入重置密码', function(input){
		if (input&&typeof input !='undefined'){
			$.ajax({
				url: '<%=basePath%>im/member/resetPassword.do',
				type: 'post',
				dataType: 'json',
				data:{'ids':ids,'password':input},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error: function(){
					$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
				}
			});
		}
	});
	
}

$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/list.do',
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