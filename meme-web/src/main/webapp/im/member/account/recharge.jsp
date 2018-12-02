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
			<th field="money" sortable="true" align="center" width="25%">充值金额</th>
			<th field="typeText" align="center" width="25%">充值类型</th>
			<th field="datetime" sortable="true" align="center" width="25%">充值时间</th>
			<th field="orderid" sortable="true" align="center" width="25%">关联订单号</th>
		</tr>
	</thead>
</table>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/account/doRechargeQuery.do?memberid=${memberid}',
        width:'auto',
        height:'auto',
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
        //pagination: true,
        //pageSize:20,
        singleSelect: false,
        striped: true,
        fitColumns: true,
        border:true
	});
});
</script>