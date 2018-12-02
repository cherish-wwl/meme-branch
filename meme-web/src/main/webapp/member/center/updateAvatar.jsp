<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@include file="/common/include/include_layui.jsp"%>
</head>
<body>
<div data-role="page">
	<div data-role="header">
		<a href="javascript: history.go(-1)" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
		<h1>更换头像</h1>
	</div>

	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="uploadForm">
			<label for="avatar">头像：</label>
      		<input type="file" data-clear-btn="true" name="avatarimg" id="avatarimg" accept="image/*">
      		<label style="color:red;">更换后如不能显示，请在1、2分钟后刷新页面</label>
			<button type="button" id="updateBtn" class="">更换头像</button>
	    </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('#updateBtn').on('tap',function(){
		var img=$('#avatarimg').val();
		if(!img){
			showDefaultMsg('请选择头像上传');
			return ;
		}
		$.ajax({
            url: '<%=basePath%>member/center/doUpdateAvatar',
            type: 'POST',
			cache: false,
			contentType: false,
			processData: false,
			beforeSend: function(){
				showloading('true');
			},
            data: new FormData($('#uploadForm')[0]),
            success: function(res){
				var data=JSON.parse(res);
				if(data.state==1) {
					//history.go(-1);
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