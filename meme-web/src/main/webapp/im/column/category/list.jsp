<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
</head>

<body>
<div>
<a class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增</a>
<a class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">编辑</a>
<a class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
<a onclick="$('#grid').treegrid('reload')" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
</div>
<table id="grid">
	<thead>
        <tr>
            <th field="catname" width="15%" align="left">栏目分类</th>
            <th field="columnname" width="15%" align="center">所属栏目</th>
            <!-- <th field="icon" width="20%" align="center" data-options="formatter: function(value,row,index){return '<img width=30 height=30 border=0 src='+value+'>';}">图标</th> -->
            <th field="icon" width="10%" align="center">图标</th>
            <th field="sortno" width="10%" align="center">排序号</th>
            <th field="url" width="10%" align="center">外链地址</th>
            <th field="stateText" width="10%" align="center">是否显示</th>
            <th field="tagText" width="10%" align="center">标签</th>
            <th field="description" width="10%" align="center">描述</th>
            <th field="addtime" width="10%" align="center">添加时间</th>
        </tr>
    </thead>
</table>
<div id="win">
<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
function add(){
	addByCallback($('#grid'),$('#win'),'<%=basePath%>im/column/category/edit.do','<%=basePath%>im/column/category/doAdd.do',function(){
		$('#grid').treegrid('reload');
	});
}
function edit(){
	var selected=$('#grid').treegrid('getSelected');
	if(typeof selected == 'undefined' || selected == null){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	modifyByCallback($('#grid'),$('#win'),'<%=basePath%>im/column/category/edit.do','<%=basePath%>im/column/category/doEdit.do',function(){
		$('#grid').treegrid('reload');
	});
}
function del(){
	var selected=$('#grid').treegrid('getSelected');
	if(typeof selected == 'undefined' || selected == null){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var ids=new Array();
	ids.push(selected.id);
	$.messager.confirm('提示', '再次确认是否删除选中记录？', function(state){
		if (state){
			$.ajax({
				url: '<%=basePath%>im/column/category/delete.do',
				type:'post',
				dataType:'json',
				data:{'ids':ids},
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						$('#grid').treegrid('reload');
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
$(function(){
	$('#grid').treegrid({
        url: '<%=basePath%>im/column/category/list.do',
        idField:'id',
        treeField:'catname',
        fitColumns:false
	});
});
</script>