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
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.MemeColumnSectionMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeColumnSection;
import com.meme.im.pojo.view.MemeColumnSectionView;
import com.meme.im.service.MemeColumnSectionService;

@Service("MemeColumnSectionService")
@Transactional
public class MemeColumnSectionServiceImpl extends AbstractService implements MemeColumnSectionService {

	@Resource private MemeColumnSectionMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeColumnSection record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeColumnSection record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeColumnSection selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeColumnSection record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeColumnSection record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumnSection> selectAll(){
		return (List<MemeColumnSection>) MapperHelper.toBeanList(this.mapper.selectAll(MemeColumnSection.class), MemeColumnSection.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumnSection> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeColumnSection.class, form);
		this.buildLimitCriterion(MemeColumnSection.class, form);
		return (List<MemeColumnSection>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeColumnSection.class,this.getList()), MemeColumnSection.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeColumnSection.class, form);
		return this.mapper.count(MemeColumnSection.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumnSection> selectByEntity(MemeColumnSection record) {
		return (List<MemeColumnSection>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeColumnSection.class);
	}

	@Override
	public int batchInsert(List<MemeColumnSection> record) {
		return this.mapper.batchInsert(record,MemeColumnSection.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeColumnSection.class);
	}

	@Override
	public int batchUpdate(List<MemeColumnSection> record) {
		return this.mapper.batchUpdate(record,MemeColumnSection.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeColumnSection record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		
		if(!StringUtil.isEmpty(record.getSectioncode())){
			List<MemeColumnSection> list=this.selectByCode(record.getSectioncode());
			if(null!=list&&list.size()>0) return ResultMessage.failed("栏目块编码重复，请修改");
		}
		
		if(null!=record.getPid()&&record.getPid()!=0l){
			MemeColumnSection cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级栏目块不存在");
			//上级栏目块非顶级栏目块时取上级栏目块的所属栏目id
			record.setColumnid(cat.getColumnid());
		}else{
			if(null==record.getColumnid() || record.getColumnid()==0l) return ResultMessage.failed("所属栏目必填");
		}
		record.setId(IDGenerator.generateId());
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeColumnSection record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		
		List<MemeColumnSection> list=this.selectByCode(record.getSectioncode());
		if(null!=list&&list.size()>0){
			for(MemeColumnSection sec:list){
				if(sec.getId().longValue()!=record.getId().longValue()){
					return ResultMessage.failed("栏目块编码重复，请修改");
				}
			}
		}
		if(null!=record.getPid()&&record.getPid()!=0l){
			MemeColumnSection cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级栏目块不存在");
			//上级栏目块非顶级栏目块时取上级栏目块的所属栏目id
			record.setColumnid(cat.getColumnid());
		}else{
			if(null==record.getColumnid() || record.getColumnid()==0l) return ResultMessage.failed("所属栏目必填");
		}
		
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
	public List<MemeColumnSectionView> selectAllSections() {
		return this.mapper.selectAllSections();
	}

	@Override
	public List<MemeColumnSection> selectByCode(String code) {
		return this.mapper.selectByCode(code);
	}

	@Override
	public List<MemeColumnSectionView> selectColumnSections(Long columnid) {
		return this.mapper.selectColumnSections(columnid);
	}

	@Override
	public List<MemeColumnSection> getSelectedSections(Long contentid) {
		return this.mapper.getSelectedSections(contentid);
	}

	@Override
	public List<MemeColumnSectionView> selectByColumncode(String columncode) {
		return this.mapper.selectByColumncode(columncode);
	}
}
