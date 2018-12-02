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
<%@include file="/common/include/include-qiniu.jsp"%>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
			<input type="hidden" name="programid" id="programid" value="${object.programid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label">节目名称:</label>
					<div class="col-xs-9">
						<input id="programName" name="programName" type="text" value="${object.programName}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">所属频道:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<select id="tvid" name="tvid" class="form-control">
						</select>
						<script type="text/javascript">
						$.ajax({
							url: '<%=basePath%>im/tv/station/getTvStation.do',
							type:'post',
							dataType:'json',
							success:function(data){
								if(data){
									var options=createSelect(data,'tvStation','tvid','');
									$('#tvid').append(options);
									$('#tvid').val('${object.tvid}');
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
					<label class="col-xs-3 control-label" style="padding-top:10px;">播放地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="url" name="url" type="text" value="${object.url}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">播出时间:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="ptimeStr" name="ptimeStr" type="text" value="<fmt:formatDate value="${object.ptime}" pattern="HH:mm" />" onfocus="WdatePicker({dateFmt:'HH:mm'})" readonly="readonly" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">推荐节目:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="istop" name="istop" selected="${object.istop}" type="select" dictGroupCode="SF" defaultOption="推荐节目" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">热门综艺:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="ishotshow" name="ishotshow" selected="${object.ishotshow}" type="select" dictGroupCode="SF" defaultOption="热门综艺" cssClass="form-control"/>
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

<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>