<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
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
			<input type="hidden" name="loginid" id="loginid" value="${object.loginid}"/>
			<input type="hidden" name="organid" id="organid" value="${organid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">账号:</label>
					<div class="col-xs-9">
						<input id="account" name="account" type="text" value="${object.account}" class="form-control"/>
					</div>
				</div>
				<c:if test="${object == null}">
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">密码:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="pwd" name="pwd" type="password" value="" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">确认密码:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="confirmpwd" name="confirmpwd" type="password" value="" class="form-control"/>
					</div>
				</div>
				</c:if>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">姓名:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="username" name="username" type="text" value="${object.username}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">昵称:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="nickname" name="nickname" type="text" value="${object.nickname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">性别:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="sex" name="sex" type="select" selected="${object.sex}" dictGroupCode="SEX" defaultOption="性别" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">邮箱:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="email" name="email" type="text" value="${object.email}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">手机号码:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="cellphone" name="cellphone" type="text" value="${object.cellphone}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">qq:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="qq" name="qq" type="text" value="${object.qq}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">固定电话:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="homephone" name="homephone" type="text" value="${object.homephone}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">联系地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="address" name="address" type="text" value="${object.address}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">个性签名:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sign" name="sign" type="text" value="${object.sign}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">部门:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input type="hidden" id="deptid" name="deptid" value="${object.deptid}"/>
						<input id="deptname" name="deptname" type="text" value="${object.deptname}" onclick="selectDepts()" class="form-control" placeholder="" readonly="readonly"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">职位:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input type="hidden" id="postid" name="postid" value="${object.postid}"/>
						<input id="posname" name="posname" type="text" value="${object.posname}" onclick="selectPositions()" class="form-control" placeholder="" readonly="readonly"/>
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
<script type="text/javascript" src="<%=basePath%>system/account/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
function validate(formObj){
	//合并验证规则
	$.extend(true, rulesSettings, additionalRules);
	return formObj.validate({
		rules: rulesSettings,
		messages: {
			qq:{
				digits:'请输入正确的QQ号'
			}
		}
	});
}
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
						$('#deptname').val(nodes[0].text);
					}else {
						$('#deptid').val('');
						$('#deptname').val('');
						//$.messager.alert('提示','未选中所属部门','error');
					}
					winObj.dialog('close');
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

function positionCheck(){
	var treeObj=window.frames['frame'].treeObj;
	var val=$('#postid').val();
	var node=treeObj.tree('find',val);
	if(node!=null) treeObj.tree('check',node.target);
}
function selectPositions(){
	var deptid=$('#deptid').val();
	winObj=$('#win');
	var url='<%=basePath%>system/position/listPositionsView.do?type=radio&organid=${organid}';
	if(deptid&&typeof deptid !='undefined'){
		url+='&deptid='+deptid;
	}
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '选择职位',
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
						$('#postid').val(nodes[0].id);
						$('#posname').val(nodes[0].text);
					}else {
						$('#postid').val('');
						$('#posname').val('');
						//$.messager.alert('提示','未选中所属部门','error');
					}
					winObj.dialog('close');
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
	treeCheck(positionCheck);
}
</script>
