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
			<th field="memberid" sortable="true" align="center" width="8%" checkbox="true">memberid</th>
			<th field="account" sortable="true" align="center" width="15%">微商名称</th>
			<th field="nickname" sortable="true" align="center" width="15%">微商账号</th>
			<th field="un_settlement" sortable="true" align="center" width="10%">是否有未结算订单</th>
			<th field="settlement_deadline" sortable="true" align="center" width="10%">结算截止日期</th>
			<th field="amount" sortable="true" align="center" width="10%">结算总金额</th>
			<th field="last_settlement_time" sortable="true" align="center" width="10%">上次结算日期</th>
			<th field="un_rg" sortable="true" align="center" width="10%">是否有退货</th>
			<th field="r_apply_time" sortable="true" align="center" width="10%">退货申请日期</th>
			<th field="r_complete_time" sortable="true" align="center" width="10%">上次退货完成日期</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			微商名称：<input class="easyui-textbox" type="text" id="member_str" name="member_str">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="wbOrderQuery('为结算',1)" class="easyui-linkbutton" iconCls="icon-search">查询未结算订单</a>
		<a href="#" onclick="wbOrderQuery('已结算',2)" class="easyui-linkbutton" iconCls="icon-search">查询已结算订单</a>
		<a href="#" onclick="wbOrderQuery('未处理退货',3)" class="easyui-linkbutton" iconCls="icon-search">查询未处理退货</a>
		<a href="#" onclick="wbOrderQuery('已退货',4)" class="easyui-linkbutton" iconCls="icon-search">查询已退货订单</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
<%-- function fix(){
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var orderid=selectOne(gridObj);
	if(null==orderid || typeof orderid == 'undefined') return ;
	orderid=checkeds[0].orderid;
	var action='<%=basePath%>pay/alipay/fix.do';
	$.ajax({
		url: action,
		type: 'post',
		dataType: 'json',
		data: {'orderid':orderid},
		success: function(data){
			if(data.state==1){
				$.messager.alert('提示',data.message,'info');
				gridObj.datagrid('reload');
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
		},
		error: function(e){
			$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
		}
	});
} --%>

function wbOrderQuery(text,state){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var memberid = checkeds[0].memberid;
	var url='<%=basePath%>im/wbordersettlement/wbOrderQuery?memberid='+memberid+'&state='+state;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '微商'+text+'订单查询：'+checkeds[0].nickname,
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

$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/wbordersettlement/list.do',
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