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
			<input type="hidden" name="tempid" id="tempid" value="${object.tempid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">模板名:</label>
					<div class="col-xs-9">
						<input id="temname" name="temname" type="text" value="${object.temname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">模板图地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="url" name="url" type="text" value="${object.url}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">上传模板图:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="file" name="file" type="file" accept="image/*"/>
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
<script type="text/javascript" src="<%=basePath%>im/member/bgtemplate/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#file').change(function(){
		var data = new FormData($('#editForm')[0]);
		$.ajax({
            url: '<%=basePath%>im/member/upload',
            data : data,
			type : 'post',
			cache : false,
			contentType : false,
			processData : false,
            success: function(res){
            	var data=JSON.parse(res);
				if(data.state==1) {
					$.messager.alert('提示','上传成功','info');
					$('#url').val(data.data);
				}else {
					$.messager.alert('提示','上传失败','error');
				}
            }
        });
	});
});
</script>