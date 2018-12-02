<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
<style type="text/css">
ul {
	text-decoration: none;
	list-style-type: none;
}

.left-ul {
	width: 100%;
	margin-top: 0px;
	margin-left: -40px;
}

.left-ul li {
	padding: 10px 0px 10px 20px;
	cursor: pointer;
	border-bottom: 0.5px dashed gray;
}

.left-ul li:hover {
	background: #ffe48d;
}

.left-ul li.active {
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
	<div id="tree"></div>
</body>
</html>
<script type="text/javascript">
var treeObj=$('#tree');
var isLoadSuccess=false;
$(function(){
	treeObj.tree({
		checkbox:true,
		lines:true,
		cascadeCheck:false,
	    url: '<%=basePath%>system/position/listPositions.do',
	    queryParams: {'organid':'${organid}','deptid':'${deptid}'},
	    loadFilter: function (data) {
	    	if(!(data&&data.length>0)){
	    		$.messager.alert('提示','此单位暂无职位','error');
	    	}
	    	return data;
	    },
	    onCheck:function(node,checked){
	    	if(checked){
	    		var checkedNodes=treeObj.tree('getChecked');
	    		if(checkedNodes&&checkedNodes.length>0){
	    			var len=checkedNodes.length;
	    			for(var i=0;i<len;i++){
	    				if(checkedNodes[i].id==node.id) continue;
	    				<c:if test="${type=='radio'}">
	    					treeObj.tree('uncheck',checkedNodes[i].target);
	    				</c:if>
	    			}
	    		}
	    	}
	    },
	    onLoadSuccess:function(node, data){
	    	isLoadSuccess=true;
	    }
	});
});
</script>
