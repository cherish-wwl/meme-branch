<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.meme.pay.alipay.util.AlipayConfig" %>
<%@page import="com.meme.core.cache.ParamsCache" %>
<%@page import="com.meme.pay.wechat.util.WeChatConstants" %>
<!DOCTYPE html>
<html>
	<head>
	<title>微信公众号支付接口</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@include file="/common/include/include_base.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/shop/css/weui.css"/>
<script src="<%=basePath%>static/shop/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/weui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/zepto.min.js" type="text/javascript"></script>
<script src="<%=basePath%>static/shop/js/wxshop.js" type="text/javascript"></script>
<style>
    *{
        margin:0;
        padding:0;
    }
    ul,ol{
        list-style:none;
    }
    body{
        font-family: "Helvetica Neue",Helvetica,Arial,"Lucida Grande",sans-serif;
    }
    .hidden{
        display:none;
    }
    .new-btn-login-sp{
        padding: 1px;
        display: inline-block;
        width: 75%;
    }
    .new-btn-login {
        background-color: #02aaf1;
        color: #FFFFFF;
        font-weight: bold;
        border: none;
        width: 100%;
        height: 30px;
        border-radius: 5px;
        font-size: 16px;
    }
    #main{
        width:100%;
        margin:0 auto;
        font-size:14px;
    }
    .red-star{
        color:#f00;
        width:10px;
        display:inline-block;
    }
    .null-star{
        color:#fff;
    }
    .content{
        margin-top:5px;
    }
    .content dt{
        width:100px;
        display:inline-block;
        float: left;
        margin-left: 20px;
        color: #666;
        font-size: 13px;
        margin-top: 8px;
    }
    .content dd{
        margin-left:120px;
        margin-bottom:5px;
    }
    .content dd input {
        width: 85%;
        height: 28px;
        border: 0;
        -webkit-border-radius: 0;
        -webkit-appearance: none;
    }
    #foot{
        margin-top:10px;
        position: absolute;
        bottom: 15px;
        width: 100%;
    }
    .foot-ul{
        width: 100%;
    }
    .foot-ul li {
        width: 100%;
        text-align:center;
        color: #666;
    }
    .note-help {
        color: #999999;
        font-size: 12px;
        line-height: 130%;
        margin-top: 5px;
        width: 100%;
        display: block;
    }
    #btn-dd{
        margin: 20px;
        text-align: center;
    }
    .foot-ul{
        width: 100%;
    }
    .one_line{
        display: block;
        height: 1px;
        border: 0;
        border-top: 1px solid #eeeeee;
        width: 100%;
        margin-left: 20px;
    }
    .am-header {
        display: -webkit-box;
        display: -ms-flexbox;
        display: box;
        width: 100%;
        position: relative;
        padding: 7px 0;
        -webkit-box-sizing: border-box;
        -ms-box-sizing: border-box;
        box-sizing: border-box;
        background: #1D222D;
        height: 50px;
        text-align: center;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        box-pack: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        box-align: center;
    }
    .am-header h1 {
        -webkit-box-flex: 1;
        -ms-flex: 1;
        box-flex: 1;
        line-height: 18px;
        text-align: center;
        font-size: 18px;
        font-weight: 300;
        color: #fff;
    }
</style>
</head>
<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
<header class="am-header">
        <h1>微信公众号支付接口</h1>
</header>
<div id="main">
        <form name='alipayment' action='<%=basePath%>wechat/pay/h5/pay.do' method=post target="_blank">
            <div id="body" style="clear:left">
                <dl class="content">
                    <dt>订单名称：</dt>
                    <dd>
                        <input id="subject" name="subject" value="测试商品"/>
                    </dd>
                    <hr class="one_line">
                    <dt>付款金额：</dt>
                    <dd>
                        <input id="amount" name="amount" value="0.01"/>
                    </dd>
                    <hr class="one_line"/>
                    <dt>商品编号：</dt>
                    <dd>
                        <input id="productid" name="productid" value="100000000000000000"/>
                    </dd>
                    <hr class="one_line"/>
                    <dt>商品描述：</dt>
                    <dd>
                        <input id="body" name="body" value="支付测试商品0.01元"/>
                    </dd>
                    <hr class="one_line">
                    <dt></dt>
                    <dd id="btn-dd">
                        <span class="new-btn-login-sp">
                            <button class="new-btn-login" id="payBtn" type="button" style="text-align:center;">确 认</button>
                        </span>
                        <span class="note-help">如果您点击“确认”按钮，即表示您同意该次的执行操作。</span>
                    </dd>
                    <script type="text/javascript">
                    $('#payBtn').click(function(){
                    	$.ajax({
                    		url: '<%=basePath%>wechat/pay/jsapi/pay.do',
                    		type:'post',
                    		dataType:'json',
                    		data:$('form').serialize(),
                    		beforeSend: function(){
                    			loading_toast('正在提交支付');
                    		},
                    		success:function(data){
                    			clearToast();
                    			if(data.state==1){
                    				WeixinJSBridge.invoke(
                    					'getBrandWCPayRequest', {
                    						"appId":data.data.appId,
                    						"timeStamp":data.data.timeStamp,
                    						"nonceStr":data.data.nonceStr,
                    						"package":data.data.pkg,
                    						"signType":data.data.signType,
                    						"paySign":data.data.paySign
                    					},
                    					function(res){
                    						if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    							//调用查询订单接口确认订单是否支付成功
                    							$.ajax({
                    								url: '<%=basePath%>wechat/pay/jsapi/check.do',
                    								type:'post',
                    								dataType:'json',
                    								data:{'orderid':data.data.orderid},
                    								success:function(d){
                    									if(d.state==1){
                    										alert(d.message);
                    									}else{
                    										alert(d.message);
                    									}
                    								},
                    								error:function(){
                    									alert('操作异常');
                    								}
                    							});
                    						}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    							alert('已取消');
                    						}else if(res.err_msg=="get_brand_wcpay_request:fail"){
                    							alert('支付失败，请稍后重试或者联系管理员处理');
                    						}
                    					}
                    				); 
                    			}else if(data.state == -1){
                    				alert(data.message);
                    				var url=window.location.href;
                					window.location.href='<%=basePath%>member/clogin?from='+encodeURI(url);
                    			}else if(data.state == -2){
                    				alert(data.message);
                    				var url='<%=WeChatConstants.API_AUTHORIZE%>?appid=<%=ParamsCache.get("WEIXIN_APP_ID").getValue()%>';
                					url+='&redirect_uri='+encodeURI(document.location.href);
                					url+='&response_type=code';
                					url+='&scope=snsapi_userinfo';
                					url+='&state=STATE#wechat_redirect';
                					window.location.href=url;
                    			}else{
                    				alert(data.message);
                    			}
                    		},
                    		error:function(){
                    			alert('操作异常');
                    		}
                    	});
                    });
                    </script>
                </dl>
            </div>
		</form>
        <div id="foot">
			<ul class="foot-ul">
				<li>
					<%=ParamsCache.get("COPY_RIGHT").getValue()%>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>