<%@ page language="java" import="java.util.*,com.meme.im.pojo.MemeContent,com.meme.core.util.HtmlUtil" pageEncoding="UTF-8"%>
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
				
				<input type="hidden" name="contentid" id="contentid" value="${object.contentid}"/>
				<div class="form-group">
					<label class="col-xs-3 control-label">内容标题:</label>
					<div class="col-xs-9">
						<input id="contentname" name="contentname" type="text" value="${object.contentname}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">作者:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="author" name="author" type="text" value="${object.author}" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">所属栏目分类:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<select id="catid" name="catid" class="form-control">
						</select>
						<script type="text/javascript">
						$.ajax({
							url: '<%=basePath%>im/column/category/getTree.do',
							type:'post',
							dataType:'json',
							success:function(data){
								if(data){
									var options=createTreeSelect(data,'catname','id','请选择所属栏目分类');
									$('#catid').append(options);
									$('#catid').val('${object.catid}');
									
									<%-- var catid='${object.catid}';
									if(catid){
										$.ajax({
											url: '<%=basePath%>im/column/section/getSections.do',
											type:'post',
											dataType:'json',
											data: {'catid':catid},
											success:function(data){
												var sec_checkbox='';
												if(data.state==1){
													var list=data.data;
													if(list&&list.length>0){
														for(var i=0;i<list.length;i++){
															var section=list[i];
															sec_checkbox+='<label class="checkbox-inline">';
															sec_checkbox+='<input type="checkbox" name="sectionid" value="'+section.id+'">'+section.sectionname;
															sec_checkbox+='</label>';
														}
													}
												}
												//移除所有加载的栏目块
												$('#sectionContainer').empty();
												$('#sectionContainer').append(sec_checkbox);
											},
											error:function(){
												$.messager.alert('提示','操作失败','error');
											}
										});
									} --%>
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
						$('#catid').change(function(){
							var catid=$('#catid').val();
							if(catid){
								$.ajax({
									url: '<%=basePath%>im/column/section/getSections.do',
									type:'post',
									dataType:'json',
									data: {'catid':catid},
									success:function(data){
										var sec_checkbox='';
										if(data.state==1){
											var list=data.data;
											if(list&&list.length>0){
												for(var i=0;i<list.length;i++){
													var section=list[i];
													sec_checkbox+='<label class="checkbox-inline">';
													sec_checkbox+='<input type="checkbox" name="sectionid" value="'+section.id+'">'+section.sectionname;
													sec_checkbox+='</label>';
												}
											}
										}
										//移除所有加载的栏目块
										$('#sectionContainer').empty();
										$('#sectionContainer').append(sec_checkbox);
									},
									error:function(){
										$.messager.alert('提示','操作失败','error');
									}
								});
							}else{
								//移除所有加载的栏目块
								$('#sectionContainer').empty();
							}
						});
						</script>
					</div>
				</div>
				
				<div class="row form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;margin-left:15px;">所属栏目块:</label>
					<div class="col-xs-8" style="padding-top:10px;" id="sectionContainer">
						<c:if test="${!empty list }">
							<c:forEach items="${list}" var="item" varStatus="status">
								<label class="checkbox-inline">
									<c:choose>
										<c:when test="${!empty item['csec_id'] }">
											<input type="checkbox" name="sectionid" checked="checked" value="${item['id']}"/>${item['sectionname']}
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="sectionid" value="${item['id']}"/>${item['sectionname']}
										</c:otherwise>
									</c:choose>
								</label>
							</c:forEach>
						</c:if>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">封面地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<div id="imgContainer">
							<div id="coverContainer1">
								<input id="cover" name="cover" type="hidden" value="${object.cover}" class="form-control">
								<a class="btn btn-default btn-primary" id="pickCover" href="#" style="position: relative; z-index: 1;">
	                                <i class="glyphicon glyphicon-plus"></i>
	                                <span>上传封面</span>
	                            </a>
							</div>
							<div id="coverContainer2" style="display:none;">
								<input id="cover" name="cover" type="text" value="${object.cover}" class="form-control">
							</div>
						</div>
						<div class="checkbox">
							<label>
								<input id="uploadCoverBtn" type="checkbox">填写封面地址
							</label>
						</div>
					</div>
					
					<table id="uploadDetail" class="table table-striped table-hover text-left"   style="display:none">
						<thead>
							<tr>
								<th class="col-md-4">文件名</th>
								<th class="col-md-2">大小</th>
								<th class="col-md-6">详情</th>
							</tr>
						</thead>
						<tbody id="fsUploadProgress">
						</tbody>
					</table>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">文件地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						
						<div id="videoContainer">
							<div id="musicContainer1">
								<input id="url" name="url" type="hidden" value="${object.url}" class="form-control">
								<a class="btn btn-default btn-primary" id="pickVideo" href="#" style="position: relative; z-index: 1;">
	                                <i class="glyphicon glyphicon-plus"></i>
	                                <span>上传文件</span>
	                            </a>
							</div>
							<div id="musicContainer2" style="display:none;">
								<input id="url" name="url" type="text" value="${object.url}" class="form-control">
							</div>
						</div>
						<div class="checkbox">
							<label>
								<input id="uploadVideoBtn" type="checkbox">填写音乐地址
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
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:10px;">歌词文件地址:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						
						<div id="lrcContainer">
							<div id="lrcContainer1">
								<input id="lrc" name="lrc" type="hidden" value="${object.lrc}" class="form-control">
								<a class="btn btn-default btn-primary" id="pickLrc" href="#" style="position: relative; z-index: 1;">
	                                <i class="glyphicon glyphicon-plus"></i>
	                                <span>上传歌词文件</span>
	                            </a>
							</div>
							<div id="lrcContainer2" style="display:none;">
								<input id="lrc" name="lrc" type="text" value="${object.lrc}" class="form-control">
							</div>
						</div>
						<div class="checkbox">
							<label>
								<input id="uploadLrcBtn" type="checkbox">填写歌词文件地址
							</label>
						</div>
					</div>
					<table id="uploadDetail3" class="table table-striped table-hover text-left"   style="display:none">
						<thead>
							<tr>
								<th class="col-md-4">文件名</th>
								<th class="col-md-2">大小</th>
								<th class="col-md-6">详情</th>
							</tr>
						</thead>
						<tbody id="fsUploadProgress3">
						</tbody>
					</table>
				</div>
				<script type="text/javascript">
				$(function(){
					//=============上传封面参数配置=============
					var coverUploadOption={
						disable_statistics_report: false,
                        runtimes: 'html5,flash,html4',
                        browse_button: 'pickCover',
                        uptoken_url: '<%=basePath%>qiniu/getUptoken.do',
                        domain: 'http://${domain}',
                        get_new_uptoken: false,
                        container: 'imgContainer',
                        drop_element: 'imgContainer',
                        flash_swf_url: '<%=basePath%>static/js/plupload-2.1.9/js/Moxie.swf',
                        max_retries: 3,
                        dragdrop: false,
                       	max_file_size : '10mb',
                        chunk_size: '4mb',
                        auto_start: true,
                        multi_selection: false,
                        //log_level: 5,
                        filters: {
                        	mime_types : [
                        		{ title : "图片", extensions : "jpg,gif,png,jpeg,bmp" }
                        	],
                        	prevent_duplicates : true
                        },
                        init: {
                            'FilesAdded': function(up, files) {
                            	$('#uploadDetail').dialog({
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
                                  var progress = new FileProgress(file, 'fsUploadProgress');
                                  progress.setStatus('准备上传...');
                                  progress.bindUploadCancel(up);
                                });
                            },
                            'BeforeUpload': function(up, file) {
                                console.debug("上传前回调函数");
                                var progress = new FileProgress(file, 'fsUploadProgress');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                if (up.runtime === 'html5' && chunk_size) {
                                  progress.setChunkProgess(chunk_size);
                                }
                              },
                              'UploadProgress': function(up, file) {
                                var progress = new FileProgress(file, 'fsUploadProgress');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                progress.setProgress(file.percent + "%", file.speed, chunk_size);
                              },
                              'UploadComplete': function() {
                                console.debug('上传完成');
                              },
                              'FileUploaded': function(up, file, info) {
                                var progress = new FileProgress(file, 'fsUploadProgress');
                                console.debug("文件上传成功响应数据:",info.response);
                                progress.setComplete(up, info.response);
                                
                                $('#uploadDetail').dialog('close');
                                var res = $.parseJSON(info.response);
                                var url=up.getOption('domain')+'/'+res.key;
                                $('#cover').val(url);
                              },
                              'Error': function(up, err, errTip) {
                            	  $('#uploadDetail').dialog({
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
                                 	var progress = new FileProgress(err.file, 'fsUploadProgress');
                                 	progress.setError();
                                 	progress.setStatus(errTip);
                                },
                            'Key': function(up, file) {
                            	var name=file.name;
                                var d=new Date();
                                var newName=d.getTime();
                            	var key = "${prefix}"+newName+name.substring(name.lastIndexOf('.'));
                            	//var key = "${prefix}${key}"+name.substring(name.lastIndexOf('.'));
					            return key;
                            }
                        }
                    };
					var uploader = Qiniu.uploader(coverUploadOption);
					
					$('#uploadCoverBtn').click(function(){
						var url=$('input[name="cover"]').val();
		          		var check=$(this).is(':checked');
		          		if(check){
		          			$('#coverContainer1').css('display','none');
		          			$('#coverContainer2').css('display','');
		          		}else{
		          			$('#coverContainer1').css('display','');
		          			$('#coverContainer2').css('display','none');
		          		}
	          			$('input[name="cover"]').val(url);
					});
					
					//=============上传文件参数配置=============
					var Q2 = new QiniuJsSDK();
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
                        //log_level: 5,
                        filters: {
                        	mime_types : [
                        		{ title : "文件", extensions : "avi,wma,rmvb,rm,flash,mp4,mid,3gp,mp3,cda,wav,wma,flac,ape,aac,ra,rm,midi" }
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
                                console.debug('音乐文件上传完成');
                              },
                              'FileUploaded': function(up, file, info) {
                                var progress = new FileProgress(file, 'fsUploadProgress2');
                                console.debug("音乐文件上传成功响应数据:",info.response);
                                progress.setComplete(up, info.response);
                                
                                $('#uploadDetail2').dialog('close');
                                var res = $.parseJSON(info.response);
                                var url=up.getOption('domain')+'/'+res.key;
                                console.debug('====音乐文件地址：',url);
                                $('#url').val(url);
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
					var uploader2=Q2.uploader(videoUploadOption);
					
					$('#uploadVideoBtn').click(function(){
						var url=$('input[name="url"]').val();
		          		var check=$(this).is(':checked');
		          		if(check){
		          			$('#musicContainer1').css('display','none');
		          			$('#musicContainer2').css('display','');
		          		}else{
		          			$('#musicContainer1').css('display','');
		          			$('#musicContainer2').css('display','none');
		          		}
	          			$('input[name="url"]').val(url);
					});
					
					
					//=============上传歌词文件参数配置=============
					var Q3 = new QiniuJsSDK();
					var lrcUploadOption={
						disable_statistics_report: false,
                        runtimes: 'html5,flash,html4',
                        browse_button: 'pickLrc',
                        uptoken_url: '<%=basePath%>qiniu/getUptoken.do',
                        domain: 'http://${domain}',
                        get_new_uptoken: false,
                        container: 'lrcContainer',
                        drop_element: 'lrcContainer',
                        flash_swf_url: '<%=basePath%>static/js/plupload-2.1.9/js/Moxie.swf',
                        max_retries: 3,
                        dragdrop: false,
                       	max_file_size : '5mb',
                        chunk_size: '20mb',
                        auto_start: true,
                        multi_selection: false,
                        //log_level: 5,
                        filters: {
                        	mime_types : [
                        		{ title : "文件", extensions : "lrc,id3,txt,tag,srt,utf,krc" }
                        	],
                        	prevent_duplicates : true
                        },
                        init: {
                            'FilesAdded': function(up, files) {
                            	$('#uploadDetail3').dialog({
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
                                  var progress = new FileProgress(file, 'fsUploadProgress3');
                                  progress.setStatus('准备上传...');
                                  progress.bindUploadCancel(up);
                                });
                            },
                            'BeforeUpload': function(up, file) {
                                var progress = new FileProgress(file, 'fsUploadProgress3');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                if (up.runtime === 'html5' && chunk_size) {
                                  progress.setChunkProgess(chunk_size);
                                }
                              },
                              'UploadProgress': function(up, file) {
                                var progress = new FileProgress(file, 'fsUploadProgress3');
                                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                                progress.setProgress(file.percent + "%", file.speed, chunk_size);
                              },
                              'UploadComplete': function() {
                              },
                              'FileUploaded': function(up, file, info) {
                                var progress = new FileProgress(file, 'fsUploadProgress3');
                                progress.setComplete(up, info.response);
                                
                                $('#uploadDetail3').dialog('close');
                                var res = $.parseJSON(info.response);
                                var url=up.getOption('domain')+'/'+res.key;
                                $('#lrc').val(url);
                              },
                              'Error': function(up, err, errTip) {
                            	  $('#uploadDetail3').dialog({
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
                                 	var progress = new FileProgress(err.file, 'fsUploadProgress3');
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
					var uploader3=Q3.uploader(lrcUploadOption);
					
					$('#uploadLrcBtn').click(function(){
						var url=$('input[name="lrc"]').val();
		          		var check=$(this).is(':checked');
		          		if(check){
		          			$('#lrcContainer1').css('display','none');
		          			$('#lrcContainer2').css('display','');
		          		}else{
		          			$('#lrcContainer1').css('display','');
		          			$('#lrcContainer2').css('display','none');
		          		}
	          			$('input[name="lrc"]').val(url);
					});
				});
				</script>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">排序号:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="sortno" name="sortno" type="text" value="${object.sortno}" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" style="padding-top:20px;">备注:</label>
					<div class="col-xs-9" style="padding-top:10px;">
						<input id="remark" name="remark" type="text" value="${object.remark}" class="form-control">
					</div>
				</div>
				
				<div class="row" style="width:99.8%;padding-left:30px;">
				<div style="text-align: left"><label>内容:</label></div>
					<%
					MemeContent obj=(MemeContent)request.getAttribute("object");
					String content=(null!=obj)?new String(obj.getContent().getBytes(),"UTF-8"):"";
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
</html>
<script type="text/javascript" src="<%=basePath%>im/content/loadValidateRules.do"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>