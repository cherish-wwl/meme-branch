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
</head>
<body>
<div data-role="page">
	<div data-role="header">
		<a href="javascript: history.go(-1)" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
		<h1>更新签名/文章</h1>
		<div data-role="navbar">
			<ul>
				<li><a href="<%=basePath%>member/center/updateSign" class=''>签名</a></li>
				<li><a href="<%=basePath%>member/center/updateSign2" class='ui-btn-active'>文章</a></li>
			</ul>
		</div>
	</div>
	
	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="form">
	    <input type="hidden" id="type" name="type" value="${type}"/>
   		<label for="title">标题：</label>
		<input type="text" name="title" id="title" value="">
		<label for="sign">正文：</label>
		<textarea rows="10" name="content" id="content"></textarea>
		
		<button type="button" id="updateSign" class="">更新文章</button>
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
					}
				}
			});
			
			$('#updateSign').on('tap',function(){
				var content=$this.summernote('code');
				var title=$('#title').val();
				var type=$('#type').val();
				if(!(title&&content)){
					showDefaultMsg('标题和正文不能为空');
					return ;
				}
				$.ajax({
		            url: '<%=basePath%>member/center/doUpdateSign',
		            type: 'POST',
		            dataType: 'json',
					beforeSend: function(){
						showloading('true');
					},
		            data: {'title':title,'content':content,'type':type},
		            success: function(res){
						var data=res;
						if(data.state==1) {
							showDefaultMsg(data.message);
							window.location.href='http://mmgmmj.com/mgch';
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
	    </form>
	</div>
</div>
</body>
</html>
