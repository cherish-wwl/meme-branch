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
			<th field="account" sortable="true" align="center" width="10%">群主账号</th>
			<th field="nickname" sortable="true" align="center" width="10%">群主昵称</th>
			<th field="groupname" sortable="true" align="center" width="10%">群名称</th>
			<th field="avatar" sortable="false" align="center" width="20%">群头像</th>
			<th field="num" sortable="true" align="center" width="10%">群成员数量</th>
			<th field="description" sortable="true" align="center" width="20%">群介绍</th>
			<th field="addtime" sortable="false" align="center" width="20%">创建时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			群主账号或昵称：<input class="easyui-textbox" type="text" id="send_str" name="send_str">
			&nbsp;&nbsp;
			群名称：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
			<a href="#" onclick="groupMember()" class="easyui-linkbutton" iconCls="icon-clear">群员查询</a>
		</form>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/group/list.do',
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
function groupMember(){
	var groupid=selectOne($('#grid'));
	if(typeof groupid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>im/group/groupmember.do?groupid='+groupid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '群成员管理：'+checkeds[0].groupname,
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
</script>