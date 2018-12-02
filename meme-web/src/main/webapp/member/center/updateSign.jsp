<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@include file="/common/include/include_layui.jsp"%>
<link href="<%=basePath%>static/js/bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="<%=basePath%>static/js/bootstrap/js/bootstrap.js"></script> 
<link href="<%=basePath%>static/js/summernote/summernote.css" rel="stylesheet">
<script src="<%=basePath%>static/js/summernote/summernote.js"></script>
<script src="<%=basePath%>static/js/summernote/lang/summernote-zh-CN.js"></script>
<style type="text/css">
.fixed-ui-title{
	
}
</style>
</head>
<body>
<div data-role="page">
	<div data-role="header" style="min-height:3.15em;">
		<a href="http://mmgmmj.com/zybj" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
		<h1 class="fixed-ui-title" style="font-size: 1em;padding:1em 0 0.7em 0;margin: 0 30%;">更新签名/文章</h1>
	</div>
	<div data-role="navbar" style="margin-top:5px;">
		<ul>
			<li><a onclick="window.location.href='<%=basePath%>member/center/updateSign?type=0'" href="javascript:void(0)" style='<c:if test="${type=='0'}">background-color: #3388cc;border-color: #3388cc;color: #fff;text-shadow: 0</c:if>'>签名</a></li>
			<li><a onclick="window.location.href='<%=basePath%>member/center/updateSign?type=1'" href="javascript:void(0)" style='<c:if test="${type=='1'}">background-color: #3388cc;border-color: #3388cc;color: #fff;text-shadow: 0</c:if>'>文章</a></li>
		</ul>
	</div>
	
	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="form">
	    <input type="hidden" id="type" name="type" value="${type}"/>
			<c:choose>
				<c:when test="${type=='0' }">
					<label for="content">签名：</label>
					<textarea rows="10" name="content" id="content"></textarea>
					<button type="button" id="updateBtn" style="margin-top: 16px">更新签名</button>
					<script type="text/javascript">
					$(document).ready(function(){
						$('#updateBtn').on('tap',function(){
							var content=$('#content').val();
							if(!content){
								showDefaultMsg('签名内容不能为空');
								return ;
							}
							var d=$('#form').serialize();
							var redirect=$('input[name="redirect"]:checked').val();
							d+='&redirect='+redirect;
							$.ajax({
					            url: '<%=basePath%>member/center/doUpdateSign',
					            type: 'POST',
					            dataType: 'json',
								beforeSend: function(){
									showloading('true');
								},
					            data: d,
					            success: function(res){
									var data=res;
									if(data.state==1) {
										showDefaultMsg(data.message);
										window.location.href='http://mmgmmj.com/zybj';
									}else {
										showDefaultMsg(data.message);
									}
					            },
					            error: function(e){
					                showDefaultMsg('操作异常，请稍后重试或联系管理员');
					            }
					        });
					   });
					});
					</script>
				</c:when>
				<c:otherwise>
					<label for="title">标题：</label>
					<input type="text" name="title" id="title" value="">
					<label for="coverimg">文章封面：</label>
      				<input type="file" data-clear-btn="true" name="coverimg" id="coverimg" accept="image/*">
					<label for="summary">文章简介：</label>
      				<textarea rows="3" name="summary" id="summary"></textarea>
					<label for="content">正文：</label>
					<textarea rows="10" name="content" id="content"  placeholder="请输入或者粘贴您需要的文章和图片"></textarea>
					
					<button type="button" id="updateBtn" style="margin-top: 16px">更新文章</button>
					<script type="text/javascript">
					$(document).ready(function(){
						var url='<%=basePath%>member/center/upload';
						var $this=$('#content');
						$this.summernote({
							height: 350,
							lang: 'zh-CN',
							callbacks: {
								onImageUpload: function(files) {
									var $files = $(files);
									$files.each(function () {
										var file = this;
										var data = new FormData();
										//将文件加入到file中，后端可获得到参数名为"file"
										data.append('file', file);
										$.ajax({
											data : data,
											type : 'post',
											url : url,
											cache : false,
											contentType : false,
											processData : false,
											success : function (res) {
												var data = JSON.parse(res);
												if(data.state==1){
													$this.summernote('insertImage', data.data);
												}else{
													
												}
											}
										});
									});
								},onFocus: function() {
                                    var content=$this.summernote('code');
                                    if(content == '<p>请输入或者粘贴您需要的文章和图片<br></p>'){
                                        $this.summernote('reset');
									}
                                    return;
                                },onBlur: function() {
                                    if ($this.summernote('isEmpty')){
                                        $this.summernote('insertText', '请输入或者粘贴您需要的文章和图片');
									}
                                    return;
                                }
							}
						}).summernote('insertText', '请输入或者粘贴您需要的文章和图片');
						$("#title").focus();
						$('#updateBtn').on('tap',function(){
							var content=$this.summernote('code');
							var title=$('#title').val();
							var type=$('#type').val();
							if(!(title&&content)){
								showDefaultMsg('标题和正文不能为空');
								return ;
							}
							var data=new FormData($('form')[0]);
							data.append('content',content);
							var redirect=$('input[name="redirect"]:checked').val();
							data.append('redirect',redirect);
							$.ajax({
					            url: '<%=basePath%>member/center/doUpdateSign',
					            type: 'POST',
					            cache: false,
								contentType: false,
								processData: false,
								beforeSend: function(){
									showloading('true');
								},
					            data: data,
					            success: function(res){
									var data=res;
									if(data.state==1) {
										showDefaultMsg(data.message);
										window.location.href='http://mmgmmj.com/zybj';
									}else {
										showDefaultMsg(data.message);
									}
					            },
					            error: function(e){
					                showDefaultMsg('操作异常，请稍后重试或联系管理员');
					            }
					        });
					   });
					});
					</script>
				</c:otherwise>
			</c:choose>
			<div style="text-align: center;margin: 0 auto;width:55%;">
				<label>会员首页显示本栏目为：</label>
				<p style="line-height:30px;"><input type="radio" name="redirect" value="0"/><label style="margin-left:-20px;">签名栏目</label></p>
				<p style="line-height:30px;"><input type="radio" name="redirect" value="1"/><label style="margin-left:-20px;">文章栏目</label></p>
			</div>
	    </form>
	</div>
</div>
</body>
</html>
