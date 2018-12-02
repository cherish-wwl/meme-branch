<%@ page language="java" import="java.util.*,com.meme.im.pojo.MemeCrawler,com.meme.core.util.HtmlUtil" pageEncoding="UTF-8"%>
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
			<form id="editForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="sizeLimit" id="sizeLimit" value="${sizeLimit}"/>
			<input type="hidden" name="uploadLimit" id="uploadLimit" value="${uploadLimit}"/>
			<input type="hidden" name="id" id="id" value="${object.id}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">标题:</label>
					<div class="col-xs-9">
						<input id="title" name="title" type="text" value="${object.title}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">摘要:</label>
					<div class="col-xs-9">
						<input id="summary" name="summary" type="text" value="${object.summary}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">封面:</label>
					<div class="col-xs-9">
						<input id="cover_result1" name="cover_result1" type="file"  class="form-control"/>
						<input id="cover_result2" name="cover_result2" type="file"  class="form-control"/>
						<input id="cover_result3" name="cover_result3" type="file"  class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">封面链接:</label>
					<div class="col-xs-9">
						<input id="cover_href" name="cover_href" type="text" value="${object.cover_href}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">文件:</label>
					<div class="col-xs-9">
						<input id="file_result" name="file_result" type="file" value="${object.file}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">文件类型:</label>
					<div class="col-xs-9">
						<select id="file_type" name="file_type"  class="form-control">
							<option value="">请选择</option>
							<option value="1">图片</option>
							<option value="2">音频</option>
							<option value="3">视频</option>
							<option value="4">图文</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">所属专辑:</label>
					<div class="col-xs-9">
						<input id="album" name="album" type="text" value="${object.album}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">自定义类目:</label>
					<div class="col-xs-9">
						<input id="custom_column" name="custom_column" type="text" value="${object.custom_column}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">文件原始链接:</label>
					<div class="col-xs-9">
						<input id="file_original_link" name="file_original_link" type="text" value="${object.file_original_link}" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">文件来源:</label>
					<div class="col-xs-9">
						<express:SelectTag id="source" name="source" type="select" selected="${object.source}" dictGroupCode="CRAWLER_DATA_SOURCE" defaultOption="请选择" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">备注:</label>
					<div class="col-xs-9">
						<input id="remarks" name="remarks" type="text" value="${object.remarks}" class="form-control"/>
					</div>
				</div>
				<div class="row" style="width:99.8%;padding-left:30px;">
				<div style="text-align: left"><label>图文内容:</label></div>
					<%
					MemeCrawler obj=(MemeCrawler)request.getAttribute("object");
					String content=(null!=obj && obj.getContent() != null)?new String(obj.getContent().getBytes(),"UTF-8"):"";
					%>
					<textarea class="form-control" rows="20" id="content" name="content"><%=HtmlUtil.htmlspecialchars(content)%></textarea>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
</body>
<script type="text/javascript">
	$("#file_type").val("${object.file_type}");
	$("#type").val("${object.type}");
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