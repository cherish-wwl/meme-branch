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
	<button id="uploadBtn">导入excel数据</button>
</div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>system/dictitem/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
$('#uploadBtn').uploadify({
	'multi':false,
	'formData': {
		'jsessionid':'${cookie.JSESSIONID.value}',
		'dictgroupid':'${dictgroupid}'
	},
	'buttonText':'导入数据',
	'fileTypeExts' : '*.xls;',
	'swf'      : '<%=basePath%>static/js/uploadify/uploadify.swf',
	'uploader' : '<%=basePath%>system/dictitem/doImport.do',
	'onUploadSuccess' : function(file, data, response) {
		data = JSON.parse(data);
		if(data.state==1){
			$.messager.alert('提示',data.message,'info');
		}else if(data.state==0){
			$.messager.alert('提示',data.message,'error');
		}
    }
});
</script>
