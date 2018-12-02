/** 域名配置 */
window.GLOBAL_DOMAIN = document.domain;
window.GLOBAL_BASE_PATH = 'http://' + GLOBAL_DOMAIN;
(function(){
	$.ajax({
		url: './member/im/validate',
		type:'get',
		dataType:'json',
		success:function(data){
			if(data.state==0){
				window.open('./member/login','_top');
			}
		},
		error:function(){
			window.open('./member/login','_top');
		}
	});
})();