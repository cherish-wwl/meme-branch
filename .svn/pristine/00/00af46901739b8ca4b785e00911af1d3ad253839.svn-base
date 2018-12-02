package com.meme.im.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.meme.core.cache.ParamsCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.ImFriendApplyMapper;
import com.meme.im.dao.ImFriendMapper;
import com.meme.im.dao.ImGroupMapper;
import com.meme.im.dao.ImMessageMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.ImFriend;
import com.meme.im.pojo.ImFriendApply;
import com.meme.im.pojo.ImGroup;
import com.meme.im.pojo.ImMessage;
import com.meme.im.service.ImGroupService;
import com.meme.im.util.AccountUtil;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

@Service("ImGroupService")
@Transactional
public class ImGroupServiceImpl implements ImGroupService {
	@Resource private ImGroupMapper mapper;
	@Resource private ImMessageMapper imMessageMapper;
	@Resource private ImFriendMapper imFriendMapper;
	@Resource private ImFriendApplyMapper imFriendApplyMapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImGroup record) {
		// TODO Auto-generated method stub
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ImGroup record) {
		// TODO Auto-generated method stub
		return this.mapper.insertSelective(record);
	}

	@Override
	public ImGroup selectByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImGroup record) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImGroup record) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ImGroup> selectAll() {
		// TODO Auto-generated method stub
		return (List<ImGroup>) MapperHelper.toBeanList(this.mapper.selectAll(ImGroup.class), ImGroup.class);

	}

	@Override
	public List<ImGroup> selectByPagination(Form form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Form form) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ImGroup> selectByEntity(ImGroup record) {
		// TODO Auto-generated method stub
		return (List<ImGroup>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ImGroup.class);
	}

	@Override
	public int batchInsert(List<ImGroup> record) {
		// TODO Auto-generated method stub
		return this.mapper.batchInsert(record, ImGroup.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		// TODO Auto-generated method stub
		return this.mapper.batchDelete(record, ImGroup.class);
	}

	@Override
	public int batchUpdate(List<ImGroup> record) {
		// TODO Auto-generated method stub
		return this.mapper.batchUpdate(record, ImGroup.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, ImGroup record) {
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			if(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
						String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
						String msg=result.getMessage();
						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						//非覆盖上传
						String prefix=ParamsCache.get("MEMBER_COVER")==null?"":ParamsCache.get("MEMBER_COVER").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    		if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						Object mineid = AccountUtil.getMemberid(request);
						long memberid = Long.valueOf(mineid.toString());
						long groupid = IDGenerator.generateId();
						record.setId(groupid);
						record.setMemberid(memberid);
						record.setAvatar("http://"+domain+"/"+key);
						record.setState(0);
						record.setNum(1);
						record.setAddtime(new Date());
						this.mapper.insertSelective(record);
						ImFriend imFriend = new ImFriend();
						imFriend.setId(IDGenerator.generateId());
						imFriend.setGroupid(groupid);
						imFriend.setFriendid(memberid);
						imFriend.setAddtime(new Date());
						this.imFriendMapper.insertSelective(imFriend);
						return ResultMessage.success("创建群组成功", record);
					}else return ResultMessage.failed("请选择群组头像上传");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				return ResultMessage.failed("请选择群组头像上传");
			}
		}
		return ResultMessage.failed("创建群组失败");
	}

	@Override
	public ResultMessage exit(Object mineid, Object groupid) {
		 ImGroup imGroup = this.selectByPrimaryKey(groupid);
		if(imGroup != null && imGroup.getMemberid().equals(mineid)) {
			this.deleteByPrimaryKey(groupid);
			List<Criterion> deleteCriterion = new ArrayList<Criterion>();
			deleteCriterion.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			deleteCriterion.add(new Criterion.CriterionBuilder().condition("groupid").operator(Operator.EQUAL).leftValue(groupid).javaType(Long.class).build());
			this.imFriendMapper.deleteByCriterions(deleteCriterion, ImFriend.class);
			this.imFriendApplyMapper.deleteByCriterions(deleteCriterion, ImFriendApply.class);
			this.imMessageMapper.deleteByCriterions(deleteCriterion, ImMessage.class);
		}else {
			List<Criterion> deleteCriterion = new ArrayList<Criterion>();
			deleteCriterion.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			deleteCriterion.add(new Criterion.CriterionBuilder().condition("groupid").operator(Operator.EQUAL).leftValue(groupid).javaType(Long.class).build());
			deleteCriterion.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			deleteCriterion.add(new Criterion.CriterionBuilder().condition("friendid").operator(Operator.EQUAL).leftValue(mineid).javaType(Long.class).build());
			this.imFriendMapper.deleteByCriterions(deleteCriterion, ImFriend.class);
		}
		return ResultMessage.defaultSuccessMessage();
	}
	@Override
	public ResultMessage edit(HttpServletRequest request, ImGroup record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectGroupListByPagination(Form form) {
		return this.mapper.selectGroupListByPagination(form);
	}

	@Override
	public int countStrangers(Form form) {
		return this.mapper.countGrouplist(form);
	}

	@Override
	public List<ImGroup> selectGroups(Long memberid) {
		// TODO Auto-generated method stub
		return this.mapper.selectGroups(memberid);
	}

	@Override
	public List<Map<String, Object>> getGroupMembers(Long groupid) {
		// TODO Auto-generated method stub
		return this.mapper.getGroupMembers(groupid);
	}

	@Override
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.countView(form);
	}

	@Override
	public List<Map<String, Object>> selectGroupMemberByPagination(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.selectGroupMemberByPagination(form);
	}

	@Override
	public int countGroupMember(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.countGroupMember(form);
	}

	@Override
	public List<Map<String, Object>> selectGroupMessageByPagination(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.selectGroupMessageByPagination(form);
	}

	@Override
	public int countGroupMessage(ImForm form) {
		// TODO Auto-generated method stub
		return this.mapper.countGroupMessage(form);
	}

}
