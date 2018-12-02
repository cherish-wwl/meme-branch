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
			<li>
			<a href="<%=basePath%>system/params/index.do?type=${item.dictitemcode}">${item.dictitemname}</a>
			</li>
		</c:forEach>
	</c:if>
	<li><a href="<%=basePath%>system/params/index.do?type=">未分类参数</a></li>
	<li class="active"><a href="<%=basePath%>system/params/addView.do">添加参数</a></li>
</ul>
<div class="container-fluid" style="width:80%;">
	<div class="row">
	<div class="col-lg-12">
	<div class="panel panel-primary" style="margin-top:20px;">
		<div class="panel-heading"><h3 class="panel-title"></h3></div>
		<div class="panel-body">
			<form id="editForm" method="post">
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数名:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="name" name="name" type="text" value="" class="form-control"/>
						<span class="help-block">建议英文大写加下划线命名</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数标题:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="title" name="title" type="text" value="" class="form-control"/>
						<span class="help-block">参数中文名</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">所属分类:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="type" name="type" type="select" dictGroupCode="DICTGROUP_TYPE" defaultOption="参数分类" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数说明:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<textarea class="form-control" rows="3" id="remark" name="remark"></textarea>
						<span class="help-block">此说明显示在参数值输入框下用作提示</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数值类型:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="vartype" name="vartype" type="select" dictGroupCode="PARAMS_TYPE" defaultOption="请选择参数值类型" cssClass="form-control"/>
						<span class="help-block">参数值类型，符合validator解析器可解析的java类简称</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">默认值:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<textarea class="form-control" rows="3" id="defval" name="defval"></textarea>
						<span class="help-block"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数值长度:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="prec" name="prec" type="text" value="" class="form-control"/>
						<span class="help-block">值为数值时取值整数和小数部分数字位数</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">小数位数:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="scale" name="scale" type="text" value="" class="form-control"/>
						<span class="help-block">参数值为小数类型时此值设置有效，字符串等类型设置无效</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数值是否必填:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="nullable" name="nullable" type="select" selected="1" dictGroupCode="SF" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">输入方式:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="enumvar" name="enumvar" type="select" dictGroupCode="INPUT_WAY" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">数据字典分组:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<select id="dictgroupcode" name="dictgroupcode" class="form-control">
						</select>
						<span class="help-block">当使用选择输入时提供数据字典限制选择输入值</span>
						<script type="text/javascript">
						$.ajax({
							url: '<%=basePath%>system/dictgroup/listAllDictGroups.do',
							type:'post',
							dataType:'json',
							success:function(data){
								if(data&&data.length>0){
									var options=createSelect(data,'dictgroupname','dictgroupcode','请选择数据字典分组');
									$('#dictgroupcode').append(options);
									//('#catid').val('${object.catid}');
								}
							},
							error:function(){
								$.messager.alert('提示','获取数据字典数据失败，请稍后重试或者联系管理员','error');
							}
						});
						</script>
					</div>
				</div>
				<%--<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数值是否多选:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="multi" name="multi" type="select" dictGroupCode="SF" cssClass="form-control"/>
						<span class="help-block">输入方式为选择输入时此值设置有效</span>
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">参数值附加验证规则:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<textarea class="form-control" rows="3" id="validate" name="validate"></textarea>
						<span class="help-block">json格式字符串，使用jquery validation验证规则写法</span>
					</div>
				</div>
			</form>
		</div>
	</div>
	</div>
	</div>
</div>
<div align="center" style="margin-bottom:100px;"><button type="button" onclick="save()" class="btn btn-primary">新增参数</button></div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>system/params/loadValidationRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
function save(){
	var validateObj=validate($('form'));
	if(validateObj.form()){
		var enumvar=$('#enumvar').val();
		var dictgroupcode=$('#dictgroupcode').val();
		if(enumvar==1&&dictgroupcode==''){
			$.messager.alert('提示','选择输入时数据字典分组必填','error');
			return ;
		}
		$.ajax({
			url:'<%=basePath%>system/params/add.do',
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
</script>