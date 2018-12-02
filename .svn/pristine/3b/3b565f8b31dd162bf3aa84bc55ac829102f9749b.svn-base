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
			<th field="contentid" sortable="true" align="center" checkbox="true" width="10%">内容编号</th>
			<th field="contentname" sortable="true" align="center" width="10%">内容标题</th>
			<th field="catname" sortable="true" align="center" width="10%">所属栏目分类</th>
			<th field="columnname" sortable="true" align="center" width="10%">所属栏目</th>
			<th field="memberid" sortable="true" align="center" width="10%">上传会员编号</th>
			<th field="account" sortable="true" align="center" width="10%">上传会员账号</th>
			<th field="author" sortable="true" align="center" width="10%">作者</th>
			<th field="sortno" sortable="true" align="center" width="10%">排序号</th>
			<th field="cover" sortable="false" align="center" width="10%">封面</th>
			<th field="url" sortable="false" align="center" width="10%">文件地址</th>
			<th field="lrc" sortable="false" align="center" width="10%">歌词文件地址</th>
			<th field="remark" sortable="true" align="center" width="10%">备注</th>
			<th field="upvote" sortable="true" align="center" width="10%">点赞数</th>
			<th field="downvote" sortable="true" align="center" width="10%">点踩数</th>
			<th field="addtime" sortable="true" align="center" width="10%">添加时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			<input class="easyui-textbox" type="text" id="searchKey" name="searchKey" data-options="prompt:'输入标题/内容/备注查找'">
			&nbsp;&nbsp;
			<input class="easyui-textbox" type="text" id="member_str" name="member_str" data-options="prompt:'输入会员账号/昵称查找'">
			&nbsp;&nbsp;
			<select id="catid" name="catid" class="form-control"></select>
			&nbsp;&nbsp;
			<select id="columnid" name="columnid" class="form-controll"></select>
			<script type="text/javascript">
			$.ajax({
				url: '<%=basePath%>im/column/category/getTree.do',
				type:'post',
				dataType:'json',
				success:function(data){
					if(data){
						var options=createTreeSelect(data,'catname','id','请选择栏目分类');
						$('#catid').append(options);
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
			$.ajax({
				url: '<%=basePath%>im/column/getTree.do',
				type:'post',
				dataType:'json',
				success:function(data){
					if(data){
						var options=createTreeSelect(data,'columnname','id','请选择栏目');
						$('#columnid').append(options);
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
			</script>
			&nbsp;&nbsp;
			添加时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate">至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate">
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
		</form>
	</div>
	<div style="">
		<a href="#" onclick="add($('#grid'),$('#win'),'<%=basePath%>im/content/edit.do','<%=basePath%>im/content/doAdd.do',{width:800,height:600,left:'20%',top:'5%'})" class="easyui-linkbutton" iconCls="icon-add">增加</a>
		<a href="#" onclick="modify($('#grid'),$('#win'),'<%=basePath%>im/content/edit.do','<%=basePath%>im/content/doEdit.do')" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
		<a onclick="del($('#grid'),'')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
</div>
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
/**
 * 重写查询方法
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
function del(){
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var ids=selectMulti(gridObj);
	if(null==ids || typeof ids == 'undefined' || ids.length<=0) return ;
	ids=new Array();
	for(var i=0;i<checkeds.length;i++){
		ids.push(checkeds[i].contentid);
	}
	var action='<%=basePath%>im/content/delete.do';
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
	var url="<%=basePath%>im/content/edit.do";
	var action="<%=basePath%>im/content/doEdit.do";
	var winObj=$('#win');
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var itemid=selectOne(gridObj);
	if(null==itemid || typeof itemid == 'undefined') return ;
	itemid=checkeds[0].contentid;
	winObj.find('iframe').attr('src',url+'?id='+itemid);
	winObj.dialog({
	    title: '编辑内容：'+checkeds[0].contentname,
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
        url: '<%=basePath%>im/content/list.do',
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