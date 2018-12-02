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
			<th field="nickname" sortable="true" align="center" width="20%">会员昵称</th>
			<th field="account" sortable="true" align="center" width="10%">会员账号</th>
			<th field="mtype" sortable="true" align="center" width="10%" data-options="formatter: function(value,row,index){if(value==3) return '微商会员';else return '普通会员';}">会员类型</th>
			<th field="balance" sortable="true" align="center" width="10%">余额</th>
			<th field="deposit" sortable="true" align="center" width="10%">押金</th>
			<th field="payaccount" sortable="true" align="center" width="10%">支付账户</th>
			<th field="paynickname" sortable="true" align="center" width="10%">支付用户名</th>
			<th field="unwithdrawals" sortable="true" align="center" width="10%">是否有未提现申请</th>
			<th field="name" sortable="true" align="center" width="10%">真实姓名</th>
			<th field="idNumber" sortable="true" align="center" width="10%">身份证号码</th>
			<th field="bankCard" sortable="true" align="center" width="10%">银行卡号码</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			会员账号或昵称：<input class="easyui-textbox" type="text" id="account" name="account">
			&nbsp;&nbsp;
			支付账户或支付用户名：<input class="easyui-textbox" type="text" id="payaccount" name="payaccount">
			&nbsp;&nbsp;
			会员类型：<select id="mtype" name="mtype">
						<option value="">请选择</option>
						<option value="1">普通会员</option>
						<option value="2">微商</option>
					</select>
			&nbsp;&nbsp;
			是否有未提现申请：<select id="unwithdrawals" name="unwithdrawals">
						<option value="">请选择</option>
						<option value="是">是</option>
						<option value="否">否</option>
					</select>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div>
			<a href="#" onclick="rechargeQuery()" class="easyui-linkbutton" iconCls="icon-search">充值记录查询</a>
			<a href="#" onclick="withdrawalsQuery()" class="easyui-linkbutton" iconCls="icon-search">提现记录查询</a>
			<a href="#" onclick="wbOrderQuery()" class="easyui-linkbutton" iconCls="icon-search">查询订单记录</a>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/member/account/list.do',
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
function rechargeQuery(){
	var memberid=selectOne($('#grid'));
	if(typeof memberid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>im/member/account/rechargeQuery.do?memberid='+memberid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '充值记录查询：'+checkeds[0].nickname,
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
function withdrawalsQuery(){
	var memberid=selectOne($('#grid'));
	if(typeof memberid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>im/member/account/withdrawalsQuery.do?memberid='+memberid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '提现记录查询：'+checkeds[0].nickname,
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

function wbOrderQuery(){
	var memberid=selectOne($('#grid'));
	if(typeof memberid=='undefined') return ;
	var checkeds=$('#grid').datagrid('getChecked');
	
	var url='<%=basePath%>im/member/wbOrderQuery?memberid='+memberid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '订单记录查询：'+checkeds[0].nickname,
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
</script>