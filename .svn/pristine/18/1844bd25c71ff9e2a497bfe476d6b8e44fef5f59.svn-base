package com.meme.im.service.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.ImMessageMapper;
import com.meme.im.entity.enums.ChatType;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.ImMessage;
import com.meme.im.service.ImMessageService;

@Service("ImMessageService")
@Transactional
public class ImMessageServiceImpl extends AbstractService implements ImMessageService {

	protected static final String Timestamp = null;
	@Resource private ImMessageMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImMessage record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ImMessage record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public ImMessage selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImMessage record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImMessage record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImMessage> selectAll(){
		return (List<ImMessage>) MapperHelper.toBeanList(this.mapper.selectAll(ImMessage.class), ImMessage.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImMessage> selectByPagination(Form form) {
		this.buildSearchCriterion(ImMessage.class, form);
		this.buildOrderByCriterion(ImMessage.class, form);
		this.buildLimitCriterion(ImMessage.class, form);
		return (List<ImMessage>) MapperHelper.toBeanList(this.mapper.selectByPagination(ImMessage.class,this.getList()), ImMessage.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(ImMessage.class, form);
		return this.mapper.count(ImMessage.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImMessage> selectByEntity(ImMessage record) {
		return (List<ImMessage>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ImMessage.class);
	}

	@Override
	public int batchInsert(List<ImMessage> record) {
		return this.mapper.batchInsert(record,ImMessage.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,ImMessage.class);
	}

	@Override
	public int batchUpdate(List<ImMessage> record) {
		return this.mapper.batchUpdate(record,ImMessage.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, ImMessage record) {
		return null;
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, ImMessage record) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		int result=0;
		if(null!=ids&&ids.size()>0){
			result=this.batchDelete(ids);
		}
		if(result>0) return ResultMessage.success("删除消息成功");
		return ResultMessage.failed("删除消息失败");
	}

	@Override
	public List<Map<String, Object>> selectUnReadMessages(Object memberid) {
		return this.mapper.selectUnReadMessages(memberid);
	}
	
	@Override
	public List<Map<String, Object>> selectUnReadGroupMessages(Object memberid) {
		return this.mapper.selectUnReadGroupMessages(memberid);
	}

	@Override
	public int updateReadState(List<Object> msgids) {
		if(null!=msgids&&msgids.size()>0){
			return this.mapper.updateReadState(msgids);
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(ImForm form) {
		return this.mapper.countView(form);
	}

	@Override
	public  List<Map<String,Object>> getFriendMessage(Object friendid, Object mineid,Integer page,String type) {
		 page = (page -1) * 20;
		 ChatType chatType = ChatType.valueOf(type.toUpperCase());
         if (null == chatType) return null;
         List<Map<String,Object>> message = null;
         if(chatType == ChatType.FRIEND) {
        	 	 message  = this.mapper.selectFriendMessages(mineid, friendid,page);
         }else if(chatType == ChatType.GROUP) {
        	 	message = this.mapper.selectGroupMessages(mineid, friendid, page);
         }
         message.sort(new Comparator<Map<String,Object>>() {  //按照发送消息时间升序排序消息记录
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					Timestamp t1 = (Timestamp)o1.get("timestamp");
					Timestamp t2 = (Timestamp)o2.get("timestamp");
					if(t1.getTime() > t2.getTime()) {
						return 1;
					}else if(t1.getTime() < t2.getTime()) {
						return -1;
					}
					return 0;
				}
			});
			 return message;
	}
}
