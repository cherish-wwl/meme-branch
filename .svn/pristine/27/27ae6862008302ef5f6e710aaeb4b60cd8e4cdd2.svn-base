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
			<th field="orderid" sortable="true" align="center" width="10%">订单编号</th>
			<th field="productid" sortable="true" align="center" width="10%">商品编码</th>
			<th field="tradeno" sortable="true" align="center" width="10%">交易单号</th>
			<th field="subject" sortable="true" align="center" width="10%">订单信息</th>
			<th field="unit_price" sortable="true" align="center" width="10%">商品单价</th>
			<th field="product_count" sortable="true" align="center" width="10%">商品数量</th>
			<th field="exxpress_cost" sortable="true" align="center" width="10%">快递费用</th>
			<th field="amount" sortable="true" align="center" width="10%">订单金额</th>
			<th field="paytype" sortable="true" align="center" width="10%">支付方式</th>
			<th field="state" sortable="true" align="center" width="10%"
			data-options="formatter: function(value,row,index){if(value==0) return '未付款';if(value==1) return '待发货';if(value==2) return '待收货';if(value==3) return '已收货';}">发收货状态</th>
			<th field="memberid" sortable="true" align="center" width="10%">会员编号</th>
			<th field="account" sortable="true" align="center" width="10%">会员账号</th>
			<th field="nickname" sortable="true" align="center" width="10%">会员昵称</th>
			<th field="cellphone" sortable="true" align="center" width="10%">会员手机</th>
			<th field="body" sortable="true" align="center" width="10%">订单描述</th>
			<th field="addtime" sortable="true" align="center" width="10%">下单时间</th>
			<th field="paytime" sortable="true" align="center" width="10%">支付时间</th>
			<th field="wb_nickname" sortable="true" align="center" width="10%">微商名称</th>
			<th field="wb_account" sortable="true" align="center" width="10%">微商账号</th>
			<th field="wb_memberid" sortable="true" align="center" width="10%">微商编号</th>
			<th field="consignee" sortable="true" align="center" width="10%">收货人</th>
			<th field="receiving_telephone" sortable="true" align="center" width="10%">收货电话</th>
			<th field="receiving_address" sortable="true" align="center" width="10%">收货地址</th>
			<th field="deliver_time" sortable="true" align="center" width="10%">发货日期</th>
			<th field="collect_time" sortable="true" align="center" width="10%">收货日期</th>
			<th field="courier_number" sortable="true" align="center" width="10%">快递单号</th>
			<th field="carrier" sortable="true" align="center" width="10%">乘运商</th>
			<th field="r_state" sortable="true" align="center" width="10%" data-options="formatter: function(value,row,index){if(value==0) return '已申请';if(value==1) return '同意退货';if(value==2) return '不同意退货';if(value==3) return '退货发出';if(value==4) return '退货完成';}">退货处理状态</th>
			<th field="r_reason" sortable="true" align="center" width="10%">退货原因</th>
			<th field="handling_opinions" sortable="true" align="center" width="10%">处理意见</th>
			<th field="r_apply_time" sortable="true" align="center" width="10%">退货申请日期</th>
			<th field="r_complete_time" sortable="true" align="center" width="10%">完成退货日期</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			下单时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate">至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate">
			&nbsp;&nbsp;
			微商名称：<input class="easyui-textbox" type="text" id="member_str" name="member_str">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="wbOrderQuery()" class="easyui-linkbutton" iconCls="icon-search">查询微商订单</a>
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
function doSearch(formObj,gridObj){
	var paramString=formObj.serialize();
	if(null==paramString||paramString==''){
		gridObj.datagrid('reload');
	}else{
		var data=new Array();
		var params=paramString.split('&');
		for(var i=0;i<params.length;i++){
			var parm=params[i].split('=');
			data[i]=parm[0]+':"'+decodeURI(parm[1])+'"';
		}
		var parms='{'+data.toString()+'}';
		var d=eval('('+parms+')');
		d.startdate=$('input[name="startdate"]').val();
		d.enddate=$('input[name="enddate"]').val();
		gridObj.datagrid('loadData',{total: 0,rows:[]});
		gridObj.datagrid('load',d);
	}
}

function wbOrderQuery(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var memberid = checkeds[0].wb_memberid;
	var url='<%=basePath%>im/wborder/wbOrderQuery?memberid='+memberid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '微商订单查询：'+checkeds[0].wb_nickname,
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
        url: '<%=basePath%>im/wborder/list.do',
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