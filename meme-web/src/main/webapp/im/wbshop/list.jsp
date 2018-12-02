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
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			商品名称：<input class="easyui-textbox" type="text" id="productname" name="productname">
			&nbsp;&nbsp;
			商品描述：<input class="easyui-textbox" type="text" id="productintro" name="productintro">
			&nbsp;&nbsp;
			商品价格：<select name="state" id="state">
						<option value="0">大于</option>
						<option value="1">小于</option>
						<option value="2">等于</option>
					</select>
			 <input type="text" class="easyui-numberbox" id="productprice" name="productprice" precision="2"/>
			&nbsp;&nbsp;
			微商名称：<input class="easyui-textbox" type="text" id="member_str" name="member_str">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a onclick="del()" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="wbShopQuery()" class="easyui-linkbutton" iconCls="icon-search">查询微商商品</a>
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
        url: '<%=basePath%>im/wbshop/list.do',
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

function wbShopQuery(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var memberid = checkeds[0].memberid;
	var url='<%=basePath%>im/wbshop/wbShopQuery?memberid='+memberid;
	var winObj=$('#win');
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '微商商品查询：'+checkeds[0].nickname,
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

/**
 * 删除操作，需保证主键在第一列
 * @param grid
 * @param url 删除操作请求url
 */
function del(){
	var checkeds=$('#grid').datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0){
		$.messager.alert('提示','未选中相应行进行操作','error');
		return ;
	}
	var ids=new Array();
	for(var i=0;i<checkeds.length;i++){
		ids.push(checkeds[i]['productid']);
	}
	if(typeof ids =='undefined') return ;
	$.messager.confirm('提示', '再次确认是否删除选中记录？', function(state){
		if (state){
			$.ajax({
				url:'<%=basePath%>im/wbshop/delete.do',
				type:'post',
				dataType:'json',
				data:{'ids':ids},
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						grid.datagrid('reload');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
		}
	});
}
</script>