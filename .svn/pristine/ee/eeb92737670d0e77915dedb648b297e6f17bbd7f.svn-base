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
				<div class="form-group">
					<label class="col-xs-3 control-label">分类名称:</label>
					<div class="col-xs-9">
						<input id="catname" name="catname" type="text" value="${object.catname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">上级分类:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<select id="pid" name="pid" class="form-control">
						</select>
						<script type="text/javascript">
						$.ajax({
							url: '<%=basePath%>im/video/category/getCatTree.do',
							type:'post',
							dataType:'json',
							success:function(data){
								if(data){
									var options=createTreeSelect(data,'catname','id','顶级分类');
									$('#pid').append(options);
									$('#pid').val('${object.pid}');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
						</script>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">类目描述:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="description" name="description" type="text" value="${object.description}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">链接地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="url" name="url" type="text" value="${object.url}" class="form-control">
						<p class="help-block">如需跳转外部地址，在此填写</p>
					</div>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>im/video/category/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>