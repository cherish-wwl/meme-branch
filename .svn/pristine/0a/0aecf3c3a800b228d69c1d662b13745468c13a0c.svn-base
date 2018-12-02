(function($){
	var CascadeSelect=function(ele,options){
		this.$element=ele;
		this.defaults={
			cssClass:'form-control',									//select控件css样式，默认使用bootstrap样式
			optname:'name',												//下拉选项名字段
			optvalue:'id',												//下拉选项值字段
			pid:'parentid',												//父ID字段名
			selectSize:4,												//初始化下拉框数量
			url:GLOBAL_BASE_PATH+'system/area/open/getChildAreas.do',	//下拉项数据获取url
//			callback:null,												//回调函数
			selectname:['country','province','city','district'],		//按下拉框顺序提供name属性名，默认国省市区四级
			selected:null												//选中值，数据格式{'country':'','province':'','city':'','district':''}
		};
		this.options = $.extend({}, this.defaults, options);
	}
	CascadeSelect.prototype={
	}
	
	$.fn.changeParent=function(nexSelectObj,options,callback){
		if(!(nexSelectObj==undefined||nexSelectObj==null)){
			var pid=$(this).val();
			var cascadeSelect=new CascadeSelect(this,options);
			var optname=cascadeSelect.options.optname;
			var optvalue=cascadeSelect.options.optvalue;
			var url=cascadeSelect.options.url;
			if(pid){
				var prev=$(nexSelectObj).prev('select');
				//清除下级所有级联select选项
				if(typeof prev!='undefined'){
					next=$(prev).next('select');
					var i=0;
					while(typeof $(next)[0]!='undefined'){
						$(next).empty();
						$(next).append('<option value="">请选择</option>');
						next=$(next).next('select');
					}
				}
				$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data:{'pid':pid},
					success: function(data){
						if(data&&data.length>0){
							var options='<option value="">请选择</option>';
							for(var i=0;i<data.length;i++){
								options+='<option value="'+data[i][optvalue]+'">'+data[i][optname]+'</option>';
							}
							$(nexSelectObj).empty();
							$(nexSelectObj).append(options);
						}
						if(callback!=null&& typeof callback =='function') callback();
					},
					error:function(){
						alert('连接异常，请稍后刷新重试或者联系管理员');
					}
				});
			}
		}
	}
	
	$.fn.cascadeSelect=function(options){
//		this.defaults={
//			optname:'name',								//下拉选项名字段
//			optvalue:'id',								//下拉选项值字段
//			pid:'parentid',								//父ID字段名
//			selectSize:4,								//初始化下拉框数量
//			url:GLOBAL_BASE_PATH+'system/area/open/getChildAreas.do',	//下拉项数据获取url
//			callback:null
//		};
//		this.options = $.extend({}, this.defaults, options);
		var cascadeSelect=new CascadeSelect(this,options);
		var names=cascadeSelect.options.selectname;
		var namesize=names.length;
		var selectSize=cascadeSelect.options.selectSize;
		if(namesize!=selectSize){
			alert('请提供完整所有下拉框name属性名称');
			return ;
		}
		
		var select='';
		var optname=cascadeSelect.options.optname;
		var optvalue=cascadeSelect.options.optvalue;
		var cssClass=cascadeSelect.options.cssClass;
		var url=cascadeSelect.options.url;
		var that=this;
		var selected=cascadeSelect.options.selected;
		if(null==selected){
			$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				data:{'pid':''},
				success: function(data){
					if(data&&data.length>0){
						select+='<select id="select_0" name="'+names[0]+'" onchange="$(this).changeParent($(\'#select_1\'),options)" class="'+cssClass+'">';
						select+='<option value="">请选择</option>';
						for(var j=0;j<data.length;j++){
							select+='<option value="'+data[j][optvalue]+'">'+data[j][optname]+'</option>';
						}
						select+='</select>';
						
						for(var i=1;i<selectSize;i++){
							select+='<select id="select_'+i+'" name="'+names[i]+'" onchange="$(this).changeParent($(\'#select_'+(i+1)+'\'),options)" class="'+cssClass+'">';
							select+='<option value="">请选择</option>';
							select+='</select>';
						}
						$(that).append(select);
					}
				},
				error:function(){alert('连接异常，请稍后刷新重试或者联系管理员');}
			});
		}else{
			selected=eval('(' + selected + ')');
			$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				data:{'pid':''},
				success: function(data){
					if(data&&data.length>0){
						select='';
						select+='<select id="select_0" name="'+names[0]+'" onchange="$(this).changeParent($(\'#select_1\'),options)" class="'+cssClass+'">';
						select+='<option value="">请选择</option>';
						for(var j=0;j<data.length;j++){
							select+='<option value="'+data[j][optvalue]+'">'+data[j][optname]+'</option>';
						}
						select+='</select>';
						$(that).append(select);
						if(selected[names[0]]) $('select[name="'+names[0]+'"]').val(selected[names[0]]);
						
						for(var i=1;i<names.length;i++){
							var val=selected[names[i]];
							var pid=selected[names[i-1]];
							if(pid){
								$.ajax({
									url: url,
									type: 'post',
									dataType: 'json',
									data:{'pid':pid},
									async:false,
									success: function(data){
										if(data&&data.length>0){
											select='';
											select+='<select id="select_'+i+'" name="'+names[i]+'" onchange="$(this).changeParent($(\'#select_'+(i+1)+'\'),options)" class="'+cssClass+'">';
											select+='<option value="">请选择</option>';
											for(var j=0;j<data.length;j++){
												select+='<option value="'+data[j][optvalue]+'">'+data[j][optname]+'</option>';
											}
											select+='</select>';
											$(that).append(select);
											if(val) $('select[name="'+names[i]+'"]').val(val);
										}
									},
									error:function(){alert('连接异常，请稍后刷新重试或者联系管理员');}
								});
							}else{
								select='';
								select+='<select id="select_'+i+'" name="'+names[i]+'" onchange="$(this).changeParent($(\'#select_'+(i+1)+'\'),options)" class="'+cssClass+'">';
								select+='<option value="">请选择</option>';
								select+='</select>';
								$(that).append(select);
							}
						}
					}
				},
				error:function(){alert('连接异常，请稍后刷新重试或者联系管理员');}
			});
		}
	}
})(jQuery);