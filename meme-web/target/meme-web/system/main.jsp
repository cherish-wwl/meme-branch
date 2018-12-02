<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/common/include/include_easyui.jsp"%>
<link href="<%=basePath %>static/css/system-main.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.tree .tree-title{
	height: 25px;line-height: 25px;
}
.tree .tree-node-selected{
	height: 25px;line-height: 25px;
}
.tree .tree-node{
	height: 25px;line-height: 25px;
}
.tree .tree-file{
	margin-top:3px;
}
.tree li span label{
	cursor:pointer;
}
.tree a{
	text-decoration:none;
}
.tree li a:link{
	color:black;
}
.tree li a:visited{
	color:black;
}
.tree li a:hover{
	color:black;
}
.tree li a:active{
	color:black;
}
</style>
</head>
<body class="easyui-layout">
	<!-- 顶部栏开始 -->
	<div data-options="region:'north',border:false" style="height:70px;background:#B3DFDA;">
		<div class="logo_container">
			<div class="info">
				<span>账号：${sessionScope.user.account}</span>
				<span>姓名：${sessionScope.user.username}</span>
				<span>角色：</span>
				<c:if test="${!empty sessionScope.roles}">
					<select id="testop">
					<c:forEach items="${sessionScope.roles}" var="item">
						<option>${item.name}</option>
					</c:forEach>
					</select>
				</c:if>
			</div>
			<div class="logo">
				<img alt="" src="<%=basePath%>resources/organization/logo/hx.png">
				<span>${sessionScope.user.orgname}</span>
			</div>
		</div>
		<div class="plist">
			<div class="plist_item" onclick="window.location.href='<%=basePath%>system/logout.do'">退出登录</div>
			<div class="plist_item">个人设置</div>
			<div id="platformList">
			<c:if test="${!empty platforms}">
				<c:forEach items="${platforms}" var="item" varStatus="status">
					<div id="platform${item.id}" onclick="switchPlatform('${item.id}','${item.name}')" class="plist_item <c:if test="${status.index==0}">active</c:if>">${item.name}</div>
				</c:forEach>
			</c:if>
			</div>
		</div>
	</div>
	<!-- 顶部栏结束 -->
	
	<!-- 左侧菜单栏开始 -->
	<div id="westRegion" title="${defaultplatform.name}" data-options="region:'west',hide:true,split:true" style="width:200px;">
		<div id="leftMenuList" class="easyui-accordion" fit="true" border="false">
		<%--
			${menus}
		--%>
		</div>
	</div>
	<!-- 左侧菜单栏结束 -->
	
	<!-- 底部栏开始 -->
	<div data-options="region:'south',border:false" style="text-align:center;height:30px;line-height:30px;background:#B3DFDA;">
		<span><%=ParamsCache.get("COPY_RIGHT").getValue()%></span>
	</div>
	<!-- 底部栏结束 -->
	
	<!-- 中间栏开始 -->
	<div data-options="region:'center',border:false">
		<div id="menuTabs" class="easyui-tabs" data-options="" style="width:100%;height:100%">
			<div title="我的主页" data-options="closable:false" style="overflow:hidden">
				<%--
				<iframe scrolling="auto" frameborder="0"  src="<%=basePath%>system/home.do" style="width:100%;height:100%;"></iframe>
				--%>
				<iframe scrolling="auto" frameborder="0"  src="<%=basePath%>system/home.do" style="width:100%;height:100%;"></iframe>
			</div>
		</div>
		<div id="tab_menu" class="easyui-menu" style="width:150px;">
			<div id="tabrefresh" data-options="name:6,iconCls:'icon-reload'">刷新</div>
			<div id="tabclose" data-options="name:1">关闭</div>
			<div id="tabcloseall" data-options="name:2">全部关闭</div>
			<div id="tabcloseother" data-options="name:3">除此之外全部关闭</div>
			<div id="tabcloseleft" data-options="name:5">当前页左侧全部关闭</div>
			<div id="tabcloseright" data-options="name:4">当前页右侧全部关闭</div>
		</div>
	</div>
	<!-- 中间栏结束-->
	
</body>
</html>
<script type="text/javascript">
/**
 * 切换平台
 */
function switchPlatform(platformid,platformname){
	var panels=$('#leftMenuList').accordion('panels');
	var length=panels.length;
	for(var i=0;i<length;i++){
		$('#leftMenuList').accordion('remove',0);
	}
	//切换左侧菜单栏标题
	$('body').layout('panel','west').panel('setTitle',platformname);
	$('#platformList').children('.active').removeClass('active');
	$('#platform'+platformid).addClass('active');
	loadMenu($('#menuTabs'),$('#leftMenuList'),platformid,'<%=basePath%>system/initTopMenu.do','<%=basePath%>system/initSubMenu.do');
}
/**
 * 右键菜单处理
 */
function menuHandler(menu, type) {
    var allTabs = $('#menuTabs').tabs('tabs');
    var allTabtitle = [];
    $.each(allTabs, function (i, n) {
        var opt = $(n).panel('options');
        if (opt.closable)
            allTabtitle.push(opt.title);
    });
    var curTabTitle = $(menu).data('tabTitle');
    var curTabIndex = $('#menuTabs').tabs('getTabIndex', $('#menuTabs').tabs('getTab', curTabTitle));
    switch (type) {
        case 1:
            $('#menuTabs').tabs('close', curTabIndex);
            return false;
            break;
        case 2:
            for (var i = 0; i < allTabtitle.length; i++) {
                $('#menuTabs').tabs('close', allTabtitle[i]);
            }
            break;
        case 3:
            for (var i = 0; i < allTabtitle.length; i++) {
                if (curTabTitle != allTabtitle[i])
                    $('#menuTabs').tabs('close', allTabtitle[i]);
            }
            $('#menuTabs').tabs('select', curTabTitle);
            break;
        case 4:
            for (var i = curTabIndex; i < allTabtitle.length; i++) {
                $('#menuTabs').tabs('close', allTabtitle[i]);
            }
            $('#menuTabs').tabs('select', curTabTitle);
            break;
        case 5:
            for (var i = 0; i < curTabIndex-1; i++) {
                $('#menuTabs').tabs('close', allTabtitle[i]);
            }
            $('#menuTabs').tabs('select', curTabTitle);
            break;
        case 6: //刷新
        	var currTab = $('#menuTabs').tabs('getTab', curTabTitle);
        	var iframe = $(currTab.panel('options').content);
        	var src = iframe.attr('src');
        	$('#menuTabs').tabs('update', { tab: currTab, options: { content: createFrame(src)} });
            break;
    }

}
function tabClose(){
	var tab=$('#menuTabs').tabs('getSelected');
	var index = $('#menuTabs').tabs('getTabIndex',tab);
	$('#menuTabs').tabs('close',index);
}
$(function(){
	loadMenu($('#menuTabs'),$('#leftMenuList'),null,'<%=basePath%>system/initTopMenu.do','<%=basePath%>system/initSubMenu.do');
	$('#menuTabs').tabs({
		onContextMenu:function(e, title,index){
			e.preventDefault();
			if(index>0){
				$('#tab_menu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data('tabTitle', title);
			}
		}
	});
	$('#tab_menu').menu({
        onClick:function (item) {
        	menuHandler(this, item.name);
        }
    });
});
</script>
