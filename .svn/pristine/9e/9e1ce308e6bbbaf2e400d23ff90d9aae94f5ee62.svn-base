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
			<th field="contentid" sortable="true" align="center" width="9%" checkbox="true">ID</th>
			<th field="cover" sortable="true" align="center" width="18%">图片</th>
			<th field="url" sortable="true" align="center" width="18%">图片链接</th>
			<th field="contentname" sortable="true" align="center" width="13%">电影名称</th>
			<th field="remark" sortable="true" align="center" width="13%">备注</th>
			<th field="dictitemname" sortable="true" align="center" width="13%">所属栏目分块</th>
			<th field="addtime" sortable="true" align="center" width="13%">添加时间</th>
			<th field="sortno" sortable="true" align="center" width="13%">排序号</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			<express:SelectTag id="sectioncode" type="select" dictGroupCode="VIDEO_COL" defaultOption="栏目分块类型" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			添加时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate">至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="javascript:add($('#grid'),$('#win'),'<%=basePath%>im/movie/editView.do?columnid=${columnid}','<%=basePath%>im/movie/add.do',{'title':'新增电影'});" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>im/movie/editView.do','<%=basePath%>im/movie/edit.do',{'title':'修改电影'})" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>im/movie/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">

/**
 * 查询操作
 * @param searchForm 查询表单ID属性
 * @param datagrid datagrid ID属性
 */
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

$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/movie/homelist.do?columnid=${columnid}',
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