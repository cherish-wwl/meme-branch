<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include/include_easyui.jsp"%>
</head>

<body>
<table id="grid" toolbar="#toolbar">
	<thead>
		<tr>
	<!-- 		<th field="productid" sortable="true" align="center" width="25%">商品编号</th>
			<th field="subject" sortable="true" align="center" width="25%">商品名称</th>
			<th field="body" sortable="true" align="center" width="25%">商品描述</th>
			<th field="amount" sortable="true" align="center" width="25%">商品价格</th> -->
		</tr>
	</thead>
</table>
<!-- <div id="toolbar">
	<div class="express-search">
		<form id="searchForm" name="searchForm">
			查询条件：<input class="easyui-textbox" type="text" id="searchKey" name="searchKey">
			&nbsp;&nbsp;
			添加时间：<input class="easyui-datetimebox" type="text" id="startdate" name="startdate">至
			<input class="easyui-datetimebox" type="text" id="enddate" name="enddate">
			&nbsp;&nbsp;
			<a href="#" onclick="doSearch($('#searchForm'),$('#grid'))" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			&nbsp;&nbsp;
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
			
			
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
			<a href="#" onclick="doReset($('#searchForm'))" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		</form>
	</div>
	<div style="">
		<a onclick="fix()" class="easyui-linkbutton">修正订单</a>
	</div>
</div> -->
<div id="win">
<iframe id="frame" scrolling="yes" frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
</div>
</body>
</html>
<script type="text/javascript">
<%-- function fix(){
	var gridObj=$('#grid');
	var checkeds=gridObj.datagrid('getChecked');
	var orderid=selectOne(gridObj);
	if(null==orderid || typeof orderid == 'undefined') return ;
	orderid=checkeds[0].orderid;
	var action='<%=basePath%>pay/alipay/fix.do';
	$.ajax({
		url: action,
		type: 'post',
		dataType: 'json',
		data: {'orderid':orderid},
		success: function(data){
			if(data.state==1){
				$.messager.alert('提示',data.message,'info');
				gridObj.datagrid('reload');
			}else if(data.state==0){
				$.messager.alert('提示',data.message,'error');
			}
		},
		error: function(e){
			$.messager.alert('提示','操作异常，请稍后重试或联系管理员','error');
		}
	});
} --%>
function doSearch(formObj,gridObj){
	var paramString=formObj.serialize();
	if(null==paramString||paramString==''){
		gridObj.datagrid('reload');
	}else{
		var data=new Array();
		var params=paramString.split('&');
		for(var i=0;i<params.length;i++){
			var parm=params[i].split('=');
			data[i]=parm[0]+':"'+decodeURI(parm[1])+'"';
		}
		var parms='{'+data.toString()+'}';
		var d=eval('('+parms+')');
		d.startdate=$('input[name="startdate"]').val();
		d.enddate=$('input[name="enddate"]').val();
		gridObj.datagrid('loadData',{total: 0,rows:[]});
		gridObj.datagrid('load',d);
	}
}
var datagrid;  
var rowEditor=undefined;
$(function(){
	datagrid=$('#grid').datagrid({
		method: 'post',
        url: '<%=basePath%>im/shop/list.do',
        width:'auto',
        height:'auto',
        rownumbers: true,
        fit: true,
        autoRowHeight: false,
        pagination: true,
        pageSize:20,
        singleSelect: false,
        striped: true,
        fitColumns: true,
        columns: [[//显示的列
            {field: 'productid', title: '商品编号', width: 100, sortable: true, checkbox: true },
             { field: 'subject', title: '商品名称', width: 100, sortable: true,
                 editor: { type: 'validatebox', options: { required: true} }
             },
              { field: 'body', title: '商品描述', width: 100,
                  editor: { type: 'validatebox', options: { required: true} }
              },
               { field: 'amount', title: '商品金额', width: 100,
                   editor: { type: 'numberbox', options: { required: true,precision:'2'} },
                   formatter: function (value, row, index) {
                       if (row != null) {
                           return parseFloat(value).toFixed(2);
                       }
                   }
               }
               ]],
            queryParams: { action: 'query' }, 
        toolbar:[              //工具条
        	{ 
                text: '商品名称：<input type="text" id="productname"/>' 
            }, { 
                text: '商品描述：<input type="text" id="productintro"/>' 
            },{ 
                text: '商品价格：<select name="jinetiaojian" id="jinetiaojian"><option value="0">大于</option><option value="1">小于</option><option value="2">等于</option></select>'
            +' <input type="text" class="easyui-numberbox" id="productprice" precision="2"/>' 
            },  
            {text:"查询",iconCls:"icon-search",handler:function(){
            	var productname = $("#productname").val();
            	var productintro = $("#productintro").val();
            	var productprice = $("#productprice").val();
            	var jinetiaojian = $("#jinetiaojian").val();
            	data="productname"+':"'+productname+'"'+",productintro"+':"'+productintro+'"'+",productprice"+':"'+productprice+'"'+",state"+':"'+jinetiaojian+'"';
            	var parms='{'+data.toString()+'}';
        		var d=eval('('+parms+')');
        		datagrid.datagrid('load',d);
            	
            }},  
            {text:"增加",iconCls:"icon-add",handler:function(){//回调函数  
                if(rowEditor==undefined)  
                {  
                    datagrid.datagrid('insertRow',{//如果处于未被点击状态，在第一行开启编辑  
                        index: 0,     
                        row: {  
                        }  
                    });  
                    rowEditor=0;  
                    datagrid.datagrid('beginEdit',rowEditor);//没有这行，即使开启了也不编辑  
                      
                }  
              

            }},  
            {text:"删除",iconCls:"icon-remove",handler:function(){  
                var rows=$('#grid').datagrid('getSelections');  
        
                if(rows.length<=0)  
                {  
                    $.messager.alert('警告','您没有选择','error');  
                }  
                else if(rows.length>1)  
                {  
                    $.messager.alert('警告','不支持批量删除','error');  
                }  
                else  
                {  
                    $.messager.confirm('确定','您确定要删除吗',function(t){  
                        if(t)  
                        {  
                              
                            $.ajax({  
                                url : '<%=basePath%>im/shop/deleteshopproduct.do',  
                                data : rows[0],  
                                dataType : 'json',  
                                success : function(r) {  
                                    if (r.success) {  
                                    	datagrid.datagrid('acceptChanges');  
                                        $.messager.show({  
                                            msg : '删除商品数据成功！',  
                                            title : '成功'  
                                        });  
                                        editRow = undefined;  
                                        datagrid.datagrid('reload');  
                                    } else {  
                                        /*datagrid.datagrid('rejectChanges');*/  
                                        datagrid.datagrid('beginEdit', editRow);  
                                        $.messager.alert('错误', r.msg, 'error');  
                                    }  
                                    datagrid.datagrid('unselectAll');  
                                }  
                            });  
                          
                        }  
                    })  
                }  
                  
                  
            }},  
            {text:"修改",iconCls:"icon-edit",handler:function(){  
                var rows=datagrid.datagrid('getSelections');  
                if(rows.length==1)  
                {  
                    if(rowEditor==undefined)  
                    {  
                        var index=datagrid.datagrid('getRowIndex',rows[0]);  
                         rowEditor=index;  
                         datagrid.datagrid('unselectAll');  
                         datagrid.datagrid('beginEdit',index);  
                          
                    }  
                }  
            }},  
            {text:"保存",iconCls:"icon-save",handler:function(){  
                  
            	datagrid.datagrid('endEdit',rowEditor);  
                rowEditor=undefined;  
            }},  
            {text:"取消编辑",iconCls:"icon-redo",handler:function(){  
                rowEditor=undefined;  
                datagrid.datagrid('rejectChanges')  
            }}  
            ],  
            onAfterEdit:function(rowIndex, rowData, changes){  
                var inserted = datagrid.datagrid('getChanges', 'inserted');  
                var updated = datagrid.datagrid('getChanges', 'updated');  
                if (inserted.length < 1 && updated.length < 1) {  
                    editRow = undefined;  
                    datagrid.datagrid('unselectAll');  
                    return;  
                }  
      
                var url = '';  
                if (inserted.length > 0) {  
                    url = '<%=basePath%>im/shop/addshopproduct.do';  
                }  
                if (updated.length > 0) {  
                    url = '<%=basePath%>im/shop/upadateshopproduct.do';  
                }  
      
                $.ajax({  
                    url : url,  
                    data : rowData,  
                    dataType : 'json',  
                    success : function(r) {  
                        if (r.success) {  
                            datagrid.datagrid('acceptChanges');  
                            $.messager.show({  
                                msg : r.msg,  
                                title : '成功'  
                            });  
                            editRow = undefined;  
                            datagrid.datagrid('reload');  
                        } else {  
                            /*datagrid.datagrid('rejectChanges');*/  
                            datagrid.datagrid('beginEdit', editRow);  
                            $.messager.alert('错误', r.msg, 'error');  
                        }  
                        datagrid.datagrid('unselectAll');  
                    }  
                });  
                  
            },  
            onDblClickCell:function(rowIndex, field, value){  
                if(rowEditor==undefined)  
                {  
                    datagrid.datagrid('beginEdit',rowIndex);  
                    rowEditor=rowIndex;  
                }  
                  
            }  ,
        border:true
	});
});
</script>