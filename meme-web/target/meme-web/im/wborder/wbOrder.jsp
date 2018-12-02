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
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">

$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/wborder/list.do?memberid=${memberid}',
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