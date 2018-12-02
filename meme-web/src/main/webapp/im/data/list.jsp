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
			<th field="title" sortable="true" align="center" width="10%">标题</th>
			<th field="summary" sortable="true" align="center" width="10%">描述</th>
			<th field="cover" sortable="true" align="center" width="10%">封面</th>
			<th field="cover_href" sortable="true" align="center" width="10%">封面链接</th>
			<th field="file" sortable="true" align="center" width="10%">文件</th>
			<th field="file_type" data-options="formatter: function(value,row,index){if(value==1) return '图片';if(value==2) return '音频';if(value==3) return '视频';if(value==4) return '图文';}" sortable="true" align="center" width="10%">文件类型</th>
			<th field="file_original_link" sortable="true" align="center" width="10%">文件原始链接</th>
			<th field="source" data-options="formatter: function(value,row,index){if(value==1) return '糗事热图';if(value==2) return '糗事视频';if(value==3) return '头条视频';if(value==4) return '头条八卦';if(value==5) return '头条社会';if(value==6) return '头条健康';if(value==7) return '头条笑话';if(value==8) return '头条人文';if(value==9) return '腾讯娱乐';if(value==10) return '腾讯社会';if(value==11) return '腾讯视频';if(value==12) return '腾讯笑话';}" sortable="true" align="center" width="10%">文件来源</th>
			<th field="remarks" sortable="true" align="center" width="10%">备注</th>
			<th field="content" sortable="true" align="center" width="10%">图文内容</th>
			<th field="album" sortable="true" align="center" width="10%">所属专辑</th>
			<th field="custom_column" sortable="true" align="center" width="10%">自定义类目</th>
			<th field="type" data-options="formatter: function(value,row,index){if(value==1) return '爬虫采集';if(value==2) return '本站搜集';}" sortable="true" align="center" width="10%">采集类型</th>
			<th field="insert_date" sortable="true" align="center" width="10%">新增时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			文件类型：<select id="file_type" name="file_type">
						<option value="">请选择</option>
						<option value="1">图片</option>
						<option value="2">音频</option>
						<option value="3">视频</option>
						<option value="4">图文</option>
					</select>
			&nbsp;&nbsp;
			文件来源：<express:SelectTag id="source" name="source" type="select" dictGroupCode="CRAWLER_DATA_SOURCE" defaultOption="请选择" cssClass="easyui-combobox"/>
			采集类型：<select id="type" name="type">
						<option value="">请选择</option>
						<option value="1">爬虫采集</option>
						<option value="2">本站搜集</option>
					</select>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div>
		<a href="javascript:add($('#grid'),$('#win'),'<%=basePath%>/im/data/editView.do','<%=basePath%>/im/data/add.do',{'title':'新增数据'});" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>/im/data/editView.do','<%=basePath%>/im/data/edit.do',{'title':'修改数据'})" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a href="javascript:del($('#grid'),'<%=basePath%>/im/data/delete.do')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		<a href="#" onclick="viewFile()" class="easyui-linkbutton" iconCls="icon-search">查看文件</a>
		<a href="#" onclick="openHref()" class="easyui-linkbutton" iconCls="icon-search">打开来源地址</a>
		<a href="#" onclick="openCover()" class="easyui-linkbutton" iconCls="icon-search">查看封面</a>
		<a href="#" onclick="addLm()" class="easyui-linkbutton" iconCls="icon-add">添加到栏目</a>
		<a href="javascript:modify($('#grid'),$('#win'),'<%=basePath%>/im/data/addHyzy.do','<%=basePath%>/im/data/doAddHyzy.do',{'title':'添加到会员主页'})" class="easyui-linkbutton" iconCls="icon-add">添加到会员主页</a>
		<a href="#" onclick="crawler()" class="easyui-linkbutton" iconCls="icon-add">开始爬取数据</a>
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
        url: '<%=basePath%>im/data/list.do',
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

function viewFile(){
	var row = $('#grid').datagrid('getSelected');
	window.open(row.file);
}

function openHref(){
	var row = $('#grid').datagrid('getSelected');
	window.open(row.file_original_link);
}

function openCover(){
	var row = $('#grid').datagrid('getSelected');
	var cover = row.cover.split(";");
	for(var i=0;i<cover.length;i++){
		window.open(cover[i]);
	}
}

function crawler(){
	$.get("<%=basePath%>im/data/crawler.do",function(result){
		alert(result.message);
	});
}
</script>