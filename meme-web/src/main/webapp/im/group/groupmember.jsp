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
			<th field="memberid" sortable="true" align="center" width="15%">会员编号</th>
			<th field="account" sortable="true" align="center" width="10%">会员账号</th>
			<th field="nickname" sortable="true" align="center" width="10%">昵称</th>
			<th field="registertime" sortable="true" align="center" width="20%">注册时间</th>
			<th field="cellphone" sortable="true" align="center" width="10%">手机号码</th>
			<th field="email" sortable="true" align="center" width="10%">邮箱</th>
			<th field="sign" sortable="true" align="center" width="10%">个性签名</th>
			<th field="addtime" sortable="true" align="center" width="20%">加群时间</th>
		</tr>
	</thead>
</table>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/group/groupmemberList.do?primarykey=${groupid}',
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