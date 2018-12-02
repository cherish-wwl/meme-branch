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
			<th field="orderid"  align="center" width="15%">订单号</th>
			<th field="subject" align="center" width="15%">订单信息</th>
			<th field="addtime"  align="center" width="15%">下单日期</th>
			<th field="courier_number"  align="center" width="15%">快递单号</th>
			<th field="carrier"  align="center" width="15%">承运商</th>
			<th field="nickname"  align="center" width="15%">商品来源</th>
			<th field="state_text"  align="center" width="15%">订单状态</th>
		</tr>
	</thead>
</table>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/doWbOrderQuery.do?memberid=${memberid}',
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