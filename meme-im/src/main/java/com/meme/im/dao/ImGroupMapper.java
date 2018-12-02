package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.ImGroup;

public interface ImGroupMapper extends BaseMapper{

	List<Map<String, Object>> selectGroupListByPagination(Form form);

	int countGrouplist(Form form);
	
	List<ImGroup> selectGroups(@Param("memberid")Long memberid);
	
	int updateNum(@Param("id")Long id);

	List<Map<String, Object>> getGroupMembers(@Param("groupid")Long  groupid);

	List<Map<String, Object>> selectByPaginationView(ImForm form);

	int countView(ImForm form);
	
	List<Map<String, Object>> selectGroupMemberByPagination(ImForm form);
	
	int countGroupMember(ImForm form);

	List<Map<String, Object>> selectGroupMessageByPagination(ImForm form);

	int countGroupMessage(ImForm form);
}