<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
<script src="<%=basePath%>static/js/zeroclipboard/jquery.zclip.min.js" type="text/javascript"></script>
<style type="text/css">
.tree .tree-title{height: 25px;line-height: 25px;}
.tree .tree-node-selected{height: 25px;line-height: 25px;}
.tree .tree-node{height: 25px;line-height: 25px;}
.tree .tree-file{margin-top:3px;}
.tree li span label{cursor:pointer;}
.tree a{text-decoration:none;}
.tree li a:link{color:black;}
.tree li a:visited{color:black;}
.tree li a:hover{color:black;}
.tree li a:active{color:black;}

.bucket_select{width:98%;padding-left:2%;}
.bucket_select select{width:100%;}
.top_dir{width:98%;padding-left:2%;height: 25px;line-height: 25px;font-size:13px;border-top:1px solid #ddd;border-bottom:1px solid #ddd;}
.top_dir a{width:50%;display:block;text-decoration: none;color:black;float:left;}
.floor{width: 100%;background-color: #fff;display: block;padding: 0;font-family: Microsoft Yahei,Helvetica,Arial;}
.floor .pic {width: 100%;overflow: hidden;padding:0;}
.floor .pic dd {width:260px;height:300px;margin-left:10px;margin-top:10px;float: left;display: block;border: 1px solid #eee;box-sizing: border-box;overflow: hidden;}
.floor .pic dd .pic_btn a{text-decoration: none;color:black;}
.floor .pic dd img{width:100%;height:80%;}
.floor .pic dd div a{margin-left:10px;}
.floor .pic dd .ptitle{height:10%;line-height:10%;padding:0;margin:0;text-align:center;}
.floor .pic dd .ptitle span{height:100%;line-height:100%;}
.floor .pic dd .pic_btn{height:10%;line-height:10%;}
.tool_bar{width:99.8%;height:35px;line-height:35px;display:inline;position: fixed;z-index: 1;background-color:#ddedfb}
.filelist{width:99.8%;height:auto;margin-top:30px;}
.tool_bar .tool_btn{height:100%;display: inline;float:left;margin-left:10px;}
</style>
</head>

<body class="easyui-layout">
	<div data-options="region:'west',split:true,collapsible:true" title="文件目录" style="width:17%;">
		<div class="bucket_select">
			<express:SelectTag id="bucket" name="bucket" type="select" selected="wxshop" dictGroupCode="QINIU_STORAGE_NAME" onchange="selectBucket()"/>
		</div>
		<div class="top_dir">
			<a href="javascript:topDir();">顶级目录</a>
			<input onclick="addTopDir()" type="button" class="easyui-linkbutton" style="float:right;width:50%;" value="创建顶级目录">
		</div>
		<ul id="list">
		</ul>
	</div>
	<div data-options="region:'center',split:true,collapsible:false" title="文件列表" style="width:80%;">
		<div class="tool_bar">
			<div class="tool_btn"><input type="checkbox" id="selectAllBtn" onclick="selectAll(this)"/>全选</div>
			<div class="tool_btn"><a class="easyui-linkbutton" style="width:50px;margin-top:4px;" onclick="refresh()">刷新</a></div>
			<div class="tool_btn"><a class="easyui-linkbutton" style="width:50px;margin-top:4px;" onclick="delFile()">删除</a></div>
			<div class="tool_btn"><button id="uploadBtn">上传</button></div>
		</div>
		<div class="filelist">
			<section class="floor">
    		<dl class="pic" id="pic">
    			<%--<dd>
    				<a href="#"><img src="http://onxz47x9k.bkt.clouddn.com/shopcar-ph01.png"></a>
    				<div style="height:10%;line-height:10%;">
    					<input type="checkbox"/>
    					<a href="#">复制链接</a>
    					<a href="#">替换</a>
    					<a href="#">删除</a>
    					<a href="#">详情</a>
    				</div>
    			</dd> --%>
    		</dl>
    	</section>
		</div>
	</div>
	<div id="m_menu" class="easyui-menu" style="width:150px;">
		<div id="addBrother" data-options="type:1,iconCls:'icon-add'">创建子目录</div>
		<div id="addChild" data-options="type:2,iconCls:'icon-add'">创建同级目录</div>
		<div id="modify" data-options="type:3,iconCls:'icon-edit'">重命名</div>
		<div id="del" data-options="type:4,iconCls:'icon-remove'">删除</div>
	</div>
	<div id="win">
		<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
var listObj=$('#list');
var winObj=$('#win');
var rmenuObj=$('#m_menu');
var bucketObj=$('#bucket');
var selectedNode=null;
function reload(){
	var bucket=bucketObj.val();
	listObj.tree('options').queryParams={'bucket':bucket};
	listObj.tree('options').url = '<%=basePath%>file/space/dir/list.do';
	listObj.tree('reload');
}
function topDir(){
	selectedNode=null;
	listObj.find('.tree-node-selected').removeClass('tree-node-selected');
	loadImage(bucketObj.val(),'');
}
function addTopDir(){
	var options={'title':'创建顶级目录','width':400,'height':200,'callback':function(){reload();}};
	var pid=0;
	var bucket=bucketObj.val();
	var url='<%=basePath%>file/space/editView.do?pid='+pid+'&bucket='+bucket;
	var action='<%=basePath%>file/space/add.do';
	edit(listObj,winObj,url,action,options);
}
function addChild(node){
	var options={'title':'创建子目录','width':400,'height':200,'callback':function(){reload();}};
	var pid=node.id;
	var bucket=bucketObj.val();
	var url='<%=basePath%>file/space/editView.do?pid='+pid+'&bucket='+bucket;
	var action='<%=basePath%>file/space/add.do';
	edit(null,winObj,url,action,options);
}
function addBrother(node){
	var options={'title':'创建同级目录','width':400,'height':200,'callback':function(){reload();}};
	var pid=node.attributes.pid;
	var bucket=bucketObj.val();
	var url='<%=basePath%>file/space/editView.do?pid='+pid+'&bucket='+bucket;
	var action='<%=basePath%>file/space/add.do';
	edit(null,winObj,url,action,options);
}
function rename(node){
	var options={'title':'重命名','width':400,'height':200,'callback':function(){reload();}};
	var pid=node.attributes.pid;
	var id=node.id;
	var bucket=bucketObj.val();
	var url='<%=basePath%>file/space/editView.do?pid='+pid+'&bucket='+bucket+'&id='+id;
	var action='<%=basePath%>file/space/edit.do';
	edit(null,winObj,url,action,options);
}
function del(node){
	var ids=new Array();
	ids.push(node.id);
	$.messager.confirm('提示', '再次确认是否删除选中当前目录(注意：删除目录的同时会删除七牛云空间中的文件)？', function(state){
		if (state){
			$.ajax({
				url: '<%=basePath%>file/space/delete.do',
				type:'post',
				dataType:'json',
				data:{'ids':ids},
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						reload();
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
		}
	});
}
function selectBucket(){
	selectedNode=null;
	initTree(bucketObj.val());
	loadImage(bucketObj.val(),'');
}
function menuHandler(menuObj, type) {
	var node=listObj.tree('getSelected');
    switch (type) {
        case 1:
        	addChild(node);
            break;
        case 2:
        	addBrother(node);
            break;
        case 3:
        	rename(node);
            break;
        case 4:
        	del(node);
            break;
    }
}

function delFile(hash){
	var keys=new Array();
	if(hash){
		var key=$('#'+hash).val();
		keys.push(key);
	}else{
		$('input[name="filekey"]').each(function(){
			if($(this).prop('checked')) keys.push($(this).val());
		});
	}
	if(keys.length==0){
		$.messager.alert('提示','请选择要删除的文件','error');
		return ;
	}
	$.messager.confirm('提示', '再次确认是否删除选中文件？', function(state){
		if (state){
			$.ajax({
				url: '<%=basePath%>file/space/deleteFile.do',
				type:'post',
				dataType:'json',
				data:{'bucket':bucketObj.val(),'keys':keys},
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						refresh();
						$('#selectAllBtn').removeAttr('checked');
					}else if(data.state==0){
						$.messager.alert('提示',data.message,'error');
					}
				},
				error:function(){
					$.messager.alert('提示','操作失败','error');
				}
			});
		}
	});
}

function copy(copyBtnObj){
	$(copyBtnObj).zclip({
		path:'<%=basePath%>static/js/zeroclipboard/ZeroClipboard.swf',
		copy:$(copyBtnObj).attr('data-clipboard-text')
	});
	//window.clipboardData.setData("Text",$(copyBtnObj).attr('data-clipboard-text'));
	//var clip = new ZeroClipboard(copyBtnObj);
}

function loadImage(bucket,prefix){
	$('#pic').empty();
	$.ajax({
		url: '<%=basePath%>file/space/file/list.do',
		type:'post',
		dataType:'json',
		data:{'bucket':bucket,'prefix':prefix},
		success:function(data){
			if(data.state==1){
				var imgs=data.data;
				if(imgs){
					var dd='';
					for(var i=0;i<imgs.length;i++){
						dd+='<dd>';
						dd+='<a href="#"><img src="'+imgs[i].url+'"></a>';
						dd+='<div class="ptitle"><span>'+imgs[i].filename+'</span></div>';
						dd+='<div class="pic_btn">';
							dd+='<input type="checkbox" name="filekey" id="'+imgs[i].hash+'" value="'+imgs[i].key+'"/>';
							dd+='<a href="#" data-clipboard-text="'+imgs[i].url+'" onclick="copy(this)">复制链接</a>';
							dd+='<a href="#">替换</a>';
							dd+='<a href="#" onclick="delFile(\''+imgs[i].hash+'\')">删除</a>';
							dd+='<a href="#">详情</a>';
						dd+='</div>';
						dd+='</dd>';
					}
					$('#pic').append(dd);
				}
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
		},
		error:function(){
			$.messager.alert('提示','操作失败','error');
		}
	});
}
function initTree(bucket){
	listObj.tree({
		lines: true,
		url: '<%=basePath%>file/space/dir/list.do',
	    queryParams: {'bucket':bucket},
	    loadFilter: function (data) {
	    	return data;
	    },
	    onSelect:function(node){
	    	//alert(node.attributes.fulldir);
	    	if(selectedNode==null||node.id!=selectedNode.id){
	    		selectedNode=node;
		    	loadImage(bucket,selectedNode.attributes.fulldir);
		    	initUpload(selectedNode.attributes.fulldir);
	    	}
	    	/*else{
	    		var selected=listObj.tree('getSelected');
	    		if(selected!=null){
	    			if(node.id==selected.id) {
	    				selectedNode=null;
	    				listObj.find('.tree-node-selected').removeClass('tree-node-selected');
	    			}
	    		}
	    		loadImage(bucket,'');
		    	initUpload('');
	    	}*/
	    },
	    onBeforeExpand: function(node){
	    	listObj.tree('options').queryParams={'bucket':bucket,'pid':node.id};
	    	listObj.tree('options').url = '<%=basePath%>file/space/dir/list.do';
	    },
	    onContextMenu: function(e,node){
	    	e.preventDefault();
	    	listObj.tree('select', node.target);
	    	rmenuObj.menu('show', {
				left: e.pageX,
				top: e.pageY
			});
	    }
	});
}
function refresh(){
	var selectedNode=listObj.tree('getSelected');
	var prefix='';
	if(null!=selectedNode&&typeof selectedNode !='undefined') prefix=selectedNode.attributes.fulldir;
	loadImage(bucketObj.val(),prefix);
}
function initUpload(prefix){
	$('#uploadBtn').uploadify({
		'multi':true,
		'formData': {
			'jsessionid':'${cookie.JSESSIONID.value}',
			'bucket':bucketObj.val(),
			'prefix':prefix
		},
		'buttonText':'上传',
		'fileTypeExts' : '*.gif;*.jpg;*.jpeg;*.png;*.bmp;',
		'swf'      : '<%=basePath%>static/js/uploadify/uploadify.swf',
		'uploader' : '<%=basePath%>file/space/upload.do',
		'onUploadSuccess' : function(file, data, response) {
			data = JSON.parse(data);
			if(data.state==1){
				//$.messager.alert('提示',data.message,'info');
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
	    }
	});
}
function selectAll(selectBtnObj){
	var checked=$(selectBtnObj).prop('checked');
	if(checked){
		$('input[name="filekey"]').prop('checked','checked');
	}else{
		$('input[name="filekey"]').removeAttr('checked');
	}
}
$(function(){
	rmenuObj.menu({
        onClick:function (item) {
        	menuHandler(this, item.type);
        }
    });
	initTree(bucketObj.val());
	loadImage(bucketObj.val(),'');
	initUpload('');
});
</script>