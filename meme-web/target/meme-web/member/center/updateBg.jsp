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
		<h1>更换背景图</h1>
	</div>

	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="uploadForm">
          	<div id="selectCon">
	          	<table style="width:100%;" border="0" cellspacing="0">
	          	<tr>
					<td style="width:70%;">
						<select id="tempid" name="tempid" >
							<option value="">选择背景模板</option>
	          				<c:if test="${!empty list}">
								<c:forEach items="${list}" var="item">
									<option url="${item.url}" value="${item.tempid}">${item.temname}</option>
								</c:forEach>
							</c:if>
	          			</select>
	          		</td>
					<td style="width:30%;"><button type="button" id="checkTemBtn">查看模板</button></td>
				</tr>
	          	</table>
          	</div>
          	<label for="checkUploadBtn">上传文件</label>
          	<input type="checkbox" name="checkUploadBtn" id="checkUploadBtn" value="">
          	<div id="container"></div>
          	<script type="text/javascript">
          	$('#checkUploadBtn').click(function(){
          		var check=$('#checkUploadBtn').is(':checked');
          		var node='';
          		$('#container').empty();
          		if(check){
          			node+='<label for="avatar">背景图上传：</label>';
          			node+='<input type="file" data-clear-btn="true" name="avatarimg" id="avatarimg" accept="image/*">';
          			$('#container').append(node);
          			$('#avatarimg').textinput();
          		}else{
          			
          		}
          	});
          	$('#checkTemBtn').click(function(){
          		var tempid=$('#tempid').val();
          		if(tempid==''){
          			showDefaultMsg('请先选择模板再查看');
          			return ;
          		}
          		var url=$('select[id="tempid"] option:selected').attr('url');
          		layui.use('layer', function(){
					var layer=layui.layer;
					layer.open({
					  type: 2,
					  title: '模板',
					  shadeClose: true,
					  shade: 0.8,
					  area: ['90%', '90%'],
					  content: '<%=basePath%>member/center/viewBgTem?tempid='+tempid
					});
				});
          	});
          	</script>
			<button type="button" id="updateBtn" class="">更换背景图</button>
	    </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('#updateBtn').on('tap',function(){
		var img=$('#avatarimg').val();
		var tempid=$('#tempid').val();
		if(!(img||tempid)){
			showDefaultMsg('请选择模板或者上传背景图');
			return ;
		}
		$.ajax({
            url: '<%=basePath%>member/center/doUpdateBg',
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