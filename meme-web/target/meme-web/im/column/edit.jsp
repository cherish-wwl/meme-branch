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
			<input type="hidden" name="id" id="id" value="${object.id}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label">栏目名称:</label>
					<div class="col-xs-9">
						<input id="columnname" name="columnname" type="text" value="${object.columnname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">栏目编码:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="columncode" name="columncode" type="text" value="${object.columncode}" class="form-control">
						<p class="help-block">栏目编码要唯一，不能重复</p>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">上级栏目:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<select id="pid" name="pid" class="form-control">
						</select>
						<script type="text/javascript">
						$.ajax({
							url: '<%=basePath%>im/column/getTree.do',
							type:'post',
							dataType:'json',
							success:function(data){
								if(data){
									var options=createTreeSelect(data,'columnname','id','顶级分类');
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
					<label class="col-xs-3 control-label" style="padding-top:10px;">图标:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						
						<div id="videoContainer">
							<div id="musicContainer1">
								<input id="icon" name="icon" type="hidden" value="${object.icon}" class="form-control">
								<a class="btn btn-default btn-primary" id="pickVideo" href="#" style="position: relative; z-index: 1;">
	                                <i class="glyphicon glyphicon-plus"></i>
	                                <span>上传图标</span>
	                            </a>
							</div>
							<div id="musicContainer2" style="display:none;">
								<input id="icon" name="icon" type="text" value="${object.icon}" class="form-control">
							</div>
						</div>
						<div class="checkbox">
							<label>
								<input id="uploadVideoBtn" type="checkbox">填写图标
							</label>
						</div>
					</div>
					<table id="uploadDetail2" class="table table-striped table-hover text-left"   style="display:none">
						<thead>
							<tr>
								<th class="col-md-4">文件名</th>
								<th class="col-md-2">大小</th>
								<th class="col-md-6">详情</th>
							</tr>
						</thead>
						<tbody id="fsUploadProgress2">
						</tbody>
					</table>
				</div>
				<script type="text/javascript">
				$(function(){
					var videoUploadOption={
						disable_statistics_report: false,
                        runtimes: 'html5,flash,html4',
                        browse_button: 'pickVideo',
                        uptoken_url: '<%=basePath%>qiniu/getUptoken.do',
                        domain: 'http://${domain}',
                        get_new_uptoken: false,
                        container: 'videoContainer',
                        drop_element: 'videoContainer',
                        flash_swf_url: '<%=basePath%>static/js/plupload-2.1.9/js/Moxie.swf',
                        max_retries: 3,
                        dragdrop: false,
                       	max_file_size : '10000mb',
                        chunk_size: '20mb',
                        auto_start: true,
                        multi_selection: false,
                        /* log_level: 5, */
                        filters: {
                        	mime_types : [
                        		{ title : "图片", extensions : "jpg,gif,png,jpeg,bmp" }
                        	],
                        	prevent_duplicates : true
                        },
                        init: {
                            'FilesAdded': function(up, files) {
                            	$('#uploadDetail2').dialog({
                            	    title: '文件上传',
                            	    width: 500,
                            	    height: 200,
                            	    closed: false,
                            	    modal: true,
                            	    maximizable: false,
                            	    resizable: false,
                            	    collapsible: false,
                            	    minimizable: false,
                            	});
                                plupload.each(files, function(file) {
                                  var progress = new FileProgress(file, 'fsUploadProgress2');
                                  progress.setStatus('准备上传...');
                                  progress.bindUploadCancel(up);
                                });
                            },
                            'BeforeUpload': function(up, file) {
                                var progress = new FileProgress(file, 'fsUploadProgress2');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                if (up.runtime === 'html5' && chunk_size) {
                                  progress.setChunkProgess(chunk_size);
                                }
                              },
                              'UploadProgress': function(up, file) {
                                var progress = new FileProgress(file, 'fsUploadProgress2');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                progress.setProgress(file.percent + "%", file.speed, chunk_size);
                              },
                              'UploadComplete': function() {
                                console.debug('文件上传完成');
                              },
                              'FileUploaded': function(up, file, info) {
                                var progress = new FileProgress(file, 'fsUploadProgress2');
                                console.debug("文件上传成功响应数据:",info.response);
                                progress.setComplete(up, info.response);
                                
                                $('#uploadDetail2').dialog('close');
                                var res = $.parseJSON(info.response);
                                var url=up.getOption('domain')+'/'+res.key;
                                console.debug('====文件地址：',url);
                                $('#icon').val(url);
                              },
                              'Error': function(up, err, errTip) {
                            	  $('#uploadDetail2').dialog({
	                            	    title: '文件上传',
	                            	    width: 500,
	                            	    height: 200,
	                            	    closed: false,
	                            	    modal: true,
	                            	    maximizable: false,
	                            	    resizable: false,
	                            	    collapsible: false,
	                            	    minimizable: false,
	                            	});
                                 	var progress = new FileProgress(err.file, 'fsUploadProgress2');
                                 	progress.setError();
                                 	progress.setStatus(errTip);
                                },
                            'Key': function(up, file) {
                                var name=file.name;
                                var d=new Date();
                                var newName=d.getTime();
                            	var key = "${prefix}"+newName+name.substring(name.lastIndexOf('.'));
					            return key;
                            }
                        }
                    };
					var uploader2=Qiniu.uploader(videoUploadOption);
					
					$('#uploadVideoBtn').click(function(){
						var url=$('input[name="icon"]').val();
		          		var check=$(this).is(':checked');
		          		if(check){
		          			$('#musicContainer1').css('display','none');
		          			$('#musicContainer2').css('display','');
		          		}else{
		          			$('#musicContainer1').css('display','');
		          			$('#musicContainer2').css('display','none');
		          		}
	          			$('input[name="icon"]').val(url);
					});
				});
				</script>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">链接地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="url" name="url" type="text" value="${object.url}" class="form-control">
						<p class="help-block">如需跳转外部地址，在此填写</p>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">是否显示:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="state" name="state" selected="${object.state}" type="select" dictGroupCode="SF" defaultOption="是否显示" cssClass="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">添加标签:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<express:SelectTag id="tag" name="tag" selected="${object.tag}" type="select" dictGroupCode="TAG_LIST" defaultOption="添加标签" cssClass="form-control"/>
						<p class="help-block"></p>
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
<script type="text/javascript" src="<%=basePath%>im/column/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>