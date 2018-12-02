<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
</head>

<body onclick="">
	<input type="hidden" id="type" name="type" value="${movieType}"
		class="text" maxlength="100">
		<input type="hidden" id="columnid" name="columnid" value="${columnid}"
		class="text" maxlength="100">

		<table id="grid" toolbar="#toolbar">
		</table>
		<div id="toolbar">
			<div style="">
				<a href="#" onclick="add()" class="easyui-linkbutton"
					iconCls="icon-add">增加</a> <a href="#" onclick="javascript:edit(datagrid.datagrid('getRowIndex',datagrid.datagrid('getSelections')[0]))" class="easyui-linkbutton"
					iconCls="icon-edit">修改</a> <a onclick="delMovie()"
					class="easyui-linkbutton" iconCls="icon-remove">删除</a>
				<a onclick="cancelMovie()" style="display: none;"  
					class="easyui-linkbutton cancelButton" iconCls="icon-cancel">取消</a>
			</div>
		</div>
		<div id="win">
			<iframe id="frame" scrolling="yes" frameborder="0" src=""
				style="width: 100%; height: 99%;"></iframe>
		</div>
</body>
</html>
<script type="text/javascript">
var type;
var datagrid;  

$(function(){
	type=$("#type").val();
	columnid=$("#columnid").val();
	datagrid = $('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/movie/list.do',
        queryParams: {
        	type: $("#type").val()
    	},
        width:'auto',
        height:'auto',
        onLoadSuccess:function(data){
        	console.log(data);
        	type=$("#type").val();
        },
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
        pagination: true,
        pageSize:20,
        singleSelect: false,
        striped: true,
        fitColumns: true,
        border:true ,
        onDblClickRow:function(index, row){
        	edit(index);
        },
        columns: [[//显示的列
        	 { field: 'contentid', title: '电影编号', checkbox: true},
        	 { field: 'columnid', title: '所属项目编号', hidden: true},
        	 { field: 'catid', title: '电影类型', hidden: true},
        	 
             { field: 'contentname', title: '电影名称', width: 100, 
                 editor: { type: 'validatebox', options: { required: true} }
             },
              { field: 'content', title: '内容简介', width: 100,
                  editor: { type: 'validatebox', options: { required: true} }
              },
               { field: 'cover', title: '封面地址', width: 100,
                   editor: { type: 'validatebox', options: { required: true} }
               }, 
               { field: 'url', title: '视频地址', width: 100, 
                   editor: { type: 'validatebox', options: { required: true} }
               },
                { field: 'account', title: '会员账号', width: 100,
                    editor: { type: 'validatebox', options: { required: true} }
                },
                { field: 'upvote', title: '赞赏数', width: 100,sortable: true,},
                { field: 'addtime', title: '录入时间', width: 100,sortable: true,}
               ]]
	});
});


function cancelEdit(){
	$('#grid').datagrid("endEdit");
}


function checkData(rows){
	console.log("inCheck!");
	if ((rows.contentname == null || rows.contentname == undefined || rows.contentname == "")
				&& (rows.content == null || rows.content == undefined || rows.content == "")
				&&(rows.cover == null || rows.cover == undefined || rows.cover == "")
				&& (rows.url == null || rows.url == undefined || rows.url == "")
				&& (rows.remark == null || rows.remark == undefined || rows.remark == "")){
		return false;
	}else{
		return true;
	}

}

	var editRow = undefined; //定义全局变量：当前编辑的行
	function edit(index) {
		var rows;
		console.log(1);
		//先判断是否有开启编辑的行，如果有则把开启编辑的那行结束编辑
	    if (editRow != undefined) {
	    	console.log(2);
	    	console.log(editRow);
	    	$('#grid').datagrid("endEdit", editRow);
	    	editRow = undefined;
	    }
	    console.log(3);
	    if (editRow == undefined) {
	    editRow=index;	
	    $('.cancelButton').show();	
		$('#grid').datagrid("beginEdit", index);
		var editors = $('#grid').datagrid('getEditors', index);
		for (var i = 0, len = editors.length; i < len; i++) {
			var editor = editors[i];
			$(editor.target).bind('keyup',function(e) {
								var code = e.keyCode || e.which;
								if (code == 13) {
									$('#grid').datagrid('endEdit', index);
									rows = $('#grid').datagrid("getChanges",
											"updated");
									if(rows[0]==undefined){
										 $('.cancelButton').hide();	
										 return;
									}
					    	        var acc = rows[0].account;
									if (checkData(rows[0])) {
										$.ajax({
							url : '<%=basePath%>im/movie/doEdit.do?account'+acc,
							type: 'post',
							dataType: 'json',
							data:rows[0],
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
								editRow = undefined;
								$('.cancelButton').hide();	
								$('#grid').datagrid('reload');
							},
							error: function(){
								editRow = undefined;
								$('.cancelButton').hide();	
								$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
							}
						});
					}else{
	        	    	console.log("aaa");
	        	    	$.messager.alert('提示',"有空项！",'error');
	        	    	editRow = undefined;
	        	    	$('.cancelButton').hide();	
	        	    	$('#grid').datagrid('reload');
	        	    }
								}
								
	    	});
	    	}
	    }
}


function add(){
	var rows;
	//添加时先判断是否有开启编辑的行，如果有则把开启编辑的那行结束编辑
    if (editRow != undefined) {
    	$('#grid').datagrid("endEdit", editRow);
    	if(editRow!=-1)
    	editRow = undefined;
    }
    //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
    if (editRow == undefined) {
    	$('.cancelButton').show();
    	$('#grid').datagrid("insertRow", {
            index: 0, // index start with 0
            row: {}
        });
        //将新插入的那一行开启编辑状态
        $('#grid').datagrid("beginEdit", 0);
        editRow = -1;
       	var editors = $('#grid').datagrid('getEditors', 0);
    	for (var i = 0, len = editors.length; i < len; i++) {
    	var editor = editors[i];
    	$(editor.target).bind('keyup', function (e) {
    	    var code = e.keyCode || e.which;
    	    if (code == 13) {
    	        $('#grid').datagrid('endEdit', 0);
    	        rows = $('#grid').datagrid("getChanges","inserted");
    	        console.log("xxxxx"+rows);
    	        rows[0].catid=type;
    	        rows[0].columnid=columnid;
    	        var acc = rows[0].account;
    	        console.log(rows);
    	        if(checkData(rows[0])) {
    				$.ajax({
    					url: '<%=basePath%>im/movie/doAdd.do?account='+acc,
    					type: 'post',
    					dataType: 'json',
    					data:rows[0],
    					success: function(data){
    						if(data.state==1){
    							$.messager.alert('提示',data.message,'info');
    						}else if(data.state==0){
    							$.messager.alert('提示',data.message,'error');
    						}
    						editRow = undefined;
    						$('.cancelButton').hide();
    						$('#grid').datagrid('reload');
    					},
    					error: function(){
    						editRow = undefined;
    						$('.cancelButton').hide();
    						$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
    					}
    				});
        	    }else{
        	    	console.log("aaa");
        	    	$.messager.alert('提示',"有空项！",'error');
        	    }
    	    } 
    	});
    	}
	}
    
}


function delMovie(){
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var ids=selectMulti(gridObj);
	console.log(checkeds);
	if(null==ids || typeof ids == 'undefined' || ids.length<=0) return ;
	ids=new Array();
	for(var i=0;i<checkeds.length;i++){
		ids.push(checkeds[i].contentid);
	}
	console.log(ids);
	var action='<%=basePath%>im/movie/delete.do';
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

function cancelMovie(){
	editRow = undefined;
	$('.cancelButton').hide();
	$('#grid').datagrid('reload');
}
</script>