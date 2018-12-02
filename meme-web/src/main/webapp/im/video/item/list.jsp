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
			<th field="itemid" sortable="true" align="center" checkbox="true" width="10%">视频编号</th>
			<th field="itemname" sortable="true" align="center" width="10%">视频名称</th>
			<th field="author" sortable="true" align="center" width="10%">作者</th>
			<th field="catname" sortable="true" align="center" width="10%">所属分类</th>
			<th field="cover" sortable="false" align="center" width="15%">封面地址</th>
			<th field="url" sortable="false" align="center" width="10%">视频地址</th>
			<th field="sortno" sortable="true" align="center" width="10%">排序号</th>
			<th field="isnewText" sortable="false" align="center" width="10%">最新大片</th>
			<th field="isrecommendText" sortable="false" align="center" width="10%">本站推荐</th>
			<th field="isanchorText" sortable="false" align="center" width="10%">主播自媒体</th>
			<th field="publishtime" sortable="true" align="center" width="10%">发布时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			<express:SelectTag id="isnew" type="select" dictGroupCode="SF" defaultOption="最新大片" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<express:SelectTag id="isrecommend" type="select" dictGroupCode="SF" defaultOption="本站推荐" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<express:SelectTag id="isanchor" type="select" dictGroupCode="SF" defaultOption="主播自媒体" cssClass="easyui-combobox"/>
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="add($('#grid'),$('#win'),'<%=basePath%>im/video/item/edit.do','<%=basePath%>im/video/item/doAdd.do')" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="#" onclick="modify($('#grid'),$('#win'),'<%=basePath%>im/video/item/edit.do','<%=basePath%>im/video/item/doEdit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a onclick="del($('#grid'),'')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function del(){
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var ids=selectMulti(gridObj);
	if(null==ids || typeof ids == 'undefined' || ids.length<=0) return ;
	ids=new Array();
	for(var i=0;i<checkeds.length;i++){
		ids.push(checkeds[i].itemid);
	}
	var action='<%=basePath%>im/video/item/delete.do';
	$.messager.confirm('提示', '请再次确认是否删除选中记录（一经删除数据将无法恢复）', function(state){
		if (state){
			$.ajax({
				url: action,
				type: 'post',
				dataType: 'json',
				data:{'ids':ids},
				success: function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
					gridObj.datagrid('reload');
				},
				error: function(){
					$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
				}
			});
		}
	});
}
function modify(){
	var url="<%=basePath%>im/video/item/edit.do";
	var action="<%=basePath%>im/video/item/doEdit.do";
	var winObj=$('#win');
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var itemid=selectOne(gridObj);
	if(null==itemid || typeof itemid == 'undefined') return ;
	itemid=checkeds[0].itemid;
	winObj.find('iframe').attr('src',url+'?id='+itemid);
	winObj.dialog({
	    title: '编辑视频：'+checkeds[0].itemname,
	    width: 800,
	    height: 600,
	    closed: false,
	    modal: true,
	    maximizable: true,
	    resizable: true,
	    collapsible: true,
	    minimizable: false,
	    buttons:[
			{
				text:'编辑',
				iconCls:'icon-save',
				handler:function(){
					var formObj=winObj.find('iframe').contents().find('form');
					var validateObj=winObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var data=formObj.serialize().replace(/\+/g," ");
						$.ajax({
							url: action,
							type: 'post',
							dataType: 'json',
							data: data,
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									winObj.dialog('close');
									gridObj.datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error: function(e){
								$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
}
$(function(){
	$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/video/item/list.do',
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