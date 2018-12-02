<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.meme.pay.alipay.util.AlipayConfig" %>
<%@page import="com.meme.core.cache.ParamsCache" %>
<!DOCTYPE html>
<html>
	<head>
	<title>微信支付结果</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@include file="/common/include/include_base.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/shop/css/weui.css"/>
<script src="<%=basePath%>static/shop/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/weui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/zepto.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/wxshop.js" type="text/javascript"></script>

</head>
<body>
<header class="am-header">
        <h1>微信支付结果</h1>
</header>
<div>
<button id="checkBtn" class="btn btn-primary btn-lg">已完成支付</button>
<button class="btn btn-danger btn-lg">支付遇到问题</button>
<script type="text/javascript">
$('#checkBtn').click(function(){
	$.ajax({
		url: '<%=basePath%>wechat/pay/h5/check.do',
		type:'post',
		dataType:'json',
		data:{'orderid':'${orderid}'},
		success:function(data){
			if(data.state==1){
				alert('支付成功');
			}else{
				alert(data.message);
			}
		},
		error:function(){
			alert('操作异常，请稍后重试或者联系管理员');
		}
	});
});
</script>
</div>
</body>
</html>