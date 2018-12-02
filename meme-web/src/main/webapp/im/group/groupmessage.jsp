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
			<th field="send_account" sortable="true" align="center" width="20%">发送方账号</th>
			<th field="send_nickname" sortable="true" align="center" width="15%">发送方昵称</th>
			<th field="send_mtype" sortable="false" align="center" width="15%">发送方类型</th>
			<th field="groupname" sortable="true" align="center" width="15%">所在群</th>
			<th field="content" sortable="false" align="center" width="15%">消息内容</th>
			<th field="sendtime" sortable="true" align="center" width="20%">发送时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			发送方账号或昵称：<input class="easyui-textbox" type="text" id="send_str" name="send_str">
			&nbsp;&nbsp;
			消息内容：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="send_mtype" type="select" dictGroupCode="MEMBER_ACCOUNT_TYPE" defaultOption="发送方类型"/>
			&nbsp;&nbsp;
			发送时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate">至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
</div>
<div id="editWindow">
<iframe id="editFrame" scrolling="no" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function doSearch(formObj,gridObj){
	var paramString=formObj.serialize();
	if(null==paramString||paramString==''){
		gridObj.datagrid('reload');
	}else{
		var data=new Array();
		var params=paramString.split('&');
		for(var i=0;i<params.length;i++){
			var parm=params[i].split('=');
			data[i]=parm[0]+':"'+decodeURI(parm[1])+'"';
		}
		var parms='{'+data.toString()+'}';
		var d=eval('('+parms+')');
		d.startdate=$('input[name="startdate"]').val();
		d.enddate=$('input[name="enddate"]').val();
		gridObj.datagrid('loadData',{total: 0,rows:[]});
		gridObj.datagrid('load',d);
	}
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/group/groupmessageList.do',
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