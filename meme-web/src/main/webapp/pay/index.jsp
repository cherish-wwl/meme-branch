<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.meme.pay.alipay.util.AlipayConfig" %>
<%@page import="com.meme.core.cache.ParamsCache" %>
<%@page import="com.meme.pay.wechat.util.WeChatConstants" %>
<!DOCTYPE html>
<html>
<head>
<title>订单确认</title>
<meta name="viewport" content="width=320,maximum-scale=1,user-scalable=no" />
  <meta name="screen-orientation" content="portrait" />
  <meta name="x5-orientation" content="portrait" />
  <meta name="description" />
  <meta name="keywords" />
<%-- <%@include file="/common/include/include_base.jsp"%> --%>
<link href="http://mmgmmj.com/Content/public/css/reset.mobile.css" rel="stylesheet" type="text/css"/>
<link href="http://mmgmmj.com/Designer/Content/base/css/pager.mobile.css" rel="stylesheet" type="text/css"/>
<link href="http://nwzimg.wezhan.cn/pubsf/10009/10009932/css/20341_Mobile_zh-CN.css" rel="stylesheet" type="text/css"/>
<script src="http://mmgmmj.com/Scripts/JQuery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Scripts/JQuery/jquery-ui.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Designer/Scripts/jquery.lazyload.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Designer/Scripts/smart.animation.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Designer/Content/Designer-panel/js/kino.razor.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Scripts/common.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Administration/Scripts/admin.validator.min.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Administration/Content/plugins/cookie/jquery.cookie.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Scripts/WechatStatistics.js" type="text/javascript"></script>
<script src="http://mmgmmj.com/Scripts/mobileAdapter.min.js" type="text/javascript"></script>
</head>
<body>
<center id="qboxs" style="position:absolute; margin-top:26px;  width:322px;  font-size:16px; color: #ffffff; z-index:100;">${param.subject}</center>
<center id="qboxt" style="position:absolute;  margin-top:65px; width:330px; color: #DA4494; font-size:24px; letter-spacing:2px; z-index:200;"></center>
<center id="qboxu" style="position:absolute; left:216px; margin-top:144px; width:100px; color: #6b6b6b; font-size:14px;  z-index:200;"></center>
<center id="qboxv" style="position:absolute; left:0px; margin-top:423px; width:316px; color: #ffffff; font-size:14px;  z-index:200;">确认支付</center>


<script >
var price='${param.amount}';
var subject='${subject}';
var body='${body}';
var productid='${productid}';

  var loc = location.href;
  var n1 = loc.length;
  var n2 = loc.indexOf("=");
  var id = decodeURI(loc.substr(n2+1, n1-n2));
<!-- ids 为商品名称。-->
   var ids = subject;
<!-- idt 为商品单价。-->
   var idt = price;
   var idu = price;
document.getElementById("qboxs").innerHTML = ids;
document.getElementById("qboxu").innerHTML = idu;
</script>



<style>
   .di {position:absolute;  top:6px;  left:10px;  width:300px;  height:10px; border-radius:6px; background: #e2e2e2;
}
   .ei {position:absolute;  top:140px;  left:20px;  width:22px;  height:22px; border-radius:3px; background: #8CBDE3;
z-index:100;}
   .fi {position:absolute;  top:140px;  left:120px;  width:22px;  height:22px; border-radius:3px; background: #F99FAD;
z-index:100;}
   .xt{position:absolute; border:none;  }
</style>
<blockquot class="di"></blockquot>
<hr  class="xt"  style="position:absolute; margin-top:12px;   left:16px; width:288px;  height:42px; background-color: #F24A5D; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:54px;   left:16px; width:288px;  height:62px; background-color: #F5f5f5; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:128px;   left:0px; width:320px;  height:44px; background-color: #f5f5f5; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:172px;   left:0px; width:320px;  height:6px; background-color: #f0f0f0; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:222px;   left:0px; width:320px;  height:1px; background-color: #f0f0f0; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:266px;   left:0px; width:320px;  height:1px; background-color: #f0f0f0; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:310px;   left:0px; width:320px;  height:1px; background-color: #f0f0f0; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:354px;   left:0px; width:320px;  height:1px; background-color: #f0f0f0; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:398px;   left:0px; width:320px;  height:80px; background-color: #f5f5f5; z-index:10;"  />
<hr  class="xt"  style="position:absolute;  margin-top:410px;   left:10px; border-radius:3px; width:300px;  height:38px; background-color: #2d7eb9; z-index:50;"  />
<p style="position:absolute;  margin-top:72px;   left:80px;  font-size:12px;  color: #6b6b6b; z-index:100;" >总计：</p>
<p style="position:absolute;  margin-top:100px;   left:24px;  font-size:10px;  color: #adadad; z-index:100;" >请在提交订单后30分钟内完成支付，否则系统会自动取消订单</p>
<blockquot class="ei"></blockquot>
<blockquot class="fi"></blockquot>
<div   style="position:absolute; 
margin-left:50px; margin-top:140px; width:60px; height:20px;
border-radius:2px; z-index:100;
border:1px solid #c7c7c7;"></div>
<p style="position:absolute; 
margin-left:26px; margin-top:136px; 
 font-size:30px; color: #ffffff; z-index:100;">-</p>
<p style="position:absolute; 
margin-left:125px; margin-top:141px; 
 font-size:22px; color: #ffffff; z-index:100;">+</p>

<script type="text/javascript">
var x0 = 1, y0 = 0, z0 = price;
$.extend({aa:function(){ 
if(x0>1) x0--; $("#a0").text(x0);
<!-- z0为购买商品总计金额。-->
z0 = (x0*idt).toFixed(2);
$("#qboxt").text("￥"+z0);
$("#qboxv").text("确认支付"+" "+"￥"+z0);
}});
$.extend({bb:function(){ 
x0++;  $("#a0").text(x0);
<!-- z0为购买商品总计金额。-->
z0 = (x0*idt).toFixed(2);
$("#qboxt").text("￥"+z0);
$("#qboxv").text("确认支付"+" "+"￥"+z0);
}});
$(document).ready(function(){  
$("#a0").text(x0);
$("#qboxt").text("￥"+z0);
$("#qboxv").text("确认支付"+" "+"￥"+z0);
});
</script>


<center id="a0" style="position:absolute;  margin-top:143px; width:162px; color: #6b6b6b; font-size:16px;  z-index:200;"></center>
<a style="position:absolute; 
margin-top: 137px; margin-left:17px;  width:28px;  height:28px;  z-index:300; "  onclick=$.aa(); href="javascript:void(0)"></a>
<a style="position:absolute; 
margin-top: 137px; margin-left:117px;  width:28px;  height:28px;  z-index:300; "  onclick=$.bb(); href="javascript:void(0)"></a>
<p style="position:absolute;  margin-top:144px;   left:206px;  font-size:14px;  color: #6b6b6b; z-index:100;" >单价：</p>
<p style="position:absolute;  margin-top:194px;   left:16px;  font-size:14px;  color: #a8a8a8; z-index:100;" >微信内支付方式</p>
<p style="position:absolute;  margin-top:237px;   left:60px;  font-size:15px;  color: #696969; z-index:100;" >微信支付1</p>

<img style="position:absolute;  margin-top:233px;   left:18px; width:28px;  height:24px;  z-index:100;" src="http://n.mmgmmj.com/微信支付.jpg">
<p style="position:absolute;  margin-top:282px;   left:16px;  font-size:14px;  color: #a8a8a8; z-index:100;">更多支付方式</p>
<p style="position:absolute;  margin-top:325px;   left:60px;  font-size:15px;  color: #696969; z-index:100;" >支付宝支付</p>
<p style="position:absolute;  margin-top:369px;   left:60px;  font-size:15px;  color: #696969; z-index:100;" >微信支付2</p>
<p style="position:absolute;  margin-top:371px;   left:132px;  font-size:12px;  color: #A8A8A8; z-index:100;" >（微信外浏览器、APP）</p>
<img style="position:absolute;  margin-top:320px;   left:18px; width:26px;  height:26px;  z-index:100;" src="http://n.mmgmmj.com/支付宝支付.jpg">
<img style="position:absolute;  margin-top:365px;   left:18px; width:28px;  height:24px;  z-index:100;" src="http://n.mmgmmj.com/微信支付.jpg">

<style>
.dan0{ 
position:absolute; 
margin-top:236px;
margin-left:278px;
height:16px; 
width:16px;  
z-index:200;
-webkit-appearance: radio;
}
.dan1{ 
position:absolute; 
margin-top:324px;
margin-left:278px;
height:16px; 
width:16px;  
z-index:200;
-webkit-appearance: radio;
}
.dan2{ 
position:absolute; 
margin-top:368px;
margin-left:278px;
height:16px; 
width:16px;  
z-index:200;
-webkit-appearance: radio;
}

</style>

<!--单选按钮-->
 
<input class="dan0" name="pay_type" type="radio" value="jsapiPay" /> 
<input class="dan1" name="pay_type" type="radio" value="alipay" />
<input class="dan2" name="pay_type" type="radio" value="wxH5Pay" />
<script type="text/javascript">
$('#qboxv').click(function(){
	var pay_type=$("input[name='pay_type']:checked").val();
	if(null == pay_type || typeof pay_type == 'undefined'){
		alert('请根据提示选择相应的支付方式');
		return ;
	}
	var amount=z0;
	var subject='${subject}';
	var body='${body}';
	var productid='${productid}';
	if(pay_type=='jsapiPay'){
		//微信公众号支付
		$.ajax({
    		url: '${pageContext.request.contextPath}/wechat/pay/jsapi/pay.do',
    		type:'post',
    		dataType:'json',
    		data:{'amount':amount,'subject':subject,'body':body,'productid':productid},
    		success:function(data){
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
    								url: '${pageContext.request.contextPath}/wechat/pay/jsapi/check.do',
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
					window.location.href='${pageContext.request.contextPath}/member/clogin?from='+encodeURI(url);
    			}else if(data.state == -2){
    				alert(data.message);
    				var url='<%=WeChatConstants.API_AUTHORIZE%>?appid=<%=ParamsCache.get("WEIXIN_APP_ID").getValue()%>';
					url+='&redirect_uri='+encodeURIComponent(document.location.href);
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
	}else if(pay_type =='wxH5Pay'){
		//微信h5支付
		$.ajax({
    		url: '${pageContext.request.contextPath}/wechat/pay/h5/pay.do',
    		type:'post',
    		dataType:'json',
    		data:{'amount':amount,'subject':subject,'body':body,'productid':productid},
    		success:function(data){
    			if(data.state==1){
    				window.location.href=data.data.MWEB_URL;
    			}else if(data.state==-1){
    				alert(data.message);
    				var url=window.location.href;
					window.location.href='${pageContext.request.contextPath}/member/clogin?redirect='+encodeURI(url);
    			}else{
    				alert(data.message);
    			}
    		},
    		error:function(){
    			alert('操作异常');
    		}
    	});
	}else if(pay_type =='alipay'){
		//支付宝手机网站支付
		var pams = [];  
		pams.push($('<input>', {name: 'amount', value: amount}));  
		pams.push($('<input>', {name: 'subject', value: subject}));  
		pams.push($('<input>', {name: 'body', value: body}));  
		pams.push($('<input>', {name: 'productid', value: productid}));
		var form=$('<form>', {
		    method: 'post',  
		    action: '${pageContext.request.contextPath}/pay/alipay/wap/pay.do'  
		}).append(pams);
		$(document.body).append(form);
		form.submit();
	}
});
</script>


</body>
</html>