package com.meme.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.ValidateRules;

public interface ValidateRulesMapper extends BaseMapper{
	/**
	 * 根据表名查询最新的验证规则
	 * @param tableName
	 * @return
	 */
	List<ValidateRules> selectLastRulesByTableName(@Param("tableName")String tableName);
	
	/**
	 * 清除所有验证规则
	 * @return
	 */
	int clearAll();
}