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
			<th field="id" sortable="true" align="center" width="8%" checkbox="true">id</th>
			<th field="orderid" sortable="true" align="center" width="20%">订单号</th>
			<th field="body" sortable="true" align="center" width="20%">订单描述</th>
			<th field="amount" sortable="true" align="center" width="15%">订单金额</th>
			<th field="nickname" sortable="true" align="center" width="15%">会员昵称</th>
			<th field="account" sortable="true" align="center" width="15%">会员账号</th>
			<th field="add_time" sortable="true" align="center" width="15%">结算截止日期</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
    <div>
		<a href="#" onclick="updateState()" class="easyui-linkbutton" iconCls="icon-undo">改为已处理</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">

$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/wbordersettlement/doWbOrderQuery.do?memberid=${memberid}&state=${state}',
        width:'auto',
        height:'auto',
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
       /*  pagination: true,
        pageSize:20, */
        singleSelect: false,
        striped: true,
        fitColumns: true,
        border:true
	});
});

function updateState(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var id = checkeds[0].id;
	alert(id);
	if(typeof id =='undefined') return ;
	$.ajax({
        url: '<%=basePath%>im/wbordersettlement/updateState',
        type:'post',
        dataType:'json',
        data:{'id':id,state:2},
        success:function(d){
            if(d.state==1){
                alert("操作成功");
                $('#grid').datagrid('reload');
            }else{
                alert(d.message);
            }
        },
        error:function(){
            alert('操作异常');
        }
    });
}
</script>