<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_layui.jsp"%>
<style>
html {
	background-color: #333;
}

.ui-btn.ui-input-btn.ui-corner-all.ui-shadow.ui-btn-inline {
	width: 89%;
}
</style>
</head>

<body>
<div data-role="page">
	<div data-role="header">
		<a href="http://mmgmmj.com" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">主页</a>
		<h1>会员登录</h1>
		<a href="<%=basePath%>member/cregister" class="ui-btn ui-corner-all ui-shadow ui-icon-search ui-btn-icon-left" data-ajax="false">注册</a>
	</div>
	<div data-role="main" class="ui-content">
		<label for="account">用户名：</label>
		<input type="text" name="account" id="account">
		<label for="password">密码：</label>
		<input type="password" name="password" id="password">
		<input type="button"  id="login" value="登录">
	</div>
</div>
</body>
</html>
<script type="text/javascript">
    $().ready(function(){
        $("#login").on("tap",function(){
            var $account=$("#account").val();
            var $password=$("#password").val();
            if($account==""){
                showDefaultMsg("用户名不能为空");
                return;
			}else if($password==""){
                showDefaultMsg("密码不能为空");
                return;
			}else{
                $.ajax({
                    url:'<%=basePath%>member/doLogin',
                    type:"POST",
                    data:{"account":$account,"password":$password},
                    success:function(data){
						if(data.state==1){
							<%--window.location.href='<%=basePath%>member/privates/'+data.data;--%>
							var from='${from}';
							if(from){
								window.location.href=from;
							}else{
								window.location.href='http://mmgmmj.com/hyzy';
							}
						}else{
							showDefaultMsg(data.message);
						}
                    },error:function(err){
                        showDefaultMsg("网络连接错误!");
                    }
                });
			}
		});

	});

</script>
