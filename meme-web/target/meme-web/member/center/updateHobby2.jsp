<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@include file="/common/include/include_layui.jsp"%>
<%--
<script type="text/javascript" src="<%=basePath%>static/js/ckeditor/ckeditor.js"></script>
--%>
<script type="text/javascript">
function changeInputType(){
	var check=$('#checkUploadBtn').is(':checked');
	var ctype=$('#ctype').val();
	if(ctype!='4'){
		if(check){
			$('#ctype_name').text('上传'+$('select[id="ctype"] option:selected').text());
		}else{
			$('#ctype_name').text($('select[id="ctype"] option:selected').text()+'文件链接地址');
		}
	}else{
		$('#ctype_name').text('文字内容');
	}
	
	$('#cover_txt').empty();
	$('#cover_val').empty();
	if(ctype==4){
	}else if(ctype==3){
		$('#cover_txt').text('图片跳转链接');
		$('#cover_val').append('<input type="text" name="extlink" id="extlink"/>');
		$('#extlink').textinput();
	}else{
		$('#cover_txt').text('封面附图');
		$('#cover_val').append('<input type="file" data-clear-btn="true" name="coverimg" id="coverimg" accept="image/*">');
		$('#coverimg').textinput();
	}
}
function checkUpload(){
	$('#con').empty();
		var node='';
		var file_type='';
		var check=$('#checkUploadBtn').is(':checked');
		if(check){
			var ctype=$('#ctype').val();
			switch(ctype){
			case '1':
				file_type='audio/*';
				node+='<input type="file" data-clear-btn="true" name="content" id="content" accept="'+file_type+'">';
				break;
			case '2':
				file_type='video/*';
				node+='<input type="file" data-clear-btn="true" name="content" id="content" accept="'+file_type+'">';
				break;
			case '3':
				file_type='image/*';
				node+='<input type="file" data-clear-btn="true" name="content" id="content" accept="'+file_type+'">';
				break;
			case '4':
				node+='<textarea rows="" cols="" name="content" id="content" class="ui-input-text"></textarea>';
				break;
			}
			$('#con').append(node);
		}else{
			node='<input type="text" name="content" id="content" value="">';
			$('#con').append(node);
		}
		$('#content').textinput();
}
</script>
</head>
<body>
<div data-role="page">
	<div data-role="header" style="height:45px;">
		<a href="javascript: history.go(-1)" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
		<h1>发布栏目内容</h1>
	</div>

	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="form">
	    	<input type="hidden" id="type" name="type" value="${type}"/>
	    	<table style="width:100%;" border="0" cellspacing="0">
    			<tr><td style="width:70%;">栏目标题</td><td style="width:30%;">栏目类型</td></tr>
    			<tr>
    			<td>
	    			<table>
		    			<tr>
			    			<td>
			    			<select id="typ" name="typ" style="width:60%;">
								<c:if test="${!empty list}">
									<c:forEach items="${list}" var="item">
										<option value="${item.dictitemcode}" <c:if test="${type==item.dictitemcode }">selected="selected"</c:if>>${item.dictitemname}</option>
									</c:forEach>
								</c:if>
								<option value="">自定义</option>
							</select>
							</td>
							<td id="alias_td"></td>
						</tr>
	    			</table>
			    	<script type="text/javascript">
			    	$('#typ').change(function(){
						var type=$('#typ').val();
						if(type==''){
							var alias='';
							alias+='<input type="text" id="alias" name="alias" value="" placeholder="请填写自定义栏目标题">';
							$('#alias_td').empty();
							$('#alias_td').append(alias);
							$('#alias').textinput();
						}else{
							window.location.href='<%=basePath%>member/center/updateHobby?type='+type;
						}
			    	});
			    	</script>
    			</td>
    			<td>
	    			<express:SelectTag id="ctype" name="ctype" type="select" selected="${ctype}" dictGroupCode="ARTICLE_CONTENT_TYPE"/>
	    			<script type="text/javascript">
	    			$('#ctype').change(function(){
	    				changeInputType();
	    				checkUpload();
	    			});
	    			</script>
    			</td>
    			</tr>
    		</table>
			
			<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="width:50%;">更新${typename}栏目内容：</td>
					<td style="width:50%;">
						<label for="checkUploadBtn">上传文件</label>
          				<input type="checkbox" name="checkUploadBtn" id="checkUploadBtn" value="">
          				<script type="text/javascript">
          				$('#checkUploadBtn').click(function(){
          					checkUpload();
          				});
          				</script>
					</td>
				</tr>
				<tr><td style="padding-right:5px;" id="ctype_name">音频文件</td><td style="padding-left:5px;">标题</td></tr>
				<tr>
					<td style="padding-right:5px;" id="con"><input type="text" name="content" id="content" value=""></td>
					<td style="padding-left:5px;"><input type="text" name="title" id="title" value=""></td>
				</tr>
				<tr><td style="padding-right:5px;">备注文字</td><td style="padding-left:5px;" id="cover_txt">封面附图</td></tr>
				<tr>
					<td style="padding-right:5px;"><input type="text" name="summary" id="summary" value=""></td>
					<td style="padding-left:5px;" id="cover_val"><input type="file" data-clear-btn="true" name="coverimg" id="coverimg" accept="image/*"></td>
				</tr>
			</table>
			
			<button type="button" id="publish" class="">确认提交</button>
	    </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
//CKEDITOR.replace('content');
$(document).ready(function(){
	changeInputType();
	<%--var url='<%=basePath%>member/center/upload';
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
	--%>
	$('#publish').on('tap',function(){
		var title=$('#title').val();
		if(!title){
			showDefaultMsg('标题不能为空');
			return ;
		}
		var type='${type}';
		var ctype=$('#ctype').val();
		//var content=$this.summernote('code');
		//var summary=$('#summary').val();
		//var extlink=$('#extlink').val();
		
		$.ajax({
            url: '<%=basePath%>member/center/doUpdateHobby',
            type: 'POST',
			cache: false,
			contentType: false,
			processData: false,
			beforeSend: function(){
				showloading('true');
			},
            data: new FormData($('#form')[0]),
            success: function(res){
            	var data=JSON.parse(res);
				if(data.state==1) {
					showDefaultMsg(data.message);
					window.location.href='http://mmgmmj.com/wybj';
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