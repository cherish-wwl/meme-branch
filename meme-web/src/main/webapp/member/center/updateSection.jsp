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
		<h1>更换栏目标题</h1>
	</div>

	<div data-role="main" class="ui-content">
	    <form id="form">
			<label for="sign">标题：</label>
			<input type="text" name="sectionname" id="sectionname" value="">
			
			<button type="button" id="updateBtn" class="">确认提交</button>
	    </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('#updateBtn').on('tap',function(){
		var sectionname=$('#sectionname').val();
		if(!sectionname){
			showDefaultMsg('请填写栏目标题');
			return ;
		}
		$.ajax({
            url: '<%=basePath%>member/center/doUpdateSection',
            type: 'POST',
			beforeSend: function(){
				showloading('true');
			},
            data:{'sectionname': sectionname},
            success: function(data){
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