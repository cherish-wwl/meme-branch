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

<body class="easyui-layout">
	<div id="platformContainer" data-options="region:'west',split:true,collapsible:false" title="模块切换" style="width:20%;">
		<ul id="platformList" class="left-ul">
			
		</ul>
	</div>
	<div id="menuContainer" data-options="region:'center',split:true,collapsible:false" title="菜单管理" style="width:50%;">
		<div id="menuList">
			
		</div>
	</div>
	<div id="operationContainer" data-options="region:'east',split:true,collapsible:false" title="操作管理" style="width:30%;">
		<a href="javascript:addOperation();" class="easyui-linkbutton">添加操作</a>
		<div id="operationList">
			
		</div>
	</div>
	<div id="m_menu" class="easyui-menu" style="width:150px;">
		<div id="addBrotherMenu" data-options="name:1,iconCls:'icon-add'">添加同级菜单</div>
		<div id="addChildMenu" data-options="name:2,iconCls:'icon-add'">添加下级菜单</div>
		<div id="modifyMenu" data-options="name:3,iconCls:'icon-edit'">修改菜单</div>
		<div id="delMenu" data-options="name:4,iconCls:'icon-remove'">删除菜单</div>
	</div>
	<div id="operation_menu" class="easyui-menu" style="width:150px;">
		<%--
		<div id="addBrotherMenu" data-options="name:5,iconCls:'icon-add'">添加操作</div>
		--%>
		<div id="addBrotherMenu" data-options="name:6,iconCls:'icon-edit'">修改操作</div>
		<div id="addBrotherMenu" data-options="name:7,iconCls:'icon-remove'">删除操作</div>
	</div>
	<div id="win">
		<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
function switchPlatform(pfid){
	var params={'platformid':pfid};
	loadMenuTree($('#menuList'),$('#m_menu'),'<%=basePath%>system/menu/getMenuTree.do',params);
	$('#platformList').children('.active').removeClass('active');
	$('#pli'+pfid).addClass('active');
}
function addBrother(node){
	var options={'title':'新增同级菜单','width':650};
	var pid=node.attributes.pid;
	var hid=node.attributes.hid;
	var platformid=node.attributes.platformid;
	var url='<%=basePath%>system/menu/editView.do?pid='+pid+'&hid='+hid+'&platformid='+platformid;
	var action='<%=basePath%>system/menu/add.do';
	edit($('#menuList'),$('#win'),url,action,options);
}
function addChild(node){
	var options={'title':'新增下级菜单','width':650};
	var pid=node.id;
	var hid=node.attributes.hid;
	if(hid==0) hid=node.id;
	var platformid=node.attributes.platformid;
	var url='<%=basePath%>system/menu/editView.do?pid='+pid+'&hid='+hid+'&platformid='+platformid;
	var action='<%=basePath%>system/menu/add.do';
	edit($('#menuList'),$('#win'),url,action,options);
}
function modify(node){
	var options={'title':'修改菜单:'+node.text,'width':650};
	var url='<%=basePath%>system/menu/editView.do?id='+node.id;
	var action='<%=basePath%>system/menu/edit.do';
	edit($('#menuList'),$('#win'),url,action,options);
}
/**
 * 此处node为菜单树选中节点
 */
function addOperation(){
	var node=$('#menuList').tree('getSelected');
	if(node==null){
		$.messager.alert('提示','选中菜单节点再添加操作按钮','error');
		return ;
	}
	var url='<%=basePath%>system/operation/editView.do?menuid='+node.id+'&platformid='+node.attributes.platformid;
	var action='<%=basePath%>system/operation/add.do';
	var options={'title':'添加操作'};
	edit($('#operationList'),$('#win'),url,action,options);
}
/**
 * 此处node为操作树选中节点
 */
function modifyOperation(node){
	var url='<%=basePath%>system/operation/editView.do?id='+node.id;
	var action='<%=basePath%>system/operation/edit.do';
	var options={'title':'添加操作'};
	edit($('#operationList'),$('#win'),url,action,options);
}

function loadMenuTree(menuListObj,rclickMenuObj,url,params){
	menuListObj.tree({
		lines:true,
	    url: url,
	    queryParams: params,
	    loadFilter: function (data) {
	    	if(!(data&&data.length>0)){
	    		var options={'title':'新增平台第一条菜单','width':650};
	    		var pid=0;
	    		var hid=0;
	    		var platformid=$('#platformList').children('.active').attr('attribute');
	    		var url='<%=basePath%>system/menu/editView.do?pid='+pid+'&hid='+hid+'&platformid='+platformid;
	    		var action='<%=basePath%>system/menu/add.do';
	    		edit($('#menuList'),$('#win'),url,action,options);
	    	}
	    	return data;
	    },
	    onContextMenu: function(e,node){
	    	e.preventDefault();
	    	menuListObj.tree('select', node.target);
	    	rclickMenuObj.menu('show', {
				left: e.pageX,
				top: e.pageY
			});
	    },
	    onSelect:function(node){
	    	var params={'menuid':node.id};
	    	loadOperation($('#operationList'),$('#operation_menu'),'<%=basePath%>system/operation/getOperationTree.do',params);
	    }
	});
}
/**
 * 右键菜单处理
 */
function menuHandler(menu, type) {
	var node=$('#menuList').tree('getSelected');
    switch (type) {
        case 1:
        	addBrother(node);
            break;
        case 2:
        	if(node.attributes.isallowchild==0){
        		$.messager.alert('提示','此菜单不允许添加下级菜单','error');
        		return ;
        	}
        	addChild(node);
            break;
        case 3:
        	modify(node);
            break;
        case 4:
        	var ids=new Array();
        	ids.push(node.id);
        	$.messager.confirm('提示', '再次确认是否删除选中菜单？', function(state){
        		if (state){
        			$.ajax({
        				url: '<%=basePath%>system/menu/delete.do',
        				type:'post',
        				dataType:'json',
        				data:{'ids':ids},
        				success:function(data){
        					if(data.state==1){
        						$.messager.alert('提示',data.message,'info');
        						$('#menuList').tree('reload');
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
        case 5:
        	break;
        case 6:
        	node=$('#operationList').tree('getSelected');
        	modifyOperation(node);
        	break;
        case 7:
        	var ids=new Array();
        	node=$('#operationList').tree('getSelected');
        	ids.push(node.id);
        	$.messager.confirm('提示', '再次确认是否删除选中操作？', function(state){
        		if (state){
        			$.ajax({
        				url: '<%=basePath%>system/operation/delete.do',
        				type:'post',
        				dataType:'json',
        				data:{'ids':ids},
        				success:function(data){
        					if(data.state==1){
        						$.messager.alert('提示',data.message,'info');
        						$('#operationList').tree('reload');
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
	$('#operation_menu').menu({
        onClick:function (item) {
        	menuHandler(this, item.name);
        }
    });
	$.ajax({
		url:'<%=basePath%>system/platform/getPlatformList.do',
		type:'post',
		dataType:'json',
		success:function(data){
			$.each(data,function(i,ele){
				var li=null;
				if(i==0) {
					var params={'platformid':ele.id};
					loadMenuTree($('#menuList'),$('#m_menu'),'<%=basePath%>system/menu/getMenuTree.do',params);
					li='<li id="pli'+ele.id+'" attribute="'+ele.id+'" class="active" onclick="switchPlatform(\''+ele.id+'\')">'+ele.name+'</li>';
				}else{
					li='<li id="pli'+ele.id+'" attribute="'+ele.id+'" onclick="switchPlatform(\''+ele.id+'\')">'+ele.name+'</li>';
				}
				$('#platformList').append(li);
			});
		},
		error:function(){
			$.messager.alert('提示','操作失败','error');
		}
	});
});
</script>
