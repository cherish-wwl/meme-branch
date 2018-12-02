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
			<th field="money" sortable="true" align="center" width="20%">提现金额</th>
			<th field="typeText" align="center" width="20%">提现类型</th>
			<th field="datetime" sortable="true" align="center" width="20%">提现时间</th>
			<th field="handletime" sortable="true" align="center" width="20%">处理时间</th>
			<th field="stateText" sortable="true" align="center" width="20%">处理状态</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
    <div>
			<a href="#" onclick="doWithdrawals()" class="easyui-linkbutton" iconCls="icon-undo">改为已处理</a>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/account/doWithdrawalsQuery.do?memberid=${memberid}',
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

function doWithdrawals(){
	var id = selectOne($('#grid'));
	if(typeof id =='undefined') return ;
	$.ajax({
        url: '<%=basePath%>im/member/account/doWithdrawals',
        type:'post',
        dataType:'json',
        data:{'id':id,memberid:'${memberid}'},
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