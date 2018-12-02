/**
 * 操作提示框
 * @param message 返回消息json对象
 * @param callback 回调函数
 */
function weui_dialog(message,callback){
	var success = '\
		<div class="express_dialog" style="display: none;">\
	    	<div class="weui-mask"></div>\
	    	<div class="weui-dialog">\
	    		<div class="weui-dialog__hd">\
	   				<div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>\
	   			</div>\
	    		<div class="weui-dialog__bd">'+message.message+'</div>\
	    		<div class="weui-dialog__ft">\
	    			<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>\
	    		</div>\
	    	</div>\
    	</div>\
    	';
	var failed='\
		<div class="express_dialog" style="display: none;">\
    	<div class="weui-mask"></div>\
    	<div class="weui-dialog">\
    		<div class="weui-dialog__hd">\
   				<div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>\
   			</div>\
    		<div class="weui-dialog__bd">'+message.message+'</div>\
    		<div class="weui-dialog__ft">\
    			<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>\
    		</div>\
    	</div>\
	</div>\
	';
	var dialog=null;
	if(message.state==1) dialog=success;
	else if(message.state==0)  dialog=failed;
	$('body').append(dialog);
    $('.express_dialog').fadeIn(200);
    $('.weui-dialog__btn').on('click',function(){
    	$('.express_dialog').fadeOut(200,function(){clearToast();});
    	if(callback!=null&&typeof callback=='function') callback();
    });
}
/**
 * 错误提示
 * @param message 消息字符串
 */
function weui_alert(message){
	var failed='\
		<div class="express_dialog" style="display: none;">\
    	<div class="weui-mask"></div>\
    	<div class="weui-dialog">\
    		<div class="weui-dialog__hd">\
   				<div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>\
   			</div>\
    		<div class="weui-dialog__bd">'+message+'</div>\
    		<div class="weui-dialog__ft">\
    			<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>\
    		</div>\
    	</div>\
	</div>\
	';
	$('body').append(failed);
	$('.express_dialog').fadeIn(200);
    $('.weui-dialog__btn').on('click',function(){
    	$('.express_dialog').fadeOut(200,function(){clearToast();});
    });
}

function clearToast(){
	$('.express_dialog').remove();
	$('.toast').remove();
}

function toast(message){
	if(!message) message='已完成';
	$('.toast').remove();
	var toast='\
		<div class="toast" style="display: none;">\
			<div class="weui-mask_transparent"></div>\
			<div class="weui-toast">\
				<i class="weui-icon-success-no-circle weui-icon_toast"></i>\
				<p class="weui-toast__content">'+message+'</p>\
			</div>\
		</div>\
		';
	$('body').append(toast);
	$('.toast').fadeIn(100);
    setTimeout(function () {
    	$('.toast').fadeOut(100);
    	clearToast();
    }, 2000);
}
function loading_toast(message){
	if(!message) message='加载中';
	var toast='\
		<div class="toast" style="display: none;">\
			<div class="weui-mask_transparent"></div>\
			<div class="weui-toast">\
				<i class="weui-loading weui-icon_toast"></i>\
				<p class="weui-toast__content">'+message+'</p>\
			</div>\
		</div>\
		';
	$('body').append(toast);
	$('.toast').fadeIn(100);
}

/**
 * 保存操作
 * @param action 操作url
 * @param callback
 */
function wxshop_save(action,callback){
	var validateObj=validate($('form'));
	if(validateObj.form()){
		$.ajax({
			url: action,
			type:'post',
			dataType:'json',
			data: $('form').serialize(),
			beforeSend: function(){
				loading_toast();
			},
			success:function(data){
				weui_dialog(data,callback);
			},
			error:function(){
				weui_alert('操作异常');
			}
		});
	}
}
/**
 * ajax基本操作
 * @param action
 * @param data
 * @param callback
 */
function ajaxAction(action,data,callback){
	$.ajax({
		url: action,
		type:'post',
		dataType:'json',
		data: data,
		beforeSend: function(){
			loading_toast();
		},
		success:function(d){
			weui_dialog(d,callback);
		},
		error:function(){
			weui_alert('操作异常');
		}
	});
}
/**
 * weui picker控件操作
 * @param action
 * @param handle 数据处理函数，ajax返回json数据处理成picker格式,参数：ajax返回的data对象
 * @param callback 选中项后的回调函数，参数：1、picker选中项；2、ajax返回的data对象
 */
function pickerAction(action,handle,callback){
	$.ajax({
		url: action,
		type:'get',
		dataType:'json',
		beforeSend: function(){
			loading_toast();
		},
		success:function(data){
			clearToast();
			if(data&&data.length>0){
				var handleResult=null;
				if(null!=handle&&typeof handle=='function') handleResult=handle(data);
				else{
					weui_alert('数据处理函数未提供');
					return ;
				}
				weui.picker(handleResult, {
	                onChange: function (result) {
	                },  
	                onConfirm: function(result){
	                	for(var i=0;i<handleResult.length;i++){
	                		if(handleResult[i].value==result){
	                			if(null!=callback&&typeof callback=='function') {
	                				callback(handleResult[i],data);
	                				return ;
	                			}
	                		}
	                	}
	                }  
	            });  
			}
		},
		error:function(){
			weui_alert('获取收货地址失败，请稍后重试或者联系管理员');
		}
	});
}
function minus(inputObj){
	var val=parseInt($(inputObj).val());
	if(val==1) return ;
	else $(inputObj).val(val-1);
}
function plus(inputObj){
	var val=parseInt($(inputObj).val());
	if(val) $(inputObj).val(val+1);
	else $(inputObj).val(1);
}
function load_order(page,state){
	if(typeof state=='undefined'||state==null) state='';
	$.ajax({
		url: GLOBAL_BASE_PATH+'shop/order/list?state='+state,
		type:'post',
		dataType:'json',
		data:{'page':page},
		beforeSend: function(){
			loading_toast();
		},
		success:function(data){
			clearToast();
			if(data&&data.length>0){
				for(var i=0;i<data.length;i++){
					var order=data[i].order;
					var list=data[i].list;
					var item='';
					item+='<dl>';
						item+='<dt><time>'+order.addtime+'</time><span>'+order.orderstateText+'</span></dt>';
						item+='<ul class="goods">';
						for(var j=0;j<list.length;j++){
							var propertynames=list[j].propertynames.split(';');
							item+='<li>';
							item+='<div class="goods_item">';
								item+='<a href="'+GLOBAL_BASE_PATH+'shop/goods/detail?id='+list[j].id+'">';
									item+='<div class="goods_img"><img src="'+list[j].mainurl+'"/></div>';
									item+='<div class="goods_info">';
										item+='<div><span class="span">'+list[j].name+'</span></div>';
										item+='<div><small class="small">'+propertynames[0]+'</small></div>';
										item+='<div><small class="small">'+propertynames[1]+'</small></div>';
									item+='</div>';
									item+='<div class="goods_price">';
										item+='<div><span class="span">￥'+(list[j].promoteprice==0?list[j].skuprice.toFixed(2):list[j].promoteprice.toFixed(2))+'</span></div>';
										item+='<div><span class="price_override span">￥'+list[j].marketprice.toFixed(2)+'</span></div>';
										item+='<div><span class="span">×'+list[j].num+'</span></div>';
									item+='</div>';
								item+='</a>';
							item+='</div>';
							item+='</li>';
						}
						item+='</ul>';
						item+='<dd><h3>商品总额</h3><i>￥'+order.sum.toFixed(2)+'(含运费 ￥0.00)</i></dd>';
						item+='<dd class="actbtn">';
						//未付款
						if(order.orderstate==0){
							item+='<input type="button" value="去付款" class="order-que" onclick="window.location.href=\'#\'"/>';
						}
						//已付款
						if(order.orderstate==1){
							item+='<input type="button" value="申请退款" class="order-red" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/refund?orderid='+order.id+'\'"/>';
						}
						//已发货
						if(order.orderstate==2){
							item+='<input type="button" value="申请退款" class="order-red" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/refund?orderid='+order.id+'\'"/>';
							item+='<input type="button" value="查看物流" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/expressinfo?shipid='+order.shipid+'&shipno='+order.shipno+'\'"/>';
							item+='<input type="button" value="确认收货" class="order-que" onclick="window.location.href=\'#\'"/>';
							
						}
						//已确认收货
						if(order.orderstate==3){
							item+='<input type="button" value="申请退款" class="order-red" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/refund?orderid='+order.id+'\'"/>';
							item+='<input type="button" value="查看物流" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/expressinfo?shipid='+order.shipid+'&shipno='+order.shipno+'\'"/>';
							item+='<input type="button" value="去评价" class="order-que" onclick="window.location.href=\'#\'"/>';
						}
						//已评价
						if(order.orderstate==4){
							item+='<input type="button" value="申请退款" class="order-red" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/refund?orderid='+order.id+'\'"/>';
							item+='<input type="button" value="查看物流" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/expressinfo?shipid='+order.shipid+'&shipno='+order.shipno+'\'"/>';
							
						}
						//交易成功
						if(order.orderstate==5){
							item+='<input type="button" value="查看物流" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/expressinfo?shipid='+order.shipid+'&shipno='+order.shipno+'\'"/>';
						}
						//退款中
						if(order.orderstate==-2){
							item+='<input type="button" value="退款详情" class="order-red" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/refund?orderid='+order.id+'\'"/>';
						}
						//已退款
						if(order.orderstate==-1){
							
						}
						item+='<input type="button" value="订单详情" onclick="window.location.href=\''+GLOBAL_BASE_PATH+'shop/order/detail?orderid='+order.id+'\'"/>';
						item+='</dd>';
					item+='</dl>';
					$('#orderList').append(item);
				}
			}
		},
		error:function(){
			clearToast();
			weui_alert('操作异常');
		}
	});
}