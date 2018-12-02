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
	<div id="platformContainer" data-options="region:'west',split:true,collapsible:false" title="平台切换" style="width:30%;">
		<ul id="platformList" class="left-ul">
			
		</ul>
	</div>
	<div id="menuContainer" data-options="region:'center',split:true,collapsible:false" title="菜单管理" style="width:40%;">
		<div id="menuList">
			
		</div>
	</div>
	<div id="operationContainer" data-options="region:'east',split:true,collapsible:false" title="操作管理" style="width:30%;">
		<div id="operationList">
			
		</div>
	</div>
	<div id="m_menu" class="easyui-menu" style="width:150px;">
		<div id="addBrotherMenu" data-options="name:1,iconCls:'icon-add'">数据过滤限制</div>
	</div>
	<div id="win">
		<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
var menus=new Map();
var operations=new Map();
var addedMenus=new Map();
var deletedMenus=new Map();
var addedOperations=new Map();
var deletedOperations=new Map();
var loadMenuSuccess=false;
var loadOperationSuccess=false;

function switchPlatform(pfid){
	var params={'platformid':pfid};
	loadMenuSuccess=false;
	loadOperationSuccess=false;
	loadMenuTree($('#menuList'),$('#m_menu'),'<%=basePath%>system/menu/getMenuTree.do',params);
	loadOperation($('#operationList'),'<%=basePath%>system/operation/getOperationTree.do',null);
	$('#platformList').children('.active').removeClass('active');
	$('#pli'+pfid).addClass('active');
}
function loadOperation(operationListObj,url,params){
	operationListObj.tree({
		lines:true,
	    url: url,
	    checkbox:true,
	    cascadeCheck:false,
	    queryParams:params,
	    loadFilter: function (data) {
	    	return data;
	    },
	    onContextMenu:function(e,node){
	    	operationListObj.tree('select', node.target);
	    	e.preventDefault();
	    },
	    onCheck:function(node,checked){
	    	//console.log('前增操作：'+JSON.stringify(addedMenus));
	    	//console.log('前删操作：'+JSON.stringify(deletedMenus));
	    	if(checked){
	    		//非onLoadSuccess加载完成方法内执行的check uncheck操作时push新增删除
	    		if(!loadOperationSuccess){
	    			if(deletedOperations.containsKey(node.id)){
	    				deletedOperations.remove(node.id);
	    			}else addedOperations.put(node.id,node.id.toString());
	    		}
	    		
	    		var children = operationListObj.tree('getChildren',node.target);
	    		if(children&&children.length>0){
	    			for(var i=0;i<children.length;i++){
	    				operationListObj.tree('check', children[i].target);

	    				if(!loadOperationSuccess){
	    	    			if(deletedOperations.containsKey(node.id)){
	    	    				deletedOperations.remove(children[i].id);
	    	    			}else addedOperations.put(children[i].id,children[i].id.toString());
	    				}
	    			}
	    		}
	    	}else{
	    		if(!loadOperationSuccess){
	    			if(addedOperations.containsKey(node.id)){
	    				addedOperations.remove(node.id);
	    			}else deletedOperations.put(node.id,node.id.toString());
	    		}
	    		
	    		var children = operationListObj.tree('getChildren',node.target);
	    		if(children&&children.length>0){
	    			for(var i=0;i<children.length;i++){
	    				operationListObj.tree('uncheck', children[i].target);
	    				
	    				if(!loadOperationSuccess){
	    					if(addedOperations.containsKey(node.id)){
	    						addedOperations.remove(children[i].id);
	    	    			}else deletedOperations.put(children[i].id,children[i].id.toString());
	    				}
	    			}
	    		}
	    	}
	    	//console.log('后增操作：'+JSON.stringify(addedMenus));
	    	//console.log('后删操作：'+JSON.stringify(deletedMenus));
	    },
	    onLoadSuccess:function(node, data){
	    	if(data&&data.length>0){
	    		loadOperationSuccess=true;
	    		if(operations.size()>0){
	    			var keys=operations.keySet();
					for(var key in keys){
						var value=operations.get(keys[key]);
						var node=operationListObj.tree('find', value);
						if(node){
							operationListObj.tree('check', node.target);
							
							var children = operationListObj.tree('getChildren',node.target);
							if(children&&children.length>0){
								for(var i=0;i<children.length;i++){
									if(operations.containsKey(children[i].id)){
										operationListObj.tree('check', children[i].target);
									}else{
										operationListObj.tree('uncheck', children[i].target);
									}
								}
							}
		    			}
					}
		    	}
	    		if(addedOperations.size()>0){
	    			var keys=addedOperations.keySet();
					for(var key in keys){
						var value=addedOperations.get(keys[key]);
						var node=operationListObj.tree('find', value);
						if(node){
							operationListObj.tree('check', node.target);
		    			}
					}
	    		}
	    		if(deletedOperations.size()>0){
	    			var keys=deletedOperations.keySet();
					for(var key in keys){
						var value=deletedOperations.get(keys[key]);
						var node=operationListObj.tree('find', value);
						if(node){
							operationListObj.tree('uncheck', node.target);
		    			}
					}
	    		}
	    		loadOperationSuccess=false;
	    	}
	    }
	});
}

function loadMenuTree(menuListObj,rclickMenuObj,url,params){
	menuListObj.tree({
		lines: true,
	    url: url,
	    checkbox: true,
	    cascadeCheck: false,
	    queryParams: params,
	    loadFilter: function (data) {
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
	    onCheck: function(node,checked){
	    	//console.log('前增菜单：'+JSON.stringify(addedMenus));
	    	//console.log('前删菜单：'+JSON.stringify(deletedMenus));
	    	if(checked){
	    		//非onLoadSuccess加载完成方法内执行的check uncheck操作时push新增删除菜单
	    		if(!loadMenuSuccess){
	    			if(deletedMenus.containsKey(node.id)){
			    		deletedMenus.remove(node.id);
	    			}else addedMenus.put(node.id,node.id.toString());
	    		}
	    		
	    		var children = menuListObj.tree('getChildren',node.target);
	    		if(children&&children.length>0){
	    			for(var i=0;i<children.length;i++){
	    				menuListObj.tree('check', children[i].target);

	    				if(!loadMenuSuccess){
	    	    			if(deletedMenus.containsKey(node.id)){
	    	    				deletedMenus.remove(children[i].id);
	    	    			}else addedMenus.put(children[i].id,children[i].id.toString());
	    				}
	    			}
	    		}
	    	}else{
	    		if(!loadMenuSuccess){
	    			if(addedMenus.containsKey(node.id)){
	    				addedMenus.remove(node.id);
	    			}else deletedMenus.put(node.id,node.id.toString());
	    		}
	    		
	    		var children = menuListObj.tree('getChildren',node.target);
	    		if(children&&children.length>0){
	    			for(var i=0;i<children.length;i++){
	    				menuListObj.tree('uncheck', children[i].target);
	    				
	    				if(!loadMenuSuccess){
	    					if(addedMenus.containsKey(node.id)){
	    						addedMenus.remove(children[i].id);
	    	    			}else deletedMenus.put(children[i].id,children[i].id.toString());
	    				}
	    			}
	    		}
	    	}
	    	//console.log('后增菜单：'+JSON.stringify(addedMenus));
	    	//console.log('后删菜单：'+JSON.stringify(deletedMenus));
	    },
	    onSelect:function(node){
	    	var n = menuListObj.tree('find', node.id);
	    	menuListObj.tree('check', n.target);
	    	var params={'menuid':node.id};
	    	loadOperationSuccess=false;
	    	loadOperation($('#operationList'),'<%=basePath%>system/operation/getOperationTree.do',params);
	    },
	    onLoadSuccess:function(node, data){
	    	if(data&&data.length>0){
	    		loadMenuSuccess=true;
	    		if(menus.size()>0){
	    			var keys=menus.keySet();
					for(var key in keys){
						var value=menus.get(keys[key]);
						var node=menuListObj.tree('find', value);
						if(node){
							menuListObj.tree('check', node.target);
							
							var children = menuListObj.tree('getChildren',node.target);
							if(children&&children.length>0){
								for(var i=0;i<children.length;i++){
									if(menus.containsKey(children[i].id)){
										menuListObj.tree('check', children[i].target);
									}else{
										menuListObj.tree('uncheck', children[i].target);
									}
								}
							}
		    			}
					}
		    	}
	    		if(addedMenus.size()>0){
	    			var keys=addedMenus.keySet();
					for(var key in keys){
						var value=addedMenus.get(keys[key]);
						var node=menuListObj.tree('find', value);
						if(node){
							menuListObj.tree('check', node.target);
		    			}
					}
	    		}
	    		if(deletedMenus.size()>0){
	    			var keys=deletedMenus.keySet();
					for(var key in keys){
						var value=deletedMenus.get(keys[key]);
						var node=menuListObj.tree('find', value);
						if(node){
							menuListObj.tree('uncheck', node.target);
		    			}
					}
	    		}
	    		loadMenuSuccess=false;
	    	}
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
        	var nodes=$('#menuList').tree('getChecked');
        	alert(nodes.length);
            break;
    }
}
$(function(){
	$('#m_menu').menu({
        onClick:function (item) {
        	menuHandler(this, item.name);
        }
    });

	$.ajax({
		url:'<%=basePath%>system/organization/initAuthorization.do',
		type:'post',
		dataType:'json',
		data:{'organid':'${organid}'},
		success:function(data){
			//menus=data.menus;
			//operations=data.operations;
			
			if(data.menus&&data.menus.length>0){
				for(var i=0;i<data.menus.length;i++){
	    			var id=data.menus[i].id;
	    			menus.put(id,id.toString());
	    		}
			}
			
			if(data.operations&&data.operations.length>0){
				for(var i=0;i<data.operations.length;i++){
	    			var id=data.operations[i].id;
	    			operations.put(id,id.toString());
	    		}
			}
		},
		error:function(){
			$.messager.alert('提示','获取单位授权功能操作失败，请检查异常日志','error');
		}
	});
	
	$.ajax({
		url:'<%=basePath%>system/platform/getPlatformList.do',
		type:'post',
		dataType:'json',
		success:function(data){
			var firstPlatid=null;
			$.each(data,function(i,ele){
				var li=null;
				if(i==0){
					firstPlatid=ele.id;
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
