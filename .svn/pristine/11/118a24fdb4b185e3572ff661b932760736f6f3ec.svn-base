package com.meme.im.service.impl;

import java.util.Date;
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
import com.meme.core.util.IDGenerator;
import com.meme.im.dao.MemberMapper;
import com.meme.im.dao.MemeContentMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeContent;
import com.meme.im.service.MemeCategoryService;
import com.meme.im.service.MemeContentSectionService;
import com.meme.im.service.MemeContentService;

@Service("MemeContentService")
@Transactional
public class MemeContentServiceImpl extends AbstractService implements MemeContentService {

	@Resource private MemeContentMapper mapper;
	@Resource private MemberMapper memberMapper;
	@Resource private MemeContentSectionService memeContentSectionService;
	@Resource private MemeCategoryService memeCategoryService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeContent record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeContent record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeContent selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeContent record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeContent record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContent> selectAll(){
		return (List<MemeContent>) MapperHelper.toBeanList(this.mapper.selectAll(MemeContent.class), MemeContent.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContent> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeContent.class, form);
		this.buildLimitCriterion(MemeContent.class, form);
		return (List<MemeContent>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeContent.class,this.getList()), MemeContent.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeContent.class, form);
		return this.mapper.count(MemeContent.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContent> selectByEntity(MemeContent record) {
		return (List<MemeContent>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeContent.class);
	}

	@Override
	public int batchInsert(List<MemeContent> record) {
		return this.mapper.batchInsert(record,MemeContent.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeContent.class);
	}

	@Override
	public int batchUpdate(List<MemeContent> record) {
		return this.mapper.batchUpdate(record,MemeContent.class);
	}

	public ResultMessage handleResult(int result){
		if(result>0){
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.defaultFaileMessage();
		}
	}
	
	@Override
	public ResultMessage add(HttpServletRequest arg0, MemeContent movie) {
		System.out.println("333333333333333");
		String account = arg0.getParameter("account");
		Member member = memberMapper.selectByAccount(account);
		Date time = new Date();
		movie.setContentid(IDGenerator.generateId());
		if(member != null){
			Long memberid= member.getMemberid();
			movie.setMemberid(memberid);
		}
		movie.setAddtime(time);
		
		System.out.println(movie);
		int result=mapper.insertSelective(movie);;
		System.out.println("add"+result);
		return handleResult(result);
	}

	@Override
	public ResultMessage edit(HttpServletRequest arg0, MemeContent movie) {
		String account = arg0.getParameter("account");
		Member member = memberMapper.selectByAccount(account);
		if(member != null){
			Long memberid= member.getMemberid();
			movie.setMemberid(memberid);
		}
		Date time = new Date();
		movie.setPublishtime(time);
		System.out.println(movie);
		int result=mapper.updateByPrimaryKeySelective(movie);;
		System.out.println("edit"+result);
		return handleResult(result);
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			this.batchDelete(ids);
			for(Object id:ids){
				this.memeContentSectionService.deleteByContentId(Long.valueOf(id.toString()));
			}
		}
		return ResultMessage.defaultSuccessMessage();
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
	public List<Map<String, Object>> selectContentSections(Long columnid) {
		return this.mapper.selectContentSections(columnid);
	}

	@Override
	public List<Map<String, Object>> selectContentPagination(ImForm form) {
		return this.mapper.selectContentPagination(form);
	}

	@Override
	public Integer countContentPagination(ImForm form) {
		return this.mapper.countContentPagination(form);
	}

	@Override
	public List<Map<String, Object>> selectByPaginationSectionView(ImForm form) {
		return this.mapper.selectByPaginationSectionView(form);
	}

	@Override
	public Integer countSelctionView(ImForm form) {
		return this.mapper.countSelctionView(form);
	}
	
	
}
