var meme = {
    init_url: './member/im/init',
    get_stranger_url: './member/im/getStrangers',
    apply_friend_url: './member/im/doApplyFriend',
    accept_friend_url: './member/im/acceptFriend',
    upload_image_url: './member/im/uploadImage',
    upload_file_url: './member/im/uploadFile',
    get_apply_friend_url: './member/im/getApplyFriends',
    del_friend_url: './member/im/delFriend',
    get_mine_url: './member/im/getMine',
    logout_url: './member/logout',
    register_url: './member/register',
    get_group_members_url: '',
    chat_log_url: '',
//	websocket_url: 'ws://m.mmgmmj.com:8080/websocket/message',
    websocket_url: 'ws://m.mmgmmj.com:8080/websocket/message',
    appName: '么么聊',
    layim: null,
    socket: null,
    isNewFriend: false,
    isgroup: false,
    notice: true,
    title: '',
    chat_type: {
        friend: 'friend',
        group: 'group',
        kefu: 'kefu',
        sys: 'sys'
    },
    copyright: true,
    heart_beat_count: 0,
    voice: 'default.mp3',
    moreList: [
        {
            alias: 'edit'
            , title: '个人资料'
            , iconUnicode: '&#xe620;'
            , iconClass: ''
        },
        {
            alias: 'friend_apply'
            , title: '好友申请'
            , iconUnicode: '&#xe612;'
            , iconClass: ''
        },
        {
            alias: 'logout'
            , title: '退出登录'
            , iconUnicode: '&#xe628;'
            , iconClass: ''
        }
    ],
    init: function (friendid) {
        $.post(this.init_url, {friendid: friendid}, function (data) {
            if (data.state == 1) {
//				meme.isNewFriend=(data.data.mine.mtype==-1)?false:true;
//				meme.isgroup=(data.data.mine.mtype==-1)?false:true;
                meme.isNewFriend = true;
                meme.isgroup = true;
                var title = '<img class="title-avatar" src="' + data.data.mine.avatar + '"/>';
                title += '&nbsp;&nbsp;&nbsp;';
                title += '<span style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;font-size: 17px;text-shadow: none!important;">';
                title += data.data.mine.nickname;
                meme.title = title;
                meme.init_layim(data.data);
            } else {
                alert(data.message);
            }
        }, 'json');
    },
    init_layim: function (initJson) {
        var from = initJson.from;
        layui.use(['mobile', 'layer'], function () {
            var mobile = layui.mobile;
            meme.layim = mobile.layim;
            var layer = mobile.layer;
            meme.layim.config({
                title: meme.title,
                init: initJson,
                uploadImage: {url: meme.upload_image_url, type: 'post'},
                uploadFile: {url: meme.upload_file_url, type: 'post'},
                isNewFriend: meme.isNewFriend,
                isgroup: meme.isgroup,
                notice: meme.notice,
                moreList: meme.moreList,
                copyright: meme.copyright,
                voice: meme.voice
            });

            meme.socket = {
                websocket: null,
                lock: false, //锁定标记，避免重复初始化
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
                reconnect: function () {
                    if (meme.socket.lock) return;
                    meme.socket.lock = true;
                    setTimeout(function () {
                        console.log('断开重连');
                        meme.socket.init();
                        meme.socket.lock = false;
                    }, 5000);
                },
                init: function () {
                    this.websocket = new WebSocket(meme.websocket_url);
                    this.websocket.onopen = function () {
                        console.log('建立连接');
                        meme.socket.heartCheck.reset().start();
                    };
                    this.websocket.onmessage = function (e) {
                        var current = new Date();
                        console.log(current.toLocaleString() + '收到消息：' + e.data);
                        meme.socket.heartCheck.reset();
                        var msg = JSON.parse(e.data);
                        var data = msg.data;
                        switch (msg.event) {
                            case meme.socket.event.ping:
                                //心跳数据不处理
                                break;
                            case meme.socket.event.init:
                                console.log('连接建立，初始化数据：' + JSON.stringify(data));
                                var online_friends = data.online_friends;
                                var unread_messages = data.unread_messages;
                                var alias = data.alias;
                                var online_total = data.online_total;

                                //初始化在线好友状态
                                if (online_friends && online_friends.length > 0) {
                                    for (var i = 0; i < online_friends.length; i++) {
                                        meme.layim.setFriendStatus(online_friends[i], 'online');
                                    }
                                }
                                //显示未读消息
                                if (unread_messages && unread_messages.length > 0) {
                                    for (var i = 0; i < unread_messages.length; i++) {
                                        meme.layim.getMessage(unread_messages[i]);
                                    }
                                }
                                if (alias && alias.length > 0) {
                                    for (var i = 0; i < alias.length; i++) {
                                        meme.layim.showNew(alias[i], true);
                                    }
                                }
                                $('title').html(meme.appName + '：' + online_total + '人在线');
                                break;
                            case meme.socket.event.add:
                                console.log('收到添加好友事件：' + JSON.stringify(data));
                                if (data) {
                                    meme.layim.addList({
                                        type: data.type,
                                        avatar: data.avatar,
                                        username: data.nickname,
                                        groupid: data.groupid,
                                        id: data.id,
                                        sign: data.sign
                                    });
                                }
                                break;
                            case meme.socket.event.remove:
                                console.log('收到移除好友事件：' + JSON.stringify(data));
                                meme.layim.removeList(data);
                                break;
                            case meme.socket.event.message:
                                console.log('收到消息：' + JSON.stringify(data));
                                //layer.msg(data.username+':'+data.content,{time: 20000});
                                meme.layim.getMessage(data);
                                break;
                            case meme.socket.event.hide:
                                break;
                            case meme.socket.event.offline:
                                var friendid = data.friendid;
                                var online_total = data.online_total;
                                $('title').html(meme.appName + '：' + online_total + '人在线');
                                console.log('编号为' + friendid + '的好友下线');
                                if (friendid != meme.layim.cache().mine.id) {
                                    meme.layim.setFriendStatus(friendid, 'offline');

                                    //修改本地缓存状态
                                    //var local = layui.data('meme.layim',  friendid);
                                    //local.status = 'offline';
                                    //layui.data('meme.layim', local);
                                }
                                break;
                            case meme.socket.event.online:
                                var friendid = data.friendid;
                                var online_total = data.online_total;
                                $('title').html(meme.appName + '：' + online_total + '人在线');
                                console.log('编号为' + friendid + '的好友上线');
                                if (friendid != meme.layim.cache().mine.id) {
                                    meme.layim.setFriendStatus(friendid, 'online');

                                    //修改本地缓存状态
                                    //var local = layui.data('meme.layim',  friendid);
                                    //local.status = 'online';
                                    //layui.data('meme.layim', local);
                                }
                                break;
                            case meme.socket.event.onlineCount:
                                var count = data.onlineCount;
                                $('title').html(meme.appName + '：' + count + '人在线');
                                break;
                            case meme.socket.event.logout:
                                break;
                            case meme.socket.event.kickout:
                                alert('账号在异地登录，强制下线');
                                $.cookie('token', '', {expires: -1});
                                window.location.href = meme.logout_url;
                                break;
                            case meme.socket.event.notice:
                                if (data && data.length > 0) {
                                    for (var i = 0; i < data.length; i++) {
                                        meme.layim.showNew(data[i], true);
                                    }
                                }
                                break;
                        }

                    };
                    this.websocket.onerror = function (e) {
                        console.log('连接出错' + e.code);
                        meme.socket.reconnect();
                    };
                    this.websocket.onclose = function (e) {
                        console.log('关闭连接');
                        meme.socket.heartCheck.close();
                        meme.socket.reconnect();
                    };
                },
                heartCheck: {
                    timeout: 30000,
                    keepalive: null,
                    receiveMessageTimer: null,
                    last_health_time: -1,
                    reset: function () {
                        //清除倒计时
                        clearTimeout(this.receiveMessageTimer);
                        //等待onmessage接收消息重置心跳，超时heartCheck.timeout还未重置，说明服务端断开则执行close()
                        this.receiveMessageTimer = setTimeout(function () {
                            console.log('主动关闭');
                            //执行close()，触发重新初始化操作
                            meme.socket.websocket.close();
                        }, this.timeout);
                        return this;
                    },
                    start: function () {
                        //客户端heartCheck.timeout秒后执行一次心跳数据发送，onmessage收到心跳响应触发重置倒计时
                        this.keepalive = setInterval(function () {
                            var time = new Date().getTime();
                            if (this.last_health_time !== -1 && time - this.last_health_time > 20000) { // 不是刚开始连接并且20s
                                meme.socket.websocket.close()
                            } else {
                                if(meme.socket.websocket.bufferedAmount === 0 && meme.socket.websocket.readyState === 1) {
                                    var message = {event: meme.socket.event.ping};
                                    //心跳检测开始时发送一个心跳，服务端收到原封不动返回，websocket onmessage接收到消息触发心跳重置倒计时
                                    console.log('发送心跳');
                                    meme.socket.websocket.send(JSON.stringify(message));
                                    this.last_health_time = time;
                                }
                            }
                        }, 1000);
                    },
                    close: function () {
                        clearTimeout(this.receiveMessageTimer);
                        clearInterval(this.keepalive)
                    }
                }
            }

            meme.socket.init();

            //layim监听发送消息事件
            meme.layim.on('sendMessage', function (res) {
                var message = {event: meme.socket.event.message, data: res};
                console.log('发送消息：' + JSON.stringify(message));
                meme.socket.websocket.send(JSON.stringify(message));
            });

            //layim监听点击新朋友按钮
            meme.layim.on('newFriend', function () {
                var mtype = meme.layim.cache().mine.mtype;
                G_current = 1;
                if (mtype == -1) {
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.confirm('临时访客需要注册成会员才能加好友？', ['去注册', '取消'], function () {
                            window.location.href = meme.register_url;
                        }, function () {

                        });
                    });
                } else {
                    $.ajax({
                        url: meme.get_stranger_url,
                        type: 'post',
                        data: {'page': 1},
                        success: function (data) {
                            G_total = data.pages;
                            meme.layim.panel({
                                title: '查找添加新朋友',
                                tpl: document.getElementById('searchFriends').innerHTML,
                                data: {
                                    list: data
                                }
                            });
                        }
                    });
                }
            });

            //监听更多tab页面列表项点击事件
            meme.layim.on('moreList', function (obj) {
                switch (obj.alias) {
                    case 'edit':
                        /*$.get(meme.get_mine_url, function(data){
                         meme.layim.panel({
                         title: '个人资料',
                         tpl: document.getElementById('myinfo').innerHTML,
                         data: {
                         mine: data
                         }
                         });
                         });*/
                        break;
                    case 'friend_apply':
                        $.get(meme.get_apply_friend_url + '?page=1', function (data) {
                            var list = data.list;
                            var pages = data.pages;
                            meme.layim.panel({
                                title: '好友申请列表',
                                tpl: document.getElementById('applyFriend').innerHTML,
                                data: {
                                    list: list
                                }
                            });
                        });
                        break;
                    case 'logout':
                        window.location.href = meme.logout_url;
                        break;
                }
            });

            //使用点击聊天加载layim时，自定义聊天窗口
            if (null != from && typeof from != 'undefined') {
                meme.layim.chat({
                    name: from.nickname
                    , type: 'friend'
                    , avatar: from.avatar
                    , id: from.memberid
                });
            }

            //每次窗口打开或切换，即更新对方的状态
            /*meme.layim.on('chatChange', function(res){
             var type = res.data.type;
             console.log('窗口改变'+JSON.stringify(res));
             if(type === 'friend'){
             meme.layim.setChatStatus('在线');
             }
             });*/
        });
    }
}