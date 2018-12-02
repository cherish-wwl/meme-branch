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
			<th field="msgid" sortable="true" align="center" checkbox="true">消息编号</th>
			<th field="send_account" sortable="true" align="center" width="10%">发送方账号</th>
			<th field="send_nickname" sortable="true" align="center" width="10%">发送方昵称</th>
			<th field="send_mtype" sortable="false" align="center" width="10%">发送方类型</th>
			<th field="accept_account" sortable="true" align="center" width="10%">接收方账号</th>
			<th field="accept_nickname" sortable="true" align="center" width="10%">接收方昵称</th>
			<th field="accept_mtype" sortable="false" align="center" width="10%">接收方类型</th>
			<th field="content" sortable="false" align="center" width="15%">消息内容</th>
			<th field="state" sortable="false" align="center" width="10%">消息状态</th>
			<th field="msgtype" sortable="false" align="center" width="10%">消息类型</th>
			<th field="sendtime" sortable="true" align="center" width="10%">发送时间</th>
			<th field="accepttime" sortable="true" align="center" width="10%">接收时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			发送方账号或昵称：<input class="easyui-textbox" type="text" id="send_str" name="send_str">
			&nbsp;&nbsp;
			接收方账号或昵称：<input class="easyui-textbox" type="text" id="accept_str" name="accept_str">
			&nbsp;&nbsp;
			消息内容：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="send_mtype" type="select" dictGroupCode="MEMBER_ACCOUNT_TYPE" defaultOption="发送方类型"/>
			&nbsp;&nbsp;
			<express:SelectTag id="accept_mtype" type="select" dictGroupCode="MEMBER_ACCOUNT_TYPE" defaultOption="接收方类型"/>
			&nbsp;&nbsp;
			<express:SelectTag id="msgtype" type="select" dictGroupCode="MESSAGE_TYPE" defaultOption="消息类型"/>
			&nbsp;&nbsp;
			<express:SelectTag id="state" type="select" dictGroupCode="MESSAGE_STATE" defaultOption="消息状态"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a onclick="del($('#grid'),'<%=basePath%>im/message/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="editWindow">
<iframe id="editFrame" scrolling="no" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/message/list.do',
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