
	var meme={
//		websocket_url: 'ws://m.mmgmmj.com:8080/websocket/message',
		websocket_url: 'ws://192.168.0.110:8080/meme-web/websocket/message',
//		init_tmp_member_url: 'http://m.mmgmmj.com:8080/member/init_tmp_member',
		init_tmp_member_url: 'http://192.168.0.110:8080/meme-web/member/init_tmp_member',
		socket: null,
		chat_type: {
			friend: 'friend',
			group: 'group',
			kefu: 'kefu',
			sys: 'sys'
		},
		init: function(token){
			meme.socket={
				websocket: null,
				lock: false, //锁定标记，避免重复初始化
				token: token,
				event: {
					ping: 'ping',
					init: 'init',
					add: 'add',
					remove: 'remove',
					message: 'message',
					hide: 'hide',
					offline: 'offline',
					online: 'online',
					onlineCount: 'onlineCount',
					logout: 'logout',
					kickout: 'kickout',
					notice: 'notice'
				},
				reconnect: function(){
					if(meme.socket.lock) return ;
					meme.socket.lock=true;
					setTimeout(function(){
						console.log('断开重连');
						meme.socket.init();
						meme.socket.lock=false;
					}, 5000);
				},
				init: function(){
//					layui.use('layer', function(){
//						var layer=layui.layer;
//						layer.msg('===token==='+meme.socket.token);
//					});
					if ('WebSocket' in window) {
						this.websocket=new WebSocket(meme.websocket_url+'?token='+meme.socket.token);
					}else if ('MozWebSocket' in window) {
						this.websocket = new MozWebSocket(meme.websocket_url+'?token='+meme.socket.token);
					} else {
				    	alert('错误：不支持此类型浏览器，请使用谷歌chrome、火狐或者IE 10以上浏览器！');
				    	return;
					}
					this.websocket.onopen=function(){
						console.log('建立连接');
						layui.use('layer', function(){
							var layer=layui.layer;
							layer.msg('建立连接');
						});
						meme.socket.heartCheck.reset().start();
					};
					this.websocket.onmessage=function(e){
						var current=new Date();
						console.log(current.toLocaleString()+'收到消息：'+e.data);
						meme.socket.heartCheck.reset().start();
						var msg = JSON.parse(e.data);
						var data=msg.data;
						switch(msg.event){
							case meme.socket.event.ping:
								//心跳数据不处理
								break;
							case meme.socket.event.init:
								break;
							case meme.socket.event.add:
								break;
							case meme.socket.event.remove:
								break;
							case meme.socket.event.message:
								console.log('收到消息：'+JSON.stringify(data));
								break;
							case meme.socket.event.hide:
								break;
							case meme.socket.event.offline:
								break;
							case meme.socket.event.online:
								break;
							case meme.socket.event.onlineCount:
								break;
							case meme.socket.event.logout:
								break;
							case meme.socket.event.kickout:
								break;
							case meme.socket.event.notice:
								break;
						}
					};
					this.websocket.onerror=function(e){
						console.log('连接出错'+e.code);
						meme.socket.reconnect();
					};
					this.websocket.onclose=function(e){
						console.log('关闭连接');
						layui.use('layer', function(){
							var layer=layui.layer;
							layer.msg('关闭连接');
						});
						meme.socket.reconnect();
					};
				},
				heartCheck:{
					timeout: 60000,
					clientTimeoutObj: null,
					serverTimeoutObj: null,
					reset: function(){
						//清除倒计时
						clearTimeout(this.clientTimeoutObj);
						clearTimeout(this.serverTimeoutObj);
						return this;
					},
					start: function(){
						//客户端heartCheck.timeout秒后执行一次心跳数据发送，onmessage收到心跳响应触发重置倒计时
						meme.socket.heartCheck.clientTimeoutObj=setTimeout(function(){
							var message={event: meme.socket.event.ping};
							//心跳检测开始时发送一个心跳，服务端收到原封不动返回，websocket onmessage接收到消息触发心跳重置倒计时
							console.log('发送心跳');
							meme.socket.websocket.send(JSON.stringify(message));
							//等待onmessage接收消息重置心跳，超时heartCheck.timeout还未重置，说明服务端断开则执行close()
							meme.socket.heartCheck.serverTimeoutObj=setTimeout(function(){
								console.log('主动关闭');
								//执行close()，触发重新初始化操作
								meme.socket.websocket.close();
							}, meme.socket.heartCheck.timeout);
						}, this.timeout);
					}
				}
			}
			meme.socket.init();
		}
	}
	var token='';
	token=$.cookie('token');
	if(token){
		meme.init(token);
	}else{
		meme.init(null);
//		$.ajax({
//			url: meme.init_tmp_member_url,
//			type: 'post',
//			success: function(e){
//				if(e.state==1){
//					//默认14天后过期
//					var Days = 14;
//					var exp = new Date();
//					exp.setTime(exp.getTime() + Days*24*60*60*1000);
//					document.cookie = "token="+ e.data + ";expires=" + exp.toGMTString();
//					meme.init(e.data);
//				}
//			}
//		});
	}
