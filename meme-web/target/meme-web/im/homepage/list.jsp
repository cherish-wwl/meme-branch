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
			<th field="account" sortable="true" align="center" width="10%">会员账号</th>
			<th field="nickname" sortable="true" align="center" width="10%">会员昵称</th>
			<th field="mtypeText" sortable="true" align="center" width="10%">会员类型</th>
			<th field="title" sortable="true" align="center" width="10%">标题</th>
			<th field="summary" sortable="true" align="center" width="10%">描述</th>
			<th field="cover" sortable="true" align="center" width="10%">封面</th>
			<th field="cover_href" sortable="true" align="center" width="10%">封面链接</th>
			<th field="file" sortable="true" align="center" width="10%">文件</th>
			<th field="file_type" data-options="formatter: function(value,row,index){if(value==1) return '图片';if(value==2) return '音频';if(value==3) return '视频';if(value==4) return '图文';}" sortable="true" align="center" width="10%">文件类型</th>
			<th field="content" sortable="true" align="center" width="10%">图文内容</th>
			<th field="typeText" sortable="true" align="center" width="10%">所属栏目</th>
			<th field="custom_column" sortable="true" align="center" width="10%">自定义类目</th>
			<th field="source" data-options="formatter: function(value,row,index){if(value==1) return '主页上传';if(value==2) return '网站添加';}" sortable="true" align="center" width="10%">文件来源</th>
			<th field="remarks" sortable="true" align="center" width="10%">备注</th>
			<th field="insert_date" sortable="true" align="center" width="10%">发布时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			会员昵称或账号：<input class="easyui-textbox" type="text" id="member_str" name="member_str"/>&nbsp;&nbsp;
			文件类型：<select id="file_type" name="file_type">
						<option value="">请选择</option>
						<option value="1">图片</option>
						<option value="2">音频</option>
						<option value="3">视频</option>
						<option value="4">图文</option>
					</select>
			&nbsp;&nbsp;
			文件来源：<select id="source" name="source">
						<option value="">请选择</option>
						<option value="1">主页上传</option>
						<option value="2">网站添加</option>
					</select>
			所属栏目：<express:SelectTag id="type" name="type" type="select" dictGroupCode="HOMEPAGE_TYPE" defaultOption="请选择" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			发布时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate"/>至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>/im/homepage/editView.do','<%=basePath%>/im/homepage/edit.do',{'title':'修改数据'})" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>/im/homepage/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="#" onclick="viewFile()" class="easyui-linkbutton" iconCls="icon-search">查看文件</a>
		<a href="#" onclick="openCover()" class="easyui-linkbutton" iconCls="icon-search">查看封面</a>
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
        url: '<%=basePath%>im/homepage/list.do?part=${part}',
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

function viewFile(){
	var row = $('#grid').datagrid('getSelected');
	window.open(row.file);
}

function openCover(){
	var row = $('#grid').datagrid('getSelected');
	var cover = row.cover.split(";");
	for(var i=0;i<cover.length;i++){
		window.open(cover[i]);
	}
}

</script>