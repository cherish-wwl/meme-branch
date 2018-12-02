package com.meme.im.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.im.dao.MemberMapper;
import com.meme.im.dao.MemeContentMapper;
import com.meme.im.dao.MemeMovieMapper;
import com.meme.im.pojo.MemeContent;
import com.meme.im.service.MemeMovieService;


@Service("MemeMovieService")
@Transactional
public class MemeMovieServiceImpl extends AbstractService implements MemeMovieService{
	
	@Resource 
	private MemeMovieMapper memeMovieMapper;
	
	@Resource
	private MemeContentMapper mapper;
	
	@Resource
	private MemberMapper memberMapper;
	
	@Override
	public int batchDelete(List<Object> arg0) {
		return 0;
	}

	@Override
	public int batchInsert(List<MemeContent> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchUpdate(List<MemeContent> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int count(Form arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(MemeContent movie) {
		System.out.println("***********");
		return memeMovieMapper.insert(movie);
	}

	@Override
	public int insertSelective(MemeContent arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MemeContent> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemeContent> selectByEntity(MemeContent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemeContent> selectByPagination(Form arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemeContent selectByPrimaryKey(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(MemeContent arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(MemeContent arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


}
