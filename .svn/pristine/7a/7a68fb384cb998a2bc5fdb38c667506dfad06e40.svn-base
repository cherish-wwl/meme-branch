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
			<th field="productid" sortable="true" align="center" width="15%">商品编号</th>
			<th field="subject" sortable="true" align="center" width="15%">商品名称</th>
			<th field="body" sortable="true" align="center" width="15%">商品描述</th>
			<th field="amount" sortable="true" align="center" width="15%">商品金额</th>
			<th field="file" sortable="true" align="center" width="15%">商品文件</th>
			<th field="memberid" sortable="true" align="center" width="15%">微商编号</th>
			<th field="nickname" sortable="true" align="center" width="15%">微商名称</th>
			<th field="account" sortable="true" align="center" width="15%">微商账号</th>
			<th field="add_time" sortable="true" align="center" width="15%">添加时间</th>
		</tr>
	</thead>
</table>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/wbshop/list.do?memberid=${memberid}',
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