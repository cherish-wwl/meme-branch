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
			<th field="id" sortable="true" align="center" checkbox="true">ID</th>
			<th field="loginid" sortable="true" align="center" width="10%">账号ID</th>
			<th field="account" sortable="true" align="center" width="10%">账号名</th>
			<th field="event" sortable="true" align="center" width="16%">日志事件</th>
			<th field="type" sortable="true" align="center" width="5%">日志类型</th>
			<th field="ip" sortable="true" align="center" width="10%">终端地址</th>
			<th field="terminal" sortable="true" align="center" width="8%">操作系统</th>
			<th field="btype" sortable="true" align="center" width="8%">浏览器</th>
			<th field="isp" sortable="true" align="center" width="10%">运营商</th>
			<th field="country" sortable="true" align="center" width="15%">国家</th>
			<th field="province" sortable="true" align="center" width="15%">省份</th>
			<th field="city" sortable="true" align="center" width="15%">城市</th>
			<th field="url" sortable="true" align="center" width="23%">请求url</th>
			<th field="createtime" sortable="true" align="center" width="10%">操作时间</th>
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
		<a href="javascript:view();" class="easyui-linkbutton" iconCls="icon-search">查看详情</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>system/log/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
	<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function view(){
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
	$('#win').find('iframe').attr('src','<%=basePath%>system/log/view.do?id='+id);
	$('#win').dialog({
	    title: '查看详情',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:true
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>system/log/list.do',
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