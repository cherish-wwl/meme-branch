<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图片选择</title>
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
		</div>
		<ul id="list">
		</ul>
	</div>
	<div data-options="region:'center',split:true,collapsible:false" title="文件列表" style="width:80%;">
		<div class="tool_bar">
			<c:if test="${type=='checkbox'}">
			<div class="tool_btn"><input type="checkbox" id="selectAllBtn" onclick="selectAll(this)"/>全选</div>
			</c:if>
			<div class="tool_btn"><a class="easyui-linkbutton" style="width:50px;margin-top:4px;" onclick="refresh()">刷新</a></div>
			<div class="tool_btn"><a class="easyui-linkbutton" style="width:50px;margin-top:4px;" onclick="delFile()">删除</a></div>
			<div class="tool_btn"><button id="uploadBtn">上传</button></div>
		</div>
		<div class="filelist">
			<section class="floor">
    		<dl class="pic" id="pic">
    			
    		</dl>
    	</section>
		</div>
	</div>
	<div id="win">
		<iframe id="frame" name="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
var listObj=$('#list');
var winObj=$('#win');
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
function del(node){
	var ids=new Array();
	ids.push(node.id);
	$.messager.confirm('提示', '再次确认是否删除选中当前目录？', function(state){
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
}

function checkedin(inputObj){
	$('#'+inputObj).prop('checked','checked');
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
						dd+='<a href="javascript:checkedin(\''+imgs[i].hash+'\')"><img src="'+imgs[i].url+'"></a>';
						dd+='<div class="ptitle"><span>'+imgs[i].filename+'</span></div>';
						dd+='<div class="pic_btn" style="text-align:center;">';
							dd+='<input type="${type}" img-url="'+imgs[i].url+'" name="filekey" id="'+imgs[i].hash+'" value="'+imgs[i].key+'"/>';
							
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
	    	if(selectedNode==null||node.id!=selectedNode.id){
	    		selectedNode=node;
		    	loadImage(bucket,selectedNode.attributes.fulldir);
		    	initUpload(selectedNode.attributes.fulldir);
	    	}
	    },
	    onBeforeExpand: function(node){
	    	listObj.tree('options').queryParams={'bucket':bucket,'pid':node.id};
	    	listObj.tree('options').url = '<%=basePath%>file/space/dir/list.do';
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
	initTree(bucketObj.val());
	loadImage(bucketObj.val(),'');
	initUpload('');
});
</script>