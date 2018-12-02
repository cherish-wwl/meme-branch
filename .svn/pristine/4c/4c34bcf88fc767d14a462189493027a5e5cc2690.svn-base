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
<ul class="nav nav-tabs">
	<c:if test="${!empty items && fn:length(items)>0}">
		<c:forEach items="${items}" var="item" varStatus="status">
			<li class="<c:if test="${item.dictitemcode==type}">active</c:if>">
			<a href="<%=basePath%>system/params/index.do?type=${item.dictitemcode}">${item.dictitemname}</a>
			</li>
		</c:forEach>
	</c:if>
	<li class="<c:if test="${type==''}">active</c:if>"><a href="<%=basePath%>system/params/index.do?type=">未分类参数</a></li>
	<li class=""><a href="<%=basePath%>system/params/addView.do">添加参数</a></li>
</ul>
<div class="container-fluid" style="width:80%;">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
				<c:if test="${!empty list && fn:length(list)>0 }">
					<c:forEach items="${list}" var="item" varStatus="status">
						<div class="form-group">
							<label class="col-xs-4 control-label" style="padding-top:20px;">${item.title}(${item.name}):</label>
							<div class="col-xs-8" style="padding-top:10px;">
								<c:choose>
									<c:when test="${item.enumvar==1}">
										<express:SelectTag id="${item.name}" name="${item.name}" selected="${(empty item.value)?item.defval:item.value}" type="select" dictGroupCode="${item.dictgroupcode}" cssClass="form-control"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${empty item.value}">
											<input id="${item.name}" name="${item.name}" type="text" value="${expressFunc:parseSpecialChars(item.defval)}" class="form-control"/>
											</c:when>
											<c:otherwise>
											<input id="${item.name}" name="${item.name}" type="text" value="${expressFunc:parseSpecialChars(item.value)}" class="form-control"/>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
								<span class="help-block">${item.remark}</span>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
<div align="center" style="margin-bottom:100px;"><button type="button" onclick="save()" class="btn btn-primary">保存更改</button></div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>system/params/loadColumnRules.do?type=${type}"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
function save(){
	var validateObj=validate($('form'));
	if(validateObj.form()){
		$.ajax({
			url:'<%=basePath%>system/params/batchupdate.do',
			type:'post',
			dataType:'json',
			data:$('form').serialize(),
			success:function(data){
				if(data.state==1){
					$.messager.alert('提示',data.message,'info',function(){
						window.location.reload();
					});
				}else if(data.state==0){
					$.messager.alert('提示',data.message,'error');
				}
			},
			error:function(){
				$.messager.alert('提示','操作失败','error');
			}
		});
	}
}
function clearRules(){
	var url='<%=basePath%>system/params/clearRules.do';
	$.messager.confirm('提示', '再次确认是否清空所有验证规则？', function(state){
		if (state){
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
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
</script>