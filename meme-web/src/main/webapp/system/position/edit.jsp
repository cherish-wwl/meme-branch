<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_bootstrap.jsp"%>
<link href="<%=basePath%>static/js/easyui-1.5/themes/color.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/easyui-1.5/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>static/js/easyui-1.5/themes/metro/easyui.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/easyui-1.5/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/easyui-1.5/easyloader.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/easyui-1.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/util.js" type="text/javascript"></script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
			<input type="hidden" name="id" id="id" value="${object.id}"/>
			<input type="hidden" name="organid" id="organid" value="${organid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">职位名称:</label>
					<div class="col-xs-9">
						<input id="name" name="name" type="text" value="${object.name}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">所属部门:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input type="hidden" id="deptid" name="deptid" value="${object.deptid}"/>
						<input id="deptName" name="deptName" onclick="selectDepts()" value="${object.deptName}" class="form-control" readonly="readonly"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">备注:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="remark" name="remark" type="text" value="${object.remark}" class="form-control"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
<div id="win">
<iframe id="frame" name="frame" scrolling="no" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>system/position/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
function deptCheck(){
	var treeObj=window.frames['frame'].treeObj;
	var val=$('#deptid').val();
	var node=treeObj.tree('find',val);
	if(node!=null) treeObj.tree('check',node.target);
}
function selectDepts(){
	winObj=$('#win');
	winObj.find('iframe').attr('src','<%=basePath%>system/department/listDeptsView.do?type=radio&organid=${organid}');
	winObj.dialog({
	    title: '选择部门',
	    width: 400,
	    height: 250,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'选择',
				iconCls:'icon-save',
				handler:function(){
					var treeObj=window.frames['frame'].treeObj;
					var nodes=treeObj.tree('getChecked');
					if(nodes&&nodes.length>0){
						$('#deptid').val(nodes[0].id);
						$('#deptName').val(nodes[0].text);
						winObj.dialog('close');
					}else $.messager.alert('提示','未选中所属部门','error');
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
	treeCheck(deptCheck);
}
</script>
