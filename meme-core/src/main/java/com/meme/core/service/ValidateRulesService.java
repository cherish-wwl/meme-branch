package com.meme.core.service;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.ValidateRules;

public interface ValidateRulesService extends IService<ValidateRules, Form>{

	/**
	 * 根据表名查询最新的验证规则
	 * @param tableName
	 * @return
	 */
	ValidateRules selectLastRulesByTableName(String tableName);
	
	/**
	 * 清除所有验证规则
	 * @return
	 */
	int clearAll();
}
