<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
<style type="text/css">
ul{
	text-decoration: none;
	list-style-type: none;
}
.left-ul{
	width:100%;
	margin-top:0px;
	margin-left:-40px;
}
.left-ul li {
	padding:10px 0px 10px 20px;
	cursor: pointer;
	border-bottom:0.5px dashed gray;
}
.left-ul li:hover{
	background: #ffe48d;
}
.left-ul li.active{
	background: #ffe48d;
}
.left-ul img {
	vertical-align: middle
}
.tree .tree-title{height: 25px;line-height: 25px;}
.tree .tree-node-selected{height: 25px;line-height: 25px;}
.tree .tree-node{height: 25px;line-height: 25px;}
.tree .tree-file{margin-top:3px;}
.tree li span label{cursor:pointer;}
.tree a{text-decoration:none;}
.tree li a:link{color:black;}
.tree li a:visited{color:black;}
.tree li a:hover{color:black;}
.tree li a:active{color:black;}
</style>
</head>

<body>
<div id="tree">
</div>
<div id="win">
	<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
<div id="m_menu" class="easyui-menu" style="width:150px;">
	<div id="addBrotherMenu" data-options="name:1,iconCls:'icon-add'">添加同级部门</div>
	<div id="addChildMenu" data-options="name:2,iconCls:'icon-add'">添加下级部门</div>
	<div id="modifyMenu" data-options="name:3,iconCls:'icon-edit'">修改部门</div>
	<div id="delMenu" data-options="name:4,iconCls:'icon-remove'">删除部门</div>
</div>
</body>
</html>
<script type="text/javascript">
function addBrother(node){
	var options={'title':'新增同级部门','width':'450','height':'300'};
	var pid=node.attributes.pid;
	var url='<%=basePath%>system/department/editView.do?pid='+pid+'&organid=${organid}';
	var action='<%=basePath%>system/department/add.do';
	edit($('#tree'),$('#win'),url,action,options);
}
function addChild(node){
	var options={'title':'新增下级部门','width':'450','height':'300'};
	var pid=node.id;
	var url='<%=basePath%>system/department/editView.do?pid='+pid+'&organid=${organid}';
	var action='<%=basePath%>system/department/add.do';
	edit($('#tree'),$('#win'),url,action,options);
}
function modify(node){
	var options={'title':'修改部门:'+node.text,'width':'450','height':'300'};
	var url='<%=basePath%>system/department/editView.do?id='+node.id;
	var action='<%=basePath%>system/department/edit.do';
	edit($('#tree'),$('#win'),url,action,options);
}
function menuHandler(menu, type) {
	var node=$('#tree').tree('getSelected');
    switch (type) {
        case 1:
        	addBrother(node);
            break;
        case 2:
        	addChild(node);
            break;
        case 3:
        	modify(node);
            break;
        case 4:
        	var ids=new Array();
        	ids.push(node.id);
        	$.messager.confirm('提示', '再次确认是否删除选中部门及其下属子部门？', function(state){
        		if (state){
        			$.ajax({
        				url: '<%=basePath%>system/department/delete.do',
        				type:'post',
        				dataType:'json',
        				data:{'ids':ids,'organid':'${organid}'},
        				success:function(data){
        					if(data.state==1){
        						$.messager.alert('提示',data.message,'info');
        						$('#tree').tree('reload');
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
            break;
    }
}
$(function(){
	$('#m_menu').menu({
        onClick:function (item) {
        	menuHandler(this, item.name);
        }
    });
	$('#tree').tree({
		lines:true,
	    url: '<%=basePath%>system/department/list.do',
	    queryParams: {'organid':'${organid}'},
	    loadFilter: function (data) {
	    	if(!(data&&data.length>0)){
	    		var options={'title':'新增部门','width':500};
	    		var pid=0;
	    		var url='<%=basePath%>system/department/editView.do?pid='+pid+'&organid=${organid}';
	    		var action='<%=basePath%>system/department/add.do';
	    		edit($('#tree'),$('#win'),url,action,options);
	    	}
	    	return data;
	    },
	    onContextMenu: function(e,node){
	    	e.preventDefault();
	    	$('#tree').tree('select', node.target);
	    	$('#m_menu').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
	    }
	});
});
</script>