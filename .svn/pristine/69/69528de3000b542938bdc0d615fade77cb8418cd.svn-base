package com.meme.im.websocket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.DictCache;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.entity.Mine;
import com.meme.im.entity.To;
import com.meme.im.entity.enums.ChatType;
import com.meme.im.pojo.ImFriend;
import com.meme.im.pojo.ImFriendApply;
import com.meme.im.pojo.ImGroup;
import com.meme.im.pojo.ImMessage;
import com.meme.im.pojo.Member;
import com.meme.im.redis.WebsocketSessionService;
import com.meme.im.service.ImFriendApplyService;
import com.meme.im.service.ImFriendService;
import com.meme.im.service.ImGroupService;
import com.meme.im.service.ImMessageService;
import com.meme.im.service.MemberService;
import com.meme.im.util.IMConstants;
import com.meme.im.websocket.message.entity.Message;
import com.meme.im.websocket.message.event.EventEnum;

/**
 * spring websocket服务端
 *
 * @author hzl
 */
public class WebSocketServer extends TextWebSocketHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(WebSocketServer.class);
    public static ConcurrentHashMap<String, ServerPoint> sessions = new ConcurrentHashMap<String, ServerPoint>();
    @Resource
    private ImFriendService imFriendService;
    @Resource
    private ImGroupService imGroupService;
    @Resource
    private ImMessageService imMessageService;
    @Resource
    private ImFriendApplyService imFriendApplyService;
    @Resource
    private MemberService memberService;
    @Resource
    private WebsocketSessionService websocketSessionService;

    /**
     * 获取用户memberid
     *
     * @param session
     * @return
     */
    private Object getMemberid(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        String token = null;
        if (null != attributes) {
            Object obj = attributes.get(IMConstants.COOKIE_TOKEN_KEY);
            if (null != obj) token = (String) obj;
        }
        Object memberid = null;
        if (StringUtil.isAllNotEmpty(token)) {
            Map<String, Object> claims = JWTUtil.verifyToken(token);
            if (null != claims && claims.size() > 0) {
                memberid = claims.get(IMConstants.COOKIE_MEMBER_KEY);
            }
        }
        return memberid;
    }

    /**
     * @param msgStr
     * @return
     */
    private TextMessage buildWsMessage(String msgStr) {
        TextMessage message = new TextMessage(msgStr);
        return message;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Object memberid = this.getMemberid(session);
        logger.info("===afterConnectionEstablished===" + memberid.toString());
        if (null != memberid) {

            //同账号多地登录需要踢下线
//			if(sessions.containsKey(memberid.toString())){
//				WebSocketSession mySession=sessions.get(memberid.toString());
//				if(null!=mySession&&mySession.isOpen()){
//					Message msg=new Message(EventEnum.KICKOUT.getEvent());
//					logger.info("账号冲突，踢用户下线："+msg.toString());
//					mySession.sendMessage(this.buildWsMessage(msg.toString()));
//				}
//			}

            Member member = this.memberService.selectByPrimaryKey(memberid);
            ServerPoint point = new ServerPoint(member, session);

            List<String> online_friend_ids = new ArrayList<String>();

            //发送online事件，通知所有在线好友已上线
            List<Map<String, Object>> friends = this.imFriendService.selectFriends(memberid);
            if (null != friends && friends.size() > 0) {
                for (Map<String, Object> friend : friends) {
                    String friendid = friend.get("memberid").toString();
                    ServerPoint fpoint = sessions.get(friendid);
                    if (null != fpoint) {
                        WebSocketSession ws = fpoint.getSession();
                        if (null != ws && ws.isOpen()) {
                            online_friend_ids.add(friendid);
                            JSONObject data = new JSONObject();
                            data.put("friendid", memberid.toString());
                            data.put("online_total", sessions.size() + 1);
                            Message msg = new Message(EventEnum.ONLINE.getEvent(), data);
                            ws.sendMessage(this.buildWsMessage(msg.toString()));
                            logger.info("编号为" + memberid + "会员的发送给朋友" + friendid + "的上线通知：" + msg.toString());
                        }
                    }
                }
            }

            if (sessions.size() > 0) {
                for (Map.Entry<String, ServerPoint> entry : sessions.entrySet()) {
                    if (null != member.getMtype() && member.getMtype() == 1) {
                        //客服上线则通知所有在线用户（包括客服）
                        if (!member.getMemberid().toString().equals(entry.getKey())) {
                            WebSocketSession ws = entry.getValue().getSession();
                            if (null != ws && ws.isOpen()) {
                                online_friend_ids.add(entry.getKey());
                                JSONObject data = new JSONObject();
                                data.put("friendid", memberid.toString());
                                data.put("online_total", sessions.size() + 1);
                                Message msg = new Message(EventEnum.ONLINE.getEvent(), data);
                                ws.sendMessage(this.buildWsMessage(msg.toString()));
                            }
                        }
                    } else if (null != member.getMtype() && member.getMtype() == 0) {
                        //非客服上线时，上线通知添加在线客服ID
                        if (entry.getValue().getMember().getMtype() == 1) online_friend_ids.add(entry.getKey());
                    }
                }
            }


            //添加会话入连接池
            sessions.put(memberid.toString(), point);

            //脱敏数据
            member.setAccount(null);
            member.setMpassword(null);
            member.setSalt(null);
            //添加进redis缓存，客服不加入缓存
            if (member.getMtype() != 1) this.websocketSessionService.add(member);

            logger.info("增加编号为" + memberid + "会员的连接，当前连接人数：" + sessions.size());


            //连接建立时发送init事件，回传在线好友id和离线未读数据
            Message msg = new Message();
            msg.setEvent(EventEnum.INIT.getEvent());
            JSONObject data = new JSONObject();

            ImFriendApply tmp = new ImFriendApply();
            tmp.setAcceptid(Long.valueOf(memberid.toString()));
            tmp.setState(0);
            tmp.setType(0);
            List<ImFriendApply> applies = this.imFriendApplyService.selectByEntity(tmp);
            if (null != applies && applies.size() > 0) {
                JSONArray alias = new JSONArray();
                alias.add("friend_apply");
                alias.add("More");
                data.put("alias", alias);
            }
            tmp.setType(1);
            applies = this.imFriendApplyService.selectByEntity(tmp);
            if (null != applies && applies.size() > 0) {
                JSONArray alias = new JSONArray();
                alias.add("group_apply");
                alias.add("More");
                data.put("alias", alias);
            }
            data.put("online_total", sessions.size());
            //data.put("online_friends", online_friend_ids);//返回所有在线好友id
            List<Map<String, Object>> list = this.imMessageService.selectUnReadMessages(memberid.toString());
            if (null != list && list.size() > 0) {
            		for(Map<String,Object> map:list) {
            			for(String str:map.keySet()) {
            				if(str.equals("mine")) {
            					if((Long)map.get(str)  == 1) {
            						map.put(str, true);
            					}else {
            						map.put(str, false);
            					}
            				}
            				if(str.equals("id")) {
            					map.put(str, map.get(str).toString());  //转换成string避免丢失精度
            				}
            			}
            		}
                data.put("unread_messages", list);//返回所有未读消息

                List<Object> msgids = new ArrayList<Object>();
                for (Map<String, Object> map : list) {
                    Object msgid = map.get("msgid");
                    msgids.add(msgid);
                }
                //批量更新消息状态
                this.imMessageService.updateReadState(msgids);
            }
            
            List<Map<String, Object>> list2 = this.imMessageService.selectUnReadGroupMessages(memberid.toString());
            if (null != list2 && list2.size() > 0) {
            	for(Map<String,Object> map:list2) {
            		for(String str:map.keySet()) {
            			if(str.equals("mine")) {
            				if((Long)map.get(str)  == 1) {
            					map.put(str, true);
            				}else {
            					map.put(str, false);
            				}
            			}
            			if(str.equals("id")) {
            				map.put(str, map.get(str).toString());  //转换成string避免丢失精度
            			}
            		}
            	}
            	List<Map<String, Object>> object = (List<Map<String, Object>>)data.get("unread_messages");
            	if(object != null) {
            		object.addAll(list2);
            	}else {
            		data.put("unread_messages", list2);//返回所有未读消息
            	}
            	List<Object> msgids = new ArrayList<Object>();
            	for (Map<String, Object> map : list2) {
            		Object msgid = map.get("msgid");
            		msgids.add(msgid);
            	}
            	//批量更新消息状态
            	this.imMessageService.updateReadState(msgids);
            }
            msg.setData(data);
            session.sendMessage(this.buildWsMessage(msg.toString()));
        }
    }

    /**
     * 消息处理
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Object memberid = this.getMemberid(session);
        Member member = this.memberService.selectByPrimaryKey(memberid);
        String payload = message.getPayload();
        Message msg = JSONObject.parseObject(payload, Message.class);
        logger.info("===handleTextMessage===" + msg.toString());
        if (null != msg && StringUtil.isAllNotEmpty(msg.getEvent())) {
            String event = msg.getEvent();
            Object obj = msg.getData();
            EventEnum eventEnum = EventEnum.valueOf(event.toUpperCase());
            switch (eventEnum) {
                case PING:
                    logger.info("收到编号为" + memberid + "会员的心跳");
                    session.sendMessage(message);
                    break;
                case INIT:
                    logger.info("收到编号为" + memberid + "会员发送的初始化事件：" + payload);
                    break;
                case ADD:
                    logger.info("收到编号为" + memberid + "会员发送的添加好友或群事件：" + payload);
                    JSONObject addJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    String itype = addJson.getString("type");
                    Object to_id = addJson.get("friendid");
                    ChatType id_type = ChatType.valueOf(itype.toUpperCase());
                    if (null != id_type) {
                        if (id_type == ChatType.FRIEND) {
                            List<Map<String, Object>> friendInfo = this.imFriendService.selectFriend(to_id, memberid);
                            List<Map<String, Object>> mineInfo = this.imFriendService.selectFriend(memberid, to_id);
                            if (sessions.containsKey(to_id.toString()) && sessions.get(to_id.toString()).getSession().isOpen()) {
                                msg = new Message(EventEnum.ADD.getEvent());

                                if (null != friendInfo && friendInfo.size() > 0) {
                                    //脱敏数据
                                    friendInfo.get(0).put("account", "");
                                    friendInfo.get(0).put("mpassword", "");
                                    friendInfo.get(0).put("salt", "");
                                    friendInfo.get(0).put("type", id_type.getType());

                                    //long类型数据转成字符串，避免js读取时丢失精度
                                    String strid = friendInfo.get(0).get("memberid").toString();
                                    String grupid = friendInfo.get(0).get("groupid").toString();
                                    friendInfo.get(0).put("memberid", strid);
                                    friendInfo.get(0).put("id", strid);
                                    friendInfo.get(0).put("groupid", grupid);
                                    msg.setData(friendInfo.get(0));
                                }
                                sessions.get(to_id.toString()).getSession().sendMessage(this.buildWsMessage(msg.toString()));
                            }
                            if (null != mineInfo && mineInfo.size() > 0) {
                                msg = new Message(EventEnum.ADD.getEvent());
                                //脱敏数据
                                mineInfo.get(0).put("account", "");
                                mineInfo.get(0).put("mpassword", "");
                                mineInfo.get(0).put("salt", "");
                                mineInfo.get(0).put("type", id_type.getType());

                                //long类型数据转成字符串，避免js读取时丢失精度
                                String strid = mineInfo.get(0).get("memberid").toString();
                                String grupid = mineInfo.get(0).get("groupid").toString();
                                mineInfo.get(0).put("memberid", strid);
                                mineInfo.get(0).put("id", strid);
                                mineInfo.get(0).put("groupid", grupid);
                                msg.setData(mineInfo.get(0));
                                session.sendMessage(this.buildWsMessage(msg.toString()));
                            }
                        }else if(id_type == ChatType.GROUP) {
                        		String groupid = addJson.getString("groupid");
                        		ImGroup imGroup = this.imGroupService.selectByPrimaryKey(Long.valueOf(groupid));
                        		 if (sessions.containsKey(to_id.toString()) && sessions.get(to_id.toString()).getSession().isOpen()) {
                                 msg = new Message(EventEnum.ADD.getEvent());
                                 msg.setData(imGroup);
                                 sessions.get(to_id.toString()).getSession().sendMessage(this.buildWsMessage(msg.toString()));
                             }
                        }
                    }
                    break;
                case REMOVE:
                    logger.info("收到编号为" + memberid + "会员发送的移除好友或群事件：" + payload);
                    addJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    itype = addJson.getString("type");
                    id_type = ChatType.valueOf(itype.toUpperCase());
                    if (null != id_type) {
                        if (id_type == ChatType.FRIEND) {
                        	 	to_id = addJson.get("friendid");
                            JSONObject data = new JSONObject();
                            data.put("type", ChatType.FRIEND.getType());
                            data.put("id", memberid.toString());
                            msg = new Message(EventEnum.REMOVE.getEvent());
                            msg.setData(data);
                            if (sessions.containsKey(to_id.toString()) && sessions.get(to_id.toString()).getSession().isOpen()) {
                                sessions.get(to_id.toString()).getSession().sendMessage(this.buildWsMessage(msg.toString()));
                            }
                        }else if(id_type == ChatType.GROUP) {
                        		to_id = addJson.getLong("groupid");
                        		ImGroup imGroup = this.imGroupService.selectByPrimaryKey(to_id);
                        		if(imGroup != null && imGroup.getMemberid().equals(memberid)) {
                        			JSONObject data = new JSONObject();
                                data.put("type", ChatType.GROUP.getType());
                                data.put("id", to_id.toString());
                                msg = new Message(EventEnum.REMOVE.getEvent());
                                msg.setData(data);
                                ImFriend imFriend = new ImFriend();
                                imFriend.setGroupid((Long)to_id);
                                List<ImFriend> list = this.imFriendService.selectByEntity(imFriend);
                                for(ImFriend i:list) {
                               	 	String sessionid = i.getFriendid().toString();
                               	 	if (sessions.containsKey(sessionid) && sessions.get(sessionid).getSession().isOpen()) {
                                        sessions.get(sessionid).getSession().sendMessage(this.buildWsMessage(msg.toString()));
                                    }
                                }
                        		}
                        		this.imGroupService.exit(memberid, to_id);
                        }
                    }
                    break;
                case MESSAGE:
                    logger.info("收到编号为" + memberid + "会员发送的消息：" + payload);
                    JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(obj));

                    JSONObject mineJson = json.getJSONObject("mine");
                    JSONObject toJson = json.getJSONObject("to");
                    Mine mine = JSONObject.parseObject(mineJson.toJSONString(), Mine.class);
                    To to = JSONObject.parseObject(toJson.toJSONString(), To.class);

                    String type = to.getType();
                    String toFriendid = to.getId().toString();
                    String content = mine.getContent();
                    if (StringUtil.isEmpty(type)) return;
                    ChatType chatType = ChatType.valueOf(type.toUpperCase());
                    if (null == chatType) return;
                    if(chatType == ChatType.FRIEND) {
	                    ServerPoint point = sessions.get(toFriendid);
	                    if (null != point) {
	                        WebSocketSession ws = point.getSession();
	                        if (null != ws && ws.isOpen()) {
	                            //用户在线则直接发回
	                            synchronized (ws) {
	                                Message toMsg = new Message();
	                                toMsg.setEvent(EventEnum.MESSAGE.getEvent());
	                                JSONObject toData = new JSONObject();
	                                toData.put("username", mine.getNickname());
	                                toData.put("avatar", mine.getAvatar());
	                                toData.put("id", "" + mine.getMemberid().toString());
	                                toData.put("type", type);
	                                toData.put("content", content);
	                                toData.put("mine", false);
	                                toData.put("timestamp", new Date());
	                                toMsg.setData(toData);
	                                ws.sendMessage(this.buildWsMessage(toMsg.toString()));
	                                logger.info("发消息：" + toMsg.toString());
	
	                                //发送完的消息持久化入消息表
	                                ImMessage imsg = new ImMessage();
	                                long id = IDGenerator.generateId();
	                                imsg.setMsgid(id);
	                                imsg.setAcceptid(Long.valueOf(toFriendid));
	                                imsg.setContent(content);
	                                imsg.setSendid(mine.getMemberid());
	                                imsg.setSendtime(new Date());
	                                imsg.setAccepttime(new Date());
	                                imsg.setState(1);
	                                imsg.setMsgtype(chatType.getCode());
	
	                                //发送方或接收方为客服时，消息类型设置为客服消息
	                                if (null != point.getMember() && point.getMember().getMtype() == 1)
	                                    imsg.setMsgtype(ChatType.KEFU.getCode());
	                                if (null != member && member.getMtype() == 1) imsg.setMsgtype(ChatType.KEFU.getCode());
	
	                                this.imMessageService.insertSelective(imsg);
	                            }
	                        } else {
	                            //用户离线则存入消息表
	                            ImMessage imsg = new ImMessage();
	                            long id = IDGenerator.generateId();
	                            imsg.setMsgid(id);
	                            imsg.setAcceptid(Long.valueOf(toFriendid));
	                            imsg.setContent(content);
	                            imsg.setSendid(mine.getMemberid());
	                            imsg.setSendtime(new Date());
	                            imsg.setMsgtype(chatType.getCode());
	                            this.imMessageService.insertSelective(imsg);
	                        }
	                    } else {
	                        //用户离线则存入消息表
	                        ImMessage imsg = new ImMessage();
	                        long id = IDGenerator.generateId();
	                        imsg.setMsgid(id);
	                        imsg.setAcceptid(Long.valueOf(toFriendid));
	                        imsg.setContent(content);
	                        imsg.setSendid(mine.getMemberid());
	                        imsg.setSendtime(new Date());
	                        imsg.setMsgtype(chatType.getCode());
	                        this.imMessageService.insertSelective(imsg);
	                    }
                    }else if(chatType == ChatType.GROUP) {
                    		ImFriend imFriend = new ImFriend();
                    		imFriend.setGroupid(to.getId());
                    		List<ImFriend> imFriends = this.imFriendService.selectByEntity(imFriend);
                    		for(ImFriend i:imFriends) {
                    			if(i.getFriendid().equals(mine.getMemberid())) {
                    				continue;
                    			}
                    			ServerPoint point = sessions.get(i.getFriendid().toString());
        	                    if (null != point) {
        	                        WebSocketSession ws = point.getSession();
        	                        if (null != ws && ws.isOpen()) {
        	                            //用户在线则直接发回
        	                            synchronized (ws) {
        	                                Message toMsg = new Message();
        	                                toMsg.setEvent(EventEnum.MESSAGE.getEvent());
        	                                JSONObject toData = new JSONObject();
        	                                toData.put("username", mine.getNickname());
        	                                toData.put("avatar", mine.getAvatar());
        	                                toData.put("id", "" + to.getId());
        	                                toData.put("type", type);
        	                                toData.put("content", content);
        	                                toData.put("mine", false);
        	                                toData.put("timestamp", new Date());
        	                                toMsg.setData(toData);
        	                                ws.sendMessage(this.buildWsMessage(toMsg.toString()));
        	                                logger.info("发消息：" + toMsg.toString());
        	
        	                                //发送完的消息持久化入消息表
        	                                ImMessage imsg = new ImMessage();
        	                                long id = IDGenerator.generateId();
        	                                imsg.setMsgid(id);
        	                                imsg.setAcceptid(i.getFriendid());
        	                                imsg.setContent(content);
        	                                imsg.setSendid(mine.getMemberid());
        	                                imsg.setSendtime(new Date());
        	                                imsg.setAccepttime(new Date());
        	                                imsg.setState(1);
        	                                imsg.setGroupid(to.getId());
        	                                imsg.setMsgtype(chatType.getCode());
        	                                this.imMessageService.insertSelective(imsg);
        	                            }
        	                        } else {
        	                            //用户离线则存入消息表
        	                            ImMessage imsg = new ImMessage();
        	                            long id = IDGenerator.generateId();
        	                            imsg.setMsgid(id);
        	                            imsg.setAcceptid(i.getFriendid());
        	                            imsg.setContent(content);
        	                            imsg.setSendid(mine.getMemberid());
        	                            imsg.setSendtime(new Date());
        	                            imsg.setGroupid(to.getId());
        	                            imsg.setMsgtype(chatType.getCode());
        	                            this.imMessageService.insertSelective(imsg);
        	                        }
        	                    } else {
			                    	 	//用户离线则存入消息表
		                            ImMessage imsg = new ImMessage();
		                            long id = IDGenerator.generateId();
		                            imsg.setMsgid(id);
		                            imsg.setAcceptid(i.getFriendid());
		                            imsg.setContent(content);
		                            imsg.setSendid(mine.getMemberid());
		                            imsg.setSendtime(new Date());
		                            imsg.setGroupid(to.getId());
		                            imsg.setMsgtype(chatType.getCode());
		                            this.imMessageService.insertSelective(imsg);
        	                    }
                    		}
                    }
                    break;
                case HIDE:
                    break;
                case OFFLINE:
                    logger.info("收到编号为" + memberid + "会员发送的下线事件：" + payload);
                    break;
                case ONLINE:
                    logger.info("收到编号为" + memberid + "会员发送的上线事件：" + payload);

                    break;
                case ONLINECOUNT:
                    logger.info("收到编号为" + memberid + "会员发送的在线人数统计事件：" + payload);
                    break;
                case LOGOUT:
                    logger.info("收到编号为" + memberid + "会员发送的退出登录事件：" + payload);
                    sessions.remove(memberid.toString());
                    session.close();
                    break;
                case NOTICE:
                    json = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    Object friendid = json.get("friendid");
                    //若好友在线，发送通知更新好友申请菜单标记
                    JSONArray alias = json.getJSONArray("alias");
                    if (null != friendid) {
                        if (sessions.containsKey(friendid.toString()) && sessions.get(friendid.toString()).getSession().isOpen()) {
                            msg = new Message(EventEnum.NOTICE.getEvent(), alias);
                            sessions.get(friendid.toString()).getSession().sendMessage(this.buildWsMessage(msg.toString()));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        Object memberid = this.getMemberid(session);
        sessions.remove(memberid.toString());
        logger.info("编号为" + memberid + "会员的连接出错，原因：" + exception.getMessage());
        logger.info("移除编号为" + memberid + "会员的连接，当前连接人数：" + sessions.size());

        if (null != memberid) {
            Member member = this.memberService.selectByPrimaryKey(memberid);

            if (null != member) {
                if (member.getMtype() == 1) {
                    //客服账号离线，通知所有在线会员已下线
                    if (sessions.size() > 0) {
                        for (Map.Entry<String, ServerPoint> entry : sessions.entrySet()) {
                            //自己下线不发给自己
                            if (entry.getKey().equals(member.getMemberid().toString())) continue;

                            WebSocketSession ws = entry.getValue().getSession();
                            if (null != ws && ws.isOpen()) {
                                JSONObject data = new JSONObject();
                                data.put("friendid", memberid.toString());
                                data.put("online_total", sessions.size());
                                Message msg = new Message(EventEnum.OFFLINE.getEvent(), data);
                                ws.sendMessage(this.buildWsMessage(msg.toString()));
                            }
                        }
                    }
                } else {
                    //非客服账号离线，发送offline事件，通知所有好友已下线
                    List<Map<String, Object>> friends = this.imFriendService.selectFriends(memberid);
                    if (null != friends && friends.size() > 0) {
                        for (Map<String, Object> friend : friends) {
                            String friendid = friend.get("memberid").toString();
                            ServerPoint fpoint = sessions.get(friendid);
                            if (null != fpoint) {
                                WebSocketSession ws = fpoint.getSession();
                                if (null != ws && ws.isOpen()) {
                                    JSONObject data = new JSONObject();
                                    data.put("friendid", memberid.toString());
                                    data.put("online_total", sessions.size());
                                    Message msg = new Message(EventEnum.OFFLINE.getEvent(), data);
                                    ws.sendMessage(this.buildWsMessage(msg.toString()));
                                }
                            }
                        }
                    }
                }
            }

            //移除在线好友容器中的数据
            sessions.remove(memberid.toString());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        Object memberid = this.getMemberid(session);
        DictItemView obj = DictCache.getDictItem("WEBSOCKET_CLOSE_STATE", String.valueOf(status.getCode()));
        String reason = null;
        if (null != obj) reason = obj.getDictitemname();
        logger.info("编号为" + memberid + "会员的连接关闭，状态码：" + status.getCode() + "，原因：" + reason);
        logger.info("移除编号为" + memberid + "会员的连接，当前连接人数：" + sessions.size());

        if (null != memberid) {
            Member member = this.memberService.selectByPrimaryKey(memberid);
            if (null != member) {
                if (member.getMtype() == 1) {
                    //客服账号离线，通知所有在线会员已下线
                    if (sessions.size() > 0) {
                        for (Map.Entry<String, ServerPoint> entry : sessions.entrySet()) {
                            //自己下线不发给自己
                            if (entry.getKey().equals(member.getMemberid().toString())) continue;

                            WebSocketSession ws = entry.getValue().getSession();
                            if (null != ws && ws.isOpen()) {
                                JSONObject data = new JSONObject();
                                data.put("friendid", memberid.toString());
                                data.put("online_total", sessions.size());
                                Message msg = new Message(EventEnum.OFFLINE.getEvent(), data);
                                ws.sendMessage(this.buildWsMessage(msg.toString()));
                            }
                        }
                    }
                } else {
                    //非客服账号离线，发送offline事件，通知所有好友已下线
                    List<Map<String, Object>> friends = this.imFriendService.selectFriends(memberid);
                    if (null != friends && friends.size() > 0) {
                        for (Map<String, Object> friend : friends) {
                            String friendid = friend.get("memberid").toString();
                            ServerPoint fpoint = sessions.get(friendid);
                            if (null != fpoint) {
                                WebSocketSession ws = fpoint.getSession();
                                if (null != ws && ws.isOpen()) {
                                    JSONObject data = new JSONObject();
                                    data.put("friendid", memberid.toString());
                                    data.put("online_total", sessions.size());
                                    Message msg = new Message(EventEnum.OFFLINE.getEvent(), data);
                                    ws.sendMessage(this.buildWsMessage(msg.toString()));
                                }
                            }
                        }
                    }
                }
            }

            //移除在线好友容器中的数据
            sessions.remove(memberid.toString());

            //移除redis缓存数据
            if (null != member && member.getMtype() != 1) this.websocketSessionService.delete(memberid.toString());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }

}