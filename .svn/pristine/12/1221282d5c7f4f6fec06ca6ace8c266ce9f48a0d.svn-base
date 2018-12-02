package com.meme.im.service;

import java.util.List;
import java.util.Map;


import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.ImGroup;

public interface ImGroupService extends IService<ImGroup, Form>, CrudService<ImGroup> {

	List<Map<String, Object>> selectGroupListByPagination(Form form);

	int countStrangers(Form form);

	List<ImGroup> selectGroups(Long memberid);

	List<Map<String, Object>> getGroupMembers(Long groupid);

	ResultMessage exit(Object memberid, Object groupid);

	List<Map<String, Object>> selectByPaginationView(ImForm form);

	int countView(ImForm form);

	List<Map<String, Object>> selectGroupMemberByPagination(ImForm form);

	int countGroupMember(ImForm form);

	List<Map<String, Object>> selectGroupMessageByPagination(ImForm form);

	int countGroupMessage(ImForm form);

}
