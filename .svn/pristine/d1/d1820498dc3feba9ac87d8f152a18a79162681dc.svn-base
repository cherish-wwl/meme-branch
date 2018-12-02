/**
 * 选中多条记录
 * @param grid
 * @returns {Array}
 */
function selectMulti(grid){
	var checkeds=grid.datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0){
		$.messager.alert('提示','未选中相应行进行操作','error');
		return ;
	}
	var ids=new Array();
	for(var i=0;i<checkeds.length;i++){
		if(checkeds[i]['id'] != undefined){
			ids.push(checkeds[i]['id']);
		}
		for(var key in checkeds[i]){
			ids.push(checkeds[i][key]);
			break;
		}
	}
	return ids;
}

/**
 * 选中一条记录
 * @param grid
 */
function selectOne(grid){
	var checkeds=grid.datagrid('getChecked');
	if(typeof checkeds == 'undefined' || checkeds.length==0 || checkeds.length>1){
		$.messager.alert('提示','请选择一行进行操作','error');
		return ;
	}
	var id = checkeds[0]['id'];
	if(id != undefined){
		return id;
	}
	for(var key in checkeds[0]){
		return checkeds[0][key];
	}
}

/**
 * 扩展弹窗属性
 * @param dialogObj 弹窗实体
 * @param options 属性数组
 */
function dialogOptions(dialogObj,options){
	if(options){
		var opts=dialogObj.dialog('options');
		for(var p in opts){
			if(typeof (opts[p]) != 'function'){
				for(var o in options){
					if(o==p){
						opts[p]=options[o];
						break;
					}
				}
			}
		}
		dialogObj.dialog(opts);
	}
}

/**
 * 新增操作
 * @param gridObj grid jquery对象
 * @param dialogObj dialog jquery对象
 * @param url 页面url
 * @param action 新增操作url
 * @param options 窗体属性，如：{'title':'','url':''}替换默认属性
 */
function add(gridObj,dialogObj,url,action,options){
	dialogObj.find('iframe').attr('src',url);
	dialogObj.dialog({
	    title: '新增',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					var formObj=dialogObj.find('iframe').contents().find('form');
					var validateObj=dialogObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var formdata = new FormData(formObj[0]);
						var keys = formdata.keys();
						var a = new Array();
						for (var key of keys) {
							console.log(key);
							a.push(key);
						}
						console.log(a);
						for(var i=0;i<a.length;i++){
							var value = formdata.get(a[i]);
							if(typeof(value) == 'object'){
								if(value.size == 0){
									formdata.delete(a[i]);
								}
							}
						}
						$.ajax({
							url:action,
							type:'post',
							enctype: "multipart/form-data",
							dataType:'json',
							data: formdata,
							contentType: false,
							processData: false,
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									dialogObj.dialog('close');
									gridObj.datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){dialogObj.dialog('close');}
			}
	    ]
	});
	dialogOptions(dialogObj,options);
}

/**
 * 
 * @param gridObj
 * @param dialogObj
 * @param url
 * @param action
 * @param callback
 * @param options
 */
function addByCallback(gridObj,dialogObj,url,action,callback,options){
	dialogObj.find('iframe').attr('src',url);
	dialogObj.dialog({
	    title: '新增',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					var formObj=dialogObj.find('iframe').contents().find('form');
					var validateObj=dialogObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						$.ajax({
							url:action,
							type:'post',
							dataType:'json',
							data: formObj.serialize(),
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									dialogObj.dialog('close');
									callback();
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){dialogObj.dialog('close');}
			}
	    ]
	});
	dialogOptions(dialogObj,options);
}

/**
 * 修改操作
 * @param gridObj grid jquery对象
 * @param dialogObj dialog jquery对象
 * @param url 页面url
 * @param action 修改操作url
 * @param options 窗体属性，如：{'title':'','url':''}替换默认属性
 */
function modify(gridObj,dialogObj,url,action,options){
	var id=selectOne(gridObj);
	if(typeof id =='undefined') return ;
	dialogObj.find('iframe').attr('src',url+'?id='+id);
	dialogObj.dialog({
	    title: '修改',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'修改',
				iconCls:'icon-save',
				handler:function(){
					var formObj=dialogObj.find('iframe').contents().find('form');
					var validateObj=dialogObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						var formdata = new FormData(formObj[0]);
						var keys = formdata.keys();
						var a = new Array();
						for (var key of keys) {
							console.log(key);
							a.push(key);
						}
						console.log(a);
						for(var i=0;i<a.length;i++){
							var value = formdata.get(a[i]);
							if(typeof(value) == 'object'){
								if(value.size == 0){
									formdata.delete(a[i]);
								}
							}
						}
						$.ajax({
							url:action,
							type:'post',
							enctype: "multipart/form-data",
							dataType:'json',
							data: formdata,
							contentType: false,
							processData: false,
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									dialogObj.dialog('close');
									gridObj.datagrid('reload');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){dialogObj.dialog('close');}
			}
	    ]
	});
	dialogOptions(dialogObj,options);
}

function modifyByCallback(gridObj,dialogObj,url,action,callback,options){
	var id=selectOne(gridObj);
	if(typeof id =='undefined') return ;
	dialogObj.find('iframe').attr('src',url+'?id='+id);
	dialogObj.dialog({
	    title: '修改',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'修改',
				iconCls:'icon-save',
				handler:function(){
					var formObj=dialogObj.find('iframe').contents().find('form');
					var validateObj=dialogObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						$.ajax({
							url:action,
							type:'post',
							dataType:'json',
							data: formObj.serialize(),
							success:function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									dialogObj.dialog('close');
									callback();
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error:function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){dialogObj.dialog('close');}
			}
	    ]
	});
	dialogOptions(dialogObj,options);
}

/**
 * 删除操作，需保证主键在第一列
 * @param grid
 * @param url 删除操作请求url
 */
function del(grid,url){
	var ids=selectMulti(grid);
	if(typeof ids =='undefined') return ;
	$.messager.confirm('提示', '再次确认是否删除选中记录？', function(state){
		if (state){
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:{'ids':ids},
				success:function(data){
					if(data.state==1){
						$.messager.alert('提示',data.message,'info');
						grid.datagrid('reload');
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

/**
 * 查询操作
 * @param searchForm 查询表单ID属性
 * @param datagrid datagrid ID属性
 */
function doSearch(searchForm,datagrid){
	var paramString=searchForm.serialize();
	if(null==paramString||paramString==''){
		datagrid.datagrid('reload');
	}else{
		var data=new Array();
		var params=paramString.split('&');
		for(var i=0;i<params.length;i++){
			var parm=params[i].split('=');
			data[i]=parm[0]+':"'+decodeURI(parm[1])+'"';
		}
		var parms='{'+data.toString()+'}';
		datagrid.datagrid('loadData',{total: 0,rows:[]});
		datagrid.datagrid('load',eval('('+parms+')'));
	}
}

/**
 * 加载操作
 * @param operationListObj
 * @param rclickOperationObj
 * @param url
 * @param params
 */
function loadOperation(operationListObj,rclickOperationObj,url,params){
	operationListObj.tree({
		lines:true,
	    url: url,
	    queryParams:params,
	    loadFilter: function (data) {
	    	return data;
	    },
	    onContextMenu:function(e,node){
	    	operationListObj.tree('select', node.target);
	    	e.preventDefault();
	    	rclickOperationObj.menu('show', {
				left: e.pageX,
				top: e.pageY
			});
	    }
	});
}

/**
 * 编辑操作
 * @param treeObj 树对象
 * @param winObj 弹窗窗体对象
 * @param url 页面url
 * @param action 操作url
 * @param options 窗体属性数组
 * @param params ajax请求参数数组
 */
function edit(treeObj,winObj,url,action,options){
	winObj.find('iframe').attr('src',url);
	winObj.dialog({
	    title: '新增',
	    width: 600,
	    height: 350,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false,
	    buttons:[
			{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					var formObj=winObj.find('iframe').contents().find('form');
					var validateObj=winObj.find('iframe')[0].contentWindow.validate(formObj);
					if(validateObj.form()) {
						$.ajax({
							url: action,
							type: 'post',
							dataType: 'json',
							data: formObj.serialize(),
							success: function(data){
								if(data.state==1){
									$.messager.alert('提示',data.message,'info');
									if(treeObj) treeObj.tree('reload');
									var callback=options.callback;
									if(typeof callback=='function') {
										callback();
									}
									winObj.dialog('close');
								}else if(data.state==0){
									$.messager.alert('提示',data.message,'error');
								}
							},
							error: function(){
								$.messager.alert('提示','操作失败','error');
							}
						});
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){winObj.dialog('close');}
			}
	    ]
	});
	dialogOptions(winObj,options);
}

/**
 * 添加子frame
 * @param src
 * @returns {String}
 */
function createFrame(src){
	return '<iframe scrolling="auto" frameborder="0"  src="'+src+'" style="width:100%;height:99.5%;"></iframe>';
}

/**
 * 添加tab面板
 * @param tabsObj
 * @param options {'title':'','url':''}
 */
function addTab(tabsObj,options){
	if (tabsObj.tabs('exists', options.title)){
		tabsObj.tabs('select', options.title);
	} else {
		var content = createFrame(options.url);
		tabsObj.tabs('add',{
			title:options.title,
			content:content,
			closable:true,
			border:true
		});
	}
}
/**
 * 重置查询条件
 * @param searchForm
 */
function doReset(searchForm){
	$(":text", searchForm).each(function() {
		$(this).val('');
	});
	$(":radio", searchForm).each(function() {
		$(this).val('');
	});
	$(":checkbox", searchForm).each(function() {
		$(this).val('');
	});
	$(":hidden", searchForm).each(function() {
		$(this).val('');
	});
	$("select", searchForm).each(function() {
		$(this).val('');
	});
}
/**
 * 加载左侧菜单
 * @param tabsObj easyui-tabs容器Object
 * @param accordionObj 左侧菜单容器Object
 * @param platformid 平台ID
 * @param initTopApi 最顶层菜单请求API
 * @param initSubApi 菜单子树请求API
 */
function loadMenu(tabsObj,accordionObj,platformid,initTopApi,initSubApi){
	$.ajax({
		url:initTopApi,
		type:'post',
		dataType:'json',
		data:{'platformid':platformid},
		success:function(data){
			setTimeout(function(){
				$.each(data,function(i,ele){
					var accordinnode='<div pid="'+ele.id+'" title="'+ele.name+'"><ul id="tree'+ele.id+'"></ul></div>';
					if(i==0){
						accordionObj.accordion('add', {
	                        title : ele.name,
	                        iconCls : ele.icon,
	                        selected : true,
	                        content : accordinnode
	                    });
					}else{
						accordionObj.accordion('add', {
	                        title : ele.name,
	                        iconCls : ele.icon,
	                        selected : false,
	                        content : accordinnode
	                    });
					}
				});
			},300);
			accordionObj.accordion({
				onSelect:function(title,index){
					var panel=accordionObj.accordion("getSelected").html();
					var pid=$(panel).attr('pid');
					$('ul[id="tree' + pid + '"]').tree({
						lines:true,
					    url: initSubApi,
					    queryParams: {"platformid":platformid,"pid":pid},
					    loadFilter: function (data) {
					    	return data;
					    },
					    onClick:function(node){
					    	var url=node.attributes.url;
							var title=node.text;
							var options={'title':title,'url':url};
							if(!(url==''||url=='#')){
								addTab(tabsObj,options);
							}
					    }
					});
				}
			});
		},
		error:function(){
			$.messager.alert('提示','获取菜单失败','error');
		}
	});
}
function recursion(treenode,ids){
	ids.push(treenode.id);
	if(treenode.children.length>0){
		for(var i=0;i<treenode.children.length;i++){
			recursion(treenode.children[i],ids);
		}
	}
	return ids;
}
function view(gridObj,dialogObj,url,options){
	var id=selectOne(gridObj);
	if(typeof id =='undefined') return ;
	
	dialogObj.find('iframe').attr('src',url+'?id='+id);
	dialogObj.dialog({
	    title: '查看明细',
	    width: 600,
	    height: 400,
	    closed: false,
	    modal: true,
	    maximizable:true,
	    resizable:true,
	    collapsible:true,
	    minimizable:false
	});
	dialogOptions(dialogObj,options);
}

/**
 * 循环判断easyui tree是否加载完成，加载完成后执行回调函数callback
 * @param callback
 */
function treeCheck(callback){
	var timer=window.setInterval(function(){
		var isLoadSuccess=window.frames['frame'].isLoadSuccess;
		if(isLoadSuccess){
			callback();
			window.clearInterval(timer);
		}
	},500);
}

/**
 * uploadify封装
 * @param pickerObj 文件选择按钮jquery对象
 * @param settings 参数配置
 */
function upload(pickerObj,settings){
	pickerObj.uploadify({
		'swf': '/static/js/uploadify/uploadify.swf',
		'onUploadSuccess' : function(file, data, response) {
			data = eval('('+data+')');
			if(data.state==1){
				$.messager.alert('提示',data.message,'info');
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
			pickerObj.uploadify('disable', false);
	    },
	    'onUploadError':function(file, errorCode, errorMsg, errorString){
	    	$.messager.alert('提示','文件上传失败，错误码：'+errorCode+'，原因：'+errorString,'error');
	    	pickerObj.uploadify('disable', false);
	    },
	    'onUploadStart':function(file){
	    	pickerObj.uploadify('disable', true);
	    }
	});
	//pickerObj.uploadify('settings','multi',false);
	if(settings){
		for(var option in settings){
			alert(option);
			alert(settings[option]);
			pickerObj.uploadify('settings',option,settings[option]);
		}
	}
	//修正点击位置偏移
	pickerObj.find('object').css({'left':0});
}
/**
 * ajax提交表单
 * @param url 请求URL
 * @param isWinClose 处理成功后是否关闭窗口
 */
function ajaxForm(url,isWinClose){
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$('form').serialize(),
		success: function(data){
			if(data.state==1){
				if(typeof isWinClose =='undefined'||!isWinClose) {
					$.messager.alert('提示',data.message,'info');
				}else{
					$.messager.alert('提示',data.message,'info',function(){
						var winObj=$(window.parent.document).find('iframe').parent();
						parent.$(winObj).dialog('close');
						/*var gridObj=$(window.parent.document).find('table');
						if(typeof gridObj!='undefined'||gridObj!=null){
							parent.$(gridObj).datagrid('reload');
						}*/
					});
				}
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
		},
		error: function(){
			$.messager.alert('提示','操作失败','error');
		}
	});
}

/**
 * 毫秒转成天时分秒毫秒表示
 * @param milSec
 * @returns {String}
 */
function millisec2Date(milSec){
	var ss=1000;
	var mm=ss*60;
	var hh=mm*60;
	var dd=hh*24;
	
	var day=parseInt(parseFloat(milSec/dd).toFixed(7));
	var hour=parseInt(parseFloat((milSec-day*dd)/hh).toFixed(7));
	var minute =parseInt(parseFloat((milSec - day * dd - hour * hh) / mm).toFixed(7));
	var second = parseInt(parseFloat((milSec - day * dd - hour * hh - minute * mm) / ss).toFixed(7));
	var milliSecond=parseInt(milSec - day * dd - hour * hh - minute * mm - second * ss);

	var strDay = day < 10 ? "0" + day : "" + day; //天
    var strHour = hour < 10 ? "0" + hour : "" + hour;//小时
    var strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
    var strSecond = second < 10 ? "0" + second : "" + second;//秒
    var strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
    strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
    
	return strDay+" 天 "+strHour+" 小时 "+strMinute+" 分钟 " +strSecond+" 秒 "+strMilliSecond+" 毫秒";
}

/**
 * 生成select控件下拉树
 * @param data 树结构，[{data:{},children:[{data:{},children:[]}]}]
 * @param optname 选项名称字段
 * @param optvalue 选项值字段
 * @returns {String}
 */
var unit="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
var nbsp="";
function createTreeSelect(data,optname,optvalue,defaultval){
	var options="";
	if(defaultval){
		options="<option value=''>"+defaultval+"</option>";
	}
	for(var i=0;i<data.length;i++){
		var nbspstr="";
		if(data[i].children.length){
			options+="<option value='"+data[i].data[optvalue]+"'>"+nbsp+data[i].data[optname]+"</option>";
			nbsp+=unit;
			options+=createTreeSelect(data[i].children,optname,optvalue);
			nbsp=nbsp.substring(0,nbsp.length-unit.length);
		}else{
			options+="<option value='"+data[i].data[optvalue]+"'>"+nbsp+data[i].data[optname]+"</option>";
		}
	}
	return options;
}

/**
 * 生成单级下拉
 * @param data 下拉数据源
 * @param optname 下拉项文字字段名
 * @param optvalue 下拉项值字段名
 * @param defaultval 默认第一个下拉项文字
 * @returns {String}
 */
function createSelect(data,optname,optvalue,defaultval){
	var options="";
	if(defaultval){
		options="<option value=''>"+defaultval+"</option>";
	}
	for(var i=0;i<data.length;i++){
		options+="<option value='"+data[i][optvalue]+"'>"+data[i][optname]+"</option>";
	}
	return options;
}

function closeWithoutSave(){
	window.opener=null;
	window.open('','_self');
	window.close();
}

/**
 * 多个二维数组中取一个元素的排列组合
 * @param arr
 */
function combine_array(arrs){
	var maxarrlen=0;
	var num=1;
	for(var i=0;i<arrs.length;i++){
		num*=arrs[i].length;
		if(maxarrlen<arrs[i].length) maxarrlen=arrs[i].length;
	}
	var result=new Array();
	for(var a=0;a<num;a++){
		result[a]=new Array();
		for(var b=0;b<maxarrlen;b++){
			result[a][b]='';
		}
	}
	var arr_num = num;
	for(var i=0;i<arrs.length;i++){
		var v_num = arrs[i].length;
		arr_num = arr_num / v_num;
		var position = 0;
		for(var m=0;m<arrs[i].length;m++){
			var v_position = position;
			var count = num / v_num / arr_num;
			for(j = 1; j <= count; j ++){
                for(var n = 0; n < arr_num; n ++)
                {
                    result[v_position + n][i] = arrs[i][m];
                }
                v_position += arr_num * v_num;
            }
            position += arr_num;
		}
	}
	return result;
}

function insertAlias(obj){
	if(obj.checked){
		$(obj).after('<input type="text" name="alias_'+$(obj).attr('property')+'&&&'+$(obj).val()+'" style="width:60px"/>');
	}else{
		$(obj).next('input[name="alias_'+$(obj).attr('property')+'&&&'+$(obj).val()+'"]').remove();
	}
	initSku();
}

function changeVal(obj,nameprefix){
	$("input[name^='"+nameprefix+"']").each(function(){
		$(this).val($(obj).val());
	});
}

/**
 * 
 */
function initSku(){
	var table='';
	if(saleprops.length>0){
		table+='<table class="table table-bordered">';
		table+='<thead><tr>';
		for(var i=0;i<saleprops.length;i++){
			table+='<td>'+saleprops[i].property.attrname+'</td>';
		}
		table+='<td>价格<input type="text" onkeyup="changeVal(this,\''+'sku_price'+'\')" style="width:60px"/></td>';
		table+='<td>数量<input type="text" onkeyup="changeVal(this,\''+'sku_quantity'+'\')" style="width:60px"/></td>';
		table+='<td>编码<input type="text" onkeyup="changeVal(this,\''+'sku_code'+'\')" style="width:60px"/></td>';
		table+='</tr></thead>';
		table+='<tbody>';
		var valueArray=new Array();
		var all=1;
		for(var i=0;i<saleprops.length;i++){
			var vals=new Array();
			var obj=document.getElementsByName('property_'+saleprops[i].property.id);
			for(var j=0;j<obj.length;j++){
				if(obj[j].checked) vals.push(obj[j]);
			}
			if(vals.length>0) {
				all*=vals.length;
				valueArray.push(vals);
			}
		}
		if(valueArray.length==saleprops.length){
			var combineArr=combine_array(valueArray);
			for(var i=0;i<combineArr.length;i++){
				var arr=combineArr[i];
				if(arr.length>0&&arr[0]!=''){
					table+='<tr>';
					var id='';
					for(var j=0;j<arr.length;j++){
						if(arr[j]) {
							table+='<td>'+$(arr[j]).attr('propval')+'</td>';
							if(j==0) id+=$(arr[j]).attr('property')+'&&&'+$(arr[j]).val();
							else id+='&&&'+$(arr[j]).attr('property')+'&&&'+$(arr[j]).val();
						}
					}
					table+='<td><input type="text" positive="true" data-rule-float="[8,2]" required name="sku_price_'+id+'" class="form-control"/></td>';
					table+='<td><input type="text" positiveInteger="true" required name="sku_quantity_'+id+'" class="form-control"/></td>';
					table+='<td><input type="text" name="sku_code_'+id+'" class="form-control"/></td>';
					table+='<tr>';
				}
			}
		}
		table+='</tbody>';
		table+='</table>';
	}
	var objs=$('#sku').find('input');
	var obj_vals=new Map();
	//记录各SKU填写的数据
	objs.each(function(){
		var name=$(this).attr('name');
		if(typeof name!='undefined'){
			var value=$(this).val();
			if(value!=null&&value!='') obj_vals.put($(this).attr('name'),$(this).val());
			//alert(name);
		}
		//obj_vals.put($(this).attr('name'),$(this).val());
	});
	
	$('#sku').empty();
	$('#sku').append(table);
	
	//清空dom之后回填SKU数据
	if(obj_vals.size()>0){
		var keys=obj_vals.keySet();
		for(var key in keys){
			var value=obj_vals.get(keys[key]);
			$('input[name="'+keys[key]+'"]').val(value);
		}
	}
}
/**
 * 
 * @param catid
 * @param callback
 */
var saleprops=new Array();
function initProperties(catid,containerObj,callback){
	saleprops=[];
	$.ajax({
		url: GLOBAL_BASE_PATH+'wxshop/goods/getPropertyVals.do',
		type:'post',
		dataType:'json',
		data:{'catid':catid},
		success:function(data){
			if(data&&data.length>0){
				var div='';
				for(var i=0;i<data.length;i++){
					var property=data[i].property;
					var values=data[i].values;
					var units=data[i].units;
					div+='<div class="form-group">';
						div+='<label class="col-xs-3 control-label" style="padding-top:10px;">'+property.attrname+':</label>';
						div+='<div class="col-xs-9" style="padding-top:10px;">';
						if(property.enumprop==0){
							div+='<input '+(property.saleprop==1?'onclick="insertAlias(this)"':'')+' property="'+property.id+'" style="'+(property.ismeasures==1?'width:80%;':'')+'float:left;" '+(property.required==1?'required':'')+' id="property_'+property.id+'" name="property_'+property.id+'" type="text" class="form-control">';
							if(property.ismeasures==1){
								div+='<select '+(property.saleprop==1?'onclick="insertAlias(this)"':'')+' property="'+property.id+'" style="width:20%;float:left;" id="unit_'+property.id+'" name="unit_'+property.id+'" class="form-control">';
								div+='<option value="">请选择度量单位</option>';
								if(units&&units.length>0){
									for(var j=0;j<units.length;j++){
										div+='<option value="'+units[j].id+'">'+units[j].cnunit+'('+units[j].unit+')</option>';
									}
								}
								div+='</select>';
							}
						}else{
							if(values&&values.length>0){
								if(property.multi==0){
									div+='<select '+(property.saleprop==1?'onclick="insertAlias(this)"':'')+' property="'+property.id+'" '+(property.required==1?'required':'')+' id="property_'+property.id+'" name="property_'+property.id+'" class="form-control">';
									for(var j=0;j<values.length;j++){
										if(values[j].isdefault){
											div+='<option value="'+values[j].id+'" selected="selected">'+values[j].propvalue+'</option>';
										}else{
											div+='<option value="'+values[j].id+'">'+values[j].propvalue+'</option>';
										}
									}
									div+='</select>';
								}else{
									for(var j=0;j<values.length;j++){
										if(values[j].isdefault){
											div+='<div class="checkbox" style="float:left;margin-top:-5px;margin-right:10px;"><label><input checked="checked" '+(property.saleprop==1?'onclick="insertAlias(this)"':'')+' '+(property.required==1?'required':'')+' property="'+property.id+'" type="checkbox" name="property_'+property.id+'" propval="'+values[j].propvalue+'" value="'+values[j].id+'"/>'+values[j].propvalue+'</label></div>';
										}else{
											div+='<div class="checkbox" style="float:left;margin-top:-5px;margin-right:10px;"><label><input '+(property.saleprop==1?'onclick="insertAlias(this)"':'')+' '+(property.required==1?'required':'')+' property="'+property.id+'" type="checkbox" name="property_'+property.id+'" propval="'+values[j].propvalue+'" value="'+values[j].id+'"/>'+values[j].propvalue+'</label></div>';
										}
									}
								}
							}
						}
						div+='</div>';
					div+='</div>';
					
					if(property.saleprop==1){
						saleprops.push(data[i]);
						if(i+1==data.length){
							div+='<div id="sku"></div>';
						}else{
							if(data[i+1].property.saleprop==0){
								div+='<div id="sku"></div>';
							}
						}
					}
				}
				containerObj.empty();
				containerObj.append(div);
				if(typeof callback !='undefined'&&typeof callback=='function') callback();
			}
		},
		error:function(){
			$.messager.alert('提示','加载类目属性数据失败，请稍后刷新重试或者联系管理员','error');
		}
	});
}

/**
 * 加载商品属性值数据
 * @param goodsid
 */
function initGoodsValues(goodsid){
	$.ajax({
		url: GLOBAL_BASE_PATH+'wxshop/goods/getGoodsValues.do',
		type: 'post',
		dataType: 'json',
		data:{'goodsid':goodsid},
		success: function(data){
			if(data&&data.length>0){
				for(var i=0;i<data.length;i++){
					var propid=data[i].propid;
					var valueid=data[i].valueid;
					var pvalue=data[i].pvalue;
					var alias=data[i].alias;
					var value='';
					if(valueid!=0) value=valueid;
					else if(pvalue!=null||pvalue!='') value=pvalue;
					var unitid=data[i].unitid;
					var obj=$('#property_'+propid)[0];
					if(obj==undefined||obj==null){
						$('input[name="property_'+propid+'"][value="'+value+'"]').attr('checked','true');
						insertAlias($('input[name="property_'+propid+'"][value="'+value+'"]')[0]);
						if(alias!=null&&alias!='') {
							$('input[name="alias_'+propid+'&&&'+value+'"]').val(alias);
						}
					}else{
						$('#property_'+propid).val(value);
						if(unitid!=null&&unitid!='') {
							$('#unit_'+propid).val(unitid);
						}
					}
				}
				initGoodsSku(goodsid);
			}
		},
		error: function(){
			$.messager.alert('提示','加载属性值失败，请稍后刷新重试或者联系管理员','error');
		}
	});
}

/**
 * 加载商品SKU
 * @param goodsid
 */
function initGoodsSku(goodsid){
	$.ajax({
		url: GLOBAL_BASE_PATH+'wxshop/goods/getGoodsSku.do',
		type: 'post',
		dataType: 'json',
		data:{'goodsid':goodsid},
		success: function(data){
			if(data&&data.length>0){
				for(var i=0;i<data.length;i++){
					var properties=data[i].properties;
					var prop_vals=properties.split(';');
					var skuid='';
					for(var j=0;j<prop_vals.length-1;j++){
						var prop_val=prop_vals[j].split(':');
						if(j==0) skuid+=prop_val[0]+'&&&'+prop_val[1];
						else skuid+='&&&'+prop_val[0]+'&&&'+prop_val[1];
					}
					$('input[name="sku_price_'+skuid+'"]').val(data[i].price);
					$('input[name="sku_quantity_'+skuid+'"]').val(data[i].quantity);
					$('input[name="sku_code_'+skuid+'"]').val(data[i].code);
				}
			}
		},
		error: function(){
			$.messager.alert('提示','加载商品SKU失败，请稍后刷新重试或者联系管理员','error');
		}
	});
}