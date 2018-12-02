<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_bootstrap.jsp"%>
</head>

<body>
<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">用户资料</h3>
  </div>
  <div class="panel-body">
    <table class="table table-bordered table-striped">
    	<tr>
    		<td>账号：</td>
    		<td>${object.account}</td>
    		<td>账号状态：</td>
    		<td>${object.accountStateText}</td>
    	</tr>
    	<tr>
    		<td>姓名：</td>
    		<td>${object.username}</td>
    		<td>昵称：</td>
    		<td>${object.nickname}</td>
    	</tr>
    	<tr>
    		<td>性别：</td>
    		<td>${object.sexText }</td>
    		<td>邮箱：</td>
    		<td>${object.email}</td>
    	</tr>
    	<tr>
    		<td>固定电话：</td>
    		<td>${object.homephone}</td>
    		<td>手机号码：</td>
    		<td>${object.cellphone}</td>
    	</tr>
    	<tr>
    		<td>所属单位：</td>
    		<td>${object.orgname}</td>
    		<td>所属部门：</td>
    		<td>${object.deptname}</td>
    	</tr>
    	<tr>
    		<td>职位：</td>
    		<td>${object.posname}</td>
    		<td>QQ：</td>
    		<td>${object.qq}</td>
    	</tr>
    	<tr>
    		<td>个性签名：</td>
    		<td colspan="3">${object.sign}</td>
    	</tr>
    	<tr>
    		<td>联系地址：</td>
    		<td colspan="3">${object.address}</td>
    	</tr>
    	<tr>
    		<td>添加时间：</td>
    		<td colspan="3"><fmt:formatDate value="${object.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	</tr>
    </table>
  </div>
</div>
</body>
</html>
