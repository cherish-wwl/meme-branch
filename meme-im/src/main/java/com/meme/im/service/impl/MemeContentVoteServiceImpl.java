package com.meme.im.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeContentVoteMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeContentVote;
import com.meme.im.service.MemeContentVoteService;

@Service("MemeContentVoteService")
@Transactional
public class MemeContentVoteServiceImpl extends AbstractService implements MemeContentVoteService {

	@Resource private MemeContentVoteMapper mapper;
	@Resource private MemeContentVoteService memeContentSectionService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeContentVote record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeContentVote record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeContentVote selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeContentVote record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeContentVote record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentVote> selectAll(){
		return (List<MemeContentVote>) MapperHelper.toBeanList(this.mapper.selectAll(MemeContentVote.class), MemeContentVote.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentVote> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeContentVote.class, form);
		this.buildLimitCriterion(MemeContentVote.class, form);
		return (List<MemeContentVote>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeContentVote.class,this.getList()), MemeContentVote.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeContentVote.class, form);
		return this.mapper.count(MemeContentVote.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentVote> selectByEntity(MemeContentVote record) {
		return (List<MemeContentVote>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeContentVote.class);
	}

	@Override
	public int batchInsert(List<MemeContentVote> record) {
		return this.mapper.batchInsert(record,MemeContentVote.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeContentVote.class);
	}

	@Override
	public int batchUpdate(List<MemeContentVote> record) {
		return this.mapper.batchUpdate(record,MemeContentVote.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeContentVote record) {
		
		return ResultMessage.defaultFaileMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeContentVote record) {
		
		this.updateByPrimaryKeySelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			this.batchDelete(ids);
		}
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public boolean checkVoted(Long contentid,Long memberid, String ip) {
		List<MemeContentVote> list=this.mapper.checkVoted(contentid,memberid, ip);
		if(null!=list&&list.size()>0) return true;
		return false;
	}
}
