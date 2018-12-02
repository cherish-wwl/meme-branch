<%@page import="com.meme.im.pojo.MemeComment"%>
<%@ page language="java" import="java.util.*,com.meme.im.pojo.MemeHomePage,com.meme.core.util.HtmlUtil" pageEncoding="UTF-8"%>
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
<%@include file="/common/include/include-qiniu.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/js/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=basePath%>static/js/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=basePath%>static/js/kindeditor-4.1.10/kindeditor-all.js"></script>
<script charset="utf-8" src="<%=basePath%>static/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
			<input type="hidden" name="sizeLimit" id="sizeLimit" value="${sizeLimit}"/>
			<input type="hidden" name="uploadLimit" id="uploadLimit" value="${uploadLimit}"/>
			<input type="hidden" name="id" id="id" value="${object.id}"/>
			<div class="row" style="width:99.8%;padding-left:30px;">
				<div style="text-align: left"><label>评论内容:</label></div>
				<%
				MemeComment obj=(MemeComment)request.getAttribute("object");
				String content=(null!=obj && obj.getContent() != null)?new String(obj.getContent().getBytes(),"UTF-8"):"";
				%>
				<textarea class="form-control" rows="20" id="content" name="content"><%=HtmlUtil.htmlspecialchars(content)%></textarea>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label" style="padding-top:10px;">备注:</label>
				<div class="col-xs-9">
					<input id="remarks" name="remarks" type="text" value="${object.remarks}" class="form-control"/>
				</div>
			</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
</body>
<script type="text/javascript">
	var editor=null;
	KindEditor.ready(function(K) {
		editor=K.create('textarea[id="content"]', {
			//添加SESSIONID，避免flash上传不提交SESSIONID被拦截退出登录
			uploadJson : '<%=basePath%>qiniu/kindeditor/upload.do;jsessionid=${cookie.JSESSIONID.value}?bucket=express',
			fileManagerJson : '<%=basePath%>qiniu/kindeditor/list.do?bucket=express',
			allowFileManager : true,
			afterBlur:function(){
				this.sync();
			}
		});
	    prettyPrint();
	});
</script>
</html>
<script type="text/javascript" src="<%=basePath%>system/platform/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>