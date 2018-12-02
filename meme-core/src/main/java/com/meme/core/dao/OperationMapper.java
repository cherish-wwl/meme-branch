package com.meme.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.Operation;

public interface OperationMapper extends BaseMapper{
	
	/**
	 * 根据菜单ID数组查询
	 * @param list
	 * @return
	 */
	List<Operation> selectByMenuids(List<Object> list);
	
	/**
	 * 查询单位授权的操作
	 * @param list
	 * @return
	 */
	List<Operation> selectOrganOperations(@Param("organid")Object organid,@Param("menuids") List<Object> menuids,@Param("platformids") List<Object> platformids);
	
	/**
	 * 查询角色授权的操作
	 * @param list
	 * @return
	 */
	List<Operation> selectRoleOperations(@Param("roleids")List<Object> roleids, @Param("menuids")List<Object> menuids, @Param("platformids")List<Object> platformids);
}