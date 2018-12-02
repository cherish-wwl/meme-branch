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
            <th field="id" width="13%" align="center">栏目块编号</th>
            <th field="sectionname" width="15%" align="left">栏目块名称</th>
            <th field="sectioncode" width="15%" align="center">栏目块编码</th>
            <th field="columnname" width="10%" align="center">所属栏目</th>
            <!-- <th field="icon" width="20%" align="center" data-options="formatter: function(value,row,index){return '<img width=30 height=30 border=0 src='+value+'>';}">图标</th> -->
            <th field="icon" width="7%" align="center">图标</th>
            <th field="sortno" width="7%" align="center">排序号</th>
            <th field="sourceapi" width="10%" align="center">接口地址</th>
            <th field="moreurl" width="10%" align="center">更多按钮链接地址</th>
            <th field="ispaginationText" width="7%" align="center">是否分页</th>
            <th field="tagText" width="10%" align="center">标签</th>
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
	addByCallback($('#grid'),$('#win'),'<%=basePath%>im/column/section/edit.do','<%=basePath%>im/column/section/doAdd.do',function(){
		$('#grid').treegrid('reload');
	});
}
function edit(){
	var selected=$('#grid').treegrid('getSelected');
	if(typeof selected == 'undefined' || selected == null){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	modifyByCallback($('#grid'),$('#win'),'<%=basePath%>im/column/section/edit.do','<%=basePath%>im/column/section/doEdit.do',function(){
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
				url: '<%=basePath%>im/column/section/delete.do',
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
        url: '<%=basePath%>im/column/section/list.do',
        idField:'id',
        treeField:'sectionname',
        fitColumns:false
	});
});
</script>