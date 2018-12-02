<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_layui.jsp"%>

<link href="<%=basePath%>static/js/validation/css/validation.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>static/js/validation/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/additional-methods.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/localization/messages_zh.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/validation/validate-func.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.validate.method.extend.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.cascade.select.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/express.map.js" type="text/javascript"></script>
<style>
html{background-color: #333;}
#sign{ height:90px!important;}
.ui-popup-container.ui-popup-active{ margin:auto; width:50%;height:50%;left:0!important;right:0!important; top:0px!important;bottom:0px !important;max-width:200px;}
</style>
</head>

<body>
<div data-role="page">
  <div data-role="header">
  	<a href="http://mmgmmj.com" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">主页</a>
    <h1>么么聊-注册</h1>
  </div>

  <div data-role="main" class="ui-content">
    <form enctype="multipart/form-data" id="uploadForm">
      <label for="account">用户名：</label>
      <input type="text" name="account" id="account" value="">
      
      <label for="mpassword">密码：</label>
      <input type="password" name="mpassword" id="mpassword" value="">
      

      <label for="nickname">昵称：</label>
      <input type="text" name="nickname" id="nickname" value="">

      <label for="sign">个性签名：</label>
      <input type="text" name="sign" id="sign" value="" placeholder="个性签名">
      
      <label for="sex">性别：</label>
      <express:SelectTag id="sex" name="sex" type="select" dictGroupCode="SEX" defaultOption="性别"/>
      
      <label for="email">邮箱：</label>
      <input type="text" name="email" id="email" value="">
      
      <label for="cellphone">手机：</label>
      <input type="text" name="cellphone" id="cellphone" value="">
      
      <label for="avatarimg">头像：</label>
      <input type="file" data-clear-btn="true" name="avatarimg" id="avatarimg">
      
      <label for="domain">域名：</label>
      <input name="domain" id="domain" placeholder="http://mmgmmj.com/XXX，请填写域名后缀XXX，不填写默认取用户名为后缀"/>
      
      <div class="ui-input-btn ui-btn ui-corner-all ui-shadow">
      	<input type="button" data-inline="true" id="btn-register" value="注册">
      	<input type="reset" data-inline="true"  value="重置">
      	<input type="button" data-inline="true" onclick="window.location.href='<%=basePath%>member/clogin'" value="已有账号登录">
      </div>
    </form>
  </div>
</div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>member/loadValidateRules"></script>
<script type="text/javascript" src="<%=basePath%>static/js/express.validate.js"></script>
<script type="text/javascript">
    $().ready(function(){
        var checkSubmit=function() {

            var tsDiv = $("#tsDiv");
            var tsMode = $("#tsMode");

            var account = $("#account").val();
            var passWord = $("#passWord").val();
            var nickName = $("#nickName").val();
            var sign = $("#sign").val();
            var avatar = $("#avatar");

            if (account.length == 0) {
                showDefaultMsg("请填写账号");
                return false;
            }
            if (passWord.length < 6) {
                showDefaultMsg("请设置6位以上的密码");
                return false;
            }
            if (nickName.length == 0) {
                showDefaultMsg("请填写昵称");
                return false;
            }
            if (avatar[0].files.length == 0) {
                showDefaultMsg("请上传头像");
                return false;
            } else if(avatar[0].files.length > 1){
                showDefaultMsg("只能上传一张图片");
                return false;
            } else {
                var fileName=avatar[0].files[0].name;
                if (!/\.(jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)){
                    showDefaultMsg("只能上传jpg,jpeg,png类型图片");
                    return false;
                }
                if(getPhotoSize(avatar[0])>5000){
                    showDefaultMsg("上传图片大小限制小于5M");
                    return false;
                }
            }
            return true;
        }
        $("#btn-register").on("tap",function(){
        	var validateObj=validate($('#uploadForm'));
			if(validateObj.form()){
                showloading("true");
                $.ajax({
                    url:'<%=basePath%>member/doRegister',
                    type:"POST",
					cache:false,
					contentType:false,
					processData:false,
                    data:new FormData($("#uploadForm")[0]),
                    success:function(res){
						var data=JSON.parse(res);
						if(data.state==1){
							<%--window.location.href='<%=basePath%>member/privates/'+data.data;--%>
							window.location.href='<%=basePath%>index.html';
						}else {
							showDefaultMsg(data.message);
						}
                    },error:function(err){
                        showDefaultMsg("网络连接错误");
                    }
                });
			}
	   }) ;
	});

	window.onload = function() {
        var loading = "${showloading}";
		showMsg();
        showloading(loading);
	};
</script>
